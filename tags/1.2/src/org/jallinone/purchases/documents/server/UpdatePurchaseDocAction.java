package org.jallinone.purchases.documents.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.purchases.documents.java.DetailPurchaseDocVO;
import org.jallinone.system.server.JAIOUserSessionParameters;
import java.math.BigDecimal;
import org.jallinone.system.progressives.server.ProgressiveUtils;
import org.jallinone.system.translations.server.TranslationUtils;
import org.jallinone.commons.server.CustomizeQueryUtil;
import org.jallinone.system.java.ApplicationParametersVO;
import org.jallinone.commons.java.ApplicationConsts;
import org.jallinone.registers.payments.java.PaymentVO;
import org.jallinone.registers.payments.server.ValidatePaymentCodeAction;
import org.openswing.swing.message.send.java.LookupValidationParams;
import org.jallinone.registers.currency.server.ValidateCurrencyCodeAction;
import org.jallinone.registers.currency.java.CurrencyVO;
import org.jallinone.events.server.EventsManager;
import org.jallinone.events.server.GenericEvent;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to update an existing purchase order.</p>
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
public class UpdatePurchaseDocAction implements Action {

  private ValidatePaymentCodeAction payBean = new ValidatePaymentCodeAction();
  private ValidateCurrencyCodeAction currBean = new ValidateCurrencyCodeAction();
  private PurchaseDocTotalsAction totalBean = new PurchaseDocTotalsAction();


  public UpdatePurchaseDocAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
    return "updatePurchaseDoc";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    String serverLanguageId = ((JAIOUserSessionParameters)userSessionPars).getServerLanguageId();
    Connection conn = null;
    Statement stmt = null;
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

      DetailPurchaseDocVO oldVO = (DetailPurchaseDocVO)((ValueObject[])inputPar)[0];
      DetailPurchaseDocVO newVO = (DetailPurchaseDocVO)((ValueObject[])inputPar)[1];

      // retrieve payment info...
      LookupValidationParams pars = new LookupValidationParams(newVO.getPaymentCodeReg10DOC06(),new HashMap());
      Response payResponse = payBean.executeCommand(pars,userSessionPars,request, response,userSession,context);
      if (payResponse.isError())
        return payResponse;
      PaymentVO payVO = (PaymentVO)((VOListResponse)payResponse).getRows().get(0);
      newVO.setFirstInstalmentDaysDOC06(payVO.getFirstInstalmentDaysREG10());
      newVO.setInstalmentNumberDOC06(payVO.getInstalmentNumberREG10());
      newVO.setPaymentTypeDescriptionDOC06(payVO.getPaymentTypeDescriptionSYS10());
      newVO.setStartDayDOC06(payVO.getStartDayREG10());
      newVO.setStepDOC06(payVO.getStepREG10());

      // retrieve currency info...
      pars = new LookupValidationParams(newVO.getCurrencyCodeReg03DOC06(),new HashMap());
      Response currResponse = currBean.executeCommand(pars,userSessionPars,request, response,userSession,context);
      if (currResponse.isError())
        return currResponse;
      CurrencyVO currVO = (CurrencyVO)((VOListResponse)currResponse).getRows().get(0);
      newVO.setCurrencySymbolREG03(currVO.getCurrencySymbolREG03());
      newVO.setDecimalSymbolREG03(currVO.getDecimalSymbolREG03());
      newVO.setThousandSymbolREG03(currVO.getThousandSymbolREG03());
      newVO.setDecimalsREG03(currVO.getDecimalsREG03());

      // recalculate totals...
      Response totalResponse = totalBean.executeCommand(newVO,userSessionPars,request, response,userSession,context);
      if (totalResponse.isError())
        return totalResponse;

      Map attribute2dbField = new HashMap();
      attribute2dbField.put("companyCodeSys01DOC06","COMPANY_CODE_SYS01");
      attribute2dbField.put("docTypeDOC06","DOC_TYPE");
      attribute2dbField.put("docStateDOC06","DOC_STATE");
      attribute2dbField.put("pricelistCodePur03DOC06","PRICELIST_CODE_PUR03");
      attribute2dbField.put("pricelistDescriptionDOC06","PRICELIST_DESCRIPTION");
      attribute2dbField.put("currencyCodeReg03DOC06","CURRENCY_CODE_REG03");
      attribute2dbField.put("docYearDOC06","DOC_YEAR");
      attribute2dbField.put("docNumberDOC06","DOC_NUMBER");
      attribute2dbField.put("taxableIncomeDOC06","TAXABLE_INCOME");
      attribute2dbField.put("totalVatDOC06","TOTAL_VAT");
      attribute2dbField.put("totalDOC06","TOTAL");
      attribute2dbField.put("docDateDOC06","DOC_DATE");

      attribute2dbField.put("progressiveReg04DOC06","PROGRESSIVE_REG04");
      attribute2dbField.put("companyCodeSys01Doc06DOC06","COMPANY_CODE_SYS01_DOC06");
      attribute2dbField.put("docTypeDoc06DOC06","DOC_TYPE_DOC06");
      attribute2dbField.put("docYearDoc06DOC06","DOC_YEAR_DOC06");
      attribute2dbField.put("docNumberDoc06DOC06","DOC_NUMBER_DOC06");
      attribute2dbField.put("discountValueDOC06","DISCOUNT_VALUE");
      attribute2dbField.put("discountPercDOC06","DISCOUNT_PERC");
      attribute2dbField.put("chargeValueDOC06","CHARGE_VALUE");
      attribute2dbField.put("chargePercDOC06","CHARGE_PERC");
      attribute2dbField.put("paymentCodeReg10DOC06","PAYMENT_CODE_REG10");
      attribute2dbField.put("paymentDescriptionDOC06","PAYMENT_DESCRIPTION");
      attribute2dbField.put("instalmentNumberDOC06","INSTALMENT_NUMBER");
      attribute2dbField.put("stepDOC06","STEP");
      attribute2dbField.put("startDayDOC06","START_DAY");
      attribute2dbField.put("firstInstalmentDaysDOC06","FIRST_INSTALMENT_DAYS");
      attribute2dbField.put("paymentTypeDescriptionDOC06","PAYMENT_TYPE_DESCRIPTION");
      attribute2dbField.put("progressiveWkf01DOC06","PROGRESSIVE_WKF01");
      attribute2dbField.put("progressiveWkf08DOC06","PROGRESSIVE_WKF08");
      attribute2dbField.put("descriptionWkf01DOC06","DESCRIPTION_WKF01");
      attribute2dbField.put("noteDOC06","NOTE");
      attribute2dbField.put("enabledDOC06","ENABLED");
      attribute2dbField.put("warehouseCodeWar01DOC06","WAREHOUSE_CODE_WAR01");
      attribute2dbField.put("descriptionWar01DOC06","DESCRIPTION_WAR01");
      attribute2dbField.put("addressWar01DOC06","ADDRESS_WAR01");
      attribute2dbField.put("cityWar01DOC06","CITY_WAR01");
      attribute2dbField.put("provinceWar01DOC06","PROVINCE_WAR01");
      attribute2dbField.put("zipWar01DOC06","ZIP_WAR01");
      attribute2dbField.put("countryWar01DOC06","COUNTRY_WAR01");

      HashSet pkAttributes = new HashSet();
      pkAttributes.add("companyCodeSys01DOC06");
      pkAttributes.add("docTypeDOC06");
      pkAttributes.add("docYearDOC06");
      pkAttributes.add("docNumberDOC06");

      // update DOC06 table...
      Response res = CustomizeQueryUtil.updateTable(
          conn,
          userSessionPars,
          pkAttributes,
          oldVO,
          newVO,
          "DOC06_PURCHASE",
          attribute2dbField,
          "Y",
          "N",
          context,
          true,
          ApplicationConsts.ID_PURCHASE_ORDER // window identifier...
      );


      Response answer = res;

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
      Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while updating an existing purchase order",ex);
      try {
        conn.rollback();
      }
      catch (Exception ex3) {
      }
      return new ErrorResponse(ex.getMessage());
    }
    finally {
      try {
        stmt.close();
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
