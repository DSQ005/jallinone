package org.jallinone.sales.customers.server;

import org.openswing.swing.server.*;
import java.io.*;
import java.util.*;

import org.openswing.swing.message.receive.java.*;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.message.send.java.LookupValidationParams;

import java.sql.*;
import org.openswing.swing.logger.server.*;
import org.jallinone.sales.customers.java.*;
import org.jallinone.system.server.*;
import java.math.*;
import org.jallinone.subjects.java.*;
import org.jallinone.events.server.*;
import org.jallinone.events.server.*;



/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * * <p>Description: Bean used to manage customers.</p>
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
@javax.jws.WebService
public interface Customers {


	/**
	 * Unsupported method, used to force the generation of a complex type in wsdl file for the return type 
	 */
	public GridCustomerVO getGridCustomer();

	public VOResponse deleteCustomers(ArrayList list,String serverLanguageId,String username) throws Throwable;

	public VOResponse insertOrganization(OrganizationCustomerVO vo,String imagePath,String t1,String t2,String serverLanguageId,String username,ArrayList companiesList, ArrayList customizedFields) throws Throwable;

	public VOResponse insertPeople(PeopleCustomerVO vo,String t1,String t2,String serverLanguageId,String username,ArrayList companiesList, ArrayList customizedFields) throws Throwable;

	public VOResponse loadCustomer(CustomerPK pk,String imagePath,String serverLanguageId,String username,ArrayList companiesList, ArrayList customizedFields) throws Throwable;

	public VOListResponse loadCustomers(GridParams gridPars,String serverLanguageId,String username,ArrayList companiesList) throws Throwable;

	public VOResponse updateOrganization(OrganizationVO oldVO,OrganizationVO newVO,String imagePath,String t1,String t2,String serverLanguageId,String username, ArrayList customizedFields) throws Throwable;

	public VOResponse updatePeople(PeopleVO oldVO,PeopleVO newVO,String t1,String t2,String serverLanguageId,String username, ArrayList customizedFields) throws Throwable;

	public VOListResponse validateCustomerCode(LookupValidationParams lookupPars,String serverLanguageId,String username,ArrayList companiesList) throws Throwable;

}

