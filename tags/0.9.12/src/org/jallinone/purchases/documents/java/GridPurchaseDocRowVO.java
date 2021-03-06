package org.jallinone.purchases.documents.java;

import org.openswing.swing.message.receive.java.*;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Value object used to store purchase order row info, for the order rows grid.</p>
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
public class GridPurchaseDocRowVO extends ValueObjectImpl {

  private String companyCodeSys01DOC07;
  private String docTypeDOC07;
  private java.math.BigDecimal docYearDOC07;
  private java.math.BigDecimal docNumberDOC07;
  private String itemCodeItm01DOC07;
  private String supplierItemCodePur02DOC07;
  private String vatCodeItm01DOC07;
  private java.math.BigDecimal valuePur04DOC07;
  private java.math.BigDecimal valueDOC07;
  private java.math.BigDecimal qtyDOC07;
  private java.math.BigDecimal discountValueDOC07;
  private java.math.BigDecimal discountPercDOC07;
  private String descriptionSYS10;
  private java.math.BigDecimal vatValueDOC07;
  private java.math.BigDecimal rowNumberDOC07;
  private java.math.BigDecimal inQtyDOC07;
  private java.math.BigDecimal orderQtyDOC07;
  private java.math.BigDecimal invoiceQtyDOC07;


  public GridPurchaseDocRowVO() {
  }


  public String getCompanyCodeSys01DOC07() {
    return companyCodeSys01DOC07;
  }
  public void setCompanyCodeSys01DOC07(String companyCodeSys01DOC07) {
    this.companyCodeSys01DOC07 = companyCodeSys01DOC07;
  }
  public String getDocTypeDOC07() {
    return docTypeDOC07;
  }
  public void setDocTypeDOC07(String docTypeDOC07) {
    this.docTypeDOC07 = docTypeDOC07;
  }

  public java.math.BigDecimal getDocYearDOC07() {
    return docYearDOC07;
  }
  public void setDocYearDOC07(java.math.BigDecimal docYearDOC07) {
    this.docYearDOC07 = docYearDOC07;
  }
  public java.math.BigDecimal getDocNumberDOC07() {
    return docNumberDOC07;
  }
  public void setDocNumberDOC07(java.math.BigDecimal docNumberDOC07) {
    this.docNumberDOC07 = docNumberDOC07;
  }
  public String getItemCodeItm01DOC07() {
    return itemCodeItm01DOC07;
  }
  public void setItemCodeItm01DOC07(String itemCodeItm01DOC07) {
    this.itemCodeItm01DOC07 = itemCodeItm01DOC07;
  }
  public String getSupplierItemCodePur02DOC07() {
    return supplierItemCodePur02DOC07;
  }
  public void setSupplierItemCodePur02DOC07(String supplierItemCodePur02DOC07) {
    this.supplierItemCodePur02DOC07 = supplierItemCodePur02DOC07;
  }
  public String getVatCodeItm01DOC07() {
    return vatCodeItm01DOC07;
  }
  public void setVatCodeItm01DOC07(String vatCodeItm01DOC07) {
    this.vatCodeItm01DOC07 = vatCodeItm01DOC07;
  }
  public java.math.BigDecimal getValuePur04DOC07() {
    return valuePur04DOC07;
  }
  public void setValuePur04DOC07(java.math.BigDecimal valuePur04DOC07) {
    this.valuePur04DOC07 = valuePur04DOC07;
  }
  public java.math.BigDecimal getValueDOC07() {
    return valueDOC07;
  }
  public void setValueDOC07(java.math.BigDecimal valueDOC07) {
    this.valueDOC07 = valueDOC07;
  }
  public java.math.BigDecimal getQtyDOC07() {
    return qtyDOC07;
  }
  public void setQtyDOC07(java.math.BigDecimal qtyDOC07) {
    this.qtyDOC07 = qtyDOC07;
  }
  public java.math.BigDecimal getDiscountValueDOC07() {
    return discountValueDOC07;
  }
  public void setDiscountValueDOC07(java.math.BigDecimal discountValueDOC07) {
    this.discountValueDOC07 = discountValueDOC07;
  }
  public java.math.BigDecimal getDiscountPercDOC07() {
    return discountPercDOC07;
  }
  public void setDiscountPercDOC07(java.math.BigDecimal discountPercDOC07) {
    this.discountPercDOC07 = discountPercDOC07;
  }
  public String getDescriptionSYS10() {
    return descriptionSYS10;
  }
  public void setDescriptionSYS10(String descriptionSYS10) {
    this.descriptionSYS10 = descriptionSYS10;
  }
  public java.math.BigDecimal getVatValueDOC07() {
    return vatValueDOC07;
  }
  public void setVatValueDOC07(java.math.BigDecimal vatValueDOC07) {
    this.vatValueDOC07 = vatValueDOC07;
  }
  public java.math.BigDecimal getRowNumberDOC07() {
    return rowNumberDOC07;
  }
  public void setRowNumberDOC07(java.math.BigDecimal rowNumberDOC07) {
    this.rowNumberDOC07 = rowNumberDOC07;
  }
  public void setInQtyDOC07(java.math.BigDecimal inQtyDOC07) {
    this.inQtyDOC07 = inQtyDOC07;
  }
  public java.math.BigDecimal getInQtyDOC07() {
    return inQtyDOC07;
  }
  public java.math.BigDecimal getOrderQtyDOC07() {
    return orderQtyDOC07;
  }
  public void setOrderQtyDOC07(java.math.BigDecimal orderQtyDOC07) {
    this.orderQtyDOC07 = orderQtyDOC07;
  }
  public java.math.BigDecimal getInvoiceQtyDOC07() {
    return invoiceQtyDOC07;
  }
  public void setInvoiceQtyDOC07(java.math.BigDecimal invoiceQtyDOC07) {
    this.invoiceQtyDOC07 = invoiceQtyDOC07;
  }


}
