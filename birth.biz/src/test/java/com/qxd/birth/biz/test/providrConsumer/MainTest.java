package com.qxd.birth.biz.test.providrConsumer;

/**
 * Created by xiangdong.qu on 17/4/2 16:52.
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        /*MyFactoryBean myFactoryBean = new MyFactoryBean();
        ConsumerThread consumerThread = new ConsumerThread(myFactoryBean);
        ProviderThread providerThread = new ProviderThread(myFactoryBean);

        new Thread(providerThread).start();
        new Thread(consumerThread).start();*/

        String temp = "<form id=\"lianpaysubmit\" name=\"lianpaysubmit\" " +
                "action=\"https://cashier.lianlianpay.com/payment/authpay.htm\" " +
                "method=\"POST\"><input type=\"hidden\" name=\"oid_partner\" " +
                "value=\"201703081001550891\"/><input type=\"hidden\" name=\"no_order\" " +
                "value=\"YXSM009901704110001-17-3-295103\"/><input type=\"hidden\" name=\"busi_partner\" " +
                "value=\"101001\"/><input type=\"hidden\" name=\"url_return\" " +
                "value=\"http://yun1.epei360.com/legend/onlinepay/lianlian/verify/\"/><input type=\"hidden\" name=\"sign_type\" " +
                "value=\"MD5\"/><input type=\"hidden\" name=\"notify_url\" " +
                "value=\"http://app.epei360.com/insurance/lianpay/notify/url\"/><input type=\"hidden\" name=\"acct_name\" " +
                "value=\"渠项栋\"/><input type=\"hidden\" name=\"name_goods\" " +
                "value=\"淘汽云修系统服务销售\"/><input type=\"hidden\" name=\"card_no\" " +
                "value=\"6222021202010582568\"/><input type=\"hidden\" name=\"money_order\" " +
                "value=\"0.02\"/><input type=\"hidden\" name=\"version\" " +
                "value=\"1.0\"/><input type=\"hidden\" name=\"sign\" " +
                "value=\"26887d557b2c4b227be84832dfa23708\"/><input type=\"hidden\" name=\"risk_item\" " +
                "value='{\"user_info_full_name\":\"渠项栋\",\"user_info_dt_register\":\"20160819093916\",\"frms_ware_category\":\"2999\",\"user_info_identify_type\":\"4\",\"user_info_identify_state\":\"1\",\"user_info_bind_phone\":\"18768107319\",\"user_info_id_no\":\"410482198908269333\",\"user_info_mercht_userno\":\"295103\"}'/><input type=\"hidden\" name=\"timestamp\" value=\"20170411163009\"/><input type=\"hidden\" name=\"pay_type\" value=\"D\"/><input type=\"hidden\" name=\"id_type\" value=\"0\"/><input type=\"hidden\" name=\"user_id\" value=\"2951039333\"/><input type=\"hidden\" name=\"dt_order\" value=\"20170411163009\"/><input type=\"hidden\" name=\"id_no\" value=\"410482198908269333\"/><input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>";
        analysisFrom(temp);
    }

    private static void analysisFrom(String formInfo) {
        String value = "value=\"";
        String tempStr = formInfo.substring(formInfo.indexOf("no_order"), formInfo.length());
        System.out.println(tempStr);
        String flow_order_sn = tempStr.substring(tempStr.indexOf(value) + value.length(), tempStr.indexOf("\"/>"));
        System.out.println(flow_order_sn);
    }
}
