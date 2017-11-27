package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.MyCard;
import com.mhh.lib.ui.base.BaseFragment;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class DetailCardFragment4 extends BaseFragment {

    int layId;
    private ImageView iv;
    private ImageView iv_photo;
    private MyCard.DataEntity.PagesEntity pages;
    private int templateId;
    private RelativeLayout rl_root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        if (getArguments() != null) {
            layId = getArguments().getInt("layId", 0);
            templateId = getArguments().getInt("templateId", 0);
            pages = (MyCard.DataEntity.PagesEntity) getArguments().getSerializable("pages");
        }
        return layId;
    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);

        System.out.println("滑到我了：：pages.getId()：："+pages.getId());
        iv = (ImageView) mRootView.findViewById(R.id.iv);
        iv_photo = (ImageView) mRootView.findViewById(R.id.iv_photo);
        rl_root= (RelativeLayout) mRootView.findViewById(R.id.rl_root);//根布局的点击事件不管用，也不知道是为什么
        if (null!=pages.getCommpents()&&pages.getCommpents().size()>0){
            if (!"http://101.201.209.200:1661/qingjian".equals(pages.getCommpents().get(0).getImageUrl())){
                Glide.with(AppAndroid.getContext()).load(pages.getCommpents().get(0).getImageUrl())
                        .into(iv_photo);
            }
        }
        Glide.with(AppAndroid.getContext()).load(pages.getBackgroundImgUrl()).into(iv);
//        final TextView tv_del = (TextView) getActivity().findViewById(R.id.title_setting);
//        tv_del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(tv_del.getText().toString())) {
        //之前我把点击事件放在这里，但是怎么弄都会有错，怎么都找不到哪里错了，点击这个图片的时候
        //显示传的值是对的，但就是删除的时候就不对了，真是太邪门了，于是我把点击事件放在了activity中
        //在activity中就好了，我也不知道为什么,可能是因为之前用的FragmentPagerAdapter的缓存问题吧
//                    delete();
//                }
//            }
//        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("pageId::"+pages.getId()+" userTempleteId::"+pages.getUserTempleteId());
                //设置大图的点击事件,记住要将布局中ImageView的clickable属性设置为true，否则不管用
                Intent intent = new Intent(getContext(), PhotoViewActivity.class);
                intent.putExtra("photoView_imageUrl", pages.getBackgroundImgUrl());
//                intent.putExtra("position", getActivity().getIntent().getIntExtra("position",0));//没用到
                intent.putExtra("userPageId", pages.getId());
                //                intent.putExtra("userTempleteId", pages.getUserTempleteId());
                //这里还要加一个布尔值来区分一下，因为最后两个模板加进去的时候是不能在上面添加图片的
                if (null==pages.getCommpents()||pages.getCommpents().size()<=0){
                    intent.putExtra("canAddPhoto", false);
                }else {
                    intent.putExtra("canAddPhoto", true);
                }
                intent.putExtra("userTempleteId", pages.getUserTempleteId());
                if (null != pages.getCommpents() && pages.getCommpents().size() > 0) {
                    intent.putExtra("componentId", pages.getCommpents().get(0).getId());
                }
                startActivityForResult(intent,0);
            }
        });
    }

    /*private void delete() {
//                Toast.makeText(getContext(), "点击了删除按钮", Toast.LENGTH_SHORT).show();
        final String url = "http://101.201.209.200:1661/qingjian/action/qingjian/user/page/del";
        System.out.println("pageId::"+pages.getId()+" userTempleteId::"+pages.getUserTempleteId());
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("pageId", pages.getId() + "")
                //                .addParams("pageId", pages.getPageId() + "")
                //                .addParams("userTempleteId", templateId + "")
                .addParams("userTempleteId", pages.getUserTempleteId() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }*/

//    private void parseJson(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            int status = jsonObject.getInt("status");
//            if (status == 1) {
//                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
