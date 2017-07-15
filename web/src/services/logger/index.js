/* eslint-disable no-underscore-dangle,no-undef */

const LEVELS = {
  DEBUG: '[DEBUG]: ',
  INFO: '[INFO]: '
};

const log = (level, args) => {
  if (__DEBUG__) {
    console.log(level, args);
  }
};


const Logger = {
  debug: (...args) => {
    log(LEVELS.DEBUG, args);
  },
  info: (...args) => {
    log(LEVELS.INFO, args);
  }
};

export default Logger;
