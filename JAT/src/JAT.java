import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class JAT extends JFrame {
    private JTextField companyNameTxt;
    private JTextField jobTitleTxt;
    private JTextField jobIDtxt;
    private JTextField urlTxt;
    private JTextField usernameTxt;
    private JPasswordField passwordTxt;
    private JRadioButton appliedBtn;
    private JRadioButton heardBackBtn;
    private JComboBox typeComboBox;
    private JList applicationsList;
    private JButton clearButton;
    private JButton viewApplicationButton;
    private JButton saveApplicationButton;
    private JLabel companyNameLbl;
    private JLabel jobTitleLbl;
    private JLabel jobIDLbl;
    private JLabel typeLbl;
    private JLabel appliedLbl;
    private JLabel urlLbl;
    private JLabel usernameLbl;
    private JLabel passwordLbl;
    private JLabel heardBackLbl;
    private JLabel programLbl;
    private JPanel rightSideSpace;
    private JPanel bottomSpace;
    private JPanel buttonsPanel;
    private JPanel leftSideSpace;
    private JPanel programNamePanel;
    private JPanel labelsPanel;
    private JPanel fillPanel;
    private JPanel mainPanel;
    private JLabel applicationsSaved;
    private JLabel applicationsSavedValue;
    private JButton deleteApplicationButton;
    private JButton editButton;

    private DefaultListModel appList;
    private Vector<jobApp> applications;
    private Vector<JTextField> textFields;
    private XMLparser parser;
    private Vector<jobApp> appXML;
    private boolean editing = false;

    private JAT(JFrame frame) {
        // create List for main window
        appList = new DefaultListModel();

        // group text fields for clearing purposes
        textFields = new Vector<>(7);
        textFields.addElement(companyNameTxt);
        textFields.addElement(jobTitleTxt);
        textFields.addElement(passwordTxt);
        textFields.addElement(urlTxt);
        textFields.addElement(usernameTxt);
        textFields.addElement(jobIDtxt);

        parser = new XMLparser();

        // saved applications will be added to this vector
        applications = parser.getExistingApplications();
        if (applications.size() > 0)
            applicationsSavedValue.setText(Integer.toString(applications.elementAt(0).applicationsSaved));

        // update list in main window
        for (int i = 0; i < applications.size(); i++) {
            jobApp temp = applications.elementAt(i);
            appList.addElement(temp.companyName + " - " + temp.jobTitle);
            applicationsList.setModel(appList);
        }

        // clear button functionality
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // save button functionality
        saveApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get values in text fields, combo box and buttons
                String compName = companyNameTxt.getText();
                String jobTitle = jobTitleTxt.getText();
                String jobID = jobIDtxt.getText();
                String type = (String) typeComboBox.getSelectedItem();
                boolean applied = appliedBtn.isSelected();
                String url = urlTxt.getText();
                String username = usernameTxt.getText();
                String password = String.valueOf(passwordTxt.getPassword());
                boolean heardBack = heardBackBtn.isSelected();

                // validate company name and job title before creating an application object
                if (compName.equals("") | jobTitle.equals("")) {
                    // display an error message
                    JFrame noSave = new JFrame();
                    JOptionPane.showMessageDialog(noSave, "You must input a Company Name and Job Title.", "Error",
                            JOptionPane.PLAIN_MESSAGE);
                    return; // exit function
                }

                if (typeComboBox.getSelectedIndex() == 0) {
                    // display an error message
                    JFrame noSave = new JFrame();
                    JOptionPane.showMessageDialog(noSave, "You must specify job type.", "Error",
                            JOptionPane.PLAIN_MESSAGE);
                    return; // exit function
                }

                // input is valid, create a jobApp object
                jobApp newJobApp;

                if (!editing) {
                    // first time seeing this jobApp
                    newJobApp = new jobApp(compName, jobTitle, jobID, type, applied, url, username, password,
                            heardBack);

                    // add new application to vector
                    applications.addElement(newJobApp);

                    // update amount of saved applications in main window
                    applicationsSavedValue.setText(Integer.toString(newJobApp.applicationsSaved));
                    appList.addElement(newJobApp.companyName + " - " + newJobApp.jobTitle);
                }

                // uptdate values on existing jobApp
                else {
                    int editingIndex = applicationsList.getSelectedIndex();
                    newJobApp = applications.elementAt(editingIndex);
                    newJobApp.companyName = compName;
                    newJobApp.jobTitle = jobTitle;
                    newJobApp.jobID = jobID;
                    newJobApp.type = type;
                    newJobApp.applied = applied;
                    newJobApp.url = url;
                    newJobApp.username = username;
                    newJobApp.password = password;
                    newJobApp.heardBack = heardBack;

                    // update jobApp on applications vector
                    applications.insertElementAt(newJobApp, editingIndex);

                    // udpate jobApp in list of main window
                    appList.set(editingIndex, newJobApp.companyName + " - " + newJobApp.jobTitle);
                }
                // update list in main window
                //appList.addElement(newJobApp.companyName + " - " + newJobApp.jobTitle);
                applicationsList.setModel(appList);

                parser.write(applications);

            }
        });

        // view button functionality
        viewApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame appView = new JFrame();
                applicationView app = new applicationView(appView); // appView frame as input
                int index = applicationsList.getSelectedIndex();
                System.out.println(Integer.toString(index));
                app.showApp(applications.elementAt(index));
                appView.setContentPane(app.mainPanel);
                appView.setSize(350, 300);
                appView.setVisible(true);
            }
        });

        // delete button functionality
        deleteApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (applicationsList.getSelectedIndex() != -1) {
                    // remove selected application from saved applications vector
                    jobApp delete = applications.remove(applicationsList.getSelectedIndex());
                    delete.applicationsSaved--;

                    // delete from xml
                    parser.delete(applicationsList.getSelectedIndex());

                    // remove from list displayed in gui
                    appList.remove(applicationsList.getSelectedIndex());

                    // decrease amount of applictions saved by 1
                    applicationsSavedValue.setText(Integer.toString(applications.size()));

                    // overwrite xml file
                    parser.overwrite(applications);
                }
            }
        });

        // edit button functionality
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int editingIndex = applicationsList.getSelectedIndex();
                // check if an application from the list has been selected to edit
                if (editingIndex != -1) {
                    // editing mode needed for save functionality
                    editing = true;
                    int comboBoxIndex = 0;
                    jobApp toEdit = applications.elementAt(editingIndex);

                    companyNameTxt.setText(toEdit.companyName);
                    jobTitleTxt.setText(toEdit.jobTitle);
                    jobIDtxt.setText(toEdit.jobID);
                    String tempType = toEdit.type;

                    if (tempType.equals("Full Time"))
                        comboBoxIndex = 1;

                    else if (tempType.equals("Part Time"))
                        comboBoxIndex = 2;

                    else if (tempType.equals("Internship"))
                        comboBoxIndex = 3;

                    typeComboBox.setSelectedIndex(comboBoxIndex);

                    if (toEdit.applied)
                        appliedBtn.setSelected(true);

                    urlTxt.setText(toEdit.url);
                    usernameTxt.setText(toEdit.username);

                    if (toEdit.password.equals("N/A"))
                        passwordTxt.setText("");
                    else
                        passwordTxt.setText(toEdit.password);

                    if (toEdit.heardBack)
                        heardBackBtn.setSelected(true);

                }

            }
        });

        // delete button functionality
        deleteApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (applicationsList.getSelectedIndex() != -1) {
                    // remove selected application from saved applications vector
                    jobApp delete = applications.remove(applicationsList.getSelectedIndex());
                    delete.applicationsSaved--;

                    // delete from xml
                    parser.delete(applicationsList.getSelectedIndex());

                    // remove from list displayed in gui
                    appList.remove(applicationsList.getSelectedIndex());

                    // decrease amount of applictions saved by 1
                    applicationsSavedValue.setText(Integer.toString(applications.size()));

                    // overwrite xml file
                    parser.overwrite(applications);
                }
            }
        });
    }

    private void clearFields() {
        for (int i = 0; i < textFields.size(); i++) {
            JTextField temp = textFields.elementAt(i);
            temp.setText("");
        }

        appliedBtn.setSelected(false);
        heardBackBtn.setSelected(false);
        typeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JAT g = new JAT(frame);
        frame.setContentPane(g.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
