import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticlesOptions from './articles-options';
import ArticlesOptionsDetail from './articles-options-detail';
import ArticlesOptionsUpdate from './articles-options-update';
import ArticlesOptionsDeleteDialog from './articles-options-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticlesOptionsDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticlesOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticlesOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticlesOptionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticlesOptions} />
    </Switch>
  </>
);

export default Routes;
