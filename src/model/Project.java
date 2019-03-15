package model;

import java.util.Date;

public class Project {
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
	private int totalRecord;
	private String Paccessory;
	private String message;//留言
	private String Paudit;
	
	public Project(String psn, String pname, String pleader, String pmember, String pgrad, String pkind, int pmoney,
			Date pstatime, String pcondition, Date pendtime, String premarks, int totalRecord, String paccessory,
			String paudit) {
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
		this.totalRecord = totalRecord;
		Paccessory = paccessory;
		Paudit = paudit;
	}




	public Project(String psn, String pname, String pleader, String pmember, String pgrad, String pkind, int pmoney,
			Date pstatime, String pcondition, Date pendtime, String premarks, int totalRecord, String paccessory,
			String message, String paudit) {
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
		this.totalRecord = totalRecord;
		Paccessory = paccessory;
		this.message = message;
		Paudit = paudit;
	}




	public Project(String psn, String pname, String pleader, String pmember, String pgrad, String pkind, int pmoney,
			Date pstatime, String pcondition, Date pendtime, String premarks, int totalRecord) {
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
		this.totalRecord = totalRecord;
	}
	



	public Project(String psn, String pname, String pleader, String pmember, String pgrad, String pkind, int pmoney,
			Date pstatime, String pcondition, Date pendtime, String premarks, String paccessory) {
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
		Paccessory = paccessory;
	}

	public Project(String psn, String pname, String pleader, String pmember, String pgrad, String pkind, int pmoney,
			Date pstatime, String pcondition, Date pendtime, String premarks) {
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
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getPaccessory() {
		return Paccessory;
	}

	public void setPaccessory(String paccessory) {
		Paccessory = paccessory;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPaudit() {
		return Paudit;
	}
	public void setPaudit(String paudit) {
		Paudit = paudit;
	}
	
}
