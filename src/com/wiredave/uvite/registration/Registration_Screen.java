package com.wiredave.uvite.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.forgotpassword.ForgotPassword_Screen;
import com.wiredave.uvite.login.Login_Screen;

public class Registration_Screen extends Activity implements OnClickListener{
	
	RadioButton rb_vendortype,rb_promotortype;
	Button btn_register;
	RelativeLayout rl_back;
	String login_type = "";
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{		
			startActivity(new Intent(Registration_Screen.this,Login_Screen.class).putExtra("login_type", login_type));
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		Common.file_path = "" ;
		
		super.onResume();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_screen);
		
		initialization(); //initialize all objects of view...
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
			login_type = bundle.getString("login_type");
		}
		
		rb_vendortype.setChecked(true);
		rb_promotortype.setChecked(false);
		
		rl_back.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		rb_vendortype.setOnClickListener(this);
		rb_promotortype.setOnClickListener(this);
		
	}
	
	public void initialization()
	{
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		
		rb_vendortype = (RadioButton)findViewById(R.id.rb_vendortype);
		rb_promotortype = (RadioButton)findViewById(R.id.rb_promotortype);
		
		btn_register = (Button)findViewById(R.id.btn_register);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rl_back)
		{
			startActivity(new Intent(Registration_Screen.this,Login_Screen.class).putExtra("login_type", login_type));
			overridePendingTransition(0, 0);
			finish();
			
		}else if(v.getId() == R.id.btn_register)
		{
			if(rb_vendortype.isChecked())
			{
				startActivity(new Intent(Registration_Screen.this,Vendor_Registration.class).putExtra("login_type", login_type).putExtra("vendor_registration_flag", "insert").putExtra("vendor_registration_backflag", "registration"));
				overridePendingTransition(0, 0);
				finish();
				
			}else if (rb_promotortype.isChecked()) {
				
				startActivity(new Intent(Registration_Screen.this,Promoter_Registration.class).putExtra("login_type", login_type).putExtra("promoter_registration_flag", "insert").putExtra("promoter_registration_backflag", "registration"));
				overridePendingTransition(0, 0);
				finish();
			}
			
		}else if(v.getId() == R.id.rb_vendortype)
		{
			rb_vendortype.setChecked(true);
			rb_promotortype.setChecked(false);
			
		}else if (v.getId() == R.id.rb_promotortype) {
			
			rb_vendortype.setChecked(false);
			rb_promotortype.setChecked(true);
		}
	}

}
