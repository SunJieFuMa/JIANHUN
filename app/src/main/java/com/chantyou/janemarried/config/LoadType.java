package com.chantyou.janemarried.config;

/**
 * Created by Administrator on 2016/7/15.
 */

//显示类型

public interface LoadType {
    public final int Load = 1;//首次加载
    public final int LoadMore = 2;//加载更多
    public final int Refresh = 3;//刷新
    public final int Loop = 4;//轮询
}
