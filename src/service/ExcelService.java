package service;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import dao.IExcelDao;
import dao.impl.ExcelDaoImpl;

public class ExcelService {
	
	IExcelDao excelDao = new ExcelDaoImpl();
	public int insertPatentValues(List list){
		return excelDao.insertPatentValues(list);
	}
	
	
	public int insertHonorValues(List list) {
		return excelDao.insertHonorValues(list);
	}
	
	

	public int insertProjectValues(List list) {
		return excelDao.insertProjectValues(list);
	}

	public int insertPaperValues(List list) {
		return excelDao.insertPaperValues(list);
	}
	
	public int insertTeacherValues(List list) {
		return excelDao.insertTeacherValues(list);
	}
}
