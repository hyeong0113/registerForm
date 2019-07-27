import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './family-member.reducer';
import { IFamilyMember } from 'app/shared/model/family-member.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFamilyMemberDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export class FamilyMemberDetail extends React.Component<IFamilyMemberDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { familyMemberEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="registerFormApp.familyMember.detail.title">FamilyMember</Translate> [<b>{familyMemberEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="registerFormApp.familyMember.registerMember">Register Member</Translate>
            </dt>
            <dd>{familyMemberEntity.registerMember ? familyMemberEntity.registerMember.korName : ''}</dd>
            <dt>
              <span id="relationStatus">
                <Translate contentKey="registerFormApp.familyMember.relationStatus">Relation Status</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.relationStatus}</dd>
            <dt>
              <span id="previousRegister">
                <Translate contentKey="registerFormApp.familyMember.previousRegister">Previous Register</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.previousRegister}</dd>
            <dt>
              <span id="korName">
                <Translate contentKey="registerFormApp.familyMember.korName">Kor Name</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.korName}</dd>
            <dt>
              <span id="engName">
                <Translate contentKey="registerFormApp.familyMember.engName">Eng Name</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.engName}</dd>
            <dt>
              <span id="birthday">
                <Translate contentKey="registerFormApp.familyMember.birthday">Birthday</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={familyMemberEntity.birthday} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="gender">
                <Translate contentKey="registerFormApp.familyMember.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.gender}</dd>
            <dt>
              <span id="profession">
                <Translate contentKey="registerFormApp.familyMember.profession">Profession</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.profession}</dd>
            <dt>
              <span id="companyName">
                <Translate contentKey="registerFormApp.familyMember.companyName">Company Name</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.companyName}</dd>
            <dt>
              <span id="cellPhone">
                <Translate contentKey="registerFormApp.familyMember.cellPhone">Cell Phone</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.cellPhone}</dd>
            <dt>
              <span id="emailAddress">
                <Translate contentKey="registerFormApp.familyMember.emailAddress">Email Address</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.emailAddress}</dd>
            <dt>
              <span id="churchServed">
                <Translate contentKey="registerFormApp.familyMember.churchServed">Church Served</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.churchServed}</dd>
            <dt>
              <span id="yearServed">
                <Translate contentKey="registerFormApp.familyMember.yearServed">Year Served</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.yearServed}</dd>
            <dt>
              <span id="dutyStatus">
                <Translate contentKey="registerFormApp.familyMember.dutyStatus">Duty Status</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.dutyStatus}</dd>
            <dt>
              <span id="prevChurch">
                <Translate contentKey="registerFormApp.familyMember.prevChurch">Prev Church</Translate>
              </span>
            </dt>
            <dd>{familyMemberEntity.prevChurch}</dd>
            <dt>
              <Translate contentKey="registerFormApp.familyMember.volunteer">Volunteer</Translate>
            </dt>
            <dd>
              {familyMemberEntity.volunteers
                ? familyMemberEntity.volunteers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.volunteerType}</a>
                    {i === familyMemberEntity.volunteers.length - 1 ? '' : ', '}
                  </span>
                ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/family-member" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/family-member/${familyMemberEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ familyMember }: IRootState) => ({
  familyMemberEntity: familyMember.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FamilyMemberDetail);
