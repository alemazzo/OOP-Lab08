package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface. Suggestion: use a second JPanel with a second
     * BorderLayout, put the panel in the North of the main panel, put the text
     * field in the center of the new panel and put the button in the line_end of
     * the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the current
     * selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should use
     * the method showSaveDialog() to display the file chooser, and if the result is
     * equal to JFileChooser.APPROVE_OPTION the program should set as new file in
     * the Controller the file chosen. If CANCEL_OPTION is returned, then the
     * program should do nothing. Otherwise, a message dialog should be shown
     * telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to update
     * the UI: in this example the UI knows when should be updated, so try to keep
     * things separated.
     */
    private final JFrame frame = new JFrame();
    private final Controller controller = new Controller();

    private final JTextArea textArea = new JTextArea();
    private final JTextField textField = new JTextField(this.controller.getCurrentFilePath());

    public SimpleGUIWithFileChooser() {

        // this.textField.setEnabled(false);
        this.textField.setEditable(false);
        
        final JPanel container = new JPanel(new BorderLayout());

        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JPanel upperPanel = new JPanel(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(container);
        
        container.add(upperPanel, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.SOUTH);

        final JButton browseButton = new JButton("Browse");
        upperPanel.add(this.textField, BorderLayout.WEST);
        upperPanel.add(browseButton, BorderLayout.EAST);

        final JButton saveButton = new JButton("Save");

        mainPanel.add(textArea, BorderLayout.NORTH);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    SimpleGUIWithFileChooser.this.controller
                            .writeOnCurrentFile(SimpleGUIWithFileChooser.this.textArea.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        browseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                final int res = fc.showSaveDialog(SimpleGUIWithFileChooser.this.frame);

                if (res == JFileChooser.APPROVE_OPTION) {
                    SimpleGUIWithFileChooser.this.controller.setCurrentFile(fc.getSelectedFile());
                    SimpleGUIWithFileChooser.this.textField
                            .setText(SimpleGUIWithFileChooser.this.controller.getCurrentFilePath());
                } else if (res != JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(SimpleGUIWithFileChooser.this.frame, "An Error Occured");
                }

            }
        });

    }

    public void display() {
        /*
         * Make the frame half the resolution of the screen. This very method is enough
         * for a single screen setup. In case of multiple monitors, the primary is
         * selected.
         * 
         * In order to deal coherently with multimonitor setups, other facilities exist
         * (see the Java documentation about this issue). It is MUCH better than
         * manually specify the size of a window in pixel: it takes into account the
         * current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this flag
         * makes the OS window manager take care of the default positioning on screen.
         * Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        new SimpleGUIWithFileChooser().display();

    }

}
