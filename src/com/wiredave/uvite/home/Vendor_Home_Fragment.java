package com.wiredave.uvite.home;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wiredave.uvite.Make_Choice;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Vendor_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.vendor.Scan_Coupon_Fragment;
import com.wiredave.uvite.vendor.Vendor_Coupon_Listing;
import com.wiredave.uvite.vendor.Vendor_Create_Coupon;

public class Vendor_Home_Fragment extends Fragment implements OnClickListener{

	View view = null;	
	Button btn_edit_account,btn_create_coupon,btn_view_coupon,btn_scan_coupon,
	       btn_payment_information,btn_logout;
	DrawerLayout drawerLayout;
	ImageView img_vendor;
	RelativeLayout leftRL,rl_edit_account,rl_create_coupon,rl_view_coupon,
	               rl_scan_coupon,rl_payment_information,rl_logout;
	
	Scan_Coupon_Fragment scan_coupon_fragment;
	
	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.vendor_home_fragment, container, false);
		
		initialization(); //initialize all objects of view...
		
	/*	Home_Screen.img_optionwindow.setVisibility(View.GONE);*/
		
		imageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		.displayer(new RoundedBitmapDisplayer(1000))
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
		
		scan_coupon_fragment = new Scan_Coupon_Fragment();
		
		Home_Screen.drawerLayout = drawerLayout;
		Home_Screen.leftRL = leftRL;
		
		if(CommonUtil.vendor_login_array.size() > 0)
		{
		 if(CommonUtil.vendor_login_array.get(0).getLogo() != null && !CommonUtil.vendor_login_array.get(0).getLogo().equals(""))					
		 {
		  //download and display profile image from url
		  imageLoader.displayImage(CommonUtil.vendor_login_array.get(0).getLogo(), img_vendor , options);
		 }
		}
		
		
		btn_edit_account.setOnClickListener(this);
		btn_create_coupon.setOnClickListener(this);
		btn_view_coupon.setOnClickListener(this);
		btn_scan_coupon.setOnClickListener(this);
		btn_payment_information.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		
		rl_edit_account.setOnClickListener(this);
		rl_create_coupon.setOnClickListener(this);
		rl_view_coupon.setOnClickListener(this);
		rl_scan_coupon.setOnClickListener(this);
		rl_payment_information.setOnClickListener(this);
		rl_logout.setOnClickListener(this);
		
		return view;
	}
	
	public void initialization()
	{
		drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
		
		img_vendor = (ImageView)view.findViewById(R.id.img_vendor);
		
		leftRL = (RelativeLayout)view.findViewById(R.id.rl_leftdrawer);	   
		rl_edit_account = (RelativeLayout)view.findViewById(R.id.rl_edit_account);
		rl_create_coupon = (RelativeLayout)view.findViewById(R.id.rl_create_coupon);
		rl_view_coupon = (RelativeLayout)view.findViewById(R.id.rl_view_coupon);
		rl_scan_coupon = (RelativeLayout)view.findViewById(R.id.rl_scan_coupon);
		rl_payment_information = (RelativeLayout)view.findViewById(R.id.rl_payment_information);
		rl_logout = (RelativeLayout)view.findViewById(R.id.rl_logout);
	    
		btn_edit_account = (Button)view.findViewById(R.id.btn_edit_account);
		btn_create_coupon = (Button)view.findViewById(R.id.btn_create_coupon);
		btn_view_coupon = (Button)view.findViewById(R.id.btn_view_coupon);
		btn_scan_coupon = (Button)view.findViewById(R.id.btn_scan_coupon);
		btn_payment_information = (Button)view.findViewById(R.id.btn_payment_information);
		btn_logout = (Button)view.findViewById(R.id.btn_logout);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_edit_account)
		{
			 //check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			   {
				//get vendor profile details...
				new Vendor_SignUp_Task(getActivity(), "get").execute();
				 
				}else {		
				  Common.showalertDialog(getActivity(),getActivity().getResources().getString(R.string.alert_internetconnectivity));
			   }
			
		}else if (v.getId() == R.id.btn_create_coupon) {
			
			//start create coupon activity...
			getActivity().startActivity(new Intent(getActivity(),Vendor_Create_Coupon.class).putExtra("vendor_create_coupon_flag", "add"));
			getActivity().overridePendingTransition(0, 0);
		     
		}else if (v.getId() == R.id.btn_view_coupon) {
			
			getActivity().startActivity(new Intent(getActivity(),Vendor_Coupon_Listing.class));
			getActivity().overridePendingTransition(0, 0);
			
		}else if (v.getId() == R.id.btn_scan_coupon) {
			
			 //replace fragment...
			 FragmentManager fragmentManager = getFragmentManager(); 
		     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		     scan_coupon_fragment = new Scan_Coupon_Fragment();
		     fragmentTransaction.replace(R.id.fragment_home, scan_coupon_fragment); 
		     
		     fragmentTransaction.commit(); 
		     
		}else if (v.getId() == R.id.btn_payment_information) {
			
		}else if (v.getId() == R.id.btn_logout) {
			
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
		}else if(v.getId() == R.id.rl_edit_account)
		{
			 if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			 
			//check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			   {
				//get vendor profile details...
				new Vendor_SignUp_Task(getActivity(), "get").execute();
				 
				}else {		
				  Common.showalertDialog(getActivity(),getActivity().getResources().getString(R.string.alert_internetconnectivity));
			   }
			
		}else if (v.getId() == R.id.rl_create_coupon) {
			
			 if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			 
			//start create coupon activity...
			getActivity().startActivity(new Intent(getActivity(),Vendor_Create_Coupon.class).putExtra("vendor_create_coupon_flag", "add"));
			getActivity().overridePendingTransition(0, 0);
			
		}else if (v.getId() == R.id.rl_view_coupon) {
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			
			getActivity().startActivity(new Intent(getActivity(),Vendor_Coupon_Listing.class));
			getActivity().overridePendingTransition(0, 0);
			
		}else if (v.getId() == R.id.rl_scan_coupon) {
			
			if(drawerLayout != null)
			   {
				 drawerLayout.closeDrawer(leftRL);	
			  }
			
			 //replace fragment...
			 FragmentManager fragmentManager = getFragmentManager(); 
		     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		     scan_coupon_fragment = new Scan_Coupon_Fragment();
		     fragmentTransaction.replace(R.id.fragment_home, scan_coupon_fragment); 
		     fragmentTransaction.commit(); 
		     
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
