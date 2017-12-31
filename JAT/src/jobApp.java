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
        companyName = _companyName;
        jobTitle = _jobTitle;
        jobID = _jobId;
        type = _type;
        applied = _applied;
        url = _url;
        username = _username;
        password = _password;
        heardBack = _heardBack;
        applicationsSaved += 1;
    }

}
