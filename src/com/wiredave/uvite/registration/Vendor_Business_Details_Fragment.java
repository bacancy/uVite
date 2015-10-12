package com.wiredave.uvite.registration;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.cropoption.InternalStorageContentProvider;

public class Vendor_Business_Details_Fragment extends Fragment implements
		OnClickListener {

	EditText ed_address1, ed_address2, ed_primaryphone, ed_fax,
			ed_primaryemail, ed_primarycontact, ed_additionalcontact,
			ed_website, ed_country, ed_state, ed_city;
	/* Spinner spinner_country, spinner_state, spinner_city; */
	Button btn_next;
	public static ImageView img_promoterprofile;
	ArrayAdapter<CharSequence> adapter_country, adapter_state, adapter_city;
	Vendor_Payment_Details_Fragment vendor_payment_details_fragment;
	View view = null;
	Bitmap bitmap = null;
	ImageLoader imageLoader = null;

	public static File mFileTemp = null;
	AlertDialog.Builder add_alert;
	private AlertDialog dialogs;

	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.vendor_business_details_fragment,
				container, false);

		initialization(); // initialize all objects of view...

		Vendor_Registration.rl_vendor_business
				.setBackgroundResource(R.color.selected_tab);
		Vendor_Registration.rl_vendor_contact
				.setBackgroundResource(R.color.unselected_tab);
		Vendor_Registration.rl_vendor_payment
				.setBackgroundResource(R.color.unselected_tab);

		imageLoader = ImageLoader.getInstance();

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					Common.TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					Common.TEMP_PHOTO_FILE_NAME);
		}

		/*
		 * adapter_country = ArrayAdapter.createFromResource(getActivity(),
		 * R.array.country, android.R.layout.simple_spinner_item);
		 * adapter_country
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * ); spinner_country.setPrompt(getActivity().getResources().getString(
		 * R.string.country_hint));
		 * 
		 * spinner_country.setAdapter(new NothingSelectedSpinnerAdapter(
		 * adapter_country,
		 * R.layout.contact_spinner_countryrow_nothing_selected,
		 * getActivity()));
		 * 
		 * adapter_state = ArrayAdapter.createFromResource(getActivity(),
		 * R.array.state, android.R.layout.simple_spinner_item); adapter_state
		 * .setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * spinner_state.setPrompt(getActivity().getResources().getString(
		 * R.string.state_hint));
		 * 
		 * spinner_state.setAdapter(new NothingSelectedSpinnerAdapter(
		 * adapter_state, R.layout.contact_spinner_staterow_nothing_selected,
		 * getActivity()));
		 * 
		 * adapter_city = ArrayAdapter.createFromResource(getActivity(),
		 * R.array.city, android.R.layout.simple_spinner_item); adapter_city
		 * .setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * spinner_city.setPrompt(getActivity().getResources().getString(
		 * R.string.city_hint));
		 * 
		 * spinner_city.setAdapter(new
		 * NothingSelectedSpinnerAdapter(adapter_city,
		 * R.layout.contact_spinner_cityrow_nothing_selected, getActivity()));
		 */

		if (Vendor_Registration.flag.equals("update")) {
			animateFirstListener = new Common.AnimateFirstDisplayListener();

			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
					.cacheOnDisk(true).resetViewBeforeLoading(true).build();

			if (Common.ref_database.checkIfExist()) {
				if (Common.ref_database.getUserType().equals("Vendor")) {
					if (CommonUtil.vendor_login_array.size() > 0) {
						if (CommonUtil.vendor_login_array.get(0).getLogo() != null
								&& !CommonUtil.vendor_login_array.get(0)
										.getLogo().equals("")) {
							// download and display profile image from url
							imageLoader.displayImage(
									CommonUtil.vendor_login_array.get(0)
											.getLogo(), img_promoterprofile,
									options);
						}

						ed_address1.setText(CommonUtil.vendor_login_array
								.get(0).getAddress_1());
						ed_address2.setText(CommonUtil.vendor_login_array
								.get(0).getAddress_2());
						ed_primaryphone.setText(CommonUtil.vendor_login_array
								.get(0).getPrimary_phone());
						ed_fax.setText(CommonUtil.vendor_login_array.get(0)
								.getFax());
						ed_primaryemail.setText(CommonUtil.vendor_login_array
								.get(0).getPrimary_email());
						ed_primarycontact.setText(CommonUtil.vendor_login_array
								.get(0).getPrimary_contact());
						ed_additionalcontact
								.setText(CommonUtil.vendor_login_array.get(0)
										.getAdditional_contacts());
						ed_website.setText(CommonUtil.vendor_login_array.get(0)
								.getWebsite());
						ed_country.setText(CommonUtil.vendor_login_array.get(0)
								.getCountry());
						ed_state.setText(CommonUtil.vendor_login_array.get(0)
								.getState());
						ed_city.setText(CommonUtil.vendor_login_array.get(0)
								.getCity());

						Common.address1 = ed_address1.getText().toString();
						Common.address2 = ed_address2.getText().toString();

						/*
						 * if (CommonUtil.vendor_login_array.get(0).getCountry()
						 * != null && !CommonUtil.vendor_login_array.get(0)
						 * .getCountry().equals("")) {
						 * spinner_country.setSelection(Common.getIndex(
						 * adapter_country, CommonUtil.vendor_login_array.get(0)
						 * .getCountry())); }
						 * 
						 * if (CommonUtil.vendor_login_array.get(0).getState()
						 * != null && !CommonUtil.vendor_login_array.get(0)
						 * .getState().equals("")) {
						 * spinner_state.setSelection(Common.getIndex(
						 * adapter_state, CommonUtil.vendor_login_array.get(0)
						 * .getState())); }
						 * 
						 * if (CommonUtil.vendor_login_array.get(0).getCity() !=
						 * null && !CommonUtil.vendor_login_array.get(0)
						 * .getCity().equals("")) {
						 * spinner_city.setSelection(Common.getIndex(
						 * adapter_city, CommonUtil.vendor_login_array
						 * .get(0).getCity())); }
						 */

						Common.country = ed_country.getText().toString();
						Common.state = ed_state.getText().toString();
						Common.city = ed_city.getText().toString();
						Common.primaryphone = ed_primaryphone.getText()
								.toString();
						Common.fax = ed_fax.getText().toString();
						Common.primaryemail = ed_primaryemail.getText()
								.toString();
						Common.primarycontact = ed_primarycontact.getText()
								.toString();
						Common.additionalcontact = ed_additionalcontact
								.getText().toString();
						Common.website = ed_website.getText().toString();

						if (ed_address1.getText().toString().length() > 0
								&& ed_address2.getText().toString().length() > 0
								/*
								 * && Common.country.length() > 0 &&
								 * Common.state.length() > 0 &&
								 * Common.city.length() > 0 &&
								 * ed_primaryphone.getText
								 * ().toString()adapter_country =
								 * ArrayAdapter.createFromResource
								 * (getActivity(), R.array.country,
								 * android.R.layout.simple_spinner_item);
								 * adapter_country
								 * .setDropDownViewResource(android
								 * .R.layout.simple_spinner_dropdown_item);
								 * spinner_country
								 * .setPrompt(getActivity().getResources
								 * ().getString( R.string.country_hint));
								 * 
								 * spinner_country.setAdapter(new
								 * NothingSelectedSpinnerAdapter(
								 * adapter_country, R.layout.
								 * contact_spinner_countryrow_nothing_selected,
								 * getActivity()));
								 * 
								 * adapter_state =
								 * ArrayAdapter.createFromResource
								 * (getActivity(), R.array.state,
								 * android.R.layout.simple_spinner_item);
								 * adapter_state
								 * .setDropDownViewResource(android
								 * .R.layout.simple_spinner_dropdown_item);
								 * spinner_state
								 * .setPrompt(getActivity().getResources
								 * ().getString( R.string.state_hint));
								 * 
								 * spinner_state.setAdapter(new
								 * NothingSelectedSpinnerAdapter( adapter_state,
								 * R
								 * .layout.contact_spinner_staterow_nothing_selected
								 * , getActivity()));
								 * 
								 * adapter_city =
								 * ArrayAdapter.createFromResource
								 * (getActivity(), R.array.city,
								 * android.R.layout.simple_spinner_item);
								 * adapter_city
								 * .setDropDownViewResource(android.
								 * R.layout.simple_spinner_dropdown_item);
								 * spinner_city
								 * .setPrompt(getActivity().getResources
								 * ().getString( R.string.city_hint));
								 * 
								 * spinner_city.setAdapter(new
								 * NothingSelectedSpinnerAdapter(adapter_city,
								 * R.
								 * layout.contact_spinner_cityrow_nothing_selected
								 * , getActivity())); .length() > 0
								 */
								&& ed_fax.getText().toString().length() > 0
								&& ed_country.getText().toString().length() > 0
								&& ed_state.getText().toString().length() > 0
								&& ed_city.getText().toString().length() > 0
								&& ed_primaryemail.getText().toString()
										.length() > 0
								&& ed_primarycontact.getText().toString()
										.length() > 0
								&& ed_additionalcontact.getText().toString()
										.length() > 0
								&& ed_website.getText().toString().length() > 0) {
							Common.flag_vendor_business_registration = true;
						}
					}
				}
			}
		} else {

			if (Common.file_path != null && !Common.file_path.equals("")) {
				bitmap = BitmapFactory.decodeFile(Common.file_path);
				img_promoterprofile.setTag(Common.file_path);
				img_promoterprofile.setImageBitmap(bitmap);
			}

			ed_address1.setText(Common.address1);
			ed_address1.setText(Common.address2);
			ed_primaryphone.setText(Common.primaryphone);
			ed_fax.setText(Common.fax);
			ed_primaryemail.setText(Common.primaryemail);
			ed_primarycontact.setText(Common.primarycontact);
			ed_additionalcontact.setText(Common.additionalcontact);
			ed_website.setText(Common.website);
			ed_country.setText(Common.country);
			ed_state.setText(Common.state);
			ed_city.setText(Common.city);
		}

		/*
		 * spinner_country.setOnItemSelectedListener(new
		 * OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) { // TODO Auto-generated method stub if
		 * (position > 0) { Common.country = spinner_country.getSelectedItem()
		 * .toString(); } }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * spinner_state.setOnItemSelectedListener(new OnItemSelectedListener()
		 * {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) { // TODO Auto-generated method stub if
		 * (position > 0) { Common.state =
		 * spinner_state.getSelectedItem().toString(); } }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * spinner_city.setOnItemSelectedListener(new OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) { // TODO Auto-generated method stub if
		 * (position > 0) { Common.city =
		 * spinner_city.getSelectedItem().toString(); } }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 */

		add_alert = new AlertDialog.Builder(getActivity());
		add_alert.setTitle(getResources().getString(R.string.photo));
		String itm[] = { getResources().getString(R.string.camera),
				getResources().getString(R.string.photo_album) };
		add_alert.setItems(itm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int which) {
				if (which == Common.PICK_FROM_CAMERA) {

					dialogs.cancel();
					takePicture();
				}
				if (which == Common.PICK_FROM_FILE) {
					dialogs.cancel();
					openGallery();
				}

			}
		});

		// create dialog for image...
		dialogs = add_alert.create();

		img_promoterprofile.setOnClickListener(this);
		btn_next.setOnClickListener(this);

		return view;
	}

	public void initialization() {

		btn_next = (Button) view.findViewById(R.id.btn_next);

		img_promoterprofile = (ImageView) view
				.findViewById(R.id.img_promoterprofile);

		/*
		 * spinner_country = (Spinner) view.findViewById(R.id.spinner_country);
		 * spinner_state = (Spinner) view.findViewById(R.id.spinner_state);
		 * spinner_city = (Spinner) view.findViewById(R.id.spinner_city);
		 */

		ed_address1 = (EditText) view.findViewById(R.id.ed_address1);
		ed_address2 = (EditText) view.findViewById(R.id.ed_address2);
		ed_primaryphone = (EditText) view.findViewById(R.id.ed_primaryphone);
		ed_fax = (EditText) view.findViewById(R.id.ed_fax);
		ed_primaryemail = (EditText) view.findViewById(R.id.ed_primaryemail);
		ed_primarycontact = (EditText) view
				.findViewById(R.id.ed_primarycontact);
		ed_additionalcontact = (EditText) view
				.findViewById(R.id.ed_additionalcontact);
		ed_website = (EditText) view.findViewById(R.id.ed_website);
		ed_country = (EditText) view.findViewById(R.id.ed_country);
		ed_state = (EditText) view.findViewById(R.id.ed_state);
		ed_city = (EditText) view.findViewById(R.id.ed_city);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.img_promoterprofile) {
			if (!dialogs.isShowing())
				dialogs.show();

		} else if (v.getId() == R.id.btn_next) {
			if (ed_address1.getText().toString().length() == 0
					&& ed_address2.getText().toString().length() == 0
					&& ed_primaryphone.getText().toString().length() == 0
					&& ed_fax.getText().toString().length() == 0
					&& ed_primaryemail.getText().toString().length() == 0
					&& ed_primarycontact.getText().toString().length() == 0
					&& ed_additionalcontact.getText().toString().length() == 0
					&& ed_website.getText().toString().length() == 0) {
				Common.showalertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.alertmmsg_fill_all_details));
			} else {
				if (ed_address1.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_address1));
				} else if (ed_address2.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_address2));
				} else if (ed_country.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_select_your_country));
				} else if (ed_state.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_select_your_state));
				} else if (ed_city.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_select_your_city));
				} else if (ed_primaryphone.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_primaryemail));
				} else if (ed_fax.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_fax));
				} else if (ed_primaryemail.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_primaryemail));
				} else if (!isEmailValid(ed_primaryemail.getText().toString())) {
					Common.showalertDialog(
							getActivity(),
							getActivity()
									.getResources()
									.getString(
											R.string.alertmmsg_enter_your_valid_email_address));

				} else if (ed_primarycontact.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity()
									.getResources()
									.getString(
											R.string.alertmmsg_enter_your_primarycontact));
				} else if (ed_website.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_website));
				} else if (!isValidUrl(ed_website.getText().toString())) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_valid_website));
				} else {

					Common.flag_vendor_business_registration = true;

					Common.address1 = ed_address1.getText().toString();
					Common.address2 = ed_address2.getText().toString();
					/*
					 * Common.country =
					 * spinner_country.getSelectedItem().toString();
					 * Common.state =
					 * spinner_state.getSelectedItem().toString(); Common.city =
					 * spinner_city.getSelectedItem().toString();
					 */
					Common.primaryphone = ed_primaryphone.getText().toString();
					Common.fax = ed_fax.getText().toString();
					Common.primaryemail = ed_primaryemail.getText().toString();
					Common.primarycontact = ed_primarycontact.getText()
							.toString();
					Common.additionalcontact = ed_additionalcontact.getText()
							.toString();
					Common.website = ed_website.getText().toString();
					Common.country = ed_country.getText().toString();
					Common.state = ed_state.getText().toString();
					Common.city = ed_city.getText().toString();

					// replace fragment...
					FragmentManager fm = getFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					vendor_payment_details_fragment = new Vendor_Payment_Details_Fragment();
					ft.replace(R.id.fragment_container,
							vendor_payment_details_fragment);
					ft.commit();

				}
			}
		}
	}

	private void takePicture() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		try {
			Uri mImageCaptureUri = null;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				mImageCaptureUri = Uri.fromFile(mFileTemp);
			} else {
				mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
			}
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					mImageCaptureUri);
			intent.putExtra("return-data", true);
			getActivity().startActivityForResult(intent,
					Common.REQUEST_CODE_TAKE_PICTURE);
		} catch (ActivityNotFoundException e) {

			Log.d("sign up", "cannot take picture", e);
		}
	}

	/**
	 * For take user profile pic from gallery...
	 */

	private void openGallery() {

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		getActivity().startActivityForResult(photoPickerIntent,
				Common.REQUEST_CODE_GALLERY);
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

	private boolean isValidUrl(String url) {
		Pattern p = Patterns.WEB_URL;
		Matcher m = p.matcher(url);
		if (m.matches())
			return true;
		else
			return false;
	}

}
