import { IUser } from "./User"
export interface IAppointment{
    bloodBankId: number,
    startTime: Date,
    endTime: Date,
    medicalStaff: IUser[],
    available: boolean
}