const REQUEST = 'REQUEST';
const SUCCESS = 'SUCCESS';
const FAILURE = 'FAILURE';

export const createRequestTypes = (base) => {
  const res = {};
  //eslint-disable-next-line
  [REQUEST, SUCCESS, FAILURE].forEach(type => res[type] = `${base}_${type}`);
  return res;
};

export const action = (type, payload = {}) => ({ type, ...payload });

export const UPDATE_ROUTER_STATE = 'UPDATE_ROUTER_STATE';
export const NAVIGATE = 'NAVIGATE';
export const RESET_ERROR_MESSAGE = 'RESET_ERROR_MESSAGE';
export const SHOW_ERROR_MESSAGE = 'SHOW_ERROR_MESSAGE';

export const CLEAN_STORAGE = 'CLEAN_STORAGE';
export const CLEAN_COOKIES = 'CLEAN_COOKIES';

export const updateRouterState = state => action(UPDATE_ROUTER_STATE, { state });
export const navigate = pathname => action(NAVIGATE, { pathname });
export const resetErrorMessage = () => action(RESET_ERROR_MESSAGE);
export const showErrorMessage = errors => action(SHOW_ERROR_MESSAGE, { errors });
export const cleanStorage = () => action(CLEAN_STORAGE, {});
export const cleanCookies = () => action(CLEAN_COOKIES, {});
