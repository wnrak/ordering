import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticlesOptions, defaultValue } from 'app/shared/model/articles-options.model';

export const ACTION_TYPES = {
  SEARCH_ARTICLESOPTIONS: 'articlesOptions/SEARCH_ARTICLESOPTIONS',
  FETCH_ARTICLESOPTIONS_LIST: 'articlesOptions/FETCH_ARTICLESOPTIONS_LIST',
  FETCH_ARTICLESOPTIONS: 'articlesOptions/FETCH_ARTICLESOPTIONS',
  CREATE_ARTICLESOPTIONS: 'articlesOptions/CREATE_ARTICLESOPTIONS',
  UPDATE_ARTICLESOPTIONS: 'articlesOptions/UPDATE_ARTICLESOPTIONS',
  DELETE_ARTICLESOPTIONS: 'articlesOptions/DELETE_ARTICLESOPTIONS',
  RESET: 'articlesOptions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticlesOptions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ArticlesOptionsState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticlesOptionsState = initialState, action): ArticlesOptionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ARTICLESOPTIONS):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLESOPTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLESOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLESOPTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLESOPTIONS):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLESOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ARTICLESOPTIONS):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLESOPTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLESOPTIONS):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLESOPTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLESOPTIONS):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLESOPTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ARTICLESOPTIONS):
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLESOPTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLESOPTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLESOPTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLESOPTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLESOPTIONS):
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

const apiUrl = 'api/articles-options';
const apiSearchUrl = 'api/_search/articles-options';

// Actions

export const getSearchEntities: ICrudSearchAction<IArticlesOptions> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ARTICLESOPTIONS,
  payload: axios.get<IArticlesOptions>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IArticlesOptions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ARTICLESOPTIONS_LIST,
  payload: axios.get<IArticlesOptions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IArticlesOptions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLESOPTIONS,
    payload: axios.get<IArticlesOptions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArticlesOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLESOPTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArticlesOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLESOPTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticlesOptions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLESOPTIONS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
