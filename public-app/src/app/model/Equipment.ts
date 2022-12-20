import { IBloodBank } from "./BloodBankk";

export interface IEquipment{
    equipmentType: string,
    quantity: number,
    bloodBank: IBloodBank
}