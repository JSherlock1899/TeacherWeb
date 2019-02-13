package model;

import java.util.Date;

public class ExcelPatent {
	private String Patname;
	private String Patsn;
	private Date Patapdate;
	private Date Patemdate;
	private String Patgrad;
	private String Patremarks;
	private String Tname;
	public String getPatname() {
		return Patname;
	}
	public void setPatname(String patname) {
		Patname = patname;
	}
	public String getPatsn() {
		return Patsn;
	}
	public void setPatsn(String patsn) {
		Patsn = patsn;
	}
	public Date getPatapdate() {
		return Patapdate;
	}
	public void setPatapdate(Date patapdate) {
		Patapdate = patapdate;
	}
	public Date getPatemdate() {
		return Patemdate;
	}
	public void setPatemdate(Date patemdate) {
		Patemdate = patemdate;
	}
	public String getPatgrad() {
		return Patgrad;
	}
	public void setPatgrad(String patgrad) {
		Patgrad = patgrad;
	}
	public String getPatremarks() {
		return Patremarks;
	}
	public void setPatremarks(String patremarks) {
		Patremarks = patremarks;
	}
	public String getTname() {
		return Tname;
	}
	public void setTname(String tname) {
		Tname = tname;
	}
	public ExcelPatent(String patname, String patsn, Date patapdate, Date patemdate, String patgrad, String patremarks,
			String tname) {
		super();
		Patname = patname;
		Patsn = patsn;
		Patapdate = patapdate;
		Patemdate = patemdate;
		Patgrad = patgrad;
		Patremarks = patremarks;
		Tname = tname;
	}
	
}
