import { IAddress } from "./Address";

export interface IBloodBank{
  version: number,
  name: string,
  address: IAddress,
  description: string,
  averageGrade: number
}
