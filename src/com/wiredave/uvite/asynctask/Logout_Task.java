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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;

public class Logout_Task extends AsyncTask<String, Void , String> {
	
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "";
	String TAG="Logout_Task";
	
	/**
	 * @author Jay Patel
	 * Called this to LogOut.
	 */
	
    public Logout_Task (Context context){
         this.mContext = context;
         
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
	     httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_Logout);
		    		   		    	
   		 MultipartEntity mpEntity = new MultipartEntity();
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));
		 mpEntity.addPart("type", new StringBody(Common.ref_database.getUserType()));
		 /*if(Common.ref_database.getUserType().equals("Vendor"))
		 {
			 mpEntity.addPart("type", new StringBody("vendor"));	 
		 }else if (Common.ref_database.getUserType().equals("Promoter")) {
			 mpEntity.addPart("type", new StringBody("promoter"));
		}*/
		 
		}else {
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     mpEntity.addPart("type", new StringBody(""));
		}

   		
   		 httppost.setEntity(mpEntity);
		 HttpResponse response = httpClient.execute(httppost);
   		 HttpEntity responseEntity = response.getEntity();
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Logout_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Logout_Task "+e.toString());
	   
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
						Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_logout_complete),Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
						try {
							Common.ref_database.Delete_Login_Detail();	
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
						//clear all activity and start login screen...
						mContext.startActivity(new Intent(mContext,Login_Screen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
						((Activity) mContext).overridePendingTransition(0, 0);
						((Activity) mContext).finish();
						
					}else if (!jobj_status.getBoolean("success")) {
						
						Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_logout_incomplete),Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
					
					/*Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.app_crash),Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();*/
				}	
		   
		  
	  }
		 }
