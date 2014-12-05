package com.newgame.sdk.utils;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.demo.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class DialogCalendar {
	private static final String TAG = DialogCalendar.class.getSimpleName();
	Dialog dlg = null;
	View view = null;

	private static DialogCalendar instance = null;
	private static Activity mContext;
	private Calendar mCalendar;

	private CalendarUtils calendarCallback = null;

	private DialogCalendar() {
	}

	public synchronized static DialogCalendar getInstance(Activity context) {
		mContext = context;
		if (instance == null) {
			instance = new DialogCalendar();
		}
		return instance;
	}

	public void setCallback(CalendarUtils cu) {
		calendarCallback = cu;
	}

	public void showDialog(Calendar calendar) {
		cancleDialog();
		mCalendar = calendar;
		if (dlg == null) {
			init();
		}
		dlg = new Dialog(mContext);
		dlg.setContentView(view);
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
	}

	public void cancleDialog() {
		if (dlg != null) {
			dlg.cancel();
			dlg = null;
			mCalendar = null;
		}
	}

	private CalendarView calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0; // 每次点击按钮，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 点击超过一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	// private String currentDate = "";

	// 上一月和下一月的按钮
	Button previous;
	Button next;
	LinearLayout main;

	// SimpleDateFormat format1 = new SimpleDateFormat("yyyy MM");
	// SimpleDateFormat format2 = new SimpleDateFormat("MMM yyyy", Locale.US);
	private void init() {
		view = View.inflate(mContext, R.layout.dlg_calender, null);
		year_c = mCalendar.get(Calendar.YEAR);
		month_c = mCalendar.get(Calendar.MONTH);
		day_c = mCalendar.get(Calendar.DAY_OF_MONTH);
		Log.i(TAG, "init    year_c=" + year_c + "    month_c=" + month_c
				+ "    day_c=" + day_c);
		jumpMonth = 0;
		jumpYear = 0;
		calV = new CalendarView(mContext, mContext.getResources(), jumpMonth,
				jumpYear, year_c, month_c + 1, day_c);
		calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
		gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setBackgroundResource(R.color.red);
		gridView.setPadding(1, 1, 1, 1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				String scheduleDay = calV.getDateByClickItem(position).split(
						"\\.")[0]; // 这一天的阳历
				String titleYear = calV.getShowYear();
				String titleMonth = calV.getShowMonth();
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();

				if (position >= startPosition && position <= endPosition) {
					Toast.makeText(
							mContext,
							titleYear + "年" + titleMonth + "月" + scheduleDay
									+ "日", Toast.LENGTH_LONG).show();
					// calV.notifyDataSetChanged();
					if (calendarCallback != null) {
						calendarCallback.seletData(titleYear, titleMonth,
								scheduleDay);
					}
					// cancleDialog();
				}
				// else if (position < startPosition) {
				// getPreviousMonth();
				// } else if (position > endPosition) {
				// getNextMonth();
				// }
				// else {
				// Toast.makeText(MainActivity.this, "No", Toast.LENGTH_LONG)
				// .show();
				// }
			}
		});
		gridView.setAdapter(calV);
		previous = (Button) view.findViewById(R.id.previous);
		next = (Button) view.findViewById(R.id.next);
		main = (LinearLayout) view.findViewById(R.id.main);
		topText = (TextView) view.findViewById(R.id.toptext);
		addTextToTopTextView(topText);
		gridView.setOnTouchListener(new OnTouchListener() {
			float downXPoint = -1;
			float downYPoint = -1;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					downXPoint = event.getX();
					downYPoint = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					float moveSizeX = downXPoint - event.getX();
					float moveSizeY = Math.abs(downYPoint - event.getY());
					if (moveSizeX > 150 &&Math.abs(moveSizeX)>moveSizeY ) {

						getNextMonth();
						return true;
					} else if (moveSizeX < -100 &&Math.abs(moveSizeX)>moveSizeY ) {
						getPreviousMonth();
						return true;
					}
					break;
				case MotionEvent.ACTION_MOVE:
				}
				return false;
			}
		});
		previous.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPreviousMonth();
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getNextMonth();
			}
		});
	}

	/**
	 * 上个月
	 */
	private void getPreviousMonth() {
		jumpMonth--;
		calV = new CalendarView(mContext, mContext.getResources(), jumpMonth,
				jumpYear, year_c, month_c + 1, day_c);
		calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
		calendarCallback.doPrevious("" + year_c, "" + (month_c + jumpMonth), ""
				+ day_c);
	}

	/**
	 * 下个月
	 */
	private void getNextMonth() {
		jumpMonth++;
		calV = new CalendarView(mContext, mContext.getResources(), jumpMonth,
				jumpYear, year_c, month_c + 1, day_c);
		calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
		calendarCallback.doNext("" + year_c, "" + (month_c + jumpMonth), ""
				+ day_c);
	}

	private void addTextToTopTextView(TextView view) {
		String datestr = "" + calV.getShowYear() + "年" + calV.getShowMonth()
				+ "月";
		System.out.println("data=" + datestr);
		view.setText(datestr);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	/**
	 * 设置开始和结束日期 1-－31
	 * 
	 * @param startDay
	 * @param endDay
	 */
	public void setStartAndEnd(ArrayList<PlaybackGetDateListItem> mlist) {
		calV.setStartAndEnd(mlist);
	}

	/**
	 * 判断是否是当前月
	 * 
	 * @param syear
	 * @param smonth
	 * @return
	 */
	public boolean isNowMonth(String syear, String smonth) {
		Log.i(TAG, "isNowMonth     syear=" + syear + "     smonth=" + smonth
				+ "     calV.getShowMonth().trim()="
				+ calV.getShowMonth().trim()
				+ "    calV.getShowMonth().trim()="
				+ calV.getShowMonth().trim());
		if ((calV.getShowYear().trim() + calV.getShowMonth().trim())
				.equals(syear.trim() + smonth.trim())) {
			return true;
		}
		return false;
	}

	public void doRefresh() {
		calV.notifyDataSetChanged();
	}

}
