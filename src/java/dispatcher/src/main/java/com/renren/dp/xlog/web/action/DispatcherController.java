package com.renren.dp.xlog.web.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.renren.dp.xlog.dispatcher.SystemManager;

public class DispatcherController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException{
		String operator=req.getParameter("operator");
		SystemManager sm=new SystemManager();
		boolean result=false;
		if(operator.equals("start")){
			result=sm.start();
		}else if(operator.equals("stop")){
			result=sm.stop();
		}
		RequestDispatcher dispatcher =null;
		if(result){
			dispatcher = req.getRequestDispatcher("/index.jsp");
		}else{
			String tip=null;
			if(operator.equals("start")){
				tip="启动dispatcher失败!";
			}else if(operator.equals("stop")){
				tip="停止dispatcher失败!";
			}
			dispatcher = req.getRequestDispatcher("/error.jsp?errTip="+tip);
		}

		dispatcher .forward(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException{
		doPost(req,res);
	}
}
