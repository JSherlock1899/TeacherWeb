package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ExcelHonor;
import model.Honor;
import model.Patent;

public interface IHonorDao {
	public int delHonor(String Hsn);			//根据荣誉号删除对应的荣誉信息
	
	
	public int alterHonor(Honor honor) throws SQLException;		//根据荣誉号修改对应的荣誉信息
	
	
	public int insertHonor(Honor honor) throws SQLException;		//插入新的荣誉信息
	
	
	public ResultSet selectCollegeHonor(String college,String sdept,String starttime,String Tname,int currentPage,int pageSize) throws SQLException ;		//根据查询条件的改变显示不同的查询结果
	
	public List<Honor> getDataList(ResultSet rs);	//将结果集转化为集合
		
	public int honorAudit(String Hsn,String Haudit,String Message); //审核
	
	public List<ExcelHonor> getExcelDataList(ResultSet rs);//获取导出excel的集合
	
	public List<Honor> getlist(Honor honor);
	
	public String getAccessory(String Hsn); //获取对应的附件路径
	
	public void saveFilePath(String path,String hsn);  //保存上传的附件的路径
}
