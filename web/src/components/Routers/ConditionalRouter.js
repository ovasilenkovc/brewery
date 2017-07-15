import React, { PropTypes } from 'react';
import { Route, Redirect, withRouter } from 'react-router-dom';
import Logger from '../../services/logger';

const ConditionRoute = ({ component: Component, condition, fallbackUrl, ...rest }) => {
  Logger.debug('ConditionRoute', condition, fallbackUrl);
  return (
    <Route
      {...rest}
      render={(props) => {
        Logger.debug('router of ConditionRoute', condition, condition ? rest.path : fallbackUrl);
        return condition ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: fallbackUrl,
              state: { from: props.location.pathname }
            }}
          />
        );
      }}
    />);
};

ConditionRoute.propTypes = {
  component: PropTypes.func,
  condition: PropTypes.bool.isRequired,
  fallbackUrl: PropTypes.string.isRequired,
  location: PropTypes.shape({
    pathname: PropTypes.string,
    hash: PropTypes.string,
    search: PropTypes.string
  })
};

ConditionRoute.defaultProps = {
  location: { pathname: '/' },
  component: undefined
};

export default withRouter(ConditionRoute);
