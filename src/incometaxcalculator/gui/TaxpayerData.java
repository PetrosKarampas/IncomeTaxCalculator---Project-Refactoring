package incometaxcalculator.gui;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class TaxpayerData extends JFrame {

  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;

  public TaxpayerData(int taxRegistrationNumber, TaxpayerManager taxpayerManager) {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(200, 100, 450, 420);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    DefaultListModel<Integer> receiptsModel = new DefaultListModel<>();

    JList<Integer> receiptsList = new JList<>(receiptsModel);
    receiptsList.setBackground(new Color(153, 204, 204));
    receiptsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    receiptsList.setSelectedIndex(0);
    receiptsList.setVisibleRowCount(3);

    JScrollPane receiptsListScrollPane = new JScrollPane(receiptsList);
    receiptsListScrollPane.setSize(150, 200);
    receiptsListScrollPane.setLocation(100, 170);
    contentPane.add(receiptsListScrollPane);

    HashMap<Integer, Receipt> receipts = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);

    for (HashMap.Entry<Integer, Receipt> entry : receipts.entrySet()) {
      Receipt receipt = entry.getValue();
      receiptsModel.addElement(receipt.getReceiptId());
    }

    JButton btnAddReceipt = new JButton("Add Receipt");
    btnAddReceipt.addActionListener(e -> {
      JPanel receiptImporterPanel = new JPanel(new GridLayout(9, 2));
      JTextField receiptID = new JTextField(16);
      JTextField date = new JTextField(16);
      JTextField kind = new JTextField(16);
      JTextField amount = new JTextField(16);
      JTextField company = new JTextField(16);
      JTextField country = new JTextField(16);
      JTextField city = new JTextField(16);
      JTextField street = new JTextField(16);
      JTextField number = new JTextField(16);
      int receiptIDValue, numberValue;
      float amountValue;
      String dateValue, kindValue, companyValue, countryValue;
      String cityValue, streetValue;
      receiptImporterPanel.add(new JLabel("Receipt ID:"));
      receiptImporterPanel.add(receiptID);
      receiptImporterPanel.add(new JLabel("Date:"));
      receiptImporterPanel.add(date);
      receiptImporterPanel.add(new JLabel("Kind:"));
      receiptImporterPanel.add(kind);
      receiptImporterPanel.add(new JLabel("Amount:"));
      receiptImporterPanel.add(amount);
      receiptImporterPanel.add(new JLabel("Company:"));
      receiptImporterPanel.add(company);
      receiptImporterPanel.add(new JLabel("Country:"));
      receiptImporterPanel.add(country);
      receiptImporterPanel.add(new JLabel("City:"));
      receiptImporterPanel.add(city);
      receiptImporterPanel.add(new JLabel("Street:"));
      receiptImporterPanel.add(street);
      receiptImporterPanel.add(new JLabel("Number:"));
      receiptImporterPanel.add(number);
      int op = JOptionPane.showConfirmDialog(null, receiptImporterPanel, "",
          JOptionPane.OK_CANCEL_OPTION);
      if (op == 0) {
        receiptIDValue = Integer.parseInt(receiptID.getText());
        dateValue = date.getText();
        kindValue = kind.getText();
        amountValue = Float.parseFloat(amount.getText());
        companyValue = company.getText();
        countryValue = country.getText();
        cityValue = city.getText();
        streetValue = street.getText();
        numberValue = Integer.parseInt(number.getText());
        try {
          taxpayerManager.addReceipt(receiptIDValue, dateValue, amountValue, kindValue,
              companyValue, countryValue, cityValue, streetValue, numberValue,
              taxRegistrationNumber);
          receiptsModel.addElement(receiptIDValue);
        } catch (IOException | WrongReceiptDateException | ReceiptAlreadyExistsException | WrongFileFormatException e1)
        {
          JOptionPane.showMessageDialog(null, e1.getStackTrace());
        }
      }
    });
    btnAddReceipt.setBounds(0, 0, 102, 23);
    contentPane.add(btnAddReceipt);

    JButton btnDeleteReceipt = new JButton("Delete Receipt");
    btnDeleteReceipt.addActionListener(e -> {
      JPanel receiptRemoverPanel = new JPanel(new GridLayout(2, 2));
      JTextField receiptID = new JTextField(16);
      receiptRemoverPanel.add(new JLabel("Receipt ID:"));
      receiptRemoverPanel.add(receiptID);
      int op = JOptionPane.showConfirmDialog(null, receiptRemoverPanel, "", JOptionPane.OK_CANCEL_OPTION);
      if (op == 0) {
        int receiptIDValue = Integer.parseInt(receiptID.getText());
        try {
          taxpayerManager.removeReceipt(receiptIDValue);
          receiptsModel.removeElement(receiptIDValue);
        } catch (IOException | ReceiptIdDoesNotExistException | WrongFileFormatException e1) {
          JOptionPane.showMessageDialog(null, e1.getStackTrace());
        }
      }
    });
    btnDeleteReceipt.setBounds(100, 0, 120, 23);
    contentPane.add(btnDeleteReceipt);

    JButton btnViewReport = new JButton("View Report");
    btnViewReport.addActionListener(e -> {
      ChartDisplay.createBarChart(taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber),
          taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber),
          taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber));
      ChartDisplay.createPieChart(
          taxpayerManager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, ENTERTAINMENT),
          taxpayerManager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, BASIC),
          taxpayerManager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, TRAVEL),
          taxpayerManager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, HEALTH),
          taxpayerManager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, OTHER));
    });
    btnViewReport.setBounds(214, 0, 109, 23);
    contentPane.add(btnViewReport);

    JButton btnSaveData = new JButton("Save Data");
    btnSaveData.addActionListener(e -> {
      JPanel fileLoaderPanel = new JPanel(new BorderLayout());
      JPanel boxPanel = new JPanel(new BorderLayout());
      JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
      JLabel TRN = new JLabel("Choose file format.");
      // JTextField taxRegistrationNumberField = new JTextField(20);
      taxRegistrationNumberPanel.add(TRN);
      // taxRegistrationNumberPanel.add(taxRegistrationNumberField);
      JPanel loadPanel = new JPanel(new GridLayout(1, 2));
      loadPanel.add(taxRegistrationNumberPanel);
      fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
      fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
      JCheckBox txtBox = new JCheckBox("Txt file");
      JCheckBox xmlBox = new JCheckBox("Xml file");

      txtBox.addActionListener(e12 -> xmlBox.setSelected(false));

      xmlBox.addActionListener(e13 -> txtBox.setSelected(false));
      txtBox.doClick();
      boxPanel.add(txtBox, BorderLayout.WEST);
      boxPanel.add(xmlBox, BorderLayout.EAST);

      int answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "",
          JOptionPane.OK_CANCEL_OPTION);
      if (answer == 0) {
        try {
          if (txtBox.isSelected()) {
            try {
              taxpayerManager.saveLogFile(taxRegistrationNumber, "_LOG.txt");
            } catch (IOException | WrongFileFormatException e1) {
              JOptionPane.showMessageDialog(null, e1.getStackTrace());
            }
          } else {
            try {
              taxpayerManager.saveLogFile(taxRegistrationNumber, "_LOG.xml");
            } catch (IOException | WrongFileFormatException e1) {
              JOptionPane.showMessageDialog(null, e1.getStackTrace());
            }
          }
        }
        catch (NumberFormatException e1) {
          JOptionPane.showMessageDialog(null, e1.getStackTrace());
        }
      }
    });
    btnSaveData.setBounds(322, 0, 102, 23);
    contentPane.add(btnSaveData);

    JTextPane txtpnName = new JTextPane();
    txtpnName.setEditable(false);
    txtpnName.setText("Name :");
    txtpnName.setBounds(10, 34, 92, 20);
    contentPane.add(txtpnName);

    JTextPane txtpnTrn = new JTextPane();
    txtpnTrn.setEditable(false);
    txtpnTrn.setText("TRN :");
    txtpnTrn.setBounds(10, 65, 92, 20);
    contentPane.add(txtpnTrn);

    JTextPane txtpnStatus = new JTextPane();
    txtpnStatus.setEditable(false);
    txtpnStatus.setText("Status :");
    txtpnStatus.setBounds(10, 96, 92, 20);
    contentPane.add(txtpnStatus);

    JTextPane txtpnIncome = new JTextPane();
    txtpnIncome.setEditable(false);
    txtpnIncome.setText("Income :");
    txtpnIncome.setBounds(10, 127, 92, 20);
    contentPane.add(txtpnIncome);

    JTextArea taxpayerName = new JTextArea();
    taxpayerName.setFont(new Font("Tahoma", Font.PLAIN, 11));
    taxpayerName.setEditable(false);
    taxpayerName.setBounds(110, 34, 213, 20);
    taxpayerName.setText(taxpayerManager.getTaxpayerFullName(taxRegistrationNumber));
    contentPane.add(taxpayerName);

    JTextArea taxpayerTRN = new JTextArea();
    taxpayerTRN.setFont(new Font("Tahoma", Font.PLAIN, 11));
    taxpayerTRN.setEditable(false);
    taxpayerTRN.setBounds(110, 65, 213, 20);
    taxpayerTRN.setText(taxRegistrationNumber + "");
    contentPane.add(taxpayerTRN);

    JTextArea taxpayerStatus = new JTextArea();
    taxpayerStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
    taxpayerStatus.setEditable(false);
    taxpayerStatus.setBounds(110, 96, 213, 20);
    taxpayerStatus.setText(taxpayerManager.getTaxpayerStatus(taxRegistrationNumber));
    contentPane.add(taxpayerStatus);

    JTextArea taxpayerIncome = new JTextArea();
    taxpayerIncome.setFont(new Font("Tahoma", Font.PLAIN, 11));
    taxpayerIncome.setEditable(false);
    taxpayerIncome.setBounds(112, 127, 213, 20);
    taxpayerIncome.setText(taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
    contentPane.add(taxpayerIncome);

    JTextPane txtpnReceipts = new JTextPane();
    txtpnReceipts.setEditable(false);
    txtpnReceipts.setText("Receipts :");
    txtpnReceipts.setBounds(10, 170, 80, 20);
    contentPane.add(txtpnReceipts);
  }
}