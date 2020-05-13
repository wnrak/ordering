import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './restaurant.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRestaurantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Restaurant = (props: IRestaurantProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { restaurantList, match, loading } = props;
  return (
    <div>
      <h2 id="restaurant-heading">
        <Translate contentKey="yoplaApp.restaurant.home.title">Restaurants</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="yoplaApp.restaurant.home.createLabel">Create new Restaurant</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('yoplaApp.restaurant.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {restaurantList && restaurantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.restaurantName">Restaurant Name</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.banner">Banner</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.logo">Logo</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.numberOfTables">Number Of Tables</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.availability">Availability</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.apiKey">Api Key</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.payLater">Pay Later</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.askForService">Ask For Service</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.enableSms">Enable Sms</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.restaurant.slug">Slug</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {restaurantList.map((restaurant, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${restaurant.id}`} color="link" size="sm">
                      {restaurant.id}
                    </Button>
                  </td>
                  <td>{restaurant.restaurantName}</td>
                  <td>{restaurant.location}</td>
                  <td>{restaurant.banner}</td>
                  <td>{restaurant.logo}</td>
                  <td>{restaurant.numberOfTables}</td>
                  <td>{restaurant.availability ? 'true' : 'false'}</td>
                  <td>{restaurant.apiKey}</td>
                  <td>{restaurant.payLater ? 'true' : 'false'}</td>
                  <td>{restaurant.askForService ? 'true' : 'false'}</td>
                  <td>{restaurant.enableSms ? 'true' : 'false'}</td>
                  <td>{restaurant.slug}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${restaurant.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${restaurant.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${restaurant.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="yoplaApp.restaurant.home.notFound">No Restaurants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ restaurant }: IRootState) => ({
  restaurantList: restaurant.entities,
  loading: restaurant.loading
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Restaurant);
