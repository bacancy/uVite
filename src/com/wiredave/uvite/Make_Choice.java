package com.wiredave.uvite;

import com.wiredave.uvite.login.Login_Screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class Make_Choice extends Activity implements OnClickListener{
	
	RadioButton rb_vendor,rb_promotor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_choice);
		
		initialization(); //initialize all objects of view...
		
		rb_vendor.setChecked(false);
		rb_promotor.setChecked(false);
		
		rb_vendor.setOnClickListener(this);
		rb_promotor.setOnClickListener(this);
		
	}

	public void initialization()
	{			
		rb_vendor = (RadioButton)findViewById(R.id.rb_vendor);
		rb_promotor = (RadioButton)findViewById(R.id.rb_promotor);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rb_vendor)
		{
			rb_vendor.setChecked(true);
			rb_promotor.setChecked(false);
			
			startActivity(new Intent(Make_Choice.this,Login_Screen.class).putExtra("login_type", "Vendor"));
			overridePendingTransition(0, 0);
			finish();
			
		}else if (v.getId() == R.id.rb_promotor) {
			
			rb_vendor.setChecked(false);
			rb_promotor.setChecked(true);
			
			startActivity(new Intent(Make_Choice.this,Login_Screen.class).putExtra("login_type", "Promoter"));
			overridePendingTransition(0, 0);
			finish();
		}
	}
	
}
