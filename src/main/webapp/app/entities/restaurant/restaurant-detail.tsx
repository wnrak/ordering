import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './restaurant.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRestaurantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RestaurantDetail = (props: IRestaurantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { restaurantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.restaurant.detail.title">Restaurant</Translate> [<b>{restaurantEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="restaurantName">
              <Translate contentKey="yoplaApp.restaurant.restaurantName">Restaurant Name</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.restaurantName}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="yoplaApp.restaurant.location">Location</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.location}</dd>
          <dt>
            <span id="banner">
              <Translate contentKey="yoplaApp.restaurant.banner">Banner</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.banner}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="yoplaApp.restaurant.logo">Logo</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.logo}</dd>
          <dt>
            <span id="numberOfTables">
              <Translate contentKey="yoplaApp.restaurant.numberOfTables">Number Of Tables</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.numberOfTables}</dd>
          <dt>
            <span id="availability">
              <Translate contentKey="yoplaApp.restaurant.availability">Availability</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.availability ? 'true' : 'false'}</dd>
          <dt>
            <span id="apiKey">
              <Translate contentKey="yoplaApp.restaurant.apiKey">Api Key</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.apiKey}</dd>
          <dt>
            <span id="payLater">
              <Translate contentKey="yoplaApp.restaurant.payLater">Pay Later</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.payLater ? 'true' : 'false'}</dd>
          <dt>
            <span id="askForService">
              <Translate contentKey="yoplaApp.restaurant.askForService">Ask For Service</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.askForService ? 'true' : 'false'}</dd>
          <dt>
            <span id="enableSms">
              <Translate contentKey="yoplaApp.restaurant.enableSms">Enable Sms</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.enableSms ? 'true' : 'false'}</dd>
          <dt>
            <span id="slug">
              <Translate contentKey="yoplaApp.restaurant.slug">Slug</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.slug}</dd>
        </dl>
        <Button tag={Link} to="/restaurant" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/restaurant/${restaurantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ restaurant }: IRootState) => ({
  restaurantEntity: restaurant.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RestaurantDetail);
