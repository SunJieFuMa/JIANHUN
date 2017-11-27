package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.InviCardSaveRunner;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsChoosePhotoHelper2;

/**
 * Created by j_turn on 2016/4/10.
 * Email 766082577@qq.com
 */
public class CardEditImgActivity extends XBaseActivity {

    @ViewInject(R.id.tvChoose)
    private TextView tvChoose;
    @ViewInject(R.id.ivImg)
    private ImageView ivImg;

    private AbsChoosePhotoHelper2 choosePhotoHelper;
    private String mPicPath;

    public static void launch(Context cxt, String image) {
        Intent intent = new Intent(cxt, CardEditImgActivity.class);
        intent.putExtra("image", image);
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_card_editimg);
    }

    @Override
    protected void init() {
        super.init();

        HImageLoader.displayImage(getIntent().getStringExtra("image"), ivImg, R.drawable.card_defpic);

        choosePhotoHelper = new AbsChoosePhotoHelper2(this) {
            @Override
            public void launchActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }

            @Override
            public void onPictureChoosed(String filePath, String displayName) {
                super.onPictureChoosed(filePath, displayName);
                mPicPath = filePath;
                HImageLoader.init().removeItemFileCache(filePath);
                HImageLoader.setBitmapFile(filePath, ivImg, HImageLoader.createOptions(R.color.gray, 0));
            }
        };

        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoHelper.launchChoosePhotoWithCrop(v, 1, 1, 800, 800);
            }
        });
    }

    private void doSave() {
        if(TextUtils.isEmpty(mPicPath)) {
            CustomToast.showWorningToast(this, "请先选择图片");
            return;
        }

        showProgressDialog(null, "正在保存...");
        pushEventEx(false, null, new InviCardSaveRunner(2, mPicPath), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_CARD_SAVE:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "已保存");
                    finish();
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("操作失败"));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_menu) {
            doSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data, RESULT_OK);
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(CardEditImgActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CardEditImgActivity.this);
    }
}
