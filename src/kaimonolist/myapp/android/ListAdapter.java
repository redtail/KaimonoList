package kaimonolist.myapp.android;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends ArrayAdapter<ListItem> {
	public static final int TOBUYLIST = 1;
	public static final int CART = 2;
	
	Context context;
	ArrayList<ListItem> items;
	LayoutInflater inflater;
	int cn; //context number
	
	public ListAdapter(Context c, int textViewResourceId, ArrayList<ListItem> items, int n) {
		super(c, textViewResourceId, items);
		this.items = items;
		this.context = c;
		this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.cn = n;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		switch(cn){
		case TOBUYLIST: {
			if(view == null){
				view = inflater.inflate(R.layout.cart_item, null);
			}
			ListItem item = items.get(position);
			
			TextView name = (TextView) view.findViewById(R.id.tv_item_name);
			name.setTypeface(Typeface.DEFAULT_BOLD);
			name.setText(item.toBuyItem.name);
			
			TextView num = (TextView) view.findViewById(R.id.tv_item_num);
			num.setTypeface(Typeface.DEFAULT_BOLD);
			num.setText(item.toBuyItem.num + context.getString(R.string.unit));
			
			TextView price = (TextView) view.findViewById(R.id.tv_item_price);
			price.setTypeface(Typeface.DEFAULT_BOLD);
			price.setText(item.toBuyItem.price + context.getString(R.string.yen));
			break;
		}
		case CART: {
			if(view == null){
				view = inflater.inflate(R.layout.cart_item, null);
			}
			ListItem item = items.get(position);
			
			TextView name = (TextView) view.findViewById(R.id.tv_item_name);
			name.setTypeface(Typeface.DEFAULT_BOLD);
			name.setText(item.toBuyItem.name);
			
			TextView num = (TextView) view.findViewById(R.id.tv_item_num);
			num.setTypeface(Typeface.DEFAULT_BOLD);
			num.setText(item.toBuyItem.num + context.getString(R.string.unit));
			
			TextView price = (TextView) view.findViewById(R.id.tv_item_price);
			price.setTypeface(Typeface.DEFAULT_BOLD);
			price.setText(item.toBuyItem.price + context.getString(R.string.yen));
			break;
		}
		}
		
		return view;
	}
}


