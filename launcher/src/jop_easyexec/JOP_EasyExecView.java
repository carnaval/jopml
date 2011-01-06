/*
 * JOP_EasyExecView.java
 */

package jop_easyexec;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.util.*;

/**
 * The application's main frame.
 */
public class JOP_EasyExecView extends FrameView {

    public JOP_EasyExecView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                }
            }
        });
        LoadFileFromDirectory();
        TabbedPane.setSelectedIndex(0);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = JOP_EasyExecApp.getApplication().getMainFrame();
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        JOP_EasyExecApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        TabbedPane = new javax.swing.JTabbedPane();
        simulationPanel = new javax.swing.JPanel();
        WorkingDirectoryTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        DirectoryBrowseButton = new javax.swing.JButton();
        ScrollFileListPane = new javax.swing.JScrollPane();
        FileListPane = new javax.swing.JList();
        BriefCheckOption = new javax.swing.JCheckBox();
        RadioInputFile = new javax.swing.JRadioButton();
        RadioInputCommand = new javax.swing.JRadioButton();
        InputFileBrowseButton = new javax.swing.JButton();
        InputFileTextField = new javax.swing.JTextField();
        InputCommandTextField = new javax.swing.JTextField();
        TickCheckOption = new javax.swing.JCheckBox();
        NumberOfTicksTextField = new javax.swing.JTextField();
        SimulateButton = new javax.swing.JButton();
        outputPanel = new javax.swing.JPanel();
        outputScrollPane = new javax.swing.JScrollPane();
        OutputTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        fileChooser = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        fileChooser2 = new javax.swing.JFileChooser();
        jScrollPane2 = new javax.swing.JScrollPane();

        mainPanel.setName("mainPanel"); // NOI18N

        TabbedPane.setName("TabbedPane"); // NOI18N

        simulationPanel.setName("simulationPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jop_easyexec.JOP_EasyExecApp.class).getContext().getResourceMap(JOP_EasyExecView.class);
        WorkingDirectoryTextField.setText(resourceMap.getString("WorkingDirectoryTextField.text")); // NOI18N
        WorkingDirectoryTextField.setName("WorkingDirectoryTextField"); // NOI18N
        WorkingDirectoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WorkingDirectoryTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        DirectoryBrowseButton.setText(resourceMap.getString("DirectoryBrowseButton.text")); // NOI18N
        DirectoryBrowseButton.setName("DirectoryBrowseButton"); // NOI18N
        DirectoryBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        ScrollFileListPane.setName("ScrollFileListPane"); // NOI18N

        FileListPane.setName("FileListPane"); // NOI18N
        FileListPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FileListPaneMouseClicked(evt);
            }
        });
        ScrollFileListPane.setViewportView(FileListPane);

        BriefCheckOption.setSelected(true);
        BriefCheckOption.setText(resourceMap.getString("BriefCheckOption.text")); // NOI18N
        BriefCheckOption.setName("BriefCheckOption"); // NOI18N

        buttonGroup1.add(RadioInputFile);
        RadioInputFile.setText(resourceMap.getString("RadioInputFile.text")); // NOI18N
        RadioInputFile.setName("RadioInputFile"); // NOI18N

        buttonGroup1.add(RadioInputCommand);
        RadioInputCommand.setSelected(true);
        RadioInputCommand.setText(resourceMap.getString("RadioInputCommand.text")); // NOI18N
        RadioInputCommand.setName("RadioInputCommand"); // NOI18N

        InputFileBrowseButton.setText(resourceMap.getString("InputFileBrowseButton.text")); // NOI18N
        InputFileBrowseButton.setName("InputFileBrowseButton"); // NOI18N
        InputFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Open2ActionPerformed(evt);
            }
        });

        InputFileTextField.setText(resourceMap.getString("InputFileTextField.text")); // NOI18N
        InputFileTextField.setName("InputFileTextField"); // NOI18N
        InputFileTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputFileTextFieldActionPerformed(evt);
            }
        });

        InputCommandTextField.setText(resourceMap.getString("InputCommandTextField.text")); // NOI18N
        InputCommandTextField.setName("InputCommandTextField"); // NOI18N
        InputCommandTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputCommandTextFieldFocusGained(evt);
            }
        });

        TickCheckOption.setSelected(true);
        TickCheckOption.setText(resourceMap.getString("TickCheckOption.text")); // NOI18N
        TickCheckOption.setName("TickCheckOption"); // NOI18N

        NumberOfTicksTextField.setText(resourceMap.getString("NumberOfTicksTextField.text")); // NOI18N
        NumberOfTicksTextField.setName("NumberOfTicksTextField"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jop_easyexec.JOP_EasyExecApp.class).getContext().getActionMap(JOP_EasyExecView.class, this);
        SimulateButton.setAction(actionMap.get("Simulate")); // NOI18N
        SimulateButton.setText(resourceMap.getString("SimulateButton.text")); // NOI18N
        SimulateButton.setName("SimulateButton"); // NOI18N

        javax.swing.GroupLayout simulationPanelLayout = new javax.swing.GroupLayout(simulationPanel);
        simulationPanel.setLayout(simulationPanelLayout);
        simulationPanelLayout.setHorizontalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addComponent(BriefCheckOption)
                        .addGap(18, 18, 18)
                        .addComponent(TickCheckOption)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NumberOfTicksTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(WorkingDirectoryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DirectoryBrowseButton))
                    .addComponent(ScrollFileListPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addComponent(RadioInputFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputFileBrowseButton))
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addComponent(RadioInputCommand)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputCommandTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                    .addComponent(SimulateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );
        simulationPanelLayout.setVerticalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WorkingDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(DirectoryBrowseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollFileListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioInputFile)
                    .addComponent(InputFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InputFileBrowseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(InputCommandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RadioInputCommand))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NumberOfTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TickCheckOption))
                    .addComponent(BriefCheckOption))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TabbedPane.addTab(resourceMap.getString("simulationPanel.TabConstraints.tabTitle"), simulationPanel); // NOI18N

        outputPanel.setName("outputPanel"); // NOI18N

        outputScrollPane.setName("outputScrollPane"); // NOI18N

        OutputTextArea.setColumns(20);
        OutputTextArea.setEditable(false);
        OutputTextArea.setRows(5);
        OutputTextArea.setName("OutputTextArea"); // NOI18N
        outputScrollPane.setViewportView(OutputTextArea);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(outputScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPane.addTab(resourceMap.getString("outputPanel.TabConstraints.tabTitle"), outputPanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        fileChooser.setDialogTitle(resourceMap.getString("fileChooser.dialogTitle")); // NOI18N
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setName("fileChooser"); // NOI18N

        fileChooser2.setName("fileChooser2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void WorkingDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WorkingDirectoryTextFieldActionPerformed
        LoadFileFromDirectory();
    }//GEN-LAST:event_WorkingDirectoryTextFieldActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        int returnVal = fileChooser.showOpenDialog(this.getComponent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                WorkingDirectoryTextField.setText( file.getAbsolutePath() );
            }
        LoadFileFromDirectory();
    }//GEN-LAST:event_OpenActionPerformed

    private void Open2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Open2ActionPerformed
        int returnVal = fileChooser2.showOpenDialog( this.getComponent() );
        try{
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser2.getSelectedFile();
                    InputFileTextField.setText( file.getAbsolutePath() );
                }
        } catch( Exception e ) {}
        RadioInputFile.setSelected(true);
    }//GEN-LAST:event_Open2ActionPerformed

    private void InputFileTextFieldActionPerformed(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputFileTextFieldActionPerformed
        RadioInputFile.setSelected(true);
    }//GEN-LAST:event_InputFileTextFieldActionPerformed

    private void InputCommandTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputCommandTextFieldFocusGained
        RadioInputCommand.setSelected(true);
    }//GEN-LAST:event_InputCommandTextFieldFocusGained

    private void FileListPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FileListPaneMouseClicked
        if(FileListPane.isSelectionEmpty() )
            return;

        List<String> GateList =
                ReadFile(WorkingDirectoryTextField.getText()
                + FileListPane.getSelectedValue() + ".nl");
        
        String toAppend = "";
        for(String S : GateList){
            String[] Parsing = S.split(" ");
            if(Parsing[0].equals("input"))
                toAppend += Parsing[1].substring(1) + "=0 ";
        }

        InputCommandTextField.setText( toAppend );
    }//GEN-LAST:event_FileListPaneMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox BriefCheckOption;
    private javax.swing.JButton DirectoryBrowseButton;
    private javax.swing.JList FileListPane;
    private javax.swing.JTextField InputCommandTextField;
    private javax.swing.JButton InputFileBrowseButton;
    private javax.swing.JTextField InputFileTextField;
    private javax.swing.JTextField NumberOfTicksTextField;
    private javax.swing.JTextArea OutputTextArea;
    private javax.swing.JRadioButton RadioInputCommand;
    private javax.swing.JRadioButton RadioInputFile;
    private javax.swing.JScrollPane ScrollFileListPane;
    private javax.swing.JButton SimulateButton;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JCheckBox TickCheckOption;
    private javax.swing.JTextField WorkingDirectoryTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JPanel simulationPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

    private void LoadFileFromDirectory(){
        File[] listOfFiles = (new File(WorkingDirectoryTextField.getText())).listFiles();
        List<String> toAppend = new ArrayList<String>();
        for(File f : listOfFiles)
            if( f.getName().endsWith(".nl") )
                toAppend.add( f.getName().substring(0, f.getName().length()-3));

        String[] finalArray = new String[toAppend.size()];
        toAppend.toArray( finalArray );
        
        FileListPane.removeAll();
        FileListPane.setListData( finalArray );
    }

    @Action
    public void Simulate() {
        String Param = "";
        
        if( BriefCheckOption.isSelected() )
            Param += "-brief";
        
        if( RadioInputFile.isSelected() )
            Param += " -f " + InputFileTextField.getText();
        else
            Param += " -i " + InputCommandTextField.getText();

        if( TickCheckOption.isSelected() )
            Param += " -tick " + NumberOfTicksTextField.getText();

        Param += " " + WorkingDirectoryTextField.getText() + FileListPane.getSelectedValue() + ".nl";

        Param = "java -jar JOP_Simulator.jar " + Param;
        jLabel2.setText( Param );

        try {
            String line;
            Process p = Runtime.getRuntime().exec( Param );
            BufferedReader input =
                new BufferedReader
                    (new InputStreamReader(p.getInputStream()));

            OutputTextArea.setText("");
            while ((line = input.readLine()) != null) {
                OutputTextArea.append(line + "\n");
            }
            input.close();

            TabbedPane.setSelectedIndex(1);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Action
    public void PutDefaultInputs() {
        System.out.println();
    }

    public static List<String> ReadFile(String FileName) {
        List<String> FList = new ArrayList<String>();

        BufferedReader BR = null;
        try { BR = new BufferedReader (new FileReader(FileName)); }
        catch(FileNotFoundException e) { System.out.println("File " + FileName + " not found."); }

        try {
        String line;
        while ((line = BR.readLine()) != null)
            FList.add(line);
        BR.close();
        } catch (Exception e) {}

        return FList;
    }
}