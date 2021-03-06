package org.jallinone.system.server;

import org.openswing.swing.logger.server.*;
import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.internationalization.java.Language;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to retrieve languages defined for the application.</p>
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
public class LoadLanguagesAction implements Action {
  public LoadLanguagesAction() {
  }


  /**
   * @return request name
   */
  public final String getRequestName() {
    return "getLanguages";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    PreparedStatement stmt = null;
    Connection conn = null;
    try {
      conn = ConnectionManager.getConnection(context);
      stmt = conn.prepareStatement("select CLIENT_LANGUAGE_CODE,DESCRIPTION from SYS09_LANGUAGES where ENABLED='Y'");
      ResultSet rset = stmt.executeQuery();
      ArrayList list = new ArrayList();
      Language lang = null;
      while(rset.next()) {
        lang = new Language(rset.getString(1),rset.getString(2));
        list.add(lang);
      }
      return new VOListResponse(list,false,list.size());
    } catch (Exception ex) {
      Logger.error("NONAME",this.getClass().getName(),"executeCommand","Error while loading languages",ex);
      return new ErrorResponse(ex.getMessage());
    } finally {
      try {
        stmt.close();
      }
      catch (Exception ex) {
      }
      try {
        ConnectionManager.releaseConnection(conn,context);
      }
      catch (Exception ex2) {
      }
    }

  }


}