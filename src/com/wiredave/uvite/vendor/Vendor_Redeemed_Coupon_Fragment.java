package com.wiredave.uvite.vendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.Make_Choice;
import com.wiredave.uvite.R;
import com.wiredave.uvite.adapter.Vendor_Expired_Coupon_Adapter;
import com.wiredave.uvite.adapter.Vendor_Redeemed_Coupon_Adapter;
import com.wiredave.uvite.asynctask.Get_Vendor_All_Coupon_Task;
import com.wiredave.uvite.asynctask.Vendor_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;

public class Vendor_Redeemed_Coupon_Fragment extends Fragment implements OnClickListener{

	View view = null;
	public static TextView txt_total_coupon,txt_total_referred,txt_total_redeemed;
	public static ListView list_vendor_redeemed;
	DrawerLayout drawerLayout;
	RelativeLayout leftRL;
	RelativeLayout  rl_edit_account, rl_create_coupon, rl_view_coupon,
	rl_scan_coupon, rl_payment_information, rl_logout;
	Scan_Coupon_Fragment scan_coupon_fragment;
	public static Vendor_Redeemed_Coupon_Adapter vendor_expired_coupon_adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.vendor_active_expired_redeemed_coupon, container, false);
		
		initialization(); //initialize all objects of view...
		
		drawerLayout = drawerLayout;
		leftRL = leftRL;
		
		
		scan_coupon_fragment = new Scan_Coupon_Fragment();
		
		Vendor_Coupon_Listing.rl_active_coupon.setBackgroundResource(R.color.unselected_tab);
		Vendor_Coupon_Listing.rl_expired_coupon.setBackgroundResource(R.color.unselected_tab);
		Vendor_Coupon_Listing.rl_redeemed_payment.setBackgroundResource(R.color.selected_tab);
		
		//check internet connetivity...
		 if(Common.isConnectingToInternet(getActivity()))
		 {
			//get vendor all active coupons...
			 new Get_Vendor_All_Coupon_Task(getActivity(), "redeemed").execute(); 
		  }else {		
			 Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
		 }
			
		
		
		
		 
		return view;
	}
	
	public void initialization()
	{				
		txt_total_coupon = (TextView)view.findViewById(R.id.txt_total_coupon);
		txt_total_referred = (TextView)view.findViewById(R.id.txt_total_referred);
		txt_total_redeemed = (TextView)view.findViewById(R.id.txt_total_redeemed);
		
		drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
		leftRL = (RelativeLayout)view.findViewById(R.id.rl_leftdrawer);
		
		list_vendor_redeemed = (ListView)view.findViewById(R.id.list_vendor_active_expired_redeemed);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 if(v.getId() == R.id.rl_edit_account)
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
		}
	}
	
}
