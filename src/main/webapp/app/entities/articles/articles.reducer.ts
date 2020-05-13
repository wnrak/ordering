import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticles, defaultValue } from 'app/shared/model/articles.model';

export const ACTION_TYPES = {
  SEARCH_ARTICLES: 'articles/SEARCH_ARTICLES',
  FETCH_ARTICLES_LIST: 'articles/FETCH_ARTICLES_LIST',
  FETCH_ARTICLES: 'articles/FETCH_ARTICLES',
  CREATE_ARTICLES: 'articles/CREATE_ARTICLES',
  UPDATE_ARTICLES: 'articles/UPDATE_ARTICLES',
  DELETE_ARTICLES: 'articles/DELETE_ARTICLES',
  RESET: 'articles/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticles>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ArticlesState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticlesState = initialState, action): ArticlesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ARTICLES):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLES):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLES):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ARTICLES):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLES):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLES):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLES):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ARTICLES):
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLES):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLES):
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

const apiUrl = 'api/articles';
const apiSearchUrl = 'api/_search/articles';

// Actions

export const getSearchEntities: ICrudSearchAction<IArticles> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ARTICLES,
  payload: axios.get<IArticles>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IArticles> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ARTICLES_LIST,
  payload: axios.get<IArticles>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IArticles> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLES,
    payload: axios.get<IArticles>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArticles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArticles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticles> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLES,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
