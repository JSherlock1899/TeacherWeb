package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.ExcelPatent;
import model.Patent;

public interface IPatentDao {
	public int delPatent(String Patsn);		//根据授权号删除对应的专利信息

	
	public int alterPatent(Patent patent) throws SQLException;	//根据授权号修改对应的修改专利内容
	
		
	
	
	public int insertPatent(Patent patent);						//插入新的专利信息
		
	
	//返回当前显示页的数据
	//currentPage：当前页（页码），pageSize：每页显示数据条数
	//college：所属学院，sdept：所属系，starttime：开始时间，endtime：结束时间，Tname：搜索的教师名
	public ResultSet selectCollegePatent(String college,String sdept,String starttime,String endtime,String Tname,int currentPage,int pageSize) throws SQLException;		//根据两个下拉框的改变显示不同的查询结果	
	
	public List<Patent> getDataList(ResultSet rs);//将结果集转化为集合
	
	public int patentAudit(String Patsn,String Paudit,String message); //审核
	
	public void saveFilePath(String path,String patsn);  //保存上传的附件的路径
	
	public List<ExcelPatent> getExcelDataList(ResultSet rs);//获取导出excel的集合
	
	public List<Patent> getlist(Patent patent);//获取单个专利的详细信息
	
	public String getAccessory(String Patsn); //获取对应的附件路径
}
