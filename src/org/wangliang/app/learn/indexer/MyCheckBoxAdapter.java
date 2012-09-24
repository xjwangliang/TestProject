package org.wangliang.app.learn.indexer;

import java.util.ArrayList;
import java.util.List;

import org.wangliang.app.learn.R;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class MyCheckBoxAdapter extends BaseAdapter implements SectionIndexer,Filterable{

	//布局实例化器
	private LayoutInflater mInflater = null;
	//程序上下文
	private Context myContext = null;
	//初始View的数据
	private List<Person> mOriginalData = null;
	//过滤后View的数据
	private List<Person> mFilteredData = null;
	//已经选择的项的数目
	private int checkedCount = 0;
	//提示最多选择多少人
	private AlertDialog alert = null;
	//过滤器，过滤电话号码和姓名
	private MyListViewFilter mFilter;
	
	//存放索引提示信息，程序根据通讯录动态生成
	private ArrayList<String> sectionContentArray = new ArrayList<String>();
	//存放sectionContent的开始的位置
	private ArrayList<Integer> SectionIndics = new ArrayList<Integer>();

	
    /**
     * 构造器
     * @param context
     * @param data
     */
    public MyCheckBoxAdapter(Context context, List<Person> data) {
    	//获得上下文
        this.myContext = context;
        //获得实例化器
        this.mInflater = LayoutInflater.from(this.myContext);
        //获得数据
        this.mOriginalData = data;
        this.mFilteredData = data;
        
        //得到汉语拼音的首字母
        ArrayList<String> pinyinArray = new ArrayList<String>();
        for(int j=0;j<data.size();j++){
        	String oName = data.get(j).getName();
            String cName = MyStringUtility.Chinese2Pinyin(oName).toUpperCase().substring(0, 1);
            System.out.println(cName);
            pinyinArray.add(cName);
        }
        
        //根据汉语拼音的首字母，获得sections和sectionIndics
        //也就是提示信息数组和提示信息开始位置数组
        String lastLetter = "";
        for(int i=0;i<pinyinArray.size();i++){
        	if(i==0){
        		SectionIndics.add(0);
        		String thisLetter = pinyinArray.get(i);
        		sectionContentArray.add(thisLetter);
        		lastLetter=thisLetter;
        	}
        	else{
        		if(pinyinArray.get(i).equals(lastLetter)){
        			continue;
        		}
        		else{
        			SectionIndics.add(i);
        			String thisLetter = pinyinArray.get(i);
        			sectionContentArray.add(thisLetter);
            		lastLetter=thisLetter;
        		}
        	}
        }
        
//        Log.i("SectionIndics", SectionIndics.toString());
//        for(int i=0;i<sectionContentArray.size();i++)
//        Log.i("SectionIndics", sectionContentArray.get(i));
    }
    
    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
    	final CheckBoxViewHolder holder;
    	//滚动时重新加载视图
    	convertView = null;
    	//获得当前联系人对象
    	final Person person = MyCheckBoxAdapter.this.mFilteredData.get(position);
    	//如果现在没有视图
    	if(convertView == null){
    		//初始化布局文件和布局的组建结构
    		convertView = this.mInflater.inflate(R.layout.contacts_indexer_item, null);
    		holder = new CheckBoxViewHolder();
    		holder.cb = (CheckBox)convertView.findViewById(R.id.cb);
    		holder.contactName = (TextView)convertView.findViewById(R.id.tv);
    		holder.phoneNum = (TextView)convertView.findViewById(R.id.ph);
    		//加入数据
    		holder.contactName.setText(mFilteredData.get(position).getName());
    		holder.phoneNum.setText(mFilteredData.get(position).getPhoneNum());
    		holder.cb.setChecked(person.isSelected());
    		
    		//为CHECKBOX添加点击事件
    		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					//如果选中了
					if (isChecked == true) {
						if(checkedCount >= ContactsIndexer.maxCheckCount){
							print("checkedCount = "+ checkedCount);
							//如果弹出过，则不弹出新的提示框
							if(alert == null){
								alert = new AlertDialog.Builder(myContext)
								.setTitle("提示")
								.setMessage("您一次最多能选择"+ContactsIndexer.maxCheckCount+"个联系人")
								.setNegativeButton("返回", null)
								.create();
							}
							alert.show();
							holder.cb.setChecked(false);
						}
						else{
							checkedCount++;
							print("checkedCount = "+ checkedCount);
							person.setSelected(true);
						}
					}
					//如果未选中
					else{
						checkedCount--;
						print("checkedCount = "+ checkedCount);
						person.setSelected(false);
					}
				}
    		});
    	}
		return convertView;
	}
    
	@Override
	public int getCount() {
		return mFilteredData.size();
	}

	@Override
	public Object getItem(int position) {
		return mFilteredData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 布局的结构类
	 * 内部类
	 * @author Liuzhao
	 *
	 */
	protected class CheckBoxViewHolder{
		public CheckBox cb;
		public TextView contactName;
		public TextView phoneNum;
	}

	/**
	 * 打印信息
	 * @param msg
	 */
	public void print(String msg){
		Log.d("MyCheckBoxAdapter", msg);
	}
	
	public int getCheckedCount() {
		return checkedCount;
	}

	public void setCheckedCount(int checkedCount) {
		this.checkedCount = checkedCount;
	}

	@Override
	public Object[] getSections() {
		//这个sections是一个提示信息列表
		//每一个元素就是显示在方框中的字符
		//设置之后，SectionIndexer接口会自动去现实
		Object[] sections = new Object[sectionContentArray.size()];
        for(int i = 0;i<sectionContentArray.size();i++){
            sections[i] = sectionContentArray.get(i);
        }
        
        return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		//获得每一个section的头位置
		//也就是每个字母在list中的第一个位置
		//这个section的值变化区间应该是0-sections.length
		//sections就是上一个函数中定义的Object[] sections
		return SectionIndics.get(section);
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {  
			mFilter = new MyListViewFilter();  
		}  
		return mFilter; 
	}
	
	/**
	 * 自己定义的通讯录的过滤器
	 * Created by Liuzhao
	 * 2011-7-29
	 */
	private class MyListViewFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			Log.d("Filter", "performFiltering");
			//返回的results，即过滤后的数据ArrayList<Person>
			FilterResults results = new FilterResults();
			
			//无约束字符，返回原数据
			if (constraint == null || constraint.length() == 0) {
				results.values = mOriginalData;
                results.count = mOriginalData.size();
            } else {
				//获得约束字符串
				String prefixString = constraint.toString().toLowerCase();
				
				//新建Values存放过滤后的数据
				int count = mOriginalData.size();
				List<Person> newValues = new ArrayList<Person>(count);
				
				for (int i = 0; i < count; i++) {
					Person p = mOriginalData.get(i);
					String name = p.getName().trim().toLowerCase();
					String phoneno = p.getPhoneNum().trim().toLowerCase();
					//如果电话号码以要过滤的字符的开头
					//或者姓名包括输入的字符
					//则加入新的newValue
					if(phoneno.startsWith(prefixString)||name.contains(prefixString)){
						newValues.add(p);
					}
                }
				
				results.values = newValues;
				results.count = newValues.size();
				Log.d("Filter", "new Count = "+results.count);
            }
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,FilterResults results) {
			Log.d("Filter", "publishResults");
			Log.d("Filter", "count = "+results.count);
			mFilteredData = (ArrayList<Person>)results.values;
			if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
		}
	}
}
