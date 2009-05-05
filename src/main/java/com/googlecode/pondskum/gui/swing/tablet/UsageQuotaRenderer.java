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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public final class UsageQuotaRenderer extends DefaultTableCellRenderer {

    private final int rowCount;
    private TotalsRenderer totalsRenderer;

    public UsageQuotaRenderer(final int rowCount, final Color totalsColor) {
        this.rowCount = rowCount;
        totalsRenderer = new TotalsRenderer(totalsColor);
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        if (UsageTableValue.class.isAssignableFrom(value.getClass())) {
            if (row == (rowCount - 1)) {
                return totalsRenderer.getTableCellRendererComponent(table, ((UsageTableValue) value).getValue(), isSelected, hasFocus, row, column);
            }

            JLabel component = (JLabel) super.getTableCellRendererComponent(table, ((UsageTableValue) value).getValue(), isSelected, hasFocus, row, column);
            component.setHorizontalAlignment(RIGHT);
            return component;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    private static class TotalsRenderer extends DefaultTableCellRenderer {

        private final Color totalsColor;

        public TotalsRenderer(final Color totalsColor) {
            this.totalsColor = totalsColor;
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setHorizontalAlignment(RIGHT);
            component.setBackground(totalsColor);
            return component;
        }
    }

}
