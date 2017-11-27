package com.chantyou.janemarried.httprunner.product;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.PRODUCT_CATE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PRODUCT_CATE;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
public class ProductCateRunner extends HttpRunner {

    private static final String url = PRODUCT_CATE;

    public ProductCateRunner(Object... params) {
        super(HTTP_PRODUCT_CATE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
