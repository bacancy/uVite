package com.wiredave.uvite.asynctask;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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
import com.wiredave.uvite.registration.Vendor_Registration;
import com.wiredave.uvite.vendor.Vendor_Make_Coupon_Payment;
import com.wiredave.uvite.vendor.Vendor_Create_Coupon;
import com.wiredave.uvite.vendor.Vendor_Payment;

public class AuthorizeNet_CreditCard_Task extends AsyncTask<String, Void, String> {

	private Context mContext;
	ProgressDialog pd;
	String TAG = "AuthorizeNet_CreditCard",status = "";
	String json_authorizecreditcard = "" ,amount = "",cardNumber = "",expirationDate = "" ,cardCode = "";

	public AuthorizeNet_CreditCard_Task(Context context,String status ,String cardnumber , String expirationdate , String cardcode , String amount) {
		this.mContext = context;
		this.status = status;
		this.cardNumber = cardnumber;
		this.expirationDate = expirationdate;
		this.cardCode = cardcode;
		this.amount = amount;
	}

	@Override
	protected void onPreExecute() {

		pd = new ProgressDialog(mContext);
		pd.setMessage(mContext.getResources().getString(R.string.please_wait_transcation));
		pd.show();
	}

	@Override
	protected String doInBackground(String... urls) {

		InputStream inputStream = null;
		String result = "";
		Common.payment_array = "";

		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(CommonUtil.AUTHORIZENET_URL);
			String json_authorizecreditcard = "";
			
			json_authorizecreditcard =  "{" 
			  + "  \"createTransactionRequest\": {" 
			  + "  \"merchantAuthentication\":  {" 
			  + "      \"name\": \""
			  +CommonUtil.API_LOGIN_ID+"\","
			  + "      \"transactionKey\": \""
			  +CommonUtil.TRANSCATION_KEY+"\","
			  + "    },"
			  + "      \"refId\": \"123456\","
			  + "  \"transactionRequest\":  {"
			  + "      \"transactionType\": \"authOnlyTransaction\","
			  + "      \"amount\": \""
			  +amount+"\","
			  + "  \"payment\":  {"
			  + "  \"creditCard\":  {"
			  + "      \"cardNumber\": \""
			  +cardNumber+"\","
			  + "      \"expirationDate\":\""
			  +expirationDate+"\","
			  + "      \"cardCode\":\""
			  +cardCode+"\","
			         + "  }"
			        + "  }"
			      +  "  }"
			    + "  }"
			  + "  }" ;
				
			Log.d(TAG, "AuthorizeNet_CreditCard JSON " + json_authorizecreditcard);		
			StringEntity se = new StringEntity(json_authorizecreditcard);
			httpPost.setEntity(se);
			httpPost.setHeader("Content-Type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = CommonUtil.convertInputStreamToString(inputStream);
			else
				result = "Error";
			Log.d(TAG, "AuthorizeNet_CreditCard result " + result);
		} catch (Exception e) {
			Log.d(TAG, "AuthorizeNet_CreditCard " + e.toString());

			return "Error";
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
			
			try {
			if(result != null && !result.equals(""))
			{
			   Common.payment_array = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1).trim() ;
			   
			   JSONObject json_result = new JSONObject(result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1));
			   
			   JSONObject json_messages = json_result.getJSONObject("messages");
					
			   if(json_messages.getString("resultCode").equals("Ok"))
				{				   
				   if(status.equals("create_coupon"))
				   {
					 //Common.showalertDialog(mContext, mContext.getResources().getString(R.string.toast_payment_success));
					   
//					 Toast toast = Toast.makeText(mContext,mContext.getResources().getString(R.string.toast_payment_success) ,Toast.LENGTH_SHORT);
//					 toast.setGravity(Gravity.CENTER, 0, 0);
//					 toast.show();
					 
				     //replace fragment...				   
				     //Vendor_Payment.Replace_Authorized_Payment_Fragment();
					 
					 ((Activity)mContext).startActivityForResult(new Intent(mContext,Vendor_Make_Coupon_Payment.class),5);
					 ((Activity)mContext).overridePendingTransition(0, 0);
					 
					 /*//check internet connetivity...
					  if(Common.isConnectingToInternet(mContext))
					   {
						   //call this for creating vendor coupon...
						   new Create_Vendor_Coupon_Task(mContext,"add").execute();
						  
						}else {		
							
						  Common.showalertDialog(mContext, mContext.getResources().getString(R.string.alert_internetconnectivity));
						}*/
					
				   
				   }else if (status.equals("registration")) {
					   
					 //check internet connetivity...
						if(Common.isConnectingToInternet(mContext))
						{
						  //call vendor sign up api...
						  new Vendor_SignUp_Task(mContext,Vendor_Registration.flag).execute();
						  
						}else {		
							
						  Common.showalertDialog(mContext, mContext.getResources().getString(R.string.alert_internetconnectivity));
						}
				}
					
				}else if(json_messages.getString("resultCode").equals("Error")){
						
					JSONArray jsonarray_unsuccess = json_messages.getJSONArray("message");
					JSONObject jsonobj_unsuccess = jsonarray_unsuccess.getJSONObject(0);
					
					Common.showalertDialog(mContext, jsonobj_unsuccess.getString("text"));
				}							
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Common.payment_array = "";
				Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
			}
		}

	}

	
}
