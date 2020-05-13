import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticlesOptionGroups from './articles-option-groups';
import ArticlesOptionGroupsDetail from './articles-option-groups-detail';
import ArticlesOptionGroupsUpdate from './articles-option-groups-update';
import ArticlesOptionGroupsDeleteDialog from './articles-option-groups-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticlesOptionGroupsDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticlesOptionGroupsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticlesOptionGroupsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticlesOptionGroupsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticlesOptionGroups} />
    </Switch>
  </>
);

export default Routes;
