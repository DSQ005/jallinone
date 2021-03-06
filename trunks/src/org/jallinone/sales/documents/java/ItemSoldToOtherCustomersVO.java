package org.jallinone.sales.documents.java;

import java.math.BigDecimal;
import org.openswing.swing.message.receive.java.ValueObjectImpl;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Value object used to store an item sold to other customers.</p>
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
public class ItemSoldToOtherCustomersVO extends ValueObjectImpl {

  private String itemDescriptionSY10;
  private BigDecimal customersNr;


  public ItemSoldToOtherCustomersVO() {
  }


  public BigDecimal getCustomersNr() {
    return customersNr;
  }
  public String getItemDescriptionSY10() {
    return itemDescriptionSY10;
  }
  public void setCustomersNr(BigDecimal customersNr) {
    this.customersNr = customersNr;
  }
  public void setItemDescriptionSY10(String itemDescriptionSY10) {
    this.itemDescriptionSY10 = itemDescriptionSY10;
  }


}
