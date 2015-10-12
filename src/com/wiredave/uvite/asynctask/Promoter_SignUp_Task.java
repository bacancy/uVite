package com.wiredave.uvite.asynctask;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
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
import com.wiredave.uvite.bean.Promoter_Login_Bean;
import com.wiredave.uvite.bean.Vendor_Login_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;
import com.wiredave.uvite.registration.Promoter_Registration;
import com.wiredave.uvite.registration.Vendor_Registration;

public class Promoter_SignUp_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",status = "";;
	String TAG="Promoter_SignUp_Task";
	MultipartEntity mpEntity = null;
	
	/**
	 * @author Jay Patel
	 * Called this to Promoter Sign Up.
	 */
	
    public Promoter_SignUp_Task (Context context,String status){
         this.mContext = context;
         this.status = status;
         
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
	
		 Common.device_token = Common.getDeviceUniqueID(mContext);
		  // 1. create HttpClient
		 HttpClient httpClient = new DefaultHttpClient();
		 
		 HttpPost httppost = null;
		 mpEntity = new MultipartEntity();
		 
		 
		 if(status.equals("get"))
		 {
			 //get promoter profile all deatils...
			 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_GetPromoterSignUp);
			 
			 if(Common.ref_database == null)
				 Common.ref_database = new Referral_Database(mContext);
			 		
			 if(Common.ref_database.checkIfExist())
				{	
				 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
				 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));	 
				}else {
			     mpEntity.addPart("user_id", new StringBody(""));
			     mpEntity.addPart("user_token", new StringBody(""));
				}
			 
		 }else if(status.equals("insert") || status.equals("update"))
		 {
			 //sign up new promoter...
			 if(status.equals("insert"))
			 {
				 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_PromoterSignUp);
				
				 mpEntity.addPart("device_type", new StringBody(Common.device_type));
			   	 mpEntity.addPart("device_token", new StringBody(Common.device_token));
				 mpEntity.addPart("password", new StringBody(Common.password));				 
				 
			 }else if (status.equals("update")) {
				 
				 //update promoter profile details...
				 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_UpdatePromoterSignUp);
			
				 if(Common.ref_database.checkIfExist())
					{	
					 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
					 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));	 
					}else {
				     mpEntity.addPart("user_id", new StringBody(""));
				     mpEntity.addPart("user_token", new StringBody(""));
					}
			 }
			 
			 if (Common.file_path == null || Common.file_path.equalsIgnoreCase("")) {
		 			try {				
		 				mpEntity.addPart("logo", new StringBody(""));
		 				Log.v("file path", "unavailable");
		 			} catch (Exception e) {
		 				// TODO: handle exception
		 			}
		 		} else {
		 			File file = new File(Common.file_path);
		 			ContentBody cbFile = new FileBody(file, "image/*");
		 			mpEntity.addPart("logo", cbFile);			
		 			Log.v("file path", "available");
		 		}
		   		 
		   		 Log.d("promotersignin_params", ""+Common.firstname+"  "+Common.lastname+"  "+Common.emailid+"  "+Common.phonenumber+
		   				 "  "+Common.contactaddress+"  "+Common.password+"  "+Common.username+"  "+Common.paypal_id);
		   		 
		   		mpEntity.addPart("firstname", new StringBody(Common.firstname));
		   		mpEntity.addPart("lastname", new StringBody(Common.lastname));
		   		mpEntity.addPart("emailid", new StringBody(Common.emailid));
		   		mpEntity.addPart("phonenumber", new StringBody(Common.phonenumber));
		   		mpEntity.addPart("contactaddress", new StringBody(Common.contactaddress));   		   		   		   				   		
		   		//mpEntity.addPart("username", new StringBody(Common.username));
		   		mpEntity.addPart("paypalid", new StringBody(Common.paypal_id));
			 
		 }
		 
			
   		httppost.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(httppost);
   		HttpEntity responseEntity = response.getEntity();
   		if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		else
   		   result = "Error";
		 
   		Log.d(TAG,"Promoter_SignUp_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Promoter_SignUp_Task "+e.toString());
	   
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
						Common.clear_allpromoterregistration();
						
						if(status.equals("get"))
						{
                            CommonUtil.promoter_login_array.clear();
							
							JSONObject json_promoter_details = jobj_status.getJSONObject("user_details");
							CommonUtil.promoter_login_bean = new Promoter_Login_Bean();
							CommonUtil.promoter_login_bean.setId(json_promoter_details.getString("id"));
							CommonUtil.promoter_login_bean.setDevice_type(json_promoter_details.getString("device_type"));
							CommonUtil.promoter_login_bean.setDevice_token(json_promoter_details.getString("device_token"));
							CommonUtil.promoter_login_bean.setFirst_name(json_promoter_details.getString("first_name"));
							CommonUtil.promoter_login_bean.setLast_name(json_promoter_details.getString("last_name"));
							CommonUtil.promoter_login_bean.setEmail_id(json_promoter_details.getString("email_id"));
							CommonUtil.promoter_login_bean.setPassword(json_promoter_details.getString("password"));
							CommonUtil.promoter_login_bean.setPhone_number(json_promoter_details.getString("phone_number"));
							CommonUtil.promoter_login_bean.setContact_address(json_promoter_details.getString("contact_address"));
							//CommonUtil.promoter_login_bean.setUsername(json_promoter_details.getString("username"));
							CommonUtil.promoter_login_bean.setPaypal_id(json_promoter_details.getString("paypal_id"));
							
							if(json_promoter_details.getString("logo") == null || json_promoter_details.getString("logo").equals(""))
							{
								CommonUtil.promoter_login_bean.setLogo("");	
							}else {
								CommonUtil.promoter_login_bean.setLogo(CommonUtil.PROMOTER_IMAGE_URL+json_promoter_details.getString("logo"));	
							}
							Log.d("promoterlogo",""+CommonUtil.PROMOTER_IMAGE_URL+json_promoter_details.getString("logo"));
							CommonUtil.promoter_login_bean.setStatus(json_promoter_details.getString("status"));
							CommonUtil.promoter_login_bean.setLogin_status(json_promoter_details.getString("login_status"));
							CommonUtil.promoter_login_bean.setUser_token(json_promoter_details.getString("user_token"));
							CommonUtil.promoter_login_bean.setCreated_date(json_promoter_details.getString("Created_date"));
							CommonUtil.promoter_login_bean.setUpdate_Date(json_promoter_details.getString("Update_Date"));
							
							
							CommonUtil.promoter_login_array.add(CommonUtil.promoter_login_bean);
							
							((Activity) mContext).startActivity(new Intent(mContext,Promoter_Registration.class).putExtra("promoter_registration_flag", "update").putExtra("promoter_registration_backflag", "home"));
							((Activity) mContext).overridePendingTransition(0, 0);
							
						}else if (status.equals("insert")) {
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_signup_complete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							
							//start login screen...
							mContext.startActivity(new Intent(mContext,Login_Screen.class).putExtra("login_type", "Promoter"));
							((Activity) mContext).overridePendingTransition(0, 0);
							((Activity) mContext).finish();
						}else if (status.equals("update")) {
							
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_update_signup_complete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							
							((Activity) mContext).finish();
						}
						
						
					}else if (!jobj_status.getBoolean("success")) {
						
						if(status.equals("get"))
						{
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_getting_promotersignup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}else if (status.equals("insert")) {
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_signup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}else if (status.equals("update")) {
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_update_signup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						
						
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
