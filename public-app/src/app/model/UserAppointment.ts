import { IBloodBank } from "./BloodBankk";
import { IUser } from "./User";

export interface IUserAppointment {
    id: number;
    user: IUser;
    startTime: Date;
    duration: number;
    bloodBank: IBloodBank
}
