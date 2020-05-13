import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './articles.reducer';
import { IArticles } from 'app/shared/model/articles.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesDetail = (props: IArticlesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articlesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.articles.detail.title">Articles</Translate> [<b>{articlesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="articleName">
              <Translate contentKey="yoplaApp.articles.articleName">Article Name</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.articleName}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="yoplaApp.articles.price">Price</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.price}</dd>
          <dt>
            <span id="taxRateIfPickUp">
              <Translate contentKey="yoplaApp.articles.taxRateIfPickUp">Tax Rate If Pick Up</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.taxRateIfPickUp}</dd>
          <dt>
            <span id="taxRateIfDineIn">
              <Translate contentKey="yoplaApp.articles.taxRateIfDineIn">Tax Rate If Dine In</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.taxRateIfDineIn}</dd>
          <dt>
            <span id="information">
              <Translate contentKey="yoplaApp.articles.information">Information</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.information}</dd>
          <dt>
            <span id="ingredient">
              <Translate contentKey="yoplaApp.articles.ingredient">Ingredient</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.ingredient}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="yoplaApp.articles.image">Image</Translate>
            </span>
          </dt>
          <dd>{articlesEntity.image}</dd>
        </dl>
        <Button tag={Link} to="/articles" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/articles/${articlesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articles }: IRootState) => ({
  articlesEntity: articles.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesDetail);
