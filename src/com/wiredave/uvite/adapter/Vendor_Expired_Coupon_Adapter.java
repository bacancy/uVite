package com.wiredave.uvite.adapter;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.AuthorizeNet_CreditCard_Task;
import com.wiredave.uvite.asynctask.Delete_Vendor_Coupon_Task;
import com.wiredave.uvite.bean.Vendor_Expired_Coupon_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.vendor.Vendor_Create_Coupon;

public class Vendor_Expired_Coupon_Adapter extends BaseAdapter {

	private Context context;
	private int mLayoutId;
	ArrayList<Vendor_Expired_Coupon_Bean> vendor_expired_coupon_array;	
	LayoutInflater linf = null;
	
	/**
	 * Constructor
	 */

	public Vendor_Expired_Coupon_Adapter(Context context,int layoutId,
			ArrayList<Vendor_Expired_Coupon_Bean> vendor_expired_coupon_array) {
		super();
		this.context = context;
		this.mLayoutId = layoutId;
		this.vendor_expired_coupon_array = vendor_expired_coupon_array;
	}// Vendor_Expired_Coupon_Adapter

	public class Vendor_ExpiredCoupon_Holder {
		
		RelativeLayout rl_vendorcoupons;
		ImageView img_coupon, txt_edit,txt_delete;
		TextView txt_availability,txt_referralcommission,
                 txt_coupontitle,txt_coupondec,txt_expiredate,txt_couponreferred,
                 txt_couponused;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vendor_expired_coupon_array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return vendor_expired_coupon_array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {

		Vendor_ExpiredCoupon_Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(mLayoutId,parent, false);
			
			holder = new Vendor_ExpiredCoupon_Holder();
		
			holder.rl_vendorcoupons = (RelativeLayout)convertView.findViewById(R.id.rl_vendorcoupons);
			
			holder.img_coupon = (ImageView)convertView.findViewById(R.id.img_coupon);
			
			holder.txt_edit = (ImageView)convertView.findViewById(R.id.txt_edit);
			holder.txt_delete = (ImageView)convertView.findViewById(R.id.txt_delete);
			holder.txt_availability = (TextView)convertView.findViewById(R.id.txt_availability);
			holder.txt_referralcommission = (TextView)convertView.findViewById(R.id.txt_referralcommission);
			holder.txt_coupontitle = (TextView)convertView.findViewById(R.id.txt_coupontitle);
		/*	holder.txt_coupondec = (TextView)convertView.findViewById(R.id.txt_coupondec);*/
			holder.txt_expiredate = (TextView)convertView.findViewById(R.id.txt_expiredate);
			/*holder.txt_couponreferred = (TextView)convertView.findViewById(R.id.txt_couponreferred);*/
			holder.txt_couponused = (TextView)convertView.findViewById(R.id.txt_couponused);
			
			convertView.setTag(holder);
		} else {
			holder = (Vendor_ExpiredCoupon_Holder) convertView.getTag();
		}

		holder.txt_availability.setText(context.getResources().getString(R.string.availability)+" "+vendor_expired_coupon_array.get(position).getAvailability());
		holder.txt_referralcommission.setText(context.getResources().getString(R.string.revcoupon_referral_commission)+" "+vendor_expired_coupon_array.get(position).getCommission());
		holder.txt_coupontitle.setText(vendor_expired_coupon_array.get(position).getCoupon_title());
		//holder.txt_coupondec.setText(vendor_expired_coupon_array.get(position).getCoupon_description());
		holder.txt_expiredate.setText(vendor_expired_coupon_array.get(position).getEnd_date());		
		//holder.txt_couponreferred.setText(context.getResources().getString(R.string.coupon_referred)+" "+vendor_expired_coupon_array.get(position).getCoupon_referred());
		holder.txt_couponused.setText(context.getResources().getString(R.string.coupon_used)+" "+vendor_expired_coupon_array.get(position).getCoupon_used());
			
        holder.txt_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				((Activity)context).startActivity(new Intent(context,Vendor_Create_Coupon.class).putExtra("vendor_type", "expired").putExtra("vendor_create_coupon_flag", "update").putExtra("val_position", position));
				((Activity)context).overridePendingTransition(0, 0);
			}
		});	
		
        holder.txt_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 //check internet connetivity...
				 if(Common.isConnectingToInternet(context))
				 {
					//delete active coupon...
					new Delete_Vendor_Coupon_Task(context, vendor_expired_coupon_array.get(position).getCoupon_id(),"expired").execute(); 
				 }else {		
					  Common.showalertDialog(context, context.getResources().getString(R.string.alert_internetconnectivity));
				 }
			}
		});
        
		return convertView;
	} // getView

} // Vendor_Expired_Coupon_Adapter
