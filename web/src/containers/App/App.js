import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

class App extends PureComponent {


  render() {
    return (
      <div>
        App
      </div>
    );
  }
}

App.propTypes = {};

App.defaultProps = {};

const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
