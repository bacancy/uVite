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
import com.wiredave.uvite.adapter.Promoter_AllActive_Coupon_Adapter;
import com.wiredave.uvite.bean.Promoter_AllActive_Coupon_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;

public class Promoter_AllActive_Coupon_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",orderby = "";
	String TAG="Promoter_AllActive_Coupon_Task";
	
	/**
	 * @author Jay Patel
	 * Called this for Promoter AllActive Coupon.
	 */
	
    public Promoter_AllActive_Coupon_Task(Context context,String orderby){
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
		 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_PromoterAllActiveCoupon);
		    		   		    	
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
		 
   		Log.d(TAG,"Promoter_AllActive_Coupon_Task result "+result);
		 	    
	   } catch (Exception e) {
	    Log.d(TAG,"Promoter_AllActive_Coupon_Task "+e.toString());
	   
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
					 //ALLACTIVE
					  CommonUtil.promoter_allactive_coupon_array.clear();							
//						Common.showalertDialog(mContext,result);
						JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
	
							if(jarray_response_data.length() > 0)
							{
							  for (int i = 0; i < jarray_response_data.length(); i++) {
								
								JSONObject jobj_activecoupon = jarray_response_data.getJSONObject(i);
								
								CommonUtil.promoter_allactive_coupon_bean = new Promoter_AllActive_Coupon_Bean();
								
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_id(jobj_activecoupon.getString("coupon_id"));
								CommonUtil.promoter_allactive_coupon_bean.setVendor_id(jobj_activecoupon.getString("vendor_id"));								
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_title(jobj_activecoupon.getString("coupon_title"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_description(jobj_activecoupon.getString("coupon_description"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_category(jobj_activecoupon.getString("coupon_category"));
								CommonUtil.promoter_allactive_coupon_bean.setDiscount_type(jobj_activecoupon.getString("discount_type"));
								CommonUtil.promoter_allactive_coupon_bean.setAvailability(jobj_activecoupon.getString("coupon_subsribe")+"/"+jobj_activecoupon.getString("total_coupon"));
								CommonUtil.promoter_allactive_coupon_bean.setStart_date(jobj_activecoupon.getString("start_date"));
								CommonUtil.promoter_allactive_coupon_bean.setEnd_date(jobj_activecoupon.getString("end_date"));
								CommonUtil.promoter_allactive_coupon_bean.setCommission(jobj_activecoupon.getString("commission"));
								if(jobj_activecoupon.getString("logo") == null || jobj_activecoupon.getString("logo").equals(""))
								{
									CommonUtil.promoter_allactive_coupon_bean.setLogo("");	
								}else {
									CommonUtil.promoter_allactive_coupon_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_activecoupon.getString("logo"));	
								}
								
								CommonUtil.promoter_allactive_coupon_bean.setTotal_coupon(jobj_activecoupon.getString("total_coupon"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_used(jobj_activecoupon.getString("coupon_used"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_referred(jobj_activecoupon.getString("coupon_referred"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_subsribe(jobj_activecoupon.getString("coupon_subsribe"));
								CommonUtil.promoter_allactive_coupon_bean.setTotal_amount(jobj_activecoupon.getString("total_amount"));
								CommonUtil.promoter_allactive_coupon_bean.setCoupon_code(jobj_activecoupon.getString("coupon_code"));
								
								if(jobj_activecoupon.getString("qrcode_image") == null || jobj_activecoupon.getString("qrcode_image").equals(""))
								{
									CommonUtil.promoter_allactive_coupon_bean.setQrcode_image("");	
								}else {
									CommonUtil.promoter_allactive_coupon_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_activecoupon.getString("qrcode_image"));	
								}
								
								CommonUtil.promoter_allactive_coupon_bean.setDelete_status(jobj_activecoupon.getString("delete_status"));
								CommonUtil.promoter_allactive_coupon_bean.setStatus(jobj_activecoupon.getString("favorite_status"));
//								CommonUtil.promoter_allactive_coupon_bean.setCreated_date(jobj_activecoupon.getString("created_date"));
								
								CommonUtil.promoter_allactive_coupon_array.add(CommonUtil.promoter_allactive_coupon_bean);
//								Common.showalertDialog(mContext,""+CommonUtil.promoter_referred_coupon_array.toString());

							}
							
							if(CommonUtil.promoter_allactive_coupon_array.size() > 0)
							{							 
								Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter = new Promoter_AllActive_Coupon_Adapter(mContext, R.layout.custom_promoter_allactivecoupons, CommonUtil.promoter_allactive_coupon_array);
								Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter);
							}
						  }
												
					}else if (!jobj_status.getBoolean("success")) {
						
						CommonUtil.promoter_allactive_coupon_array.clear();
						
						Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter = new Promoter_AllActive_Coupon_Adapter(mContext, R.layout.custom_promoter_allactivecoupons, CommonUtil.promoter_allactive_coupon_array);
						Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter);
						
						Common.showalertDialog(mContext, mContext.getResources().getString(R.string.data_not_found));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					CommonUtil.promoter_allactive_coupon_array.clear();
					
					Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter = new Promoter_AllActive_Coupon_Adapter(mContext, R.layout.custom_promoter_allactivecoupons, CommonUtil.promoter_allactive_coupon_array);
					Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_allactive_coupon_adapter);
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));

				}	
		   
		  
	  }
		 }
