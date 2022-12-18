import { IBloodBank } from "./BloodBankk";
import { IUser } from "./User";

export interface IAppointment{
    id: number
    startTime: Date
    duration: number
    bloodBank: IBloodBank
    nurse: IUser
}