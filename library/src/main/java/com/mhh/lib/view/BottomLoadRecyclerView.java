//package com.mhh.lib.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.chantyou.janemarried.R;
//import com.mhh.lib.utils.SystemUtils;
//
///**
// * Created by j_turn on 2015/12/10.
// */
//public class BottomLoadRecyclerView extends LinearLayout implements View.OnClickListener {
//
//    private RecyclerView mRecyclerView;
//    private RecyclerView.OnScrollListener mInternalOnScrollListener;
//
//    private OnScrollBottomListener mBottomLoadListener;
//
//    private View mLoadView;
//    private View mProgressBar;
//    private TextView mTextView;
//
//    private boolean isBottomLoadEnable = false;
//    private boolean mIsLoading;
//
//    private boolean mIsAutoLoad = true;
//
//    private boolean mHasMore = true;
//
//    public BottomLoadRecyclerView(Context context) {
//        super(context);
//        init(null);
//    }
//
//    public BottomLoadRecyclerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs);
//    }
//
//    public BottomLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(attrs);
//    }
//
//    private void init(AttributeSet attrs) {
//        setOrientation(LinearLayout.VERTICAL);
//        mRecyclerView = new RecyclerView(getContext());
//        mInternalOnScrollListener = new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(isBottomLoadEnable) {
//                    checkBottomLoad();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        };
//        mRecyclerView.addOnScrollListener(mInternalOnScrollListener);
//        addView(mRecyclerView);
//        if(attrs != null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BottomLoadRecyclerView);
//            isBottomLoadEnable = a.getBoolean(R.styleable.BottomLoadRecyclerView_enablemore, isBottomLoadEnable);
//
//            a.recycle();
//        }
//
//        initFootView();
//    }
//
//    private void initFootView() {
//        if(isBottomLoadEnable) {
//            final View footer = LayoutInflater.from(getContext()).inflate(
//                    R.layout.footer_bottomload, null);
//            mProgressBar = footer.findViewById(R.id.pb);
//            mTextView = (TextView) footer.findViewById(R.id.tv);
//            footer.setOnClickListener(this);
//            addView(footer);
//
//            mLoadView = footer;
//            mLoadView.setVisibility(GONE);
//        }
//    }
//
//    public RecyclerView getRecyclerView() {
//        return  mRecyclerView;
//    }
//
//    @SuppressWarnings("deprecation")
//    public void setIsAutoLoad(boolean bAuto) {
//        if(!isBottomLoadEnable) {
//            isBottomLoadEnable = true;
//            initFootView();
//        }
//        mIsAutoLoad = bAuto;
//        if (mIsAutoLoad) {
//            mProgressBar.setVisibility(View.VISIBLE);
//            mTextView.setText(R.string.bottom_load_loading);
//            mLoadView.setBackgroundDrawable(null);
//        } else {
//            if (!mIsLoading) {
//                mProgressBar.setVisibility(View.GONE);
//                mLoadView.setBackgroundResource(R.drawable.foot_bg);
//                mTextView
//                        .setMinHeight(SystemUtils.dipToPixel(48));
//                mTextView.setText(R.string.bottom_load_loadmore1);
//            }
//        }
//    }
//
//    public void endLoad() {
//        mIsLoading = false;
//        if (!mIsAutoLoad && mProgressBar != null) {
//            mProgressBar.setVisibility(View.GONE);
//        }
//    }
////
////    public void hideBottomView() {
////        mLoadView.setVisibility(View.GONE);
////    }
////
////    public void showBottomView() {
////        mLoadView.setVisibility(View.VISIBLE);
////    }
//
//    public void hasMoreLoad(boolean bHasMore) {
//        mHasMore = bHasMore;
//        if(mLoadView == null) {
//            return;
//        }
//        mLoadView.setVisibility(View.VISIBLE);
//        if (bHasMore) {
//            mProgressBar.setVisibility(View.GONE);
//            if(!mIsAutoLoad) {
//                mLoadView.setBackgroundResource(R.drawable.foot_bg);
//            }
//            if (mIsAutoLoad) {
//                mTextView.setText(null);
//            } else {
//                mTextView.setText(R.string.bottom_load_loadmore1);
//            }
//        } else {
//            mProgressBar.setVisibility(View.GONE);
//            mTextView.setText(R.string.bottom_load_nomore);
//            mLoadView.setBackgroundResource(R.color.transparent);
//        }
//    }
//
//    public void setOnScrollBottomListener(OnScrollBottomListener listener) {
//        mBottomLoadListener = listener;
//    }
//    public void setLoadFail() {
//        if(mLoadView != null) {
//            mLoadView.setVisibility(View.VISIBLE);
//            mProgressBar.setVisibility(View.GONE);
//            mTextView.setText(R.string.bottom_load_fail);
//        }
//    }
//
//    public boolean hasMore() {
//        return mHasMore;
//    }
//
//    private void checkBottomLoad() {
//        mIsLoading = false;
//        if (!mIsLoading && mRecyclerView != null && mLoadView != null) {
//            if (getLastVisiblePosition(mRecyclerView.getLayoutManager()) == mRecyclerView.getChildCount() - 1) {
//                mIsLoading = true;
//                mProgressBar.setVisibility(View.VISIBLE);
//                mTextView.setText(R.string.bottom_load_loading);
//                if (mBottomLoadListener != null) {
//                    mBottomLoadListener.onBottomLoad(mRecyclerView);
//                }
//            }
//        }
//    }
//
//
//
//    private int getLastVisiblePosition(RecyclerView.LayoutManager layoutManager) {
//        if (layoutManager instanceof LinearLayoutManager) {
//           return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
//        } else if (layoutManager instanceof GridLayoutManager) {
//            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
//        } else {
//            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if (!mIsAutoLoad) {
//            if (v == mLoadView && mHasMore) {
//                mProgressBar.setVisibility(View.VISIBLE);
//                mIsLoading = true;
//                if (mBottomLoadListener != null) {
//                    mBottomLoadListener.onBottomLoad(mRecyclerView);
//                }
//            }
//        }
//    }
//
//    public static interface OnScrollBottomListener {
//        public void onBottomLoad(RecyclerView listView);
//    }
//}
