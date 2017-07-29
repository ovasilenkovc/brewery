var SESSION_TOKEN = "Bearer ",
    CURRENT_LANGUAGE = "UA",
    SERVER_CONTEXT = "/brewery",
    LANG_MAPPING = {
        "en": "ENG",
        "ua": "UA",
        "ru": "RUS"
    };

var functionality = {

    init: function init() {
        var self = this;
        this.utils = new Utils();
        SESSION_TOKEN += localStorage.getItem("token");
        //using elements initialization
        this.userNameEl = $("#username");
        this.userPasswordEl = $("#password");
        this.loginErorEl = $("#login-error");
        this.carousel = $('#brewery .carousel');

        //edit product form elements init
        this.nameEl = $("#name");
        this.formEl = $("#edit-form");
        this.descriptionEl = $("#description");
        this.compositionEl = $("#composition");
        this.typesSelectorEl = $("#types-selector");
        this.sendProductDataEl = $("#sendProductData");
        this.addToolBtnEl = $("#asortiment-nav .addNew");

        //view popup elements init
        this.popupDesc = $('#popup .first-paragraph');
        this.popupDescComp = $('#popup .composition');
        this.popupProdName = $('#popup .product-name');
        this.popupProdImage = $('#popup .product-logo-img');

        this.products = [];
        this.productTypes = [];
        this.historyEl = $(".our_history_desc");
        this.historyEditForm = $('#history-edit-form');
        this.historyEditFormBtn = $('#sendHistoryData');
        this.historyInfo = self.historyEl.find('.history-info');
        CURRENT_LANGUAGE = LANG_MAPPING[$('#localization').attr('value')];

        //content initiation
        this.getAllProductTypes();
        this.getProducts();
        this.getImages();
        this.renderArticles();

        //elements global binding
        this.sendProductDataEl.click(function () {
            var product = $(this).data();
            self.sendProductData(product);
        });

        this.addToolBtnEl.click(function () {
            var product = {
                "name": "",
                "productType": {},
                "descriptions": [{
                    "composition": "",
                    "description": "",
                    "title": "",
                    "type": CURRENT_LANGUAGE
                }]
            };
            self.utils.resetFormData(self.formEl);
            self.sendProductDataEl.data(product);
            self.utils.initPopup($('#send-form-popup'));
        });

        $('#addNewImg').click(function () {
            self.utils.initPopup($('#save-img-popup'));
        });

        $('#editHistory').click(function () {
            self.historyInfo.attr('hidden', !self.historyInfo.attr('hidden'));
            self.historyEditForm.attr("hidden", !self.historyEditForm.attr("hidden"));
        });

        this.historyEditFormBtn.click(function () {
            var history = $(this).data();
            self.saveUpdateHistory(history)
        })
    },

    login: function () {
        var url = SERVER_CONTEXT + "/login";
        this.utils.ajaxDataBuilder(url, "POST", {
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
        this.utils.ajaxDataBuilder(url, "DELETE", {},
            function () {
                localStorage.clear()
            });
    },

    getProducts: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product";
        this.utils.ajaxDataBuilder(url, 'GET', {}, function (response) {
            var productsBlock = $('.selection-of-beer');
            productsBlock.html("");
            self.products = response;
            $.each(self.products, function (i, item) {
                self.renderProducts(productsBlock, item, CURRENT_LANGUAGE);
            });
        }, function (response) {
            alert(response.error);
        })
    },

    getAllProductTypes: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/product/type";
        var typesSelect = $('#types-selector');
        this.utils.ajaxDataBuilder(url, "GET", {}, function (response) {
            self.productTypes = response;
            for (var i = 0; i < response.length; i++) {
                var type = response[i];
                var option = $('<option value="' + type.typeName + '">' + type.typeName + '</option>');
                option.appendTo(typesSelect);
            }
            typesSelect.change(function () {
                var selectedType = $(this).val();
                var type = self.utils.getListItemByParameter(self.productTypes, "typeName", selectedType);
                $('.window-img .product-logo-img').attr('src', type.iconPath);
            })
        }, function (error) {
            alert(error.error);
        })
    },

    renderProducts: function (parent, product, local) {
        var self = this;
        var productId = product.productId;
        var productType = product.productType;
        var productDescription = this.utils.getListItemByParameter(product.descriptions, "type", local);
        var title = productDescription ? productDescription.title : product.name;

        var productDivWrapEl = $('<div class="product-wrapper">');
        var productToolEl = $('<div class="product-tools">');
        var editBtn = $('<span class="fa fa-pencil-square-o fa-3x edit-product" />');
        var removeBtn = $('<span class="fa fa-minus-square-o fa-3x remove-product" />');
        editBtn.data(product).appendTo(productToolEl);
        removeBtn.data(product).appendTo(productToolEl);
        productToolEl.appendTo(productDivWrapEl);

        var productDivEl = $('<div class="position-of-beer" id="#show_popup" value="' + productId + '" data-toggle="modal" data-target="#productModal">'
            + '<div class="beer-img"><span><img src="' + productType.iconPath + '"></span></div>'
            + '<div class="romb-img"><div class="romb-text"><span>' + title + '</span></div>'
            + '<img src="/brewery/img/romb-bg.png"></div></div>');
        productDivEl.data(product).appendTo(productDivWrapEl);

        productDivEl.click(function () {
            self.utils.initPopup($('#popup'));
            self.viewOneProduct($(this).data());
        });

        editBtn.click(function () {
            self.utils.initPopup( $("#send-form-popup"));
            self.editProduct($(this).data());
        });

        removeBtn.click(function () {
            var product = $(this).data();
            self.removeProduct(product);
        });

        productDivWrapEl.appendTo(parent);
    },

    viewOneProduct: function (product) {
        var self = this;
        if (product) {
            var descriptions = product.descriptions;
            var productType = product.productType;
            self.popupProdImage.attr("src", productType.iconPath);
            var productDescription = this.utils.getListItemByParameter(descriptions, "type", CURRENT_LANGUAGE);
            debugger;
            self.popupProdName.text(productDescription && productDescription.title ? productDescription.title : product.name);
            self.popupDesc.text(productDescription && productDescription.description ? productDescription.description : "");
            self.popupDescComp.text(productDescription && productDescription.composition ?productDescription.composition: "");
        }
    },

    editProduct: function (product) {
        var description = this.utils.getListItemByParameter(product.descriptions, "type", CURRENT_LANGUAGE);
        this.typesSelectorEl.val(product.productType.typeName).attr("selected", true);
        $("#send-form-popup .product-logo-img").attr('src', product.productType.iconPath);
        this.descriptionEl.val(description && description.description ? description.description : "");
        this.compositionEl.val(description && description.composition ? description.composition : "");
        this.nameEl.val(description && description.title ? description.title : product.name);
        this.sendProductDataEl.data(product);
    },

    sendProductData: function (product) {
        var productId = product.productId, self = this;
        var url = productId ? SERVER_CONTEXT + "/admin/content/product/" + productId : SERVER_CONTEXT + "/admin/content/product/";
        var type = productId ? "PUT" : "POST";
        // product['name'] = product.name || product.name === "" ? this.nameEl.val() : product.name;
        product['productType'] = this.utils.getListItemByParameter(this.productTypes, "typeName", this.typesSelectorEl.val());
        this.utils.updateProductTextMetaData(product.descriptions, "title", this.nameEl);
        this.utils.updateProductTextMetaData(product.descriptions, "description", this.descriptionEl);
        this.utils.updateProductTextMetaData(product.descriptions, "composition", this.compositionEl);

        this.utils.ajaxDataBuilder(url, type, product, function () {
            self.getProducts();
            self.utils.resetFormData(self.formEl);
            self.utils.popupDisable($('#send-form-popup'));
        }, function (error) {
            //to do
            $('#product-send-error').text(error);
        }, SESSION_TOKEN)
    },

    removeProduct: function (product) {
        var self = this;
        var url = SERVER_CONTEXT + "/admin/content/product/" + product.productId;
        this.utils.ajaxDataBuilder(url, "DELETE", {}, function () {
            self.getProducts();
        }, function (error) {
            $('#product-send-error').text(error);
        }, SESSION_TOKEN)
    },

    uploadImages: function () {
        var self = this;
        var files = $('#file')[0].files;
        var url = SERVER_CONTEXT + "/admin/content/files/pictures";
        this.utils.ajaxFilesSender(url, "IMAGES", files, function () {
            self.utils.popupDisable($('#save-img-popup'));
            self.carousel.html("");
            self.utils.turnSlickOff();
            self.getImages();
        }, function (error) {
        }, SESSION_TOKEN);
    },

    getImages: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/files/pictures";
        this.utils.ajaxDataBuilder(url, "GET", {}, function (response) {
            $.each(response, function (key, value) {
                var img = $("<div><div class='carousel-img'>" +
                    "<span class='fa fa-minus-square-o fa-3x remove-image' title='remove image' value='" + this.id + "'/>" +
                    "<img src='" + this.base64encodeString + "' alt='картинка слайда'></div></div>");

                img.find(".remove-image").click(function () {
                    var id = $(this).attr('value');
                    functionality.removeImg(id);
                });
                img.appendTo(functionality.carousel);
            });
            self.utils.initSlick();
        });
    },

    removeImg: function (id) {
        var self = this;
        var url = SERVER_CONTEXT + "/admin/content/files/" + id;
        this.utils.ajaxDataBuilder(url, "DELETE", {}, function (response) {
            self.utils.turnSlickOff();
            $('#brewery .carousel').html("");
            self.getImages();
        },function (error) {
        }, SESSION_TOKEN);
    },

    renderArticles: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/article";
        this.utils.ajaxDataBuilder(url, "GET", {}, function (response) {
            var historyArticle = self.utils.getListItemByParameter(response, "title", "history");
            var historyToolBar = self.historyEl.find('.toolbar');
            var historyEditFormText = $('#history-text');
            if(historyArticle){
                var currentLangDesc = self.utils.getListItemByParameter(historyArticle.translations, "type", CURRENT_LANGUAGE);
                self.historyInfo.text(currentLangDesc.translation);
                historyEditFormText.val(currentLangDesc.translation);
                self.historyEditFormBtn.data(historyArticle);
            }else {
                var addButton = $("<span class='fa fa-plus-square-o fa-3x add-history' title='remove article' />");
                addButton.click(function () {
                    self.historyInfo.attr('hidden', !self.historyInfo.attr('hidden'));
                    self.historyEditForm.attr("hidden", !self.historyEditForm.attr("hidden"));
                    self.historyEditFormBtn.data({
                        "title": "history",
                        "date": "",
                        "translations": [{
                        "title":"",
                        "translation":"",
                        "type": CURRENT_LANGUAGE
                    }]});
                });
                addButton.appendTo(historyToolBar);
            }
        }, function (error) {})
    },

    saveUpdateHistory: function (article) {
        debugger;
        var article_id = article.article_id, self = this;
        var url = article_id ? SERVER_CONTEXT + "/admin/content/article/" + article_id : SERVER_CONTEXT + "/admin/content/article/";
        var type = article_id ? "PUT" : "POST";
        this.utils.updateProductTextMetaData(article.translations, "translation", $('#history-text'));
        this.utils.ajaxDataBuilder(url, type, article, function () {
            self.renderArticles();
            self.historyInfo.attr('hidden', !self.historyInfo.attr('hidden'));
            self.historyEditForm.attr("hidden", !self.historyEditForm.attr("hidden"));
        },function () {
        }, SESSION_TOKEN);
    }
};

$(document).ready(function () {
    functionality.init();
});