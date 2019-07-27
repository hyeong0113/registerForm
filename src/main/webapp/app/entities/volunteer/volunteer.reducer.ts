import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVolunteer, defaultValue } from 'app/shared/model/volunteer.model';

export const ACTION_TYPES = {
  FETCH_VOLUNTEER_LIST: 'volunteer/FETCH_VOLUNTEER_LIST',
  FETCH_VOLUNTEER: 'volunteer/FETCH_VOLUNTEER',
  CREATE_VOLUNTEER: 'volunteer/CREATE_VOLUNTEER',
  UPDATE_VOLUNTEER: 'volunteer/UPDATE_VOLUNTEER',
  DELETE_VOLUNTEER: 'volunteer/DELETE_VOLUNTEER',
  RESET: 'volunteer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVolunteer>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VolunteerState = Readonly<typeof initialState>;

// Reducer

export default (state: VolunteerState = initialState, action): VolunteerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VOLUNTEER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VOLUNTEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VOLUNTEER):
    case REQUEST(ACTION_TYPES.UPDATE_VOLUNTEER):
    case REQUEST(ACTION_TYPES.DELETE_VOLUNTEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VOLUNTEER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VOLUNTEER):
    case FAILURE(ACTION_TYPES.CREATE_VOLUNTEER):
    case FAILURE(ACTION_TYPES.UPDATE_VOLUNTEER):
    case FAILURE(ACTION_TYPES.DELETE_VOLUNTEER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOLUNTEER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOLUNTEER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VOLUNTEER):
    case SUCCESS(ACTION_TYPES.UPDATE_VOLUNTEER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VOLUNTEER):
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

const apiUrl = 'api/volunteers';

// Actions

export const getEntities: ICrudGetAllAction<IVolunteer> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VOLUNTEER_LIST,
  payload: axios.get<IVolunteer>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVolunteer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VOLUNTEER,
    payload: axios.get<IVolunteer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVolunteer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VOLUNTEER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVolunteer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VOLUNTEER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVolunteer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VOLUNTEER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
