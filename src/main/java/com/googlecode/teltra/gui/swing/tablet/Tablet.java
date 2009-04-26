package com.googlecode.teltra.gui.swing.tablet;

import com.googlecode.teltra.client.BigpondConnector;
import com.googlecode.teltra.client.BigpondMonthlyUsage;
import com.googlecode.teltra.client.BigpondUsageInformation;
import com.googlecode.teltra.client.BigpondUsage;
import com.googlecode.teltra.config.ConfigFileLoaderImpl;
import com.googlecode.teltra.config.SystemPropertyRetrieverImpl;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.io.Serializable;

public class Tablet extends JDialog {

    private static final long serialVersionUID = -1033331705617879856L;

    private JPanel contentPane;
    private JButton updateButton;
    private JButton exitButton;
    private JLabel accountNameLabel;
    private JLabel accountNumberLabel;
    private JLabel currentPlanLabel;
    private JLabel monthlyPlanFeeLabel;
    private JTable usageTable;
    private JTextArea notificationTextArea;
    private JLabel monthlyAllowanceLabel;

    public Tablet() {
        setTitle("Bigpond Connection Tablet");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(updateButton);

        addEventListeners();
    }

    public void setTabletData(final BigpondUsageInformation usageInformation) {
        setAccountInfo(usageInformation);
        usageTable.setModel(new BigPondTableModel(usageInformation));
    }

    private void setAccountInfo(final BigpondUsageInformation usageInformation) {
        accountNameLabel.setText(usageInformation.getAccountInformation().getAccountName());
        accountNumberLabel.setText(usageInformation.getAccountInformation().getAccountNumber());
        currentPlanLabel.setText(usageInformation.getAccountInformation().getCurrentPlan());
        monthlyAllowanceLabel.setText(usageInformation.getAccountInformation().getMonthlyAllowance());
        monthlyPlanFeeLabel.setText(usageInformation.getAccountInformation().getMonthlyPlanFee());
    }

    private void addEventListeners() {
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onUpdate();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onUpdate() {
// add your code here
        dispose();
    }

    private void onExit() {
        dispose();
    }

    private final class BigPondTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 550659947871203828L;

        private final BigpondUsageInformation bigpondUsageInformation;
        private final String[] columnNames;

        private BigPondTableModel(final BigpondUsageInformation bigpondUsageInformation) {
            this.bigpondUsageInformation = bigpondUsageInformation;
            columnNames = new String[] {"Month", "Download", "Upload", "Unmetered", "Total"};
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            if (row == bigpondUsageInformation.getMonthlyUsageList().size()) {
                BigpondUsage usage = bigpondUsageInformation.getTotalUsage();
                switch (column) {
                    case 0: return "Total";
                    case 1: return usage.getDownloadUsage();
                    case 2: return usage.getUploadUsage();
                    case 3: return usage.getUnmeteredUsage();
                    case 4: return usage.getTotalUsage();
                    default: return "N/A";
                }
            }

            BigpondMonthlyUsage usage = bigpondUsageInformation.getMonthlyUsageList().get(row);
            switch (column) {
                case 0: return usage.getMonth();
                case 1: return usage.getBigpondUsage().getDownloadUsage();
                case 2: return usage.getBigpondUsage().getUploadUsage();
                case 3: return usage.getBigpondUsage().getUnmeteredUsage();
                case 4: return usage.getBigpondUsage().getTotalUsage();
                default: return "N/A";
            }
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(final int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            return bigpondUsageInformation.getMonthlyUsageList().size() + 1;
        }
    }
}
