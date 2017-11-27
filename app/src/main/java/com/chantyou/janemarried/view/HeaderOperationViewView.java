package com.chantyou.janemarried.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.HeaderOperationAdapter;
import com.chantyou.janemarried.model.OperationEntity;

import java.util.List;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderOperationViewView extends HeaderViewInterface<List<OperationEntity>> {

    private FixedGridView gvOperation;

    public HeaderOperationViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<OperationEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_operation_layout, listView, false);


        gvOperation = (FixedGridView) view.findViewById(R.id.gv_operation);

        dealWithTheView(list);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<OperationEntity> list) {
        HeaderOperationAdapter adapter = new HeaderOperationAdapter(mContext, list);
        gvOperation.setAdapter(adapter);
    }

}
