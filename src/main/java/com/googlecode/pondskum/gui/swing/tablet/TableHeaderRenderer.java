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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class TableHeaderRenderer extends DefaultTableCellRenderer implements MouseListener {

    private static final long serialVersionUID = -5922036445853578030L;
    private static final int FONT_SIZE = 16;
    private static final String FONT_NAME = "DejaVu Sans";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int RED = 131;
    private static final int GREEN = 203;
    private static final int BLUE = 146;

    private Toggle toggle;

    public TableHeaderRenderer() {
        toggle = new Toggle(true);
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
                                                   final int row, final int column) {
        JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
        component.setBackground(new Color(RED, GREEN, BLUE));
        component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        component.setHorizontalAlignment(SwingConstants.CENTER);
        component.setIcon(UIManager.getIcon(toggle.isSet() ? "Table.ascendingSortIcon" :  "Table.descendingSortIcon"));
        return component;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        toggle = toggle.toggle();
    }

    @Override
    public void mousePressed(final MouseEvent e) { }

    @Override
    public void mouseReleased(final MouseEvent e) { }

    @Override
    public void mouseEntered(final MouseEvent e) { }

    @Override
    public void mouseExited(final MouseEvent e) { }

    private static final class Toggle {
        private final boolean state;

        private Toggle(final boolean state) {
            this.state = state;
        }

        private Toggle toggle() {
           return new Toggle(!state);
        }

        private boolean isSet() {
            return state;
        }
    }
}
