package com.example.demo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.RequestCallBack;
import com.newgame.sdk.utils.CalendarUtils;
import com.newgame.sdk.utils.DialogCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JsonAActivity extends Activity {
	private Map<String, Object> map = new HashMap<String, Object>();
	DialogCalendar dc;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m1);
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// send();
			}
		});
		m_calendar = Calendar.getInstance();
		System.out.println("你那=" + m_calendar.get(Calendar.YEAR)
				+ m_calendar.get(Calendar.MONTH));

		dc = DialogCalendar.getInstance(this);
		dc.setCallback(caldenerCallback);
		dc.showDialog(m_calendar);
	}

	private int mYear;
	private int mMonth;
	private int mDay;
	private Calendar m_calendar;
	/**
	 * 日历操作回调
	 */
	CalendarUtils caldenerCallback = new CalendarUtils() {

		@Override
		public void seletData(String year, String month, String day) {
			mYear = Integer.parseInt(year);
			mMonth = Integer.parseInt(month) - 1;
			mDay = Integer.parseInt(day);
			System.out.println("m=" + mYear + "mm=" + mMonth + "md=" + mDay);
			// Log.i(TAG, "seletData    mMonth=" + mMonth);
			m_calendar.set(Calendar.YEAR, mYear);
			m_calendar.set(Calendar.MONTH, mMonth);
			m_calendar.set(Calendar.DAY_OF_MONTH, mDay);
			String strtmp = format(mYear) + "-" + format(mMonth + 1) + "-"
					+ format(mDay) + " ";
			// + format(nHour) + ":"
			// + format(nMinute);
			// myMonth = "" + format(mYear) + "" + format(mMonth + 1);
			// myDay = "" + format(mYear) + "" + format(mMonth + 1) + ""
			// + format(mDay);

			btn1.setText(strtmp);
			// doCheckPlayBack(CHECK_DAY);
		}

		@Override
		public void doNext(String year, String month, String dat) {

			mYear = Integer.parseInt(year);
			mMonth = Integer.parseInt(month);
			// Log.i(TAG, "doNext    mMonth=" + mMonth + "     mYear=" + mYear);
			// myMonth = "" + format(mYear) + "" + format(mMonth + 1);
			// doCheckPlayBack(CHECK_MOUTH);
		}

		@Override
		public void doPrevious(String year, String month, String dat) {
			mYear = Integer.parseInt(year);
			mMonth = Integer.parseInt(month);
			// Log.i(TAG, "doPrevious    mMonth=" + mMonth + "     mYear=" +
			// mYear);
			// myMonth = "" + format(mYear) + "" + format(mMonth + 1);
			// doCheckPlayBack(CHECK_MOUTH);
		}
	};

	public String format(int i) {
		String s = "" + i;
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}
	// private void send() {
	// HttpUtils httpUtils = new HttpUtils();
	// httpUtils.send(HttpMethod.GET,
	// "http://www.ksousou.com/invoke/list2.asp",
	// new RequestCallBack<String>() {
	//
	// @Override
	// public void onSuccess(String result) {
	// System.out.println("result="+result);
	//
	// try {
	// JSONObject json=new JSONObject(result);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// });
	// }
}
