import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RegisterMember from './register-member';
import Baptism from './baptism';
import FamilyMember from './family-member';
import Volunteer from './volunteer';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/register-member`} component={RegisterMember} />
      <ErrorBoundaryRoute path={`${match.url}/baptism`} component={Baptism} />
      <ErrorBoundaryRoute path={`${match.url}/family-member`} component={FamilyMember} />
      <ErrorBoundaryRoute path={`${match.url}/volunteer`} component={Volunteer} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
