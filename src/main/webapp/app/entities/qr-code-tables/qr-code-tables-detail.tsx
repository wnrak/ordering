import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './qr-code-tables.reducer';
import { IQrCodeTables } from 'app/shared/model/qr-code-tables.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQrCodeTablesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QrCodeTablesDetail = (props: IQrCodeTablesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { qrCodeTablesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.qrCodeTables.detail.title">QrCodeTables</Translate> [<b>{qrCodeTablesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tableNumber">
              <Translate contentKey="yoplaApp.qrCodeTables.tableNumber">Table Number</Translate>
            </span>
          </dt>
          <dd>{qrCodeTablesEntity.tableNumber}</dd>
          <dt>
            <span id="qrCode">
              <Translate contentKey="yoplaApp.qrCodeTables.qrCode">Qr Code</Translate>
            </span>
          </dt>
          <dd>{qrCodeTablesEntity.qrCode}</dd>
        </dl>
        <Button tag={Link} to="/qr-code-tables" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qr-code-tables/${qrCodeTablesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ qrCodeTables }: IRootState) => ({
  qrCodeTablesEntity: qrCodeTables.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QrCodeTablesDetail);
