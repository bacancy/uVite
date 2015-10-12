package com.wiredave.uvite.adapter;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.AuthorizeNet_CreditCard_Task;
import com.wiredave.uvite.asynctask.Promoter_Favorite_Coupon_Task;
import com.wiredave.uvite.asynctask.Promoter_Referred_Coupon_Task;
import com.wiredave.uvite.asynctask.Promotor_Subscribed_Coupon_Task;
import com.wiredave.uvite.asynctask.Refer_Coupon_Task;
import com.wiredave.uvite.bean.Promoter_AllActive_Coupon_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_Refer_Coupon;
import com.wiredave.uvite.vendor.Vendor_Authorized_CreditCard_Payment;

public class Promoter_AllActive_Coupon_Adapter extends BaseAdapter {

	private Context context;
	private int mLayoutId;
	ArrayList<Promoter_AllActive_Coupon_Bean> promoter_allactive_coupon_array;	
	LayoutInflater linf = null;
	
	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	
	/**
	 * Constructor
	 */

	public Promoter_AllActive_Coupon_Adapter(Context context,int layoutId,
			ArrayList<Promoter_AllActive_Coupon_Bean> promoter_allactive_coupon_array) {
		super();
		this.context = context;
		this.mLayoutId = layoutId;
		this.promoter_allactive_coupon_array = promoter_allactive_coupon_array;
		
	    imageLoader = ImageLoader.getInstance();
				
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
		
	}// Promoter_AllActive_Coupon_Adapter

	public class Promoter_AllActiveCoupon_Holder {
		
		RelativeLayout rl_promotercoupons;
		ImageView img_coupon,img_fav;
		TextView txt_refercoupon,txt_availability,txt_referralcommission,txt_subscribe,
		         txt_coupontitle,txt_coupondec,txt_expiredate;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return promoter_allactive_coupon_array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return promoter_allactive_coupon_array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Promoter_AllActiveCoupon_Holder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(mLayoutId,parent, false);
			
			holder = new Promoter_AllActiveCoupon_Holder();
		
			holder.rl_promotercoupons = (RelativeLayout)convertView.findViewById(R.id.rl_promotercoupons);
			
			holder.img_coupon = (ImageView)convertView.findViewById(R.id.img_coupon);
			holder.img_fav = (ImageView)convertView.findViewById(R.id.img_fav);
			
			holder.txt_refercoupon = (TextView)convertView.findViewById(R.id.txt_refercoupon);
			holder.txt_availability = (TextView)convertView.findViewById(R.id.txt_availability);
			holder.txt_referralcommission = (TextView)convertView.findViewById(R.id.txt_referralcommission);
			holder.txt_coupontitle = (TextView)convertView.findViewById(R.id.txt_coupontitle);
			holder.txt_subscribe=(TextView)convertView.findViewById(R.id.txt_subscribe);
			holder.txt_coupondec = (TextView)convertView.findViewById(R.id.txt_coupondec);
			holder.txt_expiredate = (TextView)convertView.findViewById(R.id.txt_expiredate);
			
			convertView.setTag(holder);
		} else {
			holder = (Promoter_AllActiveCoupon_Holder) convertView.getTag();
		}
		
		if(promoter_allactive_coupon_array.get(position).getLogo() != null && !promoter_allactive_coupon_array.get(position).getLogo().equals(""))					
		{
		  //download and display image from url
		  imageLoader.displayImage(promoter_allactive_coupon_array.get(position).getLogo(), holder.img_coupon , options);
		}
		
		holder.txt_availability.setText(context.getResources().getString(R.string.availability)+" "+promoter_allactive_coupon_array.get(position).getAvailability());
		holder.txt_referralcommission.setText(context.getResources().getString(R.string.revcoupon_referral_commission)+" "+promoter_allactive_coupon_array.get(position).getCommission());
		holder.txt_coupontitle.setText(promoter_allactive_coupon_array.get(position).getCoupon_title());
		holder.txt_coupondec.setText(promoter_allactive_coupon_array.get(position).getCoupon_description());
		holder.txt_expiredate.setText(promoter_allactive_coupon_array.get(position).getEnd_date());				
		
		holder.img_fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 //check internet connetivity...
				if(holder.img_fav.getTag().toString().equals("dislike")){
					if(Common.isConnectingToInternet(context))
					  {
						 holder.img_fav.setBackgroundResource(R.drawable.fav_like);
						 
						 //call this for add coupon to favorite list...
						 new Promoter_Favorite_Coupon_Task(context, promoter_allactive_coupon_array.get(position).getCoupon_id(), "favorite").execute(); 
					  }else {		
						  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
					  }
					holder.img_fav.setTag("like");
				}else if(holder.img_fav.getTag().toString().equals("like")){
					if(Common.isConnectingToInternet(context))
					  {
						 holder.img_fav.setBackgroundResource(R.drawable.fav_dislike);
						 
						 //call this for add coupon to favorite list...
						 new Promoter_Favorite_Coupon_Task(context, promoter_allactive_coupon_array.get(position).getCoupon_id(), "unfavorite").execute(); 
					  }else {		
						  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
					  }
					holder.img_fav.setTag("dislike");
				}
				 
			}
		});
		holder.txt_refercoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//check internet connetivity...
				 if(Common.isConnectingToInternet(context))
				  {
					//call to get promoter referred coupons...
					new Refer_Coupon_Task(context, Common.ref_database.getEmail(), promoter_allactive_coupon_array.get(position).getCoupon_id()).execute();

				  }else {		
					  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
				  }
			}
		});
		
		holder.txt_subscribe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//check internet connetivity...
				 if(Common.isConnectingToInternet(context))
				  {
					//call to get promoter referred coupons...
//					new Refer_Coupon_Task(context, Common.ref_database.getEmail(), promoter_allactive_coupon_array.get(position).getCoupon_id()).execute();
					new Promotor_Subscribed_Coupon_Task(context, promoter_allactive_coupon_array.get(position).getCoupon_id()).execute();
				  }else {		
					  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
				  }
			}
		});
		
		
		holder.rl_promotercoupons.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				((Activity)context).startActivity(new Intent(context,Promoter_Refer_Coupon.class).putExtra("coupon_position", position));
				((Activity)context).overridePendingTransition(0, 0);
				
			}
		});
		
		return convertView;
	} // getView

} // Promoter_AllActive_Coupon_Adapter
