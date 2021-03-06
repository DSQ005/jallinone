package org.jallinone.sales.documents.server;

import org.jallinone.accounting.movements.java.TaxableIncomeVO;
import org.jallinone.sales.documents.java.SaleDocPK;
import org.openswing.swing.message.receive.java.VOListResponse;
import java.util.HashMap;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Help class used to update all taxable incomes for all items and activities and charges (value charges),
 * for the specified sale document (order, contract, retail sale, invoice).
 * After updating all taxable incomes, also document totals are updated.</p>
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

public interface UpdateTaxableIncomes {

	/**
	 * Unsupported method, used to force the generation of a complex type in wsdl file for the return type
	 */
	public TaxableIncomeVO getTaxableIncome();

	public VOListResponse updateTaxableIncomes(
             HashMap variant1Descriptions,
             HashMap variant2Descriptions,
             HashMap variant3Descriptions,
             HashMap variant4Descriptions,
             HashMap variant5Descriptions,
             SaleDocPK pk, String serverLanguageId, String username) throws Throwable;

}

