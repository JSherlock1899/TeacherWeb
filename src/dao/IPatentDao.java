package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	
	public int patentAudit(String Patsn,String Paudit); //审核
	

}
