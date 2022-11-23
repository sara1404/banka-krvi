import { IBloodBank } from "../admin-profile/model/BloodBank";

export interface IPage {
    content : IBloodBank[],
    totalPages : number,
    totalElements : number
}