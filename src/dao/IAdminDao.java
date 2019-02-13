package dao;

import java.sql.SQLException;
import model.Admin;


//管理员数据库操作
public interface IAdminDao {
	
	//验证管理员登录信息
	public int AdminLogin(Admin admin) throws SQLException;
		
	//判断管理员级别
	public int AdminJudge(String adminName)throws SQLException;
		
}
