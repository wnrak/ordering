import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './articles-option-groups.reducer';
import { IArticlesOptionGroups } from 'app/shared/model/articles-option-groups.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArticlesOptionGroupsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesOptionGroupsDetail = (props: IArticlesOptionGroupsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { articlesOptionGroupsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="yoplaApp.articlesOptionGroups.detail.title">ArticlesOptionGroups</Translate> [
          <b>{articlesOptionGroupsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="optionName">
              <Translate contentKey="yoplaApp.articlesOptionGroups.optionName">Option Name</Translate>
            </span>
          </dt>
          <dd>{articlesOptionGroupsEntity.optionName}</dd>
          <dt>
            <span id="activeOptionName">
              <Translate contentKey="yoplaApp.articlesOptionGroups.activeOptionName">Active Option Name</Translate>
            </span>
          </dt>
          <dd>{articlesOptionGroupsEntity.activeOptionName}</dd>
          <dt>
            <span id="minValue">
              <Translate contentKey="yoplaApp.articlesOptionGroups.minValue">Min Value</Translate>
            </span>
          </dt>
          <dd>{articlesOptionGroupsEntity.minValue}</dd>
          <dt>
            <span id="maxValue">
              <Translate contentKey="yoplaApp.articlesOptionGroups.maxValue">Max Value</Translate>
            </span>
          </dt>
          <dd>{articlesOptionGroupsEntity.maxValue}</dd>
        </dl>
        <Button tag={Link} to="/articles-option-groups" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/articles-option-groups/${articlesOptionGroupsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ articlesOptionGroups }: IRootState) => ({
  articlesOptionGroupsEntity: articlesOptionGroups.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesOptionGroupsDetail);
