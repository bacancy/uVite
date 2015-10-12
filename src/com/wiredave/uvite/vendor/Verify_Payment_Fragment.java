package com.wiredave.uvite.vendor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.AuthorizeNet_CreditCard_Task;
import com.wiredave.uvite.common.Common;

public class Verify_Payment_Fragment extends Fragment implements OnClickListener,TextWatcher{

	View view = null;	
	String a;
    int keyDel;
    EditText ed_cardnumber,ed_cvv,ed_expirationmonth,ed_expirationyear;
	Button btn_pay;
	
	Authorized_Payment_Fragment authorized_payment_fragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.verify_vendor_payment, container, false);
		
		initialization(); //initialize all objects of view...
			
		ed_cardnumber.addTextChangedListener(this);
		btn_pay.setOnClickListener(this);
				
		return view;
	}
	
	public void initialization()
	{		
		
		ed_cardnumber = (EditText)view.findViewById(R.id.ed_cardnumber);
		ed_cvv = (EditText)view.findViewById(R.id.ed_cvv);
		ed_expirationmonth = (EditText)view.findViewById(R.id.ed_expirationmonth);
		ed_expirationyear = (EditText)view.findViewById(R.id.ed_expirationyear);
		
		btn_pay = (Button)view.findViewById(R.id.btn_pay);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_pay)
		{
			if(ed_cardnumber.getText().toString().length() > 0 && ed_cvv.getText().toString().length() > 0 &&
					ed_expirationmonth.getText().toString().length() > 0 && ed_expirationyear.getText().toString().length() > 0)
			{
				
				if(ed_cardnumber.getText().toString().replace("-", "").length() < 16) 
				{
					Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_check_cardnumber));
				}else if (ed_cvv.getText().toString().length() < 3) {
					Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_check_cvv_code));
				}else if (ed_expirationmonth.getText().toString().length() < 2) {
					Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_check_expired_month));
				}else if (ed_expirationyear.getText().toString().length() < 4) {
					Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_check_expired_year));
				}else {
					
					Common.cardnumber = ed_cardnumber.getText().toString().replace("-", "");
					Common.cvv = ed_cvv.getText().toString();
					Common.expiration_month = ed_expirationmonth.getText().toString();
					Common.expiration_year = ed_expirationyear.getText().toString();				
					Common.expiration_date = Common.expiration_month+ed_expirationyear.getText().toString().substring(2);
					
					Log.d("expiration_date", ""+Common.expiration_date);
					
					 //check internet connetivity...
					 if(Common.isConnectingToInternet(getActivity()))
					  {
						//call this for authorize .net card payment...
						new AuthorizeNet_CreditCard_Task(getActivity(),"create_coupon",Common.cardnumber,Common.expiration_date,Common.cvv,Common.total_amount).execute(); 
					  }else {		
						  Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alert_internetconnectivity));
					  }
				}
		
			}else {
				Common.showalertDialog(getActivity(), getActivity().getResources().getString(R.string.alertmmsg_fill_all_details));
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		 boolean flag = true;
         String eachBlock[] = ed_cardnumber.getText().toString().split("-");
         for (int i = 0; i < eachBlock.length; i++) {
             if (eachBlock[i].length() > 4) {
                 flag = false;
             }
         }
         if (flag) {

         	ed_cardnumber.setOnKeyListener(new OnKeyListener() {

                 @Override
                 public boolean onKey(View v, int keyCode, KeyEvent event) {

                     if (keyCode == KeyEvent.KEYCODE_DEL)
                         keyDel = 1;
                     return false;
                 }
             });

             if (keyDel == 0) {

                 if (((ed_cardnumber.getText().length() + 1) % 5) == 0) {

                     if (ed_cardnumber.getText().toString().split("-").length <= 3) {
                     	   ed_cardnumber.setText(ed_cardnumber.getText() + "-");
                     	   ed_cardnumber.setSelection(ed_cardnumber.getText().length());
                     }
                 }
                 a = ed_cardnumber.getText().toString();
             } else {
                 a = ed_cardnumber.getText().toString();
                 keyDel = 0;
             }

         } else {
         	ed_cardnumber.setText(a);
         }
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	
	
}
