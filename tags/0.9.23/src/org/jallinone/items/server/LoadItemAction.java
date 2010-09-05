package org.jallinone.items.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.items.java.*;
import org.jallinone.system.server.JAIOUserSessionParameters;
import org.jallinone.commons.server.CustomizeQueryUtil;
import java.math.BigDecimal;
import org.openswing.swing.message.send.java.GridParams;
import org.jallinone.system.java.ApplicationParametersVO;
import org.jallinone.commons.java.ApplicationConsts;
import org.jallinone.events.server.EventsManager;
import org.jallinone.events.server.GenericEvent;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to fetch a specific item from ITM01 table.</p>
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
public class LoadItemAction implements Action {


  public LoadItemAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
    return "loadItem";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    String serverLanguageId = ((JAIOUserSessionParameters)userSessionPars).getServerLanguageId();
    Connection conn = null;
    Statement stmt = null;
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


      ItemPK pk = (ItemPK)inputPar;

      Map attribute2dbField = new HashMap();
      attribute2dbField.put("companyCodeSys01ITM01","ITM01_ITEMS.COMPANY_CODE_SYS01");
      attribute2dbField.put("itemCodeITM01","ITM01_ITEMS.ITEM_CODE");
      attribute2dbField.put("descriptionSYS10","A.DESCRIPTION");
      attribute2dbField.put("progressiveHie02ITM01","ITM01_ITEMS.PROGRESSIVE_HIE02");
      attribute2dbField.put("progressiveHie01ITM01","ITM01_ITEMS.PROGRESSIVE_HIE01");
      attribute2dbField.put("addProgressiveSys10ITM01","ITM01_ITEMS.ADD_PROGRESSIVE_SYS10");
      attribute2dbField.put("progressiveSys10ITM01","ITM01_ITEMS.PROGRESSIVE_SYS10");
//      attribute2dbField.put("addDescriptionSYS10","B.DESCRIPTION");
      attribute2dbField.put("minSellingQtyITM01","ITM01_ITEMS.MIN_SELLING_QTY");
      attribute2dbField.put("minSellingQtyUmCodeReg02ITM01","ITM01_ITEMS.MIN_SELLING_QTY_UM_CODE_REG02");
      attribute2dbField.put("minSellingQtyDecimalsREG02","REG02_A.DECIMALS");
      attribute2dbField.put("vatCodeReg01ITM01","ITM01_ITEMS.VAT_CODE_REG01");
      attribute2dbField.put("vatDescriptionSYS10","B.DESCRIPTION");
      attribute2dbField.put("grossWeightITM01","ITM01_ITEMS.GROSS_WEIGHT");
      attribute2dbField.put("grossWeightUmCodeReg02ITM01","ITM01_ITEMS.GROSS_WEIGHT_UM_CODE_REG02");
      attribute2dbField.put("netWeightITM01","ITM01_ITEMS.NET_WEIGHT");
//      attribute2dbField.put("netWeightDecimalsREG02","REG02_B.DECIMALS");
//      attribute2dbField.put("grossWeightDecimalsREG02","REG02_C.DECIMALS");
      attribute2dbField.put("netWeightUmCodeReg02ITM01","ITM01_ITEMS.NET_WEIGHT_UM_CODE_REG02");
      attribute2dbField.put("widthITM01","ITM01_ITEMS.WIDTH");
//      attribute2dbField.put("widthDecimalsREG02","REG02_D.DECIMALS");
      attribute2dbField.put("widthUmCodeReg02ITM01","ITM01_ITEMS.WIDTH_UM_CODE_REG02");
      attribute2dbField.put("heightITM01","ITM01_ITEMS.HEIGHT");
//      attribute2dbField.put("heightDecimalsREG02","REG02_E.DECIMALS");
      attribute2dbField.put("heightUmCodeReg02ITM01","ITM01_ITEMS.HEIGHT_UM_CODE_REG02");
      attribute2dbField.put("noteITM01","ITM01_ITEMS.NOTE");
      attribute2dbField.put("levelDescriptionSYS10","C.DESCRIPTION");
      attribute2dbField.put("largeImageITM01","ITM01_ITEMS.LARGE_IMAGE");
      attribute2dbField.put("smallImageITM01","ITM01_ITEMS.SMALL_IMAGE");
//      private byte[] smallImage;
//      private byte[] largeImage;
      attribute2dbField.put("vatValueREG01","REG01_VATS.VALUE");
      attribute2dbField.put("vatDeductibleREG01","REG01_VATS.DEDUCTIBLE");
//      attribute2dbField.put("colorDescriptionSYS10","D.DESCRIPTION");
//      attribute2dbField.put("sizeDescriptionSYS10","E.DESCRIPTION");
      attribute2dbField.put("enabledITM01","ITM01_ITEMS.ENABLED");
      attribute2dbField.put("serialNumberRequiredITM01","ITM01_ITEMS.SERIAL_NUMBER_REQUIRED");
      attribute2dbField.put("versionITM01","ITM01_ITEMS.VERSION");
      attribute2dbField.put("revisionITM01","ITM01_ITEMS.REVISION");
      attribute2dbField.put("manufactureCodePro01ITM01","ITM01_ITEMS.MANUFACTURE_CODE_PRO01");
      attribute2dbField.put("startDateITM01","ITM01_ITEMS.START_DATE");
      attribute2dbField.put("manufactureDescriptionSYS10","PRO01.DESCRIPTION");

      attribute2dbField.put("useVariant1ITM01","ITM01_ITEMS.USE_VARIANT_1");
      attribute2dbField.put("useVariant2ITM01","ITM01_ITEMS.USE_VARIANT_2");
      attribute2dbField.put("useVariant3ITM01","ITM01_ITEMS.USE_VARIANT_3");
      attribute2dbField.put("useVariant4ITM01","ITM01_ITEMS.USE_VARIANT_4");
      attribute2dbField.put("useVariant5ITM01","ITM01_ITEMS.USE_VARIANT_5");

      attribute2dbField.put("barCodeITM01","ITM01_ITEMS.BAR_CODE");

      HashSet pkAttributes = new HashSet();
      pkAttributes.add("companyCodeSys01ITM01");
      pkAttributes.add("itemCodeITM01");

      String baseSQL =
          "select "+
          "ITM01_ITEMS.COMPANY_CODE_SYS01,ITM01_ITEMS.ITEM_CODE,ITM01_ITEMS.BAR_CODE,A.DESCRIPTION,ITM01_ITEMS.PROGRESSIVE_HIE02,ITM01_ITEMS.PROGRESSIVE_HIE01,"+
          "ITM01_ITEMS.ADD_PROGRESSIVE_SYS10,ITM01_ITEMS.PROGRESSIVE_SYS10,ITM01_ITEMS.MIN_SELLING_QTY,ITM01_ITEMS.MIN_SELLING_QTY_UM_CODE_REG02,"+
          "REG02_A.DECIMALS,ITM01_ITEMS.VAT_CODE_REG01,B.DESCRIPTION,ITM01_ITEMS.GROSS_WEIGHT,ITM01_ITEMS.GROSS_WEIGHT_UM_CODE_REG02,"+
          "ITM01_ITEMS.NET_WEIGHT,ITM01_ITEMS.NET_WEIGHT_UM_CODE_REG02,ITM01_ITEMS.WIDTH,"+
          "ITM01_ITEMS.WIDTH_UM_CODE_REG02,ITM01_ITEMS.HEIGHT,ITM01_ITEMS.HEIGHT_UM_CODE_REG02,ITM01_ITEMS.NOTE,"+
          "ITM01_ITEMS.LARGE_IMAGE,ITM01_ITEMS.SMALL_IMAGE,REG01_VATS.VALUE,REG01_VATS.DEDUCTIBLE,"+
          "C.DESCRIPTION,ITM01_ITEMS.SERIAL_NUMBER_REQUIRED,PRO01.DESCRIPTION,ITM01_ITEMS.VERSION,ITM01_ITEMS.REVISION, "+
          "ITM01_ITEMS.START_DATE,ITM01_ITEMS.MANUFACTURE_CODE_PRO01, "+
          "ITM01_ITEMS.USE_VARIANT_1,ITM01_ITEMS.USE_VARIANT_2,ITM01_ITEMS.USE_VARIANT_3,ITM01_ITEMS.USE_VARIANT_4,ITM01_ITEMS.USE_VARIANT_5 "+
          " from "+
          "SYS10_TRANSLATIONS A,SYS10_TRANSLATIONS B,REG01_VATS,REG02_MEASURE_UNITS REG02_A,SYS10_TRANSLATIONS C,HIE01_LEVELS,ITM01_ITEMS "+
          "LEFT OUTER JOIN "+
          "(select SYS10_TRANSLATIONS.DESCRIPTION,PRO01_MANUFACTURES.MANUFACTURE_CODE,PRO01_MANUFACTURES.COMPANY_CODE_SYS01 "+
          "from PRO01_MANUFACTURES,SYS10_TRANSLATIONS where "+
          "PRO01_MANUFACTURES.PROGRESSIVE_SYS10=SYS10_TRANSLATIONS.PROGRESSIVE and "+
          "SYS10_TRANSLATIONS.LANGUAGE_CODE=?) PRO01 ON "+
          "PRO01.COMPANY_CODE_SYS01=ITM01_ITEMS.COMPANY_CODE_SYS01 and "+
          "PRO01.MANUFACTURE_CODE=ITM01_ITEMS.MANUFACTURE_CODE_PRO01 "+
          "where "+
          "ITM01_ITEMS.MIN_SELLING_QTY_UM_CODE_REG02=REG02_A.UM_CODE and "+
          "ITM01_ITEMS.PROGRESSIVE_SYS10=A.PROGRESSIVE and A.LANGUAGE_CODE=? and "+
          "ITM01_ITEMS.VAT_CODE_REG01=REG01_VATS.VAT_CODE and "+
          "REG01_VATS.PROGRESSIVE_SYS10=B.PROGRESSIVE and B.LANGUAGE_CODE=? and "+
          "HIE01_LEVELS.PROGRESSIVE=ITM01_ITEMS.PROGRESSIVE_HIE01 and "+
          "HIE01_LEVELS.PROGRESSIVE=C.PROGRESSIVE and C.LANGUAGE_CODE=? and "+
          "ITM01_ITEMS.COMPANY_CODE_SYS01=? and "+
          "ITM01_ITEMS.ITEM_CODE=?";

      ArrayList values = new ArrayList();
      values.add(serverLanguageId);
      values.add(serverLanguageId);
      values.add(serverLanguageId);
      values.add(serverLanguageId);
      values.add(pk.getCompanyCodeSys01ITM01());
      values.add(pk.getItemCodeITM01());

      // read from ITM01 table...
      Response res = CustomizeQueryUtil.getQuery(
          conn,
          userSessionPars,
          baseSQL,
          values,
          attribute2dbField,
          DetailItemVO.class,
          "Y",
          "N",
          context,
          true,
          new BigDecimal(262) // window identifier...
      );

      if (!res.isError()) {
        stmt = conn.createStatement();
        ResultSet rset = null;

        DetailItemVO vo = (DetailItemVO)((VOResponse)res).getVo();
        if (vo.getAddProgressiveSys10ITM01()!=null) {
          // retrieve additional description...
          rset = stmt.executeQuery(
              "select DESCRIPTION from SYS10_TRANSLATIONS where "+
              "PROGRESSIVE="+vo.getAddProgressiveSys10ITM01()+" and LANGUAGE_CODE='"+serverLanguageId+"'"
          );
          if (rset.next())
            vo.setAddDescriptionSYS10(rset.getString(1));
          rset.close();
        }

        if (vo.getGrossWeightUmCodeReg02ITM01()!=null) {
          // retrieve gross weight decimals...
          rset = stmt.executeQuery(
              "select DECIMALS from REG02_MEASURE_UNITS where "+
              "UM_CODE='"+vo.getGrossWeightUmCodeReg02ITM01()+"'"
          );
          if (rset.next())
            vo.setGrossWeightDecimalsREG02(rset.getBigDecimal(1));
          rset.close();
        }

        if (vo.getNetWeightUmCodeReg02ITM01()!=null) {
          // retrieve net weight decimals...
          rset = stmt.executeQuery(
              "select DECIMALS from REG02_MEASURE_UNITS where "+
              "UM_CODE='"+vo.getNetWeightUmCodeReg02ITM01()+"'"
          );
          if (rset.next())
            vo.setNetWeightDecimalsREG02(rset.getBigDecimal(1));
          rset.close();
        }

        if (vo.getWidthUmCodeReg02ITM01()!=null) {
          // retrieve width decimals...
          rset = stmt.executeQuery(
              "select DECIMALS from REG02_MEASURE_UNITS where "+
              "UM_CODE='"+vo.getWidthUmCodeReg02ITM01()+"'"
          );
          if (rset.next())
            vo.setWidthDecimalsREG02(rset.getBigDecimal(1));
          rset.close();
        }

        if (vo.getHeightUmCodeReg02ITM01()!=null) {
          // retrieve height decimals...
          rset = stmt.executeQuery(
              "select DECIMALS from REG02_MEASURE_UNITS where "+
              "UM_CODE='"+vo.getHeightUmCodeReg02ITM01()+"'"
          );
          if (rset.next())
            vo.setHeightDecimalsREG02(rset.getBigDecimal(1));
          rset.close();
        }

        if (vo.getSmallImageITM01()!=null) {
          // load image from file system...
          String appPath = (String)((JAIOUserSessionParameters)userSessionPars).getAppParams().get(ApplicationConsts.IMAGE_PATH);
          appPath = appPath.replace('\\','/');
          if (!appPath.endsWith("/"))
            appPath += "/";
          if (!new File(appPath).isAbsolute()) {
            // relative path (to "WEB-INF/classes/" folder)
            appPath = this.getClass().getResource("/").getPath().replaceAll("%20"," ")+appPath;
          }
          File f = new File(appPath+vo.getSmallImageITM01());
          byte[] bytes = new byte[(int)f.length()];
          FileInputStream in = new FileInputStream(f);
          in.read(bytes);
          in.close();
          vo.setSmallImage(bytes);
        }

        if (vo.getLargeImageITM01()!=null) {
          // load image from file system...
          String appPath = (String)((JAIOUserSessionParameters)userSessionPars).getAppParams().get(ApplicationConsts.IMAGE_PATH);
          appPath = appPath.replace('\\','/');
          if (!appPath.endsWith("/"))
            appPath += "/";
          if (!new File(appPath).isAbsolute()) {
            // relative path (to "WEB-INF/classes/" folder)
            appPath = this.getClass().getResource("/").getPath().replaceAll("%20"," ")+appPath;
          }
          File f = new File(appPath+vo.getLargeImageITM01());
          byte[] bytes = new byte[(int)f.length()];
          FileInputStream in = new FileInputStream(f);
          in.read(bytes);
          in.close();
          vo.setLargeImage(bytes);
        }


        // retrieve last purchase cost...
        String sql =
            "SELECT DOC06_PURCHASE.DOC_DATE,(DOC07_PURCHASE_ITEMS.VALUE-DOC07_PURCHASE_ITEMS.VAT_VALUE)/QTY,"+
            "REG03_CURRENCIES.DECIMALS,REG03_CURRENCIES.CURRENCY_SYMBOL,REG03_CURRENCIES.DECIMAL_SYMBOL,REG03_CURRENCIES.THOUSAND_SYMBOL,REG03_CURRENCIES.CURRENCY_CODE "+
            "FROM DOC07_PURCHASE_ITEMS,DOC06_PURCHASE,REG03_CURRENCIES "+
            "WHERE DOC07_PURCHASE_ITEMS.COMPANY_CODE_SYS01=? AND "+
            "DOC07_PURCHASE_ITEMS.ITEM_CODE_ITM01=? AND "+
            "DOC07_PURCHASE_ITEMS.COMPANY_CODE_SYS01=DOC06_PURCHASE.COMPANY_CODE_SYS01 AND "+
            "DOC07_PURCHASE_ITEMS.DOC_TYPE=DOC06_PURCHASE.DOC_TYPE AND "+
            "DOC07_PURCHASE_ITEMS.DOC_YEAR=DOC06_PURCHASE.DOC_YEAR AND "+
            "DOC07_PURCHASE_ITEMS.DOC_NUMBER=DOC06_PURCHASE.DOC_NUMBER AND "+
            "DOC06_PURCHASE.DOC_TYPE='P' AND "+
            "DOC06_PURCHASE.ENABLED='Y' AND "+
            "REG03_CURRENCIES.CURRENCY_CODE=DOC06_PURCHASE.CURRENCY_CODE_REG03 "+
            "ORDER BY DOC06_PURCHASE.DOC_DATE DESC ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,pk.getCompanyCodeSys01ITM01());
        pstmt.setString(2,pk.getItemCodeITM01());
        rset = pstmt.executeQuery();
        BigDecimal decimals = new BigDecimal("2");
        String currencyCode = null;
        if(rset.next()) {
          vo.setLastPurchaseDate(rset.getDate(1));
          vo.setLastPurchaseCost(rset.getBigDecimal(2));
          decimals = rset.getBigDecimal(3);
          vo.setLastPurchaseCostDecimals(decimals);
          if (decimals!=null && vo.getLastPurchaseCost()!=null) {
            vo.setLastPurchaseCost(vo.getLastPurchaseCost().setScale(decimals.intValue(),BigDecimal.ROUND_HALF_UP));
            vo.setLastPurchaseCostCurrencySymbol(rset.getString(4));
            vo.setLastPurchaseCostDecimalSymbol(rset.getString(5));
            vo.setLastPurchaseCostThousandSymbol(rset.getString(6));
            currencyCode = rset.getString(7);
          }
        }
        rset.close();
        pstmt.close();

        if (currencyCode!=null) {
          // retrieve avg purchase cost...
          sql =
              "SELECT SUM((DOC07_PURCHASE_ITEMS.VALUE-DOC07_PURCHASE_ITEMS.VAT_VALUE)/QTY)/COUNT(*) "+
              "FROM DOC07_PURCHASE_ITEMS,DOC06_PURCHASE "+
              "WHERE DOC07_PURCHASE_ITEMS.COMPANY_CODE_SYS01=? AND "+
              "DOC07_PURCHASE_ITEMS.ITEM_CODE_ITM01=? AND "+
              "DOC07_PURCHASE_ITEMS.COMPANY_CODE_SYS01=DOC06_PURCHASE.COMPANY_CODE_SYS01 AND "+
              "DOC07_PURCHASE_ITEMS.DOC_TYPE=DOC06_PURCHASE.DOC_TYPE AND "+
              "DOC07_PURCHASE_ITEMS.DOC_YEAR=DOC06_PURCHASE.DOC_YEAR AND "+
              "DOC07_PURCHASE_ITEMS.DOC_NUMBER=DOC06_PURCHASE.DOC_NUMBER AND "+
              "DOC06_PURCHASE.DOC_TYPE='P' AND "+
              "DOC06_PURCHASE.ENABLED='Y' AND "+
              "DOC06_PURCHASE.CURRENCY_CODE_REG03=? ";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1,pk.getCompanyCodeSys01ITM01());
          pstmt.setString(2,pk.getItemCodeITM01());
          pstmt.setString(3,currencyCode);
          rset = pstmt.executeQuery();
          if(rset.next()) {
            vo.setAvgPurchaseCost(rset.getBigDecimal(1));
            if (decimals!=null && vo.getAvgPurchaseCost()!=null)
              vo.setAvgPurchaseCost(vo.getAvgPurchaseCost().setScale(decimals.intValue(),BigDecimal.ROUND_HALF_UP));
          }
          rset.close();
        }


      } // end if on isError




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

      return answer;
    }
    catch (Throwable ex) {
      Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while fetching an existing item",ex);
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