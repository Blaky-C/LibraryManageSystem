package condb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConOra {
	Connection conn = null;
	Statement stat = null;
	ResultSet result = null;
	
	//本地Oracle数据库
	/*String user = "scott";
    String pwd = "tiger";
    String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
    String className = "oracle.jdbc.driver.OracleDriver";*/
	
	//本地MySQL数据库
	String user = "rotom";
    String pwd = "123456";
    String url = "jdbc:mysql://localhost:3306/library_system";
    String className = "com.mysql.jdbc.Driver";
    
    public int num = 0;
    
    public void getCon() {
    	 try {
 	    	Class.forName(className);
 	    	System.out.println("驱动加载成功。");
 	    	conn = DriverManager.getConnection(url, user, pwd);
 	    	System.out.println("数据库连接成功。");
 	    }catch(Exception e) {
 	    	//e.printStackTrace();
 	    	System.out.println("数据库连接失败。"+e);
 	    }
    }
    
    public void getStatement() {
    	try {
			stat = conn.createStatement();
			System.out.println("Statement创建成功。");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Statement创建失败。"+e);
		}
    }
    
    //返回查询数据集
    public ResultSet getResult() {
    	return result;
    }
    
    //返回更新表结果
    public int getNum() {
    	return num;
    }
    
    public void query(String sql) {
	    try {
			result = stat.executeQuery(sql);
			System.out.println("查询成功。");
		} catch (Exception e) {
			System.out.println("查询失败。"+e);
		}
    }
    
    public void update(String sql) {
    	try {
    		num = stat.executeUpdate(sql);
    		System.out.println("数据更新成功");
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("数据更新失败");
    	}
    }
    
    public void close() {
    	try {
    		if (conn!=null) {
    			conn.close();
    		}
    		if (stat!=null) {
    			stat.close();
    		}
			if (result!=null) {
				result.close();
			}
			System.out.println("数据库关闭成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库关闭失败");
		}
    }
    
    
}
