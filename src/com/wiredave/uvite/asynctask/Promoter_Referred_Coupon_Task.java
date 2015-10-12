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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiredave.uvite.R;
import com.wiredave.uvite.adapter.Promoter_Referred_Adapter;
import com.wiredave.uvite.bean.Promoter_Referred_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;

public class Promoter_Referred_Coupon_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",orderby = "";
	String TAG="Promoter_Referred_Coupon_Task";
	
	/**
	 * @author Jay Patel
	 * Called this for Promoter Referred Coupon.
	 */
	
    public Promoter_Referred_Coupon_Task (Context context,String orderby){
         this.mContext = context;
         this.orderby = orderby;
         
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
		 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_PromoterReferCouponList);
		    		   		    	
   		 MultipartEntity mpEntity = new MultipartEntity();
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));		 
		}else {
			
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     
		}
   		
		 mpEntity.addPart("offset", new StringBody("0"));
		 mpEntity.addPart("orderby", new StringBody(orderby));
		 
   		 httppost.setEntity(mpEntity);
		 HttpResponse response = httpClient.execute(httppost);
   		 HttpEntity responseEntity = response.getEntity();
   		 if(responseEntity != null)
   		   result = EntityUtils.toString(responseEntity);		    		  		    		    
   		 else
   		   result = "Error";
		 
   		Log.d(TAG,"Promoter_Referred_Coupon_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Promoter_Referred_Coupon_Task "+e.toString());
	   
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
						 
					 //REFERRED COUPON
						 CommonUtil.promoter_referred_coupon_array.clear();							
							
						  JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
								
								if(jarray_response_data.length() > 0)
								{
								  for (int i = 0; i < jarray_response_data.length(); i++) {
									
									JSONObject jobj_activecoupon = jarray_response_data.getJSONObject(i);
									
									CommonUtil.promoter_referred_bean = new Promoter_Referred_Bean();
									
									CommonUtil.promoter_referred_bean.setPromoter_id(jobj_activecoupon.getString("promoter_id"));
									CommonUtil.promoter_referred_bean.setCoupon_id(jobj_activecoupon.getString("coupon_id"));
									CommonUtil.promoter_referred_bean.setVendor_id(jobj_activecoupon.getString("vendor_id"));								
									CommonUtil.promoter_referred_bean.setCoupon_title(jobj_activecoupon.getString("coupon_title"));
									CommonUtil.promoter_referred_bean.setCoupon_description(jobj_activecoupon.getString("coupon_description"));
									CommonUtil.promoter_referred_bean.setCoupon_category(jobj_activecoupon.getString("coupon_category"));
									CommonUtil.promoter_referred_bean.setDiscount_type(jobj_activecoupon.getString("discount_type"));
									CommonUtil.promoter_referred_bean.setAvailability(jobj_activecoupon.getString("coupon_subsribe")+"/"+jobj_activecoupon.getString("total_coupon"));
									CommonUtil.promoter_referred_bean.setStart_date(jobj_activecoupon.getString("start_date"));
									CommonUtil.promoter_referred_bean.setEnd_date(jobj_activecoupon.getString("end_date"));
									CommonUtil.promoter_referred_bean.setCommission(jobj_activecoupon.getString("commission"));
									if(jobj_activecoupon.getString("logo") == null || jobj_activecoupon.getString("logo").equals(""))
									{
										CommonUtil.promoter_referred_bean.setLogo("");	
									}else {
										CommonUtil.promoter_referred_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_activecoupon.getString("logo"));	
									}
									
									CommonUtil.promoter_referred_bean.setTotal_coupon(jobj_activecoupon.getString("total_coupon"));
									CommonUtil.promoter_referred_bean.setCoupon_used(jobj_activecoupon.getString("coupon_used"));
									CommonUtil.promoter_referred_bean.setCoupon_referred(jobj_activecoupon.getString("coupon_referred"));
									CommonUtil.promoter_referred_bean.setCoupon_subsribe(jobj_activecoupon.getString("coupon_subsribe"));
									CommonUtil.promoter_referred_bean.setTotal_amount(jobj_activecoupon.getString("total_amount"));
									CommonUtil.promoter_referred_bean.setCoupon_code(jobj_activecoupon.getString("coupon_code"));
									
									if(jobj_activecoupon.getString("qrcode_image") == null || jobj_activecoupon.getString("qrcode_image").equals(""))
									{
										CommonUtil.promoter_referred_bean.setQrcode_image("");	
									}else {
										CommonUtil.promoter_referred_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_activecoupon.getString("qrcode_image"));	
									}
									
									CommonUtil.promoter_referred_bean.setDelete_status(jobj_activecoupon.getString("delete_status"));
									CommonUtil.promoter_referred_bean.setStatus(jobj_activecoupon.getString("status"));
									CommonUtil.promoter_referred_bean.setCreated_date(jobj_activecoupon.getString("created_date"));
									
									CommonUtil.promoter_referred_coupon_array.add(CommonUtil.promoter_referred_bean);

								}
								
								
							  }
					
							if(CommonUtil.promoter_referred_coupon_array.size() > 0)
							{							 
								Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter = new Promoter_Referred_Adapter(mContext, R.layout.custom_promoter_referredcoupons, CommonUtil.promoter_referred_coupon_array);
								Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter);							
							}
						  
												
					}else if (!jobj_status.getBoolean("success")) {
						
						CommonUtil.promoter_referred_coupon_array.clear();
						
						Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter = new Promoter_Referred_Adapter(mContext, R.layout.custom_promoter_referredcoupons, CommonUtil.promoter_referred_coupon_array);
						Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter);
						
						Common.showalertDialog(mContext, mContext.getResources().getString(R.string.data_not_found));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					CommonUtil.promoter_referred_coupon_array.clear();
					
					Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter = new Promoter_Referred_Adapter(mContext, R.layout.custom_promoter_referredcoupons, CommonUtil.promoter_referred_coupon_array);
					Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_referred_coupon_adapter);
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
					
				}	

		  
	  }
		 }
