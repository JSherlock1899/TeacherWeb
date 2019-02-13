package dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//导入excel
public interface IExcelDao {
	
	public int insertPatentValues(List list);
		
	
	
	public int insertHonorValues(List list) ;
	
	
	public int insertProjectValues(List list) ;
	
	
	public int insertPaperValues(List list) ;
	
}
