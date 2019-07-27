import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRegisterMember } from 'app/shared/model/register-member.model';
import { getEntities as getRegisterMembers } from 'app/entities/register-member/register-member.reducer';
import { IFamilyMember } from 'app/shared/model/family-member.model';
import { getEntities as getFamilyMembers } from 'app/entities/family-member/family-member.reducer';
import { getEntity, updateEntity, createEntity, reset } from './volunteer.reducer';
import { IVolunteer } from 'app/shared/model/volunteer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVolunteerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVolunteerUpdateState {
  isNew: boolean;
  registerMemberId: string;
  familyMemberId: string;
}

export class VolunteerUpdate extends React.Component<IVolunteerUpdateProps, IVolunteerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      registerMemberId: '0',
      familyMemberId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRegisterMembers();
    this.props.getFamilyMembers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { volunteerEntity } = this.props;
      const entity = {
        ...volunteerEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/volunteer');
  };

  render() {
    const { volunteerEntity, registerMembers, familyMembers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="registerFormApp.volunteer.home.createOrEditLabel">
              <Translate contentKey="registerFormApp.volunteer.home.createOrEditLabel">Create or edit a Volunteer</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : volunteerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="volunteer-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="volunteer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="volunteerTypeLabel" for="volunteer-volunteerType">
                    <Translate contentKey="registerFormApp.volunteer.volunteerType">Volunteer Type</Translate>
                  </Label>
                  <AvField id="volunteer-volunteerType" type="text" name="volunteerType" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/volunteer" replace color="info">
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
  registerMembers: storeState.registerMember.entities,
  familyMembers: storeState.familyMember.entities,
  volunteerEntity: storeState.volunteer.entity,
  loading: storeState.volunteer.loading,
  updating: storeState.volunteer.updating,
  updateSuccess: storeState.volunteer.updateSuccess
});

const mapDispatchToProps = {
  getRegisterMembers,
  getFamilyMembers,
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
)(VolunteerUpdate);
