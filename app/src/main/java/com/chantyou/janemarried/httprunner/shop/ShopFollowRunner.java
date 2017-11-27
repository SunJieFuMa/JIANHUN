package com.chantyou.janemarried.httprunner.shop;

import com.chantyou.janemarried.httprunner.JYHttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_FOLLOW;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_SHOP_FOLLOW;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
//收藏店铺
public class ShopFollowRunner extends JYHttpRunner {

    private static final String url = SHOP_FOLLOW;

    public ShopFollowRunner(Object... params) {
        super(HTTP_SHOP_FOLLOW, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("shopId", (String) ev.getParamsAtIndex(0)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
    }
}
