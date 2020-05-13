import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './articles-options.reducer';
import { IArticlesOptions } from 'app/shared/model/articles-options.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlesOptionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesOptionsDetail = (props: IArticlesOptionsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articlesOptionsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.articlesOptions.detail.title">ArticlesOptions</Translate> [<b>{articlesOptionsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="yoplaApp.articlesOptions.name">Name</Translate>
            </span>
          </dt>
          <dd>{articlesOptionsEntity.name}</dd>
          <dt>
            <span id="choice">
              <Translate contentKey="yoplaApp.articlesOptions.choice">Choice</Translate>
            </span>
          </dt>
          <dd>{articlesOptionsEntity.choice}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="yoplaApp.articlesOptions.price">Price</Translate>
            </span>
          </dt>
          <dd>{articlesOptionsEntity.price}</dd>
        </dl>
        <Button tag={Link} to="/articles-options" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/articles-options/${articlesOptionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articlesOptions }: IRootState) => ({
  articlesOptionsEntity: articlesOptions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesOptionsDetail);
