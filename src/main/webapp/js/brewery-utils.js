function Utils() {
    return {
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

        initPopup: function (popupEl) {
            popupEl.bPopup({
                modalClose: true,
                opacity: 0.6,
                positionStyle: 'fixed'
            })
        },

        popupDisable: function (popupEl) {
            popupEl.bPopup().close();
        },

        resetFormData: function (formEl) {
            formEl.find('input:text, input:password, input:file, select, textarea').val('');
            formEl.find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
        },

        initSlick: function () {
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
        },

        makeProductDEscriptionObj: function (type) {
            return {
                "title": "",
                "type": type,
                "description": "",
                "composition": ""
            }
        },

        makeHistoryObj: function (language) {
            return {
                "title": "history",
                "date": "",
                "translations": [{
                    "title": "",
                    "translation": "",
                    "type": language
                }]
            }
        },

        makeHistoryTranslationObj: function (language) {
            return {
                "title": "",
                "translation": "",
                "type": language
            }
        },

        makeProductObj: function (language) {
            return {
                "name": "",
                "productType": {},
                "descriptions": [{
                    "composition": "",
                    "description": "",
                    "title": "",
                    "type": language
                }]
            };
        },

        setProductDescChanges: function (el) {
            var product = $(el).data(), language = $(el).attr("language"), keyName = $(el).attr("name");
            var currentDescription = this.utils.getListItemByParameter(product.descriptions, "type", language);
            if (!currentDescription) {
                currentDescription = {
                    "title": "",
                    "description": "",
                    "composition": "",
                    "type": CURRENT_LANGUAGE
                };
                product.descriptions.push(currentDescription);
            }
            currentDescription[keyName] = $(el).val();
        },

        ajaxDataSender: function (url, type, content, success, reject) {
            var ajaxConfig = {
                url: url,
                type: type,
                cache: false,
                dataType: 'json',
                contentType: "application/json",
                success: success,
                error: reject
            };
            Object.keys(content).length !== 0 ? ajaxConfig.data = JSON.stringify(content) : ajaxConfig;
            $.ajax(ajaxConfig)
        },

        ajaxFilesSender: function (url, type, files, success, reject, additionParams) {
            var formData = new FormData();
            var dataTypeMatcher = type === "IMAGES" ? /image.*/ : /file.*/;

            if (additionParams && Object.keys(additionParams).length > 0) {
                for (var key in additionParams) {
                    formData.append(key, additionParams[key]);
                }
            }

            if (files.length !== 0) {
                $.each(files, function (i, item) {
                    if (!!item.type.match(dataTypeMatcher)) {
                        formData.append("files", item);
                    }
                });
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: success,
                    error: reject
                })
            }
        },

        showErrorMessage: function (errorObj) {
            var message = errorObj.status == 401 ? errorObj.statusText : errorObj.responseJSON.error;
            $('#errorText').text(message);
            this.initPopup($("#error-dialog"));
        },

        nameGenerator: function () {
            var text = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            for (var i = 0; i < 10; i++)
                text += possible.charAt(Math.floor(Math.random() * possible.length));

            return text;
        }

    }
}
