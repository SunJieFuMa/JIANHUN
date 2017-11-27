package com.chantyou.janemarried.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.CheckVersion.CallBackInterface;
import com.chantyou.janemarried.CheckVersion.VersionCallBackInterface;
import com.chantyou.janemarried.R;


/**
 * Created by zhuwx on 2016/8/17.
 * 弹出报警解决方案
 */
public class MyDialog {//这个类其实是弄了一个类似popUpWindow的dialog
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private ViewHolder holder;

    private Context context;

    public MyDialog(Context context) {
        this.context = context;
    }


    public void show(String title, String content) {
        LayoutInflater factory = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        View convertView = factory.inflate(R.layout.pop_window, null);
        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
        holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);

        holder.title.setText(title);
        holder.txt_content.setText(content);

        dialog = builder.create();


        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Window window = dialog.getWindow();
        window.setContentView(convertView);
        //透明
        window.setBackgroundDrawable(new ColorDrawable());
        //////////////////////////////////////////////////
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (AppAndroid.getScreenWidth()*0.8); // 宽度
        //        lp.height = (int) (AppAndroid.getScreenHeight()*0.6); // 高度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        window.setAttributes(lp);
        ///////////////////////////////////////////////////

        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void show(String title, String content, boolean showKeyBoard, final CallBackInterface callBackInterface) {
        LayoutInflater factory = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        View convertView = factory.inflate(R.layout.pop_window, null);
        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
        holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);

        holder.title.setText(title);
        holder.txt_content.setText(content);

        dialog = builder.create();


        dialog.show();
        if (showKeyBoard) {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }


        Window window = dialog.getWindow();
        window.setContentView(convertView);
        dialog.setCanceledOnTouchOutside(true);
        //透明
        window.setBackgroundDrawable(new ColorDrawable());

        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.doSome();
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callBackInterface.doSome();
            }
        });
    }

    public void show(String title, String content, boolean showKeyBoard, boolean showBtn, final VersionCallBackInterface callBackInterface) {
        LayoutInflater factory = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        View convertView = factory.inflate(R.layout.pop_window, null);
        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
        holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);


        holder.ll_btn = (LinearLayout) convertView.findViewById(R.id.ll_btn);
        holder.tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
        holder.tv_sure = (TextView) convertView.findViewById(R.id.tv_sure);

        holder.title.setText(title);
        holder.txt_content.setText(content);

        dialog = builder.create();


        dialog.show();
        if (showKeyBoard) {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }


        Window window = dialog.getWindow();
        window.setContentView(convertView);
        dialog.setCanceledOnTouchOutside(true);
        //透明
        window.setBackgroundDrawable(new ColorDrawable());

        //关闭
        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.doCancel();
                dialog.dismiss();
            }
        });

        if (showBtn) {
            holder.ll_btn.setVisibility(View.VISIBLE);

            //确认
            holder.tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackInterface.doSure();
                    dialog.dismiss();
                }
            });
            //取消
            holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackInterface.doCancel();
                    dialog.dismiss();
                }
            });
        }


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callBackInterface.doCancel();
            }
        });
    }

    static class ViewHolder {
        private TextView title;
        private TextView txt_content;
        private TextView tv_close;


        private LinearLayout ll_btn;
        private TextView tv_cancel;
        private TextView tv_sure;
    }

}
