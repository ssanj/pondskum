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

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.client.BigpondUsage;
import com.googlecode.pondskum.client.BigpondMonthlyUsage;

import javax.swing.table.AbstractTableModel;

public final class BigpondTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 550659947871203828L;
    private static final String N_A = "N/A";

    private final BigpondUsageInformation bigpondUsageInformation;
    private final String[] columnNames;

    public BigpondTableModel(final BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
        columnNames = new String[]{"Month", "Download", "Upload", "Unmetered", "Total"};
    }

    @Override
    public Object getValueAt(final int row, final int column) {
        if (isLastRow(row)) {
            BigpondUsage usage = bigpondUsageInformation.getTotalUsage();
            return (column == 0) ?  "Total" : getUsageValue(column, usage);
        }

        BigpondMonthlyUsage usage = bigpondUsageInformation.getMonthlyUsageList().get(row);
        return (column == 0) ? usage.getMonth() : getUsageValue(column, usage.getBigpondUsage());
    }

    private boolean isLastRow(final int row) {
        return row == bigpondUsageInformation.getMonthlyUsageList().size();
    }

    private Object getUsageValue(final int column, final BigpondUsage usage) {
        String usageValue = "-1";

        switch (column) {
            case 1:
                usageValue = usage.getDownloadUsage();
                break;
            case 2:
                usageValue = usage.getUploadUsage();
                break;
            case 3:
                usageValue = usage.getUnmeteredUsage();
                break;
            case 4:
                usageValue = usage.getTotalUsage();
                break;
        }

        return new UsageTableValue(Integer.parseInt(usageValue));
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
    public Class<?> getColumnClass(final int columnIndex) {
        return columnIndex == 0 ? String.class : UsageTableValue.class;
    }

    @Override
    public int getRowCount() {
        return bigpondUsageInformation.getMonthlyUsageList().size() + 1;
    }
}

