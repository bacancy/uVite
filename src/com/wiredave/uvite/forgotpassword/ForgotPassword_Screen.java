package com.wiredave.uvite.forgotpassword;

import com.wiredave.uvite.R;
import com.wiredave.uvite.R.id;
import com.wiredave.uvite.R.layout;
import com.wiredave.uvite.asynctask.Forgot_Password_Task;
import com.wiredave.uvite.asynctask.LogIn_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;
import com.wiredave.uvite.registration.Registration_Screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgotPassword_Screen extends Activity implements OnClickListener{
	
	EditText ed_email;
	Button btn_sendpassword;
	RelativeLayout rl_back;
	String login_type = "";
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			startActivity(new Intent(ForgotPassword_Screen.this,Login_Screen.class).putExtra("login_type", login_type));
			overridePendingTransition(0, 0);
			finish();
			
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword_screen);
		
		initialization(); //initialize all objects of view...
		

		try {
				Bundle bundle = getIntent().getExtras();
				if(bundle != null)
				{
					login_type = bundle.getString("login_type");
				}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		rl_back.setOnClickListener(this);
		btn_sendpassword.setOnClickListener(this);
	}
	
	public void initialization()
	{
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		ed_email = (EditText)findViewById(R.id.ed_email);
		btn_sendpassword = (Button)findViewById(R.id.btn_sendpassword);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rl_back)
		{
			startActivity(new Intent(ForgotPassword_Screen.this,Login_Screen.class).putExtra("login_type", login_type));
			overridePendingTransition(0, 0);
			finish();
			
		}else if(v.getId() == R.id.btn_sendpassword)
		{
			if(ed_email.getText().toString().length() == 0)
			{
				Common.showalertDialog(ForgotPassword_Screen.this, this.getResources().getString(R.string.alertmmsg_enter_your_email));
			}else {
				
				if(Common.isValidEmail(ed_email.getText().toString()))
				{
					//check internet connetivity...
					 if(Common.isConnectingToInternet(ForgotPassword_Screen.this))
						{
						//call forgot password api...						 
						 new Forgot_Password_Task(ForgotPassword_Screen.this, ed_email.getText().toString(),login_type).execute();
													 					
						}else {		
						  Common.showalertDialog(ForgotPassword_Screen.this, ForgotPassword_Screen.this.getResources().getString(R.string.alert_internetconnectivity));
						}
					
				}else {
					Common.showalertDialog(ForgotPassword_Screen.this, this.getResources().getString(R.string.alertmmsg_enter_valid_email));
				}
			}
		}
	}


}
