import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './articles.reducer';
import { IArticles } from 'app/shared/model/articles.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticlesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesUpdate = (props: IArticlesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articlesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/articles');
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
        ...articlesEntity,
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
          <h2 id="yoplaApp.articles.home.createOrEditLabel">
            <Translate contentKey="yoplaApp.articles.home.createOrEditLabel">Create or edit a Articles</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articlesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="articles-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="articles-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="articleNameLabel" for="articles-articleName">
                  <Translate contentKey="yoplaApp.articles.articleName">Article Name</Translate>
                </Label>
                <AvField
                  id="articles-articleName"
                  type="text"
                  name="articleName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="articles-price">
                  <Translate contentKey="yoplaApp.articles.price">Price</Translate>
                </Label>
                <AvField
                  id="articles-price"
                  type="string"
                  className="form-control"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="taxRateIfPickUpLabel" for="articles-taxRateIfPickUp">
                  <Translate contentKey="yoplaApp.articles.taxRateIfPickUp">Tax Rate If Pick Up</Translate>
                </Label>
                <AvField id="articles-taxRateIfPickUp" type="string" className="form-control" name="taxRateIfPickUp" />
              </AvGroup>
              <AvGroup>
                <Label id="taxRateIfDineInLabel" for="articles-taxRateIfDineIn">
                  <Translate contentKey="yoplaApp.articles.taxRateIfDineIn">Tax Rate If Dine In</Translate>
                </Label>
                <AvField id="articles-taxRateIfDineIn" type="string" className="form-control" name="taxRateIfDineIn" />
              </AvGroup>
              <AvGroup>
                <Label id="informationLabel" for="articles-information">
                  <Translate contentKey="yoplaApp.articles.information">Information</Translate>
                </Label>
                <AvField id="articles-information" type="text" name="information" />
              </AvGroup>
              <AvGroup>
                <Label id="ingredientLabel" for="articles-ingredient">
                  <Translate contentKey="yoplaApp.articles.ingredient">Ingredient</Translate>
                </Label>
                <AvField id="articles-ingredient" type="text" name="ingredient" />
              </AvGroup>
              <AvGroup>
                <Label id="imageLabel" for="articles-image">
                  <Translate contentKey="yoplaApp.articles.image">Image</Translate>
                </Label>
                <AvField id="articles-image" type="text" name="image" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/articles" replace color="info">
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
  articlesEntity: storeState.articles.entity,
  loading: storeState.articles.loading,
  updating: storeState.articles.updating,
  updateSuccess: storeState.articles.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesUpdate);
