package com.googlecode.pondskum.gui.swing.suite;

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.ConfigFileLoaderException;
import com.googlecode.pondskum.gui.swing.notifyer.ConnectionStatusForm;
import com.googlecode.pondskum.gui.swing.notifyer.DefaultDisplayDetailsPack;
import com.googlecode.pondskum.gui.swing.notifyer.ErrorPanel;
import com.googlecode.pondskum.gui.swing.notifyer.ProgressionPanel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public final class ProgressionGui implements GUI {

    static final int WIDTH = 600;
    static final int HEIGHT = 90;

    private ProgressionPanel progressionPanel;
    private ConnectionStatusForm connectionStatusForm;
    private JFrame parentFrame;
    private WindowStateListener windowStateListener;
    private BigpondUsageInformation bigpondUsageInformation;

    public ProgressionGui(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        resetForReuse();
    }

    @Override
    public void resetForReuse() {
        progressionPanel = new ProgressionPanel();
        connectionStatusForm = new ConnectionStatusForm();
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(connectionStatusForm.getContentPanel());

        windowStateListener = null;
        bigpondUsageInformation = null;
    }

    private void removeWindowStateListener() {
        parentFrame.removeWindowStateListener(windowStateListener);
    }

    public void display() {
        parentFrame.setVisible(true);
    }

    @Override
    public void hide() {
        parentFrame.setVisible(false);
        removeWindowStateListener();
    }

    @Override
    public void setStateChangeListener(final StateChangeListener stateChangeListener) {
        windowStateListener = new ProgressionBarWindowStateListener(stateChangeListener);
        parentFrame.addWindowStateListener(windowStateListener);
    }

    @Override
    public BigpondUsageInformation getUsageInfo() {
        return bigpondUsageInformation;
    }

    @Override
    public void updateWithExistingUsage(final BigpondUsageInformation bigpondUsageInformation) {
        if (bigpondUsageInformation != null) {
            this.bigpondUsageInformation = bigpondUsageInformation;
            updateWithUsageInformation();
        }
    }

    @Override
    public void notifyStatusChange(final String status) {
       connectionStatusForm.setProgress(status);
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
        hideStatusForm();
        updateWithUsageInformation();
    }

    private void updateWithUsageInformation() {
        parentFrame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(bigpondUsageInformation);
    }

    private void hideStatusForm() {
        parentFrame.getContentPane().remove(connectionStatusForm.getContentPanel());
    }

    @Override
    public void connectionFailed(final Exception exception) {
        hideStatusForm();

        String errorMessage = exception.getMessage();
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(exception.getClass()));
        parentFrame.getContentPane().add(errorPanel.getContentPanel());
        parentFrame.setSize(WIDTH, HEIGHT);
        parentFrame.getContentPane().validate();
    }

    private class ProgressionBarWindowStateListener extends WindowAdapter {

        private final StateChangeListener stateChangeListener;

        public ProgressionBarWindowStateListener(final StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            JOptionPane.showMessageDialog(parentFrame, "Iconified");
            stateChangeListener.stateChangeOccured(ProgressionGui.this);
        }
    }
}
