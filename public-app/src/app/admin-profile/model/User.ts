import { IAddress } from "./Address";
import { IBloodBank } from "./BloodBank"

export interface IUser{
    id: number,
    userType: string,
    firstName: string,
    lastName: string,
    jmbg: number,
    email: string,
    password: string,
    bloodType: string,
    bloodBank: IBloodBank,
    phoneNumber: number,
    address: IAddress
}