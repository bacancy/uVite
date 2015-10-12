package com.wiredave.uvite.vendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.R;

public class Vendor_Payment extends FragmentActivity implements OnClickListener{
	
	RelativeLayout rl_back;
	Verify_Payment_Fragment verify_payment_fragment;
	Authorized_Payment_Fragment authorized_payment_fragment;	
	public static TextView txt_header;
	public static FragmentManager fragmentManager;
	static final int RESULT_CLOSE_ALL = 5 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendor_payment);
		
		initialization(); //initialize view of objects...
		
		//Master data Fragments 
		verify_payment_fragment = new Verify_Payment_Fragment(); 
		authorized_payment_fragment = new Authorized_Payment_Fragment();
					
	    fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_paymentcontainer, verify_payment_fragment);
			
		fragmentTransaction.commit();
		
		rl_back.setOnClickListener(this);
		 
	}
	
	public void initialization()
	{
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		txt_header = (TextView)findViewById(R.id.txt_header);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rl_back)
		{
			overridePendingTransition(0, 0);
			finish();
			
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
	
	public static void Replace_Authorized_Payment_Fragment()
	{
		if(fragmentManager != null)
		{
		 //replace fragment...	   	   
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    Authorized_Payment_Fragment authorized_payment_fragment = new Authorized_Payment_Fragment();
	    fragmentTransaction.replace(R.id.fragment_paymentcontainer, authorized_payment_fragment); 
	     
	    fragmentTransaction.commit();
		}
		
	}
	
}
