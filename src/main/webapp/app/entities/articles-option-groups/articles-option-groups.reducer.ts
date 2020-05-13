import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArticlesOptionGroups, defaultValue } from 'app/shared/model/articles-option-groups.model';

export const ACTION_TYPES = {
  SEARCH_ARTICLESOPTIONGROUPS: 'articlesOptionGroups/SEARCH_ARTICLESOPTIONGROUPS',
  FETCH_ARTICLESOPTIONGROUPS_LIST: 'articlesOptionGroups/FETCH_ARTICLESOPTIONGROUPS_LIST',
  FETCH_ARTICLESOPTIONGROUPS: 'articlesOptionGroups/FETCH_ARTICLESOPTIONGROUPS',
  CREATE_ARTICLESOPTIONGROUPS: 'articlesOptionGroups/CREATE_ARTICLESOPTIONGROUPS',
  UPDATE_ARTICLESOPTIONGROUPS: 'articlesOptionGroups/UPDATE_ARTICLESOPTIONGROUPS',
  DELETE_ARTICLESOPTIONGROUPS: 'articlesOptionGroups/DELETE_ARTICLESOPTIONGROUPS',
  RESET: 'articlesOptionGroups/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArticlesOptionGroups>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ArticlesOptionGroupsState = Readonly<typeof initialState>;

// Reducer

export default (state: ArticlesOptionGroupsState = initialState, action): ArticlesOptionGroupsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ARTICLESOPTIONGROUPS):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTICLESOPTIONGROUPS):
    case REQUEST(ACTION_TYPES.UPDATE_ARTICLESOPTIONGROUPS):
    case REQUEST(ACTION_TYPES.DELETE_ARTICLESOPTIONGROUPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ARTICLESOPTIONGROUPS):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS):
    case FAILURE(ACTION_TYPES.CREATE_ARTICLESOPTIONGROUPS):
    case FAILURE(ACTION_TYPES.UPDATE_ARTICLESOPTIONGROUPS):
    case FAILURE(ACTION_TYPES.DELETE_ARTICLESOPTIONGROUPS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ARTICLESOPTIONGROUPS):
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTICLESOPTIONGROUPS):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTICLESOPTIONGROUPS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTICLESOPTIONGROUPS):
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

const apiUrl = 'api/articles-option-groups';
const apiSearchUrl = 'api/_search/articles-option-groups';

// Actions

export const getSearchEntities: ICrudSearchAction<IArticlesOptionGroups> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ARTICLESOPTIONGROUPS,
  payload: axios.get<IArticlesOptionGroups>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IArticlesOptionGroups> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS_LIST,
  payload: axios.get<IArticlesOptionGroups>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IArticlesOptionGroups> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTICLESOPTIONGROUPS,
    payload: axios.get<IArticlesOptionGroups>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArticlesOptionGroups> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTICLESOPTIONGROUPS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArticlesOptionGroups> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTICLESOPTIONGROUPS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArticlesOptionGroups> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTICLESOPTIONGROUPS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
