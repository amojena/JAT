import javax.swing.*;

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

    void showApp(jobApp app)
    {
        System.out.println(app.companyName);
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
