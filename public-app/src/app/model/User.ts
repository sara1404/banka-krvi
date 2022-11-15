import { IAddress } from "./Address";
import { IBloodBank } from "./BloodBankk";

export interface IUser{
  id: number;
  firstName: string,
  lastName: string,
  password: string,
  address: IAddress
  jmbg: number,
  email: string,
  bloodType: string,
  bloodBank: IBloodBank,
  phoneNumber: string,
  gender: string,
  workplaceName: string,
  jobTitle: string
}
