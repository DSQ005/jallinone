package org.jallinone.purchases.documents.server;

import org.openswing.swing.server.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.*;
import org.jallinone.items.java.*;
import org.jallinone.system.server.*;
import org.jallinone.commons.server.*;
import java.math.*;
import org.openswing.swing.message.send.java.*;
import org.jallinone.commons.java.*;
import org.jallinone.hierarchies.java.*;
import org.jallinone.purchases.documents.java.*;
import org.openswing.swing.message.send.java.*;
import org.jallinone.events.server.*;
import org.jallinone.events.server.*;


import javax.sql.DataSource;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * * <p>Description: Bean used to fetch supplier items + price from PUR02/PUR04 table.</p>
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
public class ValidateSupplierPriceItemCodeBean  implements ValidateSupplierPriceItemCode {


  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /** external connection */
  private Connection conn = null;

  /**
   * Set external connection.
   */
  public void setConn(Connection conn) {
    this.conn = conn;
  }

  /**
   * Create local connection
   */
  public Connection getConn() throws Exception {

    Connection c = dataSource.getConnection(); c.setAutoCommit(false); return c;
  }




  public ValidateSupplierPriceItemCodeBean() {
  }


  /**
   * Unsupported method, used to force the generation of a complex type in wsdl file for the return type
   */
  public SupplierPriceItemVO getSupplierPriceItem() {
	  throw new UnsupportedOperationException();
  }



  /**
   * Business logic to execute.
   */
  public VOListResponse validateSupplierPriceItemCode(LookupValidationParams pars,String serverLanguageId,String username) throws Throwable {
    PreparedStatement pstmt = null;
    Connection conn = null;
    try {
      if (this.conn==null) conn = getConn(); else conn = this.conn;

      String companyCodeSYS10 = (String)pars.getLookupValidationParameters().get(ApplicationConsts.COMPANY_CODE_SYS01);
      BigDecimal progressiveREG04 = (BigDecimal)pars.getLookupValidationParameters().get(ApplicationConsts.PROGRESSIVE_REG04);
      String pricelistCodePUR03 = (String)pars.getLookupValidationParameters().get(ApplicationConsts.PRICELIST);

      String sql =
          "select PUR02_SUPPLIER_ITEMS.COMPANY_CODE_SYS01,PUR02_SUPPLIER_ITEMS.ITEM_CODE_ITM01,PUR02_SUPPLIER_ITEMS.SUPPLIER_ITEM_CODE,PUR02_SUPPLIER_ITEMS.PROGRESSIVE_REG04,"+
          "PUR02_SUPPLIER_ITEMS.PROGRESSIVE_HIE02,PUR02_SUPPLIER_ITEMS.PROGRESSIVE_HIE01,PUR02_SUPPLIER_ITEMS.MIN_PURCHASE_QTY,PUR02_SUPPLIER_ITEMS.MULTIPLE_QTY,"+
          "PUR02_SUPPLIER_ITEMS.UM_CODE_REG02,PUR02_SUPPLIER_ITEMS.ENABLED,SYS10_COMPANY_TRANSLATIONS.DESCRIPTION,REG02_MEASURE_UNITS.DECIMALS,"+
          "ITM01_ITEMS.VAT_CODE_REG01,SYS10_VAT.DESCRIPTION,REG01_VATS.DEDUCTIBLE,REG01_VATS.VALUE,"+
          "PUR04_SUPPLIER_ITEM_PRICES.VALUE,PUR04_SUPPLIER_ITEM_PRICES.START_DATE,PUR04_SUPPLIER_ITEM_PRICES.END_DATE,"+
          "ITM01_ITEMS.USE_VARIANT_1,ITM01_ITEMS.USE_VARIANT_2,ITM01_ITEMS.USE_VARIANT_3,ITM01_ITEMS.USE_VARIANT_4,ITM01_ITEMS.USE_VARIANT_5, "+
					"ITM01_ITEMS.NO_WAREHOUSE_MOV "+
          " from PUR02_SUPPLIER_ITEMS,SYS10_COMPANY_TRANSLATIONS,ITM01_ITEMS,REG02_MEASURE_UNITS,SYS10_TRANSLATIONS SYS10_VAT,REG01_VATS,PUR04_SUPPLIER_ITEM_PRICES where "+
          "PUR02_SUPPLIER_ITEMS.UM_CODE_REG02=REG02_MEASURE_UNITS.UM_CODE and "+
          "PUR02_SUPPLIER_ITEMS.COMPANY_CODE_SYS01=ITM01_ITEMS.COMPANY_CODE_SYS01 and "+
          "PUR02_SUPPLIER_ITEMS.ITEM_CODE_ITM01=ITM01_ITEMS.ITEM_CODE and "+
					"ITM01_ITEMS.COMPANY_CODE_SYS01=SYS10_COMPANY_TRANSLATIONS.COMPANY_CODE_SYS01 and "+
          "ITM01_ITEMS.PROGRESSIVE_SYS10=SYS10_COMPANY_TRANSLATIONS.PROGRESSIVE and "+
          "SYS10_COMPANY_TRANSLATIONS.LANGUAGE_CODE=? and "+
          "PUR02_SUPPLIER_ITEMS.COMPANY_CODE_SYS01 = ? and "+
          "PUR02_SUPPLIER_ITEMS.PROGRESSIVE_REG04=? and "+
          "PUR02_SUPPLIER_ITEMS.ENABLED='Y' and "+
          "ITM01_ITEMS.VAT_CODE_REG01=REG01_VATS.VAT_CODE and "+
          "REG01_VATS.PROGRESSIVE_SYS10=SYS10_VAT.PROGRESSIVE and "+
          "SYS10_VAT.LANGUAGE_CODE=? and "+
          "PUR02_SUPPLIER_ITEMS.ITEM_CODE_ITM01=? and "+
          "PUR02_SUPPLIER_ITEMS.COMPANY_CODE_SYS01=PUR04_SUPPLIER_ITEM_PRICES.COMPANY_CODE_SYS01 and "+
          "PUR02_SUPPLIER_ITEMS.PROGRESSIVE_REG04=PUR04_SUPPLIER_ITEM_PRICES.PROGRESSIVE_REG04 and "+
          "PUR02_SUPPLIER_ITEMS.ITEM_CODE_ITM01=PUR04_SUPPLIER_ITEM_PRICES.ITEM_CODE_ITM01 and "+
          "PUR04_SUPPLIER_ITEM_PRICES.PRICELIST_CODE_PUR03=? and "+
          "PUR04_SUPPLIER_ITEM_PRICES.START_DATE<=? and "+
          "(PUR04_SUPPLIER_ITEM_PRICES.END_DATE>? or PUR04_SUPPLIER_ITEM_PRICES.END_DATE is null) ";

      Map attribute2dbField = new HashMap();
      attribute2dbField.put("companyCodeSys01PUR02","PUR02_SUPPLIER_ITEMS.COMPANY_CODE_SYS01");
      attribute2dbField.put("itemCodeItm01PUR02","PUR02_SUPPLIER_ITEMS.ITEM_CODE_ITM01");
      attribute2dbField.put("supplierItemCodePUR02","PUR02_SUPPLIER_ITEMS.SUPPLIER_ITEM_CODE");
      attribute2dbField.put("progressiveReg04PUR02","PUR02_SUPPLIER_ITEMS.PROGRESSIVE_REG04");
      attribute2dbField.put("progressiveHie02PUR02","PUR02_SUPPLIER_ITEMS.PROGRESSIVE_HIE02");
      attribute2dbField.put("progressiveHie01PUR02","PUR02_SUPPLIER_ITEMS.PROGRESSIVE_HIE01");
      attribute2dbField.put("minPurchaseQtyPUR02","PUR02_SUPPLIER_ITEMS.MIN_PURCHASE_QTY");
      attribute2dbField.put("multipleQtyPUR02","PUR02_SUPPLIER_ITEMS.MULTIPLE_QTY");
      attribute2dbField.put("umCodeReg02PUR02","PUR02_SUPPLIER_ITEMS.UM_CODE_REG02");
      attribute2dbField.put("enabledPUR02","PUR02_SUPPLIER_ITEMS.ENABLED");
      attribute2dbField.put("descriptionSYS10","SYS10_COMPANY_TRANSLATIONS.DESCRIPTION");
      attribute2dbField.put("decimalsREG02","REG02_MEASURE_UNITS.DECIMALS");

      attribute2dbField.put("vatCodeReg01ITM01","ITM01_ITEMS.VAT_CODE_REG01");
      attribute2dbField.put("vatDescriptionSYS10","SYS10_VAT.DESCRIPTION");
      attribute2dbField.put("deductibleREG01","REG01_VATS.DEDUCTIBLE");
      attribute2dbField.put("valueREG01","REG01_VATS.VALUE");
      attribute2dbField.put("valuePUR04","PUR04_SUPPLIER_ITEM_PRICES.VALUE");
      attribute2dbField.put("startDatePUR04","PUR04_SUPPLIER_ITEM_PRICES.START_DATE");
      attribute2dbField.put("endDatePUR04","PUR04_SUPPLIER_ITEM_PRICES.END_DATE");

      attribute2dbField.put("useVariant1ITM01","ITM01_ITEMS.USE_VARIANT_1");
      attribute2dbField.put("useVariant2ITM01","ITM01_ITEMS.USE_VARIANT_2");
      attribute2dbField.put("useVariant3ITM01","ITM01_ITEMS.USE_VARIANT_3");
      attribute2dbField.put("useVariant4ITM01","ITM01_ITEMS.USE_VARIANT_4");
      attribute2dbField.put("useVariant5ITM01","ITM01_ITEMS.USE_VARIANT_5");

			attribute2dbField.put("noWarehouseMovITM01","ITM01_ITEMS.NO_WAREHOUSE_MOV");

      ArrayList values = new ArrayList();
      values.add(serverLanguageId);
      values.add(companyCodeSYS10);
      values.add(progressiveREG04);
      values.add(serverLanguageId);
      values.add(pars.getCode());
      values.add(pricelistCodePUR03);
      values.add(new java.sql.Date(System.currentTimeMillis()));
      values.add(new java.sql.Date(System.currentTimeMillis()));

      // read from PUR02 table...
      Response answer = QueryUtil.getQuery(
          conn,
          new UserSessionParameters(username),
          sql,
          values,
          attribute2dbField,
          SupplierPriceItemVO.class,
          "Y",
          "N",
          null,
          new GridParams(),
          true
      );
      if (answer.isError()) throw new Exception(answer.getErrorMessage()); else return (VOListResponse)answer;

    }
    catch (Throwable ex) {
      Logger.error(username,this.getClass().getName(),"executeCommand","Error while validating supplier item code",ex);
      throw new Exception(ex.getMessage());
    }
    finally {
        try {
            pstmt.close();
        }
        catch (Exception exx) {}
        try {
            if (this.conn==null && conn!=null) {
                // close only local connection
                conn.commit();
                conn.close();
            }

        }
        catch (Exception exx) {}
    }


  }



}

