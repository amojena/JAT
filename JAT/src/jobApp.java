public class jobApp {
    static int applicationsSaved = 0;
    String companyName;
    String jobTitle;
    String jobID;
    String type;
    boolean applied;
    String url;
    String username;
    String password;
    boolean heardBack;

    jobApp(String _companyName, String _jobTitle, String _jobId, String _type, boolean _applied, String _url, String _username, String _password, boolean _heardBack)
    {
        companyName = validateInput(_companyName);
        jobTitle = validateInput(_jobTitle);
        jobID = validateInput(_jobId);
        type = validateInput(_type);
        applied = _applied;
        url = validateInput(_url);
        username = validateInput(_username);
        password = validateInput(_password);
        heardBack = _heardBack;
        applicationsSaved += 1;
    }

    private String validateInput(String input)
    {
        if (input.equals("")) { return "N/A"; }
        return input;
    }

    public void deleteApp() {applicationsSaved--;}
}
