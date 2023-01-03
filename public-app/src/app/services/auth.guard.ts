import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { IUser } from '../model/User';
import { AuthService } from './auth.service';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { async, from, Observable, take } from 'rxjs';
import {Subject } from 'rxjs'

@Injectable({ providedIn: 'root' }) //{ providedIn: 'root' }
export class AuthGuard implements CanActivate{
    public currentUser: IUser = {
      address: null,
      bloodBank: null,
      bloodType: null,
      email: null,
      firstLogged: null,
      firstName: null,
      gender: null,
      id: null,
      jmbg: null,
      jobTitle: null,
      lastName: null,
      password: null,
      phoneNumber: null,
      points:null,
      userType: 'REGISTERED',
      workplaceName: null
    }
    loggedIn:boolean;

    constructor(private router: Router,
      private authService: AuthService) {
        console.log('u konstruktoru')
        this.authService.getCurrentUser().subscribe(data => { this.currentUser = data; console.log(this.currentUser); });
      }
    //private router: Router, private authenticationService: AuthenticationService
    public currentUserValue(): IUser {
        this.authService.getCurrentUser().subscribe(data=>{this.currentUser = data});
        return this.currentUser;
    }
    public isLoggedIn(): boolean{
        this.loggedIn = this.authService.isLoggedIn();
        return this.loggedIn;
    }
    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      console.log('u canactivate')
      this.authService.getCurrentUser().subscribe(data=>{this.currentUser = data; console.log(this.currentUser);});
      this.loggedIn = this.authService.isLoggedIn();
        if (this.isLoggedIn && this.currentUser.userType) { 
            // check if route is restricted by role
            if (route.data['roles'] && route.data['roles'].indexOf(this.currentUser.userType) === -1) {
                // role not authorised so redirect to home page
                //this.router.navigate(['/app']);
                return false;
            }

            // authorised so return true
            return true;
        }

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false;
    }
}


/*
class UserToken {}
class Permissions {
  canActivate(): boolean {
    return true;
  }
}

@Injectable({ providedIn: 'root' })
export class CanActivateTeam implements CanActivate {
  constructor(private permissions: Permissions, private currentUser: UserToken) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    //return this.permissions.canActivate(this.currentUser, route.params.id);
    return true;
  }
}
*/