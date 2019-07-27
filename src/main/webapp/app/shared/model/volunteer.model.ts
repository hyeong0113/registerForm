import { IRegisterMember } from 'app/shared/model/register-member.model';
import { IFamilyMember } from 'app/shared/model/family-member.model';

export interface IVolunteer {
  id?: number;
  volunteerType?: string;
  registerMembers?: IRegisterMember[];
  familyMembers?: IFamilyMember[];
}

export const defaultValue: Readonly<IVolunteer> = {};
