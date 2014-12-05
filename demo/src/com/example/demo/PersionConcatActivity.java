package com.example.demo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.demo.PersionContactAdapter.OnItemOnclick;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class PersionConcatActivity extends Activity{
	private EditText et_persion_contact;
	private ListView persion_list;
	private PersionContactAdapter persionContactAdapter;
	/**
	 * 先和我的商友通用一个实体类
	 */
	private List<PersionFriend> list = new ArrayList<PersionFriend>();
	private StringBuilder stringBuilder = new StringBuilder();
	/**
	 * 联系人的数组
	 */
	public static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, // 联系人姓名
			Phone.NUMBER, // 电话号码
			Photo.PHOTO_ID, // 联系人头像
			Phone.CONTACT_ID // ID
	};
	public static final int PHONES_DISPLAY_NAME_INDEX = 0;
	public static final int PHONES_NUMBER_INDEX = 1;
	public static final int PHONES_PHOTO_ID_INDEX = 2;
	public static final int PHONES_CONTACT_ID_INDEX = 3;
	private String[] id;
	private String[]orgname;
	private String []pingyin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.persion_contact);
		initView();
		getContactInformation();
	}
	/**
	 * View的初始化
	 */
	private void initView() {
		et_persion_contact=(EditText) findViewById(R.id.et_persion_contact);
		persion_list=(ListView) findViewById(R.id.persion_list);
	}
	private void getContactInformation() {
//		Uri contact_uri = Phone.CONTENT_URI; // 获得联系人默认uri
//		ContentResolver resolver = this.getContentResolver(); // 获得ContentResolver对象
//		Cursor cursor = resolver.query(contact_uri, PHONES_PROJECTION, null,
//				null, null); // 获取电话本中开始一项光标
//
//		if (null != cursor) {
//			while (cursor.moveToNext()) {
//				// ContactPeson peson = new ContactPeson();
//				PersionFriend peson = new PersionFriend();
//				/*
//				 * 获取电话号码
//				 */
//				String number = cursor.getString(PHONES_NUMBER_INDEX);
//				/*
//				 * 当手机号码为空的或者为空字段 跳过当前循环
//				 */
//				if (TextUtils.isEmpty(number))
//					continue;
//				/*
//				 * 得到联系人名称
//				 */
//				String name = cursor.getString(PHONES_DISPLAY_NAME_INDEX);
//
//				/*
//				 * 得到联系人ID
//				 */
//				Long id = cursor.getLong(PHONES_CONTACT_ID_INDEX);
//				/*
//				 * 得到联系人头像ID
//				 */
//				Long photo = cursor.getLong(PHONES_PHOTO_ID_INDEX);
//				Bitmap contactPhoto = null;
//				if (photo > 0) {
//					Uri uri = ContentUris.withAppendedId(
//							ContactsContract.Contacts.CONTENT_URI, id);
//					InputStream input = ContactsContract.Contacts
//							.openContactPhotoInputStream(resolver, uri);
//					contactPhoto = BitmapFactory.decodeStream(input);
//				} else {
//					contactPhoto = BitmapFactory.decodeResource(getResources(),
//							R.drawable.ic_launcher);
//				}
//
//				peson.contact_number = number;
//				peson.friendName = name;
//				peson.contact_photo = contactPhoto;
//				list.add(peson);
//			}
//
//			cursor.close();
//		}
		PersionFriend persionFriend=new PersionFriend();
		persionFriend.friendName="封延安";
		PersionFriend persionFriend1=new PersionFriend();
		persionFriend1.friendName="张";
		PersionFriend persionFriend2=new PersionFriend();
		persionFriend2.friendName="送礼";
		PersionFriend persionFriend3=new PersionFriend();
		persionFriend3.friendName="大伯";
		PersionFriend persionFriend4=new PersionFriend();
		persionFriend4.friendName="大大";
		PersionFriend persionFriend5=new PersionFriend();
		persionFriend5.friendName="小虾";
			list.add(persionFriend);
			list.add(persionFriend1);
			list.add(persionFriend2);
			list.add(persionFriend3);
			list.add(persionFriend4);
			list.add(persionFriend5);
			id=new String[list.size()];
			orgname = new String[list.size()];
			pingyin = new String[list.size()];
			PersionFriend f=null;
			for(int i=0;i<list.size();i++){
				f=list.get(i);
			
			}
		
		ComparatorFriend comparatorFriend = new ComparatorFriend();
		Collections.sort(list, comparatorFriend);
		System.err.println();
		persionContactAdapter=new PersionContactAdapter(PersionConcatActivity.this,list, new OnItemOnclick() {
			
			@Override
			public void onclick(int type, int position) {
				
			}
		});
		persion_list.setAdapter(persionContactAdapter);
		persionContactAdapter.setOrig_list(list);
		
		et_persion_contact.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				persionContactAdapter.searchTextChanged(et_persion_contact.getText().toString());
			}
		});
	}
	class ComparatorFriend implements Comparator<Object>{

		 public int compare(Object arg0, Object arg1) {
		   PersionFriend persionFriend0=(PersionFriend)arg0;
		   PersionFriend persionFriend1=(PersionFriend)arg1;

		    //首先比较年龄，如果年龄相同，则比较名字
		   String str1 = PingYinUtil.getPingYin(persionFriend0.friendName);
		     String str2 = PingYinUtil.getPingYin(persionFriend1.friendName);
		  int flag=str1.compareTo(str2);
		
		    return flag;
 
		  }
		  
		 }
}
