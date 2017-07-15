import { combineReducers } from 'redux';
import { showErrorMessage, router } from './common/reducers';

const rootReducer = combineReducers({
  showErrorMessage,
  router,
});

export default rootReducer;
