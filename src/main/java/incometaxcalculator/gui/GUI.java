package incometaxcalculator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;

public class GUI extends JFrame {

  private final TaxpayerManager taxpayerManager = new TaxpayerManager();

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        GUI frame = new GUI();
        frame.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public GUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 500);
    JPanel contentPane = new JPanel();
    contentPane.setBackground(new Color(204, 204, 204));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e2) {
      e2.printStackTrace();
    }

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setBackground(new Color(153, 204, 204));
    textPane.setBounds(0, 21, 433, 441);

    JPanel fileLoaderPanel = new JPanel(new BorderLayout());
    JPanel boxPanel = new JPanel(new BorderLayout());
    JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    JLabel TRN = new JLabel("Give the tax registration number:");
    JTextField taxRegistrationNumberField = new JTextField(20);
    taxRegistrationNumberPanel.add(TRN);
    taxRegistrationNumberPanel.add(taxRegistrationNumberField);
    JPanel loadPanel = new JPanel(new GridLayout(1, 2));
    loadPanel.add(taxRegistrationNumberPanel);
    fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
    fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
    JCheckBox txtBox = new JCheckBox("Txt file");
    JCheckBox xmlBox = new JCheckBox("Xml file");

    txtBox.addActionListener(e -> xmlBox.setSelected(false));

    xmlBox.addActionListener(e -> txtBox.setSelected(false));
    txtBox.doClick();
    boxPanel.add(txtBox, BorderLayout.WEST);
    boxPanel.add(xmlBox, BorderLayout.EAST);

    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<>();

    JList<String> taxRegisterNumberList = new JList<>(taxRegisterNumberModel);
    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    taxRegisterNumberList.setSelectedIndex(0);
    taxRegisterNumberList.setVisibleRowCount(3);

    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
    taxRegisterNumberListScrollPane.setSize(300, 300);
    taxRegisterNumberListScrollPane.setLocation(70, 100);
    contentPane.add(taxRegisterNumberListScrollPane);

    JButton btnLoadTaxpayer = new JButton("Load Taxpayer");
    btnLoadTaxpayer.addActionListener(e -> {
      int answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "", JOptionPane.OK_CANCEL_OPTION);
      if (answer == 0) {
        String taxRegistrationNumber = taxRegistrationNumberField.getText();
        //TODO remember to change 1 to 9
        while (taxRegistrationNumber.length() != 9 && answer == 0) {
          JOptionPane.showMessageDialog(null, "The tax  registration number must have 9 digit.\n" + " Try again.");
          answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "", JOptionPane.OK_CANCEL_OPTION);
          taxRegistrationNumber = taxRegistrationNumberField.getText();
        }
        if (answer == 0) {
          int trn;
          String taxRegistrationNumberFile;
          try {
            trn = Integer.parseInt(taxRegistrationNumber);
            if (txtBox.isSelected()) {
              taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.txt";
            } else {
              taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.xml";
            }
            if (taxpayerManager.containsTaxpayer(trn)) {
              JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
            } else {
              taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
              taxRegisterNumberModel.addElement(taxRegistrationNumber);
            }
            // textPane.setText(taxpayersTRN);
          } catch (NumberFormatException | IOException | WrongFileFormatException | WrongTaxpayerStatusException |
                   WrongReceiptKindException | WrongReceiptDateException e1) {
            JOptionPane.showMessageDialog(null, e1.getStackTrace());
          }
        }
      }
    });
    btnLoadTaxpayer.setBounds(0, 0, 146, 23);
    contentPane.add(btnLoadTaxpayer);

    JButton btnSelectTaxpayer = new JButton("Select Taxpayer");
    btnSelectTaxpayer.addActionListener(e -> {
      if (!taxpayerManager.isTaxpayerHashMapEmpty()) {
        String trn = JOptionPane.showInputDialog(null, "Give the tax registration number " + "that you want to be displayed : ");
        if (trn != null) {
          int taxRegistrationNumber;
          try {
            taxRegistrationNumber = Integer.parseInt(trn);
            if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
              TaxpayerData taxpayerData = new TaxpayerData(taxRegistrationNumber, taxpayerManager);
              taxpayerData.setVisible(true);
            } else {
              JOptionPane.showMessageDialog(null, "This tax registration number isn't loaded.");
            }
          } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, e1.getStackTrace());
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "There isn't any taxpayer loaded. Please load one first.");
      }
    });
    btnSelectTaxpayer.setBounds(147, 0, 139, 23);
    contentPane.add(btnSelectTaxpayer);

    JButton btnDeleteTaxpayer = new JButton("Delete Taxpayer");
    btnDeleteTaxpayer.addActionListener(arg0 -> {
      if (!taxpayerManager.isTaxpayerHashMapEmpty()) {
        String trn = JOptionPane.showInputDialog(null, "Give the tax registration number that you want to delete: ");
        int taxRegistrationNumber;
        try {
          taxRegistrationNumber = Integer.parseInt(trn);
          if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
            taxpayerManager.removeTaxpayer(taxRegistrationNumber);
            taxRegisterNumberModel.removeElement(trn);
          }
        }catch (WrongTaxRegistrationNumberException e) {
          JOptionPane.showMessageDialog(null, e.getStackTrace());
        }
      } else {
        JOptionPane.showMessageDialog(null, "There isn't any taxpayer loaded. Please load one first.");
      }
    });
    btnDeleteTaxpayer.setBounds(287, 0, 146, 23);
    contentPane.add(btnDeleteTaxpayer);

    JTextField txtTaxRegistrationNumber = new JTextField();
    txtTaxRegistrationNumber.setEditable(false);
    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtTaxRegistrationNumber.setText("Tax Registration Number:");
    txtTaxRegistrationNumber.setBounds(70, 80, 300, 20);
    contentPane.add(txtTaxRegistrationNumber);
    txtTaxRegistrationNumber.setColumns(10);
  }
}