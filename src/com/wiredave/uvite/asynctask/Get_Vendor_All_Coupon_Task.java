package com.wiredave.uvite.asynctask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.wiredave.uvite.R;
import com.wiredave.uvite.adapter.Vendor_Active_Coupon_Adapter;
import com.wiredave.uvite.adapter.Vendor_Expired_Coupon_Adapter;
import com.wiredave.uvite.adapter.Vendor_Redeemed_Coupon_Adapter;
import com.wiredave.uvite.bean.Vendor_Active_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Expired_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Redeemed_Coupon_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;
import com.wiredave.uvite.vendor.Vendor_Active_Coupon_Fragment;
import com.wiredave.uvite.vendor.Vendor_Coupon_Listing;
import com.wiredave.uvite.vendor.Vendor_Expired_Coupon_Fragment;
import com.wiredave.uvite.vendor.Vendor_Redeemed_Coupon_Fragment;

public class Get_Vendor_All_Coupon_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",status = "";
	String TAG="Vendor_All_Coupon_Task";
	
	/**
	 * @author Jay Patel
	 * Called this for Vendor All Coupon.
	 */
	
    public Get_Vendor_All_Coupon_Task (Context context,String status){
         this.mContext = context;
         this.status = status;
         
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
		 if(status.equals("active"))
		 {
			 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorActiveCoupon);	 
		 }else if(status.equals("expired"))
		 {
			 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorExpiredCoupon);
		 }else if(status.equals("redeemed"))
		 {
			 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorRedeemedCoupon);
		 }
		    		   		    	
   		 MultipartEntity mpEntity = new MultipartEntity();
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));
		 
		 Log.v("user_id", Common.ref_database.getUserID());
		 Log.v("user_token", Common.ref_database.getUserToken());
		}else {
			
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     
		}
		 mpEntity.addPart("offset", new StringBody("0"));
		 
   		 httppost.setEntity(mpEntity);
		 HttpResponse response = httpClient.execute(httppost);
   		 HttpEntity responseEntity = response.getEntity();
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
   		 
			if (status.equals("active")) {
				Log.d(TAG, "Vendor_All_Coupon_Task result " + result);
			} else if (status.equals("expired")) {
				Log.d(TAG, "VENDOR_EXPIRED_RESULT_TASK result " + result);
			} else if (status.equals("redeemed")) {
				Log.d(TAG, "VENDOR_REDEEMED_RESULT_TASK result " + result);
			}
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Vendor_All_Coupon_Task "+e.toString());
	   
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
		  
		     Common.total_coupon = "0";
		     Common.total_referred = "0";
		     Common.total_redeemed = "0";
		     
				try {
					
					JSONObject jobj_status=new JSONObject(result);
					
					if(jobj_status.getBoolean("success"))
					{
						if(status.equals("active"))
						{
						//ACTIVE
							CommonUtil.vendor_active_coupon_array.clear();
							
							JSONObject jobj_all_data = jobj_status.getJSONObject("all_data");
							if(jobj_status.has("all_data"))
							{
								Common.total_coupon = jobj_all_data.getString("all_coupon");
							    Common.total_referred = jobj_all_data.getString("total_referred");
							    Common.total_redeemed = jobj_all_data.getString("total_redeemded");
							    
							    Vendor_Active_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
								Vendor_Active_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
								Vendor_Active_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
							}else {
								
								Vendor_Active_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
								Vendor_Active_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
								Vendor_Active_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
							}
							
							JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
							
							if(jarray_response_data.length() > 0)
							{
							  for (int i = 0; i < jarray_response_data.length(); i++) {
								
								JSONObject jobj_activecoupon = jarray_response_data.getJSONObject(i);
								
								CommonUtil.vendor_active_coupon_bean = new Vendor_Active_Coupon_Bean();
								
								CommonUtil.vendor_active_coupon_bean.setCoupon_id(jobj_activecoupon.getString("coupon_id"));
								CommonUtil.vendor_active_coupon_bean.setVendor_id(jobj_activecoupon.getString("vendor_id"));								
								CommonUtil.vendor_active_coupon_bean.setCoupon_title(jobj_activecoupon.getString("coupon_title"));
								CommonUtil.vendor_active_coupon_bean.setCoupon_description(jobj_activecoupon.getString("coupon_description"));
								CommonUtil.vendor_active_coupon_bean.setCoupon_category(jobj_activecoupon.getString("coupon_category"));
								CommonUtil.vendor_active_coupon_bean.setDiscount_type(jobj_activecoupon.getString("discount_type"));
								CommonUtil.vendor_active_coupon_bean.setAvailability(jobj_activecoupon.getString("coupon_subsribe")+"/"+jobj_activecoupon.getString("total_coupon"));
								CommonUtil.vendor_active_coupon_bean.setStart_date(jobj_activecoupon.getString("start_date"));
								CommonUtil.vendor_active_coupon_bean.setEnd_date(jobj_activecoupon.getString("end_date"));
								CommonUtil.vendor_active_coupon_bean.setCommission(jobj_activecoupon.getString("commission"));
								if(jobj_activecoupon.getString("logo") == null || jobj_activecoupon.getString("logo").equals(""))
								{
									CommonUtil.vendor_active_coupon_bean.setLogo("");	
								}else {
									CommonUtil.vendor_active_coupon_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_activecoupon.getString("logo"));	
								}
								//CommonUtil.vendor_active_coupon_bean.setLogo(jobj_activecoupon.getString("logo"));
								CommonUtil.vendor_active_coupon_bean.setTotal_coupon(jobj_activecoupon.getString("total_coupon"));
								CommonUtil.vendor_active_coupon_bean.setTotal_amount(jobj_activecoupon.getString("total_amount"));
								CommonUtil.vendor_active_coupon_bean.setCoupon_code(jobj_activecoupon.getString("coupon_code"));
								
								if(jobj_activecoupon.getString("qrcode_image") == null || jobj_activecoupon.getString("qrcode_image").equals(""))
								{
									CommonUtil.vendor_active_coupon_bean.setQrcode_image("");	
								}else {
									CommonUtil.vendor_active_coupon_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_activecoupon.getString("qrcode_image"));	
								}
								
								//CommonUtil.vendor_active_coupon_bean.setQrcode_image(jobj_activecoupon.getString("qrcode_image"));
								CommonUtil.vendor_active_coupon_bean.setCoupon_referred(jobj_activecoupon.getString("coupon_referred"));
								CommonUtil.vendor_active_coupon_bean.setCoupon_used(jobj_activecoupon.getString("coupon_used"));
								CommonUtil.vendor_active_coupon_bean.setStatus(jobj_activecoupon.getString("status"));
								CommonUtil.vendor_active_coupon_bean.setCreated_date(jobj_activecoupon.getString("created_date"));
								
								CommonUtil.vendor_active_coupon_array.add(CommonUtil.vendor_active_coupon_bean);
								
							}
							
							if(CommonUtil.vendor_active_coupon_array.size() > 0)
							{							 
							 Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter = new Vendor_Active_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_active_coupon_array);
							 Vendor_Active_Coupon_Fragment.list_vendor_active.setAdapter(Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter);
							}
						  }
							
						}else if (status.equals("expired")) {
					      //EXPIRE
								CommonUtil.vendor_expired_coupon_array.clear();
								
								JSONObject jobj_all_data = jobj_status.getJSONObject("all_data");
								if(jobj_status.has("all_data"))
								{
									Common.total_coupon = jobj_all_data.getString("all_coupon");
								    Common.total_referred = jobj_all_data.getString("total_referred");
								    Common.total_redeemed = jobj_all_data.getString("total_redeemded");
								    
								    Vendor_Expired_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
									Vendor_Expired_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
									Vendor_Expired_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
								}else {
									
									Vendor_Expired_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
									Vendor_Expired_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
									Vendor_Expired_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
								}
								
								JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
								
								if(jarray_response_data.length() > 0)
								{
								  for (int i = 0; i < jarray_response_data.length(); i++) {
									
									JSONObject jobj_expiredcoupon = jarray_response_data.getJSONObject(i);
									
									CommonUtil.vendor_expired_coupon_bean = new Vendor_Expired_Coupon_Bean();
									
									CommonUtil.vendor_expired_coupon_bean.setCoupon_id(jobj_expiredcoupon.getString("coupon_id"));
									CommonUtil.vendor_expired_coupon_bean.setVendor_id(jobj_expiredcoupon.getString("vendor_id"));								
									CommonUtil.vendor_expired_coupon_bean.setCoupon_title(jobj_expiredcoupon.getString("coupon_title"));
									CommonUtil.vendor_expired_coupon_bean.setCoupon_description(jobj_expiredcoupon.getString("coupon_description"));
									CommonUtil.vendor_expired_coupon_bean.setCoupon_category(jobj_expiredcoupon.getString("coupon_category"));
									CommonUtil.vendor_expired_coupon_bean.setDiscount_type(jobj_expiredcoupon.getString("discount_type"));									
									CommonUtil.vendor_expired_coupon_bean.setAvailability(jobj_expiredcoupon.getString("coupon_subsribe")+"/"+jobj_expiredcoupon.getString("total_coupon"));
									CommonUtil.vendor_expired_coupon_bean.setStart_date(jobj_expiredcoupon.getString("start_date"));
									CommonUtil.vendor_expired_coupon_bean.setEnd_date(jobj_expiredcoupon.getString("end_date"));
									CommonUtil.vendor_expired_coupon_bean.setCommission(jobj_expiredcoupon.getString("commission"));
									if(jobj_expiredcoupon.getString("logo") == null || jobj_expiredcoupon.getString("logo").equals(""))
									{
										CommonUtil.vendor_expired_coupon_bean.setLogo("");	
									}else {
										CommonUtil.vendor_expired_coupon_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_expiredcoupon.getString("logo"));	
									}
									//CommonUtil.vendor_expired_coupon_bean.setLogo(jobj_expiredcoupon.getString("logo"));
									CommonUtil.vendor_expired_coupon_bean.setTotal_coupon(jobj_expiredcoupon.getString("total_coupon"));
									CommonUtil.vendor_expired_coupon_bean.setTotal_amount(jobj_expiredcoupon.getString("total_amount"));
									CommonUtil.vendor_expired_coupon_bean.setCoupon_code(jobj_expiredcoupon.getString("coupon_code"));
									if(jobj_expiredcoupon.getString("qrcode_image") == null || jobj_expiredcoupon.getString("qrcode_image").equals(""))
									{
										CommonUtil.vendor_expired_coupon_bean.setQrcode_image("");	
									}else {
										CommonUtil.vendor_expired_coupon_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_expiredcoupon.getString("qrcode_image"));	
									}
									//CommonUtil.vendor_expired_coupon_bean.setQrcode_image(jobj_expiredcoupon.getString("qrcode_image"));									
									CommonUtil.vendor_expired_coupon_bean.setCoupon_referred(jobj_expiredcoupon.getString("coupon_referred"));
									CommonUtil.vendor_expired_coupon_bean.setCoupon_used(jobj_expiredcoupon.getString("coupon_used"));
									CommonUtil.vendor_expired_coupon_bean.setStatus(jobj_expiredcoupon.getString("status"));
									CommonUtil.vendor_expired_coupon_bean.setCreated_date(jobj_expiredcoupon.getString("created_date"));
									
									CommonUtil.vendor_expired_coupon_array.add(CommonUtil.vendor_expired_coupon_bean);
									
								}
								
								if(CommonUtil.vendor_expired_coupon_array.size() > 0)
								{																	
									Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Expired_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_expired_coupon_array);
									Vendor_Expired_Coupon_Fragment.list_vendor_expired.setAdapter(Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter);
								}
							  }
								
						}else if (status.equals("redeemed")) {
						 //REDEEMED
							CommonUtil.vendor_redeemed_coupon_array.clear();
							
							JSONObject jobj_all_data = jobj_status.getJSONObject("all_data");
							if(jobj_status.has("all_data"))
							{
								Common.total_coupon = jobj_all_data.getString("all_coupon");
							    Common.total_referred = jobj_all_data.getString("total_referred");
							    Common.total_redeemed = jobj_all_data.getString("total_redeemded");
							    
							    Vendor_Redeemed_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
								Vendor_Redeemed_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
								Vendor_Redeemed_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
							}else {
								
								Vendor_Redeemed_Coupon_Fragment.txt_total_coupon.setText(mContext.getResources().getString(R.string.total_coupon)+" "+Common.total_coupon);	
								Vendor_Redeemed_Coupon_Fragment.txt_total_referred.setText(mContext.getResources().getString(R.string.total_referred)+" "+Common.total_referred);
								Vendor_Redeemed_Coupon_Fragment.txt_total_redeemed.setText(mContext.getResources().getString(R.string.total_redeemed)+" "+Common.total_redeemed);
							}
							
							JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
							
							if(jarray_response_data.length() > 0)
							{
							  for (int i = 0; i < jarray_response_data.length(); i++) {
								
								JSONObject jobj_redeemedcoupon = jarray_response_data.getJSONObject(i);
								
								CommonUtil.vendor_redeemed_coupon_bean = new Vendor_Redeemed_Coupon_Bean();
								
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_id(jobj_redeemedcoupon.getString("coupon_id"));
								CommonUtil.vendor_redeemed_coupon_bean.setVendor_id(jobj_redeemedcoupon.getString("vendor_id"));								
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_title(jobj_redeemedcoupon.getString("coupon_title"));
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_description(jobj_redeemedcoupon.getString("coupon_description"));
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_category(jobj_redeemedcoupon.getString("coupon_category"));
								CommonUtil.vendor_redeemed_coupon_bean.setDiscount_type(jobj_redeemedcoupon.getString("discount_type"));
								CommonUtil.vendor_redeemed_coupon_bean.setAvailability(jobj_redeemedcoupon.getString("coupon_subsribe")+"/"+jobj_redeemedcoupon.getString("total_coupon"));
								CommonUtil.vendor_redeemed_coupon_bean.setStart_date(jobj_redeemedcoupon.getString("start_date"));
								CommonUtil.vendor_redeemed_coupon_bean.setEnd_date(jobj_redeemedcoupon.getString("end_date"));
								CommonUtil.vendor_redeemed_coupon_bean.setCommission(jobj_redeemedcoupon.getString("commission"));
								if(jobj_redeemedcoupon.getString("logo") == null && jobj_redeemedcoupon.getString("logo").equals(""))
								{
									CommonUtil.vendor_expired_coupon_bean.setLogo("");	
								}else {
//									CommonUtil.vendor_expired_coupon_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_redeemedcoupon.getString("logo"));	
								}
								//CommonUtil.vendor_redeemed_coupon_bean.setLogo(jobj_redeemedcoupon.getString("logo"));
								CommonUtil.vendor_redeemed_coupon_bean.setTotal_coupon(jobj_redeemedcoupon.getString("total_coupon"));
								CommonUtil.vendor_redeemed_coupon_bean.setTotal_amount(jobj_redeemedcoupon.getString("total_amount"));
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_code(jobj_redeemedcoupon.getString("coupon_code"));
								if(jobj_redeemedcoupon.getString("qrcode_image") == null || jobj_redeemedcoupon.getString("qrcode_image").equals(""))
								{
									CommonUtil.vendor_redeemed_coupon_bean.setQrcode_image("");	
								}else {
									CommonUtil.vendor_redeemed_coupon_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_redeemedcoupon.getString("qrcode_image"));	
								}
								//CommonUtil.vendor_redeemed_coupon_bean.setQrcode_image(jobj_redeemedcoupon.getString("qrcode_image"));
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_referred(jobj_redeemedcoupon.getString("coupon_referred"));
								CommonUtil.vendor_redeemed_coupon_bean.setCoupon_used(jobj_redeemedcoupon.getString("coupon_used"));
								CommonUtil.vendor_redeemed_coupon_bean.setStatus(jobj_redeemedcoupon.getString("status"));
								CommonUtil.vendor_redeemed_coupon_bean.setCreated_date(jobj_redeemedcoupon.getString("created_date"));
								
								CommonUtil.vendor_redeemed_coupon_array.add(CommonUtil.vendor_redeemed_coupon_bean);
								
							}
							
							
							if(CommonUtil.vendor_redeemed_coupon_array.size() > 0)
							{							 
							 Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Redeemed_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_redeemed_coupon_array);
							 Vendor_Redeemed_Coupon_Fragment.list_vendor_redeemed.setAdapter(Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter);
							}
							
						 }
						}
												
					}else if (!jobj_status.getBoolean("success")) {
						
						if(status.equals("active"))
						{
							CommonUtil.vendor_active_coupon_array.clear();
							
							Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter = new Vendor_Active_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_active_coupon_array);
							Vendor_Active_Coupon_Fragment.list_vendor_active.setAdapter(Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter);
						}else if (status.equals("expired")) {
							
							CommonUtil.vendor_expired_coupon_array.clear();
							
							Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Expired_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_expired_coupon_array);
							Vendor_Expired_Coupon_Fragment.list_vendor_expired.setAdapter(Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter);
						}else if (status.equals("redeemed")) {
							
							CommonUtil.vendor_redeemed_coupon_array.clear();
							
							Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Redeemed_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_redeemed_coupon_array);
							Vendor_Redeemed_Coupon_Fragment.list_vendor_redeemed.setAdapter(Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter);
						}
						
						Common.showalertDialog(mContext, mContext.getResources().getString(R.string.data_not_found));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					if(status.equals("active"))
					{
						CommonUtil.vendor_active_coupon_array.clear();
						
						Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter = new Vendor_Active_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_active_coupon_array);
						Vendor_Active_Coupon_Fragment.list_vendor_active.setAdapter(Vendor_Active_Coupon_Fragment.vendor_active_coupon_adapter);
					}else if (status.equals("expired")) {
						
						CommonUtil.vendor_expired_coupon_array.clear();
						
						Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Expired_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_expired_coupon_array);
						Vendor_Expired_Coupon_Fragment.list_vendor_expired.setAdapter(Vendor_Expired_Coupon_Fragment.vendor_expired_coupon_adapter);
					}else if (status.equals("redeemed")) {
						
						CommonUtil.vendor_redeemed_coupon_array.clear();
						
						Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter = new Vendor_Redeemed_Coupon_Adapter(mContext, R.layout.custom_vendorcoupon_list, CommonUtil.vendor_redeemed_coupon_array);
						Vendor_Redeemed_Coupon_Fragment.list_vendor_redeemed.setAdapter(Vendor_Redeemed_Coupon_Fragment.vendor_expired_coupon_adapter);
					}
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
					
				}	
		   
		  
	  }
		 }
