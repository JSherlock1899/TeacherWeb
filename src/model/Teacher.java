package model;

public class Teacher {
	private int Tnum;
	private String Tsn;
	private String Tpsw;
	private String Tname;
	private String Tsex;
	private String Ttel;
	private String Tmail;
	private String Cname; //所属学院
	private String Dname; //所属系
	private int TotalRecord;
	private String TID; //教师身份证
	
	public Teacher(String tpsw, String tname) {
		this.Tpsw = tpsw;
		this.Tname = tname;
	}
	private String Csn;
	private String Dsn;
	public int getTnum() {
		return Tnum;
	}
	
	
	
	
	public Teacher(String tsn, String tname, String tsex, String ttel, String tmail, String cname, String dname,
			int totalRecord, String tID) {
		super();
		Tsn = tsn;
		Tname = tname;
		Tsex = tsex;
		Ttel = ttel;
		Tmail = tmail;
		Cname = cname;
		Dname = dname;
		TotalRecord = totalRecord;
		TID = tID;
	}




	public Teacher(String tsn, String tname, String tsex, String ttel, String tmail, String cname, String dname,
			String tID) {
		super();
		Tsn = tsn;
		Tname = tname;
		Tsex = tsex;
		Ttel = ttel;
		Tmail = tmail;
		Cname = cname;
		Dname = dname;
		TID = tID;
	}




	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public int getTotalRecord() {
		return TotalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		TotalRecord = totalRecord;
	}

	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public String getDname() {
		return Dname;
	}
	public void setDname(String dname) {
		Dname = dname;
	}

	public void setTnum(int tnum) {
		Tnum = tnum;
	}
	public String getTsn() {
		return Tsn;
	}
	public void setTsn(String tsn) {
		Tsn = tsn;
	}
	public String getTpsw() {
		return Tpsw;
	}
	public void setTpsw(String tpsw) {
		Tpsw = tpsw;
	}
	public String getTname() {
		return Tname;
	}
	public void setTname(String tname) {
		Tname = tname;
	}
	public String getTsex() {
		return Tsex;
	}
	public void setTsex(String tsex) {
		Tsex = tsex;
	}
	public String getTtel() {
		return Ttel;
	}
	public void setTtel(String ttel) {
		Ttel = ttel;
	}
	public String getTmail() {
		return Tmail;
	}
	public void setTmail(String tmail) {
		Tmail = tmail;
	}
	public String getCsn() {
		return Csn;
	}
	public void setCsn(String csn) {
		Csn = csn;
	}
	public String getDsn() {
		return Dsn;
	}
	public void setDsn(String dsn) {
		Dsn = dsn;
	}
	

}
