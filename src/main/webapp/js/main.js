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
        var self = this;
        //using elements initialization
        this.userNameEl = $("#username");
        this.userPasswordEl = $("#password");
        this.loginErorEl = $("#login-error");

        //edit form elements init
        this.nameEl = $("#name");
        this.editFormEl = $('#edit-form');
        this.descriptionEl = $("#description");
        this.compositionEl = $("#composition");
        this.contentWrapEl = $('#content-wrapper');
        this.typesSelectorEl = $("#types-selector");

        //view popup elements init
        this.popupDesc = $('#popup .first-paragraph');
        this.popupDescComp = $('#popup .composition');
        this.popupProdName = $('#popup .product-name');
        this.popupProdImage = $('#popup .product-logo-img');

        this.products = [];
        this.productTypes = [];
        CURRENT_LANGUAGE = LANG_MAPPING[$('#localization').attr('value')];

        //content initiation
        this.getAllProductTypes();
        this.getProducts();

        $('#sendProductData').click(function () {
            self.sendProductData({});
        })
    },

    login: function () {
        var url = SERVER_CONTEXT + "/login";
        ajaxBuilder(url, "POST", {
            "username": functionality.userNameEl.val(),
            "password": functionality.userPasswordEl.val()
        }, function (response) {
            localStorage.setItem('token', response.token);
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
            var saveToolbarItem = $('#asortiment-nav .toolbar .addNew');
            self.products = response;
            $.each(self.products, function (i, item) {
                self.renderProducts(productsBlock, item, CURRENT_LANGUAGE);
            });
            saveToolbarItem.click(function () {
                $('#send-form-popup').bPopup({
                    modalClose: true,
                    opacity: 0.6,
                    positionStyle: 'fixed'
                });
            });
        }, function (response) {
            alert(response.error);
        })
    },

    renderProducts: function (parent, product, local) {
        var self = this;
        var productId = product.productId;
        var productType = product.productType;
        var productDescription = this.getListItemByParameter(product.descriptions, "type", local);
        var title = productDescription ? productDescription.title : product.name;
        var productDivEl = $('<div class="position-of-beer" id="#show_popup" value="' + productId + '" data-toggle="modal" data-target="#productModal">'
            + '<div class="beer-img"><span><img src="' + productType.iconPath + '"></span></div>'
            + '<div class="romb-img"><div class="romb-text"><span>' + title + '</span></div>'
            + '<img src="/brewery/img/romb-bg.png"></div></div>');

        productDivEl.click(function () {
            $('#popup').bPopup({
                modalClose: false,
                opacity: 0.6,
                positionStyle: 'fixed'
            });
            self.viewOneProduct(productDivEl);
        });
        productDivEl.appendTo(parent);
    },

    viewOneProduct: function (element) {
        var self = this, prodId = element.attr('value');

        var product = this.getListItemByParameter(this.products, "productId", prodId);
        if(product){
            var descriptions = product.descriptions;
            var productType = product.productType;
            var productDescription = this.getListItemByParameter(descriptions, "type", CURRENT_LANGUAGE);
            var title = productDescription ? productDescription.title : product.name();
            self.popupProdName.text(title);
            self.popupDesc.text(productDescription.description);
            self.popupProdImage.attr("src", productType.iconPath);
            self.popupDescComp.text(productDescription.composition);
            $("#edit-product").click(function () {
                self.editProduct(product)
            });
        }
    },

    editProduct: function (product) {
        var self = this;
        var description = this.getListItemByParameter(product.descriptions, "type", CURRENT_LANGUAGE);

        this.typesSelectorEl.val(product.productType.typeName).attr("selected", true);
        this.descriptionEl.val(description.description);
        this.compositionEl.val(description.composition);
        this.nameEl.val(description.title);

        this.contentWrapEl.attr('hidden', true);
        this.editFormEl.attr('hidden', false);

        $('#backToProduct').click(function () {
            self.contentWrapEl.attr('hidden', false);
            self.editFormEl.attr('hidden', true);
        });

        $('#sendProductData').click(function () {
            self.sendProductData(product);
        })
    },

    sendProductData: function (product) {
        var productId = product.productId, self = this;
        var url = productId ? SERVER_CONTEXT + "/admin/content/product/" + productId : SERVER_CONTEXT + "/admin/content/product/",
            type= productId ? "PUT" : "POST";
        SESSION_TOKEN += localStorage.getItem("token");

        product['name'] = $("#name").val();
        product['productType'] = this.getListItemByParameter(this.productTypes, "typeName", $("#types-selector").val());
        this.updateProductDescription(product);

        ajaxBuilder(url, type, product, function () {
            self.contentWrapEl.attr('hidden', false);
            self.editFormEl.attr('hidden', true);
            this.getAllProductTypes();
        }, function () {
            debugger;
        })
    },

    getListItemByParameter: function (list, pramName, paramValue) {
        var searchedItem = null;
        $.each(list, function (i, item) {
            var compareParameter = item[pramName] ? item[pramName]: null;
            if(compareParameter !== null && compareParameter == paramValue){
                searchedItem = item;
                return false;
            }
        });
        return searchedItem;
    },

    updateProductDescription: function (product) {
        for(var i = 0; i < product.descriptions.length; i++){
            var description = product.descriptions[i];
            if(description['type'] == CURRENT_LANGUAGE){
                description.description = $("#description").val();
                return;
            }
        }
    },

    getAllProductTypes: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product/type";
        var typesSelect = $('#types-selector');
        ajaxBuilder(url, "GET", {}, function (response) {
            self.productTypes = response;
            for (var i = 0; i < response.length; i++) {
                var type = response[i];
                var option = $('<option value="' + type.typeName + '">' + type.typeName + '</option>');
                option.appendTo(typesSelect);
            }
            typesSelect.change(function () {
                var choosedType = $(this).val();
                var type = self.getListItemByParameter(self.productTypes, "typeName", choosedType);
                $('.window-img .product-logo-img').attr('src', type.iconPath);
            })
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