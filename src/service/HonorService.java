package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.IHonorDao;
import dao.impl.HonorDaoImpl;
import model.Honor;
import model.Patent;


public class HonorService {
	
	IHonorDao honordao = new HonorDaoImpl();
	

	public int delHonor(String Hsn) {			//根据荣誉号删除对应的荣誉信息
		return honordao.delHonor(Hsn);
	}
	
	
	
	public int alterHonor(Honor honor) throws SQLException {		//根据荣誉号修改对应的荣誉信息
		return honordao.alterHonor(honor);
	}
	
	
	
	public int insertHonor(Honor honor) throws SQLException {		//插入新的荣誉信息
		return honordao.insertHonor(honor);
	}
	
	
	
	public ResultSet selectCollegeHonor(String college,String sdept,String starttime,String Tname,int currentPage,int pageSize) {		//根据查询条件的改变显示不同的查询结果
		try {
			return honordao.selectCollegeHonor(college, sdept, starttime, Tname, currentPage, pageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Honor> getDataList(ResultSet rs){	//将结果集转化为集合
		return honordao.getDataList(rs);
	}
	
	//对未审核的荣誉进行审核
	public int honorAudit(String Hsn,String Paudit) {
		return honordao.honorAudit(Hsn, Paudit);
	}
	
	public List<Honor> getlist(Honor honor){
		return honordao.getlist(honor);
	}
}
