package com.chantyou.janemarried.ui.qingjian;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.MyCard;
import com.mhh.lib.ui.base.BaseFragment;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class DetailCardFragment1 extends BaseFragment {

    int layId;
    private MyCard.DataEntity dataEntity;
    private ImageView iv;
    private TextView tv_man;
    private TextView tv_woman;
    private TextView tv_time;
    private TextView tv_address;
    private RelativeLayout rl;
    private int[] location;
    private static int left;
    private static int top;
    private int textAlign;
    private int width;
    private int height;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        if (getArguments() != null) {
            layId = getArguments().getInt("layId", 0);
            //            left = getArguments().getInt("left", 0);
            //            top = getArguments().getInt("top", 0);
            //            Toast.makeText(getContext(), "left:: "+left+" top::"+top, Toast.LENGTH_SHORT).show();
        }
        return layId;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        dataEntity = (MyCard.DataEntity) getArguments().getSerializable("dataEntity");
        width = dataEntity.getWidth();
        height = dataEntity.getHeight();
        iv = (ImageView) mRootView.findViewById(R.id.iv);
        rl = (RelativeLayout) mRootView.findViewById(R.id.rl);
        tv_man = (TextView) mRootView.findViewById(R.id.tv_man);
        tv_woman = (TextView) mRootView.findViewById(R.id.tv_woman);
        tv_time = (TextView) mRootView.findViewById(R.id.tv_time);
        tv_address = (TextView) mRootView.findViewById(R.id.tv_address);
        tv_man.setText(dataEntity.getFirstPage().get(0).getTextContent());
        tv_man.setTextColor(Color.parseColor("#" + dataEntity.getFirstPage().get(0).getTextColor()));
        textAlign = dataEntity.getFirstPage().get(0).getTextAlign();
        //        Toast.makeText(getContext(),
        //                "对齐方式分别是："+dataEntity.getFirstPage().get(0).getTextAlign()
        //                +"  "+dataEntity.getFirstPage().get(1).getTextAlign()
        //                +"  "+dataEntity.getFirstPage().get(2).getTextAlign()
        //                +"  "+dataEntity.getFirstPage().get(3).getTextAlign()
        //                , Toast.LENGTH_SHORT).show();

        //根据从服务器上获取的数据来设置文字内容、文字颜色、控件宽高、文字内容居中等
        tv_woman.setText(dataEntity.getFirstPage().get(1).getTextContent());
        tv_woman.setTextColor(Color.parseColor("#" + dataEntity.getFirstPage().get(1).getTextColor()));


        tv_time.setText(dataEntity.getFirstPage().get(2).getTextContent());
        tv_time.setTextColor(Color.parseColor("#" + dataEntity.getFirstPage().get(2).getTextColor()));


        tv_address.setText(dataEntity.getFirstPage().get(3).getTextContent());
        tv_address.setTextColor(Color.parseColor("#" + dataEntity.getFirstPage().get(3).getTextColor()));

        /*
        在代码里面设置Gravity，那这个控件的宽度必须是固定的或者是填充父控件
        也就是必须要在代码里设置控件的宽高或者在布局中设置这个控件的宽高是填充父窗体的
         */
        setTextGravity(dataEntity.getFirstPage().get(0).getTextAlign(), tv_man);
        setTextGravity(dataEntity.getFirstPage().get(1).getTextAlign(), tv_woman);
        setTextGravity(dataEntity.getFirstPage().get(2).getTextAlign(), tv_time);
        setTextGravity(dataEntity.getFirstPage().get(3).getTextAlign(), tv_address);

        /*rl.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                location = new int[2];
                rl.getLocationInWindow(location);
                //                Toast.makeText(getContext(), "左边"+location[0]+"  上边"+location[1], Toast.LENGTH_SHORT).show();
                float widthR = (float) rl.getMeasuredWidth() / (float) AppAndroid.getScreenWidth();
                float heightR = (float) rl.getMeasuredHeight() / (float) AppAndroid.getScreenHeight();
                //                Toast.makeText(getContext(), "widthR:: "+widthR+" heightR"+heightR, Toast.LENGTH_SHORT).show();
                float v = (float) (rl.getMeasuredWidth()*rl.getMeasuredHeight())
                        /(float) (AppAndroid.getScreenWidth()*AppAndroid.getScreenHeight());
                //设置文字大小
                tv_man.setTextSize(dataEntity.getFirstPage().get(0).getTextSize() * v);
                tv_woman.setTextSize(dataEntity.getFirstPage().get(1).getTextSize() * v);
                tv_time.setTextSize(dataEntity.getFirstPage().get(2).getTextSize() * v);
                tv_address.setTextSize(dataEntity.getFirstPage().get(3).getTextSize() * v);

                setLayout(tv_man,
                        (int) (dataEntity.getFirstPage().get(0).getX() * widthR),
                        (int) (dataEntity.getFirstPage().get(0).getY() * heightR),
                        (int) (dataEntity.getFirstPage().get(0).getWidth()),
                        (int) (dataEntity.getFirstPage().get(0).getHeight())

                );
                setLayout(tv_woman,
                        (int) (dataEntity.getFirstPage().get(1).getX() * widthR),
                        (int) (dataEntity.getFirstPage().get(1).getY() * heightR),
                        (int) (dataEntity.getFirstPage().get(1).getWidth()),
                        (int) (dataEntity.getFirstPage().get(1).getHeight())

                );
                setLayout(tv_time,
                        (int) (dataEntity.getFirstPage().get(2).getX() * widthR),
                        (int) (dataEntity.getFirstPage().get(2).getY() * heightR),
                        (int) (dataEntity.getFirstPage().get(2).getWidth()),
                        (int) (dataEntity.getFirstPage().get(2).getHeight())

                );
                setLayout(tv_address,
                        (int) (dataEntity.getFirstPage().get(3).getX() * widthR),
                        (int) (dataEntity.getFirstPage().get(3).getY() * heightR),
                        (int) (dataEntity.getFirstPage().get(3).getWidth()),
                        (int) (dataEntity.getFirstPage().get(3).getHeight())
                );

                String backgroundImgUrl = dataEntity.getBackgroundImgUrl();
                Glide.with(AppAndroid.getContext()).load(backgroundImgUrl).into(iv);
            }
        });*/
        rl.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        location = new int[2];
                        rl.getLocationInWindow(location);
                        //60dp的时候 720 1079   480 789   779 1268
                        //                Toast.makeText(getContext(), "左边"+location[0]+"  上边"+location[1], Toast.LENGTH_SHORT).show();
                        //获取状态栏的高度
                        int statusBarHeight = AppAndroid.getStatusBarHeight(getActivity());

                        //这里我用的是比例，用根布局的宽度除以服务器上viewpager的宽得到宽的占比，同样得到高的占比
                                                                        //不太精确，于是我又加了50
//                        float widthR = (float) rl.getWidth() / (float) (AppAndroid.getScreenWidth());
//                        float heightR = (float) rl.getHeight() / (float) (AppAndroid.getScreenHeight());
                        float widthR = (float) rl.getWidth() / (float) (dataEntity.getWidth());
                        float heightR = (float) rl.getHeight() / (float) (dataEntity.getHeight());
//                        Toast.makeText(getContext(), rl.getWidth()+" "+rl.getHeight(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), "widthR:: "+widthR+" heightR"+heightR, Toast.LENGTH_SHORT).show();

                        //文字大小也是用的比例，因为字体还是有点大，所以分母上我又加了一个状态栏的高度
//                        float v = (float) (rl.getWidth() * rl.getHeight())
//                                / (float) (AppAndroid.getScreenWidth() * (AppAndroid.getScreenHeight() + statusBarHeight));
                        float v = (float) (rl.getWidth() * rl.getHeight())
                                / (float) (dataEntity.getWidth() * dataEntity.getHeight());
                        //设置文字大小
//                        tv_man.setTextSize(dataEntity.getFirstPage().get(0).getTextSize() * v);
//                        tv_woman.setTextSize(dataEntity.getFirstPage().get(1).getTextSize() * v);
//                        tv_time.setTextSize(dataEntity.getFirstPage().get(2).getTextSize() * v);
//                        tv_address.setTextSize(dataEntity.getFirstPage().get(3).getTextSize() * v);
                        //字体用比例的话不行，不知道怎么回事，于是我就干脆设置了一个固定值
                        tv_man.setTextSize((float) (dataEntity.getFirstPage().get(1).getTextSize() * 0.33));
                        tv_woman.setTextSize((float) (dataEntity.getFirstPage().get(1).getTextSize() * 0.33));
                        tv_time.setTextSize((float) (dataEntity.getFirstPage().get(2).getTextSize() * 0.33));
                        tv_address.setTextSize((float) (dataEntity.getFirstPage().get(3).getTextSize() * 0.33));
//                        tv_man.setTextSize((float) APKUtil2.px2sp(getContext(),
//                                (float) (dataEntity.getFirstPage().get(0).getTextSize()* v)));
//                        tv_woman.setTextSize((float) APKUtil2.px2sp(getContext(),
//                                (float) (dataEntity.getFirstPage().get(1).getTextSize()* v)));
//                        tv_time.setTextSize((float) APKUtil2.px2sp(getContext(),
//                                (float) (dataEntity.getFirstPage().get(2).getTextSize()* v)));
//                        tv_address.setTextSize((float) APKUtil2.px2sp(getContext(),
//                                (float) (dataEntity.getFirstPage().get(3).getTextSize()* v)));

                        /*
                        动态设置高度不行，不知道是什么原因，我感觉应该是服务器那边设置的高度出了问题
                        所以高度的话我就自己设置了个差不多的比例
                         */
                        setLayout(tv_man,
                                (int) (dataEntity.getFirstPage().get(0).getX() * widthR),
                                (int) (dataEntity.getFirstPage().get(0).getY() * heightR),
                                (int) (dataEntity.getFirstPage().get(0).getWidth() * widthR),
                                (int) (dataEntity.getFirstPage().get(0).getHeight()*1.2)
                        );
                        setLayout(tv_woman,
                                (int) (dataEntity.getFirstPage().get(1).getX() * widthR),
                                (int) (dataEntity.getFirstPage().get(1).getY() * heightR),
                                (int) (dataEntity.getFirstPage().get(1).getWidth() * widthR),
                                (int) (dataEntity.getFirstPage().get(1).getHeight()*1.2)
                        );
                        setLayout(tv_time,
                                (int) (dataEntity.getFirstPage().get(2).getX() * widthR),
                                (int) (dataEntity.getFirstPage().get(2).getY() * heightR),
                                (int) (dataEntity.getFirstPage().get(2).getWidth() * widthR),
                                (int) (dataEntity.getFirstPage().get(2).getHeight()*1.2)
                        );
                        setLayout(tv_address,
                                (int) (dataEntity.getFirstPage().get(3).getX() * widthR),
                                (int) (dataEntity.getFirstPage().get(3).getY() * heightR),
                                (int) (dataEntity.getFirstPage().get(3).getWidth() * widthR),
                                (int) (dataEntity.getFirstPage().get(3).getHeight()*1.2)
                        );

                        String backgroundImgUrl = dataEntity.getBackgroundImgUrl();
                        Glide.with(AppAndroid.getContext()).load(backgroundImgUrl).into(iv);
                        /*
                        需要注意的是OnGlobalLayoutListener可能会被多次触发，
                        因此在得到了宽度高度之后，要将OnGlobalLayoutListener注销掉。
                         */
                        rl.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });

        //相对位置
        //        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv_man.getLayoutParams();
        //        params.setMargins((int) dataEntity.getFirstPage().get(0).getX()-110,
        //                (int) dataEntity.getFirstPage().get(0).getY()-276, 0, 0);// 通过自定义坐标来放置你的控件left, top, right, bottom
        //        tv_man.setLayoutParams(params);

        //
        //        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)tv_woman.getLayoutParams();
        //        params2.setMargins((int) dataEntity.getFirstPage().get(1).getX(),
        //                (int) dataEntity.getFirstPage().get(1).getY(), 0, 0);
        //        tv_woman.setLayoutParams(params2);
        //
        //
        //        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)tv_time.getLayoutParams();
        //        params3.setMargins((int) dataEntity.getFirstPage().get(2).getX(),
        //                (int) dataEntity.getFirstPage().get(2).getY(), 0, 0);
        //        tv_time.setLayoutParams(params3);
        //
        //
        //        RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams)tv_address.getLayoutParams();
        //        params4.setMargins((int) dataEntity.getFirstPage().get(3).getX(),
        //                (int) dataEntity.getFirstPage().get(3).getY(), 0, 0);
        //        tv_address.setLayoutParams(params4);
        //这里的最外层布局rl点击事件不生效有点意思
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ReWriteInfoActivity.class);
                intent.putExtra("userTempleteId",dataEntity.getId());
//                intent.putExtra("templateId",dataEntity.getTemplateId());
                intent.putExtra("dataEntity",dataEntity);
                startActivityForResult(intent,0);
            }
        });

    }

    /*
    * 设置控件所在的位置和宽高
    */
    public static void setLayout(View view, int left, int top, int width, int height) {
//        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
//        margin.width = width;
//        margin.height = height;
//        margin.setMargins(left, top, 0, 0);//依次是左上右下
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
//        view.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
        params.width = width;
        params.height = height;
        params.setMargins(left,top, 0, 0);
        view.setLayoutParams(params);
    }

    public static void setTextGravity(int textAlign, TextView tv) {
        if (textAlign == 1) {
            tv.setGravity(Gravity.LEFT);
        } else if (textAlign == 2) {
            tv.setGravity(Gravity.CENTER);
        } else if (textAlign == 3) {
            tv.setGravity(Gravity.RIGHT);
        }
    }
}
