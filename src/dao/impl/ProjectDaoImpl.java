package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.BaseDao;
import dao.IProjectDao;
import dao.TeacherDao;
import model.Patent;
import model.Project;
import util.CommonUnit;
import util.DbUtil;

public class ProjectDaoImpl implements IProjectDao{
	
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDao teacherdao = new TeacherDao();
	BaseDao basedao = new BaseDao();
	CommonUnit commondao = new CommonUnit();
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
		String sql = "update Project set Pname = ?,Pleader = ?,Pmember = ?,Pgrad = ?,Pkind = ?,Pmoney = ?,Pstatime = ?,Pcondition = ?,Pendtime = ?,Premarks = ?,Tsn = ? "
				+ "where Psn = ?";
		Date Pstatime = commondao.utilToSql(project.getPstatime());
		Date Pendtime = commondao.utilToSql(project.getPendtime());
		List params = Arrays.asList(project.getPname(),project.getPleader(),project.getPmember(),project.getPgrad(),project.getPkind(),
				project.getPmoney(),Pstatime,project.getPcondition(),Pendtime,project.getPremarks(),Tsn,project.getPsn());
		return dbUtil.getUpdateResult(sql, params);
	}

	@Override
	public int insertProject(Project project) throws SQLException {
		String Tsn = teacherdao.getTsn(project.getPleader().trim());			//获取该项目负责人对应的教师号
		String sql = "insert into Project (Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tsn) Values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Date Pstatime = commondao.utilToSql(project.getPstatime());
		Date Pendtime = commondao.utilToSql(project.getPendtime());
		List params = Arrays.asList(project.getPsn(),project.getPname(),project.getPleader(),project.getPmember(),project.getPgrad(),project.getPkind(),
				project.getPmoney(),Pstatime,project.getPcondition(),Pendtime,project.getPremarks(),Tsn);
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
				college = CommonUnit.disposePageValue(college);
				sdept = CommonUnit.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
				sdept = CommonUnit.disposeSdeptValue(sdept);
				starttime = CommonUnit.disposePageValue(starttime);
				endtime = CommonUnit.disposePageValue(endtime);
				Tname = CommonUnit.disposePageValue(Tname);
				System.out.println("college =" + college);
				System.out.println("sdept =" + sdept);
				System.out.println("starttime =" + starttime);
				System.out.println("endtime =" + endtime);
				System.out.println("Tname =" + Tname);
				//什么都没有设置
				String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ," + 
				"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn ) as pp where pp.r between ? and ?";
				//设置了要查询的学院
				String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p  left join Teacher t on p.Tsn = t.Tsn "
						+ "join College c on t.Csn = c.Csn where c.Cname = ?) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业
				String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ,Cname,Dname," 
						+ "ROW_NUMBER() over (order by Psn desc) as r from Project p left join Teacher t on p.Tsn = t.Tsn join Sdept s "
						+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ?) as pp where pp.r between ? and ?";	
				//设置了要查询的起止时间
				String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn  where Pstatime >= ? and Pendtime <= ?) "
						+ "as pp where pp.r between ? and ?";
				//设置了要查询的学院和起止时间
				String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,"
						+	"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ 	"on t.Csn = c.Csn where c.Cname = ? and Pstatime >= ? and Pendtime <= ?) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业和起止时间
				String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Dname,"
						+	"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Pstatime >= ? and Pendtime <= ?) as pp where pp.r between ? and ?";	
				//没有设置要查询的教师名
				String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn where Tname = ?) "
						+ "as pp where pp.r between ? and ?";
				//设置了要查询的学院和教师名
				String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ?) as pp where pp.r between ? and ?";
				//没有设置要查询的学院和专业和教师名
				String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Dname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ?) as pp where pp.r between ? and ?";
				//设置了要查询的起止时间和教师名
				String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn "
						+ "where  Pstatime >= ? and Pendtime <= ? and Tname = ?) as pp where pp.r between ? and ?";
				//设置了要查询的学院和起止时间和教师名
				String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
						+ " where  c.Cname = ? and Pstatime >= ? and Pendtime <= ? and Tname = ?) as pp where pp.r between ? and ?"; 
				//设置了要查询的学院和专业和起止时间和教师名
				String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname,Cname,Dname ," + 
						"ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
						+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and    >= ? and Pendtime <= ? and Tname = ?) as pp where pp.r between ? and ?"; ;	
				try {
				if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
					if(college ==  null && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
						System.out.println("1");
						List params = Arrays.asList(m,n);
						return dbUtil.getResultSet(sql1, params);
					}else if(!college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院
						System.out.println("2");
						List params = Arrays.asList(college,m,n);
						return dbUtil.getResultSet(sql2, params);
					}else if(!college.equals("") && !sdept.equals("") && starttime.equals("")) {			//选了学院和专业
						System.out.println("3");
						List params = Arrays.asList(college,sdept,m,n);
						return dbUtil.getResultSet(sql3, params);
					}else if(college.equals("") && sdept.equals("") && !starttime.equals("")) {		//只选了起止时间
						System.out.println("4");
						List params = Arrays.asList(starttime,endtime,m,n);
						return dbUtil.getResultSet(sql4, params);
					}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间
						System.out.println("5");
						List params = Arrays.asList(college,starttime,endtime,m,n);
						return dbUtil.getResultSet(sql5, params);
					}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间
						System.out.println("6");
						List params = Arrays.asList(college,sdept,starttime,endtime,m,n);
						return dbUtil.getResultSet(sql6, params);
					}
				}else {
					if(college.equals("") && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("")) {							//选了教师名				
						System.out.println("7");
						List params = Arrays.asList(Tname,m,n);
						return dbUtil.getResultSet(sql7, params);
					}else if(!college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院和教师名
						System.out.println("8");
						List params = Arrays.asList(college,Tname,m,n);
						return dbUtil.getResultSet(sql8, params);
					}else if(!college.equals("") && !sdept.equals("") && starttime.equals("")) {			//选了学院和专业和教师名
						System.out.println("9");
						List params = Arrays.asList(college,sdept,Tname,m,n);
						return dbUtil.getResultSet(sql9, params);
					}else if(college.equals("") && sdept.equals("") && !starttime.equals("")) {		//只选了起止时间和教师名
						System.out.println("10");
						List params = Arrays.asList(starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql10, params);
					}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间和教师名
						System.out.println("11");
						List params = Arrays.asList(college,starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql11, params);
					}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间和教师名
						System.out.println("12");
						List params = Arrays.asList(college,sdept,starttime,endtime,Tname,m,n);
						return dbUtil.getResultSet(sql12, params);
					}
					System.out.println("0");
				}
				} catch (NullPointerException | SQLException e) {
					e.printStackTrace();
				}finally {
					basedao.closeCon();
				}
				return null;
	}

	@Override
	public List<Project> getDataList(ResultSet rs) {
		List<Project> datalist = new ArrayList<Project>();
		try {
			while(rs.next()) {
				datalist.add(new Project(rs.getString("Psn"),rs.getString("Pname"),rs.getString("Pleader"), rs.getString("Pmember"),rs.getString("Pgrad"),rs.getString("Pkind"),rs.getInt("Pmoney"),
						rs.getDate("Pstatime"), rs.getString("Pcondition"),rs.getDate("Pendtime"),rs.getString("Premarks"),rs.getInt("totalRecord")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}

}
