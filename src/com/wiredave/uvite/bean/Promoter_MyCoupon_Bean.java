package com.wiredave.uvite.bean;

import java.io.Serializable;

public class Promoter_MyCoupon_Bean implements Serializable{

	String coupon_id,promoter_id,coupon_title,coupon_description,coupon_category,discount_type,
    availability,start_date,end_date,commission,logo,coupon_code,qrcode_image,
    status,created_date;

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getPromoter_id() {
		return promoter_id;
	}

	public void setPromoter_id(String promoter_id) {
		this.promoter_id = promoter_id;
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

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
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
