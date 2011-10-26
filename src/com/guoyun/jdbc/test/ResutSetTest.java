package com.guoyun.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class ResutSetTest {
	public static final String connectionUrl="jdbc:mysql://localhost/sqoop_datas";
	public static final String userName="root";
	public static final String userPwd="123456";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		ResultSetMetaData metaData=null;
		
		String query="select sqoop_source_datas.id as foo_id, sqoop_2.id as bar_id from sqoop_source_datas ,sqoop_2";
		
		try {
			con=DriverManager.getConnection(connectionUrl, userName, userPwd);
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			
			if(rs!=null){
				metaData=rs.getMetaData();
				
				int cols=metaData.getColumnCount();
				for(int i=1;i<=cols;i++){
					System.out.println(metaData.getColumnClassName(i)+"->"+
							metaData.getColumnLabel(i)+"->"+metaData.getColumnName(i)+"->"+
							metaData.getSchemaName(i)+"->"+metaData.getTableName(i));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
