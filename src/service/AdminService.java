package service;

import java.sql.SQLException;

import dao.IAdminDao;
import dao.impl.AdminDaoImpl;

public class AdminService {
	
	IAdminDao adminDao = new AdminDaoImpl();

	//验证密码是否正确
	public boolean verifyPassword(String Aname, String oldPassword) throws SQLException {
		return adminDao.verifyPassword(Aname, oldPassword);
	}
	
	//修改密码
	public int alterPassword(String Aname, String newPassword) throws SQLException {
		return adminDao.alterPassword(Aname, newPassword);
	}
}
