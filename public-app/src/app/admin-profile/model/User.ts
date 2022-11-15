import { IAddress } from "./Address";
import { IBloodBank } from "./BloodBank"

export interface IUser{
    id: number,
    firstName: string,
    lastName: string,
    jmbg: number,
    email: string,
    userType: string,
    bloodType: string,
    phoneNumber: number,
    gender: number
    workplaceName: string,
    jobTitle: string,
    password: string,
    address: IAddress
}