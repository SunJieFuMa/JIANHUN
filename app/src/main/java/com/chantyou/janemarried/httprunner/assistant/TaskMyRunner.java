package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import static com.chantyou.janemarried.config.UrlConfig.TASK_MY;

/**
 * Created by j_turn on 2016/3/31.
 * Email 766082577@qq.com
 */
public class TaskMyRunner extends HttpRunner {

    private static final String url = TASK_MY;

    public TaskMyRunner(Object... params) {
        super(XEventCode.HTTP_TASK_MY, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
        System.out.println("查询我的结婚任务:"+response.getResult());
    }
}
