package org.os;
import java.io.File;
public class cd {
    static String cw=System.getProperty("user.dir");

    public static String cd(String path) {

       if (path.equals("..")) {
           File current = new File(cw);
           if (current.getParent() != null) {  // Prevent going above root
               cw = current.getParent();
           } else {
               return "cannot go above root directory.";
           }
       }else if (path.equals(".")) {
               return cw;
       } else {
           File dir = new File(cw, path);
           if (dir.exists() && dir.isDirectory()) {
               cw = dir.getAbsolutePath();
           } else if (!dir.exists()) {
               return "Incorrect path: " + path;
           } else {
               return "not a directory: " + path;
           }
       }
       return "current directory: " + cw;
   }

public static void main(String[] args) {
    System.out.println("Starting Directory: " + cd.cw);

    System.out.println(cd.cd(".."));
    System.out.println(cd.cd("nonexistentDir"));
    System.out.println(cd.cd("."));
    System.out.println(cd.cd("src"));
}
}