package com.chantyou.janemarried.httprunner.shop;

import com.chantyou.janemarried.httprunner.JYHttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_LIST;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_SHOP_LIST;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
//获取商铺列表
public class ShopListRunner extends JYHttpRunner {

    private static final String url = SHOP_LIST;

    public ShopListRunner(Object... params) {
        super(HTTP_SHOP_LIST, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("province", (String) ev.getParamsAtIndex(0)));
        String city = (String) ev.getParamsAtIndex(1);
        if (null != city && !"".equalsIgnoreCase(city)) {
//            city = city.substring(city.lastIndexOf("市") + 1);
//            city = city.substring(city.lastIndexOf("省") + 1);
            city = city.replace("市", "").replace("省", "");
        }
        System.out.println("zhuwx:城市:" + city);

        pList.add(new NameValuePair("city", city));
        pList.add(new NameValuePair("county", (String) ev.getParamsAtIndex(2)));
        pList.add(new NameValuePair("merryType", (String) ev.getParamsAtIndex(3)));
        pList.add(new NameValuePair("hotel", (String) ev.getParamsAtIndex(4)));
        pList.add(new NameValuePair("orderType", (String) ev.getParamsAtIndex(5)));
        pList.add(new NameValuePair("page", (String) ev.getParamsAtIndex(6)));
        pList.add(new NameValuePair("size", (String) ev.getParamsAtIndex(7)));
//        pList.add(new NameValuePair("search", (String) ev.getParamsAtIndex(8)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
        System.out.println("主页商铺的信息："+response.getResult());

        /*不带任何参数直接访问*/
        Response<String> response2 = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post));
        System.out.println("主页商铺的信息2："+response2.getResult());
    }
}
