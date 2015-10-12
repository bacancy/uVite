package com.wiredave.uvite.vendor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wiredave.uvite.R;
import com.wiredave.uvite.adapter.NothingSelectedSpinnerAdapter;
import com.wiredave.uvite.asynctask.Edit_Vendor_Coupon_Task;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.cropoption.InternalStorageContentProvider;

import eu.janmuller.android.simplecropimage.CropImage;

public class Vendor_Create_Coupon extends Activity implements OnClickListener {

	Button btn_makepayment;
	EditText ed_coupontitle, ed_coupondesc, ed_referralcommission,
			ed_numbersofcoupon;
	ImageView img_coupon;
	Spinner spinner_category, spinner_discounttype;
	TextView txt_startdate, txt_enddate, txt_totalamount;
	RelativeLayout rl_back, rl_startdate, rl_enddate;
	public static File mFileTemp = null;
	AlertDialog.Builder add_alert;
	private AlertDialog dialogs;
	Bitmap bitmap = null;
	ImageLoader imageLoader = null;
	public static String flag = "";
	Bundle bundle = null;
	int position = 0;
	String vendor_type = "";

	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;

	ArrayAdapter<CharSequence> adapter_category, adapter_discounttype;

	DatePickerDialog.OnDateSetListener start_dateListener, end_dateListener;

	static final int DATE_PICKER_START = 1;
	static final int DATE_PICKER_END = 0;
	static final int RESULT_CLOSE_ALL = 5;

	public int mYear = 0, mMonth = 0, mDay = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_coupon);

		initialization(); // initialize view of objects...

		imageLoader = ImageLoader.getInstance();

		/* get the current date */
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		try {
			bundle = getIntent().getExtras();
			if (bundle != null) {
				flag = bundle.getString("vendor_create_coupon_flag");

				if (flag.equals("update")) {
					vendor_type = bundle.getString("vendor_type");
					position = bundle.getInt("val_position");
				}

			} else {
				flag = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = "";
		}
		if (flag.equals("add")) {
			btn_makepayment.setText(this.getResources().getString(
					R.string.make_payment));
		} else if (flag.equals("update")) {
			btn_makepayment.setText(this.getResources().getString(
					R.string.update));
		}

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					Common.TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(Vendor_Create_Coupon.this.getFilesDir(),
					Common.TEMP_PHOTO_FILE_NAME);
		}

		adapter_category = ArrayAdapter.createFromResource(this,
				R.array.category, android.R.layout.simple_spinner_item);
		adapter_category
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_category.setPrompt(this.getResources().getString(
				R.string.select_category));

		spinner_category.setAdapter(new NothingSelectedSpinnerAdapter(
				adapter_category,
				R.layout.contact_spinner_categoryrow_nothing_selected, this));

		adapter_discounttype = ArrayAdapter.createFromResource(this,
				R.array.discounttype, android.R.layout.simple_spinner_item);
		adapter_discounttype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_discounttype.setPrompt(this.getResources().getString(
				R.string.discount_type));

		spinner_discounttype.setAdapter(new NothingSelectedSpinnerAdapter(
				adapter_discounttype,
				R.layout.contact_spinner_discountrow_nothing_selected, this));

		add_alert = new AlertDialog.Builder(Vendor_Create_Coupon.this);
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

		start_dateListener = new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {

				Calendar cal = Calendar.getInstance();

				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);

				if (selectedYear < year) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_startdate));
				} else if (selectedMonth < month) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_startdate));
				} else if (selectedDay < day) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_startdate));
				} else {
					Common.start_year = selectedYear;
					Common.start_month = selectedMonth;
					Common.start_day = selectedDay;

					// set start date...
					Display_Date(Common.start_day, Common.start_month,
							Common.start_year, txt_startdate);
				}

			}
		};

		end_dateListener = new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {

				Calendar cal = Calendar.getInstance();

				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);

				if (selectedYear < year) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_enddate));
				} else if (selectedMonth < month) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_enddate));
				} else if (selectedDay < day) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							getResources().getString(
									R.string.alertmmsg_invalid_enddate));
				} else {

					Common.end_year = selectedYear;
					Common.end_month = selectedMonth;
					Common.end_day = selectedDay;

					// set end date...
					Display_Date(Common.end_day, Common.end_month,
							Common.end_year, txt_enddate);
				}

			}
		};

		spinner_category
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							Common.category = spinner_category
									.getSelectedItem().toString();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		spinner_discounttype
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							Common.discount_type = spinner_discounttype
									.getSelectedItem().toString();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		ed_referralcommission.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				try {
					if (!s.toString().equals("") && s.toString() != null) {
						Common.referral_commission = s.toString();
					} else {
						Common.referral_commission = "0";
					}

					Common.total_amount = String.valueOf(Long
							.parseLong(Common.referral_commission)
							* Long.parseLong(Common.number_of_coupons));
				} catch (Exception e) {
					// TODO: handle exception
					Common.total_amount = "0";
				}

				txt_totalamount.setText(getResources().getString(
						R.string.total_amount)
						+ " " + Common.total_amount);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		ed_numbersofcoupon.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				try {
					if (!s.toString().equals("") && s.toString() != null) {
						Common.number_of_coupons = s.toString();
					} else {
						Common.number_of_coupons = "0";
					}

					Common.total_amount = String.valueOf(Long
							.parseLong(Common.referral_commission)
							* Long.parseLong(Common.number_of_coupons));
				} catch (Exception e) {
					// TODO: handle exception
					Common.total_amount = "0";
				}

				txt_totalamount.setText(getResources().getString(
						R.string.total_amount)
						+ " " + Common.total_amount);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		if (flag.equals("update")) {
			animateFirstListener = new Common.AnimateFirstDisplayListener();

			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
					.cacheOnDisk(true).resetViewBeforeLoading(true).build();

			ed_numbersofcoupon.setEnabled(false);

			if (vendor_type.equals("active")) {
				if (CommonUtil.vendor_active_coupon_array.size() > 0) {
					if (CommonUtil.vendor_active_coupon_array.get(0).getLogo() != null
							&& !CommonUtil.vendor_active_coupon_array.get(0)
									.getLogo().equals("")) {
						// download and display image from url
						imageLoader.displayImage(
								CommonUtil.vendor_active_coupon_array.get(0)
										.getLogo(), img_coupon, options);
					}

					ed_coupontitle
							.setText(CommonUtil.vendor_active_coupon_array.get(
									position).getCoupon_title());
					ed_coupondesc.setText(CommonUtil.vendor_active_coupon_array
							.get(position).getCoupon_description());
					ed_referralcommission
							.setText(CommonUtil.vendor_active_coupon_array.get(
									position).getCommission());
					ed_numbersofcoupon
							.setText(CommonUtil.vendor_active_coupon_array.get(
									position).getTotal_coupon());
					txt_startdate.setText(CommonUtil.vendor_active_coupon_array
							.get(position).getStart_date());
					txt_enddate.setText(CommonUtil.vendor_active_coupon_array
							.get(position).getEnd_date());

					if (CommonUtil.vendor_active_coupon_array.get(position)
							.getCoupon_category() != null
							&& !CommonUtil.vendor_active_coupon_array
									.get(position).getCoupon_category()
									.equals("")) {
						spinner_category.setSelection(Common.getIndex(
								adapter_category,
								CommonUtil.vendor_active_coupon_array.get(
										position).getCoupon_category()));
					}

					if (CommonUtil.vendor_active_coupon_array.get(position)
							.getDiscount_type() != null
							&& !CommonUtil.vendor_active_coupon_array
									.get(position).getDiscount_type()
									.equals("")) {
						spinner_discounttype.setSelection(Common.getIndex(
								adapter_discounttype,
								CommonUtil.vendor_active_coupon_array.get(
										position).getDiscount_type()));
					}

					if (!ed_referralcommission.getText().toString().equals("")
							&& ed_referralcommission.getText().toString() != null) {
						Common.referral_commission = ed_referralcommission
								.getText().toString();
					} else {
						Common.referral_commission = "0";
					}

					if (!ed_numbersofcoupon.getText().toString().equals("")
							&& ed_numbersofcoupon.getText().toString() != null) {
						Common.number_of_coupons = ed_numbersofcoupon.getText()
								.toString();
					} else {
						Common.number_of_coupons = "0";
					}

					Common.total_amount = String.valueOf(Long
							.parseLong(Common.referral_commission)
							* Long.parseLong(Common.number_of_coupons));

					txt_totalamount.setText(getResources().getString(
							R.string.total_amount)
							+ " " + Common.total_amount);

				}
			} else if (vendor_type.equals("expired")) {
				if (CommonUtil.vendor_expired_coupon_array.size() > 0) {
					if (CommonUtil.vendor_expired_coupon_array.get(0).getLogo() != null
							&& !CommonUtil.vendor_expired_coupon_array.get(0)
									.getLogo().equals("")) {
						// download and display image from url
						imageLoader.displayImage(
								CommonUtil.vendor_expired_coupon_array.get(0)
										.getLogo(), img_coupon, options);
					}

					ed_coupontitle
							.setText(CommonUtil.vendor_expired_coupon_array
									.get(position).getCoupon_title());
					ed_coupondesc
							.setText(CommonUtil.vendor_expired_coupon_array
									.get(position).getCoupon_description());
					ed_referralcommission
							.setText(CommonUtil.vendor_expired_coupon_array
									.get(position).getCommission());
					ed_numbersofcoupon
							.setText(CommonUtil.vendor_expired_coupon_array
									.get(position).getTotal_coupon());
					txt_startdate
							.setText(CommonUtil.vendor_expired_coupon_array
									.get(position).getStart_date());
					txt_enddate.setText(CommonUtil.vendor_expired_coupon_array
							.get(position).getEnd_date());

					if (CommonUtil.vendor_expired_coupon_array.get(position)
							.getCoupon_category() != null
							&& !CommonUtil.vendor_expired_coupon_array
									.get(position).getCoupon_category()
									.equals("")) {
						spinner_category.setSelection(Common.getIndex(
								adapter_category,
								CommonUtil.vendor_expired_coupon_array.get(
										position).getCoupon_category()));
					}

					if (CommonUtil.vendor_expired_coupon_array.get(position)
							.getDiscount_type() != null
							&& !CommonUtil.vendor_expired_coupon_array
									.get(position).getDiscount_type()
									.equals("")) {
						spinner_discounttype.setSelection(Common.getIndex(
								adapter_discounttype,
								CommonUtil.vendor_expired_coupon_array.get(
										position).getDiscount_type()));
					}
					if (!ed_referralcommission.getText().toString().equals("")
							&& ed_referralcommission.getText().toString() != null) {
						Common.referral_commission = ed_referralcommission
								.getText().toString();
					} else {
						Common.referral_commission = "0";
					}

					if (!ed_numbersofcoupon.getText().toString().equals("")
							&& ed_numbersofcoupon.getText().toString() != null) {
						Common.number_of_coupons = ed_numbersofcoupon.getText()
								.toString();
					} else {
						Common.number_of_coupons = "0";
					}

					Common.total_amount = String.valueOf(Long
							.parseLong(Common.referral_commission)
							* Long.parseLong(Common.number_of_coupons));

					txt_totalamount.setText(getResources().getString(
							R.string.total_amount)
							+ " " + Common.total_amount);

				}
			} else if (vendor_type.equals("redeemed")) {

			}
		} else {

			ed_numbersofcoupon.setEnabled(true);
		}
		rl_back.setOnClickListener(this);
		rl_startdate.setOnClickListener(this);
		rl_enddate.setOnClickListener(this);
		img_coupon.setOnClickListener(this);
		btn_makepayment.setOnClickListener(this);

	}

	public void initialization() {
		btn_makepayment = (Button) findViewById(R.id.btn_makepayment);

		txt_startdate = (TextView) findViewById(R.id.txt_startdate);
		txt_enddate = (TextView) findViewById(R.id.txt_enddate);
		txt_totalamount = (TextView) findViewById(R.id.txt_totalamount);

		spinner_category = (Spinner) findViewById(R.id.spinner_category);
		spinner_discounttype = (Spinner) findViewById(R.id.spinner_discounttype);

		img_coupon = (ImageView) findViewById(R.id.img_coupon);

		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_startdate = (RelativeLayout) findViewById(R.id.rl_startdate);
		rl_enddate = (RelativeLayout) findViewById(R.id.rl_enddate);

		ed_coupontitle = (EditText) findViewById(R.id.ed_coupontitle);
		ed_coupondesc = (EditText) findViewById(R.id.ed_coupondesc);
		ed_referralcommission = (EditText) findViewById(R.id.ed_referralcommission);
		ed_numbersofcoupon = (EditText) findViewById(R.id.ed_numbersofcoupon);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.rl_back) {
			overridePendingTransition(0, 0);
			finish();

		} else if (v.getId() == R.id.img_coupon) {

			if (!dialogs.isShowing()) {
				dialogs.show();
			}
		} else if (v.getId() == R.id.rl_startdate) {

			showDialog(DATE_PICKER_START);

		} else if (v.getId() == R.id.rl_enddate) {

			showDialog(DATE_PICKER_END);

		} else if (v.getId() == R.id.btn_makepayment) {
			if (ed_coupontitle.getText().toString().length() == 0
					&& ed_coupondesc.getText().toString().length() == 0
					&& Common.category.equals("")
					&& Common.discount_type.equals("")
					&& ed_referralcommission.getText().toString().length() == 0
					&& ed_numbersofcoupon.getText().toString().length() == 0
					&& txt_startdate
							.getText()
							.toString()
							.equals(getResources().getString(
									R.string.start_date))
					&& txt_enddate
							.getText()
							.toString()
							.equals(getResources().getString(R.string.end_date))) {
				Common.showalertDialog(
						Vendor_Create_Coupon.this,
						this.getResources().getString(
								R.string.alertmmsg_fill_all_details));
			} else {
				if (ed_coupontitle.getText().toString().length() == 0) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_enter_your_coupon_title));

				} else if (ed_coupondesc.getText().toString().length() == 0) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_enter_your_coupon_desc));
				} else if (Common.category.equals("")
						|| Common.category == null) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_select_your_category));
				} else if (Common.discount_type.equals("")
						|| Common.discount_type == null) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources()
									.getString(
											R.string.alertmmsg_select_your_discount_type));
				} else if (ed_referralcommission.getText().toString().length() == 0) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources()
									.getString(
											R.string.alertmmsg_enter_referral_commission));
				} else if (ed_numbersofcoupon.getText().toString().length() == 0) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_enter_number_of_coupons));
				} else if (txt_startdate.getText().toString()
						.equals(getResources().getString(R.string.start_date))) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_choose_start_date));
				} else if (txt_enddate.getText().toString()
						.equals(getResources().getString(R.string.end_date))) {
					Common.showalertDialog(
							Vendor_Create_Coupon.this,
							this.getResources().getString(
									R.string.alertmmsg_choose_end_date));
				} else {
					Log.d("isdatebefore",
							""
									+ Common.isDateBefore(txt_startdate
											.getText().toString(), txt_enddate
											.getText().toString()));
					if (Common.isDateAfter(txt_startdate.getText().toString(),
							txt_enddate.getText().toString())
							&& Common.isDateBefore(txt_startdate.getText()
									.toString(), txt_enddate.getText()
									.toString())) {
						Common.coupon_title = ed_coupontitle.getText()
								.toString();
						Common.coupon_description = ed_coupondesc.getText()
								.toString();
						Common.category = spinner_category.getSelectedItem()
								.toString();
						Common.discount_type = spinner_discounttype
								.getSelectedItem().toString();
						Common.start_date = txt_startdate.getText().toString();
						Common.end_date = txt_enddate.getText().toString();
						Common.referral_commission = ed_referralcommission
								.getText().toString();
						Common.number_of_coupons = ed_numbersofcoupon.getText()
								.toString();

						if (flag.equals("add")) {
							// startActivityForResult(new
							// Intent(Vendor_Create_Coupon.this,Vendor_Payment.class),5);
							startActivityForResult(
									new Intent(
											Vendor_Create_Coupon.this,
											Vendor_Authorized_CreditCard_Payment.class),
									5);
							overridePendingTransition(0, 0);
							// finish();

						} else if (flag.equals("update")) {

							// check internet connetivity...
							if (Common
									.isConnectingToInternet(Vendor_Create_Coupon.this)) {
								// call this for update vendor coupon...
								new Edit_Vendor_Coupon_Task(
										Vendor_Create_Coupon.this,
										CommonUtil.vendor_active_coupon_array
												.get(position).getCoupon_id(),
										vendor_type).execute();

							} else {
								Common.showalertDialog(
										Vendor_Create_Coupon.this,
										this.getResources()
												.getString(
														R.string.alert_internetconnectivity));
							}
						}
					} else {
						Common.showalertDialog(
								Vendor_Create_Coupon.this,
								Vendor_Create_Coupon.this
										.getResources()
										.getString(
												R.string.alertmmsg_select_valid_dates));
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
			startActivityForResult(intent, Common.REQUEST_CODE_TAKE_PICTURE);
		} catch (ActivityNotFoundException e) {

			Log.d("Vendor_Create_Coupon_Take_Picture", "cannot take picture", e);
		}
	}

	/**
	 * For take user profile pic from gallery...
	 */

	private void openGallery() {

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, Common.REQUEST_CODE_GALLERY);
	}

	/**
	 * For crop image...
	 */
	private void startCropImage() {

		Intent intent = new Intent(this, CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 2);
		intent.putExtra(CropImage.ASPECT_Y, 2);

		startActivityForResult(intent, Common.REQUEST_CODE_CROP_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) {
			return;
		}

		Bitmap bitmap = null;
		Common.file_path = "";

		switch (requestCode) {

		case RESULT_CLOSE_ALL:

			setResult(RESULT_CLOSE_ALL);
			finish();
			break;

		case Common.REQUEST_CODE_GALLERY:

			try {

				InputStream inputStream = getContentResolver().openInputStream(
						data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Log.e("Vendor_Create_Coupon", "Error while creating temp file",
						e);
			}

			break;
		case Common.REQUEST_CODE_TAKE_PICTURE:

			startCropImage();

			break;
		case Common.REQUEST_CODE_CROP_IMAGE:

			Common.file_path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (Common.file_path == null) {

				return;
			}

			Common.file_path = Common.compressImage(Vendor_Create_Coupon.this,
					Common.file_path);

			bitmap = BitmapFactory.decodeFile(Common.file_path);
			img_coupon.setTag(Common.file_path);
			img_coupon.setImageBitmap(bitmap);

			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static void copyStream(InputStream input, OutputStream output)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_PICKER_START:
			return new DatePickerDialog(this, start_dateListener, mYear,
					mMonth, mDay);
		case DATE_PICKER_END:
			return new DatePickerDialog(this, end_dateListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private void Display_Date(int day, int month, int year, TextView txt_date) {
		// TODO Auto-generated method stub
		if (month < 10 && day < 10) {
			StringBuilder sb = new StringBuilder();
			// sb.append(0).append(day).append("-").append(0).append(month +
			// 1).append("-").append(year);
			sb.append(year).append("-").append(0).append(month + 1).append("-")
					.append(0).append(day);
			date_formate(sb.toString(), "yyyy-MM-dd", txt_date);
		} else if (month < 10) {
			StringBuilder sb = new StringBuilder();
			// sb.append(day).append("-").append(0).append(month +
			// 1).append("-").append(year);
			sb.append(year).append("-").append(0).append(month + 1).append("-")
					.append(day);
			date_formate(sb.toString(), "yyyy-MM-dd", txt_date);
		} else if (day < 10) {
			StringBuilder sb = new StringBuilder();
			// sb.append(0).append(day).append("-").append(month +
			// 1).append("-").append(year);
			sb.append(year).append("-").append(month + 1).append("-").append(0)
					.append(day);
			date_formate(sb.toString(), "yyyy-MM-dd", txt_date);
		} else {
			StringBuilder sb = new StringBuilder();
			// sb.append(day).append("-").append(month +
			// 1).append("-").append(year);
			sb.append(year).append("-").append(month + 1).append("-")
					.append(month + 1);
			date_formate(sb.toString(), "yyyy-MM-dd", txt_date);
		}
	}

	public void date_formate(String selectdate, String pre_date_string,
			TextView txt_date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pre_date_string);
		Date myDate = null;
		try {
			myDate = dateFormat.parse(selectdate);
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String finalDate = timeFormat.format(myDate);
			txt_date.setText(finalDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
