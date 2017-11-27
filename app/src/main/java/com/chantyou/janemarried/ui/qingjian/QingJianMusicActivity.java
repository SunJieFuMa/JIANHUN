package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.MusicListBean;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.utils.Player;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by j_turn on 2016/4/10.
 * Email 766082577@qq.com
 */
public class QingJianMusicActivity extends MyBaseActivity implements View.OnClickListener {

    private Button title_back;
    private Button title_setting;
    private RecyclerView recyclerView;
    private MusicListBean musicListBean;
    private LinearLayoutManager manager;
    private MusicListAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_qingjian_music);
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        title_setting = (Button) findViewById(R.id.title_setting);
        title_setting.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //        Toast.makeText(this, " userTempleteId音乐：：："+userTempleteId, Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.stop();
    }

    //将之前设置过的用过的那个音乐的布局中的imageView设置图标不同

    private void initData() {
        String url = UrlConfig.QingjianHost+"/user/music/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("page", 0 + "")//这里是固定值0，否则没有数据
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(QingJianMusicActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson(String response) {
        musicListBean = new Gson().fromJson(response, MusicListBean.class);
        musicListBean.getData().add(0, new MusicListBean.DataEntity("无音乐"));
        musicListBean.getData().get(0).setSelected(true);
        manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);//是否倒序排列
        recyclerView.setLayoutManager(manager);
        adapter = new MusicListAdapter();
        recyclerView.setAdapter(adapter);
    }
    //    private void doSave() {
    //        if(key <= 0) {
    //            CustomToast.showWorningToast(this, "请先选择音乐");
    //            return;
    //        }
    //
    //        showProgressDialog(null, "正在保存...");
    //        pushEventEx(false, null, new InviCardSaveRunner(3, key+""), this);
    //    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(QingJianMusicActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(QingJianMusicActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                back();
                break;
        }
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra("musicId", getMusicId());
        intent.putExtra("name", getMusicName());
        setResult(1, intent);
        finish();
    }

    private int count;

    /*private boolean isOneMusic() {
        for (int i = 0; i < musicListBean.getData().size(); i++) {
            if (musicListBean.getData().get(i).isSelected()) {
                count++;
                if (count>1){
                    count=0;//还原
                    return false;
                }
            }
        }
        count=0;//还原
        return true;
    }*/

    private int getMusicId() {
        for (int i = 0; i < musicListBean.getData().size(); i++) {
            if (musicListBean.getData().get(i).isSelected()) {
                int id = musicListBean.getData().get(i).getId();
                return id;
            }
        }
        return 0;
    }

    private String getMusicName() {
        for (int i = 0; i < musicListBean.getData().size(); i++) {
            if (musicListBean.getData().get(i).isSelected()) {
                String name = musicListBean.getData().get(i).getName();
                return name;
            }
        }
        return "";
    }


    public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicHolder> {

        @Override
        public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(QingJianMusicActivity.this, R.layout.item_qingjian_music, null);
            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(final MusicHolder holder, final int position) {
            //强制关闭复用，关于recyclerView的item错乱问题现在还没有解决
            //            holder.setIsRecyclable(false);
            holder.tv1.setText(musicListBean.getData().get(position).getName());
            holder.imageButton.setChecked(musicListBean.getData().get(position).isSelected());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.imageButton.setChecked(!musicListBean.getData().get(position).isSelected());
                    musicListBean.getData().get(position)
                            .setSelected(!musicListBean.getData().get(position).isSelected());

                    //想实现单选的点击
                    for (int i = 0; i < musicListBean.getData().size(); i++) {
                        if (musicListBean.getData().get(i).isSelected()) {
                            if (i != position) {
                                musicListBean.getData().get(i).setSelected(false);
                                Player.stop();
                                /*
                                *   RecyclerView调用notifyItemChanged闪烁问题相信很多人都遇到过。
                                    那是因为recyclerView默认设置的动画DefaultItemAnimator造成的
                                    只要设置为false，就可以不显示动画了，也就解决了闪烁问题。
                                * */
                                notifyItemChanged(i);
                                ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//去掉闪烁
                            }
                        }
                    }

                    //然后开始播放音乐
                    String musicUrl = musicListBean.getData().get(position).getMusicUrl();
                    if (null != musicUrl && !TextUtils.isEmpty(musicUrl)
                            && holder.imageButton.isChecked()
                            && musicListBean.getData().get(position).isSelected()) {
                        Player.playNetMusic(musicUrl);
                    }else {
                        Player.stop();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return musicListBean.getData().size();
        }

        public class MusicHolder extends RecyclerView.ViewHolder {
            private LinearLayout vItem1;
            private RadioButton imageButton;
            private TextView tv1;

            public MusicHolder(View itemView) {
                super(itemView);
                vItem1 = (LinearLayout) itemView.findViewById(R.id.vItem1);
                imageButton = (RadioButton) itemView.findViewById(R.id.imageButton);
                tv1 = (TextView) itemView.findViewById(R.id.tv1);
            }

        }
    }
}
