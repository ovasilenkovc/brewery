import React, { PropTypes, Component } from 'react';
import logo from '../../theme/img/logo-img.png';
import lengBetween from '../../theme/img/leng-between.png';
import './Headers.scss'

export default class Header extends Component {
    render() {
        return (<header>
            <div className="container-fluid">
                <div className="header-wrap">
                    <div className="header-logo">
                        <a href="#"><img src={logo} alt="logo" /></a>
                    </div>
                    <div className="header-nav">
                        <ul className="navigation">
                            <li className="navigation-item"><a href="#asortiment-nav" className="navigation-link">асортимент</a></li>
                            <li className="navigation-item"><a href="#brewery" className="navigation-link">пивоварня</a></li>
                            <li className="navigation-item"><a href="#history" className="navigation-link">наша iсторiя</a></li>
                            <li className="navigation-item"><a href="#contact-info-nav" className="navigation-link">контакти</a></li>
                        </ul>
                        <div className="lenguage-change">
                            <ul>
                                <li><a href="#" className="leng">укр</a></li>
                                <li><img src={lengBetween} alt="" /></li>
                                <li><a href="#" className="leng">рус</a></li>
                                <li><img src={lengBetween} alt="" /></li>
                                <li><a href="#" className="leng">eng</a></li>
                            </ul>
                        </div>
                    </div>
                    <div className="try-new">
                        <p>спробуй новий смак<br />
                            натурального<br />
                            українського пива</p>
                    </div>
                </div>
            </div>
        </header>)
    }
}
