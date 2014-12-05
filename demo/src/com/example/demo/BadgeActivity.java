package com.example.demo;

import com.readystatesoftware.viewbadger.BadgeView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BadgeActivity extends Activity {
	private TextView tv1;
	private BadgeView badgeView1;
	private BadgeView badgeView;
	private Button btn1;
	private BadgeView bv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_badge);
		tv1 = (TextView) findViewById(R.id.tv1);
		btn1 = (Button) findViewById(R.id.btn1);
		bv1 = (BadgeView) findViewById(R.id.bv1);
//		badgeView = new BadgeView(this, tv1);
		badgeView=new BadgeView(this);
//		badgeView.setText("12");
//		badgeView.setTextSize(12);
	
		badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		// badgeView.setBackgroundResource(R.drawable.badge_ifaux);
		badgeView.show();
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				badgeView.toggle();
			}
		});
		badgeView1 = new BadgeView(this, btn1);
		badgeView1.setText("12");
		badgeView1.setTextSize(12);
		badgeView1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badgeView1.setBadgeMargin(-5);
		badgeView1.show();
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (badgeView1.isShown()) {
					badgeView1.toggle();
				}
			}
		});
		bv1.setText("23");
		bv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(BadgeActivity.this, "被点击了", Toast.LENGTH_LONG)
						.show();
			}
		});
	}
}
