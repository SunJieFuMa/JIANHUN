package com.chantyou.janemarried.httprunner.product;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.PRODUCT_FAVORITE;
import static com.chantyou.janemarried.config.UrlConfig.PRODUCT_DELFAVORITE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PRODUCT_FAVORITE;

/**
 * Created by j_turn on 2016/4/2.
 * Email 766082577@qq.com
 */
public class ProductFavoriteRunner extends HttpRunner {

    private static final String url = PRODUCT_FAVORITE;

    public ProductFavoriteRunner(Object... params) {
        super(HTTP_PRODUCT_FAVORITE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        int collect = (int) ev.getParamsAtIndex(1);
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("proId", String.valueOf((int) ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("type", collect == 1 ? "0" : "1"));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
