function Utils() {
    return {
        updateProductTextMetaData: function (list, paramName, element) {
            var item = this.getListItemByParameter(list, "type", CURRENT_LANGUAGE);
            if (item) {
                item[paramName] = element.val();
            } else {
                var descriptionObj = {
                    "description": "",
                    "composition": "",
                    "type": CURRENT_LANGUAGE
                };
                descriptionObj[paramName] = element.val();
                list.push(descriptionObj);
            }
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
            currentDescription[keyName] = $(el).val();
        },

        ajaxDataBuilder: function (url, type, content, success, reject, token) {
            var ajaxConfig = {
                url: url,
                type: type,
                cache: false,
                dataType: 'json',
                headers: {Authorization: token},
                contentType: "application/json",
                success: success,
                error: reject
            };
            Object.keys(content).length !== 0 ? ajaxConfig.data = JSON.stringify(content) : ajaxConfig;
            $.ajax(ajaxConfig)
        },

        ajaxFilesSender: function (url, type, files, success, reject, token) {
            var formData = new FormData();
            var dataTypeMatcher = type === "IMAGES" ? /image.*/ : /file.*/;
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
                    headers: {Authorization: token},
                    success: success,
                    error: reject
                })
            }
        }
    }
}
