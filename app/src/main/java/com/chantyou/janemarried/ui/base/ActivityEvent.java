package com.chantyou.janemarried.ui.base;

public interface ActivityEvent {

	// 显示评论输入框
	public void onShowCommentView(Object... args);
	// 点击评论按钮
	public void onCommentBtnClick(Object... args);
	
	public void onCommentBackBtnClick();
}
