package com.newgame.sdk.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.R;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 日历gridview中的每一个item显示的textview
 */
public class CalendarView extends BaseAdapter {

	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int lastDaysOfMonth = 0; // 上一个月的总天数
	private Context context;
	private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
	private SpecialCalendar sc = null;

	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	private String selectedDay = null;// 用于存储选中的日期 yyyymmdd
	private int[] schDateTagFlag = null; // 存储当月所有的日程日期

	private String showYear = ""; // 用于在头部显示的年份
	private String showMonth = ""; // 用于在头部显示的月份
	private String animalsYear = "";
	private String leapMonth = ""; // 闰哪一个月
	private String cyclical = ""; // 天干地支
	private ArrayList<PlaybackGetDateListItem> myList = null;

	SpecialCalendar spe = new SpecialCalendar();
	private int year_c;
	private int month_c;
	private int day_c;

	public CalendarView(Context context, Resources rs, int jumpMonth,
			int jumpYear, int year_c, int month_c, int day_c) {
		this.context = context;
		this.day_c = day_c;
		this.month_c = month_c;
		this.year_c = year_c;
		sc = new SpecialCalendar();
		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月跳转
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月跳转
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		currentYear = String.valueOf(stepYear); // 得到当前的年份
		currentMonth = String.valueOf(stepMonth); // 得到本月
													// （jumpMonth为跳动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(day_c); // 得到当前日期是哪天

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));

	}

	@Override
	public int getCount() {
		return 42;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_items, null);
			holder = new ViewHoler();
			holder.tView = (TextView) convertView.findViewById(R.id.day);
			holder.Ll = (LinearLayout) convertView.findViewById(R.id.Ll);
			convertView.setTag(holder);
		} else {
			holder = (ViewHoler) convertView.getTag();
		}

		String d = dayNumber[position].split("\\.")[0];
		SpannableString sp = new SpannableString(d);
		holder.tView.setText(sp);
		holder.tView.setTextColor(context.getResources().getColor(
				android.R.color.darker_gray));
		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			// 当前月信息显示
			holder.tView.setTextColor(context.getResources().getColor(
					R.color.s9c9c9c));// 当月
		}
		// 设置选择的天的背景
		if (("" + showYear + showMonth + (position + 1 - dayOfWeek))
				.equals(selectedDay)) {

			holder.tView.setTextColor(context.getResources().getColor(
					R.color.sFD902B));
			holder.Ll.setBackgroundResource(R.drawable.day_item);
		} else {
			if (Integer.parseInt(currentYear) > year_c
					|| Integer.parseInt(currentMonth) > month_c
					|| Integer.parseInt(d) > day_c) {
				holder.Ll.setBackgroundColor(context.getResources().getColor(
						R.color.white));
			} else {
				holder.Ll.setBackgroundColor(context.getResources().getColor(
						R.color.transparent));
			}
		}
		holder.tView.setTextColor(context.getResources().getColor(
				R.color.s9c9c9c));
		if (myList != null) {
			int mSize = myList.size();
			if (mSize > 0) {
				for (int i = 0; i < mSize; i++) {
					PlaybackGetDateListItem saei = myList.get(i);
					if (saei.start_day==position) {
//						.tView.setTextColor(context.getResources()
//								.getColor(android.R.color.holo_green_light));
						holder.tView.setTextColor(context.getResources().getColor(
								R.color.white));
						holder.Ll.setBackgroundColor(context.getResources().getColor(
								R.color.sFD902B));
					}
				}
			}
		}
		if (position < dayOfWeek || position >= daysOfMonth + dayOfWeek) {
			// 设置上一月和下一月的背景
			holder.tView.setTextColor(context.getResources().getColor(
					android.R.color.darker_gray));
			holder.Ll.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
		}
		return convertView;
	}

	
	// 得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
		getweek(year, month);
	}

	private ArrayList<ScheduleDateTag> getTagDate(int year, int month) {
		ArrayList<ScheduleDateTag> dateTagList = new ArrayList<ScheduleDateTag>() {
			private static final long serialVersionUID = -5976649074350323408L;
		};
		int i = 0;
		while (i < 10) {
			int tagID = i;
			int year1 = 2012;
			int month1 = 11;
			int day = 2 * (i);
			int scheduleID = i;
			ScheduleDateTag dateTag = new ScheduleDateTag(tagID, year1, month1,
					day, scheduleID);
			dateTagList.add(dateTag);
			i++;
		}
		if (dateTagList != null && dateTagList.size() > 0) {
			return dateTagList;
		}
		return null;
	}

	// 将一个月中的每一天的值添加入数组dayNuber中
	private void getweek(int year, int month) {
		int j = 1;
		int flag = 0;

		// 得到当前月的所有日程日期(这些日期需要标记)
		// dao = new ScheduleDAO(context);
		ArrayList<ScheduleDateTag> dateTagList = this.getTagDate(year, month);
		if (dateTagList != null && dateTagList.size() > 0) {
			schDateTagFlag = new int[dateTagList.size()];
		}

		for (int i = 0; i < dayNumber.length; i++) {
			// 周一
			if (i < dayOfWeek) { // 前一个月
				int temp = lastDaysOfMonth - dayOfWeek + 1;
				dayNumber[i] = (temp + i) + ".";
			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
				dayNumber[i] = i - dayOfWeek + 1 + ".";
				// 对于当前月才去标记当前日期
				// 标记日程日期
				if (dateTagList != null && dateTagList.size() > 0) {
					for (int m = 0; m < dateTagList.size(); m++) {
						ScheduleDateTag dateTag = dateTagList.get(m);
						int matchYear = dateTag.getYear();
						int matchMonth = dateTag.getMonth();
						int matchDay = dateTag.getDay();
						if (matchYear == year && matchMonth == month
								&& matchDay == Integer.parseInt(day)) {
							schDateTagFlag[flag] = i;
							flag++;
						}
					}

				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
			} else { // 下一个月
				dayNumber[i] = j + ".";
				j++;
			}
		}
	}

	class ViewHoler {
		TextView tView;
		LinearLayout Ll;
	}

	public String getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(String selected) {
		this.selectedDay = selected;
	}

	public void matchScheduleDate(int year, int month, int day) {

	}

	/**
	 * 点击每一个item时返回item中的日期
	 * 
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * 
	 * @return
	 */
	public int getStartPositon() {
		return dayOfWeek;
	}

	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * 
	 * @return
	 */
	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth) - 1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}

	/**
	 * 设置开始和结束日期
	 * 
	 * @param startDay
	 * @param endDay
	 *            1--31
	 */
	public void setStartAndEnd(List<PlaybackGetDateListItem> mlist) {
		if (myList == null) {
			myList = new ArrayList<PlaybackGetDateListItem>();
		}
		myList.clear();
		myList.addAll(mlist);
	}
}