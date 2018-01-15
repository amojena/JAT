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
    private JTextField passwordTxt;
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


    private DefaultListModel appList;
    private Vector<jobApp> applications;
    private Vector<JTextField> textFields;
    private XMLparser parser;
    private Vector<jobApp> appXML;



    private JAT() {
        //create List for main window
        appList = new DefaultListModel();


        //group text fields for clearing purposes
        textFields = new Vector<>(7);
        textFields.addElement(companyNameTxt);
        textFields.addElement(jobTitleTxt);
        textFields.addElement(passwordTxt);
        textFields.addElement(urlTxt);
        textFields.addElement(usernameTxt);
        textFields.addElement(jobIDtxt);

        parser = new XMLparser();

        //saved applications will be added to this vector
        applications = parser.getExistingApplications();
        if (applications.size() > 0) { applicationsSavedValue.setText(Integer.toString(applications.elementAt(0).applicationsSaved)); }

        //update list in main window
        for(int i = 0; i < applications.size(); i++)
        {
            jobApp temp = applications.elementAt(i);
            appList.addElement(temp.companyName + " - " + temp.jobTitle);
            applicationsList.setModel(appList);
        }



        //clear button functionality
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        //save button functionality
        saveApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get values in text fields, combo box and buttons
                String compName = companyNameTxt.getText();
                String jobTitle = jobTitleTxt.getText();
                String jobID = jobIDtxt.getText();
                String type = (String) typeComboBox.getSelectedItem();
                boolean applied = appliedBtn.isSelected();
                String url = urlTxt.getText();
                String username = usernameTxt.getText();
                String password = passwordTxt.getText();
                boolean heardBack = heardBackBtn.isSelected();

                //validate company name and job title before creating an application object
                if (compName.equals("") | jobTitle.equals(""))
                {
                    //display an error message
                    JFrame noSave = new JFrame();
                    JOptionPane.showMessageDialog(noSave, "You must input a Company Name and Job Title.", "Error", JOptionPane.PLAIN_MESSAGE);
                    return; //exit function
                }

                //create an application object
                jobApp j = new jobApp(compName, jobTitle, jobID, type, applied, url, username, password, heardBack);

                //add new application to vector
                applications.addElement(j);

                //update amount of saved applications in main window
                applicationsSavedValue.setText(Integer.toString(j.applicationsSaved));

                //update list in main window
                appList.addElement(j.companyName + " - " + j.jobTitle);
                applicationsList.setModel(appList);

                parser.write(applications);

            }
        });

        //load button functionality
        viewApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame appView = new JFrame();
                applicationView app = new applicationView();
                int index = applicationsList.getSelectedIndex();
                app.showApp(applications.elementAt(index));
                appView.setContentPane(app.mainPanel);
                appView.setSize(350,300);
                appView.setVisible(true);

            }
        });

        //delete button functionality
        deleteApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(applicationsList.getSelectedIndex() != -1)
                {
                    //remove selected application from saved applications vector
                    applications.remove(applicationsList.getSelectedIndex());

                    //delete from xml
                    parser.delete(applicationsList.getSelectedIndex());

                    //remove from list displayed in gui
                    appList.remove(applicationsList.getSelectedIndex());

                    //decrease amount of applictions saved by 1
                    applicationsSavedValue.setText(Integer.toString(applications.size()));

                    //overwrite xml file
                    parser.overwrite(applications);
                }
            }
        });
    }

    private void clearFields()
    {
        for(int i = 0; i < textFields.size(); i++)
        {
            JTextField temp = textFields.elementAt(i);
            temp.setText("");
        }

        appliedBtn.setSelected(false);
        heardBackBtn.setSelected(false);
        typeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JAT g =new JAT();
        frame.setContentPane(g.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
