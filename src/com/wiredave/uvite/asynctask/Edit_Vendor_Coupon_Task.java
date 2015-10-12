package com.wiredave.uvite.asynctask;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.vendor.Vendor_Coupon_Listing;

public class Edit_Vendor_Coupon_Task extends AsyncTask<String, Void , String> {
	
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",coupon_status = "",coupon_id = "0";
	String TAG="Edit_Vendor_Coupon_Task";
	
	/**
	 * @author Jay Patel
	 * Called this to Edit Vendor Coupon Task.
	 */
	
    public Edit_Vendor_Coupon_Task (Context context,String coupon_id,String coupon_status){
         this.mContext = context;
         this.coupon_id = coupon_id;
         this.coupon_status = coupon_status;
         
         
         if(Common.ref_database == null)			
     	  Common.ref_database = new Referral_Database(context);
         
    }
    
	@Override
    protected void onPreExecute() {
		   
		   pd = new ProgressDialog(mContext);
		   pd.setMessage(mContext.getResources().getString(R.string.please_wait));
	       pd.show();
    }
	
	  @Override
	  protected String doInBackground(String... urls) {
	  
	   result = "";	 
	   HttpPost httppost = null;	   
	   
	   try {			   
		 
		  // 1. create HttpClient
		 HttpClient httpClient = new DefaultHttpClient();
		 
		  // 2. make POST request to the given URL
		 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorEditCoupon);
		 
	     
   		 MultipartEntity mpEntity = new MultipartEntity();
   		
   		 if (Common.file_path == null || Common.file_path.equalsIgnoreCase("")) {
	 			try {				
	 				mpEntity.addPart("logo", new StringBody(""));
	 				Log.v("file path", "unavilable");
	 			} catch (Exception e) {
	 				// TODO: handle exception
	 			}
	 		} else {
	 			File file = new File(Common.file_path);
	 			ContentBody cbFile = new FileBody(file, "image/*");
	 			mpEntity.addPart("logo", cbFile);			
	 			Log.v("file path", "avilable");
	 		}
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		  mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		  mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));
		 
		}else {
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     
		}
   		
   		 mpEntity.addPart("coupon_id", new StringBody(coupon_id));
   		 mpEntity.addPart("coupon_title", new StringBody(Common.coupon_title));
   		 mpEntity.addPart("coupon_description", new StringBody(Common.coupon_description));
   		 mpEntity.addPart("coupon_category", new StringBody(Common.category));
   		 mpEntity.addPart("discount_type", new StringBody(Common.discount_type));
   		 mpEntity.addPart("start_date", new StringBody(Common.start_date));
   		 mpEntity.addPart("end_date", new StringBody(Common.end_date));
   		 mpEntity.addPart("commission", new StringBody(Common.referral_commission));
   		 mpEntity.addPart("total_coupon", new StringBody(Common.number_of_coupons));
   		 mpEntity.addPart("total_amount", new StringBody(Common.total_amount));
   		 
   		
   		Log.d("edit_response", "coupon_id "+coupon_id+" user_id "+Common.ref_database.getUserID()+" user_token "+Common.ref_database.getUserToken()+" coupon_title "+Common.coupon_title+" coupon_description "+Common.coupon_description+
   				" category "+Common.category+" discount_type "+Common.discount_type+" start_date "+Common.start_date+" end_date "+Common.end_date+" referral_commission "+Common.referral_commission+" number_of_coupons "+
   				Common.number_of_coupons+" total_amount "+Common.total_amount);
   		
   		 httppost.setEntity(mpEntity);
		 HttpResponse response = httpClient.execute(httppost);
   		 HttpEntity responseEntity = response.getEntity();
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Edit_Vendor_Coupon_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Edit_Vendor_Coupon_Task "+e.toString());
	   
	    return "Error";
	   }
	   return result;
	  }
	  @Override
	  protected void onPostExecute(String result) 
	  {
		  if (pd != null && pd.isShowing()) {
	            pd.dismiss();
	        }
				try {
					JSONObject jobj_status=new JSONObject(result);					
					if(jobj_status.getBoolean("success"))
					{						
					  // finish activity	
					  ((Activity) mContext).overridePendingTransition(0, 0);
					  ((Activity) mContext).finish();
							
					  // replace fragment...
					  Vendor_Coupon_Listing.Replace_Vendor_Coupon_Fragment(coupon_status);
													
					}else if (!jobj_status.getBoolean("success")) {
						
					  Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_coupon_not_added));
																	
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
				}	
		   
		  
	  }
		 }
