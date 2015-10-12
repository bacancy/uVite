package com.wiredave.uvite.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;
import com.wiredave.uvite.promoter.Promoter_Filter_Coupon_Fragment;
import com.wiredave.uvite.vendor.Review_Coupon_Fragment;
import com.wiredave.uvite.vendor.Vendor_Coupon_Listing;

public class Home_Screen extends FragmentActivity implements OnClickListener {

	Vendor_Home_Fragment vendor_home_fragment;
	Promoter_Home_Fragment promoter_home_fragment;
	Vendor_Coupon_Listing vendor_Coupon_Listing;
	Promoter_AllCoupon_Fragment promoter_AllCoupon_Fragment;
	public static ImageView img_menu;
	public static ImageView img_optionwindow;
	public static TextView txt_header;
	public static DrawerLayout drawerLayout;
	public static RelativeLayout leftRL;
	String popUpContents[];
	PopupWindow popupWindow;
	public static FragmentManager fragmentManager;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
				"WORKAROUND_FOR_BUG_19917_VALUE");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		initialization(); // initialize view of objects...

		txt_header.setText(getResources().getString(R.string.home));

		fragmentManager = getSupportFragmentManager();

		// Master data Fragments
		vendor_home_fragment = new Vendor_Home_Fragment();
		promoter_home_fragment = new Promoter_Home_Fragment();
		vendor_Coupon_Listing = new Vendor_Coupon_Listing();
		promoter_AllCoupon_Fragment = new Promoter_AllCoupon_Fragment();

		// add items on the array dynamically
		// format is Company Name:: ID
		List<String> filterList = new ArrayList<String>();
		filterList.add(getResources().getString(R.string.category));
		filterList.add(getResources().getString(R.string.location));
		filterList.add(getResources().getString(R.string.vendor));

		// convert to simple array
		popUpContents = new String[filterList.size()];
		filterList.toArray(popUpContents);

		/*
		 * initialize pop up window
		 */
		popupWindow = popupWindowDogs();

		if (Common.ref_database == null)
			Common.ref_database = new Referral_Database(Home_Screen.this);

		if (Common.ref_database.checkIfExist()) {
			fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			if (Common.ref_database.getUserType().equals("Vendor")) {

				fragmentTransaction.add(R.id.fragment_home,
						vendor_Coupon_Listing);

			} else if (Common.ref_database.getUserType().equals("Promoter")) {

				fragmentTransaction.add(R.id.fragment_home,
						promoter_AllCoupon_Fragment);
			}

			fragmentTransaction.commit();
		}

		img_menu.setOnClickListener(this);
		img_optionwindow.setOnClickListener(this);

	}

	public void initialization() {
		txt_header = (TextView) findViewById(R.id.txt_header);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		img_optionwindow = (ImageView) findViewById(R.id.img_optionwindow);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.img_menu) {
			if (Common.ref_database.checkIfExist()) {
				if (drawerLayout != null) {
					if (drawerLayout.isDrawerOpen(leftRL)) {
						drawerLayout.closeDrawer(leftRL);
					} else {
						drawerLayout.openDrawer(leftRL);
					}
				}

				/*
				 * if(Common.ref_database.getUserType().equals("Vendor")) {
				 * if(Vendor_Home_Fragment.drawerLayout != null) {
				 * if(Vendor_Home_Fragment
				 * .drawerLayout.isDrawerOpen(Vendor_Home_Fragment.leftRL)) {
				 * Vendor_Home_Fragment
				 * .drawerLayout.closeDrawer(Vendor_Home_Fragment.leftRL); }else
				 * {
				 * Vendor_Home_Fragment.drawerLayout.openDrawer(Vendor_Home_Fragment
				 * .leftRL); } } }else if
				 * (Common.ref_database.getUserType().equals("Promoter")) {
				 * 
				 * if(Promoter_Home_Fragment.drawerLayout != null) {
				 * if(Promoter_Home_Fragment
				 * .drawerLayout.isDrawerOpen(Promoter_Home_Fragment.leftRL)) {
				 * Promoter_Home_Fragment
				 * .drawerLayout.closeDrawer(Promoter_Home_Fragment.leftRL);
				 * }else { Promoter_Home_Fragment.drawerLayout.openDrawer(
				 * Promoter_Home_Fragment.leftRL); } } }
				 */
			}
		} else if (v.getId() == R.id.img_optionwindow) {

			// show dropdown pop up window...
			popupWindow.showAsDropDown(v, -5, 0);
		}
	}

	public PopupWindow popupWindowDogs() {

		// initialize a pop up window type
		PopupWindow popupWindow = new PopupWindow(this);

		// the drop down list is a list view
		ListView listView = new ListView(this);

		// set our adapter and pass our pop up window contents
		listView.setAdapter(popupviewAdapter(popUpContents));

		// set the item click listener
		listView.setOnItemClickListener(new DropdownOnItemClickListener());

		// some other visual settings
		popupWindow.setFocusable(true);
		popupWindow.setWidth(250);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

		// set the list view as pop up window content
		popupWindow.setContentView(listView);

		return popupWindow;
	}

	private ArrayAdapter<String> popupviewAdapter(String Array[]) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Array) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// setting the ID and text for every items in the list

				String text = getItem(position);

				// visual settings for the list item
				TextView listItem = new TextView(Home_Screen.this);

				listItem.setText(text);
				listItem.setTag(position);
				listItem.setTextSize(17);
				listItem.setPadding(5, 5, 5, 5);
				listItem.setTextColor(Color.WHITE);

				return listItem;
			}
		};

		return adapter;
	}

	public class DropdownOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

			// get the context and main activity to access variables
			Context mContext = v.getContext();
			Home_Screen mainActivity = ((Home_Screen) mContext);

			// add some animation when a list item was clicked
			Animation fadeInAnimation = AnimationUtils.loadAnimation(
					v.getContext(), android.R.anim.fade_in);
			fadeInAnimation.setDuration(10);
			v.startAnimation(fadeInAnimation);

			// dismiss the pop up
			mainActivity.popupWindow.dismiss();

			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			Promoter_Filter_Coupon_Fragment filtercoupon_fragment = new Promoter_Filter_Coupon_Fragment();
			fragmentTransaction.replace(R.id.fragment_home,
					filtercoupon_fragment);
			fragmentTransaction.commitAllowingStateLoss();

		}

	}

	public void Replace_Review_Coupon_Fragment() {

		if (fragmentManager != null) {
			new Handler().post(new Runnable() {
				public void run() {
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					Review_Coupon_Fragment review_coupon_fragment = new Review_Coupon_Fragment();
					fragmentTransaction.replace(R.id.fragment_home,
							review_coupon_fragment);
					fragmentTransaction.commitAllowingStateLoss();
				}
			});
		}
	}

}
