/**
 * @fileOverview 数据请求、模板渲染工具库
 * @author       haiyong.zhang
 * @createTime   2014/9/11
 * @updateTime   2015/01/08
 * @version      1.1.1
 */

(function (self) {

    var baseUrl = '';

    var isApp;
    var loading;

    String.prototype.format = function () {
        for (var i = 0, val = this, len = arguments.length; i < len; i++)
            val = val.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
        return val;
    };

    function xhrs(queue, callback) {
        var len = queue.length,
            res = new Array(len);
        loading != undefined && taoqi.close(loading);
        loading = taoqi.loading();
        for (var i = 0; i < len; i++) {
            (function (i) {
                var obj = queue[i];
                $.getJSON(obj.action, obj.params, function (data) {
                    res[i] = data;
                    if (--len <= 0) {
                        taoqi.close(loading);
                        loading = undefined;
                        type(callback) == 'function' && callback(res);
                    }
                });
            })(i);
        }
    }

    function xhr(action, params, callback, mark) {
        var url = baseUrl + action,
            _params = {};
        if (type(params) == 'object') {
            extend(_params, params);
        } else if (type(params) == 'function') {
            callback = params;
        }
//        if (_params.sign == undefined)
//            _params.sign = secret(_params);
        if (!mark) {
            loading != undefined && taoqi.close(loading);
            loading = taoqi.loading();
        }
        return $.getJSON(url, _params, function (data) {
            if (!mark) {
                taoqi.close(loading);
                loading = undefined;
            }
            type(callback) == 'function' && callback(data);
        });
    }

    function type(val) {
        return Object.prototype.toString.call(val).slice(8, -1).toLowerCase();
    }

    function post(action, params, callback, mark) {

//        if (params.sign == undefined)
//            params.sign = secret(params);
//        return $.ajax({
//            url: action,
//            type: "POST",
//            data: $.toJSON(params),
//            contentType: "application/json",
//            dataType: "json",
//            success: callback
//        });
        if (!mark) {
            loading != undefined && taoqi.close(loading);
            loading = taoqi.loading();
        }
        return $.ajax({
            type: 'POST',
            url: baseUrl + action,
            data: params,
            dataType: 'json',
            success: function (data) {
                if (!mark) {
                    taoqi.close(loading);
                    loading = undefined;
                }
                type(callback) == 'function' && callback(data);
            }
        });
    }

    var FILL_BEFORE = '<',
        FILL_COVER = '~',
        FILL_APPEND = '>';

    function render(config, data) {
        var prefix = config.tag.substr(0, 1);
        if (prefix != '#' && prefix != '.') {
            prefix = '#';
        } else {
            prefix = '';
        }
        var container = $(prefix + config.tag);
        var html = template(config.tpl, data);
        switch (config.fill) {
            case FILL_COVER:
                container.html(html);
                break;
            case FILL_APPEND:
                container.append(html);
                break;
            case FILL_BEFORE:
                container.prepend(html);
                break;
            default:
                throw new Error('没有此填充方式');
        }
    }

    function html(tpl, data) {
        return template(tpl, data);
    }

    function xhrsHtml(config, callback) {
        xhrs(config.queue, function (data) {
            if (typeof data == 'object') {
                callback && callback(html(config.tpl, data), data);
            } else {
                throw new Error('响应数据错误');
            }
        });
    }

    function xhrHtml(config, callback) {
        return xhr(config.action, config.params, function (data) {
            if (typeof data == 'object') {
                callback && callback(html(config.tpl, data), data);
            } else {
                throw new Error('响应数据错误');
            }
        }, config.mark);
    }

    function xhrsRender(config, callback) {
        xhrs(config.queue, function (data) {
            if (typeof data == 'object') {
                render(config, data);
                callback && callback(data);
            } else {
                throw new Error('响应数据错误');
            }
        });
    }

    function xhrRender(config, callback) {
        return xhr(config.action, config.params, function (data) {
//            console.log(data);
            if (typeof data == 'object') {
                data.params = config.params;
                render(config, data);
                callback && callback(data);
            } else {
                throw new Error('响应数据错误');
            }
        }, config.mark);
    }

    function parseUrl() {
        var searchStr = window.location.search.substr(1),
            searchArr = searchStr.split("&"),
            searchObj = {},
            i = 0,
            midArr;
        for (; i < searchArr.length; i++) {
            midArr = searchArr[i].split("=");
            searchObj[midArr[0]] = midArr[1];
        }
        return searchObj;
    }

    function secret(param) {
        var appkey = "1711394416800",
            secret = "cc1745453991ec29bfedd5f80a2d5bf0";
        var array = new Array();
        for (var key in param) {
            array.push(key);
        }
        array.sort();
        var paramArray = [];
        paramArray.push(appkey);
        for (var index in array) {
            var key = array[index];
            paramArray.push(key + (( typeof param[key] == "object" ) ? $.toJSON(param[key]) : param[key]));
        }
        paramArray.push(secret);
        var shaSource = paramArray.join("");
        var sign = new String(CryptoJS.SHA1(encodeURIComponent(shaSource))).toUpperCase();
        return sign;
    }

    function extend(obj, opt) {
        for (var k in opt) {
            obj[k] = opt[k];
        }
        return obj;
    }

    function SetCookie(name, value, expHours) {
        var exp = new Date();
        exp.setTime(exp.getTime() + expHours * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    function getCookie(name) {
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (arr != null)
            return unescape(arr[2]);
        return null;
    }

    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }

    self.Dao = {
        extend: extend,
        secret: secret,
        type: type,
        parseUrl: parseUrl,
        xhrRender: xhrRender,
        queueRender: xhrsRender,
        render: render,
        getData: xhr,
        queueData: xhrs,
        html: html,
        xhrHtml: xhrHtml,
        queueHtml: xhrsHtml,
        postData: post
    };
    self.Model = {};
    self.App = {

        getUserId: function () {
            return isApp && Tqmall.getUserId();
        },

        getSystem: function () {
            return isApp && Tqmall.getSystem();
        },

        getSiteId: function () {
            return isApp && Tqmall.getSiteId();
        },

        getDevice: function () {
            return isApp && Tqmall.getDevice();
        },

        getDeviceId: function () {
            return isApp && Tqmall.getDeviceId();
        },

        getVersion: function () {
            return isApp && Tqmall.getVersion();
        },

        run: function (success) {
            $(function () {
                isApp = !!self['Tqmall'];
                Dao.isApp = isApp;
                success.call(App);
            });
        }

    };

})(window);
