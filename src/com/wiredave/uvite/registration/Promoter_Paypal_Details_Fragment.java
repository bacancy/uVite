package com.wiredave.uvite.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Promoter_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;

public class Promoter_Paypal_Details_Fragment extends Fragment implements OnClickListener{
	
	EditText ed_paypalid;
	Button btn_next,btn_addlater,btn_update;
	View view = null;
	RelativeLayout rl_update;
	LinearLayout rl_insert;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//Inflate the layout for this fragment

		view = inflater.inflate(R.layout.promoter_paypal_details_fragment, container, false);
		
		initialization(); //initialize all objects of view...
		
		Promoter_Registration.rl_promoters_contact.setBackgroundResource(R.color.unselected_tab);
		Promoter_Registration.rl_promoters_paypal.setBackgroundResource(R.color.selected_tab);
		
		if(Promoter_Registration.flag.equals("update"))
		{
		  rl_insert.setVisibility(View.GONE);
		  rl_update.setVisibility(View.VISIBLE);
		if(Common.ref_database.checkIfExist())
		{
			if(Common.ref_database.getUserType().equals("Promoter"))
			{
				if(CommonUtil.promoter_login_array.size() > 0)
				{				   
				   ed_paypalid.setText(CommonUtil.promoter_login_array.get(0).getPaypal_id());
				   
				   Common.paypal_id = ed_paypalid.getText().toString();
				}	
			}
		  }
		}else {
			rl_insert.setVisibility(View.VISIBLE);
			rl_update.setVisibility(View.GONE);
		}
		
		btn_next.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		btn_addlater.setOnClickListener(this);
		
		return view;
	}
	
	public void initialization()
	{		
		
		btn_next = (Button)view.findViewById(R.id.btn_next);
		btn_addlater = (Button)view.findViewById(R.id.btn_addlater);
		btn_update = (Button)view.findViewById(R.id.btn_update);
		
		ed_paypalid = (EditText)view.findViewById(R.id.ed_paypalid);
		
		rl_insert = (LinearLayout)view.findViewById(R.id.rl_insert);
		rl_update = (RelativeLayout)view.findViewById(R.id.rl_update);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_next)
		{
			Common.paypal_id = ed_paypalid.getText().toString();
			
			if(ed_paypalid.getText().toString().length() == 0)
			{
				Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_enter_your_email));	
			}else {
				
				if(Common.isValidEmail(ed_paypalid.getText().toString()))
				{
				//check internet connetivity...
				if(Common.isConnectingToInternet(getActivity()))
				{
				  //call promoter sign up api...
				  new Promoter_SignUp_Task(getActivity(),Promoter_Registration.flag).execute();
				  
				}else {		
				  Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
				}
			  }else {
				  Common.showalertDialog(getActivity(), this.getResources().getString(R.string.alertmmsg_enter_valid_email));
			}
		 }
			
		}else if (v.getId() == R.id.btn_addlater) {
			
				Common.paypal_id = "";
				
				//check internet connetivity...
				if(Common.isConnectingToInternet(getActivity()))
				{
				  //call promoter sign up api...
				  new Promoter_SignUp_Task(getActivity(),Promoter_Registration.flag).execute();
				  
				}else {		
				  Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
				}
			
			
		}else if (v.getId() == R.id.btn_update) {
			
            Common.paypal_id = ed_paypalid.getText().toString();
			
			if(ed_paypalid.getText().toString().length() == 0)
			{
				Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_enter_your_email));	
			}else {
				
				if(Common.isValidEmail(ed_paypalid.getText().toString()))
				{
				//check internet connetivity...
				if(Common.isConnectingToInternet(getActivity()))
				{
				  //call promoter sign up api...
				  new Promoter_SignUp_Task(getActivity(),Promoter_Registration.flag).execute();
				  
				}else {		
				  Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
				}
			  }else {
				  Common.showalertDialog(getActivity(), this.getResources().getString(R.string.alertmmsg_enter_valid_email));
			}
		 }
		}
	}

}
