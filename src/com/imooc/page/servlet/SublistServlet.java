package com.imooc.page.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.page.constant.Constant;
import com.imooc.page.model.Pager;
import com.imooc.page.model.Student;
import com.imooc.page.service.StudentService;
import com.imooc.page.service.SublistStudentServiceImpl;
import com.imooc.page.util.StringUtil;

public class SublistServlet extends HttpServlet{

	private static final long serialVersionUID = 5893579854047776499L;
	
	private StudentService studentService = new SublistStudentServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收request里的参数
		String stuName = req.getParameter("stuName");
		
		//获取学生性别
		int gender = Constant.DEFAULT_GENDER;
		String genderStr = req.getParameter("gender");
		if(genderStr != null && !"".equals(genderStr.trim())){
			gender = Integer.parseInt(genderStr);
		}
		
		//校验pageNUm参数输入合法性
		String pageNumStr = req.getParameter("pageNum");
		if(pageNumStr != null && !StringUtil.isNum(pageNumStr)){
			req.setAttribute("errorMsg", "参数传输错误");
			req.getRequestDispatcher("sublistStudent.jsp").forward(req, resp); //转发
			return;
		}
		
		int pageNum = Constant.DEFAULT_PAGE_NUM;//显示第几页数据
		if(pageNumStr != null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = Constant.DEFAULT_PAGE_SIZE;//每页显示多少条记录
		String pageSizeStr = req.getParameter("pageSize");
		if(pageSizeStr != null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		//组装查询条件
		Student searchModel = new Student();
		searchModel.setStuName(stuName);
		searchModel.setGender(gender);
		
		//调用service获取查询结果
		Pager<Student> result = studentService.findStudent(searchModel, pageNum, pageSize);
		
		//返回结果到页面
		req.setAttribute("result", result);
		req.setAttribute("stuName", stuName);
		req.setAttribute("gender", gender);
		
		req.getRequestDispatcher("sublistStudent.jsp").forward(req, resp);
	}
}
