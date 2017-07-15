/* eslint-disable no-underscore-dangle,no-undef */
import { applyMiddleware, compose, createStore} from 'redux';
import createSagaMiddleware from 'redux-saga';
import logger from 'redux-logger';
import { createHashHistory } from 'history';
import { connectRouter, routerMiddleware } from 'connected-react-router';
import rootSaga from '../modules/rootSaga';
import rootReducer from '../modules/rootReducer';

const history = createHashHistory();


export default (initialState = {}) => {
    // ======================================================
    // Middleware Configuration
    // ======================================================
  const sagaMiddleware = createSagaMiddleware();

  const middleware = [sagaMiddleware, routerMiddleware(history)];

    // ======================================================
    // Store Enhancers
    // ======================================================
  const enhancers = [];

  if (__DEBUG__) {
    if (window.__REDUX_DEVTOOLS_EXTENSION__) {
      enhancers.push(window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
    }
    middleware.push(logger);
  }

    // ======================================================
    // Store Instantiation
    // ======================================================
  const store = createStore(connectRouter(history)(rootReducer), initialState, compose(applyMiddleware(...middleware), ...enhancers));

  sagaMiddleware.run(rootSaga);

  return store;
};
