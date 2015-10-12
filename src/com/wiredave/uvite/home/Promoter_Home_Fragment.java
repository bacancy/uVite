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
import com.wiredave.uvite.asynctask.Promoter_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;

public class Promoter_Home_Fragment extends Fragment implements OnClickListener{

	View view = null;
	Button btn_edit_profile,btn_all_coupons,btn_withdrawal,btn_payment_information,
	       btn_logout;
	DrawerLayout drawerLayout;
	ImageView img_promoter;
	RelativeLayout leftRL,rl_edit_profile,rl_all_coupons,rl_withdrawal,rl_payment_information,
	               rl_logout;
	ImageLoader imageLoader = null;	
	private DisplayImageOptions options;
	Promoter_AllCoupon_Fragment promoter_allcoupon_fragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.promoter_home_fragment, container, false);
		
		initialization(); //initialize all objects of view...

		/*Home_Screen.img_optionwindow.setVisibility(View.GONE);*/
		
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
		 
		btn_edit_profile.setOnClickListener(this);
		btn_all_coupons.setOnClickListener(this);
		btn_withdrawal.setOnClickListener(this);
		btn_payment_information.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		
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
			
		img_promoter = (ImageView)view.findViewById(R.id.img_promoter);
		
		leftRL = (RelativeLayout)view.findViewById(R.id.rl_leftdrawer);
		rl_edit_profile = (RelativeLayout)view.findViewById(R.id.rl_edit_profile);
		rl_all_coupons = (RelativeLayout)view.findViewById(R.id.rl_all_coupons);
		rl_withdrawal = (RelativeLayout)view.findViewById(R.id.rl_withdrawal);
		rl_payment_information = (RelativeLayout)view.findViewById(R.id.rl_payment_information);
		rl_logout = (RelativeLayout)view.findViewById(R.id.rl_logout);
		
		btn_edit_profile = (Button)view.findViewById(R.id.btn_edit_profile);
		btn_all_coupons = (Button)view.findViewById(R.id.btn_all_coupons);
		btn_withdrawal = (Button)view.findViewById(R.id.btn_withdrawal);
		btn_payment_information = (Button)view.findViewById(R.id.btn_payment_information);
		btn_logout = (Button)view.findViewById(R.id.btn_logout);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_edit_profile)
		{
			//check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			   {
				 //get promoter profile details...
				 new Promoter_SignUp_Task(getActivity(), "get").execute();
				 
				}else {		
				  Common.showalertDialog(getActivity(),getActivity().getResources().getString(R.string.alert_internetconnectivity));
			   }
					
		}else if (v.getId() == R.id.btn_all_coupons) {
			
			 //replace fragment...
			 FragmentManager fragmentManager = getFragmentManager(); 
		     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		     promoter_allcoupon_fragment = new Promoter_AllCoupon_Fragment();
		     fragmentTransaction.replace(R.id.fragment_home, promoter_allcoupon_fragment); 
		     
		     fragmentTransaction.commit();
		     
		}else if (v.getId() == R.id.btn_withdrawal) {
			
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
			
			 //replace fragment...
			 FragmentManager fragmentManager = getFragmentManager(); 
		     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		     promoter_allcoupon_fragment = new Promoter_AllCoupon_Fragment();
		     fragmentTransaction.replace(R.id.fragment_home, promoter_allcoupon_fragment); 
		     
		     fragmentTransaction.commit(); 
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
