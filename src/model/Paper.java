package model;

import java.util.Date;

public class Paper {
	private String Pasearchnum;
	private String Paname;
	private String Pawriter;
	private String Papublish;//期/卷/页
	private String Pagrad;
	private Date Padate;
	private String Paremarks;
	private int totalRecord;
	private String Paccessory; //附件
	private String message;//留言
	private String Pdisvol; 
	
	public Paper(String pasearchnum, String paname, String pawriter, String papublish, String pagrad, Date padate,
			String paremarks, int totalRecord, String pdisvol) {
		super();
		Pasearchnum = pasearchnum;
		Paname = paname;
		Pawriter = pawriter;
		Papublish = papublish;
		Pagrad = pagrad;
		Padate = padate;
		Paremarks = paremarks;
		this.totalRecord = totalRecord;
		Pdisvol = pdisvol;
	}

	



	public Paper(String pasearchnum, String paname, String pawriter, String papublish, String pagrad, Date padate,
			String paremarks, String pdisvol) {
		super();
		Pasearchnum = pasearchnum;
		Paname = paname;
		Pawriter = pawriter;
		Papublish = papublish;
		Pagrad = pagrad;
		Padate = padate;
		Paremarks = paremarks;
		Pdisvol = pdisvol;
	}


	public Paper(String pasearchnum, String paname, String pawriter, String papublish, String pagrad, Date padate,
			String paremarks, int totalRecord) {
		super();
		Pasearchnum = pasearchnum;
		Paname = paname;
		Pawriter = pawriter;
		Papublish = papublish;
		Pagrad = pagrad;
		Padate = padate;
		Paremarks = paremarks;
		this.totalRecord = totalRecord;
	}
	
	public Paper(String pasearchnum, String paname, String pawriter, String papublish, String pagrad, Date padate,
			String paremarks) {
		super();
		Pasearchnum = pasearchnum;
		Paname = paname;
		Pawriter = pawriter;
		Papublish = papublish;
		Pagrad = pagrad;
		Padate = padate;
		Paremarks = paremarks;
	}
	
	public String getPdisvol() {
		return Pdisvol;
	}
	public void setPdisvol(String pdisvol) {
		Pdisvol = pdisvol;
	}
	public String getPasearchnum() {
		return Pasearchnum;
	}
	public void setPasearchnum(String pasearchnum) {
		Pasearchnum = pasearchnum;
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
	
	public String getPaccessory() {
		return Paccessory;
	}


	public void setPaccessory(String paccessory) {
		Paccessory = paccessory;
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
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
