import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { ToastrService } from 'ngx-toastr';

export class Message {
  startLocation: number[];
  endLocation: number[];
  frequency: number;
}

declare var SockJS;
declare var Stomp;

@Component({
  selector: 'app-delivery-map',
  templateUrl: './delivery-map.component.html',
  styleUrls: ['./delivery-map.component.scss']
})
export class DeliveryMapComponent implements OnInit {

  private stompClient: any;
  isLoaded: boolean = false;
  //position: number[] = [45.24513700981312,19.84731234191584];
  positionX: number;
  positionY: number;
  frequency = 1;

  
  private defaultIcon = new L.icon({
    iconUrl: '../../assets/icons/blood-drop.png',
    iconSize: [20, 27],
    iconAnchor: [2, 2],
    popupAnchor: [0, -2]
  });

  private map;

  private initMap(): void {
    this.map = L.map('map', {
      center: [45.24513700981312,19.84731234191584],
      zoom: 13
    });
    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);
  }


  constructor(private http: HttpClient, private toast: ToastrService) { }

  ngOnInit(): void {
    this.initMap();
    let that = this;
    this.map.on('click', function(e){
        var coord = e.latlng;
        console.log(coord);
        that.positionX = coord.lat;
        that.positionY = coord.lng;
      });
    this.initializeWebSocketConnection();
  }

  
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/socket';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/socket-publisher', (message) => {
        if (message.body) {
          console.log(message.body);
          const msgSplit = message.body.split(',');
          const lat = parseFloat(msgSplit[0]);
          const lng = parseFloat(msgSplit[1]);
          var marker = L.marker([lng,lat], { icon: that.defaultIcon}).addTo(that.map)
        }
      });
    });
  }

  send() {
    // this.positionY
    // this.positionX
    console.log(this.positionX, this.positionY);
    this.http.post('http://localhost:8080/location/start', 
    {
      startLocation: [],
      endLocation: [this.positionY, this.positionX],
      frequency: this.frequency
    }).subscribe(
      (res) => {
        this.toast.success('Sending blood supply, sit tight!');
      },
      (err) => {
        this.toast.error('We lost communication to location service, please try again later!');
      }
    );
  }

}
