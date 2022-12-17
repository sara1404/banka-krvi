import { IBloodBank } from "../admin-profile/model/BloodBank";
import { IAppointment } from "./Appointment";

export interface IPage {
    content : IBloodBank[],
    totalPages : number,
    totalElements : number,

}

export interface IPageAppointment {
    content: IAppointment[],
    totalElements: number
}