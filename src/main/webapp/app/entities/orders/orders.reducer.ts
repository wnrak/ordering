import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrders, defaultValue } from 'app/shared/model/orders.model';

export const ACTION_TYPES = {
  SEARCH_ORDERS: 'orders/SEARCH_ORDERS',
  FETCH_ORDERS_LIST: 'orders/FETCH_ORDERS_LIST',
  FETCH_ORDERS: 'orders/FETCH_ORDERS',
  CREATE_ORDERS: 'orders/CREATE_ORDERS',
  UPDATE_ORDERS: 'orders/UPDATE_ORDERS',
  DELETE_ORDERS: 'orders/DELETE_ORDERS',
  RESET: 'orders/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrders>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrdersState = Readonly<typeof initialState>;

// Reducer

export default (state: OrdersState = initialState, action): OrdersState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ORDERS):
    case REQUEST(ACTION_TYPES.FETCH_ORDERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERS):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERS):
    case REQUEST(ACTION_TYPES.DELETE_ORDERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ORDERS):
    case FAILURE(ACTION_TYPES.FETCH_ORDERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERS):
    case FAILURE(ACTION_TYPES.CREATE_ORDERS):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERS):
    case FAILURE(ACTION_TYPES.DELETE_ORDERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ORDERS):
    case SUCCESS(ACTION_TYPES.FETCH_ORDERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERS):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERS):
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

const apiUrl = 'api/orders';
const apiSearchUrl = 'api/_search/orders';

// Actions

export const getSearchEntities: ICrudSearchAction<IOrders> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ORDERS,
  payload: axios.get<IOrders>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IOrders> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERS_LIST,
  payload: axios.get<IOrders>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrders> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERS,
    payload: axios.get<IOrders>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrders> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrders> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrders> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
