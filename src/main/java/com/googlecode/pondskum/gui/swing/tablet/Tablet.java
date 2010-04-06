/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pinthura.util.SystemPropertyRetriever;
import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.gui.swing.suite.ContextMenuActions;
import com.googlecode.pondskum.stub.StubbyBigpondUsageInformationBuilder;
import com.googlecode.pondskum.util.DefaultUsageConverter;
import com.googlecode.pondskum.util.NumericUtilImpl;
import com.googlecode.pondskum.util.UsageConverter;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import static com.googlecode.pondskum.gui.swing.suite.ContextMenuActions.createExitWindowTransition;

public final class Tablet extends JDialog implements UpdatableTablet {

    private static final long serialVersionUID = -1033331705617879856L;
    private static final int RED = 255;
    private static final int GREEN = 186;
    private static final int BLUE = 0;

    private JPanel contentPane;
    private JLabel accountNameLabel;
    private JLabel accountNumberLabel;
    private JLabel currentPlanLabel;
    private JLabel monthlyPlanFeeLabel;
    private JTable usageTable;
    private JTextArea notificationTextArea;
    private JLabel monthlyAllowanceLabel;
    private JLabel totalUsageLabel;
    private SystemPropertyRetriever propertyRetriever;
    private UsageConverter usageConverter;

    public Tablet() {
        propertyRetriever = new SystemPropertyRetrieverImpl();
        usageConverter = new DefaultUsageConverter(new NumericUtilImpl());
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        setDefaults();
    }

    public void resetForReuse() {
        notificationTextArea.setText("");
        usageTable.setModel(new NullBigpondTableModel());
    }

    public void setDefaults() {
        setTitle("Pondskum Tablet");
        setModal(true);
        setContentPane(contentPane);
        addEventListeners();
    }

    @Override
    public void setTabletData(final BigpondUsageInformation usageInformation) {
        BigpondTableModel bigpondTableModel = new BigpondTableModel(usageInformation, usageConverter);

        usageTable.setDefaultRenderer(UsageTableValue.class, new UsageQuotaRenderer());
        usageTable.setDefaultRenderer(String.class, new UsageDateRenderer());
        usageTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
        setAccountInfo(usageInformation);
        usageTable.setModel(bigpondTableModel);
        scrollLastRow();
        updateTotalUsage(usageInformation);
    }

    private void updateTotalUsage(final BigpondUsageInformation usageInformation) {
        totalUsageLabel.setText("Total Usage: " + usageConverter.toString(usageInformation.getTotalUsage().getTotalUsage()));
    }

    private void scrollLastRow() {
        int lastIndex = usageTable.getModel().getRowCount() - 1;
        usageTable.setRowSelectionInterval(lastIndex, lastIndex);
        usageTable.scrollRectToVisible(usageTable.getCellRect(lastIndex, 0, true));
    }

    private void setAccountInfo(final BigpondUsageInformation usageInformation) {
        accountNameLabel.setText(usageInformation.getAccountInformation().getAccountName());
        accountNumberLabel.setText(usageInformation.getAccountInformation().getAccountNumber());
        currentPlanLabel.setText(usageInformation.getAccountInformation().getCurrentPlan());
        monthlyAllowanceLabel.setText(usageInformation.getAccountInformation().getMonthlyAllowanceShaping());
        monthlyPlanFeeLabel.setText(usageInformation.getAccountInformation().getMonthlyPlanFee());
    }

    private void addEventListeners() {
        setCloseOperations();
    }

    private void setCloseOperations() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(createExitWindowTransition());

        //ESCAPE
        contentPane.registerKeyboardAction(
                ContextMenuActions.createExitTransition(),
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, BLUE), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        Tablet tablet = new Tablet();
        tablet.setTabletData(new StubbyBigpondUsageInformationBuilder().build());
        tablet.pack();
        tablet.setResizable(true);
        tablet.setLocationRelativeTo(null);
        tablet.setVisible(true);
    }

    @Override
    public void updateStatus(final String update) {
        notificationTextArea.append(update + propertyRetriever.getLineSeparator());
    }

    // CHECKSTYLE:OFF
    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, BLUE, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, BLUE, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(BLUE, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, BLUE, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1));
        contentPane.add(panel3, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, BLUE, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 2, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1));
        panel3.add(panel4, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, BLUE, false));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Dialog", Font.PLAIN, 14));
        label1.setText("Account Name");
        panel4.add(label1, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        accountNameLabel = new JLabel();
        accountNameLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        accountNameLabel.setText("N/A");
        panel4.add(accountNameLabel, new GridConstraints(BLUE, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font("Dialog", Font.PLAIN, 14));
        label2.setText("Account Number");
        panel4.add(label2, new GridConstraints(1, BLUE, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        accountNumberLabel = new JLabel();
        accountNumberLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        accountNumberLabel.setText("N/A");
        panel4.add(accountNumberLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        final JLabel label3 = new JLabel();
        label3.setFont(new Font("Dialog", Font.PLAIN, 14));
        label3.setText("Current Plan");
        panel4.add(label3, new GridConstraints(2, BLUE, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        currentPlanLabel = new JLabel();
        currentPlanLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        currentPlanLabel.setText("N/A");
        panel4.add(currentPlanLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font("Dialog", Font.PLAIN, 14));
        label4.setText("Monthly Allowance");
        panel4.add(label4, new GridConstraints(3, BLUE, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        monthlyAllowanceLabel = new JLabel();
        monthlyAllowanceLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        monthlyAllowanceLabel.setText("N/A");
        panel4.add(monthlyAllowanceLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        final JLabel label5 = new JLabel();
        label5.setFont(new Font("Dialog", Font.PLAIN, 14));
        label5.setText("Monthly Plan Fee");
        panel4.add(label5, new GridConstraints(4, BLUE, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        monthlyPlanFeeLabel = new JLabel();
        monthlyPlanFeeLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        monthlyPlanFeeLabel.setText("N/A");
        panel4.add(monthlyPlanFeeLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, BLUE, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(2, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, BLUE, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1));
        panel3.add(panel5, new GridConstraints(1, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, BLUE, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setFont(new Font(scrollPane1.getFont().getName(), scrollPane1.getFont().getStyle(), scrollPane1.getFont().getSize()));
        panel5.add(scrollPane1, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, BLUE, false));
        usageTable = new JTable();
        usageTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usageTable.setIntercellSpacing(new Dimension(1, 3));
        usageTable.setShowHorizontalLines(false);
        usageTable.setToolTipText("Usage data");
        scrollPane1.setViewportView(usageTable);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(BLUE, BLUE, BLUE, BLUE), -1, -1));
        contentPane.add(panel6, new GridConstraints(2, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, BLUE, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel6.add(scrollPane2, new GridConstraints(BLUE, BLUE, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, BLUE, false));
        notificationTextArea = new JTextArea();
        notificationTextArea.setBackground(new Color(-16777216));
        notificationTextArea.setEditable(false);
        notificationTextArea.setFont(new Font("Lucida Sans", Font.PLAIN, 10));
        notificationTextArea.setForeground(new Color(-10053376));
        notificationTextArea.setLineWrap(true);
        notificationTextArea.setRows(10);
        notificationTextArea.setToolTipText("Notication Area");
        notificationTextArea.setWrapStyleWord(false);
        scrollPane2.setViewportView(notificationTextArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
    // CHECKSTYLE:ON
}
