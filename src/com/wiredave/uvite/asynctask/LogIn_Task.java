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
import com.wiredave.uvite.bean.Promoter_Login_Bean;
import com.wiredave.uvite.bean.Vendor_Login_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.home.Home_Screen;
import com.wiredave.uvite.login.Login_Screen;

public class LogIn_Task extends AsyncTask<String, Void, String> {

	private Context mContext;
	ProgressDialog pd;
	String result = "", status = "", emailid = "", password = "";
	String TAG = "SignIn_Task";

	/**
	 * @author Jay Patel Called this to Log In.
	 */

	public LogIn_Task(Context context, String status, String emailid,
			String password) {
		this.mContext = context;
		this.status = status;
		this.emailid = emailid;
		this.password = password;

		if (Common.ref_database == null)
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

			Common.device_token = Common.getDeviceUniqueID(mContext);
			// 1. create HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			// 2. make POST request to the given URL
			if (status.equals("Vendor")) {
				httppost = new HttpPost(CommonUtil.URL
						+ CommonUtil.Method_VendorLogin);

			} else if (status.equals("Promoter")) {

				httppost = new HttpPost(CommonUtil.URL
						+ CommonUtil.Method_PromoterLogin);
			}

			MultipartEntity mpEntity = new MultipartEntity();

			mpEntity.addPart("device_type", new StringBody(Common.device_type));
			mpEntity.addPart("device_token",
					new StringBody(Common.device_token));
			mpEntity.addPart("emailid", new StringBody(emailid));
			mpEntity.addPart("password", new StringBody(password));

			httppost.setEntity(mpEntity);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null)
				result = EntityUtils.toString(responseEntity);
			else
				result = "Error";

			Log.d(TAG, "SignIn_Task result " + result);

		} catch (Exception e) {
			Log.d(TAG, "SignIn_Task " + e.toString());

			return "Error";
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		try {
			JSONObject jobj_status = new JSONObject(result);
			if (jobj_status.getInt("success") == 1) {
				Toast toast = Toast.makeText(mContext, mContext.getResources()
						.getString(R.string.toast_login_complete),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				if (jobj_status.has("user_details")) {
					// start home screen...
					if (status.equals("Vendor")) {
						CommonUtil.vendor_login_array.clear();

						JSONObject json_vendor_details = jobj_status
								.getJSONObject("user_details");
						CommonUtil.vendor_login_bean = new Vendor_Login_Bean();
						CommonUtil.vendor_login_bean.setId(json_vendor_details
								.getString("id"));
						CommonUtil.vendor_login_bean
								.setDevice_type(json_vendor_details
										.getString("device_type"));
						CommonUtil.vendor_login_bean
								.setDevice_token(json_vendor_details
										.getString("device_token"));
						CommonUtil.vendor_login_bean
								.setFirst_name(json_vendor_details
										.getString("first_name"));
						CommonUtil.vendor_login_bean
								.setLast_name(json_vendor_details
										.getString("last_name"));
						
						
						CommonUtil.vendor_login_bean
								.setEmail_id(json_vendor_details
										.getString("email_id"));
						CommonUtil.vendor_login_bean
								.setPassword(json_vendor_details
										.getString("password"));
						CommonUtil.vendor_login_bean
								.setPhone_number(json_vendor_details
										.getString("phone_number"));
						CommonUtil.vendor_login_bean
								.setContact_address(json_vendor_details
										.getString("contact_address"));
						CommonUtil.vendor_login_bean
								.setUsername(json_vendor_details
										.getString("username"));
						CommonUtil.vendor_login_bean
								.setAddress_1(json_vendor_details
										.getString("address_1"));
						CommonUtil.vendor_login_bean
								.setAddress_2(json_vendor_details
										.getString("address_2"));
						CommonUtil.vendor_login_bean
								.setCountry(json_vendor_details
										.getString("country"));
						CommonUtil.vendor_login_bean
								.setState(json_vendor_details
										.getString("state"));
						CommonUtil.vendor_login_bean
								.setCity(json_vendor_details.getString("city"));
						CommonUtil.vendor_login_bean
								.setPrimary_phone(json_vendor_details
										.getString("primary_phone"));
						CommonUtil.vendor_login_bean.setFax(json_vendor_details
								.getString("fax"));
						CommonUtil.vendor_login_bean
								.setPrimary_email(json_vendor_details
										.getString("primary_email"));
						CommonUtil.vendor_login_bean
								.setPrimary_contact(json_vendor_details
										.getString("primary_contact"));
						CommonUtil.vendor_login_bean
								.setAdditional_contacts(json_vendor_details
										.getString("additional_contacts"));
						CommonUtil.vendor_login_bean
								.setWebsite(json_vendor_details
										.getString("website"));

						if (json_vendor_details.getString("logo") == null
								&& json_vendor_details.getString("logo")
										.equals("")) {
							CommonUtil.vendor_login_bean.setLogo("");
						} else {
							CommonUtil.vendor_login_bean
									.setLogo(CommonUtil.VENDOR_IMAGE_URL
											+ json_vendor_details
													.getString("logo"));
						}

						CommonUtil.vendor_login_bean
								.setStatus(json_vendor_details
										.getString("status"));
						CommonUtil.vendor_login_bean
								.setLogin_status(json_vendor_details
										.getString("login_status"));
						CommonUtil.vendor_login_bean
								.setUser_token(json_vendor_details
										.getString("user_token"));
						CommonUtil.vendor_login_bean
								.setCreated_date(json_vendor_details
										.getString("Created_date"));
						CommonUtil.vendor_login_bean
								.setUpdate_Date(json_vendor_details
										.getString("Update_Date"));
						CommonUtil.vendor_login_bean
								.setCard_number(json_vendor_details
										.getString("card_number"));
						CommonUtil.vendor_login_bean.setCvv(json_vendor_details
								.getString("cvv"));
						CommonUtil.vendor_login_bean
								.setExpiration_month(json_vendor_details
										.getString("expiration_month"));
						CommonUtil.vendor_login_bean
								.setExpiration_year(json_vendor_details
										.getString("expiration_year"));

						CommonUtil.vendor_login_array
								.add(CommonUtil.vendor_login_bean);

						Common.ref_database.Delete_Login_Detail();
						Common.ref_database.Insert_Login_Detail(
								json_vendor_details.getString("id"),
								json_vendor_details.getString("username"),
								json_vendor_details.getString("email_id"),
								json_vendor_details.getString("password"),
								json_vendor_details.getString("phone_number"),
								"Vendor",
								json_vendor_details.getString("user_token"));

					} else if (status.equals("Promoter")) {

						CommonUtil.promoter_login_array.clear();

						JSONObject json_promoter_details = jobj_status
								.getJSONObject("user_details");
						CommonUtil.promoter_login_bean = new Promoter_Login_Bean();
						CommonUtil.promoter_login_bean
								.setId(json_promoter_details.getString("id"));
						CommonUtil.promoter_login_bean
								.setDevice_type(json_promoter_details
										.getString("device_type"));
						CommonUtil.promoter_login_bean
								.setDevice_token(json_promoter_details
										.getString("device_token"));
						CommonUtil.promoter_login_bean
								.setFirst_name(json_promoter_details
										.getString("first_name"));
						CommonUtil.promoter_login_bean
								.setLast_name(json_promoter_details
										.getString("last_name"));
						CommonUtil.promoter_login_bean
								.setEmail_id(json_promoter_details
										.getString("email_id"));
						CommonUtil.promoter_login_bean
								.setPassword(json_promoter_details
										.getString("password"));
						CommonUtil.promoter_login_bean
								.setPhone_number(json_promoter_details
										.getString("phone_number"));
						CommonUtil.promoter_login_bean
								.setContact_address(json_promoter_details
										.getString("contact_address"));
						CommonUtil.promoter_login_bean
								.setUsername(json_promoter_details
										.getString("username"));
						CommonUtil.promoter_login_bean
								.setPaypal_id(json_promoter_details
										.getString("paypal_id"));

						if (json_promoter_details.getString("logo") == null
								&& json_promoter_details.getString("logo")
										.equals("")) {
							CommonUtil.promoter_login_bean.setLogo("");
						} else {
							CommonUtil.promoter_login_bean
									.setLogo(CommonUtil.PROMOTER_IMAGE_URL
											+ json_promoter_details
													.getString("logo"));
						}

						CommonUtil.promoter_login_bean
								.setStatus(json_promoter_details
										.getString("status"));
						CommonUtil.promoter_login_bean
								.setLogin_status(json_promoter_details
										.getString("login_status"));
						CommonUtil.promoter_login_bean
								.setUser_token(json_promoter_details
										.getString("user_token"));
						CommonUtil.promoter_login_bean
								.setCreated_date(json_promoter_details
										.getString("Created_date"));
						CommonUtil.promoter_login_bean
								.setUpdate_Date(json_promoter_details
										.getString("Update_Date"));

						CommonUtil.promoter_login_array
								.add(CommonUtil.promoter_login_bean);

						Common.ref_database.Delete_Login_Detail();
						Common.ref_database
								.Insert_Login_Detail(json_promoter_details
										.getString("id"), json_promoter_details
										.getString("username"),
										json_promoter_details
												.getString("email_id"),
										json_promoter_details
												.getString("password"),
										json_promoter_details
												.getString("phone_number"),
										"Promoter", json_promoter_details
												.getString("user_token"));

					}
					mContext.startActivity(new Intent(mContext,
							Home_Screen.class));
					((Activity) mContext).overridePendingTransition(0, 0);
					((Activity) mContext).finish();

				}
			} else if (jobj_status.getInt("success") == 0) {

				if (jobj_status.has("message")) {

					Toast toast = Toast.makeText(
							mContext,
							mContext.getResources().getString(
									R.string.toast_login_incomplete),
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				}

			} else if (jobj_status.getInt("success") == 2) {
				Common.showalertDialog(mContext,
						jobj_status.getString("message"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Common.showalertDialog(mContext,
					mContext.getResources().getString(R.string.app_crash));
			/*
			 * Toast toast = Toast.makeText(mContext,
			 * mContext.getResources().getString
			 * (R.string.app_crash),Toast.LENGTH_SHORT);
			 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
			 */
		}

	}
}
