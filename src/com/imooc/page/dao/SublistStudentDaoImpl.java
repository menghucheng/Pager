/**
 * 使用sublist实现分页 即 一次取得所有的数据，再用返回对应的数据
 */
package com.imooc.page.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.imooc.page.constant.Constant;
import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.util.JdbcUtil;

public class SublistStudentDaoImpl implements StudentDao {

	public Pager<Student> findStudent(Student searchModel, int pageNum, int pageSize) {
		List<Student> allStudentList =  getAllStudent(searchModel);
		Pager<Student> pager = new Pager<Student>(pageNum, pageSize, allStudentList);
		return pager;
	}
	/**
	 * 模仿获取所有数据
	 * @param searchModel 查询参数
	 * @return 查询结果
	 */
	private static List<Student> getAllStudent(Student searchModel){
		List<Student> result = new ArrayList<Student>();
		List<Object> paramList = new ArrayList<Object>();
		
		String name = searchModel.getStuName();
		int gender = searchModel.getGender();
		
		//StringBuilder是线程不安全的 但是比StringBuffer快
		StringBuilder sql = new StringBuilder("select * from t_student where 1=1 ");
		if(name != null && !name.equals("")){
			sql.append(" and stu_name like ?");//模糊查询用like  "?"是占位符
			paramList.add("%" + name + "%");
		}
		
		if(gender == Constant.GENDER_MALE  ||  gender == Constant.GENDER_FEMALE){
			sql.append(" and gender = ?");//模糊查询用like  "?"是占位符
			paramList.add(gender);
		}
		
		JdbcUtil  jdbcUtil = null;
		try {
			jdbcUtil	= new JdbcUtil();
			//获取数据库链接
			JdbcUtil.getConnection();
			List<Map<String, Object>> resultList = jdbcUtil.findResult(sql.toString(), paramList);
			if(resultList != null){
				for(Map<String,Object> map : resultList){
					Student s = new Student(map);
					result.add(s);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常",e);
		}finally{
			if(jdbcUtil != null){
				jdbcUtil.releaseConn();//释放资源
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<Student> lst = getAllStudent(new Student());
		for (Student student : lst) {
			System.out.println(student);
		}
	}

}
