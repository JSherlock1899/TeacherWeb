package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.IBaseDao;
import dao.IPaperDao;
import model.Paper;
import util.CommonUnit;
import util.DbUtil;

public class PaperDaoImpl implements IPaperDao{
	
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	CommonUnit commondao = new CommonUnit();

	@Override
	public int delPaper(String Panum) {
		String sql = "delete from Paper where Pasearchnum = ?";		
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Panum);
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
	public int alterPaper(Paper paper) throws SQLException {
		String Tsn = teacherdao.getTsn(paper.getPawriter().trim());			//获取该论文作者对应的教师号
		String sql = "update paper set Paname = ?,Pawriter = ?,Padate = ?,Papublish = ? ,Pagrad = ?,Paremarks = ?,Tsn = ? "
				+ "where Pasearchnum = ?";
		Date Padate = commondao.utilToSql(paper.getPadate());
		List params = Arrays.asList(paper.getPaname(),paper.getPawriter(),Padate,paper.getPapublish(),paper.getPagrad(),
				paper.getParemarks(),Tsn,paper.getPasearchnum());
		return dbUtil.getUpdateResult(sql, params);
	}

	@Override
	public int insertPaper(Paper paper) throws SQLException {
		String Tsn = teacherdao.getTsn(paper.getPawriter().trim());			//获取该论文作者对应的教师号
		String sql = "insert into paper (Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Tsn) Values(?,?,?,?,?,?,?,?)";
		Date Padate = commondao.utilToSql(paper.getPadate());
		List params = Arrays.asList(paper.getPasearchnum(),paper.getPaname(),paper.getPawriter(),paper.getPapublish(),Padate,paper.getPagrad(),paper.getParemarks(),
						Tsn);
		return dbUtil.getUpdateResult(sql, params);
	}
	
	
	//currentPage：当前页（页码），pageSize：每页显示数据条数
	//college：所属学院，sdept：所属系，endendtime：论文发表时间，Tname：搜索的教师名
	@Override
	public ResultSet selectCollegePaper(String college, String sdept, String endtime, String Tname, int currentPage,
			int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		IBaseDao baseDao = new BaseDaoImpl();
		college = CommonUnit.disposePageValue(college);
		sdept = CommonUnit.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
		sdept = CommonUnit.disposeSdeptValue(sdept);
		endtime = CommonUnit.disposePageValue(endtime);
		Tname = CommonUnit.disposePageValue(Tname);
		System.out.println("college = " + college);
		System.out.println("sdept = " + sdept);
		System.out.println("endtime = " + endtime);
		System.out.println("Tname = " + Tname);
		//什么都没有设置
		String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks "
				+ ",ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn ) as pp where pp.r "
				+ "between ? and ?";
		//设置了要查询的学院
		String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname ," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p  left join Teacher t on p.Tsn = t.Tsn "
				+ "join College c on t.Csn = c.Csn where c.Cname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业
		String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Dname ," 
				+ "ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join Sdept s "
				+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ?) as pp where pp.r between ? and ?";	
		//设置了要查询的起止时间
		String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks  ," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn  where Padate <= ? ) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间
		String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname ,"
				+	"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ 	"on t.Csn = c.Csn where c.Cname = ? and Padate <= ? ) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间
		String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Dname ,"
				+	"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Padate <= ? ) as pp where pp.r between ? and ?";	
		//没有设置要查询的教师名
		String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks  ," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn where Tname = ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和教师名
		String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname  ," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//没有设置要查询的学院和专业和教师名
		String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Dname" + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的起止时间和教师名
		String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn "
				+ "where  Padate <= ?  and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间和教师名
		String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname ," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " where  c.Cname = ? and Padate <= ?  and Tname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间和教师名
		String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname,Dname" + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and Padate <= ? and Tname = ?) as pp where pp.r between ? and ?"; ;	
		try {
		if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
			if(college ==  null && sdept == null && endtime == null || college.equals("") && sdept.equals("") && endtime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
				System.out.println("1");
				List params = Arrays.asList(m,n);
				return dbUtil.getResultSet(sql1, params);
			}else if(!college.equals("") && sdept == null && endtime == null ||!college.equals("") && sdept.equals("") && endtime.equals("")) {	//只选了学院
				System.out.println("2");
				List params = Arrays.asList(college,m,n);
				return dbUtil.getResultSet(sql2, params);
			}else if(!college.equals("") && !sdept.equals("") && endtime.equals("")) {			//选了学院和专业
				System.out.println("3");
				List params = Arrays.asList(college,sdept,m,n);
				return dbUtil.getResultSet(sql3, params);
			}else if(college.equals("") && sdept.equals("") && !endtime.equals("")) {		//只选了起止时间
				System.out.println("4");
				List params = Arrays.asList(endtime,m,n);
				return dbUtil.getResultSet(sql4, params);
			}else if(!college.equals("") && sdept.equals("") && !endtime.equals("")) {	//选了学院和起止时间
				System.out.println("5");
				List params = Arrays.asList(college,endtime,m,n);
				return dbUtil.getResultSet(sql5, params);
			}else if(!college.equals("") && !sdept.equals("") && !endtime.equals("")) {	//选了学院和专业和起止时间
				System.out.println("6");
				List params = Arrays.asList(college,sdept,endtime,m,n);
				return dbUtil.getResultSet(sql6, params);
			}
		}else {
			if(college.equals("") && sdept == null && endtime == null || college.equals("") && sdept.equals("") && endtime.equals("")) {							//选了教师名				
				System.out.println("7");
				List params = Arrays.asList(Tname,m,n);
				return dbUtil.getResultSet(sql7, params);
			}else if(!college.equals("") && sdept.equals("") && endtime.equals("")) {	//只选了学院和教师名
				System.out.println("8");
				List params = Arrays.asList(college,Tname,m,n);
				return dbUtil.getResultSet(sql8, params);
			}else if(!college.equals("") && !sdept.equals("") && endtime.equals("")) {			//选了学院和专业和教师名
				System.out.println("9");
				List params = Arrays.asList(college,sdept,Tname,m,n);
				return dbUtil.getResultSet(sql9, params);
			}else if(college.equals("") && sdept.equals("") && !endtime.equals("")) {		//只选了起止时间和教师名
				System.out.println("10");
				List params = Arrays.asList(endtime,Tname,m,n);
				return dbUtil.getResultSet(sql10, params);
			}else if(!college.equals("") && sdept.equals("") && !endtime.equals("")) {	//选了学院和起止时间和教师名
				System.out.println("11");
				List params = Arrays.asList(college,endtime,Tname,m,n);
				return dbUtil.getResultSet(sql11, params);
			}else if(!college.equals("") && !sdept.equals("") && !endtime.equals("")) {	//选了学院和专业和起止时间和教师名
				System.out.println("12");
				List params = Arrays.asList(college,sdept,endtime,Tname,m,n);
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
	public List<Paper> getDataList(ResultSet rs) {
		List<Paper> datalist = new ArrayList<Paper>();
		try {
			while(rs.next()) {
				datalist.add(new Paper(rs.getString("Pasearchnum"),rs.getString("Paname"), rs.getString("Pawriter"),rs.getString("Papublish"),
						rs.getString("Pagrad"),rs.getDate("Padate"),rs.getString("Paremarks"),rs.getInt("totalRecord")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}

}
