package com.chantyou.janemarried.httprunner.shop;

import com.chantyou.janemarried.httprunner.JYHttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_AD_LIST;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_SHOP_AD_LIST;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
//店铺首页轮播图
public class ShopADPicListRunner extends JYHttpRunner {

    private static final String url = SHOP_AD_LIST;

    public ShopADPicListRunner(Object... params) {
        super(HTTP_SHOP_AD_LIST, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("adLocalType", "1"));


        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
    }
}
