//轮播插件。
;
(function ($) {
    $.fn.lunbo = function (opt) {
        var args = $.extend({}, {
            "interval": 3000
        }, opt);

        var selector = this;
        var imgList = $("ul li", selector);
        var pointList = $("ol li", selector);
        var imgCurrentName = 'img_current';
        var pointCurrentName = 'point_current';

        var fadeTime = 500;

        var gobalIndex = 0;

        if (imgList.size() <= 1) {
            pointList.hide();
            return;
        }

        var lunBoFn = function (index) {
            var imgCurrent = $("li." + imgCurrentName, selector);
            var pointCurrent = $("li." + pointCurrentName, selector);
            imgCurrent.fadeOut(fadeTime, function () {
                $(this).removeClass(imgCurrentName);
            });
            pointCurrent.removeClass(pointCurrentName);
            if (index != undefined) {
                gobalIndex = index;
            } else {
                if (gobalIndex + 1 >= imgList.length) {
                    gobalIndex = 0;
                } else {
                    gobalIndex += 1;
                }
            }

            imgList.eq(gobalIndex).fadeIn(fadeTime, function () {
                $(this).addClass(imgCurrentName);
            });
            pointList.eq(gobalIndex).addClass(pointCurrentName);
        }

        var winInter = setInterval(function () {
            lunBoFn();
        }, args.interval);

        pointList.on("click", function (event) {
            var index = $(this).index();
            window.clearInterval(winInter);
            lunBoFn(index);

            winInter = setInterval(function () {
                lunBoFn();
            }, args.interval);

            return false;
        });
    }
})(jQuery);