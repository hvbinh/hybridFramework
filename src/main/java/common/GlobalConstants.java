package common;

public class GlobalConstants {
    private static GlobalConstants globalConstants;

    private GlobalConstants()
    {

    }

    public static synchronized GlobalConstants getGlobalConstants() {
        if(globalConstants == null)
        {
            globalConstants = new GlobalConstants();
        }
        return globalConstants;
    }
    private final long SHORT_TIME = 30;
    protected long getSHORT_TIME() {
        return SHORT_TIME;
    }

    protected long getLONG_TIME() {
        return LONG_TIME;
    }

    protected String getROOT_FOLDER() {
        return ROOT_FOLDER;
    }

    protected String getOS_NAME() {
        return OS_NAME;
    }

    protected String getJAVA_VERSION() {
        return JAVA_VERSION;
    }

    private final long LONG_TIME = 60;
    private final String ROOT_FOLDER = System.getProperty("user.dir");
    private final String OS_NAME = System.getProperty("os.name");
    private final String JAVA_VERSION = System.getProperty("java.version");

    protected  String getDirectorySlash(String folderName) {
        if (isMac() || isUnix() || isSolaris()) {
            folderName = "/" + folderName + "/";
        } else {
            folderName = "\\" + folderName + "\\";
        }
        return folderName;
    }

    protected boolean isWindows() {
        return (globalConstants.OS_NAME.toLowerCase().indexOf("win") >= 0);
    }

    protected boolean isMac() {
        return (globalConstants.OS_NAME.toLowerCase().indexOf("mac") >= 0);
    }

    protected boolean isUnix() {
        return (globalConstants.OS_NAME.toLowerCase().indexOf("nix") >= 0 || globalConstants.OS_NAME.toLowerCase().indexOf("nux") >= 0 || globalConstants.OS_NAME.toLowerCase().indexOf("aix") > 0);
    }

    protected boolean isSolaris() {
        return (globalConstants.OS_NAME.toLowerCase().indexOf("sunos") >= 0);
    }
}
