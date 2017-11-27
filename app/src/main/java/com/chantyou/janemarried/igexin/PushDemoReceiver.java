package com.chantyou.janemarried.igexin;

/**
 * Created by j_turn on 2016/4/19.
 * Email 766082577@qq.com
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.chantyou.janemarried.httprunner.push.IgeXinPushRunner;
import com.chantyou.janemarried.ui.main.InformationActivity;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.lib.mark.core.AndroidEventManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据，客户端onreceive方法中获取透传消息处写跳转代码

                // String appid = bundle.getString("appid");

                //payload----服务端设置的透传消息内容
                byte[] payload = bundle.getByteArray("payload");
                //taskid---推送任务的标识
                String taskid = bundle.getString("taskid");
                //messageid---消息id
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    //获取的透传消息内容，定义字符型参数赋值
                    String data = new String(payload);
                    try {
                        //如果要区分新闻资讯里面的推送和结婚帮里面的推送还需要加一个字段来进行区分---type
                        JSONObject jsonObject = new JSONObject(data);
                        int id = jsonObject.getInt("id");
                        int type = jsonObject.getInt("type");
                        if (type == 0) {
                            //说明推送的是新闻资讯
                            String figurePic = jsonObject.getString("figurePic");
                            String title = jsonObject.getString("title");
                            if (null == figurePic || TextUtils.isEmpty(figurePic) ||
                                    null == title || TextUtils.isEmpty(title)) {
                                return;
                            }
                            //记得一定要更改intent的Flags，相当于新开了一个任务栈
                            InformationActivity.launch3(context, id, title, figurePic);
//                                                        InformationActivity.launch3(context, 72,
//                                                                "婚礼想价格低又想格调高，几个小创意满足你",
//                                                                "http://101.201.209.200/upload/news1486532485344.jpg");
                        } else if (type == 1) {
                            //说明推送的是结婚邦里的内容
                            //记得一定要更改intent的Flags，相当于新开了一个任务栈
                            TopicDetailsActivity.launch2(context, id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("GetuiSdkDemo", "receiver payload : " + data);

                    payloadData.append(data);
                    payloadData.append("\n");

                    //                    CustomToast.showRightToast(context, "data=" + data);
                    //                    if (GetuiSdkDemoActivity.tLogView != null) {
                    //                        GetuiSdkDemoActivity.tLogView.append(data + "\n");
                    //                    }
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                Log.e("Push", "clientid=" + cid);
                AndroidEventManager.getInstance().pushEventEx(new IgeXinPushRunner(cid), null);
                //                if (GetuiSdkDemoActivity.tView != null) {
                //                    GetuiSdkDemoActivity.tView.setText(cid);
                //                }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
}

