import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import QrCodeTables from './qr-code-tables';
import QrCodeTablesDetail from './qr-code-tables-detail';
import QrCodeTablesUpdate from './qr-code-tables-update';
import QrCodeTablesDeleteDialog from './qr-code-tables-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QrCodeTablesDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QrCodeTablesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QrCodeTablesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QrCodeTablesDetail} />
      <ErrorBoundaryRoute path={match.url} component={QrCodeTables} />
    </Switch>
  </>
);

export default Routes;
