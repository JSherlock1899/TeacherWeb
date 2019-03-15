package dao;

import java.sql.SQLException;
import java.util.List;

import model.Honor;
import model.Paper;
import model.Patent;
import model.Project;

public interface IAuditDao {
	
	//获取所有未审核的项目
	public List<Project> getAllUnauditedProject(int auditNum,String college,String sdept,int currentPage,int pageSize);
	
	//获取所有未审核的论文
	public List<Paper> getAllUnauditedPaper(int auditNum,String college,String sdept,int currentPage,int pageSize);
	
	//获取所有未审核的荣誉
	public List<Honor> getAllUnauditedHonor(int auditNum,String college,String sdept,int currentPage,int pageSize);
	
	//获取所有未审核的专利
	public List<Patent> getAllUnauditedPatent(int auditNum,String college,String sdept,int currentPage,int pageSize);
}
