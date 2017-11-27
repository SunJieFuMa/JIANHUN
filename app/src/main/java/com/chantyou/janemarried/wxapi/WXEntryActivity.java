package com.chantyou.janemarried.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.AndroidEventManager;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 处理微信回调
 *
 * @author chenhetong(chenhetong@baidu.com)
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;

	public static final int PayFail = 0;// 操作失败
	public static final int PaySuccess = 1;// 操作成功

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView currrentTextView = new TextView(this);
		setContentView(currrentTextView);
		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
		api.handleIntent(getIntent(), this);
		Log.d("WXCHAT", " WXCHAT onCreate WX_APP_ID=" + Constants.WX_APP_ID);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		api.handleIntent(intent, this);
		Log.d("WXCHAT", "WXCHAT onNewIntent WX_APP_ID=" + Constants.WX_APP_ID);
	}

	@Override
	public void onReq(BaseReq req) {

		Log.d("WXCHAT", "WXCHAT onReq WX_APP_ID=" + Constants.WX_APP_ID);
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("WXCHAT", "WXCHAT onResp resp.getType()=" + resp.getType());
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
//            UserManager.getInstance().disposeWxAuthResp((com.tencent.mm.sdk.modelmsg.SendAuth.Resp) resp);
			AndroidEventManager.getInstance().runEvent(XEventCode.EVENT_AUTH_CODE, Constants.WeChat, resp);
			this.finish();
			return;
		} else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {

			Intent intent = new Intent();

//            intent.setAction(Constant.SHARE_ACTION);

			if (resp.errCode == 0) {
				intent.putExtra("code", 1);
				intent.putExtra("resutl", "分享成功");
			} else {
				intent.putExtra("code", 0);
				intent.putExtra("resutl", "支付失败");
			}

			this.sendBroadcast(intent);
		}
		this.finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 百度统计
//        UmengEvents.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

//        UmengEvents.onResume(this);

	}
}