package org.jallinone.sales.discounts.java;

import java.math.BigDecimal;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Value object used to store a hierarchy customer discount.</p>
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
public class HierarCustomerDiscountVO extends DiscountVO {

  private BigDecimal progressiveHie01SAL10;


  public HierarCustomerDiscountVO() {
  }


  public BigDecimal getProgressiveHie01SAL10() {
    return progressiveHie01SAL10;
  }
  public void setProgressiveHie01SAL10(BigDecimal progressiveHie01SAL10) {
    this.progressiveHie01SAL10 = progressiveHie01SAL10;
  }

}
