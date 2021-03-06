package org.jallinone.items.java;

import org.openswing.swing.message.receive.java.*;
import org.jallinone.variants.java.VariantsItemDescriptor;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Value object used to store item info for the grid.</p>
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
public class GridItemVO extends ValueObjectImpl implements VariantsItemDescriptor {

  private String companyCodeSys01ITM01;
  private String itemCodeITM01;
  private String descriptionSYS10;
  private java.math.BigDecimal progressiveHie02ITM01;
  private java.math.BigDecimal progressiveHie01ITM01;
  private String minSellingQtyUmCodeReg02ITM01;
  private Boolean serialNumberRequiredITM01;
  private java.math.BigDecimal decimalsREG02;

  private Boolean useVariant1ITM01;
  private Boolean useVariant2ITM01;
  private Boolean useVariant3ITM01;
  private Boolean useVariant4ITM01;
  private Boolean useVariant5ITM01;


  public GridItemVO() {
  }


  public String getCompanyCodeSys01ITM01() {
    return companyCodeSys01ITM01;
  }
  public void setCompanyCodeSys01ITM01(String companyCodeSys01ITM01) {
    this.companyCodeSys01ITM01 = companyCodeSys01ITM01;
  }
  public String getItemCodeITM01() {
    return itemCodeITM01;
  }
  public void setItemCodeITM01(String itemCodeITM01) {
    this.itemCodeITM01 = itemCodeITM01;
  }
  public String getDescriptionSYS10() {
    return descriptionSYS10;
  }
  public void setDescriptionSYS10(String descriptionSYS10) {
    this.descriptionSYS10 = descriptionSYS10;
  }
  public java.math.BigDecimal getProgressiveHie02ITM01() {
    return progressiveHie02ITM01;
  }
  public void setProgressiveHie02ITM01(java.math.BigDecimal progressiveHie02ITM01) {
    this.progressiveHie02ITM01 = progressiveHie02ITM01;
  }
  public java.math.BigDecimal getProgressiveHie01ITM01() {
    return progressiveHie01ITM01;
  }
  public void setProgressiveHie01ITM01(java.math.BigDecimal progressiveHie01ITM01) {
    this.progressiveHie01ITM01 = progressiveHie01ITM01;
  }
  public String getMinSellingQtyUmCodeReg02ITM01() {
    return minSellingQtyUmCodeReg02ITM01;
  }
  public void setMinSellingQtyUmCodeReg02ITM01(String minSellingQtyUmCodeReg02ITM01) {
    this.minSellingQtyUmCodeReg02ITM01 = minSellingQtyUmCodeReg02ITM01;
  }
  public Boolean getSerialNumberRequiredITM01() {
    return serialNumberRequiredITM01;
  }
  public void setSerialNumberRequiredITM01(Boolean serialNumberRequiredITM01) {
    this.serialNumberRequiredITM01 = serialNumberRequiredITM01;
  }
  public java.math.BigDecimal getDecimalsREG02() {
    return decimalsREG02;
  }
  public void setDecimalsREG02(java.math.BigDecimal decimalsREG02) {
    this.decimalsREG02 = decimalsREG02;
  }
  public Boolean getUseVariant1ITM01() {
    return useVariant1ITM01;
  }
  public Boolean getUseVariant2ITM01() {
    return useVariant2ITM01;
  }
  public Boolean getUseVariant3ITM01() {
    return useVariant3ITM01;
  }
  public Boolean getUseVariant4ITM01() {
    return useVariant4ITM01;
  }
  public Boolean getUseVariant5ITM01() {
    return useVariant5ITM01;
  }
  public void setUseVariant5ITM01(Boolean useVariant5ITM01) {
    this.useVariant5ITM01 = useVariant5ITM01;
  }
  public void setUseVariant4ITM01(Boolean useVariant4ITM01) {
    this.useVariant4ITM01 = useVariant4ITM01;
  }
  public void setUseVariant3ITM01(Boolean useVariant3ITM01) {
    this.useVariant3ITM01 = useVariant3ITM01;
  }
  public void setUseVariant2ITM01(Boolean useVariant2ITM01) {
    this.useVariant2ITM01 = useVariant2ITM01;
  }
  public void setUseVariant1ITM01(Boolean useVariant1ITM01) {
    this.useVariant1ITM01 = useVariant1ITM01;
  }

  public String getCompanyCodeSys01() {
    return companyCodeSys01ITM01;
  }

  public String getItemCodeItm01() {
    return itemCodeITM01;
  }


}
