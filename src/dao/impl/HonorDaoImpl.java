package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.IBaseDao;
import dao.IHonorDao;
import model.Honor;
import model.Patent;
import util.CommonUtil;
import util.DbUtil;

public class HonorDaoImpl implements IHonorDao{
	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	CommonUtil commondao = new CommonUtil();
	
	@Override
	public int delHonor(String Hsn) {			//根据荣誉号删除对应的荣誉信息
		String sql = "delete from Honor where Hsn = ?";		
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Hsn);
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
	public int alterHonor(Honor honor) throws SQLException {		//根据荣誉号修改对应的荣誉信息
		String Tsn = teacherdao.getTsn(honor.getHwinner().trim());			//获取该荣誉获得者对应的教师号
		String sql = "update Honor set Hname = ?,Hwinner = ?,Hdate = ?,Hcompany = ?,Hgrad = ?,Hreward = ?,Hremarks = ?,Tsn = ? "
				+ "where Hsn = ?";
		Date Hdate = commondao.utilToSql(honor.getHdate());
		List params = Arrays.asList(honor.getHname(),honor.getHwinner(),Hdate,honor.getHcompany(),honor.getHgrad(),
				honor.getHreward(),honor.getHremarks(),Tsn,honor.getHsn());
		return dbUtil.getUpdateResult(sql, params);
	}
	
	
	@Override
	public int insertHonor(Honor honor) throws SQLException {		//插入新的荣誉信息
		String Tsn = teacherdao.getTsn(honor.getHwinner().trim());			//获取该荣誉获得者对应的教师号
		String sql = "insert into Honor (Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Tsn,Haudit) Values(?,?,?,?,?,?,?,?,?,?)";
		Date Hdate = commondao.utilToSql(honor.getHdate());
		List params = Arrays.asList(honor.getHsn(),honor.getHname(),honor.getHwinner(),Hdate,honor.getHcompany(),honor.getHgrad(),
				honor.getHreward(),honor.getHremarks(),Tsn,0);
		return dbUtil.getUpdateResult(sql, params);
	}
	
	
	
		//currentPage：当前页（页码），pageSize：每页显示数据条数
		//college：所属学院，sdept：所属系，starttime：截止时间，Tname：搜索的教师名
		@Override
		public ResultSet selectCollegeHonor(String college,String sdept,String starttime,String Tname,int currentPage,int pageSize) throws SQLException {		//根据查询条件的改变显示不同的查询结果
			//当前页的第一条记录
			int m = (currentPage - 1) * pageSize + 1;
			//当前页的最后一条记录
			int n = currentPage * pageSize;
			IBaseDao baseDao = new BaseDaoImpl();
			college = CommonUtil.disposePageValue(college);
			sdept = CommonUtil.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
			sdept = CommonUtil.disposeSdeptValue(sdept);
			starttime = CommonUtil.disposePageValue(starttime);
			Tname = CommonUtil.disposePageValue(Tname);
//			System.out.println("college = " + college);
//			System.out.println("sdept = " + sdept);
//			System.out.println("starttime = " + starttime);
//			System.out.println("Tname = " + Tname);
			//什么都没有设置
			String sql1 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Haccessory,Hremarks "
					+ ",ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn ) as pp where pp.r "
					+ "between ? and ?";
			//设置了要查询的学院
			String sql2 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Haccessory,Hremarks,Cname ," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h  left join Teacher t on h.Tsn = t.Tsn "
					+ "join College c on t.Csn = c.Csn where c.Cname = ?) as pp where pp.r between ? and ?"; 
			//设置了要查询的学院和专业
			String sql3 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Haccessory,Hremarks,Dname ," 
					+ "ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join Sdept s "
					+ "on t.Dsn = s.Dsn join College c on t.Csn = c.Csn where Cname = ? and Dname = ?) as pp where pp.r between ? and ?";	
			//设置了要查询的起止时间
			String sql4 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Haccessory,Hremarks  ," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn  where Hdate <= ? ) "
					+ "as pp where pp.r between ? and ?";
			//设置了要查询的学院和起止时间
			String sql5 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Cname ,"
					+	"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c "
					+ 	"on t.Csn = c.Csn where c.Cname = ? and Hdate <= ? ) as pp where pp.r between ? and ?"; 
			//设置了要查询的学院和专业和起止时间
			String sql6 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Dname ,"
					+	"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c "
					+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ? and Hdate <= ? ) as pp where pp.r between ? and ?";	
			//没有设置要查询的教师名
			String sql7 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory  ," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn where Tname = ?) "
					+ "as pp where pp.r between ? and ?";
			//设置了要查询的学院和教师名
			String sql8 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Cname  ," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c "
					+ "on t.Csn = c.Csn where c.Cname = ? and Tname = ?) as pp where pp.r between ? and ?";
			//没有设置要查询的学院和专业和教师名
			String sql9 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Dname" + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c "
					+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where c.Cname = ? and  Dname = ? and Tname = ?) as pp where pp.r between ? and ?";
			//设置了要查询的起止时间和教师名
			String sql10 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn "
					+ "where  Hdate <= ?  and Tname = ?) as pp where pp.r between ? and ?";
			//设置了要查询的学院和起止时间和教师名
			String sql11 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Cname ," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn"
					+ " where  c.Cname = ? and Hdate <= ?  and Tname = ?) as pp where pp.r between ? and ?"; 
			//设置了要查询的学院和专业和起止时间和教师名
			String sql12 = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Cname,Dname," + 
					"ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn"
					+ " join Sdept s on t.Dsn = s.Dsn  where  c.Cname = ? and Dname = ? and Hdate <= ? and Tname = ?) as pp where pp.r between ? and ?"; ;	
			try {
			if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
				if(college ==  null && sdept == null && starttime == null || college.equals("") && sdept.equals("") && starttime.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有专利信息
					System.out.println("1");
					List params = Arrays.asList(m,n);
					return dbUtil.getResultSet(sql1, params);
				}else if(!college.equals("") && sdept == null && starttime == null ||!college.equals("") && sdept.equals("") && starttime.equals("")) {	//只选了学院
					System.out.println("2");
					List params = Arrays.asList(college,m,n);
					return dbUtil.getResultSet(sql2, params);
				}else if(!college.equals("") && !sdept.equals("") && starttime.equals("")) {			//选了学院和专业
					System.out.println("3");
					List params = Arrays.asList(college,sdept,m,n);
					return dbUtil.getResultSet(sql3, params);
				}else if(college.equals("") && sdept.equals("") && !starttime.equals("")) {		//只选了起止时间
					System.out.println("4");
					List params = Arrays.asList(starttime,m,n);
					return dbUtil.getResultSet(sql4, params);
				}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间
					System.out.println("5");
					List params = Arrays.asList(college,starttime,m,n);
					return dbUtil.getResultSet(sql5, params);
				}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间
					System.out.println("6");
					List params = Arrays.asList(college,sdept,starttime,m,n);
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
					List params = Arrays.asList(starttime,Tname,m,n);
					return dbUtil.getResultSet(sql10, params);
				}else if(!college.equals("") && sdept.equals("") && !starttime.equals("")) {	//选了学院和起止时间和教师名
					System.out.println("11");
					List params = Arrays.asList(college,starttime,Tname,m,n);
					return dbUtil.getResultSet(sql11, params);
				}else if(!college.equals("") && !sdept.equals("") && !starttime.equals("")) {	//选了学院和专业和起止时间和教师名
					System.out.println("12");
					List params = Arrays.asList(college,sdept,starttime,Tname,m,n);
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
		public List<Honor> getDataList(ResultSet rs){	//将结果集转化为集合
			List<Honor> datalist = new ArrayList<Honor>();
			try {
				while(rs.next()) {
					datalist.add(new Honor(rs.getString("Hsn"),rs.getString("Hname"), rs.getString("Hwinner"),rs.getDate("Hdate"),
							rs.getString("Hcompany"),rs.getString("Hgrad"),rs.getString("Hremarks"),rs.getString("Hremarks"),rs.getString("Haccessory"),rs.getInt("totalRecord")));
				}
				return datalist;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return datalist;
		}


		//对未审核的专利进行审核
		@Override
		public int honorAudit(String Hsn, String Paudit) {
			String sql = "update Honor set Haudit = ? where Hsn = ?";
			try {
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, Paudit);
				stmt.setString(2, Hsn);
				int result = stmt.executeUpdate();
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			return 0;
		}
}
