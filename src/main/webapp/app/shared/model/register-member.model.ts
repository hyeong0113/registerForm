import { Moment } from 'moment';
import { IFamilyMember } from 'app/shared/model/family-member.model';
import { IBaptism } from 'app/shared/model/baptism.model';
import { IVolunteer } from 'app/shared/model/volunteer.model';

export interface IRegisterMember {
  id?: number;
  previousRegister?: string;
  korName?: string;
  engName?: string;
  birthday?: Moment;
  gender?: string;
  firstStreet?: string;
  secondStreet?: string;
  city?: string;
  province?: string;
  postalCode?: string;
  cellPhone?: string;
  residentialPhone?: string;
  emailAddress?: string;
  profession?: string;
  companyName?: string;
  firstLicenseplate?: string;
  secondLicenseplate?: string;
  residenceStatus?: string;
  dutyStatus?: string;
  prevChurch?: string;
  instructor?: string;
  familyMembers?: IFamilyMember[];
  baptisms?: IBaptism[];
  volunteers?: IVolunteer[];
}

export const defaultValue: Readonly<IRegisterMember> = {};
