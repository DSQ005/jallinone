package org.jallinone.production.orders.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.system.server.JAIOUserSessionParameters;
import java.math.BigDecimal;
import org.jallinone.system.java.ApplicationParametersVO;
import org.jallinone.commons.java.ApplicationConsts;
import org.jallinone.production.orders.java.*;
import org.jallinone.events.server.EventsManager;
import org.jallinone.events.server.GenericEvent;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to update existing production order products.</p>
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
public class UpdateProdOrderProductsAction implements Action {


  public UpdateProdOrderProductsAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
    return "updateProdOrderProducts";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    String serverLanguageId = ((JAIOUserSessionParameters)userSessionPars).getServerLanguageId();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ConnectionManager.getConnection(context);

      // fires the GenericEvent.CONNECTION_CREATED event...
      EventsManager.getInstance().processEvent(new GenericEvent(
        this,
        getRequestName(),
        GenericEvent.CONNECTION_CREATED,
        (JAIOUserSessionParameters)userSessionPars,
        request,
        response,
        userSession,
        context,
        conn,
        inputPar,
        null
      ));


      ArrayList oldVOs = ((ArrayList[])inputPar)[0];
      ArrayList newVOs = ((ArrayList[])inputPar)[1];

      ProdOrderProductVO oldVO = null;
      ProdOrderProductVO newVO = null;

      Map attribute2dbField = new HashMap();
      attribute2dbField.put("companyCodeSys01DOC23","COMPANY_CODE_SYS01");
      attribute2dbField.put("docYearDOC23","DOC_YEAR");
      attribute2dbField.put("docNumberDOC23","DOC_NUMBER");
      attribute2dbField.put("itemCodeItm01DOC23","ITEM_CODE_ITM01");
      attribute2dbField.put("qtyDOC23","QTY");
      attribute2dbField.put("progressiveHie01DOC23","PROGRESSIVE_HIE01");
      attribute2dbField.put("progressiveHie02DOC23","PROGRESSIVE_HIE02");

      HashSet pkAttributes = new HashSet();
      pkAttributes.add("companyCodeSys01DOC23");
      pkAttributes.add("docYearDOC23");
      pkAttributes.add("docNumberDOC23");
      pkAttributes.add("itemCodeItm01DOC23");


      Response res = null;
      for(int i=0;i<oldVOs.size();i++) {
        oldVO = (ProdOrderProductVO)oldVOs.get(i);
        newVO = (ProdOrderProductVO)newVOs.get(i);

        // update DOC23 table...
        res = QueryUtil.updateTable(
            conn,
            userSessionPars,
            pkAttributes,
            oldVO,
            newVO,
            "DOC23_PRODUCTION_PRODUCTS",
            attribute2dbField,
            "Y",
            "N",
            context,
            true
        );

        if (res.isError()) {
          conn.rollback();
          return res;
        }
      }

      Response answer = new VOListResponse(newVOs,false,newVOs.size());

      // fires the GenericEvent.BEFORE_COMMIT event...
      EventsManager.getInstance().processEvent(new GenericEvent(
        this,
        getRequestName(),
        GenericEvent.BEFORE_COMMIT,
        (JAIOUserSessionParameters)userSessionPars,
        request,
        response,
        userSession,
        context,
        conn,
        inputPar,
        answer
      ));

      conn.commit();

      // fires the GenericEvent.AFTER_COMMIT event...
      EventsManager.getInstance().processEvent(new GenericEvent(
        this,
        getRequestName(),
        GenericEvent.AFTER_COMMIT,
        (JAIOUserSessionParameters)userSessionPars,
        request,
        response,
        userSession,
        context,
        conn,
        inputPar,
        answer
      ));

      return answer;
    }
    catch (Throwable ex) {
      Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while updating existing production order products",ex);
      try {
        conn.rollback();
      }
      catch (Exception ex3) {
      }
      return new ErrorResponse(ex.getMessage());
    }
    finally {
      try {
        pstmt.close();
      }
      catch (Exception ex2) {
      }

      try {
        ConnectionManager.releaseConnection(conn, context);
      }
      catch (Exception ex1) {
      }
    }
  }



}
