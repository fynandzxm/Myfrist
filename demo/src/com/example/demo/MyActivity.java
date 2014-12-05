package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MyActivity extends Activity{
	private ExpandableListView listView;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity);
	listView=(ExpandableListView) findViewById(R.id.listView);
}
}
