# banka-krvi

## Running the project

**Backend** - Open the api folder as a IntelliJ project, reload maven and do maven clean install, next go to the BloodbankApplication class and run the main from there.
Your backend app should be hosted at `localhost:8080`

**Frontend** - Navigate to `public-app` folder and run `npm install` to install necessary NodeJS packages, then run `ng serve`, your frontend 
app should be hosted at `localhost:4200/`.

**Location service** - Go to `location-simulator`, then run `pip install -r requirements.txt` to install necessary packages, after that is done you can run the 
app using `python app.py`. Your location service is now hosted on `localhost:8081/`

**Important warning** - To enable communication with hospital and location service RabbitMQ server must be up and running, please follow the official RabbitMQ 
documentation to do so.

