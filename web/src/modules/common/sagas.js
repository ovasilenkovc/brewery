/* eslint-disable no-constant-condition */
import { takeEvery, put } from 'redux-saga/effects';
import { push } from 'connected-react-router';
import { NAVIGATE } from './actions';

export function* navigation(pathname) {
  yield put(push(pathname));
}

/**
 ****************************** WATCHERS ***********************************
 **/

// trigger router navigation via history
export function* watchNavigate() {
  yield takeEvery(NAVIGATE, navigation);
}

export default {
  watchNavigate
};
