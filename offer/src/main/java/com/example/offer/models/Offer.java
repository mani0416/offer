package com.example.offer.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Offer {

	@Id
	@GeneratedValue
	private Long id;
	private String productName;
	@Column(unique = true)
	private String offerCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
	private Date offerStartTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
	private Date offerEndTime;
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public Date getOfferStartTime() {
		return offerStartTime;
	}

	public void setOfferStartTime(Date offerStartTime) {
		this.offerStartTime = offerStartTime;
	}

	public Date getOfferEndTime() {
		return offerEndTime;
	}

	public void setOfferEndTime(Date offerEndTime) {
		this.offerEndTime = offerEndTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
