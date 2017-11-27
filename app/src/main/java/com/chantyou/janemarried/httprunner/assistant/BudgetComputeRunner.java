package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.bean.ComputeBudget;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.SpUtils;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;
import java.util.List;

import static com.chantyou.janemarried.config.UrlConfig.BUDGET_COMPUTE;

/**
 * Created by j_turn on 2016/4/8.
 * Email 766082577@qq.com
 */
public class BudgetComputeRunner extends HttpRunner {

    private static final String url = BUDGET_COMPUTE;

    public BudgetComputeRunner(Object... params) {
        super(XEventCode.HTTP_BUDGET_COMPUTE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("total", String.valueOf(ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("deskNum", String.valueOf(ev.getParamsAtIndex(1))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
        ////////////////////////////////////////////////////////////////////////////////////////////
        ComputeBudget computeBudget = new Gson().fromJson(response.getResult(), ComputeBudget.class);
        List<ComputeBudget.DataEntity> data = computeBudget.getData();
        if (null == data || data.size() <= 0) {
            return;
        }
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            builder.append(data.get(i).getId()+","+data.get(i).getMoney()+";");
        }
        /*
        * 这里竟然只是拼接一个字符串，我还以为是上传一个字符串数组，害的我弄了一个下午，真是气死个人
        * */
//        SpUtils.putString(AppAndroid.getContext(), "saveBudget", new Gson().toJson(list));
        SpUtils.putString(AppAndroid.getContext(), "saveBudget", builder.toString());
    }
}