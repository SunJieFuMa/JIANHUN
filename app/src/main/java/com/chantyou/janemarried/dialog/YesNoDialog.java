package com.chantyou.janemarried.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.mhh.lib.widget.XDialog;

/**
 * Created by j_turn on 2016/1/25 21:44
 * Email：766082577@qq.com
 */
public class YesNoDialog extends XDialog implements View.OnClickListener, DialogInterface {

    private Button tvOk, tvCancel;
    private TextView tvTitle, tvMessage;
    private  DialogInterface.OnClickListener pListener, nListener;

    public YesNoDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_yesorno);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvOk = (Button) findViewById(R.id.btnOK);
        tvCancel = (Button) findViewById(R.id.btnCancel);
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                dismiss();
                if(pListener != null) {
                    pListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
                }
                break;
            case R.id.btnCancel:
                dismiss();
                if(nListener != null) {
                    nListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
                }
                break;
        }
    }

    public YesNoDialog setTitle(String title) {
        if(tvTitle != null) {
            tvTitle.setText(title);
        }
        return this;
    }

    public YesNoDialog setMessage(String message) {
        if(tvMessage != null) {
            tvMessage.setText(message);
        }
        return this;
    }

    public YesNoDialog setButton(int witch, String sequence, DialogInterface.OnClickListener listener) {
        if(witch == DialogInterface.BUTTON_POSITIVE) {
            tvOk.setText(Html.fromHtml(sequence));
            pListener = listener;
        }
        if(witch == DialogInterface.BUTTON_NEGATIVE) {
            tvCancel.setText(Html.fromHtml(sequence));
            nListener = listener;
        }
        return this;
    }
}
