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

import static com.chantyou.janemarried.config.UrlConfig.TASK_USEREDIT;

/**
 * Created by j_turn on 2016/4/2.
 * Email 766082577@qq.com
 */
public class TaskEditRunner extends HttpRunner {

    private static final String url = TASK_USEREDIT;

    public TaskEditRunner(Object... params) {
        super(XEventCode.HTTP_TASK_USEREDIT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("taskIds", String.valueOf(ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("name", String.valueOf(ev.getParamsAtIndex(1))));
        pList.add(new NameValuePair("comTime", String.valueOf(ev.getParamsAtIndex(2))));
        pList.add(new NameValuePair("tipTime", String.valueOf(ev.getParamsAtIndex(3))));
        pList.add(new NameValuePair("remark", String.valueOf(ev.getParamsAtIndex(4))));
        pList.add(new NameValuePair("budget", String.valueOf(ev.getParamsAtIndex(5))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
        System.out.println("编辑一个结婚任务:"+response.getResult());
    }
}
