/**
 * 自定义jdbc工具类
 */
package com.imooc.page.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JdbcUtil {
	
	private static Logger LOG = Logger.getLogger(JdbcUtil.class);
	
	/** 用户名*/
	private static String USERNAME;
	
	/** 密码*/
	private static String PASSWORD;

	/** 驱动*/
	private static String DRIVERCLASS;
	
	/** Url*/
	private static String URL;
	
	/** 数据库链接*/
	private static Connection connection;
	
	/**定义sql语句的执行对象*/
	private static PreparedStatement pstmt;
	
	/** 定义查询返回的结果集合*/
	private static ResultSet resultSet;
	
	/** 使用静态块初始化*/
	static{
		LOG.debug("initial jdbcUtils。。");
		try {
//			加载jdbc.properties
				Properties p = new Properties();
				InputStream inStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
				p.load(inStream);
				USERNAME = p.getProperty("jdbc.user");
				PASSWORD = p.getProperty("jdbc.password");
				URL = p.getProperty("jdbc.url");
				DRIVERCLASS = p.getProperty("jdbc.driver");
				connection  = getConnection();
			} catch (IOException e) {
				LOG.error("加载jdbc.properties文件错误！",e);
				throw new RuntimeException("加载jdbc.properties文件错误！",e);
			}
	}
	
	public JdbcUtil(){
	
	}
	/**
	 * 获取数据库链接
	 * @return 数据库链接
	 */
	public static Connection getConnection(){
		try{
			Class.forName(DRIVERCLASS);
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		}catch(Exception e){
			LOG.error("获取链接失败！");
			throw new RuntimeException("get Mysql connection fail!",e);
		}
		return connection;
	}
	
	/**
	 * 执行更新操作
	 * @param sql sql语句
	 * @param params 执行参数
	 * @return 执行结果
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<?> params) throws SQLException{
		boolean flag = false;
		int result = -1 ;//表示当用户执行添加删除和修改的时候所影响数据库的行数
		 pstmt = connection.prepareStatement(sql);
		 int index = 1;
		 // 填充sql语句中的占位符
		 if(params != null && !params.isEmpty()){
			 for (int i = 0; i < params.size(); i++) {
				 pstmt.setObject(index++, params.get(i));
			}
		 }
		 result = pstmt.executeUpdate();
		 flag = result > 0 ? true : false;
		 return flag;
	}
	
	/**
	 * 执行查询操作
	 * @param sql sql语句
	 * @param params 执行参数
	 * @return 查询结果
	 * @throws SQLException
	 */
	public List<Map<String,Object>> findResult(String sql, List<?> params) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String ,Object>>();
		 pstmt = connection.prepareStatement(sql);
		 int index = 1;
		 if(params != null && !params.isEmpty()){
			 for (int i = 0; i < params.size(); i++) {
				 pstmt.setObject(index++, params.get(i));
			}
		 }
		 resultSet = pstmt.executeQuery();
		 ResultSetMetaData metaData = resultSet.getMetaData();
		 int cols_len = metaData.getColumnCount();
		 while(resultSet.next()){
			 Map<String,Object> map = new HashMap<String,Object>();
			 for (int i = 0; i < cols_len ; i++) {
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			 list.add(map);
		 }
		 return list;
	}
	
	/** 释放资源*/
	public void releaseConn(){
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException e){
				LOG.error("释放jdbc resultSet链接出错",e);
			}
		}
		if(resultSet != null){
			try{
				pstmt.close();
			}catch(SQLException e){
				LOG.error("释放jdbc pstmt链接出错",e);
			}
		}
		if(resultSet != null){
			try{
				connection.close();
			}catch(SQLException e){
				LOG.error("释放jdbc connection链接出错",e);
			}
		}
	}
}
