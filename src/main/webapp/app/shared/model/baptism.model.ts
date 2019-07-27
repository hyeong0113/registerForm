import { IRegisterMember } from 'app/shared/model/register-member.model';

export interface IBaptism {
  id?: number;
  baptismType?: string;
  baptismYear?: string;
  baptismChurch?: string;
  registerMember?: IRegisterMember;
}

export const defaultValue: Readonly<IBaptism> = {};
