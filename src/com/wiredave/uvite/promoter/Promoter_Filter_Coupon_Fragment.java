package com.wiredave.uvite.promoter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.wiredave.uvite.asynctask.Promoter_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.home.Home_Screen;

public class Promoter_Filter_Coupon_Fragment extends Fragment implements OnClickListener{
		
	View view = null;	
	DrawerLayout drawerLayout;
	public static ListView list_filter;
	ImageView img_promoter;
	EditText ed_search;
	Button btn_apply;
	RelativeLayout rl_category,rl_location,rl_vendor,leftRL,rl_edit_profile,rl_all_coupons,rl_withdrawal,rl_payment_information,
    rl_logout;	
	ImageLoader imageLoader = null;	
	private DisplayImageOptions options;
	Promoter_AllCoupon_Fragment promoter_allcoupon_fragment;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.filter, container, false);
		
		initialization(); //initialize all objects of view...
		
		
		
        imageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		    .displayer(new RoundedBitmapDisplayer(1000))
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.resetViewBeforeLoading(true).build();
		
		Home_Screen.drawerLayout = drawerLayout;
		Home_Screen.leftRL = leftRL;
		
		if(CommonUtil.promoter_login_array.size() > 0)
		{
		 if(CommonUtil.promoter_login_array.get(0).getLogo() != null && !CommonUtil.promoter_login_array.get(0).getLogo().equals(""))
		   {
		     //download and display profile image from url
		     imageLoader.displayImage(CommonUtil.promoter_login_array.get(0).getLogo(), img_promoter , options);
		   }
		}
		
		btn_apply.setOnClickListener(this);
		rl_category.setOnClickListener(this);
		rl_location.setOnClickListener(this);
		rl_vendor.setOnClickListener(this);
		
		rl_edit_profile.setOnClickListener(this);
		rl_all_coupons.setOnClickListener(this);
		rl_withdrawal.setOnClickListener(this);
		rl_payment_information.setOnClickListener(this);
		rl_logout.setOnClickListener(this);
		
		return view;
	}
	
	public void initialization()
	{
		drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
		
		ed_search = (EditText)view.findViewById(R.id.ed_search);
		btn_apply = (Button)view.findViewById(R.id.btn_apply);
		
		img_promoter = (ImageView)view.findViewById(R.id.img_promoter);
		
		list_filter = (ListView)view.findViewById(R.id.list_filter);
		 		
		leftRL = (RelativeLayout)view.findViewById(R.id.rl_leftdrawer);
		rl_edit_profile = (RelativeLayout)view.findViewById(R.id.rl_edit_profile);
		rl_all_coupons = (RelativeLayout)view.findViewById(R.id.rl_all_coupons);
		rl_withdrawal = (RelativeLayout)view.findViewById(R.id.rl_withdrawal);
		rl_payment_information = (RelativeLayout)view.findViewById(R.id.rl_payment_information);
		rl_logout = (RelativeLayout)view.findViewById(R.id.rl_logout);
		
		rl_category = (RelativeLayout)view.findViewById(R.id.rl_category);
		rl_location = (RelativeLayout)view.findViewById(R.id.rl_location);
		rl_vendor = (RelativeLayout)view.findViewById(R.id.rl_vendor);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_apply)
		{
			
			//replace fragment...
			 FragmentManager fragmentManager = getFragmentManager(); 
		     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		     promoter_allcoupon_fragment = new Promoter_AllCoupon_Fragment();
		     fragmentTransaction.replace(R.id.fragment_home, promoter_allcoupon_fragment); 
		     
		     fragmentTransaction.commit(); 
			
		}else if(v.getId() == R.id.rl_category)
		{
			ed_search.setVisibility(View.GONE);
			
			
		}else if(v.getId() == R.id.rl_location)
		{
			ed_search.setVisibility(View.VISIBLE);
			ed_search.setText("");
			
		}else if(v.getId() == R.id.rl_vendor)
		{
			ed_search.setVisibility(View.GONE);
			
		}else if(v.getId() == R.id.rl_edit_profile)
		{
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			
			//check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			   {
				//get promoter profile details...
					new Promoter_SignUp_Task(getActivity(), "get").execute();
				 
				}else {		
				  Common.showalertDialog(getActivity(),getActivity().getResources().getString(R.string.alert_internetconnectivity));
			   }
			
		}else if (v.getId() == R.id.rl_all_coupons) {
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			
		}else if (v.getId() == R.id.rl_withdrawal) {
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
		}else if (v.getId() == R.id.rl_payment_information) {
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
		}else if (v.getId() == R.id.rl_logout) {
			
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			
			try {
				if(Common.ref_database.checkIfExist())
				  Common.ref_database.Delete_Login_Detail();	
				
				//clear all activity and start login screen...
				getActivity().startActivity(new Intent(getActivity(),Make_Choice.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			 /*//check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			   {
				//call this for logout from application...
				 new Logout_Task(getActivity()).execute();
				 
				}else {		
				  Common.showalertDialog(getActivity(),getActivity().getResources().getString(R.string.alert_internetconnectivity));
			   }*/
		}
	}

}
