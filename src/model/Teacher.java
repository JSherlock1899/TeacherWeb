package model;

public class Teacher {
	private int Tnum;
	private String Tsn;
	private String Tpsw;
	private String Tname;
	private String Tsex;
	private String Ttel;
	private String Tmail;
	public Teacher(String tpsw, String tname) {
		this.Tpsw = tpsw;
		this.Tname = tname;
	}
	private String Csn;
	private String Dsn;
	public int getTnum() {
		return Tnum;
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
