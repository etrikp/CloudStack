/**
 *  Copyright (C) 2010 Cloud.com, Inc.  All rights reserved.
 * 
 * This software is licensed under the GNU General Public License v3 or later.
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.cloud.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cloud.configuration.Configuration;
import com.cloud.configuration.dao.ConfigurationDao;
import com.cloud.server.ManagementServer;
import com.cloud.user.AccountService;
import com.cloud.user.User;
import com.cloud.utils.SerialVersionUID;
import com.cloud.utils.component.ComponentLocator;

public class RegisterCompleteServlet extends HttpServlet implements ServletContextListener {
	public static final Logger s_logger = Logger.getLogger(RegisterCompleteServlet.class.getName());
	
    static final long serialVersionUID = SerialVersionUID.CloudStartupServlet;
   
    protected static AccountService _accountSvc = null;
    protected static ConfigurationDao _configDao = null;
    
	@Override
    public void init() throws ServletException {
		ComponentLocator locator = ComponentLocator.getLocator(ManagementServer.Name);
		_accountSvc = locator.getManager(AccountService.class);
		_configDao = locator.getDao(ConfigurationDao.class);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    try {
	        init();
	    } catch (ServletException e) {
	        s_logger.error("Exception starting management server ", e);
	        throw new RuntimeException(e);
	    }
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String token = req.getParameter("token");
		if (token == null || token.trim().length() == 0) {
			// Return an error code
		} 
		
		User user = _accountSvc.getActiveUser(new Long(token));
		if (user == null) {
			// Return an error code
		}
		
		Configuration config = _configDao.findByName("endpointe.url");
		
		StringBuffer sb = new StringBuffer();
        sb.append("{ \"registration_info\" : { \"endpoint_url\" : \""+config.getValue()+"\", ");
        sb.append("\"account\" : \""+user.getUsername()+"\", ");
        sb.append("\"account_api_key\" : \""+user.getApiKey()+"\", ");
        sb.append("\"account_secret_key\" : \""+user.getSecretKey()+"\" ");
        sb.append("} }");
        
        try {
	        resp.setContentType("text/javascript; charset=UTF-8");
	        resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().print(sb.toString());
        } catch (Exception ex) {
        	s_logger.error("unknown exception writing register complete response", ex);
        }
	}
}
