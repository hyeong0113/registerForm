import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './baptism.reducer';
import { IBaptism } from 'app/shared/model/baptism.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaptismDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BaptismDetail extends React.Component<IBaptismDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { baptismEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="registerFormApp.baptism.detail.title">Baptism</Translate> [<b>{baptismEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="baptismType">
                <Translate contentKey="registerFormApp.baptism.baptismType">Baptism Type</Translate>
              </span>
            </dt>
            <dd>{baptismEntity.baptismType}</dd>
            <dt>
              <span id="baptismYear">
                <Translate contentKey="registerFormApp.baptism.baptismYear">Baptism Year</Translate>
              </span>
            </dt>
            <dd>{baptismEntity.baptismYear}</dd>
            <dt>
              <span id="baptismChurch">
                <Translate contentKey="registerFormApp.baptism.baptismChurch">Baptism Church</Translate>
              </span>
            </dt>
            <dd>{baptismEntity.baptismChurch}</dd>
            <dt>
              <Translate contentKey="registerFormApp.baptism.registerMember">Register Member</Translate>
            </dt>
            <dd>{baptismEntity.registerMember ? baptismEntity.registerMember.korName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/baptism" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/baptism/${baptismEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ baptism }: IRootState) => ({
  baptismEntity: baptism.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BaptismDetail);
