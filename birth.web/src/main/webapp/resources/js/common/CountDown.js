/**
 * Created by sky on 15/9/7.
 */

/**************************************
 * --------倒计时插件--------
 * 通过start()的回调函数获得时间的参数；
 * d:天数，h:小时, m: 分钟, s: 秒钟；
 * result的每一个属性对应map的每一个字符；
 * 默认map的字符为hms；
 **************************************/
(function (win) {

    // 缓存回调函数 和 map
    var options = {
        callback: function () {
        },
        map: "hms"
    };

    win.CountDown = function () {

        this.init(arguments);
        return this;
    };
    CountDown.prototype = {
        constructor: CountDown,
        init: function (args) {

            if (args.length <= 1) {  // 直接记为倒计时的秒数
                this.t = args[0] || 0;
            } else {                // 直接记为倒计时的开始时间和结束时间
                var cur = args[0],
                    end = args[1];
                this.t = end - cur;
            }
            this.rest = this.restTime = this.t =
                !isNaN(this.t) && this.t > 0 ? this.t : 0;
        },
        getDate: function () {

            return this.convert(24 * 60 * 60);
        },
        getHours: function () {

            return this.convert(60 * 60);
        },
        getMinutes: function () {

            return this.convert(60);
        },
        getSecends: function () {

            return this.convert(1);
        },
        convert: function (d) {

            var o = parseInt(this.rest / d);
            this.rest = this.rest % d;
            return ( "" + o ).length > 1 ? o : ( "0" + o );
        },
        match: function (o) {

            var r, self = this;
            switch (o) {
                case "d":
                    r = self.getDate();
                    break;
                case "h":
                    r = self.getHours();
                    break;
                case "m":
                    r = self.getMinutes();
                    break;
                case "s":
                    r = self.getSecends();
                    break;
            }
            return r;
        },
        start: function (callback, map) {

            var self = this;
            var result = {
                end: false
            };
            options.map = map || options.map;
            options.callback = callback;

            // 时间设置函数
            var setTime = function () {
                if (self.restTime <= 0) {     // 判断剩余时间是否大于0
                    self.destroy();
                    result.end = true;
                } else {
                    result.end = false;
                }
                for (var i = 0; i < map.length; i++) {     // 封装result结果集
                    var o = map.substr(i, 1);
                    result[o] = self.match(o);
                }
                self.rest = self.restTime = --self.restTime > 0 ? self.restTime : 0;
                callback && callback(result);
            };

            setTime();
            self.timer && self.destroy();
            self.timer = setInterval(function () {   // 设置完的1s后开始执行

                setTime();
            }, 1000);
            return this;
        },
        reset: function (time) {

            this.rest = this.restTime = (time || this.t);
            this.start(options.callback, options.map);
            return this;
        },
        destroy: function () {

            clearInterval(this.timer);
            this.timer = undefined;
            return this;
        }
    };


})(window);
