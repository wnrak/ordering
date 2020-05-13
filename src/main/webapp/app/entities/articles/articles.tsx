import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './articles.reducer';
import { IArticles } from 'app/shared/model/articles.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Articles = (props: IArticlesProps) => {
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

  const { articlesList, match, loading } = props;
  return (
    <div>
      <h2 id="articles-heading">
        <Translate contentKey="yoplaApp.articles.home.title">Articles</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="yoplaApp.articles.home.createLabel">Create new Articles</Translate>
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
                  placeholder={translate('yoplaApp.articles.home.search')}
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
        {articlesList && articlesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.articleName">Article Name</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.taxRateIfPickUp">Tax Rate If Pick Up</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.taxRateIfDineIn">Tax Rate If Dine In</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.information">Information</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.ingredient">Ingredient</Translate>
                </th>
                <th>
                  <Translate contentKey="yoplaApp.articles.image">Image</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {articlesList.map((articles, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${articles.id}`} color="link" size="sm">
                      {articles.id}
                    </Button>
                  </td>
                  <td>{articles.articleName}</td>
                  <td>{articles.price}</td>
                  <td>{articles.taxRateIfPickUp}</td>
                  <td>{articles.taxRateIfDineIn}</td>
                  <td>{articles.information}</td>
                  <td>{articles.ingredient}</td>
                  <td>{articles.image}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${articles.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${articles.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${articles.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="yoplaApp.articles.home.notFound">No Articles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ articles }: IRootState) => ({
  articlesList: articles.entities,
  loading: articles.loading
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Articles);
