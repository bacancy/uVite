package com.wiredave.uvite.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;

import com.wiredave.uvite.bean.Promoter_AllActive_Coupon_Bean;
import com.wiredave.uvite.bean.Promoter_Login_Bean;
import com.wiredave.uvite.bean.Promoter_MyCoupon_Bean;
import com.wiredave.uvite.bean.Promoter_Referred_Bean;
import com.wiredave.uvite.bean.Promotor_Favorite_Bean;
import com.wiredave.uvite.bean.Vendor_Active_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Create_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Expired_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Login_Bean;
import com.wiredave.uvite.bean.Vendor_Redeemed_Coupon_Bean;
import com.wiredave.uvite.bean.Vendor_Scan_Coupan_Bean;

public class CommonUtil {
	 
	
	 public static String URL = "http://www.bacancytechnology.com/btdemo/referral/api/index.php/api/";
	 
	 public static String API_LOGIN_ID = "8FLjeT29qzNm";
	 public static String TRANSCATION_KEY = "6yT3Vk8mQ56Ke7LC";
			 
	 public static String AUTHORIZENET_URL = "https://apitest.authorize.net/xml/v1/request.api";
	 
	 /* Sandbox URL: https://apitest.authorize.net/xml/v1/request.api
        Production URL: https://api.authorize.net/xml/v1/request.api   */
	 
	 public static String VENDOR_IMAGE_URL = "http://www.bacancytechnology.com/btdemo/referral/uploads/vendor_logo/";
	 public static String PROMOTER_IMAGE_URL = "http://www.bacancytechnology.com/btdemo/referral/uploads/promoter_logo/";
	 public static String LOGO_IMAGE_URL = "http://www.bacancytechnology.com/btdemo/referral/uploads/coupon_logo/";
	 public static String QRCODE_IMAGE_URL = "http://www.bacancytechnology.com/btdemo/referral/uploads/vendor_coupon_qrcode/";	 	 
	 
	 
	 
	 public static String Method_VendorLogin = "vendor_login";
     public static String Method_PromoterLogin = "promoter_login";
     public static String Method_ForgotPassword = "forget_password";
	 public static String Method_GetVendorSignUp = "get_vendor_profile_detail";
     public static String Method_VendorSignUp = "vendor_signup";
     public static String Method_UpdateVendorSignUp = "vendor_profile_edit";
     public static String Method_GetPromoterSignUp = "get_promoter_profile_detail";
     public static String Method_PromoterSignUp = "promoter_signup";     
     public static String Method_UpdatePromoterSignUp = "promoter_profile_edit";
     public static String Method_Logout = "logout";
     public static String Method_VendorCreateCoupon = "vendor_create_coupon";
     public static String Method_VendorEditCoupon = "vendor_coupon_edit";
     public static String Method_VendorDeleteCoupon = "vendor_coupon_delete";     
     public static String Method_VendorActiveCoupon = "vendor_active_coupon_list";
     public static String Method_VendorExpiredCoupon = "vendor_expired_coupon_list";
     public static String Method_VendorRedeemedCoupon = "vendor_redeem_coupon_list";//JS
     public static String Method_VendorRedeemedCouponAccept = "vendor_redeem_coupon";//JS
     public static String Method_VendorScanCoupon = "vendor_scan_coupon"; //JS
     public static String Method_RedeemCoupon = "vendor_redeem_coupon"; //JS
     public static String Method_PromoterAllActiveCoupon = "promoter_active_coupon_list";
     public static String Method_PromoterReferCouponList = "promoter_refered_coupon_list";
     public static String Method_FavoriteCouponList = "promoter_favorite_coupon_list";
     public static String Method_PromoterMyCouponList = "promoter_my_coupon_list";//JS
     public static String Method_PromoterReferCoupon = "promoter_refer_coupon";//JS
     public static String Method_PromoterFavoriteCoupon = "promoter_coupon_favorite";//JS
     public static String Method_PromoterSubscribeCoupon = "promoter_subscribe_coupon"; //JS    
     
     public static Vendor_Login_Bean vendor_login_bean;
     public static Promoter_Login_Bean promoter_login_bean;
     
     public static Vendor_Create_Coupon_Bean vendor_create_coupon_bean;
     public static Vendor_Active_Coupon_Bean vendor_active_coupon_bean;
     public static Vendor_Expired_Coupon_Bean vendor_expired_coupon_bean;
     public static Vendor_Redeemed_Coupon_Bean vendor_redeemed_coupon_bean;
     public static Vendor_Scan_Coupan_Bean vendor_scan_coupan_bean;
//     public static Vendor_Redeem_Coupon_Bean vendor_redeem_coupon_bean;
     
     public static Promoter_AllActive_Coupon_Bean promoter_allactive_coupon_bean;
     public static Promoter_MyCoupon_Bean promoter_mycoupon_bean;
     public static Promoter_Referred_Bean promoter_referred_bean;
     public static Promotor_Favorite_Bean promoter_favorite_bean;
    

     
     public static ArrayList<Vendor_Login_Bean> vendor_login_array = new ArrayList<Vendor_Login_Bean>();
     public static ArrayList<Promoter_Login_Bean> promoter_login_array = new ArrayList<Promoter_Login_Bean>();
     public static ArrayList<Vendor_Create_Coupon_Bean> vendor_create_coupon_array = new ArrayList<Vendor_Create_Coupon_Bean>();     
     public static ArrayList<Vendor_Active_Coupon_Bean> vendor_active_coupon_array = new ArrayList<Vendor_Active_Coupon_Bean>();
     public static ArrayList<Vendor_Expired_Coupon_Bean> vendor_expired_coupon_array = new ArrayList<Vendor_Expired_Coupon_Bean>();
     public static ArrayList<Vendor_Redeemed_Coupon_Bean> vendor_redeemed_coupon_array = new ArrayList<Vendor_Redeemed_Coupon_Bean>();
     public static ArrayList<Promoter_AllActive_Coupon_Bean> promoter_allactive_coupon_array = new ArrayList<Promoter_AllActive_Coupon_Bean>();
     public static ArrayList<Promoter_MyCoupon_Bean> promoter_mycoupon_array = new ArrayList<Promoter_MyCoupon_Bean>();
     public static ArrayList<Promoter_Referred_Bean> promoter_referred_coupon_array = new ArrayList<Promoter_Referred_Bean>();
     public static ArrayList<Promotor_Favorite_Bean> promoter_favorite_coupon_array = new ArrayList<Promotor_Favorite_Bean>();
     public static ArrayList<Vendor_Scan_Coupan_Bean> vendor_scan_coupan_array = new ArrayList<Vendor_Scan_Coupan_Bean>();

     public static String convertInputStreamToString(InputStream inputStream) throws IOException{
 		String line = "";
 		String result = "";
 		try{
 			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
 			while((line = bufferedReader.readLine()) != null)
 				result += line;
 			inputStream.close();
 		}
 		catch(Exception e)
 		{
 			Log.e("Error","convertInputStreamToString "+e.toString());
 		}
 		return result;
 	}
 	
}
