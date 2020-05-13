import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQrCodeTables, defaultValue } from 'app/shared/model/qr-code-tables.model';

export const ACTION_TYPES = {
  SEARCH_QRCODETABLES: 'qrCodeTables/SEARCH_QRCODETABLES',
  FETCH_QRCODETABLES_LIST: 'qrCodeTables/FETCH_QRCODETABLES_LIST',
  FETCH_QRCODETABLES: 'qrCodeTables/FETCH_QRCODETABLES',
  CREATE_QRCODETABLES: 'qrCodeTables/CREATE_QRCODETABLES',
  UPDATE_QRCODETABLES: 'qrCodeTables/UPDATE_QRCODETABLES',
  DELETE_QRCODETABLES: 'qrCodeTables/DELETE_QRCODETABLES',
  RESET: 'qrCodeTables/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQrCodeTables>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type QrCodeTablesState = Readonly<typeof initialState>;

// Reducer

export default (state: QrCodeTablesState = initialState, action): QrCodeTablesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_QRCODETABLES):
    case REQUEST(ACTION_TYPES.FETCH_QRCODETABLES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QRCODETABLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_QRCODETABLES):
    case REQUEST(ACTION_TYPES.UPDATE_QRCODETABLES):
    case REQUEST(ACTION_TYPES.DELETE_QRCODETABLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_QRCODETABLES):
    case FAILURE(ACTION_TYPES.FETCH_QRCODETABLES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QRCODETABLES):
    case FAILURE(ACTION_TYPES.CREATE_QRCODETABLES):
    case FAILURE(ACTION_TYPES.UPDATE_QRCODETABLES):
    case FAILURE(ACTION_TYPES.DELETE_QRCODETABLES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_QRCODETABLES):
    case SUCCESS(ACTION_TYPES.FETCH_QRCODETABLES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_QRCODETABLES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_QRCODETABLES):
    case SUCCESS(ACTION_TYPES.UPDATE_QRCODETABLES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_QRCODETABLES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/qr-code-tables';
const apiSearchUrl = 'api/_search/qr-code-tables';

// Actions

export const getSearchEntities: ICrudSearchAction<IQrCodeTables> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_QRCODETABLES,
  payload: axios.get<IQrCodeTables>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IQrCodeTables> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_QRCODETABLES_LIST,
  payload: axios.get<IQrCodeTables>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IQrCodeTables> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QRCODETABLES,
    payload: axios.get<IQrCodeTables>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IQrCodeTables> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QRCODETABLES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQrCodeTables> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QRCODETABLES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQrCodeTables> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QRCODETABLES,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
