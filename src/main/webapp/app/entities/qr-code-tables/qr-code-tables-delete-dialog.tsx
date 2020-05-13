import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IQrCodeTables } from 'app/shared/model/qr-code-tables.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './qr-code-tables.reducer';

export interface IQrCodeTablesDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QrCodeTablesDeleteDialog = (props: IQrCodeTablesDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/qr-code-tables');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.qrCodeTablesEntity.id);
  };

  const { qrCodeTablesEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="yoplaApp.qrCodeTables.delete.question">
        <Translate contentKey="yoplaApp.qrCodeTables.delete.question" interpolate={{ id: qrCodeTablesEntity.id }}>
          Are you sure you want to delete this QrCodeTables?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-qrCodeTables" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ qrCodeTables }: IRootState) => ({
  qrCodeTablesEntity: qrCodeTables.entity,
  updateSuccess: qrCodeTables.updateSuccess
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QrCodeTablesDeleteDialog);
