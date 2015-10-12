package com.wiredave.uvite.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wiredave.uvite.R;
import com.wiredave.uvite.bean.Promoter_Referred_Bean;

public class Promoter_Referred_Adapter extends BaseAdapter {

	private Context context;
	private int mLayoutId;
	ArrayList<Promoter_Referred_Bean> promoter_referred_coupon_array;	
	LayoutInflater linf = null;
	
	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	
	/**
	 * Constructor
	 */

	public Promoter_Referred_Adapter(Context context,int layoutId,
			ArrayList<Promoter_Referred_Bean> promoter_referred_coupon_array) {
		super();
		this.context = context;
		this.mLayoutId = layoutId;
		this.promoter_referred_coupon_array = promoter_referred_coupon_array;
		
		imageLoader = ImageLoader.getInstance();
			
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
			
			
	}// Promoter_Referred_Adapter

	public class Promoter_Referred_Holder {
		
		RelativeLayout rl_promotercoupons;
		ImageView img_coupon;
		TextView txt_status,txt_coupontitle,txt_coupondec,txt_expiredate,txt_availability,txt_referralcommission;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return promoter_referred_coupon_array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return promoter_referred_coupon_array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {

		Promoter_Referred_Holder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(mLayoutId,parent, false);
			
			holder = new Promoter_Referred_Holder();
		
			holder.rl_promotercoupons = (RelativeLayout)convertView.findViewById(R.id.rl_promotercoupons);
			
			holder.img_coupon = (ImageView)convertView.findViewById(R.id.img_coupon);			
					
			holder.txt_status = (TextView)convertView.findViewById(R.id.txt_status);
			holder.txt_coupontitle = (TextView)convertView.findViewById(R.id.txt_coupontitle);
			holder.txt_coupondec = (TextView)convertView.findViewById(R.id.txt_coupondec);
			holder.txt_expiredate = (TextView)convertView.findViewById(R.id.txt_expiredate);
			holder.txt_availability = (TextView)convertView.findViewById(R.id.txt_availability);
			holder.txt_referralcommission = (TextView)convertView.findViewById(R.id.txt_referralcommission);
			
			convertView.setTag(holder);
		} else {
			holder = (Promoter_Referred_Holder) convertView.getTag();
		}
		
		
		if(promoter_referred_coupon_array.get(position).getLogo() != null && !promoter_referred_coupon_array.get(position).getLogo().equals(""))					
		{
		  //download and display image from url
		  imageLoader.displayImage(promoter_referred_coupon_array.get(position).getLogo(), holder.img_coupon , options);
		}
				
		if(promoter_referred_coupon_array.get(position).getCoupon_subsribe().equals("0"))
		{
			holder.txt_availability.setVisibility(View.GONE);
		}else {
			holder.txt_availability.setVisibility(View.VISIBLE);
			holder.txt_availability.setText(context.getResources().getString(R.string.availability)+" "+promoter_referred_coupon_array.get(position).getCoupon_subsribe());
		}
		holder.txt_referralcommission.setText(context.getResources().getString(R.string.revcoupon_referral_commission)+" "+promoter_referred_coupon_array.get(position).getCommission());
		holder.txt_coupontitle.setText(promoter_referred_coupon_array.get(position).getCoupon_title());
		holder.txt_coupondec.setText(promoter_referred_coupon_array.get(position).getCoupon_description());
		holder.txt_expiredate.setText(promoter_referred_coupon_array.get(position).getEnd_date());				
		
		
		return convertView;
	} // getView

} // Promoter_Referred_Adapter
