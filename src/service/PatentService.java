package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.IPatentDao;
import dao.impl.PatentDaoImpl;
import model.ExcelPatent;
import model.Patent;

//业务逻辑层
public class PatentService {
	IPatentDao patentdao = new PatentDaoImpl();
	
	//删除专利信息
	public int delPatent(String Patsn) {	
		int result = patentdao.delPatent(Patsn);
		return result;
	}
	
	//修改专利信息
	public int alterPatent(Patent patent) {		//返回0意为系统错误，返回1意为修改成功
		int result = 0;
			try {
				result = patentdao.alterPatent(patent);
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	//插入新的专利信息
	public int insertPatent(Patent patent) {	//返回0意为系统错误，返回1意为添加成功
		int result = patentdao.insertPatent(patent);
		return result;
	}
	
	//将数据按要求分页显示
	public ResultSet selectCollegePatent(String college,String sdept,String starttime,String endtime,String Tname,int currentPage,int pageSize) {
		try {
			return patentdao.selectCollegePatent(college, sdept, starttime, endtime, Tname, currentPage, pageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Patent> getDataList(ResultSet rs){	//将结果集转化为集合
		return patentdao.getDataList(rs);
	}
	
	//对未审核的专利进行审核
	public int patentAudit(String Patsn, String Paudit,String message) {
		return patentdao.patentAudit(Patsn, Paudit,message);
	}
	
	
	//保存上传的附件的路径
	public void saveFilePath(String path,String patsn) { 
		 patentdao.saveFilePath(path, patsn);
	}; 
	
	//获取单条专利信息的详细信息
	public List<Patent> getlist(Patent patent){
		return patentdao.getlist(patent);
	}
	
	//获取对应的附件路径
	public String getAccessory(String Patsn) {
		return patentdao.getAccessory(Patsn);
	}
	
	//获取导出excel的集合
	public List<ExcelPatent> getExcelDataList(ResultSet rs){			
		return patentdao.getExcelDataList(rs);
	}
}
