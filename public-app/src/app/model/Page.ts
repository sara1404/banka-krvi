import { IBloodBank } from "../admin-profile/model/BloodBank";
import { IAppointment } from "./Appointment";
import { IUser } from "./User";

export interface IPage {
    content : IBloodBank[],
    totalPages : number,
    totalElements : number,

}

export interface IPageAppointment {
    content: IAppointment[],
    totalElements: number
}

export interface IPageUser {
    content: IUser[],
    totalElements: number
}