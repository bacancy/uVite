package com.wiredave.uvite.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference_Login {

	public static final String PREFS_NAME = "Login";
	public static final String EMAIL_KEY = "Email";
	public static final String PASSWORD_KEY = "Password";
	public static final String REMEMBER_KEY = "Remember";
	public static final String LOGINTYPE_KEY = "LoginType";
	public static final String USERNAME = "UserName";
	SharedPreferences settings;
	Editor editor;
	private static SharedPreference_Login instance = null;

	public SharedPreference_Login(Context context) {
		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor = settings.edit();
	}

	/**
	 * 
	 * @param mContext
	 * @return {@link AppTypeDetails}
	 */
	public synchronized static SharedPreference_Login getInstance(
			Context mContext) {

		if (instance == null) {
			instance = new SharedPreference_Login(mContext);
		}
		return instance;
	}

	public void Set_Rememberme(Boolean flag) {

		editor.putBoolean(REMEMBER_KEY, flag);
		editor.commit();
	}

	public Boolean Get_Rememberme() {

		return settings.getBoolean(REMEMBER_KEY, false);

	}

	public void Set_Email(String text) {

		editor.putString(EMAIL_KEY, text);
		editor.commit();
	}

	public String Get_Email() {

		return settings.getString(EMAIL_KEY, "");

	}

	public void Set_UserName(String text) {

		editor.putString(USERNAME, text);
		editor.commit();
	}

	public String Get_UserName() {

		return settings.getString(USERNAME, "");

	}

	public void Set_Password(String text) {

		editor.putString(PASSWORD_KEY, text);
		editor.commit();
	}

	public String Get_Password() {

		return settings.getString(PASSWORD_KEY, "");

	}

	public void Set_LoginType(String text) {

		editor.putString(LOGINTYPE_KEY, text);
		editor.commit();
	}

	public String Get_LoginType() {

		return settings.getString(LOGINTYPE_KEY, "");

	}

	public void clearSharedPreference() {

		editor.clear();
		editor.commit();
	}

	public void removeValue() {

		editor.remove(EMAIL_KEY);
		editor.remove(PASSWORD_KEY);
		editor.remove(REMEMBER_KEY);
		editor.remove(LOGINTYPE_KEY);
		editor.commit();
	}
}
