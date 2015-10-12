package com.wiredave.uvite.vendor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Create_Vendor_Coupon_Task;
import com.wiredave.uvite.common.Common;

public class Vendor_Make_Coupon_Payment extends Activity implements OnClickListener{
	
	Button btn_pay;
	RelativeLayout rl_back;
	TextView txt_totalamount;
	EditText ed_creditcardnumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorized_payment);
		initialization(); //initialize all objects of view...
		
		btn_pay.setOnClickListener(this);
			
		txt_totalamount.setText(Vendor_Make_Coupon_Payment.this.getResources().getString(R.string.total_amount)+" "+Common.total_amount);
		
		if(!Common.cardnumber.equals("") && Common.cardnumber != null)
		  ed_creditcardnumber.setText(Common.maskNumber(Common.cardnumber, "xxxx-xxxx-xxxx-####"));
		
		rl_back.setOnClickListener(this);
	}
	
	public void initialization()
	{		
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		txt_totalamount = (TextView)findViewById(R.id.txt_totalamount);
		ed_creditcardnumber = (EditText)findViewById(R.id.ed_creditcardnumber);
		btn_pay = (Button)findViewById(R.id.btn_pay);
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
			/*//start vendor create coupon screen...
			startActivity(new Intent(Authorized_Payment.this,Vendor_Create_Coupon.class));
			((Activity) Authorized_Payment.this).overridePendingTransition(0, 0);
			((Activity) Authorized_Payment.this).finish();*/
			
			
			//check internet connetivity...
			 if(Common.isConnectingToInternet(Vendor_Make_Coupon_Payment.this))
			 {
				//call this for authorize .net card payment...
				//new AuthorizeNet_CreditCard_Task(this,"create_coupon",Common.cardnumber,Common.expiration_date,Common.cvv,Common.total_amount).execute();
					
				 //call this for creating vendor coupon...
				 new Create_Vendor_Coupon_Task(Vendor_Make_Coupon_Payment.this).execute(); 
			  }else {		
				  Common.showalertDialog(Vendor_Make_Coupon_Payment.this, Vendor_Make_Coupon_Payment.this.getResources().getString(R.string.alert_internetconnectivity));
			 }
		}
	}

}
