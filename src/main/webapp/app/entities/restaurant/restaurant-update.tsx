import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './restaurant.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRestaurantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RestaurantUpdate = (props: IRestaurantUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { restaurantEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/restaurant');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...restaurantEntity,
        ...values
      };

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
          <h2 id="yoplaApp.restaurant.home.createOrEditLabel">
            <Translate contentKey="yoplaApp.restaurant.home.createOrEditLabel">Create or edit a Restaurant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : restaurantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="restaurant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="restaurant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="restaurantNameLabel" for="restaurant-restaurantName">
                  <Translate contentKey="yoplaApp.restaurant.restaurantName">Restaurant Name</Translate>
                </Label>
                <AvField
                  id="restaurant-restaurantName"
                  type="text"
                  name="restaurantName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="locationLabel" for="restaurant-location">
                  <Translate contentKey="yoplaApp.restaurant.location">Location</Translate>
                </Label>
                <AvField
                  id="restaurant-location"
                  type="text"
                  name="location"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="bannerLabel" for="restaurant-banner">
                  <Translate contentKey="yoplaApp.restaurant.banner">Banner</Translate>
                </Label>
                <AvField
                  id="restaurant-banner"
                  type="text"
                  name="banner"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="logoLabel" for="restaurant-logo">
                  <Translate contentKey="yoplaApp.restaurant.logo">Logo</Translate>
                </Label>
                <AvField
                  id="restaurant-logo"
                  type="text"
                  name="logo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numberOfTablesLabel" for="restaurant-numberOfTables">
                  <Translate contentKey="yoplaApp.restaurant.numberOfTables">Number Of Tables</Translate>
                </Label>
                <AvField
                  id="restaurant-numberOfTables"
                  type="string"
                  className="form-control"
                  name="numberOfTables"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') }
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="availabilityLabel">
                  <AvInput id="restaurant-availability" type="checkbox" className="form-check-input" name="availability" />
                  <Translate contentKey="yoplaApp.restaurant.availability">Availability</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="apiKeyLabel" for="restaurant-apiKey">
                  <Translate contentKey="yoplaApp.restaurant.apiKey">Api Key</Translate>
                </Label>
                <AvField
                  id="restaurant-apiKey"
                  type="text"
                  name="apiKey"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="payLaterLabel">
                  <AvInput id="restaurant-payLater" type="checkbox" className="form-check-input" name="payLater" />
                  <Translate contentKey="yoplaApp.restaurant.payLater">Pay Later</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="askForServiceLabel">
                  <AvInput id="restaurant-askForService" type="checkbox" className="form-check-input" name="askForService" />
                  <Translate contentKey="yoplaApp.restaurant.askForService">Ask For Service</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="enableSmsLabel">
                  <AvInput id="restaurant-enableSms" type="checkbox" className="form-check-input" name="enableSms" />
                  <Translate contentKey="yoplaApp.restaurant.enableSms">Enable Sms</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="slugLabel" for="restaurant-slug">
                  <Translate contentKey="yoplaApp.restaurant.slug">Slug</Translate>
                </Label>
                <AvField
                  id="restaurant-slug"
                  type="text"
                  name="slug"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/restaurant" replace color="info">
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
  restaurantEntity: storeState.restaurant.entity,
  loading: storeState.restaurant.loading,
  updating: storeState.restaurant.updating,
  updateSuccess: storeState.restaurant.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RestaurantUpdate);
