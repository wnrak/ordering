import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './articles-option-groups.reducer';
import { IArticlesOptionGroups } from 'app/shared/model/articles-option-groups.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlesOptionGroupsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ArticlesOptionGroups = (props: IArticlesOptionGroupsProps) => {
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

  const { articlesOptionGroupsList, match, loading } = props;
  return (
    <div>
      <h2 id="articles-option-groups-heading">
        <Translate contentKey="yoplaApp.articlesOptionGroups.home.title">Articles Option Groups</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="yoplaApp.articlesOptionGroups.home.createLabel">Create new Articles Option Groups</Translate>
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
                  placeholder={translate('yoplaApp.articlesOptionGroups.home.search')}
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
        {articlesOptionGroupsList && articlesOptionGroupsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articlesOptionGroups.optionName">Option Name</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articlesOptionGroups.activeOptionName">Active Option Name</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articlesOptionGroups.minValue">Min Value</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articlesOptionGroups.maxValue">Max Value</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {articlesOptionGroupsList.map((articlesOptionGroups, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${articlesOptionGroups.id}`} color="link" size="sm">
                      {articlesOptionGroups.id}
                    </Button>
                  </td>
                  <td>{articlesOptionGroups.optionName}</td>
                  <td>{articlesOptionGroups.activeOptionName}</td>
                  <td>{articlesOptionGroups.minValue}</td>
                  <td>{articlesOptionGroups.maxValue}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${articlesOptionGroups.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${articlesOptionGroups.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${articlesOptionGroups.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="yoplaApp.articlesOptionGroups.home.notFound">No Articles Option Groups found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ articlesOptionGroups }: IRootState) => ({
  articlesOptionGroupsList: articlesOptionGroups.entities,
  loading: articlesOptionGroups.loading
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesOptionGroups);
