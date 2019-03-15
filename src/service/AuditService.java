package service;


import java.util.List;

import dao.IAuditDao;
import dao.impl.AuditDaoImpl;
import model.Honor;
import model.Paper;
import model.Patent;
import model.Project;

public class AuditService {
	
	IAuditDao auditDao = new AuditDaoImpl();
	
	//在数据库中，grade为0代表未审核。grade为1代表审核通过，grade为2代表审核未通过
		
		//获取该学院所有未审核的项目
		public List<Project> getAllUnauditedProject(int auditNum,String college,String sdept,int currentPage,int pageSize) {
			return auditDao.getAllUnauditedProject(auditNum, college,sdept,currentPage, pageSize);
		}
		
		//获取该学院所有未审核的项目
		public List<Paper> getAllUnauditedPaper(int auditNum,String college,String sdept,int currentPage,int pageSize) {
			return auditDao.getAllUnauditedPaper(auditNum, college, sdept,currentPage, pageSize);
		}
		
		
		//获取该学院所有未审核的项目
		public List<Honor> getAllUnauditedHonor(int auditNum,String college,String sdept,int currentPage,int pageSize) {
			return auditDao.getAllUnauditedHonor(auditNum, college,sdept, currentPage, pageSize);
		}
		
		
		//获取该学院所有未审核的项目
		public List<Patent> getAllUnauditedPatent(int auditNum,String college,String sdept,int currentPage,int pageSize) {
			return auditDao.getAllUnauditedPatent(auditNum, college, sdept,currentPage, pageSize);
		}
}
