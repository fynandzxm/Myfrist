package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ScrollView;

public class ScrollActivity extends Activity{
	private MylistView listView;
	private MyGridView gridView;
	private ListAdapter listAdapter;
	private GridAdapter gridAdapter;
	private ScrollView scrollView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll);
		listView=(MylistView) findViewById(R.id.listview1);
		gridView=(MyGridView) findViewById(R.id.gridView1);
		scrollView1=(ScrollView) findViewById(R.id.scrollView1);
		scrollView1.smoothScrollTo(0, 0);
		List<String>list=new ArrayList<String>();
		for(int i=0;i<20;i++){
			list.add("当前的item=="+i);
		}
		listAdapter=new ListAdapter(this, list);
		gridAdapter=new GridAdapter(this, list);
		listView.setAdapter(listAdapter);
		gridView.setAdapter(gridAdapter);
	}
}
