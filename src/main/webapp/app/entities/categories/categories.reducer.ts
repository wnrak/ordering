import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICategories, defaultValue } from 'app/shared/model/categories.model';

export const ACTION_TYPES = {
  SEARCH_CATEGORIES: 'categories/SEARCH_CATEGORIES',
  FETCH_CATEGORIES_LIST: 'categories/FETCH_CATEGORIES_LIST',
  FETCH_CATEGORIES: 'categories/FETCH_CATEGORIES',
  CREATE_CATEGORIES: 'categories/CREATE_CATEGORIES',
  UPDATE_CATEGORIES: 'categories/UPDATE_CATEGORIES',
  DELETE_CATEGORIES: 'categories/DELETE_CATEGORIES',
  RESET: 'categories/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICategories>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CategoriesState = Readonly<typeof initialState>;

// Reducer

export default (state: CategoriesState = initialState, action): CategoriesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CATEGORIES):
    case REQUEST(ACTION_TYPES.FETCH_CATEGORIES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CATEGORIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CATEGORIES):
    case REQUEST(ACTION_TYPES.UPDATE_CATEGORIES):
    case REQUEST(ACTION_TYPES.DELETE_CATEGORIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CATEGORIES):
    case FAILURE(ACTION_TYPES.FETCH_CATEGORIES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CATEGORIES):
    case FAILURE(ACTION_TYPES.CREATE_CATEGORIES):
    case FAILURE(ACTION_TYPES.UPDATE_CATEGORIES):
    case FAILURE(ACTION_TYPES.DELETE_CATEGORIES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CATEGORIES):
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORIES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORIES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CATEGORIES):
    case SUCCESS(ACTION_TYPES.UPDATE_CATEGORIES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CATEGORIES):
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

const apiUrl = 'api/categories';
const apiSearchUrl = 'api/_search/categories';

// Actions

export const getSearchEntities: ICrudSearchAction<ICategories> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CATEGORIES,
  payload: axios.get<ICategories>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICategories> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CATEGORIES_LIST,
  payload: axios.get<ICategories>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICategories> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CATEGORIES,
    payload: axios.get<ICategories>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICategories> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CATEGORIES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICategories> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CATEGORIES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICategories> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CATEGORIES,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
