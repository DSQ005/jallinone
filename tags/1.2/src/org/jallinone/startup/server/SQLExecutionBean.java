package org.jallinone.startup.server;

import org.openswing.swing.server.*;
import org.openswing.swing.message.receive.java.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import org.jallinone.startup.java.DbConnVO;
import java.util.Properties;
import org.openswing.swing.logger.server.Logger;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.sql.*;
import java.util.ArrayList;
import org.jallinone.commons.java.ApplicationConsts;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Execute a SQL script.</p>
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
public class SQLExecutionBean {


  public SQLExecutionBean() {}


  /**
   * Execute the SQL scripts contained in the specified file.
   * @param fileName file to read
   */
  public final void executeSQL(Connection conn,DbConnVO vo,String fileName) throws Throwable {
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer("");
    InputStream in = null;
    try {
      try {
        in = this.getClass().getResourceAsStream("/" + fileName);
      }
      catch (Exception ex5) {
      }
      if (in==null)
        in = new FileInputStream(this.getClass().getResource("/").getPath().replaceAll("%20"," ") + fileName);

      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line = null;
      ArrayList vals = new ArrayList();
      int pos = -1;
      String oldfk = null;
      String oldIndex = null;
      StringBuffer newIndex = null;
      StringBuffer newfk = null;
      ArrayList fks = new ArrayList();
      ArrayList indexes = new ArrayList();
      boolean fkFound = false;
      boolean indexFound = false;
      String defaultValue = null;
      boolean useDefaultValue =
          conn.getMetaData().getDriverName().equals("oracle.jdbc.driver.OracleDriver") ||
          conn.getMetaData().getDriverName().equals("com.microsoft.jdbc.sqlserver.SQLServerDriver") ||
          conn.getMetaData().getDriverName().equals("com.mysql.jdbc.Driver");
      while ( (line = br.readLine()) != null) {
        sql.append(' ').append(line);
        if (line.endsWith(";")) {
          if (vo.getDriverName().equals("oracle.jdbc.driver.OracleDriver")) {
            sql = replace(sql, " VARCHAR(", " VARCHAR2(");
            sql = replace(sql, " NUMERIC(", " NUMBER(");
            sql = replace(sql, " DECIMAL(", " NUMBER(");
            sql = replace(sql, " TIMESTAMP", " DATE ");
            sql = replace(sql, " DATETIME", " DATE ");
          }
          else if (vo.getDriverName().equals("com.microsoft.jdbc.sqlserver.SQLServerDriver")) {
            sql = replace(sql, " TIMESTAMP", " DATETIME ");
            sql = replace(sql, " DATE ", " DATETIME  ");
          }
          else if (vo.getDriverName().toLowerCase().indexOf("postgres")!=-1) {
            sql = replace(sql, " DATETIME", " TIMESTAMP ");
            sql = replace(sql, " DATE ", " TIMESTAMP ");
          }
          else {
            sql = replace(sql, " DATE ", " DATETIME ");
          }


          sql = replace(sql,"ON DELETE NO ACTION","");
          sql = replace(sql,"ON UPDATE NO ACTION","");

          if (sql.indexOf(":COMPANY_CODE") != -1) {
            sql = replace(sql, ":COMPANY_CODE", "'" + vo.getCompanyCode() + "'");
          }
          if (sql.indexOf(":COMPANY_DESCRIPTION") != -1) {
            sql = replace(sql, ":COMPANY_DESCRIPTION",
                          "'" + vo.getCompanyDescription() + "'");
          }
          if (sql.indexOf(":LANGUAGE_CODE") != -1) {
            sql = replace(sql, ":LANGUAGE_CODE",
                          "'" + vo.getLanguageCode() + "'");
          }
          if (sql.indexOf(":LANGUAGE_DESCRIPTION") != -1) {
            sql = replace(sql, ":LANGUAGE_DESCRIPTION",
                          "'" + vo.getLanguageDescription() + "'");
          }
          if (sql.indexOf(":CLIENT_LANGUAGE_CODE") != -1) {
            sql = replace(sql, ":CLIENT_LANGUAGE_CODE",
                          "'" + vo.getClientLanguageCode() + "'");
          }
          if (sql.indexOf(":PASSWORD") != -1) {
            sql = replace(sql, ":PASSWORD", "'" + vo.getAdminPassword() + "'");
          }
          if (sql.indexOf(":DATE") != -1) {
            sql = replace(sql, ":DATE", "?");
            vals.add(new Date(System.currentTimeMillis()));
          }

          if (sql.indexOf(":CURRENCY_CODE") != -1) {
            sql = replace(sql, ":CURRENCY_CODE", "'" + vo.getCurrencyCodeREG03() + "'");
          }
          if (sql.indexOf(":CURRENCY_SYMBOL") != -1) {
            sql = replace(sql, ":CURRENCY_SYMBOL", "'" + vo.getCurrencySymbolREG03() + "'");
          }
          if (sql.indexOf(":DECIMALS") != -1) {
            sql = replace(sql, ":DECIMALS", vo.getDecimalsREG03().toString());
          }
          if (sql.indexOf(":DECIMAL_SYMBOL") != -1) {
            sql = replace(sql, ":DECIMAL_SYMBOL", "'" + vo.getDecimalSymbolREG03() + "'");
          }
          if (sql.indexOf(":THOUSAND_SYMBOL") != -1) {
            sql = replace(sql, ":THOUSAND_SYMBOL", "'" + vo.getThousandSymbolREG03() + "'");
          }

          if (sql.indexOf(":USE_VARIANT_TYPE_1") != -1) {
            sql = replace(sql, ":USE_VARIANT_TYPE_1", "'" + vo.getUseVariantType1() + "'");
          }
          if (sql.indexOf(":USE_VARIANT_TYPE_2") != -1) {
            sql = replace(sql, ":USE_VARIANT_TYPE_2", "'" + vo.getUseVariantType2() + "'");
          }
          if (sql.indexOf(":USE_VARIANT_TYPE_3") != -1) {
            sql = replace(sql, ":USE_VARIANT_TYPE_3", "'" + vo.getUseVariantType3() + "'");
          }
          if (sql.indexOf(":USE_VARIANT_TYPE_4") != -1) {
            sql = replace(sql, ":USE_VARIANT_TYPE_4", "'" + vo.getUseVariantType4() + "'");
          }
          if (sql.indexOf(":USE_VARIANT_TYPE_5") != -1) {
            sql = replace(sql, ":USE_VARIANT_TYPE_5", "'" + vo.getUseVariantType5() + "'");
          }

          if (sql.indexOf(":VARIANT_1") != -1) {
            sql = replace(sql, ":VARIANT_1", "'" + (vo.getVariant1()==null || vo.getVariant1().trim().equals("")?ApplicationConsts.JOLLY:vo.getVariant1()) + "'");
          }
          if (sql.indexOf(":VARIANT_2") != -1) {
            sql = replace(sql, ":VARIANT_2", "'" + (vo.getVariant2()==null || vo.getVariant2().trim().equals("")?ApplicationConsts.JOLLY:vo.getVariant2()) + "'");
          }
          if (sql.indexOf(":VARIANT_3") != -1) {
            sql = replace(sql, ":VARIANT_3", "'" + (vo.getVariant3()==null || vo.getVariant3().trim().equals("")?ApplicationConsts.JOLLY:vo.getVariant3()) + "'");
          }
          if (sql.indexOf(":VARIANT_4") != -1) {
            sql = replace(sql, ":VARIANT_4", "'" + (vo.getVariant4()==null || vo.getVariant4().trim().equals("")?ApplicationConsts.JOLLY:vo.getVariant4()) + "'");
          }
          if (sql.indexOf(":VARIANT_5") != -1) {
            sql = replace(sql, ":VARIANT_5", "'" + (vo.getVariant5()==null || vo.getVariant5().trim().equals("")?ApplicationConsts.JOLLY:vo.getVariant5()) + "'");
          }

          if (!useDefaultValue)
            while((pos=sql.indexOf("DEFAULT "))!=-1) {
              defaultValue = sql.substring(pos, sql.indexOf(",", pos));
              sql = replace(
                  sql,
                  defaultValue,
                  ""
              );
            }

          fkFound = false;
          while((pos=sql.indexOf("FOREIGN KEY"))!=-1) {
            oldfk = sql.substring(pos,sql.indexOf(")",sql.indexOf(")",pos)+1)+1);
            sql = replace(
                sql,
                oldfk,
                ""
            );
            newfk = new StringBuffer("ALTER TABLE ");
            newfk.append(sql.substring(sql.indexOf(" TABLE ")+7,sql.indexOf("(")).trim());
            newfk.append(" ADD ");
            newfk.append(oldfk);
            fks.add(newfk);
            fkFound = true;
          }

          if (fkFound)
            sql = removeCommasAtEnd(sql);

          indexFound = false;
          while((pos=sql.indexOf("INDEX "))!=-1) {
            oldIndex = sql.substring(pos,sql.indexOf(")",pos)+1);
            sql = replace(
                sql,
                oldIndex,
                ""
            );
            newIndex = new StringBuffer("CREATE ");
            newIndex.append(oldIndex.substring(0,oldIndex.indexOf("(")));
            newIndex.append(" ON ");
            newIndex.append(sql.substring(sql.indexOf(" TABLE ")+7,sql.indexOf("(")).trim());
            newIndex.append( oldIndex.substring(oldIndex.indexOf("(")) );
            indexes.add(newIndex);
            indexFound = true;
          }

          if (indexFound)
            sql = removeCommasAtEnd(sql);


          if (sql.toString().trim().length()>0) {
            pstmt = conn.prepareStatement(sql.toString().substring(0,sql.length() - 1));
            for (int i = 0; i < vals.size(); i++) {
              pstmt.setObject(i + 1, vals.get(i));
            }
            pstmt.execute();
            pstmt.close();
          }

          sql.delete(0, sql.length());
          vals.clear();
        }
      }
      br.close();

      for(int i=0;i<fks.size();i++) {
        sql = (StringBuffer)fks.get(i);
        pstmt = conn.prepareStatement(sql.toString());
        try {
          pstmt.execute();
        }
        catch (SQLException ex4) {
          Logger.error("NONAME", this.getClass().getName(), "executeSQL",
                       "Invalid SQL: " + sql, ex4);
        }
        pstmt.close();
      }

      for(int i=0;i<indexes.size();i++) {
        sql = (StringBuffer)indexes.get(i);
        pstmt = conn.prepareStatement(sql.toString());
        try {
          pstmt.execute();
        }
        catch (SQLException ex3) {
          Logger.error("NONAME", this.getClass().getName(), "executeSQL",
                       "Invalid SQL: " + sql, ex3);
        }
        pstmt.close();
      }

    }
    catch (Throwable ex) {
      try {
        Logger.error("NONAME", this.getClass().getName(), "executeSQL",
                     "Invalid SQL: " + sql, ex);
      }
      catch (Exception ex2) {
      }
      throw ex;
    }
    finally {
      try {
        if (pstmt!=null)
          pstmt.close();
      }
      catch (SQLException ex1) {
      }
    }
  }


  /**
   * Remove "," symbols at the end of the script.
   * Example: "INDEX WKF10_INSTANCE_PROPERTIES_FKIndex2(PROGRESSIVE_WKF01, PROGRESSIVE_WKF08),  , );
   * @param sql script to analyze
   * @return sql script, without "," symbols at the end
   */
  private StringBuffer removeCommasAtEnd(StringBuffer sql) {
    int i=sql.length()-3;
    while(i>0 && (sql.charAt(i)==' ' || sql.charAt(i)==','))
      i--;
    sql = sql.replace(i+1,sql.length()-2," ");

    return sql;
  }


  /**
   * Replace the specified pattern with the new one.
   * @param b sql script
   * @param oldPattern pattern to replace
   * @param newPattern new pattern
   * @return sql script with substitutions
   */
  private StringBuffer replace(StringBuffer b,String oldPattern,String newPattern) {
    int i = 0;
    while((i=b.indexOf(oldPattern,i))!=-1) {
      b.replace(i,i+oldPattern.length(),newPattern);
      i = i+oldPattern.length();
    }
    return b;
  }


}