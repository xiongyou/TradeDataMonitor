package com.tradedatamonitor.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_productURL")
public class ProductURL 
{
	@Id
	private String productURL;
	
	@Column(name="getURLTime")
	private Date getURLTime;
	
	@Column(name="platform")
	private String platform;
	
	@Column(name="status")
	private Byte status;
	
	@Column(name="keyWord")
	private String keyWord;
	
	
	public String getProductURL() {
		return productURL;
	}
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	public Date getGetURLTime() {
		return getURLTime;
	}
	public void setGetURLTime(Date getURLTime) {
		this.getURLTime = getURLTime;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}	
	
	
}


