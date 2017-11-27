package com.chantyou.janemarried.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chantyou.janemarried.R;


/**
 * 联系人 右侧的导航菜单
 * 
 * @author FX_SKY 2012.11.15
 * 
 */
public class MyLetterListView extends View {

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	String[] keyword = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z" };
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
	
	public static final byte FINGER_ACTION_DOWN = -3;  
    public static final byte FINGER_ACTION_MOVE = -2;  
    public static final byte FINGER_ACTION_UP = -1;  

	public MyLetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLetterListView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#35e4eff9"));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / keyword.length;
		for (int i = 0; i < keyword.length; i++) {
			paint.setColor(getResources().getColor(R.color.black));	//设置字体的颜色
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_12));//设置字体的大小
			paint.setAntiAlias(true);
			if (i == choose) {//如果字母被选中的话字体就变大并且颜色变为红色
				paint.setColor(Color.parseColor("#ef2a3d"));
				paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_20));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(keyword[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(keyword[i], xPos, yPos, paint);
			paint.reset();// 重置Paint
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * keyword.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < keyword.length) {
					listener.onTouchingLetterChanged(c,keyword[c],FINGER_ACTION_DOWN);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < keyword.length) {
					listener.onTouchingLetterChanged(c,keyword[c],FINGER_ACTION_MOVE);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			listener.onTouchingLetterChanged(-1,null,FINGER_ACTION_UP);
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(int selectionIndex, String sectionLetter, int state);
	}

}

