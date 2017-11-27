package com.chantyou.janemarried.httprunner.product;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.PRODUCT_BYTYPE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PRODUCT_BYTYPE;

/**
 * Created by j_turn on 2016/3/30.
 * Email 766082577@qq.com
 */
public class ProductByTypeRunner extends HttpRunner {

    private static final String url = PRODUCT_BYTYPE;

    public ProductByTypeRunner(Object... params) {
        super(HTTP_PRODUCT_BYTYPE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        int type = (int) ev.getParamsAtIndex(0);
//        String tp = "all";
//        if(type < Constants.PRODUCT.length) {
//            tp = Constants.PRODUCT[type];
//        }
        pList.add(new NameValuePair("type", type+""));
        pList.add(new NameValuePair("pageCur", String.valueOf(ev.getParamsAtIndex(1))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
