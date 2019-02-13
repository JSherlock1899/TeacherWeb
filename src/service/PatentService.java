package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import dao.IPatentDao;
import dao.impl.PatentDaoImpl;
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
}
