import { IBloodBank } from "./BloodBankk";

export interface IUser{
  id: number;
  firstName: string,
  lastName: string,
  jmbg: number,
  email: string,
  bloodType: string,
  bloodBank: IBloodBank,
}

