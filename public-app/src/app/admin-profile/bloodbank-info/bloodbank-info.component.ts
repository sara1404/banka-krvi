import { Component, OnInit } from '@angular/core';
import { zip } from 'rxjs';
import { IAddress } from '../model/Address';
import { IBloodBank } from '../model/BloodBank';
import { AdminInfoService } from '../service/admin-info.service';
import {fromLonLat, toLonLat} from 'ol/proj';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import Vector from 'ol/source/Vector.js'
import Point from 'ol/geom/Point'
import Feature from 'ol/Feature'
import Style from 'ol/style/Style'
import Circle from 'ol/style/Circle'
import Fill from 'ol/style/Fill'
import VectorLayer from 'ol/layer/Vector.js';

@Component({
  selector: 'app-bloodbank-info',
  templateUrl: './bloodbank-info.component.html',
  styleUrls: ['./bloodbank-info.component.scss']
})
export class BloodbankInfoComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }
  public bloodBank: IBloodBank;
  map: Map;

  public isDisabled: boolean = true;
  complete: boolean = true;
  showSave: boolean = false;
  showEdit: boolean = true;
  okName: boolean = true;
  okStreet: boolean = true;
  okNumber: boolean = true;
  okCity: boolean = true;
  okZipcode: boolean = true;
  okCountry: boolean = true;
  okLongitude: boolean = true;
  okLatitude: boolean = true;
  okDescription: boolean = true;
  okAverageGrade: boolean = true;
  updated: boolean = false;

  longitude: number;
  latitude: number;
  
  ngOnInit(): void {
    this.adminInfoService.getBloodBank().subscribe(data=>{this.bloodBank = data;
      var layer = new VectorLayer({
        source: new Vector({
            features: [
                new Feature({
                    geometry: new Point(fromLonLat([this.bloodBank.address.longitude, this.bloodBank.address.latitude]))
                })
            ]
        }),
        style: new Style({
          image: new Circle({
            radius: 5,
            fill: new Fill({color: 'red'})
          })
        })
      });
      layer.set('name', 'vectorLayer');
      this.map = new Map({
        view: new View({
          center: fromLonLat([this.bloodBank.address.longitude, this.bloodBank.address.latitude]),
          zoom: 18,
        }),
        layers: [
          new TileLayer({
            source: new OSM(),
          })
        ],
        target: 'ol-map'
      });
      this.map.addLayer(layer);
    });
    
  }

  validate(
    name: string,
    street: string,
    number: string,
    city: string,
    zipcode: string,
    country: string,
    longitude: string,
    latitude: string,
    description: string,
    averageGrade: string
  ):boolean{
    if(name==""|| street=="" || number=="" ||city=="" || zipcode == "" || country == "" || 
    longitude == "" || latitude == "" || description == "" || averageGrade == ""){
      this.complete = false;
    }else{
      this.complete = true;
    }
    if(name.substring(0,1) != name.substring(0,1).toUpperCase()){
      this.okName = false;
    }else{
      this.okName = true;
    }
    if(street.substring(0,1) != street.substring(0,1).toUpperCase()){
      this.okStreet = false;
    }else{
      this.okStreet = true;
    }
    if(!Number(number)){
      this.okNumber = false;
    }else{
      this.okNumber = true;
    }
    if(city.substring(0,1) != city.substring(0,1).toUpperCase()){
      this.okCity = false;
    }else{
      this.okCity = true;
    }
    if(!Number(zipcode) || zipcode.length < 5){
      this.okZipcode = false;
    }else{
      this.okZipcode = true;
    }
    if(country.substring(0,1) != country.substring(0,1).toUpperCase()){
      this.okCountry = false;
    }else{
      this.okCountry = true;
    }
    if(!Number(longitude)){
      this.okLongitude = false;
    }else{
      this.okLongitude = true;
    }
    if(!Number(latitude)){
      this.okLatitude = false;
    }else{
      this.okLatitude= true;
    }
    if(!this.complete || !this.okName || !this.okCity || !this.okNumber || !this.okZipcode || !this.okCountry 
      || !this.okLongitude || !this.okLatitude || !this.okDescription || !this.okAverageGrade){
      return false;
    }
    return true;
  }

  editClick()
  {
    this.isDisabled = false;
    this.showSave = true;
    this.showEdit = false;
    this.updated = false;
  }
  okClick(
    name: string,
    street: string,
    number: string,
    city: string,
    zipcode: string,
    country: string,
    longitude: string,
    latitude: string,
    description: string,
    averageGrade: string
  )
  {
    if(!this.validate(name, street, number, city, zipcode, country, longitude, latitude, description, averageGrade)){
      return;
    }
    const updatedAddress: IAddress={
      id: this.bloodBank.address.id,
      street: street,
      number: Number(number),
      city: city,
      zipcode: Number(zipcode),
      country: country,
      longitude: Number(longitude),
      latitude: Number(latitude)
    }
    const updatedBloodBank: IBloodBank = {
      id: this.bloodBank.id,
      name: name,
      address: updatedAddress,
      description: description,
      averageGrade: Number(averageGrade)
    }
    this.adminInfoService.editBloodBank(updatedBloodBank).subscribe(data=>{this.bloodBank = data;});
    this.isDisabled = true;
    this.showEdit = true;
    this.showSave = false;
    this.updated = true;
  }

  getCoord(event: any){
    var coordinate = this.map.getEventCoordinate(event);
    var lonlat = toLonLat(coordinate)
    this.reverseGeoCode(lonlat)
    this.map.getLayers().forEach(layer => {
      if (layer.get('name') && layer.get('name') == 'vectorLayer'){
          this.map.removeLayer(layer)
      }
    });
    var layer = new VectorLayer({
      source: new Vector({
          features: [
              new Feature({
                  geometry: new Point(fromLonLat([lonlat[0], lonlat[1]]))
              })
          ]
      }),
      style: new Style({
        image: new Circle({
          radius: 5,
          fill: new Fill({color: 'red'})
        })
      })
    });
    layer.set('name', 'vectorLayer');
    this.map.addLayer(layer)
    console.log(this.map.getAllLayers())
    //console.log(lonlat)
    //console.log(coordinate)
  }

  reverseGeoCode(coords){
    fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
                .then(function (response) {
                    return response.json();
                }).then(json => {
                console.log(coords);
                this.longitude = coords[0];
                this.latitude = coords[1];
                console.log(json.address);
                if (json.address.city) {
                  this.bloodBank.address.city = json.address.city;
                } else if (json.address.city_district) {
                  this.bloodBank.address.city = json.address.city_district;
                }

                if (json.address.road) {
                  this.bloodBank.address.street = json.address.road;
                }

                if (json.address.house_number) {
                  this.bloodBank.address.number = json.address.house_number;
                }

                if (json.address.postcode) {
                  this.bloodBank.address.zipcode = json.address.postcode;
                }
                this.bloodBank.address.longitude = this.longitude;
                this.bloodBank.address.latitude = this.latitude;
                }
                )
  }

}
