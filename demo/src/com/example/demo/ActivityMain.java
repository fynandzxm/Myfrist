package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @作者：Administrator
 * @时间：2014-4-2 下午3:51:03
 * @描述：
 */
public class ActivityMain extends Activity {
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ActivityMain.this,
						ActivityCharge.class));
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(i, 1);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("resui=" + requestCode + "resu" + resultCode);
		switch (resultCode) {
		case 1:
			//

			if (data != null) {

				Bundle extras = data.getExtras();

				Bitmap bmp = (Bitmap) extras.get("data");

				iv.setImageBitmap(bmp); // 设置照片现实在界面上

				// hasShootPic = true;//此变量是在提交数据时，验证是否有图片用

			}

			break;
		}
	}
}
