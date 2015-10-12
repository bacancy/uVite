package com.wiredave.uvite.bean;

import java.io.Serializable;

public class Promoter_AllActive_Coupon_Bean implements Serializable{

	String coupon_id,vendor_id,coupon_title,coupon_description,coupon_category,
	       discount_type,start_date,end_date,commission,logo,total_coupon,coupon_used,
	       availability,coupon_referred,coupon_subsribe,total_amount,coupon_code,qrcode_image,delete_status,
	       status,created_date;

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

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

	public String getCoupon_category() {
		return coupon_category;
	}

	public void setCoupon_category(String coupon_category) {
		this.coupon_category = coupon_category;
	}

	public String getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
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

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTotal_coupon() {
		return total_coupon;
	}

	public void setTotal_coupon(String total_coupon) {
		this.total_coupon = total_coupon;
	}

	public String getCoupon_used() {
		return coupon_used;
	}

	public void setCoupon_used(String coupon_used) {
		this.coupon_used = coupon_used;
	}

	public String getCoupon_referred() {
		return coupon_referred;
	}

	public void setCoupon_referred(String coupon_referred) {
		this.coupon_referred = coupon_referred;
	}

	public String getCoupon_subsribe() {
		return coupon_subsribe;
	}

	public void setCoupon_subsribe(String coupon_subsribe) {
		this.coupon_subsribe = coupon_subsribe;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

	public String getQrcode_image() {
		return qrcode_image;
	}

	public void setQrcode_image(String qrcode_image) {
		this.qrcode_image = qrcode_image;
	}

	public String getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(String delete_status) {
		this.delete_status = delete_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
}
