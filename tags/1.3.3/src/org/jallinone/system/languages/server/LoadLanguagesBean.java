package org.jallinone.system.languages.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.system.languages.java.*;
import org.jallinone.system.server.JAIOUserSessionParameters;
import org.jallinone.events.server.EventsManager;
import org.jallinone.events.server.GenericEvent;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Bean used to fetch languages from SYS09 table.</p>
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
public class LoadLanguagesBean  {


  public LoadLanguagesBean() {
  }


  /**
   * Business logic to execute.
   */
  public final Response loadLanguages(Connection conn,UserSessionParameters userSessionPars) {
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "select LANGUAGE_CODE,DESCRIPTION,CLIENT_LANGUAGE_CODE from SYS09_LANGUAGES where ENABLED='Y'"
      );
      LanguageVO vo = null;
      ArrayList list = new ArrayList();
      while(rset.next()) {
        vo = new LanguageVO();
        vo.setLanguageCodeSYS09(rset.getString(1));
        vo.setDescriptionSYS09(rset.getString(2));
        vo.setClientLanguageCodeSYS09(rset.getString(3));
        list.add(vo);
      }

      rset.close();
      return new VOListResponse(list,false,list.size());
    }
    catch (Throwable ex) {
      Logger.error(userSessionPars!=null?userSessionPars.getUsername():null,this.getClass().getName(),"loadLanguages","Error while fetching languages list",ex);
      return new ErrorResponse(ex.getMessage());
    }
    finally {
      try {
        stmt.close();
      }
      catch (Exception ex2) {
      }
    }

  }



}
