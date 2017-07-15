import { all } from 'redux-saga/effects';
import { watchNavigate } from './common/sagas';

export default function* rootSaga() {
  yield all([
    watchNavigate(),
  ]);
}
