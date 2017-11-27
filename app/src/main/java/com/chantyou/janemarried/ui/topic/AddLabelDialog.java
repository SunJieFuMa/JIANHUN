package com.chantyou.janemarried.ui.topic;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.lib.mark.core.AndroidEventManager;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.widget.XDialog;

/**
 * Created by j_turn on 2016/2/20.
 * Email 766082577@qq.com
 */
public class AddLabelDialog extends XDialog implements View.OnClickListener {

    private EditText et;
    private  int phaseId;

    public AddLabelDialog(Context context, int phaseId) {
        super(context);
        setContentView(R.layout.dialog_addlabel);
        this.phaseId = phaseId;
        et = (EditText) findViewById(R.id.et);

        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                String name = et.getText().toString();
                if(!TextUtils.isEmpty(name)) {
                    AndroidEventManager.getInstance().runEvent(XEventCode.EVENT_RUNCODE, XEventCode.HTTP_TAGS_ADDONE, phaseId, name);
                } else {
                    CustomToast.showWorningToast(v.getContext(), "输入标签名称");
                }
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
