package com.chantyou.janemarried.httprunner.topic;

import android.text.TextUtils;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_ADD_EDIT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_ADD_EDIT;

/**
 * Created by j_turn on 2016/2/5 15:20
 * Email：766082577@qq.com
 */
public class TopicAddEditRunner extends HttpRunner {

    private static final String url = TOPIC_ADD_EDIT;

    public TopicAddEditRunner(Object... params) {
        super(HTTP_TOPIC_ADD_EDIT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        MultipartBody body = postPublicMultiPair();
        String topicId = (String) ev.getParamsAtIndex(0);
        if(!TextUtils.isEmpty(topicId)) {
            body.addPart(new StringPart("topicId", topicId));
        }
        body.addPart(new StringPart("title", (String) ev.getParamsAtIndex(1)));
        body.addPart(new StringPart("content", (String) ev.getParamsAtIndex(2)));
        body.addPart(new StringPart("phasename", (String) ev.getParamsAtIndex(3)));
        body.addPart(new StringPart("tags", (String) ev.getParamsAtIndex(4)));
        String datetime = (String) ev.getParamsAtIndex(5);
        body.addPart(new StringPart("datetime", datetime));
        List<String> pics = (List<String>) ev.getParamsAtIndex(6);
        int num = pics == null ? 0 : pics.size();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsssss");
        for (int i = 0; i < num; i++) {
            body.addPart(new StringPart("filesFileName["+i+"]", (format.format(new Date()))+".png"));
            body.addPart(new FilePart("files["+i+"]", new File(pics.get(i))));
        }

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(body));

        doDefParams(ev, response.getResult());
    }
}
