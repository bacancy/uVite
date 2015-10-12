package com.wiredave.uvite.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wiredave.uvite.R;
import com.wiredave.uvite.asynctask.Refer_Coupon_Task;
import com.wiredave.uvite.bean.Promoter_MyCoupon_Bean;
import com.wiredave.uvite.common.Common;
import com.wiredave.uvite.common.CommonUtil;
import com.wiredave.uvite.database.Referral_Database;
import com.wiredave.uvite.promoter.Promoter_Refer_Coupon;

public class Promoter_MyCoupon_Adapter extends BaseAdapter {

	private Context context;
	private int mLayoutId;
	ArrayList<Promoter_MyCoupon_Bean> promoter_mycoupon_array;	
	LayoutInflater linf = null;
	ImageLoader imageLoader = null;
	private DisplayImageOptions options;
	
	/**
	 * Constructor
	 */

	public Promoter_MyCoupon_Adapter(Context context,int layoutId,
			ArrayList<Promoter_MyCoupon_Bean> promoter_mycoupon_array) {
		super();
		this.context = context;
		this.mLayoutId = layoutId;
		this.promoter_mycoupon_array = promoter_mycoupon_array;
		
		imageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true).build();
	}// Promoter_MyCoupon_Adapter

	public class Promoter_MyCoupon_Holder {
		
		RelativeLayout rl_promotercoupons;
		ImageView img_coupon,img_qrcode;
		TextView txt_coupontitle,txt_coupondec,txt_expiredate,tv_reffered,tv_favorite;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return promoter_mycoupon_array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return promoter_mycoupon_array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {

		Promoter_MyCoupon_Holder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(mLayoutId,parent, false);
			
			holder = new Promoter_MyCoupon_Holder();
		
			holder.rl_promotercoupons = (RelativeLayout)convertView.findViewById(R.id.rl_promotercoupons);
			
			holder.img_coupon = (ImageView)convertView.findViewById(R.id.img_coupon);
			holder.img_qrcode = (ImageView)convertView.findViewById(R.id.img_qrcode);
								
			holder.txt_coupontitle = (TextView)convertView.findViewById(R.id.txt_coupontitle);
			holder.txt_coupondec = (TextView)convertView.findViewById(R.id.txt_coupondec);
			holder.txt_expiredate = (TextView)convertView.findViewById(R.id.txt_expiredate);
		
			convertView.setTag(holder);
		} else {
			holder = (Promoter_MyCoupon_Holder) convertView.getTag();
		}
		
		
		
		if(promoter_mycoupon_array.get(position).getLogo() != null && !promoter_mycoupon_array.get(position).getLogo().equals(""))					
		{
		  //download and display image from url
		  imageLoader.displayImage(promoter_mycoupon_array.get(position).getLogo(), holder.img_coupon , options);
		}
		
		if(promoter_mycoupon_array.get(position).getQrcode_image() != null && !promoter_mycoupon_array.get(position).getQrcode_image().equals(""))					
		{
		  //download and display image from url
		  imageLoader.displayImage(promoter_mycoupon_array.get(position).getQrcode_image(), holder.img_qrcode , options);
		}
		
		holder.txt_coupontitle.setText(promoter_mycoupon_array.get(position).getCoupon_title());
		holder.txt_coupondec.setText(promoter_mycoupon_array.get(position).getCoupon_description());
		holder.txt_expiredate.setText(promoter_mycoupon_array.get(position).getEnd_date());	
			
		
		
		
		return convertView;
	} // getView

} // Promoter_MyCoupon_Adapter
