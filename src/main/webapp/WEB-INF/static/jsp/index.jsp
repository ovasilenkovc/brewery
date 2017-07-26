<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-reboot.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-grid.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/headhesive.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/styles.css">

    <link rel="apple-touch-icon" sizes="180x180" href="${contextPath}/img/favicon_folder/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${contextPath}/img/favicon_folder/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${contextPath}/img/favicon_folder/favicon-16x16.png">
    <link rel="manifest" href="${contextPath}/img/favicon_folder/manifest.json">
    <link rel="mask-icon" href="${contextPath}/img/favicon_folder/safari-pinned-tab.svg" color="#5bbad5">
    <meta name="theme-color" content="#ffffff">

    <script type="text/javascript" src="${contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${contextPath}/js/headhesive.js"></script>
    <script type="text/javascript" src="${contextPath}/js/slick.js"></script>
    <script src="${contextPath}/js/modal_window.js" type="text/javascript"></script>
    <script src="${contextPath}/js/templates.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/js/main.js"></script>
</head>
<body>
<div id="localization" value="${pageContext.response.locale}" style="display: none"></div>
<figure id="invis" hidden=>
    <header class="banner">
        <nav class="container">
            <div class="nav-min">
                <div class="logo-stick">
                    <a href="#"><img src="${contextPath}/img/logo-stick.png" alt=""></a>
                </div>
                <div class="stick-nav">
                    <ul class="navigation">
                        <li class="navigation-item">
                            <a href="#asortiment-nav" class="navigation-link-stick">
                                <spring:message code="assortment"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#brewery" class="navigation-link-stick">
                                <spring:message code="brewery"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#history" class="navigation-link-stick">
                                <spring:message code="history"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#contact-info-nav" class="navigation-link-stick">
                                <spring:message code="contacts"/>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
</figure>
<header>
    <div class="container-fluid">
        <div class="header-wrap">
            <!-- logo -->
            <div class="header-logo">
                <a href="#"><img src="${contextPath}/img/logo-img.png" alt="logo"></a>
            </div>
            <!-- navigation -->
            <div class="header-nav">
                <ul class="navigation">
                    <li class="navigation-item">
                        <a href="#asortiment-nav" class="navigation-link">
                            <spring:message code="assortment"/>
                        </a>
                    </li>
                    <li class="navigation-item">
                        <a href="#brewery" class="navigation-link">
                            <spring:message code="brewery"/>
                        </a>
                    </li>
                    <li class="navigation-item">
                        <a href="#history" class="navigation-link">
                            <spring:message code="history"/>
                        </a>
                    </li>
                    <li class="navigation-item">
                        <a href="#contact-info-nav" class="navigation-link">
                            <spring:message code="contacts"/>
                        </a>
                    </li>
                </ul>
                <div class="lenguage-change">
                    <ul>
                        <li><a href="${contextPath}?language=ua" class="leng" value="UA">укр</a></li>
                        <li><img src="${contextPath}/img/leng-between.png" alt=""></li>
                        <li><a href="${contextPath}?language=ru" class="leng" value="RUS">рус</a></li>
                        <li><img src="${contextPath}/img/leng-between.png" alt=""></li>
                        <li><a href="${contextPath}?language=en" class="leng" value="ENG">eng</a></li>
                    </ul>
                </div>
            </div>
            <div class="try-new">
                <p><spring:message code="tagline"/></p>
            </div>
        </div>
    </div>
</header>
<main>
    <a id="showHere"></a>
    <div class="contact-us">
        <div class="container">
            <div class="contact-us-text">
                    <span class="white-text">
                        <spring:message code="contact.us"/>
                    </span>
                <span class="contact-us-text-mail">
                        <spring:message code="contact.email"/>
                    </span>
                <span class="number">
                        <spring:message code="contact.phone.first"/>
                    </span>
                <span class="number">
                        <spring:message code="contact.phone.second"/>
                    </span>
            </div>
        </div>
    </div>
    <div id="asortiment-nav" class="asortiment-wrap">
        <div class="assortment-par-wrapper">
            <div class="container">
                <div class="asortiment">
                    <h2>
                        <spring:message code="assortment"/>
                    </h2>
                </div>
            </div>
        </div>
        <div class="toolbar">
            <button type="button" class="btn btn-default btn-sm addNew">
                <span class="glyphicon glyphicon-plus"></span> Plus
            </button>
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-remove"></span> Remove
            </button>
        </div>
        <!--Beer variety-->
        <div class="selection-of-beer-wpar">
            <div class="container-fluid">
                <div class="selection-of-beer">
                    <!--This block will be represented as tubs! With one difference between them is content (img, name)
                     It means that block "position-of-beer" is component!-->
                    <%--
                    <div class="position-of-beer">
                        <div class="beer-img-wraper">
                            <div class="beer-img">
                                <a href="#">
                                    <img src="${contextPath}/img/dark-beer.png">
                                </a>
                            </div>
                            <div class="romb-img">
                                <div class="romb-text">
                                    <span>темне</span>
                                </div>
                                <img src="${contextPath}/img/romb-bg.png">
                            </div>
                        </div>
                    </div>
                    --%>
                </div>
            </div>
        </div>
    </div>
    <div id="brewery" class="slider-wrap">
        <div class="container-fluid">
            <div class="carousel fade">
                <div>
                    <div class="carousel-img">
                        <img src="${contextPath}/img/slider-img-1.png" alt="картинка слайда">
                    </div>
                </div>
                <div>
                    <div class="carousel-img">
                        <img src="${contextPath}/img/slider-img-2.png" alt="картинка слайда">
                    </div>
                </div>
                <div>
                    <div class="carousel-img">
                        <img src="${contextPath}/img/slider-img.png" alt="картинка слайда">
                    </div>
                </div>
                <div>
                    <div class="carousel-img">
                        <img src="${contextPath}/img/slider-img.png" alt="картинка слайда">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="history" class="history_wrap">
        <div class="toolbar">
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-edit"></span> Edit
            </button>
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-plus"></span> Plus
            </button>
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-remove"></span> Remove
            </button>
        </div>
        <div class="container">
            <div class="asortiment our_history">
                <h2>
                    <spring:message code="our.history"/>
                </h2>
            </div>
            <div class="our_history_desc">
                <p class="history-info">
                    Trillium Brewing Company was established on the values of family, passion, and dedication.
                    Celebratory ales were first crafted to commemorate the marriage of founders JC and Esther Tetreault.
                    This initial concept evolved into a lifestyle and dream to share, not only beer, experiences with
                    one another and their community.
                </p>
                <p class="history-info">
                    Trillium opened in March 2013 with the support of family, volunteers, two babies, and three
                    employees. This small brewery has since developed into an exciting venue of collaboration and
                    innovation. Trillium is a New England farmhouse style brewery, deeply rooted in the dynamic
                    landscapes, abundant natural resources, and resilient population of the region. From our wild ales,
                    fermented with our native New England mixed microbe culture, to our more hop-forward offerings, we
                    aim to produce beer that is both approachable and engaging.
                </p>
                <p class="history-info">
                    Our flagship location is tucked in the vibrant Fort Point neighborhood of South Boston where we
                    utilize practices representing both tradition and modern re-invention. We find inspiration in the
                    heritage of farmhouse brewing methods while actively employing novel concepts and technologies. In
                    December 2015, we opened our secondary facility in Canton, MA, which will allow us to significantly
                    increase production volume for wider availability and expanded variety.
                </p>
            </div>
        </div>
    </div>
    <div id="contact-info-nav" class="contact-info-wrap">
        <div class="toolbar">
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-edit"></span> Edit
            </button>
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-plus"></span> Plus
            </button>
            <button type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-remove"></span> Remove
            </button>
        </div>
        <div class="container">
            <div class="contact-info">
                <h2>
                    <spring:message code="contact.information"/>
                </h2>
            </div>
            <div class="contact-info-text">
                <div class="contact-info-address">
                    <div class="address"><spring:message code="address"/></div>
                    <div class="adress-text"><spring:message code="address.text"/></div>
                </div>
                <div class="contact-info-numbers">
                    <div class="contact-email">
                        <span class="bolder-font">e-mail:</span> hzpivovarnya@com.ua
                    </div>
                    <div class="contact-info-tel">
                        <span class="bolder-font"><spring:message code="phone"/></span> +38 (057) 888-22-55, +38 (095)
                        111-22-33
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="finaly-img-wrap">
        <div class="container-fluid">
            <div class="finaly-img">
            </div>
        </div>
    </div>
</main>
<footer>
    <div class="down-info-wrap">
        <div class="container">
            <div class="down-info-text">
                <div class="footer-nav">
                    <ul class="navigation">
                        <li class="navigation-item">
                            <a href="#asortiment-nav" class="navigation-link-stick">
                                <spring:message code="assortment"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#brewery" class="navigation-link-stick">
                                <spring:message code="brewery"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#history" class="navigation-link-stick">
                                <spring:message code="history"/>
                            </a>
                        </li>
                        <li class="navigation-item">
                            <a href="#contact-info-nav" class="navigation-link-stick">
                                <spring:message code="contacts"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="all-rights">
                    <span class="footer-text akcent-color"><spring:message code="copyright.name"/></span>
                    <span class="footer-text"><spring:message code="copyright.rights"/></span>
                </div>
            </div>
        </div>
    </div>
</footer>
<script type="text/javascript">
    var options = {
        offset: '#showHere',
        offsetSide: 'top',
        classes: {
            clone: 'banner--clone',
            stick: 'banner--stick',
            unstick: 'banner--unstick'
        }
    };
    $('.fade').slick({
        dots: true,
        infinite: true,
        autoplay: true,
        speed: 500,
        fade: true,
        cssEase: 'linear'
    });
    // Initialise with options
    var banner = new Headhesive('.banner', options);
</script>
</body>
<div id="popup">
    <div class="wrap-popup">
        <div class="container">
            <div class="window">
                <span class="button b-close">X</span>
                <div class="window-img">
                    <img class="product-logo-img" src="${contextPath}/img/dark-beer.png" alt="">
                </div>
                <div class="window-text">
                    <div id="content-wrapper">
                        <div class="window-text-beer-place">
                        <span>
                            <spring:message code="brewery.name"/>
                        </span>
                        </div>
                        <div class="window-text-title">
                            <h4 class="product-name"></h4>
                        </div>
                        <div class="windows-text-paragraph">
                            <button class="btn-info" id="edit-product" value="">Edit</button>
                            <p class="first-paragraph">
                            </p>
                            <p class="composition"></p>
                        </div>
                    </div>
                    <div id="edit-form" hidden>
                        <form>
                            <div class="form-group">
                                <label for="name">Product Name:</label>
                                <input type="text" class="form-control" id="name"/>
                            </div>
                            <div class="form-group">
                                <label for="types-selector">Select types:</label>
                                <select class="form-control" id="types-selector">
                                    <option value="light">default</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="description">Product description:</label>
                                <textarea class="form-control" id="description" rows="7"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="composition">Product composition:</label>
                                <textarea class="form-control" id="composition" rows="3"></textarea>
                            </div>
                            <button type="button" class="btn btn-info" id="backToProduct">Back</button>
                            <button type="button" class="btn btn-info login-btn" id="sendProductData" value="">Send</button>
                        </form>
                    </div>
                </div>
                <div class="last-row-popup">
                    <span>
                        <spring:message code="health.warning"/>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div id="send-form-popup">
    <div class="wrap-popup">
        <div class="container">
            <div class="window">
                <span class="button b-close">X</span>
                <div class="window-img">
                    <img class="product-logo-img" src="${contextPath}/img/dark-beer.png" alt="">
                </div>
                <div class="window-text">
                    <div id="edit-form">
                        <form>
                            <div class="form-group">
                                <label for="name">Product Name:</label>
                                <input type="text" class="form-control" id="name"/>
                            </div>
                            <div class="form-group">
                                <label for="types-selector">Select types:</label>
                                <select class="form-control" id="types-selector">
                                    <option value="light">default</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="description">Product description:</label>
                                <textarea class="form-control" id="description" rows="7"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="composition">Product composition:</label>
                                <textarea class="form-control" id="composition" rows="3"></textarea>
                            </div>
                            <button type="button" class="btn btn-info login-btn" id="sendProductData">Send</button>
                        </form>
                    </div>
                </div>
                <div class="last-row-popup">
                    <span>
                        <spring:message code="health.warning"/>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>--%>
</html>
