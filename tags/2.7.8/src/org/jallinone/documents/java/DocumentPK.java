package org.jallinone.documents.java;

import java.io.Serializable;

/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Document primary key.</p>
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
public class DocumentPK implements Serializable {

  private String companyCodeSys01DOC14;
  private java.math.BigDecimal progressiveDOC14;

  
  public DocumentPK() {}

  public DocumentPK(String companyCodeSys01DOC14,java.math.BigDecimal progressiveDOC14) {
    this.companyCodeSys01DOC14 = companyCodeSys01DOC14;
    this.progressiveDOC14 = progressiveDOC14;
  }


  public String getCompanyCodeSys01DOC14() {
    return companyCodeSys01DOC14;
  }
  public java.math.BigDecimal getProgressiveDOC14() {
    return progressiveDOC14;
  }

  public void setCompanyCodeSys01DOC14(String companyCodeSys01DOC14) {
	  this.companyCodeSys01DOC14 = companyCodeSys01DOC14;
  }

  public void setProgressiveDOC14(java.math.BigDecimal progressiveDOC14) {
	  this.progressiveDOC14 = progressiveDOC14;
  }

}
