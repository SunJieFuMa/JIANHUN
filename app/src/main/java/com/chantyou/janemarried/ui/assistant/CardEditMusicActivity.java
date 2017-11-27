package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.InviCardSaveRunner;
import com.chantyou.janemarried.utils.Player;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;

/**
 * Created by j_turn on 2016/4/10.
 * Email 766082577@qq.com
 */
public class CardEditMusicActivity extends XBaseActivity {

    @ViewInject(R.id.vItem1)
    private View vItem1;
    @ViewInject(R.id.vItem2)
    private View vItem2;
    @ViewInject(R.id.vItem3)
    private View vItem3;
    @ViewInject(R.id.vItem4)
    private View vItem4;
    @ViewInject(R.id.vItem5)
    private View vItem5;

    private int key;

    public static void launch(Context cxt, String audio) {
        Intent intent = new Intent(cxt, CardEditMusicActivity.class);
        intent.putExtra("audio", audio);
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_card_editmusic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.stop();
    }

    @Override
    protected void init() {
        super.init();
        try {
            String audio = getIntent().getStringExtra("audio");
            if(!TextUtils.isEmpty(audio) && audio.indexOf("/mc") > 0 && audio.indexOf(".mp3") > 0) {
                String ks = audio.substring(audio.indexOf("/mc")+3, audio.indexOf(".mp3"));
                int key = Integer.parseInt(ks);
                if(key > 0) {
                    if (key == 1) {
                        setSel(vItem1);
                    } else if(key == 2) {
                        setSel(vItem2);
                    } else if(key == 3) {
                        setSel(vItem3);
                    } else if(key == 4) {
                        setSel(vItem4);
                    } else if(key == 5) {
                        setSel(vItem5);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //将之前设置过的用过的那个音乐的布局中的imageView设置图标不同
    private void setSel(View v) {
        ((ImageView) vItem1.findViewById(R.id.iv)).setImageResource(v == vItem1 ? R.drawable.icon_cbx_s : R.color.transparent);
        ((ImageView) vItem2.findViewById(R.id.iv)).setImageResource(v == vItem2 ? R.drawable.icon_cbx_s : R.color.transparent);
        ((ImageView) vItem3.findViewById(R.id.iv)).setImageResource(v == vItem3 ? R.drawable.icon_cbx_s : R.color.transparent);
        ((ImageView) vItem4.findViewById(R.id.iv)).setImageResource(v == vItem4 ? R.drawable.icon_cbx_s : R.color.transparent);
        ((ImageView) vItem5.findViewById(R.id.iv)).setImageResource(v == vItem5 ? R.drawable.icon_cbx_s : R.color.transparent);

        Player.playRaw(this, getMusicRaw(v));
    }

    private int getMusicRaw(View v) {
        if(v == vItem1) {
            key = 1;
            return R.raw.mc1;
        } else if(v == vItem2) {
            key = 2;
            return R.raw.mc2;
        } else if(v == vItem3) {
            key = 3;
            return R.raw.mc3;
        } else if(v == vItem4) {
            key = 4;
            return R.raw.mc4;
        } else if(v == vItem5) {
            key = 5;
            return R.raw.mc5;
        }
        return R.raw.mc1;
    }

    @OnClick({R.id.vItem1, R.id.vItem2, R.id.vItem3, R.id.vItem4, R.id.vItem5})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.vItem1:
            case R.id.vItem2:
            case R.id.vItem3:
            case R.id.vItem4:
            case R.id.vItem5:
                setSel(v);
                break;
        }
    }

    private void doSave() {
        if(key <= 0) {
            CustomToast.showWorningToast(this, "请先选择音乐");
            return;
        }

        showProgressDialog(null, "正在保存...");
        pushEventEx(false, null, new InviCardSaveRunner(3, key+""), this);
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
    protected void onResume() {
        super.onResume();
        StatService.onResume(CardEditMusicActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CardEditMusicActivity.this);
    }
}
