package org.jallinone.system.importdata.server;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.jallinone.commons.java.*;
import org.jallinone.events.server.*;
import org.jallinone.system.importdata.java.*;
import org.jallinone.system.server.*;
import org.openswing.swing.logger.server.*;
import org.openswing.swing.message.receive.java.*;
import org.openswing.swing.message.send.java.*;
import org.openswing.swing.server.*;
import java.math.BigDecimal;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Bean used to load ETL process fields defined in SYS24 table.</p>
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
public class LoadETLProcessFieldsBean {


  public LoadETLProcessFieldsBean() {}


   /**
   * Business logic to execute.
   */
  public final Response loadETLProcessFields(Connection conn,BigDecimal progressiveSYS23,UserSessionParameters userSessionPars) {
    try {
      String sql =
          "SELECT SYS24_ETL_PROCESS_FIELDS.FIELD_NAME,SYS24_ETL_PROCESS_FIELDS.LANGUAGE_CODE,SYS24_ETL_PROCESS_FIELDS.START_POS,"+
          "SYS24_ETL_PROCESS_FIELDS.END_POS,SYS24_ETL_PROCESS_FIELDS.DATE_FORMAT,SYS24_ETL_PROCESS_FIELDS.POS,"+
          "SYS24_ETL_PROCESS_FIELDS.PROGRESSIVE_SYS23,SYS24_ETL_PROCESS_FIELDS.PROGRESSIVE "+
          "FROM SYS24_ETL_PROCESS_FIELDS WHERE SYS24_ETL_PROCESS_FIELDS.PROGRESSIVE_SYS23=?";

      Map attribute2dbField = new HashMap();
      attribute2dbField.put("fieldNameSYS24","SYS24_ETL_PROCESS_FIELDS.FIELD_NAME");
      attribute2dbField.put("languageCodeSYS24","SYS24_ETL_PROCESS_FIELDS.LANGUAGE_CODE");
      attribute2dbField.put("startPosSYS24","SYS24_ETL_PROCESS_FIELDS.START_POS");
      attribute2dbField.put("endPosSYS24","SYS24_ETL_PROCESS_FIELDS.END_POS");
      attribute2dbField.put("dateFormatSYS24","SYS24_ETL_PROCESS_FIELDS.DATE_FORMAT");
      attribute2dbField.put("posSYS24","SYS24_ETL_PROCESS_FIELDS.POS");
      attribute2dbField.put("progressiveSys23SYS24","SYS24_ETL_PROCESS_FIELDS.PROGRESSIVE_SYS23");
      attribute2dbField.put("progressiveSYS24","SYS24_ETL_PROCESS_FIELDS.PROGRESSIVE");

      ArrayList values = new ArrayList();
      values.add(progressiveSYS23);


      // read from SYS24 table...
      return QueryUtil.getQuery(
          conn,
          userSessionPars,
          sql,
          values,
          attribute2dbField,
          ETLProcessFieldVO.class,
          "Y",
          "N",
          null,
          new GridParams(),
          true
      );

    }
    catch (Throwable ex) {
      Logger.error(userSessionPars!=null?userSessionPars.getUsername():null,this.getClass().getName(),"loadETLProcessFields","Error while fetching ETL process fields",ex);
      return new ErrorResponse(ex.getMessage());
    }

  }



}
