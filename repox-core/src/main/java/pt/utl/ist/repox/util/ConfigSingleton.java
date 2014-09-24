package pt.utl.ist.repox.util;

import pt.utl.ist.repox.configuration.RepoxContextUtil;

/**
 * Created by IntelliJ IDEA.
 * User: GPedrosa
 * Date: 01-04-2011
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public class ConfigSingleton {
    private static RepoxContextUtil repoxContextUtil;

    @SuppressWarnings("javadoc")
    public static RepoxContextUtil getRepoxContextUtil() {
        return repoxContextUtil;
    }

    @SuppressWarnings("javadoc")
    public static void setRepoxContextUtil(RepoxContextUtil repoxContextUtil) {
        ConfigSingleton.repoxContextUtil = repoxContextUtil;
    }
}
