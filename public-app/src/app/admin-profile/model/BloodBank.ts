import { IAddress } from "./Address";

export interface IBloodBank {
  version: number,
  id: number,
  name: string;
  address: IAddress;
  description: string;
  averageGrade: number;
}