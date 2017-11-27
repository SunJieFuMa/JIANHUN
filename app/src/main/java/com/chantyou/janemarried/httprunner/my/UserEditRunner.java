package com.chantyou.janemarried.httprunner.my;

import android.text.TextUtils;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
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

import static com.chantyou.janemarried.config.UrlConfig.USER_EDIT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_EDIT;

/**
 * Created by j_turn on 2016/2/21.
 * Email 766082577@qq.com
 */
public class UserEditRunner extends HttpRunner {

    private static final String url = USER_EDIT;

    public UserEditRunner(Object... params) {
        super(HTTP_USER_EDIT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        MultipartBody pList = postPublicMultiPair();
        String file = (String) ev.getParamsAtIndex(0);
        if(!TextUtils.isEmpty(file)) {
            pList.addPart(new StringPart("fileFileName", (new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date()))+".jpg"));
            pList.addPart(new FilePart("file", new File(file)));
        }
        String nickname = (String) ev.getParamsAtIndex(1);
        pList.addPart(new StringPart("nickname", nickname));
        String state = (String) ev.getParamsAtIndex(2);
        if(!TextUtils.isEmpty(state)) {
            pList.addPart(new StringPart("state", state));
        }
        String marryDay = (String) ev.getParamsAtIndex(3);
        pList.addPart(new StringPart("marryDay", marryDay));
        String city = (String) ev.getParamsAtIndex(4);
        pList.addPart(new StringPart("city", city));
        String address = (String) ev.getParamsAtIndex(5);
        if(!TextUtils.isEmpty(address)) {
            pList.addPart(new StringPart("address", address));
        }
        String introduce = (String) ev.getParamsAtIndex(6);
        if(!TextUtils.isEmpty(introduce)) {
            pList.addPart(new StringPart("introduce", introduce));
        }
        String matchid = Constants.getUserInfoByKey(Constants.SU_MATCHID);
        if(!TextUtils.isEmpty(matchid)) {
            pList.addPart(new StringPart("matchid", matchid));
        }
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(pList));

        doDefParams(ev, response.getResult());
    }
}
