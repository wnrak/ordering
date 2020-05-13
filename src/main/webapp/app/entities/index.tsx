import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Restaurant from './restaurant';
import Categories from './categories';
import ArticlesOptionGroups from './articles-option-groups';
import Articles from './articles';
import ArticlesOptions from './articles-options';
import QrCodeTables from './qr-code-tables';
import Orders from './orders';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}restaurant`} component={Restaurant} />
      <ErrorBoundaryRoute path={`${match.url}categories`} component={Categories} />
      <ErrorBoundaryRoute path={`${match.url}articles-option-groups`} component={ArticlesOptionGroups} />
      <ErrorBoundaryRoute path={`${match.url}articles`} component={Articles} />
      <ErrorBoundaryRoute path={`${match.url}articles-options`} component={ArticlesOptions} />
      <ErrorBoundaryRoute path={`${match.url}qr-code-tables`} component={QrCodeTables} />
      <ErrorBoundaryRoute path={`${match.url}orders`} component={Orders} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
