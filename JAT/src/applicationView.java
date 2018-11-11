import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class applicationView {
    private JLabel companyNameLbl;
    private JLabel jobTitleLabel;
    private JLabel heardBackLbl;
    private JLabel passwordLbl;
    private JLabel jobIdLbl;
    private JLabel usernameLbl;
    private JLabel typeLbl;
    private JLabel appliedLbl;
    private JLabel urlLbl;
    private JLabel companyNameShow;
    private JLabel jobTitleShow;
    private JLabel jobIdShow;
    private JLabel typeShow;
    private JLabel appliedShow;
    private JLabel urlShow;
    private JLabel usernameShow;
    private JLabel passwordShow;
    private JLabel heardBackShow;
    private JPanel labelPanel;
    private JPanel showPanel;
    public JPanel mainPanel;
    private JPanel topSpacePanel;
    private JPanel bottomSpacePanel;
    private JPanel leftSpacePanel;
    private JPanel rightSpacePanel;
    private JButton closeButton;

    public applicationView(JFrame appView, JFrame mainFrame) {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close the window if the button is pressed
                appView.setVisible(false);
            }
        });
    }

    void showApp(jobApp app)
    {
        companyNameShow.setText(app.companyName);
        jobTitleShow.setText(app.jobTitle);
        jobIdShow.setText(app.jobID);
        typeShow.setText(app.type);
        appliedShow.setText(String.valueOf(app.applied));
        urlShow.setText(app.url);
        usernameShow.setText(app.username);
        passwordShow.setText(app.password);
        heardBackShow.setText(String.valueOf(app.heardBack));
    }
}
