/**
 * Created by sky on 15/9/10.
 */

;(function (win) {

    win.Shuffling = function (opts) {

        this.opts = opts || {};
        this.init();
    };

    Shuffling.prototype = {
        constructor: Shuffling,
        init: function () {

            var self = this;
            if (!self.opts.domId) {
                throw new Error('domId element can not be empty!');
            } else if (!isString(self.opts.domId)) {
                throw new Error('domId element must be string!')
            }

            self.dom = document.querySelector('#' + self.opts.domId);
            if (!self.dom) {
                throw new Error('dom is not existed!');
            }

            self.children = self.dom.children;
            self.childCount = self.children.length;
            self.parent = self.dom.parentNode;
            self.parentWidth = self.parent && self.parent.clientWidth;
            self.index = 0;    // 当前页码
            self.isAni = false;   // 判断动画是否执行;

            self.exec();

            var AniEnd = function () {
                self.isAni = false;
            };

            // 动画结束操作
            var transitionEnd = whichTransitionEvent();
            transitionEnd ? self.dom.addEventListener(transitionEnd, AniEnd, false) : AniEnd();
        },
        exec: function () {

            var self = this;
            self.ani();
            self.opts.autoplay && self.autoplay();
        },
        ani: function () {

            var self = this;
            self.isAni = true;
            self.index = self.index < 0 ? ( self.childCount - 1 ) : ( self.index >= self.childCount ? 0 : self.index );
            self.dom.style.cssText =
                'width: ' + ( self.childCount * self.parentWidth ) + 'px;' +
                '-webkit-transform: translateZ(0) translate(' + ( -1 * self.index * self.parentWidth ) + 'px, 0);' +
                'transform: translateZ(0) translate(' + ( -1 * self.index * self.parentWidth ) + 'px, 0); ' +
                '-webkit-transform-origin: 0% 0%;' +
                'transform-origin: 0% 0%;' +
                '-webkit-transition: -webkit-transform 1s linear 0s;' +
                'transition: transform 1s linear 0s;';
        },
        prev: function () {

            var self = this;
            if (!self.isAni) {
                self.index--;
                self.exec();
            }
        },
        next: function () {

            var self = this;
            if (!self.isAni) {
                self.index++;
                self.exec();
            }
        },
        autoplay: function () {

            var self = this,
                t = self.opts.delay || 5000;

            self.timer && clearTimeout(self.timer);
            self.timer = setTimeout(function () {

                self.next();
            }, t);
        }
    };

    function isString(str) {

        return typeof str == 'string' && Object.prototype.toString.call(str) === '[object String]';
    }

    function whichTransitionEvent() {
        var el = document.createElement('fakeelement');
        var transitions = {
            'WebkitTransition': 'webkitTransitionEnd',
            'transition': 'transitionend',
            'OTransition': 'oTransitionEnd',
            'MozTransition': 'transitionend',
            'MsTransition': 'msTransitionEnd'
        };

        for (var t in transitions) {
            if (el.style[t] !== undefined) {
                return transitions[t];
            }
        }
    }

})(window)
