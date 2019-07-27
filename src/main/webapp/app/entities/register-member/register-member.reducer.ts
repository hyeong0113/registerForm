import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRegisterMember, defaultValue } from 'app/shared/model/register-member.model';

export const ACTION_TYPES = {
  FETCH_REGISTERMEMBER_LIST: 'registerMember/FETCH_REGISTERMEMBER_LIST',
  FETCH_REGISTERMEMBER: 'registerMember/FETCH_REGISTERMEMBER',
  CREATE_REGISTERMEMBER: 'registerMember/CREATE_REGISTERMEMBER',
  UPDATE_REGISTERMEMBER: 'registerMember/UPDATE_REGISTERMEMBER',
  DELETE_REGISTERMEMBER: 'registerMember/DELETE_REGISTERMEMBER',
  RESET: 'registerMember/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRegisterMember>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RegisterMemberState = Readonly<typeof initialState>;

// Reducer

export default (state: RegisterMemberState = initialState, action): RegisterMemberState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REGISTERMEMBER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REGISTERMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_REGISTERMEMBER):
    case REQUEST(ACTION_TYPES.UPDATE_REGISTERMEMBER):
    case REQUEST(ACTION_TYPES.DELETE_REGISTERMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_REGISTERMEMBER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REGISTERMEMBER):
    case FAILURE(ACTION_TYPES.CREATE_REGISTERMEMBER):
    case FAILURE(ACTION_TYPES.UPDATE_REGISTERMEMBER):
    case FAILURE(ACTION_TYPES.DELETE_REGISTERMEMBER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTERMEMBER_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGISTERMEMBER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_REGISTERMEMBER):
    case SUCCESS(ACTION_TYPES.UPDATE_REGISTERMEMBER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_REGISTERMEMBER):
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

const apiUrl = 'api/register-members';

// Actions

export const getEntities: ICrudGetAllAction<IRegisterMember> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTERMEMBER_LIST,
    payload: axios.get<IRegisterMember>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRegisterMember> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REGISTERMEMBER,
    payload: axios.get<IRegisterMember>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRegisterMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REGISTERMEMBER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IRegisterMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REGISTERMEMBER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRegisterMember> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REGISTERMEMBER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
