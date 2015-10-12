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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Promoter_SignUp_Task;
import com.wiredave.uvite.asynctask.Vendor_SignUp_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.cropoption.InternalStorageContentProvider;
import com.wiredave.uvite.registration.Promoter_Registration;
import com.wiredave.uvite.registration.Vendor_Registration;

public class Promoter_Contact_Details_Fragment extends Fragment implements
		OnClickListener {

	EditText ed_firstname, ed_lastname, ed_emailaddress, ed_password,
			ed_phonenumber, ed_contactaddress;
	public static ImageView img_promoterprofile;
	Button btn_next;
	View view = null;
	Bitmap bitmap = null;

	// ed_repassword,ed_username

	Promoter_Paypal_Details_Fragment promoter_paypal_details_fragment;

	public static File mFileTemp = null;
	AlertDialog.Builder add_alert;
	private AlertDialog dialogs;
	ImageLoader imageLoader = null;
	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.promoter_contact_details_fragment,
				container, false);

		initialization(); // initialize all objects of view...

		Promoter_Registration.rl_promoters_contact
				.setBackgroundResource(R.color.selected_tab);
		Promoter_Registration.rl_promoters_paypal
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

		if (Promoter_Registration.flag.equals("update")) {
			animateFirstListener = new Common.AnimateFirstDisplayListener();

			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
					.cacheOnDisk(true).resetViewBeforeLoading(true).build();

			if (Common.ref_database.checkIfExist()) {
				if (Common.ref_database.getUserType().equals("Promoter")) {
					if (CommonUtil.promoter_login_array.size() > 0) {
						ed_password.setVisibility(View.GONE);
						/* ed_repassword.setVisibility(View.GONE); */

						if (CommonUtil.promoter_login_array.get(0).getLogo() != null
								&& !CommonUtil.promoter_login_array.get(0)
										.getLogo().equals("")) {
							// download and display profile image from url
							imageLoader.displayImage(
									CommonUtil.promoter_login_array.get(0)
											.getLogo(), img_promoterprofile,
									options);
						}

						ed_firstname.setText(CommonUtil.promoter_login_array
								.get(0).getFirst_name());
						ed_lastname.setText(CommonUtil.promoter_login_array
								.get(0).getLast_name());
						ed_emailaddress.setText(CommonUtil.promoter_login_array
								.get(0).getEmail_id());
						ed_phonenumber.setText(CommonUtil.promoter_login_array
								.get(0).getPhone_number());
						ed_contactaddress
								.setText(CommonUtil.promoter_login_array.get(0)
										.getContact_address());
						/*
						 * ed_username.setText(CommonUtil.promoter_login_array
						 * .get(0).getUsername());
						 */

						Common.firstname = ed_firstname.getText().toString();
						Common.lastname = ed_lastname.getText().toString();
						Common.emailid = ed_emailaddress.getText().toString();
						Common.phonenumber = ed_phonenumber.getText()
								.toString();
						Common.contactaddress = ed_contactaddress.getText()
								.toString();
						/* Common.username = ed_username.getText().toString(); */

						if (ed_firstname.getText().toString().length() > 0
								&& ed_lastname.getText().toString().length() > 0
								&& ed_emailaddress.getText().toString()
										.length() > 0
								&& ed_phonenumber.getText().toString().length() > 0
								&& ed_contactaddress.getText().toString()
										.length() > 0) {
							Common.flag_promoter_contact_registration = true;
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

			ed_password.setVisibility(View.VISIBLE);
			// ed_repassword.setVisibility(View.VISIBLE);

			ed_firstname.setText(Common.firstname);
			ed_lastname.setText(Common.lastname);
			ed_emailaddress.setText(Common.emailid);
			ed_password.setText(Common.password);
			ed_phonenumber.setText(Common.phonenumber);
			ed_contactaddress.setText(Common.contactaddress);
			// ed_username.setText(Common.username);

		}

		add_alert = new AlertDialog.Builder(getActivity());
		add_alert.setTitle(getResources().getString(R.string.photo));
		String itm[] = { getResources().getString(R.string.photo_album) };
		// getResources().getString(R.string.camera),
		add_alert.setItems(itm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int which) {
				/*
				 * if (which == Common.PICK_FROM_CAMERA) {
				 * 
				 * dialogs.cancel(); takePicture(); }
				 */
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
		img_promoterprofile = (ImageView) view
				.findViewById(R.id.img_promoterprofile);

		btn_next = (Button) view.findViewById(R.id.btn_next);

		ed_firstname = (EditText) view.findViewById(R.id.ed_firstname);
		ed_lastname = (EditText) view.findViewById(R.id.ed_lastname);
		ed_emailaddress = (EditText) view.findViewById(R.id.ed_emailaddress);
		ed_password = (EditText) view.findViewById(R.id.ed_password);
		// ed_repassword = (EditText) view.findViewById(R.id.ed_repassword);
		ed_phonenumber = (EditText) view.findViewById(R.id.ed_phonenumber);
		ed_contactaddress = (EditText) view
				.findViewById(R.id.ed_contactaddress);
		// ed_username = (EditText) view.findViewById(R.id.ed_username);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.img_promoterprofile) {
			dialogs.show();

		} else if (v.getId() == R.id.btn_next) {
			if (ed_firstname.getText().toString().length() == 0
					&& ed_lastname.getText().toString().length() == 0
					&& ed_emailaddress.getText().toString().length() == 0
					&& ed_password.getText().toString().length() == 0

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
				} /*
				 * else if (ed_repassword.getVisibility() == View.VISIBLE &&
				 * ed_repassword.getText().toString().length() == 0) {
				 * Common.showalertDialog( getActivity(),
				 * getActivity().getResources().getString(
				 * R.string.alertmmsg_enter_your_repassword)); }
				 */else if (ed_phonenumber.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.alertmmsg_enter_your_phonenumber));
				} else if (ed_contactaddress.getText().toString().length() == 0) {
					Common.showalertDialog(
							getActivity(),
							getActivity()
									.getResources()
									.getString(
											R.string.alertmmsg_enter_your_contactaddress));
				} /*
				 * else if (ed_username.getText().toString().length() == 0) {
				 * Common.showalertDialog( getActivity(),
				 * getActivity().getResources().getString(
				 * R.string.alertmmsg_enter_your_username)); }
				 */else {

					if (Promoter_Registration.flag.equals("update")) {
						if (Common.isValidEmail(ed_emailaddress.getText()
								.toString())) {

							Common.flag_promoter_contact_registration = true;

							Common.firstname = ed_firstname.getText()
									.toString();
							Common.lastname = ed_lastname.getText().toString();
							Common.emailid = ed_emailaddress.getText()
									.toString();
							Common.phonenumber = ed_phonenumber.getText()
									.toString();
							Common.contactaddress = ed_contactaddress.getText()
									.toString();
							// Common.username =
							// ed_username.getText().toString();

							// replace fragment...
							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							promoter_paypal_details_fragment = new Promoter_Paypal_Details_Fragment();
							fragmentTransaction.replace(
									R.id.fragment_container,
									promoter_paypal_details_fragment);

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

							if (ed_emailaddress.getText().toString().length() != 0) {
								if (Common.isValidEmail(ed_emailaddress
										.getText().toString())) {
									Common.flag_promoter_contact_registration = true;

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
									promoter_paypal_details_fragment = new Promoter_Paypal_Details_Fragment();
									fragmentTransaction.replace(
											R.id.fragment_container,
											promoter_paypal_details_fragment);

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
														R.string.alertmmsg_enter_your_email_address));
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

	/**
	 * For take user profile pic from gallery...
	 */

	private void openGallery() {

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		getActivity().startActivityForResult(photoPickerIntent,
				Common.REQUEST_CODE_GALLERY);
	}

}
