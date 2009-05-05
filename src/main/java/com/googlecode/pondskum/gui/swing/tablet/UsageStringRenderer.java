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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public final class UsageStringRenderer extends DefaultTableCellRenderer {

    private final int rowCount;
    private TotalsRender totalsRender;

    public UsageStringRenderer(final int rowCount, final Color totalsColor) {
        this.rowCount = rowCount;
        totalsRender = new TotalsRender(totalsColor);
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
                                                   final int row, final int column) {
        if (row == (rowCount - 1)) {
            return totalsRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setHorizontalAlignment(SwingConstants.CENTER);

        return component;
    }

    private static class TotalsRender extends DefaultTableCellRenderer {
        private final Color totalsColor;

        public TotalsRender(final Color totalsColor) {
            this.totalsColor = totalsColor;
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
                                                       final int row, final int column) {
            JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setHorizontalAlignment(SwingConstants.CENTER);
            component.setBackground(totalsColor);
            return component;
        }
    }
}
