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
import com.wiredave.uvite.bean.Vendor_Login_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;
import com.wiredave.uvite.registration.Vendor_Registration;

public class Vendor_SignUp_Task extends AsyncTask<String, Void , String> {
	
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",status = "";
	String TAG="Vendor_SignUp_Task";
	MultipartEntity mpEntity = null;
	
	/**
	 * @author Jay Patel
	 * Called this to Vendor Sign Up.
	 */
	
    public Vendor_SignUp_Task (Context context,String status){
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
		  // 2. make POST request to the given URL
		 
		 HttpPost httppost = null;
		 mpEntity = new MultipartEntity();
		 
		 if(status.equals("get"))
		 {
			 //get vendor profile all deatils...
			 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_GetVendorSignUp);
			 
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
			 //sign up new vendor...
			 if(status.equals("insert"))
			 {
				 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorSignUp);
				 
				 mpEntity.addPart("device_type", new StringBody(Common.device_type));
			   	 mpEntity.addPart("device_token", new StringBody(Common.device_token));
				 mpEntity.addPart("password", new StringBody(Common.password));
				 
				 
			 }else if (status.equals("update")) {
				 
				//update vendor profile details...
				 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_UpdateVendorSignUp);
				 
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
		   		 
		   		mpEntity.addPart("firstname", new StringBody(Common.firstname));
		   		mpEntity.addPart("lastname", new StringBody(Common.lastname));
		   		mpEntity.addPart("emailid", new StringBody(Common.emailid));
		   		mpEntity.addPart("phonenumber", new StringBody(Common.phonenumber));
		   		mpEntity.addPart("contactaddress", new StringBody(Common.contactaddress));
		   		mpEntity.addPart("address1", new StringBody(Common.address1));
		   		mpEntity.addPart("address2", new StringBody(Common.address2));
		   		mpEntity.addPart("primaryphone", new StringBody(Common.primaryphone));
		   		mpEntity.addPart("fax", new StringBody(Common.fax));
		   		mpEntity.addPart("primaryemail", new StringBody(Common.primaryemail));
		   		mpEntity.addPart("primarycontact", new StringBody(Common.primarycontact));
		   		mpEntity.addPart("additionalcontact", new StringBody(Common.additionalcontact));
		   		/*mpEntity.addPart("username", new StringBody("abcx"));*/
		   		mpEntity.addPart("country", new StringBody(Common.country));
		   		mpEntity.addPart("state", new StringBody(Common.state));
		   		mpEntity.addPart("city", new StringBody(Common.city));
		   		mpEntity.addPart("website", new StringBody(Common.website));
		   		mpEntity.addPart("cardnumber", new StringBody(Common.cardnumber));
			   	mpEntity.addPart("cvv", new StringBody(Common.cvv));
			   	mpEntity.addPart("expiration_month", new StringBody(Common.expiration_month));
			   	mpEntity.addPart("expiration_year", new StringBody(Common.expiration_year));
		   		
			 
		 }
   		    		   		    	
   		httppost.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(httppost);
   		HttpEntity responseEntity = response.getEntity();
   		
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Vendor_SignUp_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Vendor_SignUp_Task "+e.toString());
	   
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
						Common.clear_allvendorregistration();
						
						if(status.equals("get"))
						{
							CommonUtil.vendor_login_array.clear();
							
							JSONObject json_vendor_details = jobj_status.getJSONObject("user_details");
							CommonUtil.vendor_login_bean = new Vendor_Login_Bean();
							CommonUtil.vendor_login_bean.setId(json_vendor_details.getString("id"));
							CommonUtil.vendor_login_bean.setDevice_type(json_vendor_details.getString("device_type"));
							CommonUtil.vendor_login_bean.setDevice_token(json_vendor_details.getString("device_token"));
							CommonUtil.vendor_login_bean.setFirst_name(json_vendor_details.getString("first_name"));
							CommonUtil.vendor_login_bean.setLast_name(json_vendor_details.getString("last_name"));
							CommonUtil.vendor_login_bean.setEmail_id(json_vendor_details.getString("email_id"));
							CommonUtil.vendor_login_bean.setPassword(json_vendor_details.getString("password"));
							CommonUtil.vendor_login_bean.setPhone_number(json_vendor_details.getString("phone_number"));
							CommonUtil.vendor_login_bean.setContact_address(json_vendor_details.getString("contact_address"));
							CommonUtil.vendor_login_bean.setUsername(json_vendor_details.getString("username"));
							CommonUtil.vendor_login_bean.setAddress_1(json_vendor_details.getString("address_1"));
							CommonUtil.vendor_login_bean.setAddress_2(json_vendor_details.getString("address_2"));
							CommonUtil.vendor_login_bean.setCountry(json_vendor_details.getString("country"));
							CommonUtil.vendor_login_bean.setState(json_vendor_details.getString("state"));
							CommonUtil.vendor_login_bean.setCity(json_vendor_details.getString("city"));
							CommonUtil.vendor_login_bean.setPrimary_phone(json_vendor_details.getString("primary_phone"));
							CommonUtil.vendor_login_bean.setFax(json_vendor_details.getString("fax"));
							CommonUtil.vendor_login_bean.setPrimary_email(json_vendor_details.getString("primary_email"));
							CommonUtil.vendor_login_bean.setPrimary_contact(json_vendor_details.getString("primary_contact"));
							CommonUtil.vendor_login_bean.setAdditional_contacts(json_vendor_details.getString("additional_contacts"));
							CommonUtil.vendor_login_bean.setWebsite(json_vendor_details.getString("website"));
							
							if(json_vendor_details.getString("logo") == null || json_vendor_details.getString("logo").equals(""))
							{
								CommonUtil.vendor_login_bean.setLogo("");	
							}else {
								CommonUtil.vendor_login_bean.setLogo(CommonUtil.VENDOR_IMAGE_URL+json_vendor_details.getString("logo"));	
							}
							Log.d("vendorlogo",""+CommonUtil.VENDOR_IMAGE_URL+json_vendor_details.getString("logo"));
							CommonUtil.vendor_login_bean.setStatus(json_vendor_details.getString("status"));
							CommonUtil.vendor_login_bean.setLogin_status(json_vendor_details.getString("login_status"));
							CommonUtil.vendor_login_bean.setUser_token(json_vendor_details.getString("user_token"));
							CommonUtil.vendor_login_bean.setCreated_date(json_vendor_details.getString("Created_date"));
							CommonUtil.vendor_login_bean.setUpdate_Date(json_vendor_details.getString("Update_Date"));
													    
							if(json_vendor_details.has("payment_detail"))
							{
								JSONObject json_payment_detail = json_vendor_details.getJSONObject("payment_detail");
								
								if(json_payment_detail.getString("card_number") == null || json_payment_detail.getString("card_number").equalsIgnoreCase("null"))
								{
									CommonUtil.vendor_login_bean.setCard_number("");
								}else {
									CommonUtil.vendor_login_bean.setCard_number(json_payment_detail.getString("card_number"));
								}
								
								if(json_payment_detail.getString("cvv") == null || json_payment_detail.getString("cvv").equalsIgnoreCase("null"))
								{
									CommonUtil.vendor_login_bean.setCvv("");
								}else {
									CommonUtil.vendor_login_bean.setCvv(json_payment_detail.getString("cvv"));
								}
								
								if(json_payment_detail.getString("expiration_month") == null || json_payment_detail.getString("expiration_month").equalsIgnoreCase("null"))
								{
									CommonUtil.vendor_login_bean.setExpiration_month("");
								}else {
									CommonUtil.vendor_login_bean.setExpiration_month(json_payment_detail.getString("expiration_month"));
								}
								
								if(json_payment_detail.getString("expiration_year") == null || json_payment_detail.getString("expiration_year").equalsIgnoreCase("null"))
								{
									CommonUtil.vendor_login_bean.setExpiration_year("");
								}else {
									CommonUtil.vendor_login_bean.setExpiration_year(json_payment_detail.getString("expiration_year"));
								}
																							
							}
								
							CommonUtil.vendor_login_array.add(CommonUtil.vendor_login_bean);
							
							((Activity) mContext).startActivity(new Intent(mContext,Vendor_Registration.class).putExtra("vendor_registration_flag", "update").putExtra("vendor_registration_backflag", "home"));
							((Activity) mContext).overridePendingTransition(0, 0);
							
						}else if (status.equals("insert")) {
							Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_signup_complete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							
							//start login screen...
							mContext.startActivity(new Intent(mContext,Login_Screen.class).putExtra("login_type", "Vendor"));
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
							Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_getting_vendorsignup_incomplete));
							/*Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_getting_vendorsignup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();*/
						}else if (status.equals("insert")) {
							Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_signup_incomplete));
							/*Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_signup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();*/
						}else if (status.equals("update")) {
							Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_update_signup_incomplete));
							/*Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_update_signup_incomplete),Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();*/
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
