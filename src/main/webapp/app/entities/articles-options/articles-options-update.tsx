import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './articles-options.reducer';
import { IArticlesOptions } from 'app/shared/model/articles-options.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticlesOptionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesOptionsUpdate = (props: IArticlesOptionsUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articlesOptionsEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/articles-options');
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
        ...articlesOptionsEntity,
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
          <h2 id="yoplaApp.articlesOptions.home.createOrEditLabel">
            <Translate contentKey="yoplaApp.articlesOptions.home.createOrEditLabel">Create or edit a ArticlesOptions</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articlesOptionsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="articles-options-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="articles-options-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="articles-options-name">
                  <Translate contentKey="yoplaApp.articlesOptions.name">Name</Translate>
                </Label>
                <AvField id="articles-options-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="choiceLabel" for="articles-options-choice">
                  <Translate contentKey="yoplaApp.articlesOptions.choice">Choice</Translate>
                </Label>
                <AvField id="articles-options-choice" type="text" name="choice" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="articles-options-price">
                  <Translate contentKey="yoplaApp.articlesOptions.price">Price</Translate>
                </Label>
                <AvField id="articles-options-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/articles-options" replace color="info">
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
  articlesOptionsEntity: storeState.articlesOptions.entity,
  loading: storeState.articlesOptions.loading,
  updating: storeState.articlesOptions.updating,
  updateSuccess: storeState.articlesOptions.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesOptionsUpdate);
