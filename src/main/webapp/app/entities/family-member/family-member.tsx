import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './family-member.reducer';
import { IFamilyMember } from 'app/shared/model/family-member.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFamilyMemberProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export type IFamilyMemberState = IPaginationBaseState;

export class FamilyMember extends React.Component<IFamilyMemberProps, IFamilyMemberState> {
  state: IFamilyMemberState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { familyMemberList, match } = this.props;
    return (
      <div>
        <h2 id="family-member-heading">
          <Translate contentKey="registerFormApp.familyMember.home.title">Family Members</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="registerFormApp.familyMember.home.createLabel">Create new Family Member</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {familyMemberList && familyMemberList.length > 0 ? (
              <Table responsive>
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="registerFormApp.familyMember.registerMember">Register Member</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('relationStatus')}>
                      <Translate contentKey="registerFormApp.familyMember.relationStatus">Relation Status</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('previousRegister')}>
                      <Translate contentKey="registerFormApp.familyMember.previousRegister">Previous Register</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('korName')}>
                      <Translate contentKey="registerFormApp.familyMember.korName">Kor Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('engName')}>
                      <Translate contentKey="registerFormApp.familyMember.engName">Eng Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('birthday')}>
                      <Translate contentKey="registerFormApp.familyMember.birthday">Birthday</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('gender')}>
                      <Translate contentKey="registerFormApp.familyMember.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('profession')}>
                      <Translate contentKey="registerFormApp.familyMember.profession">Profession</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('companyName')}>
                      <Translate contentKey="registerFormApp.familyMember.companyName">Company Name</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('cellPhone')}>
                      <Translate contentKey="registerFormApp.familyMember.cellPhone">Cell Phone</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('emailAddress')}>
                      <Translate contentKey="registerFormApp.familyMember.emailAddress">Email Address</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('churchServed')}>
                      <Translate contentKey="registerFormApp.familyMember.churchServed">Church Served</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('yearServed')}>
                      <Translate contentKey="registerFormApp.familyMember.yearServed">Year Served</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('dutyStatus')}>
                      <Translate contentKey="registerFormApp.familyMember.dutyStatus">Duty Status</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('prevChurch')}>
                      <Translate contentKey="registerFormApp.familyMember.prevChurch">Prev Church</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {familyMemberList.map((familyMember, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${familyMember.id}`} color="link" size="sm">
                          {familyMember.id}
                        </Button>
                      </td>
                      <td>
                        {familyMember.registerMember ? (
                          <Link to={`register-member/${familyMember.registerMember.id}`}>{familyMember.registerMember.korName}</Link>
                        ) : (
                            ''
                          )}
                      </td>
                      <td>{familyMember.relationStatus}</td>
                      <td>{familyMember.previousRegister}</td>
                      <td>{familyMember.korName}</td>
                      <td>{familyMember.engName}</td>
                      <td>
                        <TextFormat type="date" value={familyMember.birthday} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>{familyMember.gender}</td>
                      <td>{familyMember.profession}</td>
                      <td>{familyMember.companyName}</td>
                      <td>{familyMember.cellPhone}</td>
                      <td>{familyMember.emailAddress}</td>
                      <td>{familyMember.churchServed}</td>
                      <td>{familyMember.yearServed}</td>
                      <td>{familyMember.dutyStatus}</td>
                      <td>{familyMember.prevChurch}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${familyMember.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${familyMember.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${familyMember.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.delete">Delete</Translate>
                            </span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
                <div className="alert alert-warning">
                  <Translate contentKey="registerFormApp.familyMember.home.notFound">No Family Members found</Translate>
                </div>
              )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ familyMember }: IRootState) => ({
  familyMemberList: familyMember.entities,
  totalItems: familyMember.totalItems,
  links: familyMember.links,
  entity: familyMember.entity,
  updateSuccess: familyMember.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FamilyMember);
