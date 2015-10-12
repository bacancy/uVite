package com.wiredave.uvite.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference_Promoter {
	 
    public static final String PREFS_NAME = "Promoter_Coupon";
    public static final String COUPON_TYPE = "Coupon_Type";    
    SharedPreferences settings;
    Editor editor;
    private static SharedPreference_Promoter instance = null;
    
    public SharedPreference_Promoter(Context context) {
    	settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }
 
    /**
	 * 
	 * @param mContext
	 * @return {@link AppTypeDetails}
	 */
	public synchronized static SharedPreference_Promoter getInstance(Context mContext) {

		if (instance == null) {
			instance = new SharedPreference_Promoter(mContext);
		}
		return instance;
	}
	
    
    
    public void Set_Promoter_Coupon(String text) {
        
        editor.putString(COUPON_TYPE, text); 
        editor.commit(); 
    }
 
    public String Get_Promoter_Coupon() {        
                 
    	return settings.getString(COUPON_TYPE, "allactive");
         
    }
   
   
    public void clearSharedPreference() {
         
        editor.clear();
        editor.commit();
    }
 
    public void removeValue() {
        
        editor.remove(COUPON_TYPE);        
        editor.commit();
    }    
}
