import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './orders.reducer';
import { IOrders } from 'app/shared/model/orders.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrdersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrdersUpdate = (props: IOrdersUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { ordersEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/orders');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ordersEntity,
        ...values
      };
      entity.user = users[values.user];

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="yoplaApp.orders.home.createOrEditLabel">
            <Translate contentKey="yoplaApp.orders.home.createOrEditLabel">Create or edit a Orders</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ordersEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="orders-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="orders-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="orderNumberLabel" for="orders-orderNumber">
                  <Translate contentKey="yoplaApp.orders.orderNumber">Order Number</Translate>
                </Label>
                <AvField id="orders-orderNumber" type="string" className="form-control" name="orderNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="orderStatusLabel" for="orders-orderStatus">
                  <Translate contentKey="yoplaApp.orders.orderStatus">Order Status</Translate>
                </Label>
                <AvField id="orders-orderStatus" type="text" name="orderStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentStatusLabel" for="orders-paymentStatus">
                  <Translate contentKey="yoplaApp.orders.paymentStatus">Payment Status</Translate>
                </Label>
                <AvField id="orders-paymentStatus" type="text" name="paymentStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="tableNumberLabel" for="orders-tableNumber">
                  <Translate contentKey="yoplaApp.orders.tableNumber">Table Number</Translate>
                </Label>
                <AvField id="orders-tableNumber" type="text" name="tableNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="customerNameLabel" for="orders-customerName">
                  <Translate contentKey="yoplaApp.orders.customerName">Customer Name</Translate>
                </Label>
                <AvField id="orders-customerName" type="text" name="customerName" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="orders-email">
                  <Translate contentKey="yoplaApp.orders.email">Email</Translate>
                </Label>
                <AvField id="orders-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="orders-phoneNumber">
                  <Translate contentKey="yoplaApp.orders.phoneNumber">Phone Number</Translate>
                </Label>
                <AvField id="orders-phoneNumber" type="text" name="phoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="orders-address">
                  <Translate contentKey="yoplaApp.orders.address">Address</Translate>
                </Label>
                <AvField id="orders-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="totalAmountLabel" for="orders-totalAmount">
                  <Translate contentKey="yoplaApp.orders.totalAmount">Total Amount</Translate>
                </Label>
                <AvField id="orders-totalAmount" type="string" className="form-control" name="totalAmount" />
              </AvGroup>
              <AvGroup>
                <Label for="orders-user">
                  <Translate contentKey="yoplaApp.orders.user">User</Translate>
                </Label>
                <AvInput id="orders-user" type="select" className="form-control" name="user">
                  <option value="" key="0" />
                  {users
                    ? users.map((otherEntity, index) => (
                        <option value={index} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/orders" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  ordersEntity: storeState.orders.entity,
  loading: storeState.orders.loading,
  updating: storeState.orders.updating,
  updateSuccess: storeState.orders.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrdersUpdate);
