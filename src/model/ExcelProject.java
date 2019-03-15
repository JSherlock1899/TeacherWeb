package model;

import java.util.Date;

public class ExcelProject {
	private String Psn;
	private String Pname;
	private String Pleader;		//负责人
	private String Pmember;		//成员
	private String Pgrad;
	private String Pkind;		
	private int Pmoney;	
	private Date Pstatime;
	private String Pcondition;	//科研状态
	private Date Pendtime;
	private String Premarks;
	public ExcelProject(String psn, String pname, String pleader, String pmember, String pgrad, String pkind,
			int pmoney, Date pstatime, String pcondition, Date pendtime, String premarks) {
		super();
		Psn = psn;
		Pname = pname;
		Pleader = pleader;
		Pmember = pmember;
		Pgrad = pgrad;
		Pkind = pkind;
		Pmoney = pmoney;
		Pstatime = pstatime;
		Pcondition = pcondition;
		Pendtime = pendtime;
		Premarks = premarks;
	}
	public String getPsn() {
		return Psn;
	}
	public void setPsn(String psn) {
		Psn = psn;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	public String getPleader() {
		return Pleader;
	}
	public void setPleader(String pleader) {
		Pleader = pleader;
	}
	public String getPmember() {
		return Pmember;
	}
	public void setPmember(String pmember) {
		Pmember = pmember;
	}
	public String getPgrad() {
		return Pgrad;
	}
	public void setPgrad(String pgrad) {
		Pgrad = pgrad;
	}
	public String getPkind() {
		return Pkind;
	}
	public void setPkind(String pkind) {
		Pkind = pkind;
	}
	public int getPmoney() {
		return Pmoney;
	}
	public void setPmoney(int pmoney) {
		Pmoney = pmoney;
	}
	public Date getPstatime() {
		return Pstatime;
	}
	public void setPstatime(Date pstatime) {
		Pstatime = pstatime;
	}
	public String getPcondition() {
		return Pcondition;
	}
	public void setPcondition(String pcondition) {
		Pcondition = pcondition;
	}
	public Date getPendtime() {
		return Pendtime;
	}
	public void setPendtime(Date pendtime) {
		Pendtime = pendtime;
	}
	public String getPremarks() {
		return Premarks;
	}
	public void setPremarks(String premarks) {
		Premarks = premarks;
	}
	
}
