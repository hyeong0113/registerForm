import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RegisterMember from './register-member';
import RegisterMemberDetail from './register-member-detail';
import RegisterMemberUpdate from './register-member-update';
import RegisterMemberDeleteDialog from './register-member-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegisterMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegisterMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegisterMemberDetail} />
      <ErrorBoundaryRoute path={match.url} component={RegisterMember} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RegisterMemberDeleteDialog} />
  </>
);

export default Routes;
