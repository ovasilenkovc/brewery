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
        CURRENT_LANGUAGE = LANG_MAPPING[$('#localization').attr('value')];

        //content initiation
        this.getAllProductTypes();
        this.getProducts();
        this.getImages();

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
            self.resetFormData(self.formEl);
            self.sendProductDataEl.data(product);
            $('#send-form-popup').bPopup({
                modalClose: true,
                opacity: 0.6,
                positionStyle: 'fixed'
            });
        });

        $('#addNewImg').click(function () {
            $('#save-img-popup').bPopup({
                modalClose: true,
                opacity: 0.6,
                positionStyle: 'fixed'
            });
        });
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
        ajaxBuilder(url, "GET", {}, function (response) {
            self.productTypes = response;
            for (var i = 0; i < response.length; i++) {
                var type = response[i];
                var option = $('<option value="' + type.typeName + '">' + type.typeName + '</option>');
                option.appendTo(typesSelect);
            }
            typesSelect.change(function () {
                var selectedType = $(this).val();
                var type = self.getListItemByParameter(self.productTypes, "typeName", selectedType);
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
        var productDescription = this.getListItemByParameter(product.descriptions, "type", local);
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
            $('#popup').bPopup({
                modalClose: false,
                opacity: 0.6,
                positionStyle: 'fixed'
            });
            self.viewOneProduct($(this).data());
        });

        editBtn.click(function () {
            $("#send-form-popup").bPopup({
                modalClose: true,
                opacity: 0.6,
                positionStyle: 'fixed'
            });
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
            var productDescription = this.getListItemByParameter(descriptions, "type", CURRENT_LANGUAGE);
            var title = productDescription ? productDescription.title : product.name();
            self.popupProdName.text(title);
            self.popupDesc.text(productDescription.description);
            self.popupProdImage.attr("src", productType.iconPath);
            self.popupDescComp.text(productDescription.composition);
        }
    },

    editProduct: function (product) {
        var description = this.getListItemByParameter(product.descriptions, "type", CURRENT_LANGUAGE);
        this.typesSelectorEl.val(product.productType.typeName).attr("selected", true);
        $("#send-form-popup .product-logo-img").attr('src', product.productType.iconPath);
        this.descriptionEl.val(description.description);
        this.compositionEl.val(description.composition);
        this.nameEl.val(description.title);
        this.sendProductDataEl.data(product);
    },

    sendProductData: function (product) {
        var productId = product.productId, self = this;
        var url = productId ? SERVER_CONTEXT + "/admin/content/product/" + productId : SERVER_CONTEXT + "/admin/content/product/";
        var type = productId ? "PUT" : "POST";

        product['name'] = this.nameEl.val();
        product['productType'] = this.getListItemByParameter(this.productTypes, "typeName", this.typesSelectorEl.val());
        this.updateProductTextMetaData(product.descriptions, "title", this.nameEl);
        this.updateProductTextMetaData(product.descriptions, "description", this.descriptionEl);
        this.updateProductTextMetaData(product.descriptions, "composition", this.compositionEl);

        ajaxBuilder(url, type, product, function () {
            self.getProducts();
            self.resetFormData(self.formEl);
            $('#send-form-popup').bPopup().close();
        }, function (error) {
            //to do
            $('#product-send-error').text(error);
        })
    },

    removeProduct: function (product) {
        var self = this;
        var url = SERVER_CONTEXT + "/admin/content/product/" + product.productId;
        ajaxBuilder(url, "DELETE", {}, function () {
            self.getProducts();
        }, function (error) {
            $('#product-send-error').text(error);
        })
    },

    uploadImages: function () {
        var self = this;
        var formData = new FormData();
        var files = $('#file')[0].files;
        var url = SERVER_CONTEXT + "/admin/content/files/pictures";
        if (files.length !== 0) {
            $.each(files, function (i, item) {
                if (!!item.type.match(/image.*/)) {
                    formData.append("files", item);
                }
            });
            $.ajax({
                url: url,
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                headers: {Authorization: SESSION_TOKEN},
                success: function () {
                    self.carousel.html("");
                    self.turnSlickOff();
                    self.getImages();
                }
            })
        }
    },

    getImages: function () {
        var self = this;
        var url = SERVER_CONTEXT + "/content/files/pictures";
        ajaxBuilder(url, "GET", {}, function (response) {
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
            self.initSlick();
        });
    },

    removeImg: function (id) {
        var self = this;
        debugger;
        var url = SERVER_CONTEXT + "/admin/content/files/" + id;
        ajaxBuilder(url, "DELETE", {}, function (response) {
            self.carousel.html("");
            self.turnSlickOff();
            self.getImages();
        });
    },

    getListItemByParameter: function (list, pramName, paramValue) {
        var searchedItem = null;
        $.each(list, function (i, item) {
            var compareParameter = item[pramName] ? item[pramName] : null;
            if (compareParameter !== null && compareParameter == paramValue) {
                searchedItem = item;
                return false;
            }
        });
        return searchedItem;
    },

    updateProductTextMetaData: function (list, paramName, element) {
        $.each(list, function (i, item) {
            var itemType = item['type'];
            if (itemType == CURRENT_LANGUAGE) {
                item[paramName] = element.val();
                return false;
            }
        });
    },

    resetFormData: function (formEl) {
        formEl.find('input:text, input:password, input:file, select, textarea').val('');
        formEl.find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
    },

    initSlick: function(){
        $('.fade').slick({
            dots: false,
            infinite: true,
            autoplay: true,
            speed: 500,
            fade: true,
            cssEase: 'linear'
        });
    },

    turnSlickOff: function () {
        $('.fade').slick('unslick');
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