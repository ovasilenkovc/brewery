var CURRENT_LANGUAGE = "UA";
var privateConstants = function () {
    var server_context = "/brewery";
    var lang_mapping = {
        "en": "ENG",
        "ua": "UA",
        "ru": "RUS"
    };

    var iconMapping = {
        "facebook": "fa fa-facebook-square",
        "instagram": "fa fa-instagram",
        "youtube": "fa fa-youtube-square",
        "twitter": "fa fa-twitter-square",
        "VK": "fa fa-vk"
    };

    var unknown_titles = {
        "ENG": "Unnamed Beer variety",
        "RUS": "Неназванный сорт пива",
        "UA": "Неназваний сорт пива"
    };

    return {
        SERVER_CONTEXT: server_context,
        LANG_MAPPING: lang_mapping,
        UNKNOWN_TITLES: unknown_titles,
        ICONS_MAPPING: iconMapping
    }
};

var functionality = {

    init: function init() {
        var self = this;
        this.products = [];
        this.productTypes = [];
        this.utils = new Utils();
        this.authenticated = ($("#authenticated").attr("value") === "true");

        CURRENT_LANGUAGE = privateConstants().LANG_MAPPING[$('#localization').attr('value')];

        //content initiation
        this.getAllProductTypes();
        this.getProducts();
        this.getImages();
        this.renderArticles();
        this.getContacts();

        //elements global binding
        $("#sendProductData").click(function (ev) {
            ev.preventDefault();
            var product = $(this).data();
            self.sendProductData(product);
        });

        $("#asortiment-nav").find(".addNew").click(function () {
            var product = self.utils.makeProductObj(CURRENT_LANGUAGE);
            self.utils.resetFormData($("#edit-form"));
            self.editProduct(product);
            self.utils.initPopup($('#send-form-popup'));
        });

        $('#addNewImg').click(function () {
            self.utils.initPopup($('#save-img-popup'));
        });

        $('#addNewType').click(function () {
            self.utils.initPopup($('#save-type-popup'));
        });

        $(".error").click(function () {
            $(this).attr("hidden", true);
        });

        $('#history-lang-selector').change(function () {
            var currentLang = $(this).val();
            var form = $(this).closest('form');
            var history = form.find("#sendHistoryData").data();
            self.viewHistory(history, currentLang);
        });

        $('#typesManage').click(function () {
            self.utils.initPopup($('#manage-type-popup'));
            var typesListEl = $("#types");
            typesListEl.html("");

            for (var i in self.productTypes) {
                var type = self.productTypes[i];
                var listEl = $("<li class='list-group-item types-item'>");
                var typeEl = $('<div class="type-title">' + type.typeName + '</div>' +
                    '<div class="remove-type-el"><span class="fa fa-minus" aria-hidden="true"></span></div>').val(type);
                typeEl.appendTo(listEl);
                listEl.appendTo(typesListEl);
            }

            $('.remove-type-el').click(function () {
                self.removeProductType(this)
            });
        });

        $("#editContacts").click(function () {
            var contactsForm = $("#contacts-edit-form");
            var contactsText = $("#contacts-text");
            contactsForm.attr('hidden', !contactsForm.attr('hidden'));
            contactsText.attr('hidden') ? contactsText.attr('hidden', !contactsText.attr('hidden')) : contactsText.attr('hidden', true);
            if (!contactsForm.attr('hidden')) self.renderContactEditForm()
        });

        $("#saveContacts").click(function () {
            self.saveContacts();
        });

        $("#addChannel").click(function () {
            var channels = $(this).attr("data").split(",");
            self.addNewChannelEditRow(channels, channels.length, $('.channels').find(".clist"));
        });

        $('#editHistory').click(function () {
            var historyEditForm = $('#history-edit-form'),
                historyInfo = $(".our_history_desc").find('.history-info');

            $('#history-lang-selector').val(CURRENT_LANGUAGE);
            historyInfo.attr('hidden', !historyInfo.attr('hidden'));
            historyEditForm.attr("hidden", !historyEditForm.attr("hidden"));
        });

        $('#addHistory-info').click(function () {
            var historyEditForm = $('#history-edit-form'),
                historyEditFormBtn = $('#sendHistoryData'),
                historyInfo = $(".our_history_desc").find('.history-info');

            historyInfo.attr('hidden', !historyInfo.attr('hidden'));
            historyEditForm.attr("hidden", !historyEditForm.attr("hidden"));
            historyEditFormBtn.data(self.utils.makeHistoryObj(CURRENT_LANGUAGE));
        });

        $('#sendHistoryData').click(function () {
            var history = $(this).data();
            self.saveUpdateHistory(history)
        });

        $("#desc-lang-selector").change(function () {
            var currentLang = $(this).val();
            var form = $(this).closest('form');
            var product = form.find("#sendProductData").data();
            self.productLangSwitcher(product, currentLang)
        });

        //Beer description content managing part
        var $titleEl = $("#title"),
            $compositionEl = $("#composition"),
            $descriptionEl = $("#description");

        $titleEl.focusout(function () {
            var alert = $(this).parent().find('.alert');
            $(this).val() === "" ? alert.attr("hidden", false) : alert.attr("hidden", true);
        });

        //dynamically saving typed input values
        $titleEl.change($.proxy(self.utils.setProductDescChanges, self, $titleEl));
        $descriptionEl.change($.proxy(self.utils.setProductDescChanges, self, $descriptionEl));
        $compositionEl.change($.proxy(self.utils.setProductDescChanges, self, $compositionEl));

        $('#history-text').change(function () {
            var translation = $(this).data(), language = $(this).attr("language");
            var currentTranslation = self.utils.getListItemByParameter(translation.translations, "type", language);
            currentTranslation.translation = $(this).val();
        });

    },

    getProducts: function () {
        var self = this;
        var url = privateConstants().SERVER_CONTEXT + "/content/product";
        this.utils.ajaxDataSender(url, 'GET', {}, function (response) {
            var productsBlock = $('.selection-of-beer');
            productsBlock.html("");
            self.products = response;
            $.each(self.products, function (i, item) {
                self.renderProducts(productsBlock, item, CURRENT_LANGUAGE);
            });
        }, function (response) {
            self.utils.showErrorMessage(response);
        })
    },

    getAllProductTypes: function () {
        var self = this;
        this.productTypes = [];
        var url = privateConstants().SERVER_CONTEXT + "/content/product/type";
        var typesSelect = $('#types-selector');
        this.utils.ajaxDataSender(url, "GET", {}, function (response) {
            self.productTypes = response;
            typesSelect.html("");
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
            $('#errorText').text(error.responseJSON.error);
            self.utils.initPopup($("#error-dialog"));
        })
    },

    renderProducts: function (parent, product, local) {
        var self = this;
        var productId = product.productId;
        var productType = product.productType;
        var productDescription = this.utils.getListItemByParameter(product.descriptions, "type", local);
        var title = productDescription ? productDescription.title : privateConstants().UNKNOWN_TITLES[CURRENT_LANGUAGE];
        var productDivWrapEl = $('<div class="product-wrapper">');

        if (this.authenticated) {
            var productToolEl = $('<div class="product-tools">');
            var editBtn = $('<span class="fa fa-pencil-square-o fa-3x edit-product" />');
            var removeBtn = $('<span class="fa fa-minus-square-o fa-3x remove-product" />');
            editBtn.data(product).appendTo(productToolEl);
            removeBtn.data(product).appendTo(productToolEl);
            productToolEl.appendTo(productDivWrapEl);

            editBtn.click(function () {
                self.utils.initPopup($("#send-form-popup"));
                self.editProduct($(this).data());
            });

            removeBtn.click(function () {
                var product = $(this).data();
                self.removeProduct(product);
            });
        }
        var productDivEl = $('<div class="position-of-beer" id="#show_popup" value="' + productId + '" data-toggle="modal" data-target="#productModal">'
            + '<div class="beer-img"><span><img src="' + productType.iconPath + '"></span></div>'
            + '<div class="romb-img"><div class="romb-text"><span>' + title + '</span></div>'
            + '<img src="/brewery/img/romb-bg.png"></div></div>');
        productDivEl.data(product).appendTo(productDivWrapEl);

        productDivEl.click(function () {
            self.utils.initPopup($('#popup'));
            self.viewOneProduct($(this).data());
        });

        productDivWrapEl.appendTo(parent);
    },

    viewOneProduct: function (product) {
        if (product) {
            var descriptions = product.descriptions,
                productType = product.productType,
                popupDescComp = $('#popup').find('.composition'),
                popupDesc = $('#popup').find('.first-paragraph'),
                popupProdName = $('#popup').find('.product-name'),
                popupProdImage = $('#popup').find('.product-logo-img');

            popupProdImage.attr("src", productType.iconPath);
            var productDescription = this.utils.getListItemByParameter(descriptions, "type", CURRENT_LANGUAGE);

            popupProdName.text(productDescription && productDescription.title ? productDescription.title : product.name);
            popupDesc.text(productDescription && productDescription.description ? productDescription.description : "");
            popupDescComp.text(productDescription && productDescription.composition ? productDescription.composition : "");
        }
    },

    editProduct: function (product) {
        var titleEl = $("#title"),
            descriptionEl = $("#description"),
            compositionEl = $("#composition"),
            description = this.utils.getListItemByParameter(product.descriptions, "type", CURRENT_LANGUAGE)

        $("#name").val(product.name);
        $("#desc-lang-selector").val(CURRENT_LANGUAGE);

        titleEl.data(product).attr("language", CURRENT_LANGUAGE);
        descriptionEl.data(product).attr("language", CURRENT_LANGUAGE);
        compositionEl.data(product).attr("language", CURRENT_LANGUAGE);

        $("#types-selector").val(product.productType.typeName).attr("selected", true);
        $("#send-form-popup").find(".product-logo-img").attr('src', product.productType.iconPath);

        descriptionEl.val(description && description.description ? description.description : "");
        compositionEl.val(description && description.composition ? description.composition : "");
        titleEl.val(description && description.title ? description.title : privateConstants().UNKNOWN_TITLES[CURRENT_LANGUAGE]);

        $("#sendProductData").data(product);
    },

    productLangSwitcher: function (product, language) {
        var titleEl = $("#title"),
            descriptionEl = $("#description"),
            compositionEl = $("#composition"),
            currentDescription = this.utils.getListItemByParameter(product.descriptions, "type", language);

        if (currentDescription === null) {
            currentDescription = this.utils.makeProductDEscriptionObj(language);
            product.descriptions.push(currentDescription);
        }

        titleEl.data(product).attr("language", language);
        descriptionEl.data(product).attr("language", language);
        compositionEl.data(product).attr("language", language);
        descriptionEl.val(currentDescription && currentDescription.description ? currentDescription.description : "");
        compositionEl.val(currentDescription && currentDescription.composition ? currentDescription.composition : "");
        titleEl.val(currentDescription && currentDescription.title ? currentDescription.title : "");
    },

    sendProductData: function (product) {
        var productId = product.productId, self = this;
        var serverContext = privateConstants().SERVER_CONTEXT;
        var url = productId ? serverContext + "/admin/content/product/" + productId : serverContext + "/admin/content/product/";
        var type = productId ? "PUT" : "POST";
        type === "POST" ? product['name'] = this.utils.nameGenerator() : $("#name").val();
        product['productType'] = this.utils.getListItemByParameter(this.productTypes, "typeName", $("#types-selector").val());

        this.utils.ajaxDataSender(url, type, product, function () {
            self.getProducts();
            self.utils.resetFormData($("#edit-form"));
            self.utils.popupDisable($('#send-form-popup'));
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    removeProduct: function (product) {
        var self = this;
        var url = privateConstants().SERVER_CONTEXT + "/admin/content/product/" + product.productId;
        this.utils.ajaxDataSender(url, "DELETE", {}, function () {
            self.getProducts();
        }, function (error) {
            self.utils.showErrorMessage(error);
        })
    },

    saveProductType: function () {
        var self = this,
            errorJson = {},
            typeName = $("#type-name").val(),
            files = $('#type-icon')[0].files,
            url = privateConstants().SERVER_CONTEXT + "/admin/content/product/type";

        errorJson["status"] = "";
        errorJson["responseJSON"] = {};
        if (typeName !== "" && (files.length !== 0)) {
            var file = files[0];

            if (!!file.type.match(/image.*/)) {

                this.utils.ajaxFilesSender(url, "IMAGES", files, function () {
                    self.getAllProductTypes();
                    self.utils.popupDisable($('#save-type-popup'));
                    self.utils.resetFormData($("#beer-type-form"));
                }, function (error) {
                    self.utils.showErrorMessage(error);
                }, {"typeName": typeName});

            } else {
                errorJson.responseJSON["error"] = "The icon file should be an image!";
                self.utils.showErrorMessage(errorJson);
            }
        } else {
            errorJson.responseJSON["error"] = "Type name and type icon are required parameters!";
            self.utils.showErrorMessage(errorJson);
        }
    },

    removeProductType: function (element) {
        var type = $(element).val(), self = this;
        var url = privateConstants().SERVER_CONTEXT + "/admin/content/product/type/" + type.typeName;
        this.utils.ajaxDataSender(url, "DELETE", {}, function () {
            $(element).parent().remove();
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    uploadImages: function () {
        var self = this;
        var files = $('#file')[0].files;
        var url = privateConstants().SERVER_CONTEXT + "/admin/content/files/pictures";
        this.utils.ajaxFilesSender(url, "IMAGES", files, function () {
            self.utils.popupDisable($('#save-img-popup'));
            $('#brewery').find('.carousel').html("");
            self.utils.turnSlickOff();
            self.getImages();
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    getImages: function () {
        var self = this;
        var url = privateConstants().SERVER_CONTEXT + "/content/files/pictures";
        this.utils.ajaxDataSender(url, "GET", {}, function (response) {
            var carousel = $('#brewery').find('.carousel');
            $.each(response, function (key, value) {
                var img = $("<div><div class='carousel-img'>" +
                    "<span class='fa fa-minus-square-o fa-3x remove-image' title='remove image' value='" + this.id + "' hidden/>" +
                    "<img src='" + this.base64encodeString + "' alt='картинка слайда'></div></div>");

                self.authenticated ? img.find(".remove-image").attr("hidden", false) : img.find(".remove-image").attr("hidden", true);

                img.find(".remove-image").click(function () {
                    var id = $(this).attr('value');
                    functionality.removeImg(id);
                });
                img.appendTo(carousel);
            });
            self.utils.initSlick();
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    removeImg: function (id) {
        var self = this;
        var url = privateConstants().SERVER_CONTEXT + "/admin/content/files/" + id;
        this.utils.ajaxDataSender(url, "DELETE", {}, function () {
            self.utils.turnSlickOff();
            $('#brewery').find('.carousel').html("");
            self.getImages();
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    renderArticles: function () {
        var self = this;
        var url = privateConstants().SERVER_CONTEXT + "/content/article";
        this.utils.ajaxDataSender(url, "GET", {}, function (response) {
            var historyArticle = self.utils.getListItemByParameter(response, "title", "history");
            if (historyArticle) {
                $('#editHistory').attr("hidden", false);
                self.viewHistory(historyArticle, CURRENT_LANGUAGE)
            } else {
                $('#addHistory-info').attr("hidden", false);
            }
        }, function (error) {
            self.utils.showErrorMessage(error);
        })
    },

    viewHistory: function (history, language) {

        var historyText = $('#history-text'),
            historyInfo = $(".our_history_desc").find('.history-info'),
            currentLangDesc = this.utils.getListItemByParameter(history.translations, "type", language);

        if (currentLangDesc === null) {
            currentLangDesc = this.utils.makeHistoryTranslationObj(language);
            history.translations.push(currentLangDesc);
        }

        historyText.data(history);
        historyText.attr("language", language);
        historyText.val(currentLangDesc ? currentLangDesc.translation : "");
        historyInfo.html(currentLangDesc ? currentLangDesc.translation : "");

        $('#sendHistoryData').data(history);
    },

    saveUpdateHistory: function (article) {
        var article_id = article.article_id, self = this,
            historyEditForm = $('#history-edit-form'),
            historyInfo = $(".our_history_desc").find('.history-info'),

            serverContext = privateConstants().SERVER_CONTEXT,
            url = article_id ? serverContext + "/admin/content/article/" + article_id : serverContext + "/admin/content/article/",
            type = article_id ? "PUT" : "POST";

        this.utils.ajaxDataSender(url, type, article, function () {
            self.renderArticles();
            historyInfo.attr('hidden', !historyInfo.attr('hidden'));
            historyEditForm.attr("hidden", !historyEditForm.attr("hidden"));
        }, function (error) {
            self.utils.showErrorMessage(error);
        });
    },

    getContacts: function () {
        var self = this, url = privateConstants().SERVER_CONTEXT + "/content/contacts";
        this.utils.ajaxDataSender(url, "GET", {}, function (response) {
            var contactsText = $("#contacts-text");
            contactsText.find(".adress-text").text(response["address"]);
            $(".email-text").text(response["email"]);
            $(".phone-text").text(response["phones"]);
            $("#channelsStr").val(response["channels"]);

            var channels = response["channels"].split(",");
            var contactsWrpEl = $("#channels");

            contactsWrpEl.html("");
            for (var i = 0; i < channels.length; i++) {
                var channel = channels[i];
                var socialVar = channel.substring(channel.indexOf("[") + 1, channel.indexOf("]")),
                    chanelVal = channel.replace("[" + socialVar + "]", "").trim(),
                    iconClass = privateConstants().ICONS_MAPPING[socialVar],
                    socialElStyle = socialVar == "youtube" ? "youtube-element" : "social-element";
                var channelLink = $("<a class='" + socialElStyle + "' href='" + chanelVal + "' target='_blank'><span class='" + iconClass + " fa-5x' aria-hidden='true'></span></a>");
                channelLink.appendTo(contactsWrpEl);
            }
        }, function (error) {
            self.utils.showErrorMessage(error);
        })
    },

    renderContactEditForm: function () {
        var contactsText = $("#contacts-text");
        var channelsEditBlock = $('.channels').find(".clist");

        var addressText = contactsText.find(".adress-text").text().trim();
        $("#contact-address").val(addressText);

        var emailText = contactsText.find(".email-text").text().trim();
        $("#contact-email").val(emailText);

        var phoneText = contactsText.find(".phone-text").text().trim();
        $("#contact-phone").val(phoneText);

        var channels = $("#channelsStr").val().split(",");
        $("#addChannel").attr("data", channels);

        channelsEditBlock.html("");
        for (var i = 0; i < channels.length; i++) {
            this.addNewChannelEditRow(channels, i, channelsEditBlock);
        }
    },

    saveContacts: function () {
        var self = this, url = privateConstants().SERVER_CONTEXT + "/admin/content/contacts";
        var contactJson = {
            "email": $("#contact-email").val(),
            "phones": $("#contact-phone").val(),
            "address": $("#contact-address").val(),
            "channels": (function () {
                var channelsString = "",
                    channelsArray = $('.channels').find("input:text");

                for (var i = 0; i < channelsArray.length; i++) {
                    var socialVal = "[" + $("#social" + i + "").val() + "]";
                    channelsString += i < channelsArray.length - 1 ? socialVal + " " + channelsArray[i].value + "," : socialVal + " " + channelsArray[i].value;
                }
                return channelsString;
            })()
        };
        this.utils.ajaxDataSender(url, "POST", contactJson, function () {
            self.getContacts();
            $("#contacts-edit-form").attr('hidden', true);
            $("#contacts-text").attr('hidden', false);
        }, function (error) {
            self.utils.showErrorMessage(error);
        });

    },

    addNewChannelEditRow: function (array, index, parentEl) {
        var channel = array[index] ? array[index].trim() : "",
            socialVar = channel.substring(channel.indexOf("[") + 1, channel.indexOf("]")),
            chanelVal = channel.replace("[" + socialVar + "]", "").trim(),
            blockWrap = $("<div class='row channels-wrapper'>"),
            selectedVal = socialVar != "" && socialVar !== "undefined" ? socialVar : "youtube",
            input = $("<input class='form-control col-md-9'>").val(chanelVal);

        var select = $("<select class='form-control col-md-2' id='social" + index + "'>" +
            "<option value='facebook'>facebook</option>" +
            "<option value='youtube'>youtube</option>" +
            "<option value='twitter'>twitter</option>" +
            "<option value='instagram'>instagram</option>" +
            "<option value='VK'>VK</option></select>");

        select.val(selectedVal);
        var removeBtn = $('<span class="fa fa-minus-square-o fa-3x  col-md-1 remove-channel" title="remove this channel" data="' + index + '">');

        removeBtn.click(function () {
            var id = $(this).attr("data");
            array.slice(id, 1);
            $(this).parent().remove();
        });

        select.appendTo(blockWrap);
        input.appendTo(blockWrap);
        removeBtn.appendTo(blockWrap);
        blockWrap.appendTo(parentEl)
    }
};

$(document).ready(function () {
    localStorage.setItem('token', $("#token").val());
    functionality.init();
});
// $(document).bind("contextmenu", function (event) {
//     event.preventDefault();
// });
// $(window).bind('mousewheel DOMMouseScroll', function (event) {
//     if (event.ctrlKey == true) {
//         event.preventDefault();
//     }
// });
