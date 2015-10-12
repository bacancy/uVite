package com.wiredave.uvite.promoter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wiredave.uvite.Make_Choice;
import com.wiredave.uvite.R;
import com.wiredave.uvite.adapter.Promoter_AllActive_Coupon_Adapter;
import com.wiredave.uvite.adapter.Promoter_Favorites_Adapter;
import com.wiredave.uvite.adapter.Promoter_MyCoupon_Adapter;
import com.wiredave.uvite.adapter.Promoter_Referred_Adapter;
import com.wiredave.uvite.asynctask.Favorite_Coupon_Task;
import com.wiredave.uvite.asynctask.Promoter_AllActive_Coupon_Task;
import com.wiredave.uvite.asynctask.Promoter_Favorite_Coupon_Task;
import com.wiredave.uvite.asynctask.Promoter_MyCoupon_Task;
import com.wiredave.uvite.asynctask.Promoter_Referred_Coupon_Task;
import com.wiredave.uvite.asynctask.Promoter_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.home.Home_Screen;
import com.wiredave.uvite.sharedpreference.SharedPreference_Promoter;

public class Promoter_AllCoupon_Fragment extends Fragment implements
		OnClickListener {

	EditText ed_paypalid;
	Button btn_next, btn_addlater;
	DrawerLayout drawerLayout;
	ImageView img_promoter;
	RelativeLayout leftRL, rl_edit_profile, rl_all_coupons, rl_withdrawal,
			rl_payment_information, rl_logout, rl_allactive_coupons,
			rl_my_coupons, rl_referred, rl_favorite;
	public static ListView list_allcoupons;
	public static Promoter_AllActive_Coupon_Adapter promoter_allactive_coupon_adapter;
	public static Promoter_MyCoupon_Adapter promoter_mycoupon_adapter;
	public static Promoter_Referred_Adapter promoter_referred_coupon_adapter;
	public static Promoter_Favorites_Adapter promoter_favorites_adapter;

	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	View view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.promoter_allcoupons_listing,
				container, false);

		initialization(); // initialize all objects of view...

		Home_Screen.txt_header.setText(getResources().getString(
				R.string.all_active_coupons));

		//Home_Screen.img_optionwindow.setVisibility(View.VISIBLE);

		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder()
				.displayer(new RoundedBitmapDisplayer(1000))
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).resetViewBeforeLoading(true).build();

		Home_Screen.drawerLayout = drawerLayout;
		Home_Screen.leftRL = leftRL;

		if (CommonUtil.promoter_login_array.size() > 0) {
			if (CommonUtil.promoter_login_array.get(0).getLogo() != null
					&& !CommonUtil.promoter_login_array.get(0).getLogo()
							.equals("")) {
				// download and display profile image from url
				imageLoader.displayImage(CommonUtil.promoter_login_array.get(0)
						.getLogo(), img_promoter, options);
			}
		}

		if (SharedPreference_Promoter.getInstance(getActivity())
				.Get_Promoter_Coupon().equals("allactive")) {

			rl_allactive_coupons.setBackgroundResource(R.color.selected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);
			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter allactive coupons...
				new Promoter_AllActive_Coupon_Task(getActivity(), "none")
						.execute();
				// promoter_allactive_coupon_adapter= new
				// Promoter_AllActive_Coupon_Adapter(getActivity(),
				// R.layout.custom_promoter_allactivecoupons,
				// CommonUtil.promoter_allactive_coupon_array);
				// list_allcoupons.setAdapter(promoter_allactive_coupon_adapter);
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (SharedPreference_Promoter.getInstance(getActivity())
				.Get_Promoter_Coupon().equals("mycoupon")) {

			// promoter_mycoupon_adapter = new
			// Promoter_MyCoupon_Adapter(getActivity(),
			// R.layout.custom_promoter_mycoupons,
			// CommonUtil.promoter_mycoupon_array);
			// list_allcoupons.setAdapter(promoter_mycoupon_adapter);
			rl_allactive_coupons.setBackgroundResource(R.color.selected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);

			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter mycoupons...
				new Promoter_MyCoupon_Task(getActivity(), "none").execute();

			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}
		} else if (SharedPreference_Promoter.getInstance(getActivity())
				.Get_Promoter_Coupon().equals("referred")) {

			rl_allactive_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.selected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter referred coupons...
				new Promoter_Referred_Coupon_Task(getActivity(), "none")
						.execute();
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (SharedPreference_Promoter.getInstance(getActivity())
				.Get_Promoter_Coupon().equals("favorite")) {

			rl_allactive_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.selected_tab);
			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promotor favorite coupons...
				new Favorite_Coupon_Task(getActivity(), "none").execute();
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		}

		rl_allactive_coupons.setOnClickListener(this);
		rl_my_coupons.setOnClickListener(this);
		rl_referred.setOnClickListener(this);
		rl_favorite.setOnClickListener(this);

		rl_edit_profile.setOnClickListener(this);
		rl_all_coupons.setOnClickListener(this);
		rl_withdrawal.setOnClickListener(this);
		rl_payment_information.setOnClickListener(this);
		rl_logout.setOnClickListener(this);

		return view;
	}

	public void initialization() {
		drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

		img_promoter = (ImageView) view.findViewById(R.id.img_promoter);

		list_allcoupons = (ListView) view.findViewById(R.id.list_allcoupons);

		rl_allactive_coupons = (RelativeLayout) view
				.findViewById(R.id.rl_allactive_coupons);
		rl_my_coupons = (RelativeLayout) view.findViewById(R.id.rl_my_coupons);
		rl_referred = (RelativeLayout) view.findViewById(R.id.rl_referred);
		rl_favorite = (RelativeLayout) view.findViewById(R.id.rl_favorite);

		leftRL = (RelativeLayout) view.findViewById(R.id.rl_leftdrawer);
		rl_edit_profile = (RelativeLayout) view
				.findViewById(R.id.rl_edit_profile);
		rl_all_coupons = (RelativeLayout) view
				.findViewById(R.id.rl_all_coupons);
		rl_withdrawal = (RelativeLayout) view.findViewById(R.id.rl_withdrawal);
		rl_payment_information = (RelativeLayout) view
				.findViewById(R.id.rl_payment_information);
		rl_logout = (RelativeLayout) view.findViewById(R.id.rl_logout);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.rl_allactive_coupons) {
			Home_Screen.txt_header.setText(getResources().getString(
					R.string.all_coupons));
			SharedPreference_Promoter.getInstance(getActivity())
					.Set_Promoter_Coupon("allactive");

			rl_allactive_coupons.setBackgroundResource(R.color.selected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter allactive coupons...
				new Promoter_AllActive_Coupon_Task(getActivity(), "none")
						.execute();

			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

			// promoter_allactive_coupon_adapter= new
			// Promoter_AllActive_Coupon_Adapter(getActivity(),
			// R.layout.custom_promoter_allactivecoupons,
			// CommonUtil.promoter_allactive_coupon_array);
			// list_allcoupons.setAdapter(promoter_allactive_coupon_adapter);

		} else if (v.getId() == R.id.rl_my_coupons) {

			Home_Screen.txt_header.setText(getResources().getString(
					R.string.my_coupons));
			SharedPreference_Promoter.getInstance(getActivity())
					.Set_Promoter_Coupon("mycoupon");

			rl_allactive_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_my_coupons.setBackgroundResource(R.color.selected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);

			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter allactive coupons...
				new Promoter_MyCoupon_Task(getActivity(), "none").execute();

			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

			// promoter_mycoupon_adapter = new
			// Promoter_MyCoupon_Adapter(getActivity(),
			// R.layout.custom_promoter_mycoupons,
			// CommonUtil.promoter_mycoupon_array);
			// list_allcoupons.setAdapter(promoter_mycoupon_adapter);

		} else if (v.getId() == R.id.rl_referred) {

			Home_Screen.txt_header.setText(getResources().getString(
					R.string.referred));
			SharedPreference_Promoter.getInstance(getActivity())
					.Set_Promoter_Coupon("referred");

			rl_allactive_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.selected_tab);
			rl_favorite.setBackgroundResource(R.color.unselected_tab);

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promoter referred coupons...
				new Promoter_Referred_Coupon_Task(getActivity(), "none")
						.execute();
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (v.getId() == R.id.rl_favorite) {
			Home_Screen.txt_header.setText(getResources().getString(
					R.string.favorites));
			SharedPreference_Promoter.getInstance(getActivity())
					.Set_Promoter_Coupon("favorite");

			rl_allactive_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_my_coupons.setBackgroundResource(R.color.unselected_tab);
			rl_referred.setBackgroundResource(R.color.unselected_tab);
			rl_favorite.setBackgroundResource(R.color.selected_tab);

			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call to get promotor favorite coupons...
				new Favorite_Coupon_Task(getActivity(), "none").execute();
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (v.getId() == R.id.rl_edit_profile) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// get promoter profile details...
				new Promoter_SignUp_Task(getActivity(), "get").execute();

			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (v.getId() == R.id.rl_all_coupons) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}

		} else if (v.getId() == R.id.rl_withdrawal) {
			if (drawerLayout != null) {
				drawerLayout.closeDrawer(leftRL);
			}
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
			/*
			 * //check internet connetivity...
			 * if(Common.isConnectingToInternet(getActivity())) { //call this
			 * for logout from application... new
			 * Logout_Task(getActivity()).execute();
			 * 
			 * }else {
			 * Common.showalertDialog(getActivity(),getActivity().getResources
			 * ().getString(R.string.alert_internetconnectivity)); }
			 */
		}
	}

}
