package rover;

import java.util.Locale;

/**
 * Helper class to check the operating system this Java VM runs in
 * notes below kept as a pseudo-license
 * <a href="http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java">
 * src</a>
 */
public final class OsCheck {
    /**
     * types of Operating Systems
     */
    public enum OsType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    private static OsType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @return - the operating system detected
     */
    public static OsType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || (os.contains("darwin"))) {
                detectedOS = OsType.MacOS;
            } else if (os.contains("win")) {
                detectedOS = OsType.Windows;
            } else if (os.contains("nux")) {
                detectedOS = OsType.Linux;
            } else {
                detectedOS = OsType.Other;
            }
        }
        return detectedOS;
    }
}
