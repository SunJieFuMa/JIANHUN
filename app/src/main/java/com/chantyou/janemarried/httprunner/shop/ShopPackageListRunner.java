package com.chantyou.janemarried.httprunner.shop;

import com.chantyou.janemarried.httprunner.JYHttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_PACKAGE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_SHOP_PACKAGE;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
//商铺套餐
public class ShopPackageListRunner extends JYHttpRunner {

    private static final String url = SHOP_PACKAGE;

    public ShopPackageListRunner(Object... params) {
        super(HTTP_SHOP_PACKAGE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("shopId", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("page", (String) ev.getParamsAtIndex(1)));
//        pList.add(new NameValuePair("page", "1"));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
    }
}
