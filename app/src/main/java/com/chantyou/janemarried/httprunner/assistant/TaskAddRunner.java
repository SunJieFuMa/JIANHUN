package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TASK_USERADD;

/**
 * Created by j_turn on 2016/4/2.
 * Email 766082577@qq.com
 */
public class TaskAddRunner extends HttpRunner {

    private static final String url = TASK_USERADD;

    public TaskAddRunner(Object... params) {
        super(XEventCode.HTTP_TASK_USERADD, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("taskIds", String.valueOf(ev.getParamsAtIndex(0))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
        System.out.println("添加一个结婚任务:"+ response.getResult());
    }
}