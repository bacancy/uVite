package com.wiredave.uvite.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.wiredave.uvite.Make_Choice;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.LogIn_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.forgotpassword.ForgotPassword_Screen;
import com.wiredave.uvite.registration.Promoter_Registration;
import com.wiredave.uvite.registration.Vendor_Registration;

public class Login_Screen extends Activity implements OnClickListener{

	Button btn_login;
	CheckBox cb_rememberme;
	EditText ed_email, ed_password;
	TextView txt_signup, txt_forgotpassword;
	String login_type = "";

	// RadioButton rb_vendortype,rb_promotortype;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(Login_Screen.this, Make_Choice.class));
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);

		try {
			if (Common.ref_database == null)
				Common.ref_database = new Referral_Database(Login_Screen.this);

			Bundle bundle = getIntent().getExtras();
			if (bundle != null) {
				login_type = bundle.getString("login_type");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		initialization(); // initialize all objects of view...

		/*if (Common.sharedpreference_login.getInstance(Login_Screen.this)
				.Get_Rememberme()) {
			cb_rememberme.setChecked(true);
			ed_email.setText(Common.sharedpreference_login.getInstance(
					Login_Screen.this).Get_Email());
			ed_password.setText(Common.sharedpreference_login.getInstance(
					Login_Screen.this).Get_Password());
		} else {
			cb_rememberme.setChecked(false);
			ed_email.setText("");
			ed_password.setText("");
		}*/

		/*
		 * rb_vendortype.setChecked(true); rb_promotortype.setChecked(false);
		 */

		/*
		 * rb_vendortype.setOnClickListener(this);
		 * rb_promotortype.setOnClickListener(this);
		 */
		//cb_rememberme.setOnCheckedChangeListener(this);
		btn_login.setOnClickListener(this);
		txt_signup.setOnClickListener(this);
		txt_forgotpassword.setOnClickListener(this);
	}

	public void initialization() {
		txt_signup = (TextView) findViewById(R.id.txt_signup);
		txt_forgotpassword = (TextView) findViewById(R.id.txt_forgotpassword);

		ed_email = (EditText) findViewById(R.id.ed_email);
		ed_password = (EditText) findViewById(R.id.ed_password);

		ed_password.setTypeface(Typeface.DEFAULT);
		ed_password.setTransformationMethod(new PasswordTransformationMethod());
		
		/*cb_rememberme = (CheckBox) findViewById(R.id.cb_rememberme);*/

		btn_login = (Button) findViewById(R.id.btn_login);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_login) {
			if (ed_email.getText().toString().length() == 0
					&& ed_password.getText().toString().length() == 0) {
				Common.showalertDialog(Login_Screen.this, this.getResources()
						.getString(R.string.alertmmsg_fill_all_details));
			} else {
				if (ed_email.getText().toString().length() > 0) {
					if (Common.isValidEmail(ed_email.getText().toString())) {
						if (ed_password.getText().toString().length() > 0) {
							// check internet connetivity...
							if (Common
									.isConnectingToInternet(Login_Screen.this)) {
								// call sign in api...
								new LogIn_Task(Login_Screen.this, login_type,
										ed_email.getText().toString(),
										ed_password.getText().toString())
										.execute();

								/*
								 * if(rb_vendortype.isChecked()) { new
								 * LogIn_Task
								 * (Login_Screen.this,"Vendor",ed_email
								 * .getText()
								 * .toString(),ed_password.getText().toString
								 * ()).execute();
								 * 
								 * }else if (rb_promotortype.isChecked()) {
								 * 
								 * new
								 * LogIn_Task(Login_Screen.this,"Promoter",ed_email
								 * .getText().toString(),ed_password.getText().
								 * toString()).execute();
								 * 
								 * }
								 */

							} else {
								Common.showalertDialog(
										Login_Screen.this,
										Login_Screen.this
												.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}

						} else {
							Common.showalertDialog(
									Login_Screen.this,
									this.getResources()
											.getString(
													R.string.alertmmsg_enter_your_password));
						}
					} else {
						Common.showalertDialog(
								Login_Screen.this,
								this.getResources().getString(
										R.string.alertmmsg_enter_valid_email));
					}
				} else {
					Common.showalertDialog(
							Login_Screen.this,
							this.getResources().getString(
									R.string.alertmmsg_enter_your_email));
				}
			}
		} else if (v.getId() == R.id.txt_forgotpassword) {

			// start forgot password screen to get password.
			startActivity(new Intent(Login_Screen.this,
					ForgotPassword_Screen.class).putExtra("login_type",
					login_type));
			overridePendingTransition(0, 0);
			finish();

		} else if (v.getId() == R.id.txt_signup) {

			if (login_type.equals("Vendor")) {

				// start registration screen for register.s
				/*
				 * startActivity(new Intent(Login_Screen.this,
				 * Registration_Screen.class).putExtra("login_type",
				 * login_type)); overridePendingTransition(0, 0); finish();
				 */

				startActivity(new Intent(Login_Screen.this,
						Vendor_Registration.class)
						.putExtra("login_type", login_type)
						.putExtra("vendor_registration_flag", "insert")
						.putExtra("vendor_registration_backflag",
								"registration"));
				overridePendingTransition(0, 0);
				finish();
			} else if (login_type.equals("Promoter")) {

				/*
				 * // start registration screen for register.s startActivity(new
				 * Intent(Login_Screen.this,
				 * Registration_Screen.class).putExtra("login_type",
				 * login_type)); overridePendingTransition(0, 0); finish();
				 */

				startActivity(new Intent(Login_Screen.this,
						Promoter_Registration.class)
						.putExtra("login_type", login_type)
						.putExtra("promoter_registration_flag", "insert")
						.putExtra("promoter_registration_backflag",
								"registration"));
				overridePendingTransition(0, 0);
				finish();
			}

		}/*
		 * else if(v.getId() == R.id.rb_vendortype) {
		 * rb_vendortype.setChecked(true); rb_promotortype.setChecked(false);
		 * 
		 * }else if (v.getId() == R.id.rb_promotortype) {
		 * 
		 * rb_vendortype.setChecked(false); rb_promotortype.setChecked(true); }
		 */
	}

	/*@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (buttonView.getId() == R.id.cb_rememberme) {
			Common.sharedpreference_login.getInstance(Login_Screen.this)
					.Set_Rememberme(isChecked);
			if (isChecked) {
				Common.sharedpreference_login.getInstance(Login_Screen.this)
						.Set_Email(ed_email.getText().toString());
				Common.sharedpreference_login.getInstance(Login_Screen.this)
						.Set_Password(ed_password.getText().toString());
			} else {

				Common.sharedpreference_login.getInstance(Login_Screen.this)
						.Set_Email("");
				Common.sharedpreference_login.getInstance(Login_Screen.this)
						.Set_Password("");
			}
		}
	}*/

}
