package com.wiredave.uvite.adapter;

import java.util.ArrayList;

import android.content.Context;
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
import com.wiredave.uvite.asynctask.Promoter_Favorite_Coupon_Task;
import com.wiredave.uvite.bean.Promotor_Favorite_Bean;
import com.wiredave.uvite.common.Common;

public class Promoter_Favorites_Adapter extends BaseAdapter{
	
	private Context context;
	private int mLayoutId;
	ArrayList<Promotor_Favorite_Bean> promoter_favorite_coupon_array;	
	LayoutInflater linf = null;
	
	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	
	
	public Promoter_Favorites_Adapter(Context context,int layoutId,
			ArrayList<Promotor_Favorite_Bean> promoter_favorite_coupon_array) {
		super();
		this.context = context;
		this.mLayoutId = layoutId;
		this.promoter_favorite_coupon_array = promoter_favorite_coupon_array;
		
		imageLoader = ImageLoader.getInstance();
			
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
			
			
	}// Promoter_Referred_Adapter

	public class Promoter_Favorite_Holder {
		
		RelativeLayout rl_favoritecoupons;
		ImageView img_coupon;
		TextView txt_unfavorite,txt_coupontitle,txt_coupondec,txt_expiredate,txt_availability,txt_referralcommission;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return promoter_favorite_coupon_array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return promoter_favorite_coupon_array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Promoter_Favorite_Holder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(mLayoutId,parent, false);
			
			holder = new Promoter_Favorite_Holder();
		
			holder.rl_favoritecoupons = (RelativeLayout)convertView.findViewById(R.id.rl_favoritecoupons);
			
			holder.img_coupon = (ImageView)convertView.findViewById(R.id.img_coupon);			
					
			holder.txt_unfavorite = (TextView)convertView.findViewById(R.id.txt_unfavorite);
			holder.txt_coupontitle = (TextView)convertView.findViewById(R.id.txt_coupontitle);
			holder.txt_coupondec = (TextView)convertView.findViewById(R.id.txt_coupondec);
			holder.txt_expiredate = (TextView)convertView.findViewById(R.id.txt_expiredate);
			holder.txt_availability = (TextView)convertView.findViewById(R.id.txt_availability);
			holder.txt_referralcommission = (TextView)convertView.findViewById(R.id.txt_referralcommission);
			
			convertView.setTag(holder);
		} else {
			holder = (Promoter_Favorite_Holder) convertView.getTag();
		}
		
		
		if(promoter_favorite_coupon_array.get(position).getLogo() != null && !promoter_favorite_coupon_array.get(position).getLogo().equals(""))					
		{
		  //download and display image from url
		  imageLoader.displayImage(promoter_favorite_coupon_array.get(position).getLogo(), holder.img_coupon , options);
		}
				
		if(promoter_favorite_coupon_array.get(position).getCoupon_subsribe().equals("0"))
		{
			holder.txt_availability.setVisibility(View.GONE);
		}else {
			holder.txt_availability.setVisibility(View.VISIBLE);
			holder.txt_availability.setText(context.getResources().getString(R.string.availability)+" "+promoter_favorite_coupon_array.get(position).getCoupon_subsribe());
		}
		holder.txt_referralcommission.setText(context.getResources().getString(R.string.revcoupon_referral_commission)+" "+promoter_favorite_coupon_array.get(position).getCommission());
		holder.txt_coupontitle.setText(promoter_favorite_coupon_array.get(position).getCoupon_title());
		holder.txt_coupondec.setText(promoter_favorite_coupon_array.get(position).getCoupon_description());
		holder.txt_expiredate.setText(promoter_favorite_coupon_array.get(position).getEnd_date());	
		
		holder.txt_unfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Common.isConnectingToInternet(context))
				  {
					 //call this for remove coupon to favorite list...
					 new Promoter_Favorite_Coupon_Task(context, promoter_favorite_coupon_array.get(position).getCoupon_id(), "unfavorite").execute(); 
				  }else {		
					  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
				  }
			}
		});
		
		
		return convertView;
	}

}
