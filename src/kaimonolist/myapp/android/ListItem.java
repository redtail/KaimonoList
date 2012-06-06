package kaimonolist.myapp.android;

public class ListItem {
	final static String TAG = "ListItem";
	int index;
	ToBuyItem toBuyItem;
	
	public ListItem(int i){
		this.index = i;
	}
	
	public ListItem(ToBuyItem item){
		this.toBuyItem = item;
	}
}
