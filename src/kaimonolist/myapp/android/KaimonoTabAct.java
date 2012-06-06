package kaimonolist.myapp.android;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class KaimonoTabAct extends TabActivity {
	final static String TAG = "TabAct";
	
	int sum = 0;
	ToBuyItem[] toBuyItems, cartItems;
	ToBuyItem selected;
	ListAdapter toBuyAdapter, cartAdapter;
	ArrayList<ListItem> toBuyArrayList = new ArrayList<ListItem>();
	ArrayList<ListItem> cartItemArrayList = new ArrayList<ListItem>();
	TabHost tabs;
	ListView toBuyList;
	ListView cartList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //test data
        toBuyItems = new ToBuyItem[5];
        cartItems= new ToBuyItem[5];
        toBuyItems[0] = new ToBuyItem("じゃがいも", 1, 198);
        toBuyItems[1] = new ToBuyItem("にんじん", 2, 198);
        toBuyItems[2] = new ToBuyItem("たまねぎ", 1, 198);
        toBuyItems[3] = new ToBuyItem("鶏もも肉", 3, 200);
        toBuyItems[4] = new ToBuyItem("お茶", 2, 168);
        
        tabs = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.main, tabs.getTabContentView(), true);
        
        //To buy list
        TabSpec toBuyListTab = tabs.newTabSpec(getString(R.string.tab_to_buy_list));
        toBuyListTab.setIndicator(getString(R.string.tab_to_buy_list), getResources().getDrawable(R.drawable.to_buy_item));
        toBuyListTab.setContent(R.id.tab_to_buy_list);
        tabs.addTab(toBuyListTab);
        
        //cart
        TabSpec cartListTab = tabs.newTabSpec(getString(R.string.tab_cart));
        cartListTab.setIndicator(getString(R.string.tab_cart), getResources().getDrawable(R.drawable.cart));
        cartListTab.setContent(R.id.tab_cart_list);
        tabs.addTab(cartListTab);
        
        //set list view
    	toBuyList = (ListView) findViewById(R.id.lv_to_buy_list);
    	cartList = (ListView) findViewById(R.id.lv_cart_list);
        for(int i = 0; i < toBuyItems.length; i++){
        	toBuyArrayList.add(new ListItem(toBuyItems[i]));
        }
        //cartItemArrayList = null;
        
        toBuyAdapter = new ListAdapter(this, R.layout.to_but_list_item, toBuyArrayList, ListAdapter.TOBUYLIST);
        //cartAdapter = new ListAdapter(this, R.layout.cart_item, cartItemArrayList, ListAdapter.CART);
        
        toBuyList.setAdapter(toBuyAdapter);
        toBuyList.setOnItemClickListener(toBuyListListener);
        toBuyList.setOnItemLongClickListener(toBuyListLongListener);
        
        //cartList.setAdapter(cartAdapter);
        
        tabs.setCurrentTab(0);
        
        //Button
        ((Button) findViewById(R.id.btn_add_item)).setOnClickListener(addItemListener);
        ((Button) findViewById(R.id.btn_end)).setOnClickListener(endListener);
        
        //TextView
        ((TextView) findViewById(R.id.tv_sum)).setText(getString(R.string.sum) + "0" + getString(R.string.yen));
    }
    
    AdapterView.OnItemClickListener toBuyListListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			selected = toBuyArrayList.get(position).toBuyItem;
			if(toBuyArrayList.size() - 1 == 0){
				toBuyArrayList.remove(position);
				tabs.setCurrentTab(1);
			} else {
				toBuyArrayList.remove(position);
				toBuyAdapter.notifyDataSetChanged();
				if(cartItemArrayList.size() == 0){
					cartItemArrayList.add(new ListItem(selected));
					cartAdapter = new ListAdapter(KaimonoTabAct.this, R.layout.cart_item, cartItemArrayList, ListAdapter.CART);
					cartList.setAdapter(cartAdapter);
				} else {
					cartItemArrayList.add(new ListItem(selected));
					cartAdapter.notifyDataSetChanged();
				}
				sum += selected.price;
			}
		}
	};
	
	AdapterView.OnItemLongClickListener toBuyListLongListener = new AdapterView.OnItemLongClickListener(){
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			selected = toBuyArrayList.get(position).toBuyItem;
			deleteItem();
			return false;
		}
	};
	
	//Button click listener
	OnClickListener addItemListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			addItem();
		}
	};
	
	OnClickListener endListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			finish();
		}
		
	};
	
	//add dialog
	public void addItem(){
		AlertDialog.Builder addItemDialog = new AlertDialog.Builder(this);
		addItemDialog.setTitle(getString(R.string.dlg_add_title));
		final EditText itemName = new EditText(this);
		final EditText itemNum = new EditText(this);
		final EditText itemPrice = new EditText(this);
		
		itemName.setMaxLines(1); itemName.setLines(1);
		itemNum.setMaxLines(1); itemNum.setLines(1); itemNum.setInputType(InputType.TYPE_CLASS_NUMBER);
		itemPrice.setMaxLines(1); itemPrice.setLines(1); itemPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		addItemDialog.setView(itemName);
		addItemDialog.setView(itemNum);
		addItemDialog.setView(itemPrice);
		
		addItemDialog.setPositiveButton(getString(R.string.dlg_btn_yes), new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = itemName.getText().toString();
				int num = Integer.parseInt(itemNum.getText().toString());
				int price = Integer.parseInt(itemPrice.getText().toString());
				toBuyArrayList.add(new ListItem(new ToBuyItem(name, num, price)));
				toBuyAdapter.notifyDataSetChanged();
			}
		});
		addItemDialog.show();
	}
	
	//delete dialog
	public void deleteItem(){
		AlertDialog.Builder deleteItemDialog = new AlertDialog.Builder(this);
		deleteItemDialog.setTitle(getString(R.string.dlg_delete_title));
		deleteItemDialog.setMessage(selected.name + getString(R.string.dlg_delete_message));
		deleteItemDialog.setPositiveButton(getString(R.string.dlg_btn_yes), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				toBuyArrayList.remove(which);
				toBuyAdapter.notifyDataSetChanged();
			}
		});
		deleteItemDialog.setNegativeButton(getString(R.string.dlg_btn_no), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing
			}
		});
		deleteItemDialog.show();
	}
}