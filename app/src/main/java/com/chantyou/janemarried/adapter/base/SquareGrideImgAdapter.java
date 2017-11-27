package com.chantyou.janemarried.adapter.base;

import android.view.View;
import android.widget.ImageView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.HImageLoader;

import java.util.List;


/**
 * Created by j_turn on 2015/12/17.
 * Email 766082577@qq.com
 */
public class SquareGrideImgAdapter extends BaseListAdapter<String> {

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_lib_squareimg;
    }

    @Override
    public void setData(List<String> data) {
        clear();
        super.setData(data);
    }

    @Override
    protected AbsViewHolder setViewHolder(View view) {
        return new ViewHolder(view);
    }

    private class ViewHolder extends AbsViewHolder {

        private View root;
        ImageView iv;

        public ViewHolder(View view) {
            super();
            root = view.findViewById(R.id.root);
            iv = (ImageView) view.findViewById(R.id.iv);
        }

        @Override
        public void setValue(int position, String item) {
            HImageLoader.displayImage(item, iv, R.color.white_gray);
        }
    }
}
