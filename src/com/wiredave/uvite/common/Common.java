package com.wiredave.uvite.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wiredave.uvite.R;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.sharedpreference.SharedPreference_Login;

public class Common {

	public static Referral_Database ref_database;
	public static SharedPreference_Login sharedpreference_login;
	public static Pattern pattern;
	public static Matcher matcher;

	public static String device_type = "0", device_token = "", firstname = "",
			lastname = "", emailid = "", phonenumber = "", contactaddress = "",
			address1 = "", address2 = "", primaryphone = "", fax = "",
			primaryemail = "", primarycontact = "", additionalcontact = "",
			password = "", username = "", country = "", state = "", city = "",
			website = "";

	public static String paypal_id = "";

	public static String cardnumber = "", cvv = "", expiration_date = "",
			expiration_month = "", expiration_year = "";

	public static String coupon_title = "", coupon_description = "",
			discount_details = "", category = "", discount_type = "",
			start_date = "", end_date = "", referral_commission = "0",
			number_of_coupons = "0", total_amount = "0", payment_array = "";

	public static String total_coupon = "0", total_referred = "0",
			total_redeemed = "0";

	public static int start_year = 0, start_month = 0, start_day = 0,
			end_year = 0, end_month = 0, end_day = 0;

	public static final String TEMP_PHOTO_FILE_NAME = "crop_image.jpg";
	public static final int REQUEST_CODE_GALLERY = 0x1;
	public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
	public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
	public static final int PICK_FROM_CAMERA = 0;
	public static final int PICK_FROM_FILE = 0;
	public static final int CROP_FROM_CAMERA = 2;
	public static String file_path = "";

	public static boolean flag_vendor_contact_registration = false;
	public static boolean flag_vendor_business_registration = false;
	public static boolean flag_vendor_payment_registration = false;

	public static boolean flag_promoter_contact_registration = false;

	public static void clear_allvendorregistration() {
		flag_vendor_contact_registration = false;
		flag_vendor_business_registration = false;
		flag_vendor_payment_registration = false;

		device_token = "";
		firstname = "";
		lastname = "";
		emailid = "";
		phonenumber = "";
		contactaddress = "";
		address1 = "";
		address2 = "";
		primaryphone = "";
		fax = "";
		primaryemail = "";
		primarycontact = "";
		additionalcontact = "";
		password = "";
		username = "";
		country = "";
		state = "";
		city = "";
		website = "";
		cardnumber = "";
		cvv = "";
		expiration_month = "";
		expiration_year = "";
	}

	public static void clear_allpromoterregistration() {
		flag_promoter_contact_registration = false;

		device_token = "";
		firstname = "";
		lastname = "";
		emailid = "";
		phonenumber = "";
		contactaddress = "";
		password = "";
		username = "";
		paypal_id = "";

	}

	public static void clear_allcreatevendorcoupon() {

		coupon_title = "";
		coupon_description = "";
		category = "";
		discount_type = "";
		start_date = "";
		end_date = "";
		referral_commission = "0";
		number_of_coupons = "0";

	}

	// check internet connectivity
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";

	/**
	 * Validate username with regular expression
	 * 
	 * @param username
	 *            for validation
	 * @return true valid username, false invalid username
	 */
	public static boolean isValidPassword(final String password) {

		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();

	}

	// validating email id
	public static boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static String getDeviceUniqueID(Context context) {
		String device_unique_id = Secure.getString(
				context.getContentResolver(), Secure.ANDROID_ID);
		if (device_unique_id == null) {
			return "";
		}
		Log.d("getDeviceUniqueID", " - " + device_unique_id);
		return device_unique_id;
	}

	/*
	 * // validating password with retype password public static boolean
	 * isValidPassword(String pass) { if (pass != null && pass.length() > 6) {
	 * return true; } return false; }
	 */

	// private method of your class
	public static int getIndex(ArrayAdapter<CharSequence> adpater,
			String myString) {
		int index = 0;

		index = adpater.getPosition(myString);

		return index + 1;
	}

	public static String maskNumber(String cardNumber, String mask) {

		// format the number
		int index = 0;
		StringBuilder maskedNumber = new StringBuilder();
		for (int i = 0; i < mask.length(); i++) {
			char c = mask.charAt(i);
			if (c == '#') {
				maskedNumber.append(cardNumber.charAt(index));
				index++;
			} else if (c == 'x') {
				maskedNumber.append(c);
				index++;
			} else {
				maskedNumber.append(c);
			}
		}

		// return the masked number
		return maskedNumber.toString();
	}

	public static boolean isDateBefore(String startDate, String endDate) {
		try {

			long yourmilliseconds = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date resultdate = new Date(yourmilliseconds);
			System.out.println(sdf.format(resultdate));
			Date startingDate = sdf.parse(startDate);
			System.out.println(sdf.format(startingDate).toUpperCase());

			Log.d("beforestartingdate", "" + resultdate.before(startingDate));
			Log.d("resultdate.getTime()-startingDate.getTime()", ""
					+ (resultdate.getTime() - startingDate.getTime()));
			// return resultdate.before(startingDate);
			Log.d("startingDate.compareTo(resultdate)",
					"" + resultdate.compareTo(startingDate));

			String currentdate = sdf.format(resultdate);
			String startdate = sdf.format(startingDate);

			if (currentdate.equals(startdate)) {

				Log.d("equals", "equals");
				return true;
			} else {
				if (startingDate.before(resultdate)) {
					Log.d("less", "less");
					return false;
				} else {
					Log.d("greater", "greater");
					return true;
				}
			}

			// return false;
			// return false;
		}

		catch (Exception e) {
			return false;
		}
	}

	public static boolean isDateAfter(String startDate, String endDate) {
		try {
			String myFormatString = "yyyy-MM-dd"; // for example
			SimpleDateFormat df = new SimpleDateFormat(myFormatString);
			Date date1 = df.parse(endDate);
			Date startingDate = df.parse(startDate);

			if (date1.after(startingDate)) {
				return true;

			} else {
				return false;
			}
		}

		catch (Exception e) {
			return false;
		}
	}

	public static String set_FormatCardNumber(String t, String s, int num) {
		StringBuilder retVal;

		if (null == s || 0 >= num) {
			throw new IllegalArgumentException("Don't be silly");
		}

		if (s.length() <= num) {
			// String to small, do nothing
			return s;
		}

		retVal = new StringBuilder(s);

		for (int i = retVal.length(); i > 0; i -= num) {
			retVal.insert(i, t);
		}
		return retVal.toString();
	}

	// show alert dialog
	public static void showalertDialog(Context context, String message) {
		AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			alertdialog = new AlertDialog.Builder(new ContextThemeWrapper(
					context, android.R.style.Theme_Holo_Light_Dialog));
		} else {
			alertdialog = new AlertDialog.Builder(context);
		}

		// set icon
		alertdialog.setIcon(android.R.drawable.ic_dialog_alert);

		// set title
		alertdialog.setTitle(android.R.string.dialog_alert_title);

		// set message
		alertdialog.setMessage(message);

		alertdialog.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						dialog.dismiss();
					}
				});

		alertdialog.show();
	}

	public static void show_CustomAlertDialog(Context context, String message) {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom_alertdialog);

		// set the custom dialog components - text
		TextView text = (TextView) dialog.findViewById(R.id.txt_message);
		text.setText(message);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static String getFilename() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), "uVite/Images");
		if (!file.exists()) {
			file.mkdirs();
		}
		String uriSting = (file.getAbsolutePath() + "/"
				+ System.currentTimeMillis() + ".jpg");
		return uriSting;

	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

	public static Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	public static String getRealPathFromURI(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	public static String getRealPathFromURI(Context context, String contentURI) {
		Uri contentUri = Uri.parse(contentURI);
		Cursor cursor = context.getContentResolver().query(contentUri, null,
				null, null, null);
		if (cursor == null) {
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int index = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(index);
		}
	}

	public static String compressImage(Context context, String imageUri) {

		String filePath = getRealPathFromURI(context, imageUri);
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		// by setting this field as true, the actual bitmap pixels are not
		// loaded in the memory. Just the bounds are loaded. If
		// you try the use the bitmap here, you will get null.
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		// max Height and width values of the compressed image is taken as
		// 816x612

		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		// width and height values are set maintaining the aspect ratio of the
		// image

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		// setting inSampleSize value allows to load a scaled down version of
		// the original image

		options.inSampleSize = calculateInSampleSize(options, actualWidth,
				actualHeight);

		// inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;

		// this options allow android to claim the bitmap memory if it runs low
		// on memory
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			// load the bitmap from its path
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
				middleY - bmp.getHeight() / 2, new Paint(
						Paint.FILTER_BITMAP_FLAG));

		// check the rotation of the image and display it properly
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileOutputStream out = null;
		String filename = getFilename();
		try {
			out = new FileOutputStream(filename);

			// write the compressed bitmap at the destination specified by
			// filename.
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return filename;

	}

	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
