package com.wiredave.uvite.vendor;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.AuthorizeNet_CreditCard_Task;
import com.wiredave.uvite.asynctask.Create_Vendor_Coupon_Task;
import com.wiredave.uvite.common.Common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Vendor_Authorized_CreditCard_Payment extends Activity implements OnClickListener,TextWatcher{
	
	String a;
    int keyDel;
    RelativeLayout rl_back;
    EditText ed_cardnumber,ed_cvv,ed_expirationmonth,ed_expirationyear;
	Button btn_pay;
	static final int RESULT_CLOSE_ALL = 5 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credit_card_payment);
		
		initialization(); //initialize all objects of view...
		
		rl_back.setOnClickListener(this);
		ed_cardnumber.addTextChangedListener(this);
		btn_pay.setOnClickListener(this);
		
	}

	public void initialization()
	{		
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		ed_cardnumber = (EditText)findViewById(R.id.ed_cardnumber);
		ed_cvv = (EditText)findViewById(R.id.ed_cvv);
		ed_expirationmonth = (EditText)findViewById(R.id.ed_expirationmonth);
		ed_expirationyear = (EditText)findViewById(R.id.ed_expirationyear);
		
		btn_pay = (Button)findViewById(R.id.btn_pay);
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rl_back)
		{
			overridePendingTransition(0, 0);
			finish();
			
		}else if(v.getId() == R.id.btn_pay)
		{
			if(ed_cardnumber.getText().toString().length() > 0 && ed_cvv.getText().toString().length() > 0 &&
					ed_expirationmonth.getText().toString().length() > 0 && ed_expirationyear.getText().toString().length() > 0)
			{
				
				if(ed_cardnumber.getText().toString().replace("-", "").length() < 16) 
				{
					Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alertmmsg_check_cardnumber));
				}else if (ed_cvv.getText().toString().length() < 3) {
					Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alertmmsg_check_cvv_code));
				}else if (ed_expirationmonth.getText().toString().length() < 2) {
					Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alertmmsg_check_expired_month));
				}else if (ed_expirationyear.getText().toString().length() < 4) {
					Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alertmmsg_check_expired_year));
				}else {
					
					/*Common.cardnumber = ed_cardnumber.getText().toString().replace("-", "");
					Common.cvv = ed_cvv.getText().toString();
					Common.expiration_month = ed_expirationmonth.getText().toString();
					Common.expiration_year = ed_expirationyear.getText().toString();				
					Common.expiration_date = Common.expiration_month+ed_expirationyear.getText().toString().substring(2);*/
					
					Common.cardnumber = "5424000000000015";
					Common.cvv = "999";
					Common.expiration_month = "12";
					Common.expiration_year = "23";
					Common.expiration_date = "1223";
					
					Log.d("expiration_date", ""+Common.expiration_date);
					
					/*startActivityForResult(new Intent(this,Vendor_Make_Coupon_Payment.class),5);
					overridePendingTransition(0, 0);*/
					 
					 //check internet connetivity...
					 if(Common.isConnectingToInternet(Vendor_Authorized_CreditCard_Payment.this))
					  {
						//call this for authorize .net card payment...
						new AuthorizeNet_CreditCard_Task(Vendor_Authorized_CreditCard_Payment.this,"create_coupon",Common.cardnumber,Common.expiration_date,Common.cvv,Common.total_amount).execute(); 
					  }else {		
						  Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alert_internetconnectivity));
					  }
				}
		
			}else {
				Common.showalertDialog(Vendor_Authorized_CreditCard_Payment.this, Vendor_Authorized_CreditCard_Payment.this.getResources().getString(R.string.alertmmsg_fill_all_details));
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(resultCode)
	    {
	    case RESULT_CLOSE_ALL:
	    		    			
	        setResult(RESULT_CLOSE_ALL);
	        finish();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

}
