package org.jallinone.warehouse.documents.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.warehouse.documents.java.*;
import org.jallinone.system.server.JAIOUserSessionParameters;
import java.math.BigDecimal;
import org.jallinone.events.server.EventsManager;
import org.jallinone.events.server.GenericEvent;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to (phisically) delete existing in delivery rows.</p>
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
public class DeleteInDeliveryNoteRowsAction implements Action {

  private InsertInSerialNumbersBean serialNumBean = new InsertInSerialNumbersBean();


  public DeleteInDeliveryNoteRowsAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
    return "deleteInDeliveryNoteRows";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
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

      ArrayList list = (ArrayList)inputPar;
      InDeliveryNoteRowPK rowPK = null;

      pstmt = conn.prepareStatement(
          "delete from DOC09_IN_DELIVERY_NOTE_ITEMS where COMPANY_CODE_SYS01=? and DOC_TYPE=? and DOC_YEAR=? and DOC_NUMBER=? and DOC_TYPE_DOC06=? and DOC_YEAR_DOC06=? and DOC_NUMBER_DOC06=? and ROW_NUMBER=? and ITEM_CODE_ITM01=?"
      );

      pstmt2 = conn.prepareStatement(
       "delete from DOC11_IN_SERIAL_NUMBERS where "+
       "COMPANY_CODE_SYS01=? and "+
       "DOC_TYPE=? and "+
       "DOC_YEAR=? and "+
       "DOC_NUMBER=? and "+
       "DOC_TYPE_DOC06=? and "+
       "DOC_YEAR_DOC06=? and "+
       "DOC_NUMBER_DOC06=? and "+
       "ROW_NUMBER=? and "+
       "ITEM_CODE_ITM01=?"
      );

      Response res = null;
      for(int i=0;i<list.size();i++) {
        rowPK = (InDeliveryNoteRowPK)list.get(i);

        // delete previous serial numbers from DOC11...
        pstmt2.setString(1,rowPK.getCompanyCodeSys01DOC09());
        pstmt2.setString(2,rowPK.getDocTypeDOC09());
        pstmt2.setBigDecimal(3,rowPK.getDocYearDOC09());
        pstmt2.setBigDecimal(4,rowPK.getDocNumberDOC09());
        pstmt2.setString(5,rowPK.getDocTypeDoc06DOC09());
        pstmt2.setBigDecimal(6,rowPK.getDocYearDoc06DOC09());
        pstmt2.setBigDecimal(7,rowPK.getDocNumberDoc06DOC09());
        pstmt2.setBigDecimal(8,rowPK.getRowNumberDOC09());
        pstmt2.setString(9,rowPK.getItemCodeItm01DOC09());
        pstmt2.execute();

        // phisically delete the record in DOC09...
        pstmt.setString(1,rowPK.getCompanyCodeSys01DOC09());
        pstmt.setString(2,rowPK.getDocTypeDOC09());
        pstmt.setBigDecimal(3,rowPK.getDocYearDOC09());
        pstmt.setBigDecimal(4,rowPK.getDocNumberDOC09());
        pstmt.setString(5,rowPK.getDocTypeDoc06DOC09());
        pstmt.setBigDecimal(6,rowPK.getDocYearDoc06DOC09());
        pstmt.setBigDecimal(7,rowPK.getDocNumberDoc06DOC09());
        pstmt.setBigDecimal(8,rowPK.getRowNumberDOC09());
        pstmt.setString(9,rowPK.getItemCodeItm01DOC09());
        pstmt.execute();
      }

      Response answer =  new VOResponse(new Boolean(true));

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
      Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while deleting existing in delivery note rows",ex);
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
        pstmt2.close();
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
