import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Articles from './articles';
import ArticlesDetail from './articles-detail';
import ArticlesUpdate from './articles-update';
import ArticlesDeleteDialog from './articles-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticlesDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticlesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Articles} />
    </Switch>
  </>
);

export default Routes;
