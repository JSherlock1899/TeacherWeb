package model;

public class ExcelTeacher {
	private String Tsn;
	private String Tname;
	private String Tsex;
	private String Ttel;
	private String Tmail;
	private String Cname; //所属学院
	private String Dname; //所属系
	private String TID; //教师身份证
	public ExcelTeacher(String tsn, String tname, String tsex, String ttel, String tmail, String cname, String dname,
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
	public String getTsn() {
		return Tsn;
	}
	public void setTsn(String tsn) {
		Tsn = tsn;
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
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	
}
