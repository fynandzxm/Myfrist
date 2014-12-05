package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.newgame.sdk.utils.CalendarUtils;
import com.newgame.sdk.utils.CalendarView;
import com.newgame.sdk.utils.PlaybackGetDateListItem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarActivity extends Activity implements CalendarUtils {
	private GridView gridView;
	private Calendar mCalendar;
	private CalendarView calV = null;
	private TextView topText = null;
	private static int jumpMonth = 0; // 每次点击按钮，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 点击超过一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private Button previous;
	private Button next;
	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dlg_calender);
		initView();
		initClick();
	}

	private void initClick() {
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

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				LinearLayout lv = (LinearLayout) view.findViewById(R.id.Ll);
				TextView tvView = (TextView) view.findViewById(R.id.day);
				String day = tvView.getText().toString();
				String scheduleDay = calV.getDateByClickItem(position).split(
						"\\.")[0]; // 这一天的阳历
				String titleYear = calV.getShowYear();
				String titleMonth = calV.getShowMonth();
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (position >= startPosition && position <= endPosition) {
					if (currentY == year_c && currentM <= (month_c + 1)
							&& Integer.parseInt(day) < day_c) {

					} else {
						List<PlaybackGetDateListItem> list = new ArrayList<PlaybackGetDateListItem>();

						PlaybackGetDateListItem playbackGetDateListItem = new PlaybackGetDateListItem();
						playbackGetDateListItem.start_day = position;
						list.add(playbackGetDateListItem);
						calV.setStartAndEnd(list);
						calV.notifyDataSetChanged();
					}
					seletData(titleYear, titleMonth, scheduleDay);
				}
			}
		});
		/**
		 * GridView的ontouch事件
		 */
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
					if (moveSizeX > 150 && Math.abs(moveSizeX) > moveSizeY) {
						getNextMonth();
						return true;
					} else if (moveSizeX < -100
							&& Math.abs(moveSizeX) > moveSizeY) {
						getPreviousMonth();
						return true;
					}
					break;
				case MotionEvent.ACTION_MOVE:
				}
				return false;
			}
		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setPadding(1, 1, 1, 1);
		mCalendar = Calendar.getInstance();
		previous = (Button) findViewById(R.id.previous);
		next = (Button) findViewById(R.id.next);
		// main = (LinearLayout) view.findViewById(R.id.main);
		topText = (TextView) findViewById(R.id.toptext);
		year_c = mCalendar.get(Calendar.YEAR);
		month_c = mCalendar.get(Calendar.MONTH);
		day_c = mCalendar.get(Calendar.DAY_OF_MONTH);
		jumpMonth = 0;
		jumpYear = 0;
		calV = new CalendarView(this, getResources(), jumpMonth, jumpYear,
				year_c, month_c + 1, day_c);
		calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
		currentY = Integer.parseInt(calV.getShowYear());
		currentM = Integer.parseInt(calV.getShowMonth());
		addTextToTopTextView(topText);
		gridView.setAdapter(calV);
	}

	private void addTextToTopTextView(TextView view) {
		String datestr = "" + calV.getShowYear() + "年" + calV.getShowMonth()
				+ "月";
		System.out.println("data=" + datestr);
		view.setText(datestr);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	private int currentY;
	private int currentM;
	/**
	 * 上个月
	 */
	private void getPreviousMonth() {
		if (currentY > year_c
				|| (currentY == year_c && currentM > (month_c + 1))) {
			jumpMonth--;
			calV = new CalendarView(this, getResources(), jumpMonth, jumpYear,
					year_c, month_c + 1, day_c);
			calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
			gridView.setAdapter(calV);
			showback();
			addTextToTopTextView(topText);
			doPrevious("" + year_c, "" + (month_c + jumpMonth), "" + day_c);
		}
	}
	/**
	 * 判断是否显示返回的按钮
	 */
	private void showback() {
		currentY = Integer.parseInt(calV.getShowYear());
		currentM = Integer.parseInt(calV.getShowMonth());
		if (currentY > year_c
				|| (currentY == year_c && currentM > (month_c + 1))) {
			previous.setVisibility(View.VISIBLE);
		} else {
			previous.setVisibility(View.GONE);
		}
	}

	/**
	 * 下个月
	 */
	private void getNextMonth() {
		previous.setVisibility(View.VISIBLE);
		jumpMonth++;
		calV = new CalendarView(this, getResources(), jumpMonth, jumpYear,
				year_c, month_c + 1, day_c);
		calV.setSelectedDay("" + year_c + (month_c + 1) + day_c);
		gridView.setAdapter(calV);
		showback();
		addTextToTopTextView(topText);
		doNext("" + year_c, "" + (month_c + jumpMonth), "" + day_c);
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
		if ((calV.getShowYear().trim() + calV.getShowMonth().trim())
				.equals(syear.trim() + smonth.trim())) {
			return true;
		}
		return false;
	}

	public void doRefresh() {
		calV.notifyDataSetChanged();
	}

	public String format(int i) {
		String s = "" + i;
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}

	@Override
	public void seletData(String year, String month, String day) {
		mYear = Integer.parseInt(year);
		mMonth = Integer.parseInt(month) - 1;
		mDay = Integer.parseInt(day);
		System.out.println("m=" + mYear + "mm=" + mMonth + "md=" + mDay);
		mCalendar.set(Calendar.YEAR, mYear);
		mCalendar.set(Calendar.MONTH, mMonth);
		mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
		String strtmp = format(mYear) + "-" + format(mMonth + 1) + "-"
				+ format(mDay) + " ";
	}

	@Override
	public void doNext(String year, String month, String dat) {
		mYear = Integer.parseInt(year);
		mMonth = Integer.parseInt(month);
	}

	@Override
	public void doPrevious(String year, String month, String dat) {
		mYear = Integer.parseInt(year);
		mMonth = Integer.parseInt(month);
	}
}
