package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.IProjectDao;
import dao.impl.ProjectDaoImpl;
import model.ExcelProject;
import model.Project;

public class ProjectService {
	
	IProjectDao projectdao = new ProjectDaoImpl();
	
	public int delProject(String Psn) {
		return projectdao.delProject(Psn);
	}

	public int alterProject(Project project) throws SQLException {
		return projectdao.alterProject(project);
	}

	public int insertProject(Project project) throws SQLException {
		return projectdao.insertProject(project);
	}
	
	//currentPage：当前页（页码），pageSize：每页显示数据条数
	//college：所属学院，sdept：所属系，starttime：立项时间，endtime：结题时间，Tname：搜索的教师名
	public ResultSet selectCollegeProject(String college, String sdept, String starttime, String endtime, String Tname,
			int currentPage, int pageSize) throws SQLException {
		return projectdao.selectCollegeProject(college, sdept, starttime, endtime, Tname, currentPage, pageSize);
	}
	
	
	public List<Project> getDataList(ResultSet rs) {
		return projectdao.getDataList(rs);
	}
	
	//对未审核的项目进行审核
	public int projectAudit(String Psn, String Paudit,String message) {
		return projectdao.projectAudit(Psn, Paudit,message);
	}
	
	//获取对应的附件路径
	public String getAccessory(String Psn) {
		return projectdao.getAccessory(Psn);
	}
	
	//获取审核的详细信息
	public List<Project> getlist(Project project) {
		return projectdao.getlist(project);
	}
	
	//保存上传的附件的路径
	public void saveFilePath(String path,String psn) { 
		projectdao.saveFilePath(path, psn);
	}; 
	
	//获取导出excel的集合
	public List<ExcelProject> getExcelDataList(ResultSet rs) {
		return projectdao.getExcelDataList(rs);
	}
	
}
