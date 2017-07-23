var $SESSION_TOKEN = "Bearer ",
    $CURRENT_LANGUAGE = "UA",
    $SERVER_CONTEXT = "/brewery";


var functionality = {

    init: function init() {
        this.userNameEl = $("#username");
        this.userPasswordEl = $("#password");
        this.loginErorEl = $("#login-error");
    },

    login: function () {
        var url = $SERVER_CONTEXT + "/login";
        ajaxBuilder(url, "POST", {
            "username": functionality.userNameEl.val(),
            "password": functionality.userPasswordEl.val()
        }, function (response) {
            localStorage.setItem('token', JSON.stringify(response.token));
            window.location.href = window.location.origin + $SERVER_CONTEXT;
        }, function () {
            functionality.loginErorEl.addClass("show")
        })
    },

    logout: function () {
        var url = $SERVER_CONTEXT + "/logout";
        ajaxBuilder(url, "DELETE", {},
            function () {
            localStorage.clear()
        });
    }

};

var ajaxBuilder = function (url, type, content, success, reject) {
    var ajaxConfig = {
        url: url,
        type: type,
        cache: false,
        dataType: 'json',
        headers: { Authorization: $SESSION_TOKEN },
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