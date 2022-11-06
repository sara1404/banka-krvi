import {IAddress} from './Address'

export interface IBloodBank {
  name: string;
  address: IAddress;
  description: string;
  averageGrade: number;
}
