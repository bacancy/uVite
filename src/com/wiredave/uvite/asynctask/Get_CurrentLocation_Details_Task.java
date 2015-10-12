package com.wiredave.uvite.asynctask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.util.Log;

import com.wiredave.uvite.R;
import com.wiredave.uvite.common.Common;


public class Get_CurrentLocation_Details_Task extends AsyncTask<String, Void , String> {
	
	private Context mContext;
	ProgressDialog pd; 
	String result = "" , loc_address = "" , street_number = "" , route = "" , sublocality = "";
	int fetch_flag = 0;
	long latitute = 0 , longitute = 0 ;
	String TAG="Get_CurrentLocation_Details_Task";
	
	/**
	 * @author Jay Patel
	 * Called this to Current Location Details.
	 */
	
    public Get_CurrentLocation_Details_Task (Context context,long latitute ,long longitute){
    	
         this.mContext = context;
         this.latitute = latitute;
         this.longitute = longitute;
         
    }
    
	@Override
    protected void onPreExecute() {
		   
		   pd = new ProgressDialog(mContext);
		   pd.setMessage(mContext.getResources().getString(R.string.please_wait));
	       pd.show();
    }
	
	  @Override
	  protected String doInBackground(String... urls) {
		  
		  String full_address = null;                          
          loc_address = "";    
          
          try
          {
          String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitute + ","
                      + longitute + "&sensor=false";
          /*String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + 23.050239 + ","
                  + 72.50451 + "&sensor=false";*/
          	if(googleMapUrl == null)
          	{
          		googleMapUrl = "";
          	}
          	HttpClient httpClient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(googleMapUrl);	    			    			    					
	        HttpResponse response = httpClient.execute(httppost);
		    HttpEntity responseEntity = response.getEntity();
			String res = EntityUtils.toString(responseEntity);
			Log.d("Api_res", "All locations : "+res);				

            JSONObject jsonObject = new JSONObject(res);                   
            if(jsonObject.getString("status").equalsIgnoreCase("OK"))
			  {
            	full_address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
				   if(full_address == null)
             	    {                   		   
                      loc_address = "";                   		      
             	   }else {
                      loc_address = full_address;
					 }                       
				JSONArray results = (JSONArray) jsonObject.get("results");
                   for (int i = 0; i < results.length(); i++)
                   {
                       // loop among all addresses within this result
                       JSONObject result = results.getJSONObject(i);
                       if (result.has("address_components"))
                       {
                           JSONArray addressComponents = result.getJSONArray("address_components");
                           // loop among all address component to find a 'locality' or 'sublocality'
                           for (int j = 0; j < addressComponents.length(); j++)
                           {
                               JSONObject addressComponent = addressComponents.getJSONObject(j);
                               if (result.has("types"))
                               {
                                   JSONArray types = addressComponent.getJSONArray("types");

                                   // search for locality and sublocality                                  
                                   for (int k = 0; k < types.length(); k++)
                                   {
                                  	 if ("street_number".equals(types.getString(k)))
	                                        {
	                                            if (addressComponent.has("long_name"))
	                                            {
	                                            	street_number = addressComponent.getString("long_name");
	                                            	street_number = street_number+",";
	                                                Log.d("street_number", street_number);
	                                            }
	                                            else if (addressComponent.has("short_name"))
	                                            {
	                                            	street_number = addressComponent.getString("short_name");
	                                            	street_number = street_number+",";
	                                            	Log.d("street_number", street_number);
	                                            }else {
	                                            	street_number = "";
												}
	                                        }
                               	 
                                   if ("route".equals(types.getString(k)))
                                   {
                                       if (addressComponent.has("long_name"))
                                       {
                                       	route = addressComponent.getString("long_name");
                                       	route = route+",";
                                           Log.d("route", route);
                                       }
                                       else if (addressComponent.has("short_name"))
                                       {
                                       	route = addressComponent.getString("short_name");
                                       	route = route+",";
                                       	Log.d("route", route);
                                       }else {
                                       	route = "";
											}
                                   }
                                  
                                   if ("sublocality".equals(types.getString(k)))
                                   {
                                       if (addressComponent.has("long_name"))
                                       {
                                       	sublocality = addressComponent.getString("long_name");
                                           Log.d("sublocality", sublocality);
                                       }
                                       else if (addressComponent.has("short_name"))
                                       {
                                       	sublocality = addressComponent.getString("short_name");
                                       	Log.d("sublocality", sublocality);
                                       }else {
                                       	sublocality = "";
										 }
                                      }
                                   }                                   
                               }
                           }
                         
                       }
                   }
                 
                   fetch_flag = 1;
			  }else {
				 fetch_flag = 0;
			}
                              
          }
          catch (Exception ignored)
          {
              ignored.printStackTrace();
              fetch_flag = 0;
          }
          return "";
	  }
	  @Override
	  protected void onPostExecute(String result) 
	  {
		  if (pd != null && pd.isShowing()) {
	            pd.dismiss();
	        }
				try {
					if(fetch_flag == 1)
	            	{            		    		            		
	            		
	            	}else {
	            		            	          	
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Common.showalertDialog(mContext, mContext.getResources().getString(R.string.app_crash));
									
				}	
		   
		  
	  }
		 }
