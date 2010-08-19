package org.jallinone.purchases.documents.invoices.client;

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
import org.jallinone.purchases.documents.java.*;
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
import java.util.HashSet;
import org.jallinone.purchases.documents.client.*;
import org.jallinone.variants.client.ProductVariantsPanel;
import org.openswing.swing.table.client.GridController;
import org.jallinone.variants.client.ProductVariantsController;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Panel that contains the invoice rows grid + row detail.</p>
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
 * but WITHOUT ANY WARRANTY; within even the implied warranty of
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
public class PurchaseInvoiceDocRowsGridPanel extends JPanel implements CurrencyColumnSettings,PurchaseDocument {

  BorderLayout borderLayin1 = new BorderLayout();
  JPanel buttonsPanel = new JPanel();
  JSplitPane splitPane = new JSplitPane();
  FlowLayout flowLayin1 = new FlowLayout();
  InsertButton insertButton1 = new InsertButton();
  EditButton editButton1 = new EditButton();
  SaveButton saveButton1 = new SaveButton();
  ReloadButton reloadButton1 = new ReloadButton();
  DeleteButton deleteButton1 = new DeleteButton();
  NavigatorBar navigatorBar1 = new NavigatorBar();
  CopyButton copyButton1 = new CopyButton();
  GridControl grid = new GridControl();
  Form detailPanel = new Form();
  GridBagLayout gridBagLayin1 = new GridBagLayout();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  ExportButton exportButton1 = new ExportButton();
  DecimalColumn colRowNum = new DecimalColumn();
  TextColumn colItemCode = new TextColumn();
  TextColumn colItemDescr = new TextColumn();
  DecimalColumn colQty = new DecimalColumn();
  CurrencyColumn colPriceUnit = new CurrencyColumn();
  CurrencyColumn colPrice = new CurrencyColumn();
  TextColumn colVatCode = new TextColumn();
  CurrencyColumn colVatValue = new CurrencyColumn();
  CurrencyColumn colDiscValue = new CurrencyColumn();
  CurrencyColumn colDiscPerc = new CurrencyColumn();
  LabelControl labelItemCode = new LabelControl();
  CodLookupControl controlItemCode = new CodLookupControl();
  TextControl controlItemDescr = new TextControl();
  LabelControl labelInvoiceQty = new LabelControl();
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
  LabelControl labelTotal = new LabelControl();
  CurrencyControl controlTotal = new CurrencyControl();
  CurrencyControl controlVatValue = new CurrencyControl();
  LabelControl labelDiscPerc = new LabelControl();
  LabelControl labelDiscValue = new LabelControl();
  CurrencyControl controlDiscValue = new CurrencyControl();
  NumericControl controlDiscPerc = new NumericControl();


  /** header v.o. */
  private DetailPurchaseDocVO parentVO = null;

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
  private InvoiceDocument frame = null;

  LabelControl labelDeliveryDate = new LabelControl();
  DateControl controlDeliveryDate = new DateControl();

  JTabbedPane itemTabbedPane = new JTabbedPane();
  BookedItemsPanel bookedItemsPanel = new BookedItemsPanel(false,true);
  OrderedItemsPanel orderedItemsPanel = new OrderedItemsPanel(false,true);

  BorderLayout borderLayin2 = new BorderLayout();
  JPanel southPanel = new JPanel();

  /** used to enable insert/edit of item rows and item discounts */
  private boolean enabledInsertEdit;

  /** used to enable delete of item rows and item discounts */
  private boolean enabledDelete;

  private int splitDiv = 300;

  private ProductVariantsPanel variantsPanel = new ProductVariantsPanel(
      new ProductVariantsController() {

        public BigDecimal validateQty(BigDecimal qty) {
          return qty;
        }

        public void qtyUpdated(BigDecimal qty) {
          updateTotals();
        }

      },
      detailPanel,
      controlItemCode,
      itemController,
      "loadProductVariantsMatrix",
      //"loadPurchaseDocVariantsRow",
      controlQty,
      splitPane,
      splitDiv
  );


  public PurchaseInvoiceDocRowsGridPanel(InvoiceDocument frame,Form headerPanel,boolean enabledInsertEdit,boolean enabledDelete) {
    this.frame = frame;
    this.headerPanel = headerPanel;
    this.enabledInsertEdit = enabledInsertEdit;
    this.enabledDelete = enabledDelete;
    try {
      jbInit();

      if (Beans.isDesignTime())
        return;

      init();

      grid.setController(new PurchaseInvoiceDocRowsController(this));
      grid.setGridDataLocator(gridDataLocator);
      gridDataLocator.setServerMethodName("loadPurchaseDocRows");

      detailPanel.setFormController(new PurchaseInvoiceDocRowController(this));
      detailPanel.setMode(Consts.READONLY);

      // item code lookup...
      itemDataLocator.setGridMethodName("loadSupplierPriceItems");
      itemDataLocator.setValidationMethodName("validateSupplierPriceItemCode");

      controlItemCode.setLookupController(itemController);
      itemController.setForm(detailPanel);
      itemController.setLookupDataLocator(itemDataLocator);
      itemController.setFrameTitle("items");

      itemController.setCodeSelectionWindow(itemController.TREE_GRID_FRAME);
      treeLevelDataLocator.setServerMethodName("loadHierarchy");
      itemDataLocator.setTreeDataLocator(treeLevelDataLocator);
      itemDataLocator.setNodeNameAttribute("descriptionSYS10");

      itemController.setLookupValueObjectClassName("org.jallinone.purchases.documents.java.SupplierPriceItemVO");
      itemController.addLookup2ParentLink("itemCodeItm01PUR02", "itemCodeItm01DOC07");
      itemController.addLookup2ParentLink("supplierItemCodePUR02", "supplierItemCodePur02DOC07");
      itemController.addLookup2ParentLink("descriptionSYS10", "descriptionSYS10");
      itemController.addLookup2ParentLink("minPurchaseQtyPUR02","minPurchaseQtyPur02DOC07");
      itemController.addLookup2ParentLink("multipleQtyPUR02","multipleQtyPur02DOC07");
      itemController.addLookup2ParentLink("decimalsREG02", "decimalsReg02DOC07");
      itemController.addLookup2ParentLink("umCodeReg02PUR02", "umCodePur02DOC07");
      itemController.addLookup2ParentLink("vatCodeReg01ITM01", "vatCodeItm01DOC07");
      itemController.addLookup2ParentLink("vatDescriptionSYS10", "vatDescriptionDOC07");
      itemController.addLookup2ParentLink("deductibleREG01", "deductibleReg01DOC07");
      itemController.addLookup2ParentLink("valueREG01", "valueReg01DOC07");
      itemController.addLookup2ParentLink("valuePUR04", "valuePur04DOC07");
      itemController.addLookup2ParentLink("startDatePUR04", "startDatePur04DOC07");
      itemController.addLookup2ParentLink("endDatePUR04", "endDatePur04DOC07");

      itemController.setAllColumnVisible(false);
      itemController.setVisibleColumn("itemCodeItm01PUR02", true);
      itemController.setVisibleColumn("supplierItemCodePUR02", true);
      itemController.setVisibleColumn("descriptionSYS10", true);
      itemController.setVisibleColumn("minPurchaseQtyPUR02", true);
      itemController.setVisibleColumn("umCodeReg02PUR02", true);
      itemController.setVisibleColumn("valuePUR04", true);
      itemController.setVisibleColumn("startDatePUR04", true);
      itemController.setVisibleColumn("endDatePUR04", true);
      itemController.setPreferredWidthColumn("descriptionSYS10", 200);
      itemController.setPreferredWidthColumn("minPurchaseQtyPUR02", 60);
      itemController.setPreferredWidthColumn("umCodeReg02PUR02", 50);
      itemController.setFramePreferedSize(new Dimension(750,500));
      itemController.addLookupListener(new LookupListener() {

        public void codeValidated(boolean validated) {}

        public void codeChanged(ValueObject parentVO,Collection parentChangedAttributes) {
          // fill in the detail form, according to the selected item settings...
          DetailPurchaseDocRowVO vo = (DetailPurchaseDocRowVO)detailPanel.getVOModel().getValueObject();
          if (vo.getItemCodeItm01DOC07()==null || vo.getItemCodeItm01DOC07().equals("")) {
            vo.setDescriptionSYS10(null);
            vo.setUmCodePur02DOC07(null);
            vo.setMinPurchaseQtyPur02DOC07(null);
            vo.setDecimalsReg02DOC07(null);
            vo.setVatCodeItm01DOC07(null);
            vo.setVatDescriptionDOC07(null);
            vo.setDeductibleReg01DOC07(null);
            vo.setValueReg01DOC07(null);
            vo.setValuePur04DOC07(null);
            vo.setInvoiceQtyDOC07(null);
          }
          else {
            vo.setQtyDOC07(vo.getMinPurchaseQtyPur02DOC07());
            controlQty.setMinValue(vo.getMinPurchaseQtyPur02DOC07().doubleValue());
            controlQty.setDecimals(vo.getDecimalsReg02DOC07().intValue());

            SupplierPriceItemVO lookupVO = (SupplierPriceItemVO)itemController.getLookupVO();
            bookedItemsPanel.getControlItemType().setValue(lookupVO.getProgressiveHie02PUR02());
            bookedItemsPanel.getControlItemCode().setValue(vo.getItemCodeItm01DOC07());
            bookedItemsPanel.getControlItemCode().getLookupController().forceValidate();
            bookedItemsPanel.getGrid().reloadData();
            orderedItemsPanel.getControlItemType().setValue(lookupVO.getProgressiveHie02PUR02());
            orderedItemsPanel.getControlItemCode().setValue(vo.getItemCodeItm01DOC07());
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
      colDiscValue.setDynamicSettings(this);
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
      java.util.List list = ((VOListResponse)res).getRows();
      for(int i=0;i<list.size();i++) {
        vo = (ItemTypeVO)list.get(i);
        d.addDomainPair(vo.getProgressiveHie02ITM02(),vo.getDescriptionSYS10());
      }
    }
    controlItemType.setDomain(d);
    controlItemType.getComboBox().addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==e.SELECTED && detailPanel.getMode()!=Consts.READONLY) {
          DetailPurchaseDocRowVO vo = (DetailPurchaseDocRowVO)detailPanel.getVOModel().getValueObject();
          vo.setItemCodeItm01DOC07(null);
          vo.setDescriptionSYS10(null);
          vo.setUmCodePur02DOC07(null);
          vo.setVatCodeItm01DOC07(null);
          vo.setVatDescriptionDOC07(null);
          vo.setDeductibleReg01DOC07(null);
          vo.setValueReg01DOC07(null);
          vo.setValuePur04DOC07(null);
          vo.setQtyDOC07(null);

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
    this.setLayout(borderLayin1);
    buttonsPanel.setLayout(flowLayin1);
    flowLayin1.setAlignment(FlowLayout.LEFT);
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    grid.setAutoLoadData(false);
    if (enabledInsertEdit) {
      grid.setCopyButton(copyButton1);
    }
    grid.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    if (enabledDelete)
      grid.setDeleteButton(deleteButton1);
    grid.setExportButton(exportButton1);
    grid.setFunctionId(frame.getFunctionId());
    grid.setMaxSortedColumns(3);
    grid.setNavBar(navigatorBar1);
    grid.setReloadButton(reloadButton1);
    grid.setValueObjectClassName("org.jallinone.purchases.documents.java.GridPurchaseDocRowVO");
    detailPanel.setLayout(gridBagLayin1);
    detailPanel.setBorder(titledBorder1);
    //detailPanel.setMinimumSize(new Dimension(740, 140));
    if (enabledInsertEdit) {
      detailPanel.setInsertButton(insertButton1);
      detailPanel.setEditButton(editButton1);
      detailPanel.setCopyButton(copyButton1);
    }

    detailPanel.setReloadButton(reloadButton1);
    detailPanel.setSaveButton(saveButton1);
    detailPanel.setVOClassName("org.jallinone.purchases.documents.java.DetailPurchaseDocRowVO");
    detailPanel.setFunctionId(frame.getFunctionId());
    titledBorder1.setTitle(ClientSettings.getInstance().getResources().getResource("line detail"));
    titledBorder1.setTitleColor(Color.blue);
    colRowNum.setColumnFilterable(false);
    colRowNum.setColumnName("rowNumberDOC07");
    colRowNum.setColumnVisible(false);
    colRowNum.setSortVersus(org.openswing.swing.util.java.Consts.ASC_SORTED);
    colRowNum.setColumnSelectable(false);

    colItemCode.setColumnFilterable(false);
    colItemCode.setColumnName("itemCodeItm01DOC07");
    colItemCode.setColumnSortable(false);
    colItemCode.setPreferredWidth(80);
    colItemDescr.setColumnName("descriptionSYS10");
    colItemDescr.setColumnSortable(false);
    colItemDescr.setHeaderColumnName("descriptionSYS10");
    colItemDescr.setPreferredWidth(200);
    colQty.setDecimals(5);
    colQty.setGrouping(false);
    colQty.setColumnName("qtyDOC07");
    colQty.setHeaderColumnName("invoiceQtyDOC07");
    colQty.setColumnSortable(false);
    colQty.setPreferredWidth(70);

    colPriceUnit.setColumnName("valuePur04DOC07");
    colPriceUnit.setDecimals(5);
    colPriceUnit.setPreferredWidth(90);
    colPrice.setColumnName("valueDOC07");
    colPrice.setColumnSortable(false);
    colPrice.setDecimals(5);
    colPrice.setPreferredWidth(90);
    colVatCode.setColumnName("vatCodeItm01DOC07");
    colVatCode.setColumnSortable(false);
    colVatCode.setHeaderColumnName("vatCode");
    colVatCode.setPreferredWidth(70);
    colVatValue.setColumnName("vatValueDOC07");
    colVatValue.setColumnSortable(false);
    colVatValue.setDecimals(5);
    colVatValue.setPreferredWidth(90);
    colDiscValue.setColumnName("discountValueDOC07");
    colDiscValue.setPreferredWidth(90);
    colDiscPerc.setColumnName("discountPercDOC07");
    colDiscPerc.setColumnSortable(false);
    colDiscPerc.setDecimals(5);
    colDiscPerc.setPreferredWidth(60);
    labelItemCode.setText("item");
    labelInvoiceQty.setText("invoiceQtyDOC07");
    labelPriceUnit.setText("valuePur04DOC07");
    labelVat.setText("vatCode");
    labelValueReg01.setText("valueReg01DOC07");
    labelDeductibleReg01.setText("deductibleReg01DOC07");
    labelVatValue.setText("vatValueDOC07");
    labelTotal.setRequestFocusEnabled(true);
    labelTotal.setText("valueDOC07");
    controlItemCode.setAttributeName("itemCodeItm01DOC07");
    controlItemCode.setCanCopy(true);
    controlItemCode.setEnabledOnEdit(false);
    controlItemCode.setLinkLabel(labelItemCode);
    controlItemCode.setMaxCharacters(20);
    controlItemCode.setRequired(true);
    controlItemDescr.setAttributeName("descriptionSYS10");
    controlItemDescr.setCanCopy(true);
    controlItemDescr.setEnabledOnInsert(false);
    controlItemDescr.setEnabledOnEdit(false);
    controlVatCode.setAttributeName("vatCodeItm01DOC07");
    controlVatCode.setCanCopy(true);
    controlVatCode.setEnabledOnInsert(false);
    controlVatCode.setEnabledOnEdit(false);
    controlVatDescr.setAttributeName("vatDescriptionDOC07");
    controlVatDescr.setCanCopy(true);
    controlVatDescr.setEnabledOnInsert(false);
    controlVatDescr.setEnabledOnEdit(false);
    controlValueReg01.setAttributeName("valueReg01DOC07");
    controlValueReg01.setCanCopy(true);
    controlValueReg01.setDecimals(5);
    controlValueReg01.setEnabledOnInsert(false);
    controlValueReg01.setEnabledOnEdit(false);
    controlDeductibleReg01.setAttributeName("deductibleReg01DOC07");
    controlDeductibleReg01.setCanCopy(true);
    controlDeductibleReg01.setDecimals(5);
    controlDeductibleReg01.setEnabledOnInsert(false);
    controlDeductibleReg01.setEnabledOnEdit(false);
    controlQty.setAttributeName("qtyDOC07");
    controlQty.setCanCopy(true);
    controlQty.setDecimals(5);
    controlQty.setLinkLabel(labelInvoiceQty);
    controlQty.setMinValue(0.0);
    controlQty.setRequired(true);
    controlQty.addFocusListener(new PurchaseInvoiceDocRowsGridPanel_controlQty_focusAdapter(this));
    controlUmCode.setAttributeName("umCodePur02DOC07");
    controlUmCode.setCanCopy(true);
    controlUmCode.setEnabledOnInsert(false);
    controlUmCode.setEnabledOnEdit(false);
    controlPriceUnit.setAttributeName("valuePur04DOC07");
    controlPriceUnit.setCanCopy(true);
    controlPriceUnit.setDecimals(5);
    controlPriceUnit.setEnabledOnEdit(true);
    controlPriceUnit.setLinkLabel(labelPriceUnit);
    controlPriceUnit.setMinValue(0.0);
    controlPriceUnit.setRequired(true);
    controlPriceUnit.addFocusListener(new PurchaseInvoiceDocRowsGridPanel_controlPriceUnit_focusAdapter(this));
    controlVatValue.setAttributeName("vatValueDOC07");
    controlVatValue.setCanCopy(true);
    controlVatValue.setDecimals(5);
    controlVatValue.setEnabledOnEdit(false);
    controlVatValue.setEnabledOnInsert(false);
    controlVatValue.setLinkLabel(labelVatValue);

    labelDiscPerc.setText("discountPercDOC07");
    controlDiscValue.setAttributeName("discountValueDOC07");
    controlDiscValue.setCanCopy(true);
    controlDiscValue.setDecimals(5);
    controlDiscValue.setLinkLabel(labelDiscValue);
    controlDiscValue.setMinValue(0.0);
    controlDiscValue.addFocusListener(new PurchaseInvoiceDocRowsGridPanel_controlDiscValue_focusAdapter(this));
    controlDiscPerc.setAttributeName("discountPercDOC07");
    controlDiscPerc.setCanCopy(true);
    controlDiscPerc.setDecimals(5);
    controlDiscPerc.setLinkLabel(labelDiscPerc);
    controlDiscPerc.setMaxValue(100.0);
    controlDiscPerc.setMinValue(0.0);
    controlDiscPerc.addFocusListener(new PurchaseInvoiceDocRowsGridPanel_controlDiscPerc_focusAdapter(this));

    labelDiscValue.setText("discountValueDOC07");
    labelDiscPerc.setText("discountPercDOC07");

    controlTotal.setAttributeName("valueDOC07");
    controlTotal.setCanCopy(true);
    controlTotal.setDecimals(5);
    controlTotal.setEnabledOnEdit(false);
    controlTotal.setEnabledOnInsert(false);
    controlTotal.setLinkLabel(labelTotal);


    controlDeliveryDate.setAttributeName("deliveryDateDOC07");
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
    controlItemType.setAttributeName("progressiveHie02DOC07");
    controlItemType.setCanCopy(true);
    controlItemType.setLinkLabel(labelItemCode);
    controlItemType.setRequired(true);
    controlItemType.setEnabledOnEdit(false);
    labelDeliveryDate.setText("deliveryDateDOC07");

    this.add(buttonsPanel, BorderLayout.NORTH);
    this.add(splitPane,  BorderLayout.CENTER);
    if (enabledInsertEdit) {
      buttonsPanel.add(insertButton1, null);
      buttonsPanel.add(copyButton1, null);
      buttonsPanel.add(editButton1, null);
      buttonsPanel.add(saveButton1, null);
    }
    buttonsPanel.add(reloadButton1, null);
    buttonsPanel.add(exportButton1, null);

    if (enabledDelete)
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
    grid.getColumnContainer().add(colDiscValue, null);
    grid.getColumnContainer().add(colDiscPerc, null);
    splitPane.add(itemTabbedPane, JSplitPane.BOTTOM);

    southPanel.setLayout(borderLayin2);
    southPanel.add(detailPanel,BorderLayout.CENTER);
    itemTabbedPane.add(southPanel,"item");
    itemTabbedPane.add(bookedItemsPanel,"booked items and availability");
    itemTabbedPane.add(orderedItemsPanel,"future item availability");
    itemTabbedPane.setTitleAt(0,ClientSettings.getInstance().getResources().getResource("item"));
    itemTabbedPane.setTitleAt(1,ClientSettings.getInstance().getResources().getResource("booked items and availability"));
    itemTabbedPane.setTitleAt(2,ClientSettings.getInstance().getResources().getResource("future item availability"));

    detailPanel.add(labelItemCode,          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    detailPanel.add(controlItemCode,                        new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 80, 0));
    detailPanel.add(labelInvoiceQty,           new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlQty,             new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelVat,          new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlVatCode,              new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlVatDescr,           new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 70, 0));
    detailPanel.add(labelValueReg01,          new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlValueReg01,             new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelDeductibleReg01,          new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlDeductibleReg01,           new GridBagConstraints(8, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlItemDescr,         new GridBagConstraints(4, 0, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    detailPanel.add(controlUmCode,          new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 70, 0));
    detailPanel.add(labelPriceUnit,       new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlPriceUnit,        new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelVatValue,       new GridBagConstraints(7, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlDiscValue,        new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelDiscValue,       new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));

    if (!enabledInsertEdit) {
      controlDiscPerc.setEnabledOnEdit(false);
      controlDiscValue.setEnabledOnEdit(false);
    }

    detailPanel.add(controlDiscPerc,        new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelDiscPerc,       new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));

    detailPanel.add(controlVatValue,        new GridBagConstraints(8, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(labelTotal,       new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlTotal,        new GridBagConstraints(8, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 80, 0));
    detailPanel.add(controlItemType,       new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    detailPanel.add(labelDeliveryDate,        new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(controlDeliveryDate,          new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    detailPanel.add(variantsPanel,      new GridBagConstraints(0, 1, 9, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    splitPane.setDividerLocation(splitDiv);

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
    }

    bookedItemsPanel.setEnabled(enabled);
    orderedItemsPanel.setEnabled(enabled);
  }


  public void setParentVO(DetailPurchaseDocVO parentVO) {
    this.parentVO = parentVO;

    controlDiscValue.setCurrencySymbol(parentVO.getCurrencySymbolREG03());
    controlDiscValue.setDecimalSymbol(parentVO.getDecimalSymbolREG03().charAt(0));
    controlDiscValue.setGroupingSymbol(parentVO.getThousandSymbolREG03().charAt(0));
    controlDiscValue.setDecimals(parentVO.getDecimalsREG03().intValue());

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

    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC06());
    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.PROGRESSIVE_REG04,parentVO.getProgressiveReg04DOC06());
    itemDataLocator.getLookupFrameParams().put(ApplicationConsts.PRICELIST,parentVO.getPricelistCodePur03DOC06());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC06());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.PROGRESSIVE_REG04,parentVO.getProgressiveReg04DOC06());
    itemDataLocator.getLookupValidationParameters().put(ApplicationConsts.PRICELIST,parentVO.getPricelistCodePur03DOC06());

    bookedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC06());
    bookedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.WAREHOUSE_CODE,parentVO.getWarehouseCodeWar01DOC06());
    bookedItemsPanel.setEnabled(true);

    orderedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.COMPANY_CODE_SYS01,parentVO.getCompanyCodeSys01DOC06());
    orderedItemsPanel.getGrid().getOtherGridParams().put(ApplicationConsts.WAREHOUSE_CODE,parentVO.getWarehouseCodeWar01DOC06());
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


  public DetailPurchaseDocVO getParentVO() {
    return parentVO;
  }


  void controlQty_focusLost(FocusEvent e) {
    updateTotals();
  }


  void controlPriceUnit_focusLost(FocusEvent e) {
    updateTotals();
  }


  void controlDiscValue_focusLost(FocusEvent e) {
    if (controlDiscValue.getValue()!=null && controlDiscPerc.getValue()!=null)
      controlDiscPerc.setValue(null);
    updateTotals();
  }


  void controlDiscPerc_focusLost(FocusEvent e) {
    if (controlDiscValue.getValue()!=null && controlDiscPerc.getValue()!=null)
      controlDiscValue.setValue(null);
    updateTotals();
  }



  /**
   * Method called when qty or price per unit or vat or discount value/percentage has been changed.
   */
  private void updateTotals() {
    DetailPurchaseDocRowVO vo = (DetailPurchaseDocRowVO)detailPanel.getVOModel().getValueObject();

    if (vo.getStartDatePur04DOC07()!=null &&
        vo.getEndDatePur04DOC07()!=null &&
        (vo.getStartDatePur04DOC07().getTime()>System.currentTimeMillis() ||
        vo.getEndDatePur04DOC07().getTime()<System.currentTimeMillis())) {
      JOptionPane.showMessageDialog(
          ClientUtils.getParentFrame(this),
          ClientSettings.getInstance().getResources().getResource("the price is no more valid"),
          ClientSettings.getInstance().getResources().getResource("Attention"),
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    if (PurchaseUtils.updateTotals(vo,parentVO.getDecimalsREG03().intValue())) {
      detailPanel.pull("valueDOC07");
      detailPanel.pull("taxableIncomeDOC07");
      detailPanel.pull("vatValueDOC07");
      detailPanel.pull("discountValueDOC07");
      detailPanel.pull("discountPercDOC07");
      detailPanel.pull("qtyDOC07");
    }

/*
    DetailPurchaseDocRowVO vo = (DetailPurchaseDocRowVO)detailPanel.getVOModel().getValueObject();

    if (vo.getStartDatePur04DOC07()!=null &&
        vo.getEndDatePur04DOC07()!=null &&
        (vo.getStartDatePur04DOC07().getTime()>System.currentTimeMillis() ||
        vo.getEndDatePur04DOC07().getTime()<System.currentTimeMillis())) {
      JOptionPane.showMessageDialog(
          ClientUtils.getParentFrame(this),
          ClientSettings.getInstance().getResources().getResource("the price is no more valid"),
          ClientSettings.getInstance().getResources().getResource("Attention"),
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    if (vo.getQtyDOC07()!=null && vo.getValueReg01DOC07()!=null && vo.getValuePur04DOC07()!=null) {
      vo.setTaxableIncomeDOC07(vo.getQtyDOC07().multiply(vo.getValuePur04DOC07()).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));

      // apply percentage discount...
      if (vo.getDiscountPercDOC07()!=null) {
        double taxtable = vo.getTaxableIncomeDOC07().doubleValue()-vo.getTaxableIncomeDOC07().doubleValue()*vo.getDiscountPercDOC07().doubleValue()/100d;
        vo.setTaxableIncomeDOC07(new BigDecimal(taxtable).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));
      }

      // apply value discount...
      if (vo.getDiscountValueDOC07()!=null) {
        vo.setTaxableIncomeDOC07(vo.getTaxableIncomeDOC07().subtract(vo.getDiscountValueDOC07()).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));
      }

      // calculate row vat...
      double vatPerc = vo.getValueReg01DOC07().doubleValue()*(1d-vo.getDeductibleReg01DOC07().doubleValue()/100d)/100;
      vo.setVatValueDOC07(vo.getTaxableIncomeDOC07().multiply(new BigDecimal(vatPerc)).setScale(parentVO.getDecimalsREG03().intValue(),BigDecimal.ROUND_HALF_UP));

      // calculate row total...
      vo.setValueDOC07(vo.getTaxableIncomeDOC07().add(vo.getVatValueDOC07()));

      detailPanel.pull("valueDOC07");
      detailPanel.pull("taxableIncomeDOC07");
      detailPanel.pull("vatValueDOC07");
      detailPanel.pull("discountValueDOC07");
      detailPanel.pull("discountPercDOC07");
      detailPanel.pull("qtyDOC07");

    }
    else {
      vo.setTaxableIncomeDOC07(null);
      vo.setVatValueDOC07(null);
      vo.setValueDOC07(null);
    }
*/
  }


  public Form getHeaderPanel() {
    return headerPanel;
  }


  public GridControl getInvoices() {
    return frame.getInvoices();
  }


  public PurchaseDocument getFrame() {
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

  public ProductVariantsPanel getVariantsPanel() {
    return variantsPanel;
  }



}


class PurchaseInvoiceDocRowsGridPanel_controlQty_focusAdapter extends java.awt.event.FocusAdapter {
  PurchaseInvoiceDocRowsGridPanel adaptee;

  PurchaseInvoiceDocRowsGridPanel_controlQty_focusAdapter(PurchaseInvoiceDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlQty_focusLost(e);
  }
}

class PurchaseInvoiceDocRowsGridPanel_controlPriceUnit_focusAdapter extends java.awt.event.FocusAdapter {
  PurchaseInvoiceDocRowsGridPanel adaptee;

  PurchaseInvoiceDocRowsGridPanel_controlPriceUnit_focusAdapter(PurchaseInvoiceDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlPriceUnit_focusLost(e);
  }
}

class PurchaseInvoiceDocRowsGridPanel_controlDiscValue_focusAdapter extends java.awt.event.FocusAdapter {
  PurchaseInvoiceDocRowsGridPanel adaptee;

  PurchaseInvoiceDocRowsGridPanel_controlDiscValue_focusAdapter(PurchaseInvoiceDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlDiscValue_focusLost(e);
  }
}

class PurchaseInvoiceDocRowsGridPanel_controlDiscPerc_focusAdapter extends java.awt.event.FocusAdapter {
  PurchaseInvoiceDocRowsGridPanel adaptee;

  PurchaseInvoiceDocRowsGridPanel_controlDiscPerc_focusAdapter(PurchaseInvoiceDocRowsGridPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.controlDiscPerc_focusLost(e);
  }
}
