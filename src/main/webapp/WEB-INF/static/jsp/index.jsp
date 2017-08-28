<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-reboot.css">
    <link rel="stylesheet" href="${contextPath}/css/font-awesome.min.css">
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
    <script type="text/javascript" src="${contextPath}/js/brewery-utils.js"></script>
    <script type="text/javascript" src="${contextPath}/js/main.js"></script>
</head>
<body>
<div id="localization" value="${pageContext.response.locale}" style="display: none"></div>
<input type="text" id="authenticated" value="${authenticated}" hidden/>
<input type="text" id="token" value="${token}" hidden/>
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
                        <c:if test="${authenticated}">
                            <li class="navigation-item logout">
                                <span class="fa fa-sign-out fa-2x logout-ico"></span>
                            </li>
                        </c:if>
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
                <a href="#"><img src="${contextPath}/img/logo.png" alt="logo"></a>
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
                    <li class="navigation-item contacts-nav">
                        <a href="#contact-info-nav" class="navigation-link">
                            <spring:message code="contacts"/>
                        </a>
                    </li>
                    <c:if test="${authenticated}">
                        <li class="navigation-item logout">
                            <span class="fa fa-sign-out fa-2x logout-ico"></span>
                        </li>
                    </c:if>
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
        <div class="text-wrapper">
            <div class="container">
                <div class="asortiment">
                    <h2>
                        <spring:message code="assortment"/>
                    </h2>
                </div>
            </div>
        </div>
        <div class="toolbar">
            <c:if test="${authenticated}">
                <button type="button" class="addNew">
                    <span class="fa fa-plus-square-o fa-3x" title="Add new product"></span>
                </button>
            </c:if>
        </div>
        <!--Beer variety-->
        <div class="selection-of-beer-wpar">
            <div class="container-fluid">
                <div class="selection-of-beer"></div>
            </div>
        </div>
    </div>
    <div id="brewery" class="slider-wrap">
        <div class="container-fluid">
            <div class="toolbar">
                <c:if test="${authenticated}">
                    <button type="button" class="addNew" id="addNewImg">
                        <span class="fa fa-plus-square-o fa-3x" title="Add new image"></span>
                    </button>
                </c:if>
            </div>
            <div class="carousel fade"></div>
        </div>
    </div>
    <div id="history" class="history_wrap">
        <div class="text-wrapper">
            <div class="container">
                <div class="asortiment our_history">
                    <h2>
                        <spring:message code="our.history"/>
                    </h2>
                </div>
            </div>
        </div>
        <div class="history-info-wrapper">
            <div class="container">
                <div class="our_history_desc">
                    <div class="toolbar">
                        <c:if test="${authenticated}">
                            <span class="fa fa-pencil-square-o fa-3x edit-history" id="editHistory" title="edit history"></span>
                            <span class='fa fa-plus-square-o fa-3x add-history'  id="addHistory" title='add article'></span>
                        </c:if>
                    </div>
                    <p class="history-info"></p>
                    <form id="history-edit-form" hidden>
                        <div class="form-group">
                            <select class="form-control" id="history-lang-selector">
                                <option value="ENG">English</option>
                                <option value="UA">Україньська</option>
                                <option value="RUS">Русский</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="history-text"></label>
                            <textarea class="form-control" id="history-text" rows="10"></textarea>
                        </div>
                        <div>
                            <button type="button" class="btn btn-info send-button" id="sendHistoryData">Send</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="contact-info-nav" class="contact-info-wrap">
        <div class="contact-info-logo">
            <div class="container">
                <div class="contact-info">
                    <h2>
                        <spring:message code="contact.information"/>
                    </h2>
                </div>
            </div>
        </div>
        <div class="container">
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
                            <p class="first-paragraph">
                            </p>
                            <p class="composition"></p>
                        </div>
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
<div id="send-form-popup">
    <div class="wrap-popup">
        <div class="container">
            <div class="window">
                <span class="button b-close">X</span>
                <div class="window-img">
                    <img class="product-logo-img" src="${contextPath}/img/dark-beer.png" alt="">
                </div>
                <div class="window-text">
                    <div>
                        <form id="edit-form">
                            <span id="product-send-error" class="product-send-error"></span>
                            <div class="form-group" hidden>
                                <label for="name">Product Name:</label>
                                <input type="text" title="Product name should be consist only from latin chars" class="form-control" id="name" disabled/>
                            </div>
                            <div class="form-group">
                                <label for="desc-lang-selector">Description Language:</label>
                                <select class="form-control" id="desc-lang-selector">
                                    <option value="ENG">English</option>
                                    <option value="UA">Україньська</option>
                                    <option value="RUS">Русский</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="alert alert-danger alert-dismissable" hidden>
                                    <strong>Danger!</strong> Product tittle is required parameter.
                                </div>
                                <label for="title">Product Title:</label>
                                <input type="text" class="form-control" name="title" id="title"/>
                            </div>
                            <div class="form-group">
                                <label for="types-selector">Select Types:</label>
                                <select class="form-control" id="types-selector"></select>
                            </div>
                            <div class="form-group">
                                <label for="description">Product Description:</label>
                                <textarea class="form-control" name="description" id="description" rows="7"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="composition">Product Composition:</label>
                                <textarea class="form-control" id="composition" name="composition" rows="3"></textarea>
                            </div>
                            <div>
                                <button type="button" class="btn btn-info send-button" id="sendProductData">Send</button>
                            </div>
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
<div id="save-img-popup">
    <div class="wrap-popup">
        <div class="image-window-container">
            <div class="b-close-wrapper">
                <span class="button b-close">X</span>
            </div>
            <div class="image-window">
                <form id="img-form">
                    <div class="form-group">
                        <label for="file">Upload Image:</label>
                        <input id="file" type="file" name="files[]" multiple>
                    </div>
                    <div>
                        <button type="button" class="btn btn-info send-button" onclick="functionality.uploadImages()" id="saveImg">Send</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</html>
