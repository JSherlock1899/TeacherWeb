package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import dao.IProjectDao;
import dao.impl.ProjectDaoImpl;
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
	
}
