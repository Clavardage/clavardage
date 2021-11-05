package clavardage;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/** Main Class
 * @author Romain MONIER
 */
public class Clavardage
{
    public static InputStream getResourceStream(String path) {
        return Objects.requireNonNull(Clavardage.class.getResourceAsStream(path));
    }

    /** Starting point
     * @author Romain MONIER
     * @param args
     */
    public static void main(String[] args)
    {
        AppDirs appDirs = AppDirsFactory.getInstance();
        String app_dir = appDirs.getUserDataDir("Clavardage", null, "Clavardage") + File.separator + "CLAVARDAGE" + File.separator + "db";
        (new File(app_dir)).mkdirs();
        System.out.println("OK: app_dir for database : " + app_dir);
    }
}
