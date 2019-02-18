public class jobApp {
    static int applicationsSaved = 0;
    String companyName;
    String jobTitle;
    String jobID;
    String type;
    String applied;
    String url;
    String username;
    String password;
    String heardBack;

    jobApp(String _companyName, String _jobTitle, String _jobId, String _type, boolean _applied, String _url, String _username, String _password, boolean _heardBack)
    {
        companyName = validateInput(_companyName);
        jobTitle = validateInput(_jobTitle);
        jobID = validateInput(_jobId);
        type = validateInput(_type);
        applied = boolToString(_applied);
        url = validateInput(_url);
        username = validateInput(_username);
        password = validateInput(_password);
        heardBack = boolToString(_heardBack);
        applicationsSaved += 1;
    }

    private String validateInput(String input)
    {
        if (input.equals("")) { return "-"; }
        return input;
    }

    public String boolToString(boolean bool)
    {
        if (bool) {return "Yes";}
        return "No";
    }

    static void deleteApp() {applicationsSaved--;}
}
