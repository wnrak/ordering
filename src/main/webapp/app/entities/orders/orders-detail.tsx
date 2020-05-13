import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './orders.reducer';
import { IOrders } from 'app/shared/model/orders.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrdersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrdersDetail = (props: IOrdersDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ordersEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.orders.detail.title">Orders</Translate> [<b>{ordersEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="orderNumber">
              <Translate contentKey="yoplaApp.orders.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.orderNumber}</dd>
          <dt>
            <span id="orderStatus">
              <Translate contentKey="yoplaApp.orders.orderStatus">Order Status</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.orderStatus}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="yoplaApp.orders.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.paymentStatus}</dd>
          <dt>
            <span id="tableNumber">
              <Translate contentKey="yoplaApp.orders.tableNumber">Table Number</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.tableNumber}</dd>
          <dt>
            <span id="customerName">
              <Translate contentKey="yoplaApp.orders.customerName">Customer Name</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.customerName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="yoplaApp.orders.email">Email</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="yoplaApp.orders.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.phoneNumber}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="yoplaApp.orders.address">Address</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.address}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="yoplaApp.orders.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{ordersEntity.totalAmount}</dd>
          <dt>
            <Translate contentKey="yoplaApp.orders.user">User</Translate>
          </dt>
          <dd>{ordersEntity.user ? ordersEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/orders" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/orders/${ordersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orders }: IRootState) => ({
  ordersEntity: orders.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrdersDetail);
