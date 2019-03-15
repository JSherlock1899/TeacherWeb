package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ExcelPaper;
import model.Paper;
import model.Patent;

public interface IPaperDao {
	
	public int delPaper(String Panum);			//根据论文号删除对应的论文信息
	
	
	public int alterPaper(Paper paper) throws SQLException;		//根据论文号修改对应的论文信息
	
	
	public int insertPaper(Paper paper) throws SQLException;		//插入新的论文信息
	
	//根据查询条件的改变显示不同的查询结果
	public ResultSet selectCollegePaper(String college,String sdept,String endtime,String Tname,int currentPage,int pageSize) throws SQLException ;		
	
	public List<Paper> getDataList(ResultSet rs);	//将结果集转化为集合
	
	public int paperAudit(String Pasearchnum,String Paudit,String Message); //审核
	
	public List<ExcelPaper> getExcelDataList(ResultSet rs);//获取导出excel的集合
	
	
	public List<Paper> getlist(Paper paper); //获取审核的详细信息

	
	public String getAccessory(String Patsn); //获取对应的附件路径
	
	public void saveFilePath(String path,String pasearchnum);  //保存上传的附件的路径
}
