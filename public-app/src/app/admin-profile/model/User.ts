import { IBloodBank } from "./BloodBank"

export interface IUser{
    firstName: string,
    lastName: string,
    jmbg: number,
    email: string,
    bloodType: string,
    bloodBank: IBloodBank
}