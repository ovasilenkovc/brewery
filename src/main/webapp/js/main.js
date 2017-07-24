var SESSION_TOKEN = "Bearer ",
    CURRENT_LANGUAGE = "UA",
    SERVER_CONTEXT = "/brewery";
LANG_MAPPING = {
    "en": "ENG",
    "ua": "UA",
    "ru": "RUS"
};


var functionality = {

    init: function init() {
        this.userNameEl = $("#username");
        this.userPasswordEl = $("#password");
        this.loginErorEl = $("#login-error");
        CURRENT_LANGUAGE = LANG_MAPPING[$('#localization').attr('value')];
        this.getProducts();
    },

    login: function () {
        var url = SERVER_CONTEXT + "/login";
        ajaxBuilder(url, "POST", {
            "username": functionality.userNameEl.val(),
            "password": functionality.userPasswordEl.val()
        }, function (response) {
            localStorage.setItem('token', JSON.stringify(response.token));
            window.location.href = window.location.origin + SERVER_CONTEXT;
        }, function () {
            functionality.loginErorEl.addClass("show")
        })
    },

    logout: function () {
        var url = SERVER_CONTEXT + "/logout";
        ajaxBuilder(url, "DELETE", {},
            function () {
                localStorage.clear()
            });
    },

    getProducts: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product";
        ajaxBuilder(url, 'GET', {}, function (response) {
            var productsBlock = $('.selection-of-beer');
            for (var i = 0; i < response.length; i++) {
                self.renderProducts(productsBlock, response[i], CURRENT_LANGUAGE);
            }
            this.productEl = $(".position-of-beer");
            this.productEl.click(self.viewOneProduct);
        }, function (response) {
            alert(response);
        })
    },

    renderProducts: function (parent, product, local) {
        var productId = product.productId;
        var productType = product.productType;
        var productDsecription = this.getDescriptionByLeng(product.descriptions, local);
        var title = productDsecription ? productDsecription.title : product.name;
        var productDivEl = $('<div class="position-of-beer" value="' + productId + '" data-toggle="modal" data-target="#productModal">'
            + '<div class="beer-img"><a href="#"><img src="' + productType.iconPath + '"></a></div>'
            + '<div class="romb-img"><div class="romb-text"><span>' + title + '</span></div>'
            + '<img src="/brewery/img/romb-bg.png"></div></div>');
        productDivEl.appendTo(parent);
    },

    getDescriptionByLeng: function (descriptions, leng) {
        for (var i = 0; i < descriptions.length; i++) {
            var decType = descriptions[i].type;
            if (decType === leng) {
                return descriptions[i];
            }
        }
        return null;
    },

    viewOneProduct: function () {
        var prodId = $(this).attr('value');
        var url = SERVER_CONTEXT + "/content/product/" + prodId;
        ajaxBuilder(url, "GET", {}, function (response) {
            debugger;
            var descriptions = response.descriptions;
            var productType = response.productType;
            var productDescription = functionality.getDescriptionByLeng(descriptions, CURRENT_LANGUAGE);
        }, function (err) {
            debugger;
        })
    }

};

var ajaxBuilder = function (url, type, content, success, reject) {
    var ajaxConfig = {
        url: url,
        type: type,
        cache: false,
        dataType: 'json',
        headers: {Authorization: SESSION_TOKEN},
        contentType: "application/json",
        success: success,
        error: reject
    };
    content ? ajaxConfig.data = JSON.stringify(content) : ajaxConfig;
    $.ajax(ajaxConfig)
};

$(document).ready(function () {
    functionality.init();
});