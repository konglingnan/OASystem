package com.icss.oa.car.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icss.oa.car.pojo.Car;
import com.icss.oa.car.service.CarService;
import com.icss.oa.common.Pager;

/**
 * 汽车照片数据访问接口
 */
@WebServlet("/QueryCarServlet")
public class QueryCarServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		设置编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
//		输出流
		PrintWriter out = response.getWriter();

//		获得页码
		String pageNumStr = request.getParameter("pageNum");
		
		int pageNum = 1;
		
		try {
			pageNum = Integer.parseInt(pageNumStr);
		} catch (Exception e) {
			
		}
		
//		每页记录数
		String pageSizeStr = request.getParameter("pageSize");
		
		int pageSize = 6;
		
		try {
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (Exception e) {
			
		}
		
		CarService service = new CarService();
		
		try {
			Pager pager = new Pager(service.getCarCount(),pageNum,pageSize);
			ArrayList<Car> list = service.queryAll(pager);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("pager", pager);
			map.put("list", list);
			
//			产生json格式数据
			Gson gson = new GsonBuilder()  
			  .setDateFormat("yyyy-MM-dd")  
			  .create();
			
			out.print(gson.toJson(map));
		}  catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

}