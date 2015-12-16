package com.imooc.test;

import java.sql.Connection;

import com.imooc.page.util.JdbcUtil;

public class testConnection {
	public static void main(String[] args) {
		Connection connection = JdbcUtil.getConnection();

		if (connection == null) {
			System.out.println("error");
		} else {
			System.out.println("success");
		}
	}
}
