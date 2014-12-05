package com.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;

public class ratingBar extends RatingBar {
	public ratingBar(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}

	public ratingBar(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public ratingBar(Context context) {
		super(context);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(160, heightMeasureSpec);

	}
}
