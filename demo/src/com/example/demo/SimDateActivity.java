package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SimDateActivity extends Activity {
	private ListView listView;
	private TelephonyManager manager;

	private List<String> item = new ArrayList<String>();

	private List<String> value = new ArrayList<String>();

	private List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sim);
		listView = (ListView) findViewById(R.id.listView);
		manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		item.add("SIM卡状态");

		switch (manager.getSimState()) {

		case TelephonyManager.SIM_STATE_READY:

			value.add("良好");

			break;

		case TelephonyManager.SIM_STATE_ABSENT:

			value.add("无SIM卡");

			break;

		default:

			value.add("SIM卡被锁定或未知状态");

			break;

		}

		item.add("SIM卡序列号");

		if (manager.getSimSerialNumber() != null)

			value.add(manager.getSimSerialNumber());

		else

			value.add("无法取得");

		item.add("SIM卡提供商代码");

		if (manager.getSimOperator() != null)

			value.add(manager.getSimOperator());

		else

			value.add("无法取得");

		item.add("SIM卡提供商名称");

		if (manager.getSimOperatorName() != null)

			value.add(manager.getSimOperatorName());

		else

			value.add("无法取得");

		item.add("SIM卡国别");

		if (manager.getSimCountryIso() != null)

			value.add(manager.getSimCountryIso());

		else

			value.add("无法取得");

		Iterator<String> itItem = item.iterator();

		Iterator<String> itValue = value.iterator();

		while (itItem.hasNext() && itValue.hasNext()) {

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("item", itItem.next());

			map.put("value", itValue.next());

			mData.add(map);

		}

		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),

		mData, R.layout.list_item, new String[] { "item", "value" },

		new int[] { R.id.title, R.id.value });

		listView.setAdapter(adapter);

	}

}
