package com.wiredave.uvite.registration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.login.Login_Screen;

import eu.janmuller.android.simplecropimage.CropImage;

public class Promoter_Registration extends FragmentActivity implements
		OnClickListener {

	TextView txt_title;
	public static RelativeLayout rl_back, rl_promoters_contact, rl_promoters_paypal;
	Promoter_Contact_Details_Fragment promoter_contact_details_fragment;
	Promoter_Paypal_Details_Fragment promoter_paypal_details_fragment;
	public static String flag = "", back_flag = "", login_type = "";
	Bundle bundle = null;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (back_flag.equals("registration")) {
				startActivity(new Intent(Promoter_Registration.this,
						Login_Screen.class).putExtra("login_type",
						login_type));
			}
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Common.clear_allpromoterregistration();
		Log.d("ondestroy", "destroy");
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promoter_registration);

		initialization(); // initialize all objects of view...

		if (Common.ref_database == null)
			Common.ref_database = new Referral_Database(
					Promoter_Registration.this);

		try {
			bundle = getIntent().getExtras();
			if (bundle != null) {
				login_type = bundle.getString("login_type");
				flag = bundle.getString("promoter_registration_flag");
				back_flag = bundle.getString("promoter_registration_backflag");

			} else {

				flag = "";
				back_flag = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = "";
			back_flag = "";
		}

		if (flag.equals("insert")) {
			txt_title.setText(this.getResources().getString(
					R.string.promoters_registration));
		} else if (flag.equals("update")) {

			txt_title.setText(this.getResources().getString(
					R.string.update_promoter_registration));
		}

		// Master data Fragments
		promoter_contact_details_fragment = new Promoter_Contact_Details_Fragment();
		promoter_paypal_details_fragment = new Promoter_Paypal_Details_Fragment();

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container,
				promoter_contact_details_fragment);

		fragmentTransaction.commit();

		rl_back.setOnClickListener(this);
		rl_promoters_contact.setOnClickListener(this);
		rl_promoters_paypal.setOnClickListener(this);

		rl_promoters_contact.setBackgroundResource(R.color.selected_tab);
		rl_promoters_paypal.setBackgroundResource(R.color.unselected_tab);

	}

	public void initialization() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);

		txt_title = (TextView) findViewById(R.id.txt_title);
		rl_promoters_contact = (RelativeLayout) findViewById(R.id.rl_promoters_contact);
		rl_promoters_paypal = (RelativeLayout) findViewById(R.id.rl_promoters_paypal);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.rl_back) {
			if (back_flag.equals("registration")) {
				startActivity(new Intent(Promoter_Registration.this,
						Login_Screen.class).putExtra("login_type",
						login_type));
			}
			overridePendingTransition(0, 0);
			finish();

		} else if (v.getId() == R.id.rl_promoters_contact) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container,
					promoter_contact_details_fragment);

			fragmentTransaction.commit();

			rl_promoters_contact.setBackgroundResource(R.color.selected_tab);
			rl_promoters_paypal.setBackgroundResource(R.color.unselected_tab);

		} else if (v.getId() == R.id.rl_promoters_paypal) {
			if (Common.flag_promoter_contact_registration) {

				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.fragment_container,
						promoter_paypal_details_fragment);

				fragmentTransaction.commit();

				rl_promoters_contact
						.setBackgroundResource(R.color.unselected_tab);
				rl_promoters_paypal.setBackgroundResource(R.color.selected_tab);

			} else {

				Common.showalertDialog(Promoter_Registration.this,
						getResources().getString(R.string.toast_movenext_form));
			}

		}
	}

	/**
	 * For crop image...
	 */
	private void startCropImage() {

		Intent intent = new Intent(this, CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH,
				Promoter_Contact_Details_Fragment.mFileTemp.getPath());
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

		case Common.REQUEST_CODE_GALLERY:

			try {

				InputStream inputStream = getContentResolver().openInputStream(
						data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						Promoter_Contact_Details_Fragment.mFileTemp);
				copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Log.e("Signup", "Error while creating temp file", e);
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

			Common.file_path = Common.compressImage(Promoter_Registration.this,
					Common.file_path);

			bitmap = BitmapFactory.decodeFile(Common.file_path);
			Promoter_Contact_Details_Fragment.img_promoterprofile
					.setTag(Common.file_path);
			Promoter_Contact_Details_Fragment.img_promoterprofile
					.setImageBitmap(bitmap);
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

}
