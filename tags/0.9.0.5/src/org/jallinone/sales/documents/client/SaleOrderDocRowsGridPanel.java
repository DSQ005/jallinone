package org.jallinone.sales.documents.client;

import java.awt.*;
import javax.swing.*;
import org.openswing.swing.client.*;
import javax.swing.border.*;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.table.columns.client.*;
import org.openswing.swing.form.client.Form;
import java.awt.event.*;
import org.openswing.swing.table.java.ServerGridDataLocator;
import org.openswing.swing.util.java.Consts;
import org.jallinone.commons.java.ApplicationConsts;
import org.jallinone.sales.documents.java.*;
import java.math.BigDecimal;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.lookup.client.LookupServerDataLocator;
import org.openswing.swing.lookup.client.LookupListener;
import org.openswing.swing.message.receive.java.*;
import java.util.Collection;
import org.openswing.swing.util.client.ClientUtils;
import org.openswing.swing.tree.client.TreeServerDataLocator;
import org.openswing.swing.message.receive.java.*;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.domains.java.Domain;
import org.jallinone.items.java.ItemTypeVO;
import java.util.ArrayList;
import java.beans.Beans;
import org.jallinone.warehouse.availability.client.ItemAvailabilityPanel;
import org.jallinone.warehouse.availability.client.BookedItemsPanel;
import org.jallinone.warehouse.availability.client.OrderedItemsPanel;
import org.jallinone.sales.documents.itemdiscounts.client.*;
import java.util.HashSet;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Panel that contains the order rows grid + row detail.</p>
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
public class SaleOrderDocRowsGridPanel extends JPanel implements CurrencyColumnSettings,SaleDocument {

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel buttonsPanel = new JPanel();
  JSplitPane splitPane = new JSplitPane();
  FlowLayout flowLayout1 = new FlowLayout();
  InsertButton insertButton1 = new InsertButton();
  EditButton editButton1 = new EditButton();
  SaveButton saveButton1 = new SaveButton();
  ReloadButton reloadButton1 = new ReloadButton();
  DeleteButton deleteButton1 = new DeleteButton();
  NavigatorBar navigatorBar1 = new NavigatorBar();
  CopyButton copyButton1 = new CopyButton();
  GridControl grid = new GridControl();
  Form detailPanel = new Form();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  ExportButton exportButton1 = new ExportButton();
  DecimalColumn colRowNum = new DecimalColumn();
  TextColumn colItemCode = new TextColumn();
  TextColumn colItemDescr = new TextColumn();
  DecimalColumn colQty = new DecimalColumn();
  DecimalColumn colOutQty = new DecimalColumn();
  DecimalColumn colInvoiceQty = new DecimalColumn();
  CurrencyColumn colPriceUnit = new CurrencyColumn();
  CurrencyColumn colPrice = new CurrencyColumn();
  TextColumn colVatCode = new TextColumn();
  CurrencyColumn colVatValue = new CurrencyColumn();
  CurrencyColumn colTotalDisc = new CurrencyColumn();
  LabelControl labelItemCode = new LabelControl();
  CodLookupControl controlItemCode = new CodLookupControl();
  TextControl controlItemDescr = new TextControl();
  LabelControl labelQty = new LabelControl();
  NumericControl controlQty = new NumericControl();
  TextControl controlUmCode = new TextControl();
  LabelControl labelPriceUnit = new LabelControl();
  CurrencyControl controlPriceUnit = new CurrencyControl();
  LabelControl labelVat = new LabelControl();
  TextControl controlVatCode = new TextControl();
  TextControl controlVatDescr = new TextControl();
  LabelControl labelValueReg01 = new LabelControl();
  NumericControl controlValueReg01 = new NumericControl();
  LabelControl labelDeductibleReg01 = new LabelControl();
  NumericControl controlDeductibleReg01 = new NumericControl();
  LabelControl labelVatValue = new LabelControl();
  CurrencyControl controlTotalDisc = new CurrencyControl();
  LabelControl labelTotal = new LabelControl();
  CurrencyControl controlTotal = new CurrencyControl();
  LabelControl labelTotalDisc = new LabelControl();
  CurrencyControl controlVatValue = new CurrencyControl();

  /** header v.o. */
  private DetailSaleDocVO parentVO = null;

  /** grid data locator */
  private ServerGridDataLocator gridDataLocator = new ServerGridDataLocator();

  /** item code lookup controller */
  LookupController itemController = new LookupController();

  /** item code lookup data locator */
  LookupServerDataLocator itemDataLocator = new LookupServerDataLocator();

  LookupServerDataLocator levelDataLocator = new LookupServerDataLocator();
  TreeServerDataLocator treeLevelDataLocator = new TreeServerDataLocator();
  ComboBoxControl controlItemType = new ComboBoxControl();

  /** header panel */
  private Form headerPanel = null;

  /** detail frame */
  private SaleOrderDocFrame frame = null;

  LabelControl labelDeliveryDate = new LabelControl();
  DateControl controlDeliveryDate = new DateControl();

  JTabbedPane itemTabbedPane = new JTabbedPane();
  BookedItemsPanel bookedItemsPanel = new BookedItemsPanel(false,true);
  OrderedItemsPanel orderedItemsPanel = new OrderedItemsPanel(false,true);
  SaleDocRowDiscountsPanel discountsPanel = new SaleDocRowDiscountsPanel(this,grid,detailPanel,true);

  BorderLayout borderLayout2 = new BorderLayout();
  JPanel southPanel = new JPanel();


  public SaleOrderDocRowsGridPanel(SaleOrderDocFrame frame,Form headerPanel) {
    this.frame = frame;
    this.headerPanel = headerPanel;
    try {
      jbInit();

      if (Beans.isDesignTime())
        return;

      init();

      grid.setController(new SaleOrderDocRowsController(this));
      grid.setGridDataLocator(gridDataLocator);
      gridDataLocator.setServerMethodName("loadSaleDocRows");

      detailPanel.setFormController(new SaleOrderDocRowController(this));
      detailPanel.setMode(Consts.READONLY);

      // item code lookup...
      itemDataLocator.setGridMethodName("loadPriceItems");
      itemDataLocator.setValidationMethodName("validatePriceItemCode");

      controlItemCode.setLookupController(itemController);
      controlItemCode.setControllerMethodName("getItemsList");
      itemController.setForm(detailPanel);
      itemController.setLookupDataLocator(itemDataLocator);
      itemController.setFrameTitle("items");

      itemController.setCodeSelectionWindow(itemController.TREE_GRID_FRAME);
      treeLevelDataLocator.setServerMethodName("loadHierarchy");
      itemDataLocator.setTreeDataLocator(treeLevelDataLocator);
      itemDataLocator.setNodeNameAttribute("descriptionSYS10");

      itemController.setLookupValueObjectClassName("org.jallinone.sales.documents.java.PriceItemVO");
      itemController.addLookup2ParentLink("itemCodeItm01SAL02", "itemCodeItm01DOC02");
      itemController.addLookup2ParentLink("itemDescriptionSYS10", "descriptionSYS10");
      itemController.addLookup2ParentLink("minSellingQtyITM01","minSellingQtyItm01DOC02");
      itemController.addLookup2ParentLink("decimalsREG02", "decimalsReg02DOC02");
      itemController.addLookup2ParentLink("minSellingQtyUmCodeReg02ITM01", "minSellingQtyUmCodeReg02DOC02");
      itemController.addLookup2ParentLink("vatCodeReg01ITM01", "vatCodeItm01DOC02");
      itemController.addLookup2ParentLink("vatDescriptionSYS10", "vatDescriptionDOC02");
      itemController.addLookup2ParentLink("deductibleREG01", "deductibleReg01DOC02");
      itemController.addLookup2ParentLink("valueREG01", "valueReg01DOC02");
      itemController.addLookup2ParentLink("valueSAL02", "valueSal02DOC02");
      itemController.addLookup2ParentLink("startDateSAL02", "startDateSal02DOC02");
      itemController.addLookup2ParentLink("endDateSAL02", "endDateSal02DOC02");

      itemController.setAllColumnVisible(false);
      itemController.setVisibleColumn("itemCodeItm01SAL02", true);
      itemController.setVisibleColumn("itemDescriptionSYS10", true);
      itemController.setVisibleColumn("minSellingQtyITM01", true);
      itemController.setVisibleColumn("minSellingQtyUmCodeReg02ITM01", true);
      itemController.setVisibleColumn("valueSAL02", true);
      itemController.setVisibleColumn("startDateSAL02", true);
      itemController.setVisibleColumn("endDateSAL02", true);
      itemController.setPreferredWidthColumn("itemDescriptionSYS10", 200);
      itemController.setPreferredWidthColumn("minSellingQtyITM01", 60);
      itemController.setPreferredWidthColumn("minSellingQtyUmCodeReg02ITM01", 50);
      itemController.setFramePreferedSize(new Dimension(750,500));
      itemController.addLookupListener(new LookupListener() {

        public void codeValidated(boolean validated) {}

        public void codeChanged(ValueObject parentVO,Collection parentChangedAttributes) {
          // fill in the detail form, according to the selected item settings...
          DetailSaleDocRowVO vo = (DetailSaleDocRowVO)detailPanel.getVOModel().getValueObject();
          if (vo.getItemCodeItm01DOC02()==null || vo.getItemCodeItm01DOC02().equals("")) {
            vo.setDescriptionSYS10(null);
            vo.setMinSellingQtyUmCodeReg02DOC02(null);
            vo.setMinSellingQtyItm01DOC02(null);
            vo.setDecimalsReg02DOC02(null);
            vo.setVatCodeItm01DOC02(null);
            vo.setVatDescriptionDOC02(null);
            vo.setDeductibleReg01DOC02(null);
            vo.setValueReg01DOC02(null);
            vo.setValueSal02DOC02(null);
            vo.setQtyDOC02(null);
          }
          else {
            vo.setQtyDOC02(vo.getMinSellingQtyItm01DOC02());
            controlQty.setMinValue(vo.getMinSellingQtyItm01DOC02().doubleValue());
            controlQty.setDecimals(vo.getDecimalsReg02DOC02().intValue());

            PriceItemVO lookupVO = (PriceItemVO)itemController.getLookupVO();
            bookedItemsPanel.getControlItemType().setValue(lookupVO.getProgressiveHie02ITM01());
            bookedItemsPanel.getControlItemCode().setValue(vo.getItemCodeItm01DOC02());
            bookedItemsPanel.getControlItemCode().getLookupController().forceValidate();
            bookedItemsPanel.getGrid().reloadData();
            orderedItemsPanel.getControlItemType().setValue(lookupVO.getProgressiveHie02ITM01());
            orderedItemsPanel.getControlItemCode().setValue(vo.getItemCodeItm01DOC02());
            orderedItemsPanel.getControlItemCode().getLookupController().forceValidate();
            orderedItemsPanel.getGrid().reloadData();
          }
          updateTotals();
        }

        public void beforeLookupAction(ValueObject parentVO) {}

        public void forceValidate() {}

      });



      colPrice.setDynamicSettings(this);
      colPriceUnit.setDynamicSettings(this);
      colTotalDisc.setDynamicSettings(this);
      colVatValue.setDynamicSettings(this);

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Retrieve item types and fill in the item types combo box and
   * set buttons disabilitation...
   */
  private void init() {
    Response res = ClientUtils.getData("loadItemTypes",new GridParams());
    final Domain d = new Domain("ITEM_TYPES");
    if (!res.isError()) {
      ItemTypeVO vo = null;
      ArrayList list = ((VOListResponse)res).getRows();
      for(int i=0;i<list.size();i++) {
        vo = (ItemTypeVO)list.get(i);
        d.addDomainPair(vo.getProgressiveHie02ITM02(),vo.getDescriptionSYS10());
      }
    }
    controlItemType.setDomain(d);
    controlItemType.getComboBox().addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==e.SELECTED && detailPanel.getMode()!=Consts.READONLY) {
          DetailSaleDocRowVO vo = (DetailSaleDocRowVO)detailPanel.getVOModel().getValueObject();
          vo.setItemCodeItm01DOC02(null);
          vo.setDescriptionSYS10(null);
          vo.setMinSellingQtyUmCodeReg02DOC02(null);
          vo.setVatCodeItm01DOC02(null);
          vo.setVatDescriptionDOC02(null);
          vo.setDeductibleReg01DOC02(null);
          vo.setValueReg01DOC02(null);
          vo.setValueSal02DOC02(null);
          vo.setQtyDOC02(null);

          int selIndex = ((JComboBox)e.getSource()).getSelectedIndex();
          Object selValue = d.getDomainPairList()[selIndex].getCode();
          treeLevelDataLocator.getTreeNodeParams().put(ApplicationConsts.PROGRESSIVE_HIE02,selValue);
        }
      }
    });

    // set buttons disabilitation...
    HashSet buttonsToDisable = new HashSet();
    buttonsToDisable.add(insertButton1);
    buttonsToDisable.add(editButton1);
    buttonsToDisable.add(deleteButton1);
    buttonsToDisable.add(copyButton1);
    detailPanel.addButtonsNotEnabled(buttonsToDisable,frame);
    grid.addButtonsNotEnabled(buttonsToDisable,frame);
  }



  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    this.setLayout(borderLayout1);
    buttonsPanel.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    grid.setAutoLoadData(false);
    grid.setCopyButton(copyButton1);
    grid.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    grid.setDeleteButton(deleteButton1);
    grid.setExportButton(exportButton1);
    grid.setFunctionId("DOC01_ORDERS");
    discountsPanel.getGrid().setFunctionId("DOC01_ORDERS");
    grid.setMaxSortedColumns(3);
    grid.setNavBar(navigatorBar1);
    grid.setReloadButton(reloadButton1);
    grid.setValueObjectClassName("org.jallinone.sales.documents.java.GridSaleDocRowVO");
    detailPanel.setLayout(gridBagLayout1);
    detailPanel.setBorder(titledBorder1);
    detailPanel.setMinimumSize(new Dimension(740, 140));
    detailPanel.setInsertButton(insertButton1);
    detailPanel.setCopyButton(copyButton1);
    detailPanel.setEditButton(editButton1);
    detailPanel.setReloadButton(reloadButton1);
    detailPanel.setSaveButton(saveButton1);
    detailPanel.setVOClassName("org.jallinone.sales.documents.java.DetailSaleDocRowVO");
    detailPanel.setFunctionId("DOC01_ORDERS");
    titledBorder1.setTitle(ClientSettings.getInstance().getResources().getResource("line detail"));
    titledBorder1.setTitleColor(Color.blue);
    colRowNum.setColumnFilterable(false);
    colRowNum.setColumnName("rowNumberDOC02");
    colRowNum.setColumnVisible(false);
    colRowNum.setSortVersus(org.openswing.swing.util.java.Consts.ASC_SORTED);
    colRowNum.setColumnSelectable(false);

    colItemCode.setColumnFilterable(false);
    colItemCode.setColumnName("itemCodeItm01DOC02");
    colItemCode.setColumnSortable(false);
    colItemCode.setPreferredWidth(80);
    colItemDescr.setColumnName("descriptionSYS10");
    colItemDescr.setColumnSortable(false);
    colItemDescr.setHeaderColumnName("itemDescriptionSYS10");
    colItemDescr.setPreferredWidth(200);
    colQty.setDecimals(5);
    colQty.setGrouping(false);
    colQty.setColumnName("qtyDOC02");
    colQty.setColumnSortable(false);
    colQty.setPreferredWidth(60);

    colOutQty.setDecimals(5);
    colOutQty.setGrouping(false);
    colOutQty.setColumnName("outQtyDOC02");
    colOutQty.setColumnSortable(false);
    colOutQty.setPreferredWidth(60);

    colInvoiceQty.setDecimals(5);
    colInvoiceQty.setGrouping(false);
    colInvoiceQty.setColumnName("invoiceQtyDOC02");
    colInvoiceQty.setColumnSortable(false);
    colInvoiceQty.setPreferredWidth(80);

    colPriceUnit.setColumnName("valueSal02DOC02");
    colPriceUnit.setDecimals(5);
    colPriceUnit.setPreferredWidth(90);
    colPrice.setColumnName("valueDOC02");
    colPrice.setColumnSortable(false);
    colPrice.setDecimals(5);
    colPrice.setPreferredWidth(90);
    colVatCode.setColumnName("vatCodeItm01DOC02");
    colVatCode.setColumnSortable(false);
    colVatCode.setHeaderColumnName("vatCode");
    colVatCode.setPreferredWidth(70);
    colVatValue.setColumnName("vatValueDOC02");
    colVatValue.setColumnSortable(false);
    colVatValue.setDecimals(5);
    colVatValue.setPreferredWidth(90);
    colTotalDisc.setColumnName("totalDiscountDOC02");
    colTotalDisc.setPreferredWidth(90);
    labelItemCode.setText("item");
    labelQty.setText("qtyDOC02");
    labelPriceUnit.setText("valueSal02DOC02");
    labelVat.setText("vatCode");
    labelValueReg01.setText("valueReg01DOC02");
    labelDeductibleReg01.setText("deductibleReg01DOC02");
    labelVatValue.setText("vatValueDOC02");
    labelTotal.setRequestFocusEnabled(true);
    labelTotal.setText("valueDOC02");
    labelTotalDisc.setText("totalDiscountDOC02");
    controlItemCode.setAttributeName("itemCodeItm01DOC02");
    controlItemCode.setCanCopy(true);
    controlItemCode.setEnabledOnEdit(false);
    controlItemCode.setLinkLabel(labelItemCode);
    controlItemCode.setMaxCharacters(20);
    controlItemCode.setRequired(true);
    controlItemDescr.setAttributeName("descriptionSYS10");
    controlItemDescr.setCanCopy(true);
    controlItemDescr.setEnabledOnInsert(false);
    controlItemDescr.setEnabledOnEdit(false);
    controlVatCode.setAttributeName("vatCodeItm01DOC02");
    controlVatCode.setCanCopy(true);
    controlVatCode.setEnabledOnInsert(false);
    controlVatCode.setEnabledOnEdit(false);
    controlVatDescr.setAttributeName("vatDescriptionDOC02");
    controlVatDescr.setCanCopy(true);
    controlVatDescr.setEnabledOnInsert(false);
    controlVatDescr.setEnabledOnEdit(false);
    controlValueReg01.setAttributeName("valueReg01DOC02");
    controlValueReg01.setCanCopy(true);
    controlValueReg01.setDecimals(5);
    controlValueReg01.setEnabledOnInsert(false);
    controlValueReg01.setEnabledOnEdit(false);
    controlDeductibleReg01.setAttributeName("deductibleReg01DOC02");
    controlDeductibleReg01.setCanCopy(true);
    controlDeductibleReg01.setDecimals(5);
    controlDeductibleReg01.setEnabledOnInsert(false);
    controlDeductibleReg01.setEnabledOnEdit(false);
    controlQty.setAttributeName("qtyDOC02");
    controlQty.setCanCopy(true);
    controlQty.setDecimals(5);
    controlQty.setLinkLabel(labelQty);
    controlQty.setMinValue(0.0);
    controlQty.setRequired(true);
    controlQty.addFocusListener(new SaleOrderDocRowsGridPanel_controlQty_focusAdapter(this));
    controlUmCode.setAttributeName("minSellingQtyUmCodeReg02DOC02");
    controlUmCode.setCanCopy(true);
    controlUmCode.setEnabledOnInsert(false);
    controlUmCode.setEnabledOnEdit(false);
    controlPriceUnit.setAttributeName("valueSal02DOC02");
    controlPriceUnit.setCanCopy(true);
    controlPriceUnit.setDecimals(5);
    controlPriceUnit.setEnabledOnEdit(true);
    controlPriceUnit.setLinkLabel(labelPriceUnit);
    controlPriceUnit.setMinValue(0.0);
    controlPriceUnit.setRequired(true);
    controlPriceUnit.addFocusListener(new SaleOrderDocRowsGridPanel_controlPriceUnit_focusAdapter(this));
    controlVatValue.setAttributeName("vatValueDOC02");
    controlVatValue.setCanCopy(true);
    controlVatValue.setDecimals(5);
    controlVatValue.setEnabledOnEdit(false);
    controlVatValue.setEnabledOnInsert(false);
    controlVatValue.setLinkLabel(labelVatValue);
    controlTotalDisc.setAttributeName("discountPercDOC02");
    controlTotalDisc.setCanCopy(true);
    controlTotalDisc.setDecimals(5);
    controlTotalDisc.setAttributeName("totalDiscountDOC02");
    controlTotalDisc.setLinkLabel(labelTotalDisc);
    controlTotalDisc.setMaxValue(100.0);
    controlTotalDisc.setMinValue(0.0);
    controlTotalDisc.setEnabledOnInsert(false);
    controlTotalDisc.setEnabledOnEdit(false);
    controlTotal.setAttributeName("valueDOC02");
    controlTotal.setCanCopy(true);
    controlTotal.setDecimals(5);
    controlTotal.setEnabledOnEdit(false);
    controlTotal.setEnabledOnInsert(false);
    controlTotal.setLinkLabel(labelTotal);


    controlDeliveryDate.setAttributeName("deliveryDateDOC02");
    controlDeliveryDate.setCanCopy(true);
//    controlDeliveryDate.setLowerLimit(new java.sql.Date(System.currentTimeMillis()));
    controlDeliveryDate.setEnabledOnEdit(true);
    controlDeliveryDate.setEnabledOnInsert(true);
    controlDeliveryDate.setLinkLabel(labelDeliveryDate);
    controlDeliveryDate.setRequired(true);

    insertButton1.setEnabled(false);
    copyButton1.setEnabled(false);
    saveButton1.setEnabled(false);
    deleteButton1.setEnabled(false);
    exportButton1.setEnabled(false);
    editButton1.setEnabled(false);
    controlItemType.setAttributeName("progressiveHie02DOC02");
    controlItemType.setCanCopy(true);
    controlItemType.setLinkLabel(labelItemCode);
    controlItemType.setRequired(true);
    controlItemType.setEnabledOnEdit(false);
    labelDeliveryDate.setText("deliveryDateDOC02");

    this.add(buttonsPanel, BorderLayout.NORTH);
    this.add(splitPane,  BorderLayout.CENTER);
    buttonsPanel.add(insertButton1, null);
    buttonsPanel.add(copyButton1, null);
    buttonsPanel.add(editButton1, null);
    buttonsPanel.add(saveButton1, null);
    buttonsPanel.add(reloadButton1, null);
    buttonsPanel.add(exportButton1, null);
    buttonsPanel.add(deleteButton1, null);
    buttonsPanel.add(navigatorBar1, null);
    splitPane.add(grid, JSplitPane.TOP);
    grid.getColumnContainer().add(colRowNum, null);
    grid.getColumnContainer().add(colItemCode, null);
    grid.getColumnContainer().add(colItemDescr, null);
    grid.getColumnContainer().add(colQty, null);
    grid.getColumnContainer().add(colPriceUnit, null);
    grid.getColumnContainer().add(colPrice, null);
    grid.getColumnContainer().add(colVatCode, null);
    grid.getColumnContainer().add(colVatValue, null);
    grid.getColumnContainer().add(colTotalDisc, null);
    grid.getColumnContainer().add(colOutQty, null);
    grid.getColumnContainer().add(colInvoiceQty, null);
    splitPane.add(itemTabbedPane, JSplitPane.BOTTOM);

    southPanel.setLayout(borderLayout2);
    southPanel.add(detailPanel,BorderLayout.NORTH);
    southPanel.add(discountsPanel,BorderLayout.CENTER);

    itemTabbedPane.add(southPanel,"item");
    itemTabbedPane.add(bookedItemsPanel,"booked items and availability");
    itemTabbedPane.add(orderedItemsPanel,"future item availability");
    itemTabbedPane.setTitleAt(0,ClientSettings.getInstance().getResources().getResource("item"));
    itemTabbedPane.setTitleAt(1,ClientSettings.getInstance().getResources().getResource("booked items and availability"));
    itemTabbedPane.setTitleAt(2,ClientSettings.getInstance().getResources().getResource("future item availability"));

    detailPanel.add(labelItemCode,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    detailPanel.add(controlItemCode,                      new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 80, 0));
    detailPanel.add(labelQty,          new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlQty,            new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelVat,         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlVatCode,             new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlVatDescr,          new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 70, 0));
    detailPanel.add(labelValueReg01,         new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlValueReg01,            new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelDeductibleReg01,         new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlDeductibleReg01,          new GridBagConstraints(8, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlItemDescr,       new GridBagConstraints(4, 0, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    detailPanel.add(controlUmCode,         new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 70, 0));
    detailPanel.add(labelPriceUnit,      new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlPriceUnit,       new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelVatValue,      new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlTotalDisc,       new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelTotalDisc,      new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlVatValue,       new GridBagConstraints(8, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelTotal,      new GridBagConstraints(7, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlTotal,       new GridBagConstraints(8, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlItemType,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    detailPanel.add(labelDeliveryDate,      new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlDeliveryDate,        new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

    splitPane.setDividerLocation(180);

  }


  public void setButtonsEnabled(boolean enabled) {
    if (enabled) {
      insertButton1.setEnabled(enabled);
      reloadButton1.setEnabled(enabled);
    }
    else {
      insertButton1.setEnabled(enabled);
      editButton1.setEnabled(enabled);
      saveButton1.setEnabled(enabled);
      deleteButton1.setEnabled(enabled);
      exportButton1.setEnabled(enabled);
      reloadButton1.setEnabled(enabled);
      copyButton1.setEnabled(false);
      discountsPanel.setEnabled(enabled);
    }

    bookedItemsPanel.setEnabled(enabled);
    orderedItemsPanel.setEnabled(enabled);
  }


  public void setParentVO(DetailSaleDocVO parentVO) {
    this.parentVO = parentVO;

    controlTotalDisc.setCurrencySymbol(parentVO.getCurrencySymbolREG03());
    controlTotalDisc.setDecimalSymbol(parentVO.getDecimalSymbolREG03().charAt(0));
    controlTotalDisc.setGroupingSymbol(parentVO.getThousandSymbolREG03().charAt(0));
    controlTotalDisc.setDecimals(parentVO.getDecimalsREG03().intValue());

    controlPriceUnit.setCurrencySymbol(parentVO.getCurrencySymbolREG03());
    controlPriceUnit.setDecimalSymbol(parentVO.getDecimalSymbolREG03().charAt(0));
    controlPriceUnit.setGroupingSymbol(parentVO.getThousandSymbolREG03().charAt(0));
    controlPriceUnit.setDecimals(parentVO.getDecimalsREG03().intValue());

    controlTotal.setCurrencySymbol(parentVO.getCurrencySymbolREG03());
    controlTotal.setDecimalSymbol(parentVO.getDecimalSymbolREG03().charAt(0));
    controlTotal.setGroupingSymbol(parentVO.getThousandSymbolREG03().charAt(0));
    controlTotal.setDecimals(parentVO.getDecimalsREG03().intValue());

    controlVatValue.setCurrencySymbol(parentVO.getCurrencySymbolREG03());
    controlVatValue.setDecimalSymbol(parentVO.getDecimalSymbolREG03().charAt(0));
    controlVatValue.setGroupingSymbol(parentVO.getThousandSymbolREG03().charAt(0));
    controlVatValue.setDecimals(parentVO.getDecimalsREG03().intValue());

    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC01());
    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.PROGRESSIVE_REG04,parentVO.getProgressiveReg04DOC01());
    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.PRICELIST,parentVO.getPricelistCodeSal01DOC01());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC01());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.PROGRESSIVE_REG04,parentVO.getProgressiveReg04DOC01());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.PRICELIST,parentVO.getPricelistCodeSal01DOC01());

    bookedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC01());
    bookedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.WAREHOUSE_CODE,parentVO.getWarehouseCodeWar01DOC01());
    bookedItemsPanel.setEnabled(true);

    orderedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC01());
    orderedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.WAREHOUSE_CODE,parentVO.getWarehouseCodeWar01DOC01());
    bookedItemsPanel.setEnabled(true);

    setButtonsEnabled(true);
    detailPanel.setMode(Consts.READONLY);

  }


  public GridControl getGrid() {
    return grid;
  }


  public Form getDetailPanel() {
    return detailPanel;
  }


  public DetailSaleDocVO getParentVO() {
    return parentVO;
  }


  void controlQty_focusLost(FocusEvent e) {
    updateTotals();
  }


  void controlPriceUnit_focusLost(FocusEvent e) {
    updateTotals();
  }


  /**
   * Method called when qty or price per unit or vat or discount value/percentage has been changed.
   */
  private void updateTotals() {

    DetailSaleDocRowVO vo = (DetailSaleDocRowVO)detailPanel.getVOModel().getValueObject();

    if (vo.getStartDateSal02DOC02()!=null &&
        vo.getEndDateSal02DOC02()!=null &&
        (vo.getStartDateSal02DOC02().getTime()>System.currentTimeMillis() ||
        vo.getEndDateSal02DOC02().getTime()<System.currentTimeMillis())) {
      JOptionPane.showMessageDialog(
          ClientUtils.getParentFrame(this),
          ClientSettings.getInstance().getResources().getResource("the price is no more valid"),
          ClientSettings.getInstance().getResources().getResource("Attention"),
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }


    if (vo.getQtyDOC02()!=null && vo.getValueReg01DOC02()!=null && vo.getValueSal02DOC02()!=null) {
      vo.setTaxableIncomeDOC02(vo.getQtyDOC02().multiply(vo.getValueSal02DOC02()).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));

      // determine total discount...
      Response res = ClientUtils.getData("getSaleItemTotalDiscounts",vo);
      if (res.isError()) {
        JOptionPane.showMessageDialog(
            ClientUtils.getParentFrame(this),
            ClientSettings.getInstance().getResources().getResource("error on applying item discounts"),
            ClientSettings.getInstance().getResources().getResource("Attention"),
            JOptionPane.ERROR_MESSAGE
        );
        return;
      }
      DetailSaleDocRowVO newVO = (DetailSaleDocRowVO)((VOResponse)res).getVo();
      vo.setTotalDiscountDOC02( newVO.getTotalDiscountDOC02().setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP) );

      // apply total discount to taxable income...
      vo.setTaxableIncomeDOC02(vo.getTaxableIncomeDOC02().subtract(vo.getTotalDiscountDOC02()).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));

      // calculate row vat...
      double vatPerc = vo.getValueReg01DOC02().doubleValue()*(1d-vo.getDeductibleReg01DOC02().doubleValue()/100d)/100;
      vo.setVatValueDOC02(vo.getTaxableIncomeDOC02().multiply(new BigDecimal(vatPerc)).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));

      // calculate row total...
      vo.setValueDOC02(vo.getTaxableIncomeDOC02().add(vo.getVatValueDOC02()));
      detailPanel.pull("valueDOC02");
      detailPanel.pull("taxableIncomeDOC02");
      detailPanel.pull("vatValueDOC02");
      detailPanel.pull("totalDiscountDOC02");
      detailPanel.pull("qtyDOC02");
    }
    else {
      vo.setTotalDiscountDOC02(null);
      vo.setTaxableIncomeDOC02(null);
      vo.setVatValueDOC02(null);
      vo.setValueDOC02(null);
    }

  }


  public Form getHeaderPanel() {
    return headerPanel;
  }


  public GridControl getOrders() {
    return frame.getOrders();
  }


  public SaleOrderDocFrame getFrame() {
    return frame;
  }


  public double getMaxValue(int int0) {
    return Double.MAX_VALUE;
  }

  public double getMinValue(int int0) {
    return 0.0;
  }

  public boolean isGrouping(int int0) {
    return true;
  }

  public int getDecimals(int int0) {
    if (parentVO!=null)
      return parentVO.getDecimalsREG03().intValue();
    else
      return 0;
  }

  public String getCurrencySymbol(int int0) {
    if (parentVO!=null)
      return parentVO.getCurrencySymbolREG03();
    else
    return "E";
  }


  public SaleDocRowDiscountsPanel getDiscountsPanel() {
    return discountsPanel;
  }
  public BookedItemsPanel getBookedItemsPanel() {
    return bookedItemsPanel;
  }
  public OrderedItemsPanel getOrderedItemsPanel() {
    return orderedItemsPanel;
  }


  public Form getHeaderFormPanel() {
    return frame.getHeaderFormPanel();
  }


  /**
   * Method NOT supported.
   */
  public boolean isButtonDisabled(GenericButton button) {
    return false;
  }


}


class SaleOrderDocRowsGridPanel_controlQty_focusAdapter extends java.awt.event.FocusAdapter {
  SaleOrderDocRowsGridPanel adaptee;

  SaleOrderDocRowsGridPanel_controlQty_focusAdapter(SaleOrderDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlQty_focusLost(e);
  }
}

class SaleOrderDocRowsGridPanel_controlPriceUnit_focusAdapter extends java.awt.event.FocusAdapter {
  SaleOrderDocRowsGridPanel adaptee;

  SaleOrderDocRowsGridPanel_controlPriceUnit_focusAdapter(SaleOrderDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlPriceUnit_focusLost(e);
  }
}
