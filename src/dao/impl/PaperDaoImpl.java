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
import model.ExcelPaper;
import model.Paper;
import util.CommonUtil;
import util.DbUtil;

public class PaperDaoImpl implements IPaperDao{
	
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	CommonUtil util = new CommonUtil();
	IBaseDao baseDao = new BaseDaoImpl();

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
		String sql = "update paper set Paname = ?,Pawriter = ?,Padate = ?,Papublish = ? ,Pagrad = ?,Paremarks = ?,Pdisvol = ?,Tsn = ?,Paudit = 0"
				+ "where Pasearchnum = ?";
		Date Padate = util.utilToSql(paper.getPadate());
		List params = Arrays.asList(paper.getPaname(),paper.getPawriter(),Padate,paper.getPapublish(),paper.getPagrad(),
				paper.getParemarks(),paper.getPdisvol(),Tsn,paper.getPasearchnum());
		return dbUtil.getUpdateResult(sql, params);
	}

	@Override
	public int insertPaper(Paper paper) throws SQLException {
		String Tsn = teacherdao.getTsn(paper.getPawriter().trim());			//获取该论文作者对应的教师号
		String sql = "insert into paper (Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Tsn,Paudit) Values(?,?,?,?,?,?,?,?,?,?)";
		Date Padate = util.utilToSql(paper.getPadate());
		List params = Arrays.asList(paper.getPasearchnum(),paper.getPaname(),paper.getPawriter(),paper.getPapublish(),Padate,paper.getPagrad(),paper.getParemarks(),
				paper.getPdisvol(),Tsn,0);
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
		college = CommonUtil.disposePageValue(college);
		sdept = CommonUtil.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
		sdept = CommonUtil.disposeSdeptValue(sdept);
		endtime = CommonUtil.disposePageValue(endtime);
		Tname = CommonUtil.disposePageValue(Tname);
		//什么都没有设置
		String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Paccessory,Paudit,message"
				+ ",ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn ) as pp where pp.r "
				+ "between ? and ?";
		//设置了要查询的学院
		String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Cname,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p  left join Teacher t on p.Tsn = t.Tsn "
				+ "join College c on t.Csn = c.Csn where c.Cname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业
		String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Dname,Paccessory,Paudit,message," 
				+ "ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join Sdept s "
				+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ?) as pp where pp.r between ? and ?";	
		//设置了要查询的起止时间
		String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Pdisvol,Paremarks,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn  where Padate <= ? ) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间
		String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Cname,Paccessory,Paudit,message,"
				+	"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ 	"on t.Csn = c.Csn where c.Cname = ? and Padate <= ? ) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间
		String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Dname,Pdisvol,Paccessory,Paudit,message,"
				+	"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Padate <= ? ) as pp where pp.r between ? and ?";	
		//没有设置要查询的教师名
		String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn where Tname = ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院和教师名
		String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Cname,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//没有设置要查询的学院和专业和教师名
		String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Dname,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c "
				+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的起止时间和教师名
		String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn "
				+ "where  Padate <= ?  and Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的学院和起止时间和教师名
		String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Cname,Pdisvol,Paccessory,Paudit,message," + 
				"ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " where  c.Cname = ? and Padate <= ?  and Tname = ?) as pp where pp.r between ? and ?"; 
		//设置了要查询的学院和专业和起止时间和教师名
		String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Cname,Dname,Paccessory,Paudit,message" + 
				",ROW_NUMBER() over (order by Pasearchnum) as r from paper p left join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn"
				+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and Padate <= ? and Tname = ?) as pp where pp.r between ? and ?"; ;	
		try {
		if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
			if(college ==  null && sdept == null && endtime == null || college.equals("") && sdept.equals("") && endtime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
				List params = Arrays.asList(m,n);
				return dbUtil.getResultSet(sql1, params);
			}else if(!college.equals("") && sdept == null && endtime == null ||!college.equals("") && sdept.equals("") && endtime.equals("")) {	//只选了学院
				List params = Arrays.asList(college,m,n);
				return dbUtil.getResultSet(sql2, params);
			}else if(!college.equals("") && !sdept.equals("") && endtime.equals("")) {			//选了学院和专业
				List params = Arrays.asList(college,sdept,m,n);
				return dbUtil.getResultSet(sql3, params);
			}else if(college.equals("") && sdept.equals("") && !endtime.equals("")) {		//只选了起止时间
				List params = Arrays.asList(endtime,m,n);
				return dbUtil.getResultSet(sql4, params);
			}else if(!college.equals("") && sdept.equals("") && !endtime.equals("")) {	//选了学院和起止时间
				List params = Arrays.asList(college,endtime,m,n);
				return dbUtil.getResultSet(sql5, params);
			}else if(!college.equals("") && !sdept.equals("") && !endtime.equals("")) {	//选了学院和专业和起止时间
				List params = Arrays.asList(college,sdept,endtime,m,n);
				return dbUtil.getResultSet(sql6, params);
			}
		}else {
			if(college.equals("") && sdept == null && endtime == null || college.equals("") && sdept.equals("") && endtime.equals("")) {							//选了教师名				
				List params = Arrays.asList(Tname,m,n);
				return dbUtil.getResultSet(sql7, params);
			}else if(!college.equals("") && sdept.equals("") && endtime.equals("")) {	//只选了学院和教师名
				List params = Arrays.asList(college,Tname,m,n);
				return dbUtil.getResultSet(sql8, params);
			}else if(!college.equals("") && !sdept.equals("") && endtime.equals("")) {			//选了学院和专业和教师名
				List params = Arrays.asList(college,sdept,Tname,m,n);
				return dbUtil.getResultSet(sql9, params);
			}else if(college.equals("") && sdept.equals("") && !endtime.equals("")) {		//只选了起止时间和教师名
				List params = Arrays.asList(endtime,Tname,m,n);
				return dbUtil.getResultSet(sql10, params);
			}else if(!college.equals("") && sdept.equals("") && !endtime.equals("")) {	//选了学院和起止时间和教师名
				List params = Arrays.asList(college,endtime,Tname,m,n);
				return dbUtil.getResultSet(sql11, params);
			}else if(!college.equals("") && !sdept.equals("") && !endtime.equals("")) {	//选了学院和专业和起止时间和教师名
				List params = Arrays.asList(college,sdept,endtime,Tname,m,n);
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
	
	
	//将结果集转化为集合
	@Override
	public List<Paper> getDataList(ResultSet rs) {
		List<Paper> datalist = new ArrayList<Paper>();
		try {
			while(rs.next()) {
				datalist.add(new Paper(rs.getString("Pasearchnum"),rs.getString("Paname"), rs.getString("Pawriter"),rs.getString("Papublish"),
						rs.getString("Pagrad"),rs.getDate("Padate"),rs.getString("Paremarks"),rs.getInt("totalRecord"),rs.getString("Paccessory")
						,rs.getString("message"),rs.getString("Paudit")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	//获取导出excel的集合
	@Override
	public List<ExcelPaper> getExcelDataList(ResultSet rs) {
		List<ExcelPaper> datalist = new ArrayList<ExcelPaper>();
		try {
			while(rs.next()) {
				datalist.add(new ExcelPaper(rs.getString("Pasearchnum"),rs.getString("Paname"), rs.getString("Pawriter"),rs.getString("Papublish"),
						rs.getString("Pagrad"),rs.getDate("Padate"),rs.getString("Paremarks")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	//对未审核的论文进行审核
	@Override
	public int paperAudit(String Pasearchnum,String Paudit,String Message) {
		String sql = "update Paper set Paudit = ? ,message = ? where Pasearchnum = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Paudit);
			stmt.setString(2, Message);	
			stmt.setString(3, Pasearchnum);
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
	}
	
	//获取审核的详细信息
	@Override
	public List<Paper> getlist(Paper paper) {
		String Paudit = util.disposeAuditValue(paper.getPaudit());
		String Message = util.disposeMessageValue(paper.getMessage());
		List<Paper> datalist = new ArrayList<Paper>();
		datalist.add(new Paper(paper.getPasearchnum(),paper.getPaname(), paper.getPawriter(),paper.getPapublish(),
				paper.getPagrad(),paper.getPadate(),paper.getParemarks(),paper.getTotalRecord(),paper.getPaccessory(),Message
				,Paudit));
		return datalist;
	}
	
	
	//获取对应的附件路径
	public String getAccessory(String Pasearchnum) {
		String sql = "select Paccessory from Paper where Pasearchnum = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Pasearchnum);
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
	
	//保存上传的附件的路径
	@Override
	public void saveFilePath(String path,String pasearchnum) {
		String sql = "update Paper set Paccessory = ? where Pasearchnum = ?";
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, path);
			stmt.setString(2, pasearchnum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			
			
}
