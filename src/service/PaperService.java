package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.IPaperDao;
import dao.impl.PaperDaoImpl;
import model.ExcelPaper;
import model.Paper;

public class PaperService {
	
	IPaperDao Paperdao = new PaperDaoImpl();
	

	public int delPaper(String Pasearchnum) {			//根据荣誉号删除对应的荣誉信息
		return Paperdao.delPaper(Pasearchnum);
	}
	
	
	
	public int alterPaper(Paper Paper) throws SQLException {		//根据荣誉号修改对应的荣誉信息
		return Paperdao.alterPaper(Paper);
	}
	
	
	
	public int insertPaper(Paper Paper) throws SQLException {		//插入新的荣誉信息
		return Paperdao.insertPaper(Paper);
	}
	
	
	
	public ResultSet selectCollegePaper(String college,String sdept,String endtime,String Tname,int currentPage,int pageSize) {		//根据查询条件的改变显示不同的查询结果
		try {
			return Paperdao.selectCollegePaper(college, sdept, endtime, Tname, currentPage, pageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Paper> getDataList(ResultSet rs){	//将结果集转化为集合
		return Paperdao.getDataList(rs);
	}
	
	//对未审核的论文进行审核
	public int paperAudit(String Pasearchnum,String Paudit,String message) {
		return Paperdao.paperAudit(Pasearchnum, Paudit,message);
	}
	
	//获取审核的详细信息
	public List<Paper> getlist(Paper paper) {
		return Paperdao.getlist(paper);
	}
		
	//获取对应的附件路径
	public String getAccessory(String Pasearchnum) {
		return Paperdao.getAccessory(Pasearchnum);
	}
	
	//保存上传的附件的路径
	public void saveFilePath(String path,String pasearchnum) { 
		 Paperdao.saveFilePath(path, pasearchnum);
	}; 
	
	//获取导出excel的集合
	public List<ExcelPaper> getExcelDataList(ResultSet rs) {
		return Paperdao.getExcelDataList(rs);
	}
		
}
