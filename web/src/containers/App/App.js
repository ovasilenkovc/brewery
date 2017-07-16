import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Header } from '../../components/index';
require('../../theme/fonts.scss');

class App extends Component {


  render() {
    return (
      <div>
        <Header />
      </div>
    );
  }
}

App.propTypes = {};

App.defaultProps = {};

const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(App);
