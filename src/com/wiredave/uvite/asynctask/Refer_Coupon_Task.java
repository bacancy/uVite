package com.wiredave.uvite.asynctask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;

public class Refer_Coupon_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",emailid = "",coupon_id = "";
	String TAG="Refer_Coupon_Task";
	
	/**
	 * @author Jay Patel
	 * Called this for Refer Coupon.
	 */
	
    public Refer_Coupon_Task(Context context,String emailid, String coupon_id){
    	
         this.mContext = context;         
         this.emailid = emailid;
         this.coupon_id = coupon_id;
         
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
		 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_PromoterReferCoupon);
		    		   		    	
   		 MultipartEntity mpEntity = new MultipartEntity();
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));		 
		}else {
			
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     
		}
   		
		 mpEntity.addPart("emailid", new StringBody(emailid));
		 mpEntity.addPart("coupon_id", new StringBody(coupon_id));
		 
   		 httppost.setEntity(mpEntity);
		 HttpResponse response = httpClient.execute(httppost);
   		 HttpEntity responseEntity = response.getEntity();
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Refer_Coupon_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Refer_Coupon_Task "+e.toString());
	   
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
					
					  Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_refer_coupon));
 					
					}else if (!jobj_status.getBoolean("success")) {
						
					 Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_not_refer_coupon));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
					
				}	
		   
	      }
   }
