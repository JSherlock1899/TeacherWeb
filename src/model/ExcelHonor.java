package model;

import java.util.Date;

public class ExcelHonor {
	private String Hsn;
	private String Hname;
	private String Hwinner;
	private Date Hdate;
	private String Hcompany;
	private String Hgrad;
	private String Hreward;
	private String Hremarks;
	public ExcelHonor(String hsn, String hname, String hwinner, Date hdate, String hcompany, String hgrad,
			String hreward, String hremarks) {
		super();
		Hsn = hsn;
		Hname = hname;
		Hwinner = hwinner;
		Hdate = hdate;
		Hcompany = hcompany;
		Hgrad = hgrad;
		Hreward = hreward;
		Hremarks = hremarks;
	}
	public String getHsn() {
		return Hsn;
	}
	public void setHsn(String hsn) {
		Hsn = hsn;
	}
	public String getHname() {
		return Hname;
	}
	public void setHname(String hname) {
		Hname = hname;
	}
	public String getHwinner() {
		return Hwinner;
	}
	public void setHwinner(String hwinner) {
		Hwinner = hwinner;
	}
	public Date getHdate() {
		return Hdate;
	}
	public void setHdate(Date hdate) {
		Hdate = hdate;
	}
	public String getHcompany() {
		return Hcompany;
	}
	public void setHcompany(String hcompany) {
		Hcompany = hcompany;
	}
	public String getHgrad() {
		return Hgrad;
	}
	public void setHgrad(String hgrad) {
		Hgrad = hgrad;
	}
	public String getHreward() {
		return Hreward;
	}
	public void setHreward(String hreward) {
		Hreward = hreward;
	}
	public String getHremarks() {
		return Hremarks;
	}
	public void setHremarks(String hremarks) {
		Hremarks = hremarks;
	}
	
}
