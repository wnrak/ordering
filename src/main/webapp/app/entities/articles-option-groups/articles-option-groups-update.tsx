import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './articles-option-groups.reducer';
import { IArticlesOptionGroups } from 'app/shared/model/articles-option-groups.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticlesOptionGroupsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticlesOptionGroupsUpdate = (props: IArticlesOptionGroupsUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articlesOptionGroupsEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/articles-option-groups');
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
        ...articlesOptionGroupsEntity,
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
          <h2 id="yoplaApp.articlesOptionGroups.home.createOrEditLabel">
            <Translate contentKey="yoplaApp.articlesOptionGroups.home.createOrEditLabel">Create or edit a ArticlesOptionGroups</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articlesOptionGroupsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="articles-option-groups-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="articles-option-groups-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="optionNameLabel" for="articles-option-groups-optionName">
                  <Translate contentKey="yoplaApp.articlesOptionGroups.optionName">Option Name</Translate>
                </Label>
                <AvField
                  id="articles-option-groups-optionName"
                  type="text"
                  name="optionName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="activeOptionNameLabel" for="articles-option-groups-activeOptionName">
                  <Translate contentKey="yoplaApp.articlesOptionGroups.activeOptionName">Active Option Name</Translate>
                </Label>
                <AvField
                  id="articles-option-groups-activeOptionName"
                  type="text"
                  name="activeOptionName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="minValueLabel" for="articles-option-groups-minValue">
                  <Translate contentKey="yoplaApp.articlesOptionGroups.minValue">Min Value</Translate>
                </Label>
                <AvField id="articles-option-groups-minValue" type="string" className="form-control" name="minValue" />
              </AvGroup>
              <AvGroup>
                <Label id="maxValueLabel" for="articles-option-groups-maxValue">
                  <Translate contentKey="yoplaApp.articlesOptionGroups.maxValue">Max Value</Translate>
                </Label>
                <AvField id="articles-option-groups-maxValue" type="string" className="form-control" name="maxValue" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/articles-option-groups" replace color="info">
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
  articlesOptionGroupsEntity: storeState.articlesOptionGroups.entity,
  loading: storeState.articlesOptionGroups.loading,
  updating: storeState.articlesOptionGroups.updating,
  updateSuccess: storeState.articlesOptionGroups.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesOptionGroupsUpdate);
