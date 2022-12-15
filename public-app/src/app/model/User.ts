import { IAddress } from "./Address";
import { IBloodBank } from "./BloodBankk";

export interface IUser{
  id: number;
  firstName: string,
  lastName: string,
  jmbg: number,
  email: string,
  userType: string,
  bloodType: string,
  password: string,
  bloodBank: IBloodBank,
  phoneNumber: string,
  gender: string,
  workplaceName: string,
  jobTitle: string,
  address: IAddress,
  points: number
}
