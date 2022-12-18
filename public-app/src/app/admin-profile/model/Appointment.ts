import { IUser } from "./User"
export interface IAppointment{
    bloodBankId: number,
    startTime: Date,
    duration: number,
    medicalStaff: IUser[],
    available: boolean
    appointmentInfo: string
}