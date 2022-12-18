import { IBloodBank } from "./BloodBankk";

export interface IAppointment{
    id: number
    startTime: Date
    duration: number
    bloodBank: IBloodBank
}
