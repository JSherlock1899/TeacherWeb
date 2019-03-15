package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import dao.IBaseDao;
import dao.IProjectDao;
import model.ExcelProject;
import model.Project;
import util.CommonUtil;
import util.DbUtil;

public class ProjectDaoImpl implements IProjectDao{
	
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	IBaseDao baseDao = new BaseDaoImpl();
	CommonUtil util = new CommonUtil();
	@Override
	public int delProject(String Psn) {
		String sql = "delete from project where Psn = ?";		
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Psn);
			int result = stmt.executeUpdate();
			if(result == 1) {	//当删除成功时返回1，删除失败时返回0
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
	}

	@Override
	public int alterProject(Project project) throws SQLException {
		String Tsn = teacherdao.getTsn(project.getPleader().trim());			//获取该项目负责人对应的教师号
		String sql = "update Project set Pname = ?,Pleader = ?,Pmember = ?,Pgrad = ?,Pkind = ?,Pmoney = ?,Pstatime = ?,Pcondition = ?,Pendtime = ?,Premarks = ?,Tsn = ?,Paudit = 0 "
				+ "where Psn = ?";
		Date Pstatime = util.utilToSql(project.getPstatime());
		Date Pendtime = util.utilToSql(project.getPendtime());
		List params = Arrays.asList(project.getPname(),project.getPleader(),project.getPmember(),project.getPgrad(),project.getPkind(),
				project.getPmoney(),Pstatime,project.getPcondition(),Pendtime,project.getPremarks(),Tsn,project.getPsn());
		return dbUtil.getUpdateResult(sql, params);
	}

	@Override
	public int insertProject(Project project) throws SQLException {
		String Tsn = teacherdao.getTsn(project.getPleader().trim());			//获取该项目负责人对应的教师号
		String sql = "insert into Project (Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tsn,Paudit) Values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Date Pstatime = util.utilToSql(project.getPstatime());
		Date Pendtime = util.utilToSql(project.getPendtime());
		List params = Arrays.asList(project.getPsn(),project.getPname(),project.getPleader(),project.getPmember(),project.getPgrad(),project.getPkind(),
				project.getPmoney(),Pstatime,project.getPcondition(),Pendtime,project.getPremarks(),Tsn,0);
		return dbUtil.getUpdateResult(sql, params);
	}
	
	//currentPage：当前页（页码），pageSize：每页显示数据条数
	//college：所属学院，sdept：所属系，starttime：立项时间，endtime：结题时间，Tname：搜索的教师名
	@Override
	public ResultSet selectCollegeProject(String college, String sdept, String starttime, String endtime, String Tname,
			int currentPage, int pageSize) throws SQLException {
				//当前页的第一条记录
				int m = (currentPage - 1) * pageSize + 1;
				//当前页的最后一条记录
				int n = currentPage * pageSize;
				college = CommonUtil.disposePageValue(college);
				sdept = CommonUtil.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
				sdept = CommonUtil.disposeSdeptValue(sdept);
				starttime = CommonUtil.disposePageValue(starttime);
				endtime = CommonUtil.disposePageValue(endtime);
				Tname = CommonUtil.disposePageValue(Tname);
//				tem.out.println("college =" + college);
//				System.out.println("sdept =" + sdept);
//				System.out.println("starttime =" + starttime);
//				System.out.println("endtime =" + endtime);
//				System.out.println("Tname =" + Tname);
				//什么都没有设置
				String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn where p.Paudit = 1) as pp where pp.r between ? and ?";
				//设置了要查询的学院
				String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p  left join Teacher t on p.Tsn = t.Tsn "
						+ "join College c on t.Csn = c.Csn where c.Cname = ? and p.Paudit = 1) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业
				String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ,Cname,Dname,Paccessory,Paudit,message," 
						+ "ROW_NUMBER() over (order by Psn desc) as r from Project p left join Teacher t on p.Tsn = t.Tsn join Sdept s "
						+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ? and p.Paudit = 1) as pp where pp.r between ? and ?";	
				//设置了要查询的起止时间
				String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn  where Pstatime >= ? and Pendtime <= ? and p.Paudit = 1) "
						+ "as pp where pp.r between ? and ?";
				//设置了要查询的学院和起止时间
				String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message,"
						+	"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ 	"on t.Csn = c.Csn where c.Cname = ? and Pstatime >= ? and Pendtime <= ? and p.Paudit = 1) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业和起止时间
				String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Dname,Paccessory,Paudit,message,"
						+	"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Pstatime >= ? and Pendtime <= ? and p.Paudit = 1) as pp where pp.r between ? and ?";	
				//没有设置要查询的教师名
				String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn where Tname = ? and p.Paudit = 1) "
						+ "as pp where pp.r between ? and ?";
				//设置了要查询的学院和教师名
				String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ? and p.Paudit = 1) as pp where pp.r between ? and ?";
				//没有设置要查询的学院和专业和教师名
				String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Dname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ? and p.Paudit = 1) as pp where pp.r between ? and ?";
				//设置了要查询的起止时间和教师名
				String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn "
						+ "where  Pstatime >= ? and Pendtime <= ? and Tname = ? and p.Paudit = 1) as pp where pp.r between ? and ?";
				//设置了要查询的学院和起止时间和教师名
				String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
						+ " where  c.Cname = ? and Pstatime >= ? and Pendtime <= ? and Tname = ? and p.Paudit = 1) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业和起止时间和教师名
				String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname,Dname,Paccessory,Paudit,message," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
						+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and Pendtime <= ? and Tname = ? and p.Paudit = 1) as pp where pp.r between ? and ?"; ;	
				try {
				if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
					if(college ==  null && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
						List params = Arrays.asList(m,n);
						return dbUtil.getResultSet(sql1, params);
					}else if(!college.equals("") && sdept == null && starttime == null ||!college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院
						List params = Arrays.asList(college,m,n);
						return dbUtil.getResultSet(sql2, params);
					}else if(!college.equals("") && !sdept.equals("") && starttime.equals("")) {			//选了学院和专业
						List params = Arrays.asList(college,sdept,m,n);
						return dbUtil.getResultSet(sql3, params);
					}else if(college.equals("") && sdept.equals("") && !starttime.equals("")) {		//只选了起止时间
						List params = Arrays.asList(starttime,endtime,m,n);
						return dbUtil.getResultSet(sql4, params);
					}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间
						List params = Arrays.asList(college,starttime,endtime,m,n);
						return dbUtil.getResultSet(sql5, params);
					}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间
						List params = Arrays.asList(college,sdept,starttime,endtime,m,n);
						return dbUtil.getResultSet(sql6, params);
					}
				}else {
					if(college.equals("") && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("")) {							//选了教师名				
						List params = Arrays.asList(Tname,m,n);
						return dbUtil.getResultSet(sql7, params);
					}else if(!college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院和教师名
						List params = Arrays.asList(college,Tname,m,n);
						return dbUtil.getResultSet(sql8, params);
					}else if(!college.equals("") && !sdept.equals("") && starttime.equals("")) {			//选了学院和专业和教师名
						List params = Arrays.asList(college,sdept,Tname,m,n);
						return dbUtil.getResultSet(sql9, params);
					}else if(college.equals("") && sdept.equals("") && !starttime.equals("")) {		//只选了起止时间和教师名
						List params = Arrays.asList(starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql10, params);
					}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间和教师名
						List params = Arrays.asList(college,starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql11, params);
					}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间和教师名
						List params = Arrays.asList(college,sdept,starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql12, params);
					}
				}
				} catch (NullPointerException | SQLException e) {
					e.printStackTrace();
				}finally {
					baseDao.closeCon();
				}
				return null;
	}
	
	
	//获取分页的结果的集合
	@Override
	public List<Project> getDataList(ResultSet rs) {
		List<Project> datalist = new ArrayList<Project>();
		try {
			while(rs.next()) {
				datalist.add(new Project(rs.getString("Psn"),rs.getString("Pname"),rs.getString("Pleader"), rs.getString("Pmember"),rs.getString("Pgrad"),rs.getString("Pkind"),rs.getInt("Pmoney"),
						rs.getDate("Pstatime"), rs.getString("Pcondition"),rs.getDate("Pendtime"),rs.getString("Premarks"),rs.getInt("totalRecord")
						,rs.getString("Paccessory"),rs.getString("message"),rs.getString("Paudit")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	//获取导出excel的集合
	@Override
	public List<ExcelProject> getExcelDataList(ResultSet rs) {
		List<ExcelProject> datalist = new ArrayList<ExcelProject>();
		try {
			while(rs.next()) {
				datalist.add(new ExcelProject(rs.getString("Psn"),rs.getString("Pname"),rs.getString("Pleader"), rs.getString("Pmember"),rs.getString("Pgrad"),rs.getString("Pkind"),rs.getInt("Pmoney"),
						rs.getDate("Pstatime"), rs.getString("Pcondition"),rs.getDate("Pendtime"),rs.getString("Premarks")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	
	//对未审核的项目进行审核
	@Override
	public int projectAudit(String Psn, String Paudit,String message) {
		System.out.println("message = " + message);
		String sql = "update Project set Paudit = ? ,message = ? where Psn = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Paudit);
			stmt.setString(2, message);	
			stmt.setString(3, Psn);
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//获取对应的附件路径
	public String getAccessory(String Psn) {
		String sql = "select Paccessory from Project where Psn = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Psn);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String accessory = rs.getString("Paccessory");
				return accessory;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
		
	//获取审核的详细信息
	@Override
	public List<Project> getlist(Project project) {
		String Paudit = util.disposeAuditValue(project.getPaudit());
		String Message = util.disposeMessageValue(project.getMessage());
		List<Project> datalist = new ArrayList<Project>();
		datalist.add(new Project(project.getPsn(),project.getPname(), project.getPleader(),project.getPmember(),project.getPgrad(),
				project.getPkind(),project.getPmoney(),project.getPstatime(),project.getPcondition(),project.getPendtime(),
				project.getPremarks(),project.getTotalRecord(),project.getPaccessory(),Message,Paudit));
		return datalist;
	}
		
	//保存上传的附件的路径
	@Override
	public void saveFilePath(String path,String psn) {
		String sql = "update Project set Paccessory = ? where Psn = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, path);
			stmt.setString(2, psn);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
