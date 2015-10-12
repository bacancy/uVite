package com.wiredave.uvite.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference_Filter {
	 
    public static final String PREFS_NAME = "Filter";
    public static final String FILTER_TYPE = "Filter_Type";    
    SharedPreferences settings;
    Editor editor;
    private static SharedPreference_Filter instance = null;
    
    public SharedPreference_Filter(Context context) {
    	settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }
 
    /**
	 * 
	 * @param mContext
	 * @return {@link AppTypeDetails}
	 */
	public synchronized static SharedPreference_Filter getInstance(Context mContext) {

		if (instance == null) {
			instance = new SharedPreference_Filter(mContext);
		}
		return instance;
	}
	
    
    public void Set_Filter_Type(String text) {
        
        editor.putString(FILTER_TYPE, text); 
        editor.commit(); 
    }
 
    public String Get_Filter_Type() {        
                 
    	return settings.getString(FILTER_TYPE, "category");
    }
   
   
    public void clearSharedPreference() {
         
        editor.clear();
        editor.commit();
    }
 
    public void removeValue() {
        
        editor.remove(FILTER_TYPE);        
        editor.commit();
    }    
}
