package com.wiredave.uvite.bean;

import java.io.Serializable;

public class Vendor_Create_Coupon_Bean implements Serializable{
	
	String coupon_title = "" , coupon_description = "" , discount_details = "",
		   start_date = "" , end_date = "" , referral_commission = "" , number_of_coupons = "";

	public String getCoupon_title() {
		return coupon_title;
	}

	public void setCoupon_title(String coupon_title) {
		this.coupon_title = coupon_title;
	}

	public String getCoupon_description() {
		return coupon_description;
	}

	public void setCoupon_description(String coupon_description) {
		this.coupon_description = coupon_description;
	}

	public String getDiscount_details() {
		return discount_details;
	}

	public void setDiscount_details(String discount_details) {
		this.discount_details = discount_details;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getReferral_commission() {
		return referral_commission;
	}

	public void setReferral_commission(String referral_commission) {
		this.referral_commission = referral_commission;
	}

	public String getNumber_of_coupons() {
		return number_of_coupons;
	}

	public void setNumber_of_coupons(String number_of_coupons) {
		this.number_of_coupons = number_of_coupons;
	}

}
