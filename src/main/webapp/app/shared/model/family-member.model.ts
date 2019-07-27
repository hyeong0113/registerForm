import { Moment } from 'moment';
import { IVolunteer } from 'app/shared/model/volunteer.model';
import { IRegisterMember } from 'app/shared/model/register-member.model';

export interface IFamilyMember {
  id?: number;
  relationStatus?: string;
  previousRegister?: string;
  korName?: string;
  engName?: string;
  birthday?: Moment;
  gender?: string;
  profession?: string;
  companyName?: string;
  cellPhone?: string;
  emailAddress?: string;
  churchServed?: string;
  yearServed?: string;
  dutyStatus?: string;
  prevChurch?: string;
  volunteers?: IVolunteer[];
  registerMember?: IRegisterMember;
}

export const defaultValue: Readonly<IFamilyMember> = {};
