import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField, AvRadio, AvRadioGroup } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVolunteer } from 'app/shared/model/volunteer.model';
import { getEntities as getVolunteers } from 'app/entities/volunteer/volunteer.reducer';
import { IRegisterMember } from 'app/shared/model/register-member.model';
import { getEntities as getRegisterMembers } from 'app/entities/register-member/register-member.reducer';
import { getEntity, updateEntity, createEntity, reset } from './family-member.reducer';
import { IFamilyMember } from 'app/shared/model/family-member.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFamilyMemberUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export interface IFamilyMemberUpdateState {
  isNew: boolean;
  idsvolunteer: any[];
  registerMemberId: string;
}

export class FamilyMemberUpdate extends React.Component<IFamilyMemberUpdateProps, IFamilyMemberUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsvolunteer: [],
      registerMemberId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVolunteers();
    this.props.getRegisterMembers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { familyMemberEntity } = this.props;
      const entity = {
        ...familyMemberEntity,
        ...values,
        volunteers: mapIdList(values.volunteers)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/family-member');
  };

  render() {
    const { familyMemberEntity, volunteers, registerMembers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="registerFormApp.familyMember.home.createOrEditLabel">
              <Translate contentKey="registerFormApp.familyMember.home.createOrEditLabel">Create or edit a FamilyMember</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
                <AvForm model={isNew ? {} : familyMemberEntity} onSubmit={this.saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="family-member-id">
                        <Translate contentKey="global.field.id">ID</Translate>
                      </Label>
                      <AvInput id="family-member-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label for="family-member-registerMember">
                      <Translate contentKey="registerFormApp.familyMember.registerMember">Register Member</Translate>
                    </Label>
                    <AvInput id="family-member-registerMember" type="select" className="form-control" name="registerMember.id" required>
                      <option value="" key="0" />
                      {registerMembers
                        ? registerMembers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.korName}
                          </option>
                        ))
                        : null}
                    </AvInput>
                  </AvGroup>
                  <AvGroup>
                    <Label id="relationStatusLabel" for="family-member-relationStatus">
                      <Translate contentKey="registerFormApp.familyMember.relationStatus">Relation Status</Translate>
                    </Label>
                    <AvField id="family-member-relationStatus" type="select" name="relationStatus" required>
                      <option>--Select--</option>
                      <option>Wife/Husband</option>
                      <option>Child</option>
                      <option>Parents</option>
                      <option>Sibling</option>
                      <option>Grandchild</option>
                      <option>Relatives</option>
                    </AvField>
                  </AvGroup>
                  <AvGroup>
                    <Label id="previousRegisterLabel" for="family-member-previousRegister">
                      <Translate contentKey="registerFormApp.familyMember.previousRegister">Previous Register</Translate>
                    </Label>
                    <AvRadioGroup inline id="family-member-previousRegister" name="previousRegister" required>
                      <AvRadio label="Yes" value="Yes" />
                      <AvRadio label="No" value="No" />
                    </AvRadioGroup>
                  </AvGroup>
                  <AvGroup>
                    <Label id="korNameLabel" for="family-member-korName">
                      <Translate contentKey="registerFormApp.familyMember.korName">Kor Name</Translate>
                    </Label>
                    <AvField
                      id="family-member-korName"
                      type="text"
                      name="korName"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="engNameLabel" for="family-member-engName">
                      <Translate contentKey="registerFormApp.familyMember.engName">Eng Name</Translate>
                    </Label>
                    <AvField
                      id="family-member-engName"
                      type="text"
                      name="engName"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="birthdayLabel" for="family-member-birthday">
                      <Translate contentKey="registerFormApp.familyMember.birthday">Birthday</Translate>
                    </Label>
                    <AvField
                      id="family-member-birthday"
                      type="date"
                      className="form-control"
                      name="birthday"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="genderLabel" for="family-member-gender">
                      <Translate contentKey="registerFormApp.familyMember.gender">Gender</Translate>
                    </Label>
                    <AvField id="family-member-gender" type="select" name="gender" required>
                      <option>--Select--</option>
                      <option>Male</option>
                      <option>Female</option>
                      <option>Other</option>
                    </AvField>
                  </AvGroup>
                  <AvGroup>
                    <Label id="professionLabel" for="family-member-profession">
                      <Translate contentKey="registerFormApp.familyMember.profession">Profession</Translate>
                    </Label>
                    <AvField id="family-member-profession" type="text" name="profession" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="companyNameLabel" for="family-member-companyName">
                      <Translate contentKey="registerFormApp.familyMember.companyName">Company Name</Translate>
                    </Label>
                    <AvField id="family-member-companyName" type="text" name="companyName" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="cellPhoneLabel" for="family-member-cellPhone">
                      <Translate contentKey="registerFormApp.familyMember.cellPhone">Cell Phone</Translate>
                    </Label>
                    <AvField
                      id="family-member-cellPhone"
                      type="text"
                      name="cellPhone"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="emailAddressLabel" for="family-member-emailAddress">
                      <Translate contentKey="registerFormApp.familyMember.emailAddress">Email Address</Translate>
                    </Label>
                    <AvField
                      id="family-member-emailAddress"
                      type="text"
                      name="emailAddress"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="churchServedLabel" for="family-member-churchServed">
                      <Translate contentKey="registerFormApp.familyMember.churchServed">Church Served</Translate>
                    </Label>
                    <AvField id="family-member-churchServed" type="text" name="churchServed" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="yearServedLabel" for="family-member-yearServed">
                      <Translate contentKey="registerFormApp.familyMember.yearServed">Year Served</Translate>
                    </Label>
                    <AvField id="family-member-yearServed" type="text" name="yearServed" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="dutyStatusLabel" for="family-member-dutyStatus">
                      <Translate contentKey="registerFormApp.familyMember.dutyStatus">Duty Status</Translate>
                    </Label>
                    <AvField id="family-member-dutyStatus" type="text" name="dutyStatus" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="prevChurchLabel" for="family-member-prevChurch">
                      <Translate contentKey="registerFormApp.familyMember.prevChurch">Prev Church</Translate>
                    </Label>
                    <AvField id="family-member-prevChurch" type="text" name="prevChurch" />
                  </AvGroup>
                  <AvGroup>
                    <Label for="family-member-volunteer">
                      <Translate contentKey="registerFormApp.familyMember.volunteer">Volunteer</Translate>
                    </Label>
                    <AvInput
                      id="family-member-volunteer"
                      type="select"
                      multiple
                      className="form-control"
                      name="volunteers"
                      value={familyMemberEntity.volunteers && familyMemberEntity.volunteers.map(e => e.id)}
                    >
                      <option value="" key="0" />
                      {volunteers
                        ? volunteers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.volunteerType}
                          </option>
                        ))
                        : null}
                    </AvInput>
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="/entity/family-member" replace color="info">
                    <FontAwesomeIcon icon="arrow-left" />
                    &nbsp;
                  <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.back">Back</Translate>
                    </span>
                  </Button>
                  &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                    <FontAwesomeIcon icon="save" />
                    &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                  </Button>
                </AvForm>
              )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  volunteers: storeState.volunteer.entities,
  registerMembers: storeState.registerMember.entities,
  familyMemberEntity: storeState.familyMember.entity,
  loading: storeState.familyMember.loading,
  updating: storeState.familyMember.updating,
  updateSuccess: storeState.familyMember.updateSuccess
});

const mapDispatchToProps = {
  getVolunteers,
  getRegisterMembers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FamilyMemberUpdate);
