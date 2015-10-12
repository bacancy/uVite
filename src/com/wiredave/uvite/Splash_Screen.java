package com.wiredave.uvite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.home.Home_Screen;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;
import com.wiredave.uvite.vendor.Vendor_Coupon_Listing;

public class Splash_Screen extends Activity{
	
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		Common.ref_database = new Referral_Database(Splash_Screen.this);
		
		new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. 
             */	
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
            	if(Common.ref_database.checkIfExist()){   
            		startActivity(new Intent(Splash_Screen.this,Home_Screen.class));
            		/*if(Common.ref_database.getUserType().equals("Vendor")){
            			startActivity(new Intent(Splash_Screen.this,Home_Screen.class));
            		}else{
            			startActivity(new Intent(Splash_Screen.this,Promoter_AllCoupon_Fragment.class));
            		}*/
            		
    			}else {
    				startActivity(new Intent(Splash_Screen.this,Make_Choice.class));	
				}
    			
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
	}

}
