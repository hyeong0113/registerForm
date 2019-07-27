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
import { getEntity, updateEntity, createEntity, reset } from './baptism.reducer';
import { IBaptism } from 'app/shared/model/baptism.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBaptismUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBaptismUpdateState {
  isNew: boolean;
  registerMemberId: string;
}

export class BaptismUpdate extends React.Component<IBaptismUpdateProps, IBaptismUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRegisterMembers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { baptismEntity } = this.props;
      const entity = {
        ...baptismEntity,
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
    this.props.history.push('/entity/baptism');
  };

  render() {
    const { baptismEntity, registerMembers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="registerFormApp.baptism.home.createOrEditLabel">
              <Translate contentKey="registerFormApp.baptism.home.createOrEditLabel">Create or edit a Baptism</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : baptismEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="baptism-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="baptism-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="baptismTypeLabel" for="baptism-baptismType">
                    <Translate contentKey="registerFormApp.baptism.baptismType">Baptism Type</Translate>
                  </Label>
                  <AvField id="baptism-baptismType" type="text" name="baptismType" />
                </AvGroup>
                <AvGroup>
                  <Label id="baptismYearLabel" for="baptism-baptismYear">
                    <Translate contentKey="registerFormApp.baptism.baptismYear">Baptism Year</Translate>
                  </Label>
                  <AvField id="baptism-baptismYear" type="text" name="baptismYear" />
                </AvGroup>
                <AvGroup>
                  <Label id="baptismChurchLabel" for="baptism-baptismChurch">
                    <Translate contentKey="registerFormApp.baptism.baptismChurch">Baptism Church</Translate>
                  </Label>
                  <AvField id="baptism-baptismChurch" type="text" name="baptismChurch" />
                </AvGroup>
                <AvGroup>
                  <Label for="baptism-registerMember">
                    <Translate contentKey="registerFormApp.baptism.registerMember">Register Member</Translate>
                  </Label>
                  <AvInput id="baptism-registerMember" type="select" className="form-control" name="registerMember.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/baptism" replace color="info">
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
  baptismEntity: storeState.baptism.entity,
  loading: storeState.baptism.loading,
  updating: storeState.baptism.updating,
  updateSuccess: storeState.baptism.updateSuccess
});

const mapDispatchToProps = {
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
)(BaptismUpdate);
