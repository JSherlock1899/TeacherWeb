package model;

import java.util.Date;

public class Patent {
	private	String Patname;
	private String Pleader;
	private String Patsn;
	private Date Patapdate;
	private Date Patemdate;
	private String Patgrad;
	private String Patremarks;
	private String Paccessory;
	private int TotalRecord; //数据总条数

	
	
	//	public Patent(Map <String,Object> map) {
//		Patname = (String) map.get("Patname");
//		Pleader = (String) map.get("Pleader");
//		Patsn = (String) map.get("Patsn");
//		Patapdate = (Date) map.get("Patapdate");
//		Patemdate = (Date) map.get("Patemdate");
//		Patgrad = (String) map.get("Patgrad");
//		Patremarks = (String) map.get("Patremarks");
//	}
	
	
	public Patent(String patname, String pleader, String patsn, Date patapdate, Date patemdate, String patgrad,
			String patremarks) {
		super();
		Patname = patname;
		Pleader = pleader;
		Patsn = patsn;
		Patapdate = patapdate;
		Patemdate = patemdate;
		Patgrad = patgrad;
		Patremarks = patremarks;
	}
	
	public Patent(String patname, String pleader, String patsn, Date patapdate, Date patemdate, String patgrad,
			String patremarks, String paccessory, int totalRecord) {
		super();
		Patname = patname;
		Pleader = pleader;
		Patsn = patsn;
		Patapdate = patapdate;
		Patemdate = patemdate;
		Patgrad = patgrad;
		Patremarks = patremarks;
		Paccessory = paccessory;
		TotalRecord = totalRecord;
	}
	public int getTotalRecord() {
		return TotalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		TotalRecord = totalRecord;
	}
	public String getPaccessory() {
		return Paccessory;
	}
	public void setPaccessory(String paccessory) {
		Paccessory = paccessory;
	}
	public String getPatname() {
		return Patname;
	}
	public void setPatname(String patname) {
		Patname = patname;
	}
	public String getPleader() {
		return Pleader;
	}
	public void setPleader(String pleader) {
		Pleader = pleader;
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
	
}
