import { IAddress } from "./Address";

export interface IBloodBank {
  id: number,
  name: string;
  address: IAddress;
  description: string;
  averageGrade: number;
}