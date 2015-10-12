package com.wiredave.uvite.vendor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Create_Vendor_Coupon_Task;
import com.wiredave.uvite.common.Common;

public class Authorized_Payment_Fragment extends Fragment implements OnClickListener{

	View view = null;	
	Button btn_pay;
	TextView txt_totalamount;
	EditText ed_creditcardnumber;
	Verify_Payment_Fragment verify_payment_fragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.authorized_vendor_payment, container, false);
		
		initialization(); //initialize all objects of view...
						
		btn_pay.setOnClickListener(this);
			
		txt_totalamount.setText(getActivity().getResources().getString(R.string.total_amount)+" "+Common.total_amount);
		
		if(!Common.cardnumber.equals("") && Common.cardnumber != null)
		ed_creditcardnumber.setText(Common.maskNumber(Common.cardnumber, "xxxx-xxxx-xxxx-####"));
				
		return view;
	}
	
	public void initialization()
	{		
		txt_totalamount = (TextView)view.findViewById(R.id.txt_totalamount);
		ed_creditcardnumber = (EditText)view.findViewById(R.id.ed_creditcardnumber);
		btn_pay = (Button)view.findViewById(R.id.btn_pay);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_pay)
		{
			/*//start vendor create coupon screen...
			startActivity(new Intent(getActivity(),Vendor_Create_Coupon.class));
			((Activity) getActivity()).overridePendingTransition(0, 0);
			((Activity) getActivity()).finish();*/
			
			//check internet connetivity...
			 if(Common.isConnectingToInternet(getActivity()))
			 {
				 //call this for creating vendor coupon...
				 new Create_Vendor_Coupon_Task(getActivity()).execute(); 
			  }else {		
				  Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
			 }
		}
	}
	
}
