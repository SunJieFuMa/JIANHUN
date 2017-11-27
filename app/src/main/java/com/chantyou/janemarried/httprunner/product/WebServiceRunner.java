package com.chantyou.janemarried.httprunner.product;

import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import static com.chantyou.janemarried.config.UrlConfig.CARD_INFO;

/**
 * Created by j_turn on 2016/4/13.
 * Email 766082577@qq.com
 */
public class WebServiceRunner extends HttpRunner {


    private static final String url = "http://58.221.62.184:8188/BsSchoolWebService.asmx";
//    private static final String url = "http://58.221.62.184:8188/BsSchoolWebService";

    private String NAME_SPACE = "http://tempuri.org/";

    public WebServiceRunner( Object... params) {
        super(XEventCode.HTTP_TOPIC_COLLECT, UrlConfig.CARD_INFO, params);
    }


    public String createNetSoapText(String methodname, HashMap<String, String> params) {

        StringBuffer buf = new StringBuffer();
        Set<String> tags = params == null ? null : params.keySet();

        buf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
        buf.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
//        buf.append("<S:Header>");
//        buf.append("<SllmWebService xmlns=\""
//                + NAME_SPACE
//                + "\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" SOAP-ENV:actor=\"http://www.w3.org/2003/05/soap-envelope/role/next\">");
//        buf.append(USERNAME + "&amp;" + PASSWORD);
//        buf.append("</SllmWebService>");
//        buf.append("</S:Header>");
        buf.append("<soap12:Body>");
        buf.append("<").append(methodname).append(" xmlns=\"").append(NAME_SPACE).append("\">");

        if(tags != null)
            for (String tag : tags) {
                buf.append("<").append(tag).append(">");
                buf.append(params.get(tag));
                buf.append("</").append(tag).append(">");
            }

        buf.append("</").append(methodname).append(">");
        buf.append("</soap12:Body>");
        buf.append("</soap12:Envelope>");

        return buf.toString();
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
//        LinkedList<NameValuePair> pList = postPublicPair();
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("LocalSchoolID", "CC-366");
//        params.put("BoxSN", "Box-179");
        params.put("bsShcoolID", "CC-366");
        params.put("bsShcoolBoxID", "Box-179");
        params.put("fileGuid", "FCABB109-4629-400A-A9B6-095A801C6503");

//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("charset", HTTP.UTF_8);
//        httpPost.addHeader("Content-Type","application/soap+xml; charset=utf-8");
//        StringEntity entity = new StringEntity(createNetSoapText("GetPlayFileInfo", params), "utf-8");
//        httpPost.setEntity(entity);
//
//        HttpClient client = new DefaultHttpClient();
//        HttpParams params2 = client.getParams();
//        HttpConnectionParams.setConnectionTimeout(params2,
//                5000);
//        HttpConnectionParams.setSoTimeout(params2, 5000);
//
//        HttpResponse response = client.execute(httpPost);
//
//        if (isResponseAvailable(response)) {
//            String strResult = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
//                .addHeader("SOAPAction", NAME_SPACE+"GetPlayFileInfo")
                .setHttpBody(new StringBody(createNetSoapText("GetPlayFileInfo", params), "application/soap+xml", null)));

        doDefForm(ev, response.getResult());
    }

//    private boolean isResponseAvailable(HttpResponse response) {
//        if (response != null)
//            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
//        return false;
//    }
}
