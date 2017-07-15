import { notification } from 'antd';
import { UPDATE_ROUTER_STATE, SHOW_ERROR_MESSAGE } from './actions';


export const showErrorMessage = (state = null, action) => {
  switch (action.type) {
    case SHOW_ERROR_MESSAGE: {
      action.errors.forEach((it) => {
        notification.error({
          message: it.title,
          description: it.detail,
        });
      });
      return state;
    }
    default:
      return state;
  }
};

export const router = (state = { pathname: '/' }, action) => {
  switch (action.type) {
    case UPDATE_ROUTER_STATE:
      return action.state;
    default:
      return state;
  }
};


export default {
  showErrorMessage,
  router
};
