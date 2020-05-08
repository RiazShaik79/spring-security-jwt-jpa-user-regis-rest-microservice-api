package io.javabrains;

public class OTPModel {
	
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public long getExpirytime() {
		return expirytime;
	}
	public void setExpirytime(long expirytime) {
		this.expirytime = expirytime;
	}
	public OTPModel(String mobilenumber, String otp, long expirytime) {
		super();
		this.mobilenumber = mobilenumber;
		this.otp = otp;
		this.expirytime = expirytime;
	}
	public OTPModel(String mobilenumber, String otp, long expirytime, String status) {
		super();
		this.mobilenumber = mobilenumber;
		this.otp = otp;
		this.expirytime = expirytime;
		this.status = status;
	}
	private String mobilenumber;
	private String otp;
	private long expirytime;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OTPModel() {
		
	}
	
	public OTPModel(String mobilenumber, String otp) {
		super();
		this.mobilenumber = mobilenumber;
		this.otp = otp;
	}

}
