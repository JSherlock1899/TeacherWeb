package dao;

import java.sql.SQLException;
import model.Admin;


//管理员数据库操作
public interface IAdminDao {
	
	//验证管理员登录信息
	public int AdminLogin(Admin admin) throws SQLException;
		
	//判断管理员级别
	public int AdminJudge(String adminName)throws SQLException;
	
	
	//获取院管理员的所属学院
	public String getAdminCname(String Adminname)throws SQLException;
	
	//验证密码是否正确
	public boolean verifyPassword(String Aname,String oldPassword)throws SQLException;
	
	//修改密码
	public int alterPassword(String Aname,String newPassword)throws SQLException;
	
		
}
