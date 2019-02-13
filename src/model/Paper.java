package model;

import java.util.Date;

public class Paper {
	private String Paname;
	private String Pawriter;
	private String Papublish;
	private String Pagrad;
	private Date Padate;
	private String Paremarks;
	public Paper(String paname, String pawriter, String papublish, String pagrad, Date padate, String paremarks) {
		super();
		Paname = paname;
		Pawriter = pawriter;
		Papublish = papublish;
		Pagrad = pagrad;
		Padate = padate;
		Paremarks = paremarks;
	}
	public String getPaname() {
		return Paname;
	}
	public void setPaname(String paname) {
		Paname = paname;
	}
	public String getPawriter() {
		return Pawriter;
	}
	public void setPawriter(String pawriter) {
		Pawriter = pawriter;
	}
	public String getPapublish() {
		return Papublish;
	}
	public void setPapublish(String papublish) {
		Papublish = papublish;
	}
	public String getPagrad() {
		return Pagrad;
	}
	public void setPagrad(String pagrad) {
		Pagrad = pagrad;
	}
	public Date getPadate() {
		return Padate;
	}
	public void setPadate(Date padate) {
		Padate = padate;
	}
	public String getParemarks() {
		return Paremarks;
	}
	public void setParemarks(String paremarks) {
		Paremarks = paremarks;
	}
	
}
