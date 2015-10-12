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
import com.wiredave.uvite.adapter.Promoter_Favorites_Adapter;
import com.wiredave.uvite.bean.Promotor_Favorite_Bean;
import com.wiredave.uvite.bean.Vendor_Scan_Coupan_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_AllCoupon_Fragment;

public class Vendor_Scan_Coupon_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "",emailid = "",coupon_data = "";
	String TAG="Scan_Coupon_Task";
	
	/**
	 * @author Jaymin Suchak
	 * Called this for Vendor Scan Coupon.
	 */
	
    public Vendor_Scan_Coupon_Task(Context context,String coupon_data){
    	
         this.mContext = context;         
         this.coupon_data = coupon_data;
         
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
		 httppost = new HttpPost(CommonUtil.URL+CommonUtil.Method_VendorScanCoupon);
		    		   		    	
   		 MultipartEntity mpEntity = new MultipartEntity();
   		 
   		if(Common.ref_database.checkIfExist())
		{	
		 mpEntity.addPart("user_id", new StringBody(Common.ref_database.getUserID()));
		 mpEntity.addPart("user_token", new StringBody(Common.ref_database.getUserToken()));		 
		}else {
			
	     mpEntity.addPart("user_id", new StringBody(""));
	     mpEntity.addPart("user_token", new StringBody(""));
	     
		}
   		
		 mpEntity.addPart("coupon_data", new StringBody(coupon_data));
		 
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
						Common.showalertDialog(mContext, jobj_status.getString("message"));

//					 //Favorite COUPON
						 CommonUtil.vendor_scan_coupan_array.clear();							
							
						  JSONArray jarray_response_data = jobj_status.getJSONArray("response_data");
								
								if(jarray_response_data.length() > 0)
								{
								  for (int i = 0; i < jarray_response_data.length(); i++) {
									
									JSONObject jobj_activecoupon = jarray_response_data.getJSONObject(i);
									
									CommonUtil.vendor_scan_coupan_bean = new Vendor_Scan_Coupan_Bean();
									
									CommonUtil.vendor_scan_coupan_bean.setPromoter_id(jobj_activecoupon.getString("promoter_id"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_id(jobj_activecoupon.getString("coupon_id"));
									CommonUtil.vendor_scan_coupan_bean.setVendor_id(jobj_activecoupon.getString("vendor_id"));								
									CommonUtil.vendor_scan_coupan_bean.setCoupon_title(jobj_activecoupon.getString("coupon_title"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_description(jobj_activecoupon.getString("coupon_description"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_category(jobj_activecoupon.getString("coupon_category"));
									CommonUtil.vendor_scan_coupan_bean.setDiscount_type(jobj_activecoupon.getString("discount_type"));
									CommonUtil.vendor_scan_coupan_bean.setAvailability(jobj_activecoupon.getString("coupon_subsribe")+"/"+jobj_activecoupon.getString("total_coupon"));
									CommonUtil.vendor_scan_coupan_bean.setStart_date(jobj_activecoupon.getString("start_date"));
									CommonUtil.vendor_scan_coupan_bean.setEnd_date(jobj_activecoupon.getString("end_date"));
									CommonUtil.vendor_scan_coupan_bean.setCommission(jobj_activecoupon.getString("commission"));
									if(jobj_activecoupon.getString("logo") == null || jobj_activecoupon.getString("logo").equals(""))
									{
										CommonUtil.vendor_scan_coupan_bean.setLogo("");	
									}else {
										CommonUtil.vendor_scan_coupan_bean.setLogo(CommonUtil.LOGO_IMAGE_URL+jobj_activecoupon.getString("logo"));	
									}
									
									CommonUtil.vendor_scan_coupan_bean.setTotal_coupon(jobj_activecoupon.getString("total_coupon"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_used(jobj_activecoupon.getString("coupon_used"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_referred(jobj_activecoupon.getString("coupon_referred"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_subsribe(jobj_activecoupon.getString("coupon_subsribe"));
									CommonUtil.vendor_scan_coupan_bean.setTotal_amount(jobj_activecoupon.getString("total_amount"));
									CommonUtil.vendor_scan_coupan_bean.setCoupon_code(jobj_activecoupon.getString("coupon_code"));
									
									if(jobj_activecoupon.getString("qrcode_image") == null || jobj_activecoupon.getString("qrcode_image").equals(""))
									{
										CommonUtil.vendor_scan_coupan_bean.setQrcode_image("");	
									}else {
										CommonUtil.vendor_scan_coupan_bean.setQrcode_image(CommonUtil.QRCODE_IMAGE_URL+jobj_activecoupon.getString("qrcode_image"));	
									}
									
									CommonUtil.vendor_scan_coupan_bean.setDelete_status(jobj_activecoupon.getString("delete_status"));
									CommonUtil.vendor_scan_coupan_bean.setStatus(jobj_activecoupon.getString("status"));
									CommonUtil.vendor_scan_coupan_bean.setCreated_date(jobj_activecoupon.getString("created_date"));
									
									CommonUtil.vendor_scan_coupan_array.add(CommonUtil.vendor_scan_coupan_bean);

								}
								
								
							  }
					
//							if(CommonUtil.vendor_scan_coupan_array.size() > 0)
//							{							 
//								Promoter_AllCoupon_Fragment.promoter_favorites_adapter = new Promoter_Favorites_Adapter(mContext, R.layout.custom_promoter_favorites, CommonUtil.promoter_favorite_coupon_array);
//								Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_favorites_adapter);							
//							}
						  
												
					}else if (!jobj_status.getBoolean("success")) {
						
						CommonUtil.vendor_scan_coupan_array.clear();
						
//						Promoter_AllCoupon_Fragment.promoter_favorites_adapter = new Promoter_Favorites_Adapter(mContext, R.layout.custom_promoter_favorites, CommonUtil.promoter_favorite_coupon_array);
//						Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_favorites_adapter);							
						
						Common.showalertDialog(mContext, jobj_status.getString("message"));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					CommonUtil.vendor_scan_coupan_array.clear();
					
//					Promoter_AllCoupon_Fragment.promoter_favorites_adapter = new Promoter_Favorites_Adapter(mContext, R.layout.custom_promoter_favorites, CommonUtil.promoter_favorite_coupon_array);
//					Promoter_AllCoupon_Fragment.list_allcoupons.setAdapter(Promoter_AllCoupon_Fragment.promoter_favorites_adapter);
//					
					Common.showalertDialog(mContext, e.getMessage());					
				}	

		  
	  }
}
