package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import dao.IBaseDao;
import dao.IPatentDao;
import model.ExcelPatent;
import model.Pager;
import model.Patent;
import util.CommonUnit;
import util.DbUtil;

//对应专利的各种数据库操作
public class PatentDaoImpl implements IPatentDao{
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	CommonUnit commondao = new CommonUnit();
	IBaseDao baseDao = new BaseDaoImpl();
	
	@Override
	public int delPatent(String Patsn) {		//根据授权号删除对应的专利信息
		String sql = "delete from Patent where Patsn = ?";		
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Patsn);
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
	public int alterPatent(Patent patent) throws SQLException{		//根据授权号修改对应的修改专利内容
		String Tsn = teacherdao.getTsn(patent.getPleader().trim());			//获取该专利第一所有者的教师号
		String sql = "update Patent set Patname = ?,Patapdate = ?,Patendate = ?,Patgrad = ?,Patremarks = ?,Tsn = ? "
				+ "where Patsn = ?";
		Date Patapdate = commondao.utilToSql(patent.getPatapdate());
		java.sql.Date Patendate = new java.sql.Date(patent.getPatemdate().getTime());
		List params = Arrays.asList(patent.getPatname().trim(),Patapdate,Patendate,patent.getPatgrad().trim(),patent.getPatremarks().trim(),Tsn,patent.getPatsn().trim());
		return dbUtil.getUpdateResult(sql, params);
		
	}
	
	@Override
	public int insertPatent(Patent patent) {						//插入新的专利信息
		try {
			String Tsn = teacherdao.getTsn(patent.getPatsn().trim());		//获取该专利第一所有者的教师号
			String sql = "insert into Patent (Patname,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Tsn) Values(?,?,?,?,?,?,?)";
			Date Patapdate = commondao.utilToSql(patent.getPatapdate());
			stmt.setDate(3,Patapdate);
			java.sql.Date Patendate = new java.sql.Date(patent.getPatemdate().getTime());
			List params = Arrays.asList(patent.getPatname().trim(),patent.getPatsn().trim(),Patapdate,Patendate,patent.getPatgrad().trim(),patent.getPatremarks().trim(),Tsn);
			return dbUtil.getUpdateResult(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	//返回当前显示页的数据
	//currentPage：当前页（页码），pageSize：每页显示数据条数
	//college：所属学院，sdept：所属系，starttime：开始时间，endtime：结束时间，Tname：搜索的教师名
	@Override
	public ResultSet selectCollegePatent(String college,String sdept,String starttime,String endtime,String Tname,int currentPage,int pageSize) throws SQLException {		//根据查询条件的改变显示不同的查询结果
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
		String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ," + 
		"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn ) as pp where pp.r between ? and ?";
		//设置了要查询的学院
		String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,Cname," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p  left join Teacher t on p.Tsn = t.Tsn "
				+ "join College c on t.Csn = c.Csn where c.Cname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业
		String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ,Cname,Dname," 
				+ "ROW_NUMBER() over (order by Patnum desc) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join Sdept s "
				+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ?) as pp where pp.r between ? and ?";	
		//设置了要查询的起止时间
		String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn  where Patapdate >= ? and Patendate <= ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间
		String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,"
				+	"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ 	"on t.Csn = c.Csn where c.Cname = ? and Patapdate >= ? and Patendate <= ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间
		String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,Dname,"
				+	"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Patapdate >= ? and Patendate <= ?) as pp where pp.r between ? and ?";	
		//没有设置要查询的教师名
		String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn where Tname = ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和教师名
		String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,Cname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//没有设置要查询的学院和专业和教师名
		String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,Dname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的起止时间和教师名
		String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn "
				+ "where  Patapdate >= ? and Patendate <= ? and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间和教师名
		String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " where  c.Cname = ? and Patapdate >= ? and Patendate <= ? and Tname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间和教师名
		String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,Cname,Dname ," + 
				"ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and Patapdate >= ? and Patendate <= ? and Tname = ?) as pp where pp.r between ? and ?"; ;	
		try {
		if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
			if(college ==  null && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
				System.out.println("1");
				List params = Arrays.asList(m,n);
				return dbUtil.getResultSet(sql1, params);
			}else if(!college.equals("") && sdept == null && starttime == null || !college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院
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
			baseDao.closeCon();
		}
		
		return null;
	}
	

	@Override	
	public List<Patent> getDataList(ResultSet rs){	//将结果集转化为集合
		List<Patent> datalist = new ArrayList<Patent>();
		try {
			while(rs.next()) {
				datalist.add(new Patent(rs.getString("Patname"),rs.getString("Tname"), rs.getString("Patsn"),rs.getDate("Patendate"),
						rs.getDate("Patapdate"), rs.getString("Patgrad"),rs.getString("Patremarks"),rs.getString("Paccessory"),rs.getInt("totalRecord")));
			}
			return datalist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datalist;
	}
	
	
	public static void main(String[] args) {

	}



}
