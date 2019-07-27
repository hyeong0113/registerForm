import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBaptism, defaultValue } from 'app/shared/model/baptism.model';

export const ACTION_TYPES = {
  FETCH_BAPTISM_LIST: 'baptism/FETCH_BAPTISM_LIST',
  FETCH_BAPTISM: 'baptism/FETCH_BAPTISM',
  CREATE_BAPTISM: 'baptism/CREATE_BAPTISM',
  UPDATE_BAPTISM: 'baptism/UPDATE_BAPTISM',
  DELETE_BAPTISM: 'baptism/DELETE_BAPTISM',
  RESET: 'baptism/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBaptism>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BaptismState = Readonly<typeof initialState>;

// Reducer

export default (state: BaptismState = initialState, action): BaptismState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BAPTISM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BAPTISM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BAPTISM):
    case REQUEST(ACTION_TYPES.UPDATE_BAPTISM):
    case REQUEST(ACTION_TYPES.DELETE_BAPTISM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BAPTISM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BAPTISM):
    case FAILURE(ACTION_TYPES.CREATE_BAPTISM):
    case FAILURE(ACTION_TYPES.UPDATE_BAPTISM):
    case FAILURE(ACTION_TYPES.DELETE_BAPTISM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BAPTISM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BAPTISM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BAPTISM):
    case SUCCESS(ACTION_TYPES.UPDATE_BAPTISM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BAPTISM):
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

const apiUrl = 'api/baptisms';

// Actions

export const getEntities: ICrudGetAllAction<IBaptism> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BAPTISM_LIST,
  payload: axios.get<IBaptism>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBaptism> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BAPTISM,
    payload: axios.get<IBaptism>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBaptism> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BAPTISM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBaptism> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BAPTISM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBaptism> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BAPTISM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
