package com.chantyou.janemarried.ui.view;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.ActivityEvent;
import com.mhh.lib.framework.CustomToast;

public class CommentView implements OnClickListener {
	
	private final TextView tRname;
	private final EditText et;
	private long atUserId = 0;
	
	private Object tag;
	
	public void setTag(Object tag) {
		this.tag = tag;
	}
	
	public Object getTag() {
		return tag;
	}

	public CommentView(final View cView) {
		tRname = (TextView) cView.findViewById(R.id.trName);
		et = (EditText) cView.findViewById(R.id.et);
		et.setImeOptions(EditorInfo.IME_ACTION_DONE);
		
		final View vDel = cView.findViewById(R.id.ivDel);
		
		TextWatcher watcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if(TextUtils.isEmpty(et.getText().toString())
						&& TextUtils.isEmpty(tRname.getText().toString())) {
					vDel.setVisibility(View.GONE);
				} else {
					vDel.setVisibility(View.VISIBLE);
				}
			}
		};
		et.addTextChangedListener(watcher);
		tRname.addTextChangedListener(watcher);
		
		et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE){
					onClick(cView.findViewById(R.id.btnComment));
					return true;
				}
				return false;
			}
		});

		cView.findViewById(R.id.ivBack).setOnClickListener(this);
		cView.findViewById(R.id.btnComment).setOnClickListener(this);
		vDel.setOnClickListener(this);
	}
	
	public EditText getEt() {
		return et;
	}
	
	public void setTxt(String txt, long atUserId, String rName) {
		if(et != null) {
			et.setText(txt);
		}
		this.atUserId = atUserId;
		if(atUserId == 0) {
			tRname.setText("");
		} else {
			tRname.setText("回复"+rName+":");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			if(v.getContext() != null && v.getContext() instanceof ActivityEvent) {
				((ActivityEvent) v.getContext()).onCommentBackBtnClick();
			}
			break;
		case R.id.ivDel:
			setTxt("", 0, "");
			break;
		case R.id.btnComment:
			if(TextUtils.isEmpty(et.getText().toString())) {
				CustomToast.showWorningToast(v.getContext(), "输入评论的内容");
				return;
			}
			if(v.getContext() != null && v.getContext() instanceof ActivityEvent) {
				((ActivityEvent) v.getContext()).onCommentBtnClick(v, atUserId, et.getText().toString());
			}
			break;
		default:
			break;
		}
	}
}
