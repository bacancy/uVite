package com.wiredave.uvite.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.AuthorizeNet_CreditCard_Task;
import com.wiredave.uvite.asynctask.Vendor_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;

public class Vendor_Payment_Details_Fragment extends Fragment implements
		OnClickListener, TextWatcher {

	EditText ed_cardnumber, ed_cvv, ed_expirationmonth, ed_expirationyear;
	Button btn_next, btn_addlater, btn_update;
	RelativeLayout rl_update ;
	LinearLayout rl_insert;
	String a;
	int keyDel;
	View view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.vendor_payment_details_fragment,
				container, false);

		initialization(); // initialize all objects of view...

		Vendor_Registration.rl_vendor_business
				.setBackgroundResource(R.color.unselected_tab);
		Vendor_Registration.rl_vendor_contact
				.setBackgroundResource(R.color.unselected_tab);
		Vendor_Registration.rl_vendor_payment
				.setBackgroundResource(R.color.selected_tab);

		ed_cardnumber.addTextChangedListener(this);

		if (Vendor_Registration.flag.equals("update")) {
			rl_insert.setVisibility(View.GONE);
			rl_update.setVisibility(View.VISIBLE);

			if (Common.ref_database.checkIfExist()) {
				if (Common.ref_database.getUserType().equals("Vendor")) {
					if (CommonUtil.vendor_login_array.size() > 0) {
						if (CommonUtil.vendor_login_array.get(0)
								.getCard_number() != null
								& CommonUtil.vendor_login_array.get(0)
										.getCard_number().length() > 0) {
							// ed_cardnumber.setText(Common.maskNumber(CommonUtil.vendor_login_array.get(0).getCard_number(),
							// "xxxx-xxxx-xxxx-####"));
							ed_cardnumber.setText(Common.set_FormatCardNumber(
									"-", CommonUtil.vendor_login_array.get(0)
											.getCard_number(), 4));
						} else {
							ed_cardnumber.setText("");
						}

						ed_cvv.setText(CommonUtil.vendor_login_array.get(0)
								.getCvv());
						ed_expirationmonth
								.setText(CommonUtil.vendor_login_array.get(0)
										.getExpiration_month());
						ed_expirationyear.setText(CommonUtil.vendor_login_array
								.get(0).getExpiration_year());

						/*
						 * ed_cvv.setText(Common.maskNumber(CommonUtil.
						 * vendor_login_array.get(0).getCvv(), "xxx"));
						 * ed_expirationmonth
						 * .setText(Common.maskNumber(CommonUtil
						 * .vendor_login_array.get(0).getExpiration_month(),
						 * "xx"));
						 * ed_expirationyear.setText(Common.maskNumber(CommonUtil
						 * .vendor_login_array.get(0).getExpiration_year(),
						 * "xxxx"));
						 */

						Common.cardnumber = ed_cardnumber.getText().toString()
								.replace("-", "");
						Common.cvv = ed_cvv.getText().toString();
						Common.expiration_month = ed_expirationmonth.getText()
								.toString();
						Common.expiration_year = ed_expirationyear.getText()
								.toString();

						if (ed_cardnumber.getText().toString().length() > 0
								&& ed_cvv.getText().toString().length() > 0
								&& ed_expirationmonth.getText().toString()
										.length() > 0
								&& ed_expirationyear.getText().toString()
										.length() > 0) {
							Common.flag_vendor_payment_registration = true;
						}
					}
				}
			}
		} else {
			rl_insert.setVisibility(View.VISIBLE);
			rl_update.setVisibility(View.GONE);
		}

		ed_cardnumber.addTextChangedListener(this);

		btn_next.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		btn_addlater.setOnClickListener(this);

		return view;
	}

	public void initialization() {

		btn_next = (Button) view.findViewById(R.id.btn_next);
		btn_addlater = (Button) view.findViewById(R.id.btn_addlater);
		btn_update = (Button) view.findViewById(R.id.btn_update);

		rl_insert = (LinearLayout) view.findViewById(R.id.rl_insert);
		rl_update = (RelativeLayout) view.findViewById(R.id.rl_update);

		ed_cardnumber = (EditText) view.findViewById(R.id.ed_cardnumber);
		ed_cvv = (EditText) view.findViewById(R.id.ed_cvv);
		ed_expirationmonth = (EditText) view
				.findViewById(R.id.ed_expirationmonth);
		ed_expirationyear = (EditText) view
				.findViewById(R.id.ed_expirationyear);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_next) {
			if (ed_cardnumber.getText().toString().length() > 0
					&& ed_cvv.getText().toString().length() > 0
					&& ed_expirationmonth.getText().toString().length() > 0
					&& ed_expirationyear.getText().toString().length() > 0) {
				if (ed_cardnumber.getText().toString().replace("-", "")
						.length() < 16) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_cardnumber));
				} else if (ed_cvv.getText().toString().length() < 3) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_cvv_code));
				} else if (ed_expirationmonth.getText().toString().length() < 2) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_expired_month));
				} else if (Integer.parseInt(ed_expirationmonth.getText()
						.toString()) > 12) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_expired_month));
				} else if (ed_expirationyear.getText().toString().length() < 4) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_expired_year));
				} else {

					Common.flag_vendor_payment_registration = true;

					/*Common.cardnumber = ed_cardnumber.getText().toString()
							.replace("-", "");
					Common.cvv = ed_cvv.getText().toString();
					Common.expiration_month = ed_expirationmonth.getText()
							.toString();
					Common.expiration_year = ed_expirationyear.getText()
							.toString();
					Common.expiration_date = Common.expiration_month
							+ ed_expirationyear.getText().toString()
									.substring(2);*/
					
					Common.cardnumber = "5424000000000015";
					Common.cvv = "999";
					Common.expiration_month = "12";
					Common.expiration_year = "23";
					Common.expiration_date = "1223";

					Common.total_amount = "5";

					if (Vendor_Registration.flag.equals("insert")) {
						// check internet connetivity...
						if (Common.isConnectingToInternet(getActivity())) {
							// call this for authorize .net card payment...
							new AuthorizeNet_CreditCard_Task(getActivity(),
									"registration", Common.cardnumber,
									Common.expiration_date, Common.cvv,
									Common.total_amount).execute();

						} else {

							Common.showalertDialog(
									getActivity(),
									getActivity()
											.getResources()
											.getString(
													R.string.alert_internetconnectivity));
						}
					} else if (Vendor_Registration.flag.equals("update")) {

						if (CommonUtil.vendor_login_array.get(0)
								.getCard_number().equals(Common.cardnumber)) {
							// check internet connetivity...
							if (Common.isConnectingToInternet(getActivity())) {
								// call vendor sign up api...
								new Vendor_SignUp_Task(getActivity(),
										Vendor_Registration.flag).execute();

							} else {

								Common.showalertDialog(
										getActivity(),
										getActivity()
												.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}
						} else {
							// check internet connetivity...
							if (Common.isConnectingToInternet(getActivity())) {
								// call this for authorize .net card payment...
								new AuthorizeNet_CreditCard_Task(getActivity(),
										"registration", Common.cardnumber,
										Common.expiration_date, Common.cvv,
										Common.total_amount).execute();

							} else {

								Common.showalertDialog(
										getActivity(),
										getActivity()
												.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}
						}
					}
				}
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alertmmsg_fill_all_details));
			}

		} else if (v.getId() == R.id.btn_addlater) {

			Common.flag_vendor_payment_registration = false;

			Common.cardnumber = "";
			Common.cvv = "";
			Common.expiration_month = "";
			Common.expiration_year = "";

			// check internet connetivity...
			if (Common.isConnectingToInternet(getActivity())) {
				// call vendor sign up api...
				new Vendor_SignUp_Task(getActivity(), Vendor_Registration.flag)
						.execute();

			} else {

				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alert_internetconnectivity));
			}

		} else if (v.getId() == R.id.btn_update) {

			if (ed_cardnumber.getText().toString().length() > 0
					&& ed_cvv.getText().toString().length() > 0
					&& ed_expirationmonth.getText().toString().length() > 0
					&& ed_expirationyear.getText().toString().length() > 0) {
				if (ed_cardnumber.getText().toString().replace("-", "")
						.length() < 16) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_cardnumber));
				} else if (ed_cvv.getText().toString().length() < 3) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_cvv_code));
				} else if (ed_expirationmonth.getText().toString().length() < 2) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_expired_month));
				} else if (ed_expirationyear.getText().toString().length() < 4) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_check_expired_year));
				} else {

					Common.flag_vendor_payment_registration = true;

					/*Common.cardnumber = ed_cardnumber.getText().toString()
							.replace("-", "");
					Common.cvv = ed_cvv.getText().toString();
					Common.expiration_month = ed_expirationmonth.getText()
							.toString();
					Common.expiration_year = ed_expirationyear.getText()
							.toString();
					Common.expiration_date = Common.expiration_month
							+ ed_expirationyear.getText().toString()
									.substring(2);*/
					
					Common.cardnumber = "5424000000000015";
					Common.cvv = "999";
					Common.expiration_month = "12";
					Common.expiration_year = "23";
					Common.expiration_date = "1223";

					Common.total_amount = "5";

					if (Vendor_Registration.flag.equals("insert")) {
						// check internet connetivity...
						if (Common.isConnectingToInternet(getActivity())) {
							// call this for authorize .net card payment...
							new AuthorizeNet_CreditCard_Task(getActivity(),
									"registration", Common.cardnumber,
									Common.expiration_date, Common.cvv,
									Common.total_amount).execute();

						} else {

							Common.showalertDialog(
									getActivity(),
									getActivity()
											.getResources()
											.getString(
													R.string.alert_internetconnectivity));
						}
					} else if (Vendor_Registration.flag.equals("update")) {

						if (CommonUtil.vendor_login_array.get(0)
								.getCard_number().equals(Common.cardnumber)) {
							// check internet connetivity...
							if (Common.isConnectingToInternet(getActivity())) {
								// call vendor sign up api...
								new Vendor_SignUp_Task(getActivity(),
										Vendor_Registration.flag).execute();

							} else {

								Common.showalertDialog(
										getActivity(),
										getActivity()
												.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}
						} else {
							// check internet connetivity...
							if (Common.isConnectingToInternet(getActivity())) {
								// call this for authorize .net card payment...
								new AuthorizeNet_CreditCard_Task(getActivity(),
										"registration", Common.cardnumber,
										Common.expiration_date, Common.cvv,
										Common.total_amount).execute();

							} else {

								Common.showalertDialog(
										getActivity(),
										getActivity()
												.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}
						}
					}
				}
			} else {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alertmmsg_fill_all_details));
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		boolean flag = true;
		String eachBlock[] = ed_cardnumber.getText().toString().split("-");
		for (int i = 0; i < eachBlock.length; i++) {
			if (eachBlock[i].length() > 4) {
				flag = false;
			}
		}
		if (flag) {

			ed_cardnumber.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if (keyCode == KeyEvent.KEYCODE_DEL)
						keyDel = 1;
					return false;
				}
			});

			if (keyDel == 0) {

				if (((ed_cardnumber.getText().length() + 1) % 5) == 0) {

					if (ed_cardnumber.getText().toString().split("-").length <= 3) {
						ed_cardnumber.setText(ed_cardnumber.getText() + "-");
						ed_cardnumber.setSelection(ed_cardnumber.getText()
								.length());
					}
				}
				a = ed_cardnumber.getText().toString();
			} else {
				a = ed_cardnumber.getText().toString();
				keyDel = 0;
			}

		} else {
			ed_cardnumber.setText(a);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
