import { IBloodBank } from "./BloodBank"

export interface IUser{
    id: number,
    firstName: string,
    lastName: string,
    jmbg: number,
    email: string,
    password: string,
    bloodType: string,
    bloodBank: IBloodBank
}