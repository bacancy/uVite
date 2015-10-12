package com.wiredave.uvite.registration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;

public class Vendor_Contact_Details_Fragment extends Fragment implements
		OnClickListener {

	EditText ed_firstname, ed_lastname, ed_emailaddress, ed_password,
			ed_repassword, ed_phonenumber, ed_contactaddress;
	Button btn_next;
	Vendor_Business_Details_Fragment vendor_business_details_fragment;
	View view = null;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.vendor_contact_details_fragment,
				container, false);

		initialization(); // initialize all objects of view...

		if (Vendor_Registration.flag.equals("update")) {
			if (Common.ref_database.checkIfExist()) {
				if (Common.ref_database.getUserType().equals("Vendor")) {
					if (CommonUtil.vendor_login_array.size() > 0) {
						ed_password.setVisibility(View.GONE);
						ed_repassword.setVisibility(View.GONE);

						ed_firstname.setText(CommonUtil.vendor_login_array.get(
								0).getFirst_name());
						ed_lastname.setText(CommonUtil.vendor_login_array
								.get(0).getLast_name());
						ed_emailaddress.setText(CommonUtil.vendor_login_array
								.get(0).getEmail_id());
						ed_phonenumber.setText(CommonUtil.vendor_login_array
								.get(0).getPhone_number());
						ed_contactaddress.setText(CommonUtil.vendor_login_array
								.get(0).getContact_address());

						Common.firstname = ed_firstname.getText().toString();
						Common.lastname = ed_lastname.getText().toString();
						Common.emailid = ed_emailaddress.getText().toString();
						Common.phonenumber = ed_phonenumber.getText()
								.toString();
						Common.contactaddress = ed_contactaddress.getText()
								.toString();

						if (ed_firstname.getText().toString().length() > 0
								&& ed_lastname.getText().toString().length() > 0
								&& ed_emailaddress.getText().toString()
										.length() > 0
								&& ed_phonenumber.getText().toString().length() > 0
								&& ed_contactaddress.getText().toString()
										.length() > 0) {
							Common.flag_vendor_contact_registration = true;
						}

						try {
							if (CommonUtil.vendor_login_array.get(0)
									.getAddress_1().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getAddress_2().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getCountry().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getState().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getCity().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getPrimary_phone().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getFax().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getPrimary_email().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getPrimary_contact().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getAdditional_contacts().length() > 0
									&& CommonUtil.vendor_login_array.get(0)
											.getWebsite().length() > 0) {
								Common.flag_vendor_business_registration = true;
							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}
				}
			}
		} else {

			ed_password.setVisibility(View.VISIBLE);
			ed_repassword.setVisibility(View.VISIBLE);

			ed_firstname.setText(Common.firstname);
			ed_lastname.setText(Common.lastname);
			ed_emailaddress.setText(Common.emailid);
			ed_password.setText(Common.password);
			ed_phonenumber.setText(Common.phonenumber);
			ed_contactaddress.setText(Common.contactaddress);

		}

		btn_next.setOnClickListener(this);

		return view;
	}

	public void initialization() {

		btn_next = (Button) view.findViewById(R.id.btn_next);

		ed_firstname = (EditText) view.findViewById(R.id.ed_firstname);
		ed_lastname = (EditText) view.findViewById(R.id.ed_lastname);
		ed_emailaddress = (EditText) view.findViewById(R.id.ed_emailaddress);
		ed_password = (EditText) view.findViewById(R.id.ed_password);
		ed_repassword = (EditText) view.findViewById(R.id.ed_repassword);
		ed_phonenumber = (EditText) view.findViewById(R.id.ed_phonenumber);
		ed_contactaddress = (EditText) view
				.findViewById(R.id.ed_contactaddress);
		
		ed_password.setTypeface(Typeface.DEFAULT);
		ed_password.setTransformationMethod(new PasswordTransformationMethod());
		
		ed_repassword.setTypeface(Typeface.DEFAULT);
		ed_repassword.setTransformationMethod(new PasswordTransformationMethod());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_next) {
			if (ed_firstname.getText().toString().length() == 0
					&& ed_lastname.getText().toString().length() == 0
					&& ed_emailaddress.getText().toString().length() == 0
					&& ed_password.getText().toString().length() == 0
					&& ed_repassword.getText().toString().length() == 0
					&& ed_phonenumber.getText().toString().length() == 0
					&& ed_contactaddress.getText().toString().length() == 0) {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alertmmsg_fill_all_details));
			} else {
				if (ed_firstname.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_firstname));
				} else if (ed_lastname.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_lastname));
				} else if (ed_emailaddress.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity()
									.getResources()
									.getString(
											R.string.alertmmsg_enter_your_email_address));
				} else if (!isEmailValid(ed_emailaddress.getText().toString())) {
					Common.showalertDialog(
							getActivity(),
							getActivity()
									.getResources()
									.getString(
											R.string.alertmmsg_enter_your_valid_email_address));

				} else if (ed_password.getVisibility() == View.VISIBLE
						&& ed_password.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_password));
				} else if (ed_repassword.getVisibility() == View.VISIBLE
						&& ed_repassword.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_repassword));
				} else if (ed_phonenumber.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_phonenumber));
				} /*
				 * else if (ed_username.getText().toString().length() == 0) {
				 * Common.showalertDialog( getActivity(),
				 * getActivity().getResources().getString(
				 * R.string.alertmmsg_enter_your_username)); }
				 */else {

					if (Vendor_Registration.flag.equals("update")) {
						if (Common.isValidEmail(ed_emailaddress.getText()
								.toString())) {
							Common.flag_vendor_contact_registration = true;

							Common.firstname = ed_firstname.getText()
									.toString();
							Common.lastname = ed_lastname.getText().toString();
							Common.emailid = ed_emailaddress.getText()
									.toString();
							Common.phonenumber = ed_phonenumber.getText()
									.toString();
							Common.contactaddress = ed_contactaddress.getText()
									.toString();

							// replace fragment...
							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							vendor_business_details_fragment = new Vendor_Business_Details_Fragment();
							fragmentTransaction.replace(
									R.id.fragment_container,
									vendor_business_details_fragment);

							fragmentTransaction.commit();
						} else {

							Common.showalertDialog(
									getActivity(),
									getActivity()
											.getResources()
											.getString(
													R.string.alertmmsg_enter_valid_email));
						}

					} else {
						if (ed_password.getText().toString().length() >= 8) {
							if (ed_password.getText().toString()
									.equals(ed_repassword.getText().toString())) {

								if (Common.isValidEmail(ed_emailaddress
										.getText().toString())) {
									Common.flag_vendor_contact_registration = true;

									Common.firstname = ed_firstname.getText()
											.toString();
									Common.lastname = ed_lastname.getText()
											.toString();
									Common.emailid = ed_emailaddress.getText()
											.toString();
									Common.password = ed_password.getText()
											.toString();
									Common.phonenumber = ed_phonenumber
											.getText().toString();
									Common.contactaddress = ed_contactaddress
											.getText().toString();

									// replace fragment...
									FragmentManager fragmentManager = getFragmentManager();
									FragmentTransaction fragmentTransaction = fragmentManager
											.beginTransaction();
									vendor_business_details_fragment = new Vendor_Business_Details_Fragment();
									fragmentTransaction.replace(
											R.id.fragment_container,
											vendor_business_details_fragment);

									fragmentTransaction.commit();
								} else {
									Common.showalertDialog(
											getActivity(),
											getActivity()
													.getResources()
													.getString(
															R.string.alertmmsg_enter_valid_email));
								}
							} else {
								Common.showalertDialog(
										getActivity(),
										getActivity()
												.getResources()
												.getString(
														R.string.alertmmsg_password_mismatch));
							}
						} else {
							Common.showalertDialog(
									getActivity(),
									getActivity()
											.getResources()
											.getString(
													R.string.alertmmsg_make_strong_password));
						}
					}

				}
			}
		}
	}

	public boolean isEmailValid(String passward) {

		boolean flag;

		String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		CharSequence inputStr = passward.trim();
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

}
