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
import { getEntity, updateEntity, createEntity, reset } from './register-member.reducer';
import { IRegisterMember } from 'app/shared/model/register-member.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRegisterMemberUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export interface IRegisterMemberUpdateState {
  isNew: boolean;
  idsvolunteer: any[];
}

export class RegisterMemberUpdate extends React.Component<IRegisterMemberUpdateProps, IRegisterMemberUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsvolunteer: [],
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { registerMemberEntity } = this.props;
      const entity = {
        ...registerMemberEntity,
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
    this.props.history.push('/entity/register-member');
  };

  render() {
    const { registerMemberEntity, volunteers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="registerFormApp.registerMember.home.createOrEditLabel">
              <Translate contentKey="registerFormApp.registerMember.home.createOrEditLabel">Create or edit a RegisterMember</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
                <AvForm model={isNew ? {} : registerMemberEntity} onSubmit={this.saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="register-member-id">
                        <Translate contentKey="global.field.id">ID</Translate>
                      </Label>
                      <AvInput id="register-member-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null}
                  {/* <Col xs="12" sm="8"> */}
                  <AvGroup>
                    <Label id="previousRegisterLabel" for="register-member-previousRegister">
                      <Translate contentKey="registerFormApp.registerMember.previousRegister">Previous Register</Translate>
                    </Label>
                    <AvRadioGroup inline id="register-member-previousRegister" name="previousRegister" required>
                      <AvRadio label="Yes" value="Yes" />
                      <AvRadio label="No" value="No" />
                    </AvRadioGroup>
                  </AvGroup>
                  {/* </Col> */}
                  <Row>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="korNameLabel" for="register-member-korName">
                          <Translate contentKey="registerFormApp.registerMember.korName">Kor Name</Translate>
                        </Label>
                        <AvField
                          id="register-member-korName"
                          type="text"
                          name="korName"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="engNameLabel" for="register-member-engName">
                          <Translate contentKey="registerFormApp.registerMember.engName">Eng Name</Translate>
                        </Label>
                        <AvField
                          id="register-member-engName"
                          type="text"
                          name="engName"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="birthdayLabel" for="register-member-birthday">
                          <Translate contentKey="registerFormApp.registerMember.birthday">Birthday</Translate>
                        </Label>
                        <AvField
                          id="register-member-birthday"
                          type="date"
                          className="form-control"
                          name="birthday"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="2">
                      <AvGroup>
                        <Label id="genderLabel" for="register-member-gender">
                          <Translate contentKey="registerFormApp.registerMember.gender">Gender</Translate>
                        </Label>
                        <AvField id="register-member-gender" type="select" name="gender" required>
                          <option>--Select--</option>
                          <option>Male</option>
                          <option>Female</option>
                          <option>Other</option>
                        </AvField>
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="6">
                      <AvGroup>
                        <Label id="firstStreetLabel" for="register-member-firstStreet">
                          <Translate contentKey="registerFormApp.registerMember.firstStreet">First Street</Translate>
                        </Label>
                        <AvField
                          id="register-member-firstStreet"
                          type="text"
                          name="firstStreet"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="6">
                      <AvGroup>
                        <Label id="secondStreetLabel" for="register-member-secondStreet">
                          <Translate contentKey="registerFormApp.registerMember.secondStreet">Second Street</Translate>
                        </Label>
                        <AvField id="register-member-secondStreet" type="text" name="secondStreet" />
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="cityLabel" for="register-member-city">
                          <Translate contentKey="registerFormApp.registerMember.city">City</Translate>
                        </Label>
                        <AvField
                          id="register-member-city"
                          type="text"
                          name="city"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="provinceLabel" for="register-member-province">
                          <Translate contentKey="registerFormApp.registerMember.province">Province</Translate>
                        </Label>
                        <AvField id="register-member-province" type="select" name="province" required>
                          <option>--Select--</option>
                          <option>Alberta</option>
                          <option>British Columbia</option>
                          <option>Manitoba</option>
                          <option>New Brunswick</option>
                          <option>Newfoundland and Labrador</option>
                          <option>Nova Scotia</option>
                          <option>Northwest Territories</option>
                          <option>Nunavut</option>
                          <option>Ontario</option>
                          <option>Prince Edward Island</option>
                          <option>Quebec</option>
                          <option>Saskatchewan</option>
                          <option>Yukon</option>
                        </AvField>
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="2">
                      <AvGroup>
                        <Label id="postalCodeLabel" for="register-member-postalCode">
                          <Translate contentKey="registerFormApp.registerMember.postalCode">Postal Code</Translate>
                        </Label>
                        <AvField
                          id="register-member-postalCode"
                          type="text"
                          name="postalCode"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="4">
                      <AvGroup>
                        <Label id="cellPhoneLabel" for="register-member-cellPhone">
                          <Translate contentKey="registerFormApp.registerMember.cellPhone">Cell Phone</Translate>
                        </Label>
                        <AvField
                          id="register-member-cellPhone"
                          type="text"
                          name="cellPhone"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="4">
                      <AvGroup>
                        <Label id="residentialPhoneLabel" for="register-member-residentialPhone">
                          <Translate contentKey="registerFormApp.registerMember.residentialPhone">Residential Phone</Translate>
                        </Label>
                        <AvField id="register-member-residentialPhone" type="text" name="residentialPhone" />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="4">
                      <AvGroup>
                        <Label id="emailAddressLabel" for="register-member-emailAddress">
                          <Translate contentKey="registerFormApp.registerMember.emailAddress">Email Address</Translate>
                        </Label>
                        <AvField
                          id="register-member-emailAddress"
                          type="text"
                          name="emailAddress"
                          validate={{
                            required: { value: true, errorMessage: translate('entity.validation.required') }
                          }}
                        />
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="5">
                      <AvGroup>
                        <Label id="professionLabel" for="register-member-profession">
                          <Translate contentKey="registerFormApp.registerMember.profession">Profession</Translate>
                        </Label>
                        <AvField id="register-member-profession" type="text" name="profession" />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="5">
                      <AvGroup>
                        <Label id="companyNameLabel" for="register-member-companyName">
                          <Translate contentKey="registerFormApp.registerMember.companyName">Company Name</Translate>
                        </Label>
                        <AvField id="register-member-companyName" type="text" name="companyName" />
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="4">
                      <AvGroup>
                        <Label id="firstLicenseplateLabel" for="register-member-firstLicenseplate">
                          <Translate contentKey="registerFormApp.registerMember.firstLicenseplate">First Licenseplate</Translate>
                        </Label>
                        <AvField id="register-member-firstLicenseplate" type="text" name="firstLicenseplate" />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="4">
                      <AvGroup>
                        <Label id="secondLicenseplateLabel" for="register-member-secondLicenseplate">
                          <Translate contentKey="registerFormApp.registerMember.secondLicenseplate">Second Licenseplate</Translate>
                        </Label>
                        <AvField id="register-member-secondLicenseplate" type="text" name="secondLicenseplate" />
                      </AvGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="residenceStatusLabel" for="register-member-residenceStatus">
                          <Translate contentKey="registerFormApp.registerMember.residenceStatus">Residence Status</Translate>
                        </Label>
                        <AvField id="register-member-residenceStatus" type="select" name="residenceStatus" required>
                          <option>--Select--</option>
                          <option>Citizen</option>
                          <option>Permanent Residence</option>
                          <option>Visitor</option>
                          <option>Study Permit</option>
                          <option>Work Permit</option>
                          <option>Working Holiday</option>
                        </AvField>
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="dutyStatusLabel" for="register-member-dutyStatus">
                          <Translate contentKey="registerFormApp.registerMember.dutyStatus">Duty Status</Translate>
                        </Label>
                        <AvField id="register-member-dutyStatus" type="select" name="dutyStatus">
                          <option>--Select--</option>
                          <option>성도</option>
                          <option>서리집사</option>
                          <option>안수집사</option>
                          <option>권사</option>
                          <option>장로</option>
                          <option>전도사</option>
                          <option>목사</option>
                          <option>사모</option>
                          <option>선교사</option>
                        </AvField>
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="prevChurchLabel" for="register-member-prevChurch">
                          <Translate contentKey="registerFormApp.registerMember.prevChurch">Prev Church</Translate>
                        </Label>
                        <AvField id="register-member-prevChurch" type="text" name="prevChurch" />
                      </AvGroup>
                    </Col>
                    <Col xs="12" sm="3">
                      <AvGroup>
                        <Label id="instructorLabel" for="register-member-instructor">
                          <Translate contentKey="registerFormApp.registerMember.instructor">Instructor</Translate>
                        </Label>
                        <AvField id="register-member-instructor" type="text" name="instructor" />
                      </AvGroup>
                    </Col>
                  </Row>
                  <AvGroup>
                    <Label for="register-member-volunteer">
                      <Translate contentKey="registerFormApp.registerMember.volunteer">Volunteer</Translate>
                    </Label>
                    <AvInput
                      id="register-member-volunteer"
                      type="select"
                      multiple
                      className="form-control"
                      name="volunteers"
                      value={registerMemberEntity.volunteers && registerMemberEntity.volunteers.map(e => e.id)}
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
                  <Button tag={Link} id="cancel-save" to="/entity/register-member" replace color="info">
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
  registerMemberEntity: storeState.registerMember.entity,
  loading: storeState.registerMember.loading,
  updating: storeState.registerMember.updating,
  updateSuccess: storeState.registerMember.updateSuccess
});

const mapDispatchToProps = {
  getVolunteers,
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
)(RegisterMemberUpdate);
