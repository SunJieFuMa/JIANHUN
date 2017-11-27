package com.chantyou.janemarried.httprunner;

import android.text.TextUtils;

import com.chantyou.janemarried.framework.XEventCode;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import org.json.JSONObject;

import static com.chantyou.janemarried.config.UrlConfig.GIFTS_QUERYALL;

/**
 * Created by j_turn on 2016/1/24 18:53
 * Email：766082577@qq.com
 */
public class GiftsQueryAllRunner extends HttpRunner {

    private static final String url = GIFTS_QUERYALL;

    public GiftsQueryAllRunner(Object... params) {
        super(XEventCode.HTTP_GIFTS_QUERYALL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
    }
//    {"cashgiftlist":"[{\"amountOfMoney\":200,\"name\":\"马宁\",\"tip\":\"留言内容简介\"},{\"amountOfMoney\":20000,\"name\":\"李四\",\"tip\":\"备注\"},{\"amountOfMoney\":20000,\"name\":\"李四\",\"tip\":\"备注\"}]","return_code":"0","msg":"查询成功"}
}
