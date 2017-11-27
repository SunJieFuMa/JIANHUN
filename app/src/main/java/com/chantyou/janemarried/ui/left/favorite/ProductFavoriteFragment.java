package com.chantyou.janemarried.ui.left.favorite;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductFindCollectRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.excellent.fragment.ExcellentMarriageGoodsFragment;
import com.lib.mark.core.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2016/4/3.
 * Email 766082577@qq.com
 */
public class ProductFavoriteFragment extends ExcellentMarriageGoodsFragment {

    @Override
    public void onPullDown() {
        adapter.clear();
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new ProductFindCollectRunner(pageCur+""), this);
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        ((XBaseActivity) getActivity()).pushEvent(new ProductFindCollectRunner(pageCur+""), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_FINDCOLLECT:
                onRefreshCompleted();
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> pros = (List<Map<String, Object>>) map.get("myProducts");
                    hasMore(pros != null && pros.size() >= 10);
                    adapter.addData(pros);
                } else {
                    if(pageCur > 0) {
                        pageCur -= 1;
                    }
                }
                break;
        }
    }



}
