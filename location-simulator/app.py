from flask import Flask
from flask import Response
from flask import request
import pika
import time
import _thread
import requests

app = Flask(__name__)

@app.post("/start")
def start():
    json = request.get_json(force=True)
    freq = int(json['frequency'])
    coordinates = get_points(json['startLocation'], json['endLocation'])
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()
    channel.queue_declare(queue='coordinates', durable=True)
    _thread.start_new_thread(start_queuing, (coordinates, connection, freq))
    
    return Response(status=200)

def start_queuing(coordinates, connection, freq):
    total = len(coordinates)
    print('Started queuing ' + str(total) + ' coordinates:')
    i = 0
    for position in coordinates:
        i += 1
        print('Position ' + str(i) + '/' + str(total) + ' sent', flush=True)
        position_str = jsonify_position(position)
        connection.channel().basic_publish(exchange='', routing_key='coordinates', body=position_str)
        time.sleep(freq)
    print('All coordinates were sent, closing queue!')
    connection.close()

def jsonify_position(position):
    return '{ "message": "' + str(position[0]) + ',' + str(position[1]) + ' " }'

URL = 'https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248f99840030bd54a4d9dde6aafc89b6316&start={start}&end={end}'

def get_points(start, end):
    url = URL.format(start=array_to_string(start), end=array_to_string(end))
    r = requests.get(url)
    reset_sec = 2
    if r.status_code != 200:
        print('Fetching coordinates failed, resending request in {sec} seconds'.format(sec=reset_sec))
        time.sleep(reset_sec)
        r = requests.get(url)
        

    data = r.json()
    return data['features'][0]['geometry']['coordinates']

def array_to_string(array):
    return str(array[0]) + ',' + str(array[1])

if __name__ == '__main__':
    app.run(host='localhost', port=8081, debug=True)