package child.ryl.child.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.pay.alipay.PayResult;
import child.ryl.child.pay.alipay.util.OrderInfoUtil2_0;
import child.ryl.child.pay.wxutils.MD5;
import child.ryl.child.pay.wxutils.ResourceUtils;
import child.ryl.child.pay.wxutils.Util;
import child.ryl.child.pay.wxutils.getIP;

/**
 * 卖房通支付页面
 * 任玉林
 */
public class MftPay {
    private IWXAPI api;
    private Context context;
    private int key;//支付类型
    private String orderId;
    private Double money;//需要支付的总钱数
    private Double balance;//余额的总数
    private Double needMoney = 0.00;
    private boolean flag;//是否使用余额，由StickTimeFragment传递来的
    private boolean payWay;//当余额充足，转变为余额支付的判断状态
    private Double payCoupon = 0.00;//当toggleButton为true，使用的余额数量
    private String versionCode;//支付宝versionCode
    private String mBody;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "ddddd";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE =
            "dddddddddddddddddddd==";

    private static final int SDK_PAY_FLAG = 1;

    public MftPay(Context context, int key,
                  String money, String balance, String versionCode, boolean flag, String body) {
        this.mBody ="body";
        this.flag = flag;
        this.context = context;
        this.key = key;
        try {
            this.money = Double.parseDouble(money);
            this.balance = Double.parseDouble(balance);
        } catch (Exception e) {
//            PromptHelper.displayMessage(context, context.getString(R.string.net_error_prompt));
            return;
        }
        this.versionCode = versionCode;
        // 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID，代码如下：
        // 将该app注册到微信
        api = WXAPIFactory.createWXAPI(context,"WXAPP_ID");
        api.registerApp( "WXAPP_ID");
    }

    /**
     * 支付
     */
    private void pay() {
        switch (key) {
            case 0:
                aliPay();
                break;
            case 1:
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                if (isPaySupported) {
                    payInWechat();
                } else {
//                    PromptHelper.displayMessage(context, context.getString(R.string.wx_unable_prompt));
                }
                break;
        }
    }
    /**
     * 微信支付
     */
    private void payInWechat() {
        try {
            new Thread(runnable).start();
        } catch (Exception e) {
            Log.e("PAY_GET", "error：" + e.getMessage());
//            PromptHelper.displayMessage(context, context.getString(R.string.error) + e.getMessage());
        }
    }

    /**
     * 微信开启线程
     */
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            /**
             * 商户服务器生成支付订单，先调用统一下单API(详见第7节)生成预付单，获取到prepay_id后将参数再次签名传输给APP发起支付。
             */
            //商品描述
            String str = mBody;
            String body = null;
            try {
                body = new String(str.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //随机字符串
            String nonce_str = ResourceUtils.createRandomString(32).toUpperCase();
            //通知地址
            String notify_url = "http://ddddddddd";
            //商户订单号
            String out_trade_no = orderId;
            //总金额(单位分):除去余额支付的部分
            int total_fee = (int) (needMoney * 100);
//            int total_fee = 1;
            //终端IP
            String IP = getIP.getIP();
            //商户号
            String MCH_ID = "dddddd";
            //安全码
            String key = "dddddddd";

            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            String sign = MD5.GetMD5Code("appid=" + "WXAPP_ID" + "&body=" + body +
                    "&mch_id=" + MCH_ID + "&nonce_str=" + nonce_str + "&notify_url=" + notify_url +
                    "&out_trade_no=" + out_trade_no + "&spbill_create_ip=" + IP +
                    "&total_fee=" + total_fee + "&trade_type=APP" + "&key=" + key).toUpperCase();

            //参数以xml格式传递
            String entity = "<xml><appid>" + "WXAPP_ID" + "</appid>" +
                    "<body>" + body + "</body>" +
                    "<mch_id>" + MCH_ID + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<notify_url>" + notify_url + "</notify_url>" +
                    "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
                    "<sign>" + sign + "</sign>" +
                    "<spbill_create_ip>" + IP + "</spbill_create_ip>" +
                    "<total_fee>" + total_fee + "</total_fee>" +
                    "<trade_type>APP</trade_type>" +
                    "</xml>";

            try {
                entity = new String(entity.getBytes(), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buf = Util.httpPost(url, entity);
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                Log.e("get server pay params:", content);
                Map<String, String> map = ResourceUtils.readStringXmlOut(content);
                //下单成功，调起支付
                PayReq req = new PayReq();
                req.appId = "WXAPP_ID";
                req.partnerId = MCH_ID;
                req.prepayId = map.get("prepay_id");
                req.packageValue = "Sign=WXPay";
                req.nonceStr = nonce_str;
                String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
                req.timeStamp = timeStamp;
                req.extData = "app data";
                req.sign = MD5.GetMD5Code("appid=" + "WXAPP_ID" + "&noncestr=" + nonce_str + "&package=Sign=WXPay" +
                        "&partnerid=" + MCH_ID + "&prepayid=" + map.get("prepay_id") + "&timestamp=" + timeStamp + "&key=" + key).toUpperCase(Locale.getDefault());
                api.sendReq(req);
            } else {
//                PromptHelper.displayMessage(context, context.getString(R.string.webservice_require_error));
            }
        }
    };


    /**
     * 支付宝回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, PAY_SUCCESS_ALIPAY)) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        PromptHelper.displayMessage(context, context.getString(R.string.success_pay));
//                        statusBoolean.result(true);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        PromptHelper.displayMessage(context, context.getString(R.string.fail_pay));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private static final String PAY_SUCCESS_ALIPAY = "9000";

    /**
     * 支付宝支付业务
     */
    public void aliPay() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
//            Logger.e(MftPay.class, "the error is that id is null");
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params =
                OrderInfoUtil2_0.buildOrderParamMap(APPID, needMoney + "",
                        mBody, mBody, orderId, versionCode);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Logger.v(MftPay.class, "alipay-->" + result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
/*
添加混淆
-libraryjars libs/alipaySDK-20150602.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-libraryjars libs/libammsdk.jar
-keep class com.tencent.** { *;}
 */