import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { ConnectedRouter } from 'connected-react-router';
import { createHashHistory } from 'history';
import 'babel-polyfill';
import 'antd/dist/antd.css';
import enUS from 'antd/lib/locale-provider/en_US';
import LocaleProvider from 'antd/lib/locale-provider';
import createStore from './store/createStore';
import App from './containers/App/App';

const store = createStore();

const history = createHashHistory({});

ReactDOM.render((
  <Provider store={store}>
    <LocaleProvider locale={enUS}>
      <ConnectedRouter history={history}>
        <App />
      </ConnectedRouter>
    </LocaleProvider>
  </Provider>
), document.getElementById('react-container'));
