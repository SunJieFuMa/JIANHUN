package com.chantyou.janemarried.httprunner.assistant;

import android.text.TextUtils;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.io.File;

import static com.chantyou.janemarried.config.UrlConfig.CARD_SAVE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_CARD_SAVE;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class InviCardSaveRunner extends HttpRunner {

    private static final String url = CARD_SAVE;

    public InviCardSaveRunner(Object... params) {
        super(HTTP_CARD_SAVE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        MultipartBody pList = postPublicMultiPair();
        int type = (int) ev.getParamsAtIndex(0);
        pList.addPart(new StringPart("type", type+""));
        if(type == 1) {     // 请柬文本信息
            pList.addPart(new StringPart("newMan", String.valueOf(ev.getParamsAtIndex(1))));
            pList.addPart(new StringPart("newWoman", String.valueOf(ev.getParamsAtIndex(2))));
            String dateTime = String.valueOf(ev.getParamsAtIndex(3));
            pList.addPart(new StringPart("marryDay", dateTime.split(" ")[0]));
            pList.addPart(new StringPart("marryTime", dateTime.split(" ")[1]));
            pList.addPart(new StringPart("hotelName", String.valueOf(ev.getParamsAtIndex(4))));
            pList.addPart(new StringPart("hotelAddress", String.valueOf(ev.getParamsAtIndex(5))));
        } else if(type == 2) { // 请柬图像
            String file = (String) ev.getParamsAtIndex(1);//本地图片位置，提交到服务器保存起来，以免用户在手机里把这个照片删了
            if(!TextUtils.isEmpty(file)) {
                pList.addPart(new FilePart("image", new File(file)));
            }
        } else if(type == 3) {  // 请柬音乐
            pList.addPart(new StringPart("key", (String) ev.getParamsAtIndex(1)));
        }
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(pList));

        doDefForm(ev, response.getResult());
    }
}
