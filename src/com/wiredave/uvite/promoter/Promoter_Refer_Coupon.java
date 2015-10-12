package com.wiredave.uvite.promoter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Refer_Coupon_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;

public class Promoter_Refer_Coupon extends Activity implements OnClickListener{

	RelativeLayout rl_back;
	ImageView img_coupon,img_refercoupon;
	TextView txt_availability,txt_referralcommission,txt_coupontitle,
	         txt_coupondec,txt_couponreferred,txt_expiredate,txt_coupon_code;
	Button btn_email_address,btn_refer_friend;
	Bundle bundle = null;
	int position = 0;
	
	ImageLoader imageLoader = null;	
	private DisplayImageOptions options;
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promoter_refers_coupon);
		
        imageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
		
		try {
			 bundle = getIntent().getExtras();
			 if(bundle != null)
			 {
				 position = bundle.getInt("coupon_position");				 
				 
			 }else {				 				
				 position = 0;
			 }	
		} catch (Exception e) {
			// TODO: handle exception
			position = 0;
			e.printStackTrace();
		}
		
		initialization(); //initialize all objects of view...
		
		
		
		
		if(CommonUtil.promoter_allactive_coupon_array.size() > 0)
		{
			if(CommonUtil.promoter_allactive_coupon_array.get(position).getLogo() != null && !CommonUtil.promoter_allactive_coupon_array.get(position).getLogo().equals(""))					
			{
			  //download and display image from url
			  imageLoader.displayImage(CommonUtil.promoter_allactive_coupon_array.get(position).getLogo(), img_coupon , options);
			}
			
			if(CommonUtil.promoter_allactive_coupon_array.get(position).getQrcode_image() != null && !CommonUtil.promoter_allactive_coupon_array.get(position).getQrcode_image().equals(""))					
			{
			  //download and display image from url
			  imageLoader.displayImage(CommonUtil.promoter_allactive_coupon_array.get(position).getQrcode_image(), img_refercoupon , options);
			}
			
			txt_availability.setText(this.getResources().getString(R.string.availability)+" "+CommonUtil.promoter_allactive_coupon_array.get(position).getAvailability());
			txt_referralcommission.setText(this.getResources().getString(R.string.revcoupon_referral_commission)+" "+CommonUtil.promoter_allactive_coupon_array.get(position).getCommission());
			txt_coupontitle.setText(CommonUtil.promoter_allactive_coupon_array.get(position).getCoupon_title());
			txt_coupondec.setText(CommonUtil.promoter_allactive_coupon_array.get(position).getCoupon_description());
			txt_couponreferred.setText(this.getResources().getString(R.string.coupon_referred)+" "+CommonUtil.promoter_allactive_coupon_array.get(position).getCoupon_referred());
			txt_expiredate.setText(CommonUtil.promoter_allactive_coupon_array.get(position).getEnd_date());
			txt_coupon_code.setText(CommonUtil.promoter_allactive_coupon_array.get(position).getCoupon_code());
		}
		
		rl_back.setOnClickListener(this);
		btn_email_address.setOnClickListener(this);
		btn_refer_friend.setOnClickListener(this);
		
	}
	
	
	public void initialization()
	{
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		
		img_coupon = (ImageView)findViewById(R.id.img_coupon);
		img_refercoupon = (ImageView)findViewById(R.id.img_refercoupon);
		
		txt_availability = (TextView)findViewById(R.id.txt_availability);
		txt_referralcommission = (TextView)findViewById(R.id.txt_referralcommission);
		txt_coupontitle = (TextView)findViewById(R.id.txt_coupontitle);
		txt_coupondec = (TextView)findViewById(R.id.txt_coupondec);
		txt_couponreferred = (TextView)findViewById(R.id.txt_couponreferred);
		txt_expiredate = (TextView)findViewById(R.id.txt_expiredate);
		txt_coupon_code = (TextView)findViewById(R.id.txt_coupon_code);
		
		btn_email_address = (Button)findViewById(R.id.btn_email_address);
		btn_refer_friend = (Button)findViewById(R.id.btn_refer_friend);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.rl_back)
		{
			overridePendingTransition(0, 0);
			finish();
			
		}else if (v.getId() == R.id.btn_email_address) {
			
			// get prompts.xml view
			LayoutInflater li = LayoutInflater.from(Promoter_Refer_Coupon.this);
			View promptsView = li.inflate(R.layout.prompts, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Promoter_Refer_Coupon.this);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);

			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton(getResources().getString(android.R.string.ok),
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    	
				    	if(userInput.getText().toString().length() != 0)
				    	{
				    	if((Common.isValidEmail(userInput.getText().toString())))
				    	{
				    	 //check internet connetivity...
				   		 if(Common.isConnectingToInternet(Promoter_Refer_Coupon.this))
				   		 {
				   		     //call for refer coupon...
					    	 new Refer_Coupon_Task(Promoter_Refer_Coupon.this, userInput.getText().toString(), CommonUtil.promoter_allactive_coupon_array.get(position).getCoupon_id()).execute(); 
				   		  }else {		
				   			 Common.showalertDialog(Promoter_Refer_Coupon.this, Promoter_Refer_Coupon.this.getResources().getString(R.string.alert_internetconnectivity));
				   		 }
				   		 				    	  
				    	}else {
				    		
				    		Common.showalertDialog(Promoter_Refer_Coupon.this, Promoter_Refer_Coupon.this.getResources().getString(R.string.alertmmsg_enter_valid_email));
						}
				    	}else {
				    		Common.showalertDialog(Promoter_Refer_Coupon.this, Promoter_Refer_Coupon.this.getResources().getString(R.string.alertmmsg_enter_your_email_address));
						}
				    }
				  })
				.setNegativeButton(android.R.string.cancel,
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					   dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			
		}else if (v.getId() == R.id.btn_refer_friend) {
			
		}
	}
	
	
}
