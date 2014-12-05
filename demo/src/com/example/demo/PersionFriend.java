package com.example.demo;

import java.io.Serializable;

import android.graphics.Bitmap;


public class PersionFriend implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 联系人的还原id
	 */
	public String contact_id;
	
	public String ids;
	public String name;
	public String pinyin;
	public Bitmap contact_photo; // 联系人照片

	public String friendName; // 联系人名字

	public String contact_number; // 联系人电话号码
	/**
	 * 方式 0的时候添加 ，1的时候邀请
	 */
	public int type;
	/**
	 * 在新的朋友里面的字段
	 */
	public String friendCompanyName;
	/**
	 * 组织成员的类型
	 */
	public String teamType;
	/**
	 * 方式
	 */
	public String typeContent;
	/**
	 * 图片的地址
	 */
	public  String imageUrl;
	/**
	 * 当前手机号的用户
	 */
	public String merid;
	/**
	 * 备注的名字
	 */
	public String remark;
	/**
	 * 登陆的字段
	 */
	public String mer_lognam;
}
