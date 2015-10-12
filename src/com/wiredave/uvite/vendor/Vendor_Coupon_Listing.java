package com.wiredave.uvite.vendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.Make_Choice;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Vendor_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.home.Home_Screen;

import eu.janmuller.android.simplecropimage.Util;

public class Vendor_Coupon_Listing extends Fragment implements OnClickListener {

	public static RelativeLayout rl_back, rl_active_coupon, rl_expired_coupon,
			rl_redeemed_payment;
	View view = null;

	Vendor_Active_Coupon_Fragment vendor_active_coupon_fragment;
	Vendor_Expired_Coupon_Fragment vendor_expired_coupon_fragment;
	Vendor_Redeemed_Coupon_Fragment vendor_redeemed_coupon_fragment;
	public static FragmentManager fragmentManager;
	DrawerLayout drawerLayout;
	RelativeLayout leftRL;
	ImageView img_vendor, img_optionwindow;
	RelativeLayout rl_edit_account, rl_create_coupon, rl_view_coupon,
			rl_scan_coupon, rl_payment_information, rl_logout;
	Scan_Coupon_Fragment scan_coupon_fragment;
	TextView txt_name;

	/*
	 * @Override protected void onSaveInstanceState(Bundle outState) {
	 * outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
	 * "WORKAROUND_FOR_BUG_19917_VALUE"); super.onSaveInstanceState(outState); }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.vendor_coupon_listing);

		view = inflater.inflate(R.layout.vendor_coupon_listing, container,
				false);

		Home_Screen.txt_header.setText(getResources().getString(
				R.string.active_coupon));

		initialization(); // initialize all objects of view...

		vendor_active_coupon_fragment = new Vendor_Active_Coupon_Fragment();
		vendor_expired_coupon_fragment = new Vendor_Expired_Coupon_Fragment();
		vendor_redeemed_coupon_fragment = new Vendor_Redeemed_Coupon_Fragment();

		Home_Screen.drawerLayout = drawerLayout;
		Home_Screen.leftRL = leftRL;

		scan_coupon_fragment = new Scan_Coupon_Fragment();

		// Master data Fragments
		fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_vendorcouponlist_container,
				vendor_active_coupon_fragment);

		fragmentTransaction.commit();

		rl_active_coupon.setOnClickListener(this);
		rl_expired_coupon.setOnClickListener(this);
		rl_redeemed_payment.setOnClickListener(this);

		rl_active_coupon.setBackgroundResource(R.color.selected_tab);
		rl_expired_coupon.setBackgroundResource(R.color.unselected_tab);
		rl_redeemed_payment.setBackgroundResource(R.color.unselected_tab);

		rl_edit_account.setOnClickListener(this);
		rl_create_coupon.setOnClickListener(this);
		rl_view_coupon.setOnClickListener(this);
		rl_scan_coupon.setOnClickListener(this);
		rl_payment_information.setOnClickListener(this);
		rl_logout.setOnClickListener(this);

		return view;

	}

	public void initialization() {

		rl_active_coupon = (RelativeLayout) view
				.findViewById(R.id.rl_active_coupon);
		rl_expired_coupon = (RelativeLayout) view
				.findViewById(R.id.rl_expired_coupon);
		rl_redeemed_payment = (RelativeLayout) view
				.findViewById(R.id.rl_redeemed_payment);

		drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
		leftRL = (RelativeLayout) view.findViewById(R.id.rl_leftdrawer);

		rl_edit_account = (RelativeLayout) view
				.findViewById(R.id.rl_edit_account);
		rl_create_coupon = (RelativeLayout) view
				.findViewById(R.id.rl_create_coupon);
		rl_view_coupon = (RelativeLayout) view
				.findViewById(R.id.rl_view_coupon);
		rl_scan_coupon = (RelativeLayout) view
				.findViewById(R.id.rl_scan_coupon);
		rl_payment_information = (RelativeLayout) view
				.findViewById(R.id.rl_payment_information);
		rl_logout = (RelativeLayout) view.findViewById(R.id.rl_logout);

		txt_name = (TextView) view.findViewById(R.id.txt_name);
		

		/*
		 * img_optionwindow = (ImageView)
		 * view.findViewById(R.id.img_optionwindow);
		 */
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		 * if (v.getId() == R.id.rl_back) { overridePendingTransition(0, 0);
		 * finish();
		 * 
		 * if(Common.ref_database.checkIfExist()) { if(drawerLayout != null) {
		 * if(drawerLayout.isDrawerOpen(leftRL)) {
		 * drawerLayout.closeDrawer(leftRL); }else {
		 * drawerLayout.openDrawer(leftRL); } } }
		 * 
		 * } else
		 */if (v.getId() == R.id.rl_active_coupon) {

			rl_active_coupon.setBackgroundResource(R.drawable.border);
			rl_redeemed_payment
					.setBackgroundResource(R.drawable.border_darkblue);
			rl_expired_coupon.setBackgroundResource(R.drawable.border_darkblue);
			// replace fragment...
			fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(
					R.id.fragment_vendorcouponlist_container,
					vendor_active_coupon_fragment);

			fragmentTransaction.commit();

			rl_active_coupon.setBackgroundResource(R.color.selected_tab);
			rl_expired_coupon.setBackgroundResource(R.color.unselected_tab);
			rl_redeemed_payment.setBackgroundResource(R.color.unselected_tab);

		} else if (v.getId() == R.id.rl_expired_coupon) {

			Home_Screen.txt_header.setText(getResources().getString(
					R.string.expired_coupon));

			rl_active_coupon.setBackgroundResource(R.drawable.border_darkblue);
			rl_redeemed_payment
					.setBackgroundResource(R.drawable.border_darkblue);
			rl_expired_coupon.setBackgroundResource(R.drawable.border);
			// replace fragment...
			fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(
					R.id.fragment_vendorcouponlist_container,
					vendor_expired_coupon_fragment);
			fragmentTransaction.commit();

			rl_active_coupon.setBackgroundResource(R.color.unselected_tab);
			rl_expired_coupon.setBackgroundResource(R.color.selected_tab);
			rl_redeemed_payment.setBackgroundResource(R.color.unselected_tab);

		} else if (v.getId() == R.id.rl_redeemed_payment) {

			Home_Screen.txt_header.setText(getResources().getString(
					R.string.redeemed_coupon));
			rl_active_coupon.setBackgroundResource(R.drawable.border_darkblue);
			rl_redeemed_payment.setBackgroundResource(R.drawable.border);
			rl_expired_coupon.setBackgroundResource(R.drawable.border_darkblue);
			// replace fragment...
			fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(
					R.id.fragment_vendorcouponlist_container,
					vendor_redeemed_coupon_fragment);

			fragmentTransaction.commit();

			rl_active_coupon.setBackgroundResource(R.color.unselected_tab);
			rl_expired_coupon.setBackgroundResource(R.color.unselected_tab);
			rl_redeemed_payment.setBackgroundResource(R.color.selected_tab);
		} else if (v.getId() == R.id.rl_edit_account) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// get vendor profile details...
				new Vendor_SignUp_Task(getActivity(), "get").execute();

			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (v.getId() == R.id.rl_create_coupon) {

			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			// start create coupon activity...
			getActivity().startActivity(
					new Intent(getActivity(), Vendor_Create_Coupon.class)
							.putExtra("vendor_create_coupon_flag", "add"));
			getActivity().overridePendingTransition(0, 0);
		} else if (v.getId() == R.id.rl_view_coupon) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}
			getActivity().startActivity(
					new Intent(getActivity(), Vendor_Coupon_Listing.class));
			getActivity().overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.rl_scan_coupon) {

			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			// replace fragment...
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			scan_coupon_fragment = new Scan_Coupon_Fragment();
			fragmentTransaction.replace(R.id.fragment_home,
					scan_coupon_fragment);
			fragmentTransaction.commit();

		} else if (v.getId() == R.id.rl_payment_information) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}
		} else if (v.getId() == R.id.rl_logout) {

			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			try {
				if (Common.ref_database.checkIfExist())
					Common.ref_database.Delete_Login_Detail();

				// clear all activity and start login screen...
				getActivity().startActivity(
						new Intent(getActivity(), Make_Choice.class)
								.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
										| Intent.FLAG_ACTIVITY_CLEAR_TASK));
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	public static void Replace_Vendor_Coupon_Fragment(String coupon_status) {
		Vendor_Active_Coupon_Fragment vendor_active_coupon_fragment;
		Vendor_Expired_Coupon_Fragment vendor_expired_coupon_fragment;
		Vendor_Redeemed_Coupon_Fragment vendor_redeemed_coupon_fragment;

		if (fragmentManager != null) {
			// replace fragment...
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			if (coupon_status.equals("active")) {
				vendor_active_coupon_fragment = new Vendor_Active_Coupon_Fragment();
				fragmentTransaction.replace(
						R.id.fragment_vendorcouponlist_container,
						vendor_active_coupon_fragment);
			} else if (coupon_status.equals("expired")) {
				vendor_expired_coupon_fragment = new Vendor_Expired_Coupon_Fragment();
				fragmentTransaction.replace(
						R.id.fragment_vendorcouponlist_container,
						vendor_expired_coupon_fragment);
			} else if (coupon_status.equals("redeemed")) {
				vendor_redeemed_coupon_fragment = new Vendor_Redeemed_Coupon_Fragment();
				fragmentTransaction.replace(
						R.id.fragment_vendorcouponlist_container,
						vendor_redeemed_coupon_fragment);
			}
			fragmentTransaction.commitAllowingStateLoss();
		}

	}

}
