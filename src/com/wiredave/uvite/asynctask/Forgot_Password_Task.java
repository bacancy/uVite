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
import com.wiredave.uvite.login.Login_Screen;

public class Forgot_Password_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",emailid = "" , type = "";
	String TAG="Forgot_Password_Task";
	
	/**
	 * @author Jay Patel
	 * Called this to Get Forgot Password.
	 */
	
    public Forgot_Password_Task (Context context,String emailid,String type){
         this.mContext = context;
         this.emailid = emailid;
         this.type = type;
         
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
	   	  	   
	   try {			   
		  // 1. create HttpClient
		 HttpClient httpClient = new DefaultHttpClient();
		  // 2. make POST request to the given URL
   		 HttpPost httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_ForgotPassword);   		   		    	
   		
   		MultipartEntity mpEntity = new MultipartEntity();
   		
   		mpEntity.addPart("emailid", new StringBody(emailid));
   		mpEntity.addPart("type", new StringBody(type));
   		
   		httppost.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(httppost);
   		HttpEntity responseEntity = response.getEntity();
   		
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Forgot_Password_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Forgot_Password_Task "+e.toString());
	   
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
												
						Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_forgotpassword_complete),Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
												
					}else if (!jobj_status.getBoolean("success")) {
						
						Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_forgotpassword_incomplete),Toast.LENGTH_SHORT);
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
