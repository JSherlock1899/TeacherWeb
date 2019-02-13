package model;

public class Admin {
	private int Anum;
	private String Aname;
	private String Apsw;
	private String Agrad;
	private String Csn;
	private String Dsn;
	public int getAnum() {
		return Anum;
	}
	public void setAnum(int anum) {
		Anum = anum;
	}
	public String getAname() {
		return Aname;
	}
	public void setAname(String aname) {
		Aname = aname;
	}
	public String getApsw() {
		return Apsw;
	}
	public void setApsw(String apsw) {
		Apsw = apsw;
	}
	public String getAgrad() {
		return Agrad;
	}
	public void setAgrad(String agrad) {
		Agrad = agrad;
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
	public Admin(String aname, String apsw) {
		
		this.Aname = aname;
		this.Apsw = apsw;
	}
	
	
}
