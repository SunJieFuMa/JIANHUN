package com.chantyou.janemarried.adapter.assistant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.assistant.BudgetActivity;
import com.chantyou.janemarried.ui.assistant.FortuneActivity;
import com.chantyou.janemarried.ui.assistant.GuestListActivity;
import com.chantyou.janemarried.ui.assistant.PchListActivity;
import com.chantyou.janemarried.ui.assistant.SpeechActivity;
import com.chantyou.janemarried.ui.assistant.wedding.WeddingProcessActivity;
import com.chantyou.janemarried.ui.qingjian.QingJianActivity;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;

/**
 * Created by j_turn on 2015/12/20 23:52
 * Email：766082577@qq.com
 */
public class MarriedassistantAdapter extends BaseRecyclerViewAdapter {

//    int ivs[] = new int[]{R.drawable.icon_assistant_1,R.drawable.icon_assistant_2
//            ,R.drawable.icon_assistant_3,R.drawable.icon_assistant_4
//            ,R.drawable.icon_assistant_5,R.drawable.icon_assistant_6
//            ,R.drawable.icon_assistant_7,R.drawable.icon_assistant_8
//            ,R.drawable.icon_assistant_9};
//    static String[] tvs = new String[]{"备婚工具","婚礼预算","宾客名单","微信请柬","采购清单"
//            ,"发言稿","黄道吉日","婚礼工具","我的结婚任务","礼金记账","婚礼流程"};
    int ivs[] = new int[]{R.drawable.icon_assistant_1,R.drawable.icon_assistant_2
            ,R.drawable.icon_assistant_4
            ,R.drawable.icon_assistant_5,R.drawable.icon_assistant_6
            ,R.drawable.icon_assistant_9};
    static String[] tvs = new String[]{"婚礼预算","宾客名单","采购清单"
            ,"发言稿","黄道吉日","婚礼流程"};

    public MarriedassistantAdapter(Context context) {
        super(context);
    }

//    @Override//0是备婚工具，7是婚礼工具
//    public int getItemViewType(int position) {
//        return position == 0 || position == 7 ? 1 : 2;
//    }
    @Override//0是备婚工具，7是婚礼工具
    public int getItemViewType(int position) {
        return position == 0 ? 1 : 2;
    }

//    @Override
//    public int getItemCount() {
//        return ivs.length + 2;
//    }
    @Override
    public int getItemCount() {
        return ivs.length+1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1) {
//            return new ViewHolder2(inflateView(parent, R.layout.adapter_marriedassistant_title));
            return new ViewHolder2(inflateView(parent, R.layout.adapter_marriedassistant2));
        }
        return new ViewHolder(inflateView(parent, R.layout.adapter_marriedassistant));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder == null) {
            return;
        }
        if(holder instanceof ViewHolder) {//如果是ViewHolder
            ViewHolder vh = (ViewHolder) holder;
//            vh.iv.setImageResource(ivs[position>6 ? position-2 : position-1]);
            vh.iv.setImageResource(ivs[position-1]);
            vh.tv.setText(tvs[position-1]);
            vh.pos = position;
        } else if(holder instanceof ViewHolder2) {//如果是ViewHolder2
//            ViewHolder2 vh = (ViewHolder2) holder;
//            vh.tv.setText(tvs[position]);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv)
        ImageView iv;
        @ViewInject(R.id.tv)
        TextView tv;
        int pos;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(pos == tvs.length - 1) {
                        WeddingProcessActivity.launch(v.getContext(), WeddingProcessActivity.class);
                    } else if(pos == tvs.length - 2) {
                        GiftsBillingActivity.launch(v.getContext(), GiftsBillingActivity.class);
                    } else if(pos == tvs.length - 3) {
                        MyTaskActivity.launch(v.getContext(), MyTaskActivity.class);
                    } else if(pos == tvs.length - 7) {
                        PchListActivity.launch(v.getContext(), PchListActivity.class);
                    } else if(pos == tvs.length - 6) {
                        SpeechActivity.launch(v.getContext(), SpeechActivity.class);
                    }  else if(pos == tvs.length - 5) {
                        FortuneActivity.launch(v.getContext(), FortuneActivity.class);
                    } else if(pos == 1) {
                        BudgetActivity.launch(v.getContext(), BudgetActivity.class);
                    }  else if(pos == 2) {
                        GuestListActivity.launch(v.getContext(), GuestListActivity.class);
                    } else if(pos == 3) {
                        InviCardActivity.launch(v.getContext(), InviCardActivity.class);
                    }*/
                   if(pos == 4) {
                        SpeechActivity.launch(v.getContext(), SpeechActivity.class);
                    } else if(pos == 5) {
                        FortuneActivity.launch(v.getContext(), FortuneActivity.class);
                    }  else if(pos == 6) {
                        WeddingProcessActivity.launch(v.getContext(), WeddingProcessActivity.class);
                    } else if(pos == 1) {
                        BudgetActivity.launch(v.getContext(), BudgetActivity.class);
                    }  else if(pos == 2) {
                        GuestListActivity.launch(v.getContext(), GuestListActivity.class);
                    } else if(pos == 3) {
                       PchListActivity.launch(v.getContext(), PchListActivity.class);
                    }
                }
            });
        }
    }

    private static class ViewHolder2 extends RecyclerView.ViewHolder {

        /*@ViewInject(R.id.tv)
        TextView tv;
        public ViewHolder2(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }*/

        public ViewHolder2(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //                        InviCardActivity.launch(v.getContext(), InviCardActivity.class);
                        QingJianActivity.launch(v.getContext(), QingJianActivity.class);
                }
            });
    }
   }
}
