/**
 * @fileOverview 数据请求、模板渲染工具库
 * @author       haiyong.zhang
 * @createTime   2014/9/11
 * @updateTime   2015/3/3
 * @version      0.1.3
 */

(function (self) {

    var baseUrl = '';
    //var baseUrl = 'http://10.0.0.245:8090';
    //var baseUrl = 'http://10.0.1.88:8090';

    var isApp;

    String.prototype.format = function () {
        for (var i = 0, val = this, len = arguments.length; i < len; i++)
            val = val.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
        return val;
    };

    Function.prototype.delay = function () {
        var _this = this;
        var arg = Array.prototype.slice.call(arguments);
        return setTimeout(function () {
            _this.apply(arg.shift(), arg);
        }, arg.shift());
    };


    function xhr(action, params, callback) {
        var url = baseUrl + action,
            _params = {};
        if (arguments.length >= 3) {
            extend(_params, params);
        } else {
            callback = params;
        }
        if (_params.sign == undefined)
            _params.sign = secret(_params);
        return $.getJSON(url, _params, callback);
    }

    function xhrs(queue, callback) {
        var len = queue.length,
            res = new Array(len);
        for (var i = 0; i < len; i++) {
            (function (i) {
                var obj = queue[i];
                var url = baseUrl + obj.action,
                    _params = {};
                obj.params && extend(_params, obj.params);
                if (_params.sign == undefined)
                    _params.sign = secret(_params);
                $.getJSON(url, _params, function (data) {
                    res[i] = data;
                    if (--len <= 0) {
                        type(callback) == 'function' && callback(res);
                    }
                });
            })(i);
        }
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

    function posts(queue, callback) {
        var len = queue.length,
            res = new Array(len);
        for (var i = 0; i < len; i++) {
            (function (i) {
                var obj = queue[i];
                post(obj.action, obj.params, function (data) {
                    res[i] = data;
                    if (--len <= 0) {
                        type(callback) == 'function' && callback(res);
                    }
                });
            })(i);
        }
    }

    function postsRender(config, callback) {
        posts(config.queue, function (data) {
            if (typeof data == 'object') {
                render(config, data);
                callback && callback(data);
            } else {
                throw new Error('响应数据错误');
            }
        });
    }

    function postRender(config, callback) {
        return post(config.action, config.params, function (data) {
            if (typeof data == 'object') {
                render(config, data);
                callback && callback(data);
            } else {
                throw new Error('响应数据错误');
            }
        });
    }

    function post(action, params, callback) {

        if (params.sign == undefined)
            params.sign = secret(params);
        return $.ajax({
            url: baseUrl + action,
            type: "POST",
            data: JSON.stringify(params),
            contentType: "application/json",
            dataType: "json",
            success: callback
        });

//        return $.ajax({
//            type: 'POST',
//            url: baseUrl + action,
//            data: params,
//            dataType: 'json',
//            success: callback
//        });
    }

    var FILL_BEFORE = '<',
        FILL_COVER = '~',
        FILL_APPEND = '>';

    function render(config, data) {

        var container = $('#' + config.tag),
            html = template(config.tpl, data);
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

    function xhrRender(config, callback) {
        return xhr(config.action, config.params, function (data) {
            if (typeof data == 'object') {
                data.params = config.params;
                render(config, data);
                callback && callback(data);
            } else {
                throw new Error('响应数据错误');
            }
        });
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
            paramArray.push(key + (( typeof param[key] == "object" ) ? JSON.stringify(param[key]) : param[key]));
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

    function type(val) {
        return Object.prototype.toString.call(val).slice(8, -1).toLowerCase();
    }

    self.Dao = {
        extend: extend,
        secret: secret,
        type: type,
        parseUrl: parseUrl,
        xhrRender: xhrRender,
        render: render,
        getData: xhr,
        queueData: xhrs,
        queueRender: xhrsRender,
        posts: posts,
        postsRender: postsRender,
        postRender: postRender,
        postData: post
    };
    self.Model = {};
    self.App = {

        /* 微信config配置，签名（如果不签名，就不能触发分享） */
        wxconfig: function () {
            var link = location.href.split("#")[0];
            $.ajax({
                url: BASE_PATH + "/pub/wechat/share",
                data: {
                    url: encodeURIComponent(link)
                },
                success: function (result) {
                    var data = result.data;
                    if (result.success && data) {
                        wx.config({
                            debug: false,
                            appId: data.appId,
                            timestamp: data.timestamp,
                            nonceStr: data.nonceStr,
                            signature: data.signature,
                            jsApiList: [
                                "onMenuShareTimeline",
                                "onMenuShareAppMessage"
                            ]
                        });
                    }
                }
            });
        },

        share: function (title, desc, imgUrl, link) {
            /* 调用前需先引入wxshare.js */
            link = link.split('#')[0];

            this.wxconfig();

            wx.ready(function () {
                //alert("wx is ready!");
                /*wx.checkJsApi({
                 jsApiList: [
                 "onMenuShareTimeline",
                 "onMenuShareAppMessage"
                 ],
                 success: function(res) {
                 alert(res.errMsg);
                 }
                 });
                 wx.error(function (res) {
                 alert(res.errMsg);
                 });*/
                var sdata = {
                    title: title,
                    desc: desc,
                    link: link,
                    imgUrl: imgUrl
                    /*trigger: function() {
                     alert("用户点击分享按钮触发的函数");
                     },
                     success: function () {
                     alert('用户确认分享后执行的回调函数');
                     },
                     fail: function() {
                     alert('用户分享失败后执行的回调函数');
                     },
                     cancel: function () {
                     alert('用户取消分享后执行的回调函数');
                     }*/
                };
                wx.onMenuShareTimeline(sdata);
                wx.onMenuShareAppMessage(sdata);
            });
        },

        getSystem: function () {
            return isApp && TqmallLegend.getSys();
        },

        getToken: function () {
            return isApp && TqmallLegend.getToken();
        },

        getVersion: function () {
            return isApp && TqmallLegend.getVersion();
        },

        getRefer: function () {
            return isApp && TqmallLegend.getRefer();
        },

        getSid: function () {
            return isApp && TqmallLegend.getSid();
        },

        getUid: function () {
            return isApp && TqmallLegend.getUid();
        },

        setTitle: function (title) {
            return isApp && TqmallLegend.setTitle(title);
        },

        showToast: function (msg) {
            isApp ? TqmallLegend.showToast(msg) : alert(msg);
        },
        takePhoto: function (url) {
            isApp && TqmallLegend.takePhoto(url);
        },
        openTqmall: function (url) {
            isApp && TqmallLegend.openTqmall(url);
        },
        goBackPayListView: function () {
            isApp && TqmallLegend.goBackPayListView();
        },
        //iframe2open: function () {
        //    var iframe = document.createElement('iframe');
        //    var openUrl, UA = window.navigator.userAgent.toLowerCase(),
        //        pageArgs = Dao.parseUrl();
        //    if (UA.match(/android/i)) {
        //        openUrl = "tqmallRepair://yunxiu.com?type=3&&value=" + window.location.href;
        //    } else if (UA.match(/iphone/i)) {
        //        openUrl = "tqmallRepair://yunxiu.com?type=3&&value=" + window.location.href;
        //    }
        //    iframe.src = openUrl;
        //    iframe.style.display = "none";
        //    document.body.appendChild(iframe);
        //},
        //jump2download: function () {
        //    var UA = window.navigator.userAgent.toLowerCase();
        //    if (UA.match(/android/i)) {
        //        window.location.href = "http://tqmall-flash.b0.upaiyun.com/dandelion/taoqiyunxiu_czapp_release.apk?t=" + new Date().getTime();
        //    } else if (UA.match(/iphone/i)) {
        //        window.location.href = 'https://itunes.apple.com/us/app/tao-qi-yun-xiu-che-zhu-ban/id1026391647?mt=8';
        //    }
        //},

        run: function (success, callback) {
            Zepto(function ($) {
                isApp = !!self['TqmallLegend'];
                Dao.isApp = isApp;
                success.call(App, callback);
            });
        }

    };

})(window);