import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FamilyMember from './family-member';
import FamilyMemberDetail from './family-member-detail';
import FamilyMemberUpdate from './family-member-update';
import FamilyMemberDeleteDialog from './family-member-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FamilyMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FamilyMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FamilyMemberDetail} />
      <ErrorBoundaryRoute path={match.url} component={FamilyMember} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FamilyMemberDeleteDialog} />
  </>
);

export default Routes;
