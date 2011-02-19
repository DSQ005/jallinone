package org.jallinone.subjects.java;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Detail frame related to a company.</p>
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
public class SubjectPK implements Serializable {


  private String companyCodeSys01REG04 = null;
  private BigDecimal progressiveREG04 = null;



  public SubjectPK(String companyCodeSys01REG04,BigDecimal progressiveREG04) {
    this.companyCodeSys01REG04 = companyCodeSys01REG04;
    this.progressiveREG04 = progressiveREG04;
  }


  public String getCompanyCodeSys01REG04() {
    return companyCodeSys01REG04;
  }
  public BigDecimal getProgressiveREG04() {
    return progressiveREG04;
  }

}