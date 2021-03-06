package org.jallinone.sales.charges.java;

import org.jallinone.system.customizations.java.BaseValueObject;
import java.math.BigDecimal;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Value object used to store charges info.</p>
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
public class ChargeVO extends BaseValueObject {


  private String chargeCodeSAL06;
  private java.math.BigDecimal progressiveSys10SAL06;
  private String enabledSAL06;
  private String descriptionSYS10;
  private String companyCodeSys01SAL06;
  private java.math.BigDecimal valueSAL06;
  private java.math.BigDecimal percSAL06;
  private String currencyCodeReg03SAL06;
  private String vatCodeReg01SAL06;
  private String vatDescriptionSYS10;
  private java.math.BigDecimal vatValueREG01;
  private java.math.BigDecimal vatDeductibleREG01;
	private String currencySymbolREG03;
	private BigDecimal decimalsREG03;


  public ChargeVO() {
  }


  public String getChargeCodeSAL06() {
    return chargeCodeSAL06;
  }
  public void setChargeCodeSAL06(String chargeCodeSAL06) {
    this.chargeCodeSAL06 = chargeCodeSAL06;
  }
  public java.math.BigDecimal getProgressiveSys10SAL06() {
    return progressiveSys10SAL06;
  }
  public void setProgressiveSys10SAL06(java.math.BigDecimal progressiveSys10SAL06) {
    this.progressiveSys10SAL06 = progressiveSys10SAL06;
  }
  public String getDescriptionSYS10() {
    return descriptionSYS10;
  }
  public void setDescriptionSYS10(String descriptionSYS10) {
    this.descriptionSYS10 = descriptionSYS10;
  }
  public String getEnabledSAL06() {
    return enabledSAL06;
  }
  public void setEnabledSAL06(String enabledSAL06) {
    this.enabledSAL06 = enabledSAL06;
  }
  public String getCompanyCodeSys01SAL06() {
    return companyCodeSys01SAL06;
  }
  public void setCompanyCodeSys01SAL06(String companyCodeSys01SAL06) {
    this.companyCodeSys01SAL06 = companyCodeSys01SAL06;
  }
  public java.math.BigDecimal getValueSAL06() {
    return valueSAL06;
  }
  public void setValueSAL06(java.math.BigDecimal valueSAL06) {
    this.valueSAL06 = valueSAL06;
  }
  public java.math.BigDecimal getPercSAL06() {
    return percSAL06;
  }
  public void setPercSAL06(java.math.BigDecimal percSAL06) {
    this.percSAL06 = percSAL06;
  }
  public String getCurrencyCodeReg03SAL06() {
    return currencyCodeReg03SAL06;
  }
  public void setCurrencyCodeReg03SAL06(String currencyCodeReg03SAL06) {
    this.currencyCodeReg03SAL06 = currencyCodeReg03SAL06;
  }
  public String getVatCodeReg01SAL06() {
    return vatCodeReg01SAL06;
  }
  public void setVatCodeReg01SAL06(String vatCodeReg01SAL06) {
    this.vatCodeReg01SAL06 = vatCodeReg01SAL06;
  }
  public String getVatDescriptionSYS10() {
    return vatDescriptionSYS10;
  }
  public void setVatDescriptionSYS10(String vatDescriptionSYS10) {
    this.vatDescriptionSYS10 = vatDescriptionSYS10;
  }
  public java.math.BigDecimal getVatValueREG01() {
    return vatValueREG01;
  }
  public void setVatValueREG01(java.math.BigDecimal vatValueREG01) {
    this.vatValueREG01 = vatValueREG01;
  }
  public java.math.BigDecimal getVatDeductibleREG01() {
    return vatDeductibleREG01;
  }
  public void setVatDeductibleREG01(java.math.BigDecimal vatDeductibleREG01) {
    this.vatDeductibleREG01 = vatDeductibleREG01;
  }
  public String getCurrencySymbolREG03() {
    return currencySymbolREG03;
  }
  public BigDecimal getDecimalsREG03() {
    return decimalsREG03;
  }
  public void setCurrencySymbolREG03(String currencySymbolREG03) {
    this.currencySymbolREG03 = currencySymbolREG03;
  }
  public void setDecimalsREG03(BigDecimal decimalsREG03) {
    this.decimalsREG03 = decimalsREG03;
  }

}
