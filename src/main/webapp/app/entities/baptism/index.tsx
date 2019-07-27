import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Baptism from './baptism';
import BaptismDetail from './baptism-detail';
import BaptismUpdate from './baptism-update';
import BaptismDeleteDialog from './baptism-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaptismUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaptismUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaptismDetail} />
      <ErrorBoundaryRoute path={match.url} component={Baptism} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BaptismDeleteDialog} />
  </>
);

export default Routes;
