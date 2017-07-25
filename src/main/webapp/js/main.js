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

        //using elements initialization
        this.userNameEl = $("#username");
        this.userPasswordEl = $("#password");
        this.loginErorEl = $("#login-error");
        this.popupDesc = $('#popup .first-paragraph');
        this.popupDescComp = $('#popup .composition');
        this.popupProdName = $('#popup .product-name');
        this.popupProdImage = $('#popup .product-logo-img');
        CURRENT_LANGUAGE = LANG_MAPPING[$('#localization').attr('value')];

        //content initiation
        this.getAllProductTypes();
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
            this.productEl.click(function () {
                $('#popup').bPopup({
                    modalClose: true,
                    opacity: 0.6,
                    positionStyle: 'fixed'
                });
                self.viewOneProduct($(this).attr('value'));
            });
        }, function (response) {
            alert(response.error);
        })
    },

    renderProducts: function (parent, product, local) {
        var productId = product.productId;
        var productType = product.productType;
        var productDsecription = this.getDescriptionByLeng(product.descriptions, local);
        var title = productDsecription ? productDsecription.title : product.name;
        var productDivEl = $('<div class="position-of-beer" id="#show_popup" value="' + productId + '" data-toggle="modal" data-target="#productModal">'
            + '<div class="beer-img"><span><img src="' + productType.iconPath + '"></span></div>'
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

    viewOneProduct: function (prodId) {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product/" + prodId;
        ajaxBuilder(url, "GET", {}, function (response) {
            var descriptions = response.descriptions;
            var productType = response.productType;
            var productDescription = functionality.getDescriptionByLeng(descriptions, CURRENT_LANGUAGE);
            var title = productDescription ? productDescription.title : response.name;
            self.popupProdName.text(title);
            self.popupDesc.text(productDescription.description);
            self.popupProdImage.attr("src", productType.iconPath);
            self.popupDescComp.text(productDescription.composition);
            $(".edit-product").click(function () {
                self.editProduct(response)
            });
        }, function (err) {
            alert(err.error);
        })
    },

    editProduct: function (product) {
        var conentWrapEl = $('#content-wrapper');
        var editFormEl = $('#edit-form');

        var nameEl = $("#name");
        var descriptionEl = $("#description");
        var compositionEl = $("#composition");
        var typesSelectorEl = $("#types-selector");
        var description = this.getDescriptionByLeng(product.descriptions, CURRENT_LANGUAGE);

        nameEl.val(product.name);
        typesSelectorEl.val(product.productType.typeName);
        descriptionEl.val(description.description);
        compositionEl.val(description.composition);

        conentWrapEl.attr('hidden', true);
        editFormEl.attr('hidden', false);

        $('#backToProduct').click(function () {
            conentWrapEl.attr('hidden', false);
            editFormEl.attr('hidden', true);
        });
    },

    sendProductData: function (productId) {
        var url = productId ? SERVER_CONTEXT + "/admin/content/product/" + productId : SERVER_CONTEXT + "/admin/content/product/",
            type= productId ? "PUT" : "POST";

        ajaxBuilder(url, type, {}, function () {
            
        }, function () {
            
        })
    },

    getAllProductTypes: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product/type";
        ajaxBuilder(url, "GET", {}, function (response) {
            var typesSelect = $('#types-selector');
            for (var i = 0; i < response.length; i++) {
                var type = response[i];
                var option = $('<option value="' + type.typeName + '">' + type.typeName + '</option>');
                option.appendTo(typesSelect);
            }
        }, function (error) {
            alert(error.error);
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