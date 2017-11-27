package com.chantyou.janemarried.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.SquareGrideImgAdapter;
import com.chantyou.janemarried.adapter.topic.TopicCommentAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.topic.TopicCollectRunner;
import com.chantyou.janemarried.httprunner.topic.TopicCommentListRunner;
import com.chantyou.janemarried.httprunner.topic.TopicCommentRunner;
import com.chantyou.janemarried.httprunner.topic.TopicDelCollectRunner;
import com.chantyou.janemarried.httprunner.topic.TopicInfoRunner;
import com.chantyou.janemarried.ui.base.ActivityEvent;
import com.chantyou.janemarried.ui.base.LookPhotoActivity;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.ui.base.ThreadShareHelper;
import com.chantyou.janemarried.ui.view.CommentView;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.widget.GridViewEx;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RefreshRecyclerAdapterManager;

/**
 * Created by maohongyong on 16/3/5.
 * Email 766082577@qq.com
 */
public class TopicDetailsActivity extends PullrefreshBottomloadActivity implements ActivityEvent {

    private static final int MAX_NUM = 3;

    @ViewInject(R.id.backdrop)
    ImageView backdrop;
    @ViewInject(R.id.ivHead)
    ImageView ivHead;
    @ViewInject(R.id.tvSubject)
    TextView tvSubject;
    @ViewInject(R.id.tvTags)
    TextView tvTags;
    @ViewInject(R.id.tvPicNums)
    TextView tvPicNums;
    @ViewInject(R.id.vComment)
    View vComment;

    private CommentView mCommentView;

    private View header;

    private int topicId;

    Map<String, Object> topicInfo;

    private TopicCommentAdapter mAdapter;

    ThreadShareHelper shareHelper;

    public static void launch(Context cxt, int topicId) {
        Intent intent = new Intent(cxt, TopicDetailsActivity.class);
        intent.putExtra("topicId", topicId);
        cxt.startActivity(intent);
    }
    public static void launch2(Context cxt, int topicId) {
        Intent intent = new Intent(cxt, TopicDetailsActivity.class);
        intent.putExtra("topicId", topicId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topicdetails;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        topicId = getIntent().getIntExtra("topicId", -1);
        if (topicId == -1) {
            CustomToast.showWorningToast(this, "话题Id错误");
            finish();
        }
        super.onCreate(arg0);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
    }

    @Override
    protected void init() {
        super.init();
        mCommentView = new CommentView(findViewById(R.id.view_comment));
        shareHelper = new ThreadShareHelper(this);

        onPullDown();
    }

    private void setTopicInfo() {
        if(topicInfo != null) {
            HImageLoader.displayImage(JsonUtil.getItemString(topicInfo, "headPic"), ivHead, R.drawable.defaulthead);
            tvSubject.setText(JsonUtil.getItemString(topicInfo, "title"));
            tvPicNums.setText("0张");
            tvPicNums.setVisibility(View.INVISIBLE);
            List<Map<String, Object>> tags = (List<Map<String, Object>>) JsonUtil.jsonToList(JsonUtil.getItemString(topicInfo, "tags"));
            if(menuFavorite != null) {
                menuFavorite.setIcon(JsonUtil.getItemInt(topicInfo, "isfavorite") == 1 ? R.drawable.menu_favorite_s : R.drawable.menu_favorite);
            }
            String stag = "";
            int size = tags == null ? 0 : tags.size();
            for (int i = 0; i < size; i++) {
                Map<String, Object> tMap = tags.get(i);
                stag += JsonUtil.getItemString(tMap, "name");
                if (i != size - 1) {
                    stag += "/";
                }
            }

            tvTags.setText(stag);

            ((TextView) header.findViewById(R.id.tvIntro)).setText(JsonUtil.getItemString(topicInfo, "content"));
            ((TextView) header.findViewById(R.id.tvFavorite)).setText(String.valueOf(JsonUtil.getItemInt(topicInfo, "hits")));
            ((TextView) header.findViewById(R.id.tvComment)).setText(String.valueOf(JsonUtil.getItemInt(topicInfo, "comments")));

            GridViewEx gridEx = (GridViewEx) header.findViewById(R.id.gridEx);
            gridEx.setNumColumns(3);
            gridEx.setHorizontalSpacing(SystemUtils.dipToPixel(this, 5));
            final SquareGrideImgAdapter adapter = new SquareGrideImgAdapter();
            gridEx.setAdapter(adapter);

            gridEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        LookPhotoActivity.launch(view.getContext(), (String) parent.getAdapter().getItem(position), (ArrayList<String>) adapter.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            List<Map<String, Object>> images = (List<Map<String, Object>>) topicInfo.get("images");
            List<String> imgs = new ArrayList<>();
            if(images != null) {
                for (Map<String, Object> it : images) {
                    imgs.add(JsonUtil.getItemString(it, "source"));
                }
            }
                DisplayImageOptions options =
                        new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.drawable.huati)
                                .showImageForEmptyUri(R.drawable.huati)
                                .showImageOnFail(R.drawable.huati)
                                .cacheInMemory(true)
                                .cacheOnDisk(true).considerExifParams(true)
                                .resetViewBeforeLoading(true)
                                .displayer(new FadeInBitmapDisplayer(300))
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                                .build();
                HImageLoader.displayImage((images != null && images.size() > 0)  ? JsonUtil.getItemString(images.get(0), "source"):"", backdrop, options);
            if(imgs.size() > 0) {
                shareHelper.setImageUrl(imgs.get(0));
            }
            adapter.setData(imgs);
        }


    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        pageCur = 0;
        mAdapter.clear();
        pushEvent(new TopicInfoRunner(topicId));
        pushEvent(new TopicCommentListRunner(topicId, pageCur));
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        pushEvent(new TopicCommentListRunner(topicId, pageCur));
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new TopicCommentAdapter(this);
        RefreshRecyclerAdapterManager manager = setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.BOTTOM);
        header = LayoutInflater.from(this).inflate(R.layout.adapter_topicdetails_index, null);
        manager.addHeaderView(header);

    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topicdetails, menu);
        menuFavorite = menu.findItem(R.id.menu1);
        return true;
    }

    MenuItem menuFavorite;
    /**
     * Toolbar 菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu1) {
            if(JsonUtil.getItemInt(topicInfo, "isfavorite") == 1) {
                pushEvent(new TopicDelCollectRunner(topicId));
            } else {
                pushEvent(new TopicCollectRunner(topicId));
            }
//        } else if(id == R.id.menu2) {
//            if(topicInfo != null) {
//                shareHelper.showShareDialog(null, tvSubject.getText().toString(), JsonUtil.getItemString(topicInfo, "content"));
//            }
        } else if(id == R.id.menu3) {
            onShowCommentView();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_INFO:
                onRefreshCompleted();
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    topicInfo = (Map<String, Object>) map.get("topicdetail");
                    setTopicInfo();
                }
                break;
            case XEventCode.HTTP_TOPIC_COMMENT_LIST:
                onRefreshCompleted();
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> reviews = (List<Map<String, Object>>) map.get("reviews");
                    hasMore(reviews != null && reviews.size() >= 10);
                    if(reviews == null || reviews.size() == 0) {
                        pageCur -= 1;
                    }
                    mAdapter.addData(reviews);
                } else {
                    pageCur -= 1;
                }
                break;
            case XEventCode.HTTP_TOPIC_COLLECT:
            case XEventCode.HTTP_TOPIC_DELCOLLECT:
                if (event.isSuccess()) {
                    onPullDown();
                    CustomToast.showRightToast(this, event.getEventCode() == XEventCode.HTTP_TOPIC_DELCOLLECT ? "已取消收藏" : "已收藏");
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("操作失败"));
                }
                break;
            case XEventCode.HTTP_TOPIC_COMMENT:
                if (event.isSuccess()) {
                    onCommentBackBtnClick();
                    onPullDown();
                }
                break;
        }
    }

    @Override
    public void onCommentBackBtnClick() {
        vComment.setVisibility(View.GONE);
        mCommentView.setTxt("", 0, "");
    }

    @Override
    public void onShowCommentView(Object... args) {
        vComment.setVisibility(View.VISIBLE);
        showSoftInput(mCommentView.getEt());
//        mCommentView.setTag(args[0]);
        mCommentView.setTxt("", 0, "");
    }

    @Override
    public void onCommentBtnClick(Object... args) {
        hidenSoftInput(mCommentView.getEt());
        if(args != null) {
            publishComment(topicId, (String) args[2]);
        }
    }

    private void publishComment(int topicId, String comment) {
        pushEvent(new TopicCommentRunner(topicId, comment));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(shareHelper != null) {
            shareHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(shareHelper != null) {
            shareHelper.handleWeiboResponse(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(TopicDetailsActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(TopicDetailsActivity.this);
    }
}
