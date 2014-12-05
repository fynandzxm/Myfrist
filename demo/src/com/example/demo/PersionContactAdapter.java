package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 联系人的适配器
 *
 */
public class PersionContactAdapter extends BaseAdapter {
	private List<PersionFriend> list;
	private List<PersionFriend> orig_list;
	private Context context;
	private LayoutInflater inflater;
	private OnItemOnclick onclick;
	private List<PersionFriend>serachlist=new ArrayList<PersionFriend>();
	public PersionContactAdapter(Context context,List<PersionFriend> list,
			OnItemOnclick onclick) {
		setList(list);
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.onclick = onclick;

	}

	
	public List<PersionFriend> getOrig_list() {
		return orig_list;
	}


	public void setOrig_list(List<PersionFriend> orig_list) {
		this.orig_list = orig_list;
	}
	public void setList(List<PersionFriend> list) {
		if (list != null)
			this.list = list;
		else
			this.list = new ArrayList<PersionFriend>();
	}
	/**
	 * 改变
	 */
	public void changeData(List<PersionFriend> list) {
		this.setList(list);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void searchTextChanged(String search) {
		
		serachlist.clear();
		if(search!=null&&search.length() > 0) {
			String oneChar = "";
			for (int i = 0; i < orig_list.size(); i++) {
				List<Integer> indexList = new ArrayList<Integer>();
				List<Boolean> existList = new ArrayList<Boolean>();
				boolean flag = false;
				for (int c = 0; c < search.length(); c++) {
					oneChar = search.substring(c, c + 1).toLowerCase();
					flag = false;
					for (int index = 0; index < orig_list.get(i).friendName
							.length(); index++) {
						if (orig_list.get(i).friendName
								.substring(index, index + 1)
								.equalsIgnoreCase(oneChar)) {
							indexList.add(index);
							flag = true;
						} else if (orig_list.get(i).pinyin
								.substring(index, index + 1)
								.equalsIgnoreCase(oneChar)) {
							indexList.add(index);
							flag = true;
						}
					}
					if (flag) {
						existList.add(flag);
					}
				}
				if (indexList.size() > 1) {
					for (int mmm = 0; mmm < indexList.size(); mmm++) {
						for (int nn = mmm + 1; nn < indexList.size();) {
							if (indexList.get(mmm) == indexList.get(nn)) {
								indexList.remove(nn);
							} else {
								nn++;
							}
						}
					}
					for (int z = 1; z < indexList.size(); z++) {
						if (indexList.get(0) > indexList.get(z)) {
							indexList = new ArrayList<Integer>();
							break;
						}
					}
				}
				if (indexList.size() > 0 & indexList.size() >= search.length()
						& existList.size() == search.length()) {	
					PersionFriend persionFriend=orig_list.get(i);
					serachlist.add(persionFriend);
				}
			}
			changeData(serachlist);
		} else {
			changeData(orig_list);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHodlder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.persion_contact_item, null);
			holder = new ViewHodlder();
			holder.persion_item_contact_iv = (ImageView) convertView
					.findViewById(R.id.persion_item_contact_iv);
			holder.contact_name = (TextView) convertView
					.findViewById(R.id.contact_name);
			holder.contact_add = (Button) convertView
					.findViewById(R.id.contact_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHodlder) convertView.getTag();
		}
		final PersionFriend persionFriend = list.get(position);
		holder.contact_name.setText(persionFriend.friendName);
		/**
		 * 添加
		 */
		holder.contact_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onclick.onclick(persionFriend.type, position);
			}
		});
		return convertView;
	}

	public static interface OnItemOnclick {
		public void onclick(int type, int position);
	}
	public class UnitBean {
		public String id;
		public String name;
		public String orgName;
		public String pinyin;

		public UnitBean() {

		}

		public UnitBean(String id, String name, String pinyin) {
			this.id = id;
			this.name = name;
			this.pinyin = pinyin;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPinyin() {
			return pinyin;
		}

		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

	}
	class ViewHodlder {
		private ImageView persion_item_contact_iv;
		private TextView contact_name;
		private Button contact_add;
	}
}
