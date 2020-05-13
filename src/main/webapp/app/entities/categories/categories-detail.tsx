import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './categories.reducer';
import { ICategories } from 'app/shared/model/categories.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategoriesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CategoriesDetail = (props: ICategoriesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { categoriesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.categories.detail.title">Categories</Translate> [<b>{categoriesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="categoryName">
              <Translate contentKey="yoplaApp.categories.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.categoryName}</dd>
        </dl>
        <Button tag={Link} to="/categories" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categories/${categoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ categories }: IRootState) => ({
  categoriesEntity: categories.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CategoriesDetail);
