package org.jallinone.sales.customers.server;

import org.openswing.swing.server.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import org.openswing.swing.message.receive.java.*;
import java.sql.*;

import org.openswing.swing.internationalization.server.ServerResourcesFactory;
import org.openswing.swing.logger.server.*;
import org.jallinone.sales.customers.java.*;
import org.jallinone.system.server.*;

import java.math.*;

import org.jallinone.commons.server.*;
import org.jallinone.system.java.CustomizedWindows;
import org.jallinone.system.progressives.server.*;
import org.jallinone.system.translations.server.*;
import org.jallinone.subjects.java.*;
import org.jallinone.subjects.server.*;
import org.jallinone.subjects.java.*;
import org.jallinone.subjects.server.*;
import org.jallinone.subjects.java.*;
import org.jallinone.commons.java.*;
import org.jallinone.events.server.*;
import org.jallinone.events.server.*;
import org.jallinone.system.progressives.server.*;


import org.jallinone.commons.server.JAIOBeanFactory;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to insert a new warehouse in SAL07 table.</p>
 * <p>Copyright: Copyright (C) 2006 Mauro Carniel</p>
 *
 * <p> This file is part of JAllInOne ERP/CRM application.
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the (LGPL) Lesser General Public
 * License as published by the Free Software Foundation;
 *
 *                GNU LESSER GENERAL PUBLIC LICENSE
 *                 Version 2.1, February 1999
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *       The author may be contacted at:
 *           maurocarniel@tin.it</p>
 *
 * @author Mauro Carniel
 * @version 1.0
 */
public class InsertCustomerAction implements Action {

  public InsertCustomerAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
	  return "insertCustomer";
  }


  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
	  Subject vo = (Subject)inputPar;
	  try {
		  // retrieve internationalization settings (Resources object)...
		  ServerResourcesFactory factory = (ServerResourcesFactory)context.getAttribute(Controller.RESOURCES_FACTORY);
		  String serverLanguageId = ((JAIOUserSessionParameters)userSessionPars).getServerLanguageId();
		  String t1 = factory.getResources(serverLanguageId).getResource("there is already another people with the same first and last name.");
		  String t2 = factory.getResources(serverLanguageId).getResource("there is already another organization with the same corporate name.");
		  ArrayList companiesList = ((JAIOUserSessionParameters)userSessionPars).getCompanyBa().getCompaniesList("SAL07");

		  CustomizedWindows cust = ((JAIOUserSessionParameters)userSessionPars).getCustomizedWindows();
		  ArrayList customizedFields = cust.getCustomizedFields(new BigDecimal(282));
			String imagePath = (String)((JAIOUserSessionParameters)userSessionPars).getAppParams().get(ApplicationConsts.IMAGE_PATH);

		  Customers bean = (Customers)JAIOBeanFactory.getInstance().getBean(Customers.class);
		  Response answer = null;
	      if (vo.getSubjectTypeREG04().equals(ApplicationConsts.SUBJECT_ORGANIZATION_CUSTOMER))
	    	answer = bean.insertOrganization((OrganizationCustomerVO)vo,imagePath,t1,t2,((JAIOUserSessionParameters)userSessionPars).getServerLanguageId(),userSessionPars.getUsername(),companiesList,customizedFields);
	      else
		    answer = bean.insertPeople((PeopleCustomerVO)vo,t1,t2,((JAIOUserSessionParameters)userSessionPars).getServerLanguageId(),userSessionPars.getUsername(),companiesList,customizedFields);

		  return answer;
	  }
	  catch (Throwable ex) {
		  Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while processing request",ex);
		  return new ErrorResponse(ex.getMessage());
	  }
  }
}

