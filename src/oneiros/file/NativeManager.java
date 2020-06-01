package oneiros.file;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class NativeManager {

    private static final boolean SYSTEM_LIBS = false;
    HashMap<OS, LinkedList<URL>> natives;
    private String applicationName;
    private Class source;

    public NativeManager(String applicationName) {
        this(applicationName, NativeManager.class);
    }

    public NativeManager(String applicationName, Class source) {
        this.applicationName = applicationName;
        this.source = source;
        this.natives = new HashMap<>();
    }

    public void addNative(OS os, String string) throws MalformedURLException {
        LinkedList list = this.natives.get(os);
        if (list == null) {
            list = new LinkedList();
        }
        list.add(new URL(string));
        this.natives.put(os, list);
    }

    public void addNatives(OS os, String string) throws Exception {
        String[] strings = getResourceListing(string);
        LinkedList list = this.natives.get(os);
        if (list == null) {
            list = new LinkedList();
        }
        for (String s : strings) {
            list.add(this.source.getResource(string + s));
        }
        this.natives.put(os, list);
    }

    public void removeNative(OS os, String string) {
        LinkedList<URL> list = this.natives.get(os);
        if (list != null) {
            for (URL url : list) {
                if (url.getPath().equals(string)) {
                    list.remove(url);
                    this.natives.put(os, list);
                    break;
                }
            }
        }
    }

    private static void copyFile(InputStream in, File outFile) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(outFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    void hide(File src) throws InterruptedException, IOException {
        Process p = Runtime.getRuntime().exec("attrib +h " + src.getPath());
        p.waitFor();
    }

    public void load() throws IOException, URISyntaxException {
        File workingDirectory = null;
        if (SYSTEM_LIBS) {
            String libraryPath = System.getProperty("java.library.path");
            StringTokenizer tokenizer = new StringTokenizer(libraryPath, File.pathSeparator);
            while (tokenizer.hasMoreElements()) {
                String path = tokenizer.nextToken();
                workingDirectory = new File(path);
                if (new File(workingDirectory, "prova.txt").canWrite()) {
                    break;
                }
            }
        } else {
            workingDirectory = new File(".");
        }
        OS platform = getPlatform();
        LinkedList<URL> platformNatives = this.natives.get(platform);
        if (platformNatives != null) {
            File[] files = new File[platformNatives.size()];
            for (int i = 0; i < files.length; i++) {
                URL url = platformNatives.get(i);
                String fileName = url.getPath().substring(url.getPath().lastIndexOf("/") + 1);
                files[i] = new File(workingDirectory, fileName);
                if (!files[i].exists()) {
                    InputStream is = url.openStream();
                    copyFile(is, files[i]);
                    try {
                        hide(files[i]);
                    } catch (InterruptedException ex) {
                    }
                }
            }

            /*
             * for (File file : files) {
             * System.out.println(file.getAbsolutePath()); try { String fileName
             * = file.getPath().substring(file.getPath().lastIndexOf("\\") + 1);
             * System.loadLibrary(fileName); System.out.println("loaded"); }
             * catch (UnsatisfiedLinkError ex) { System.err.println(ex); }
            }
             */

        }
    }

    public File setWorkingDirectory() {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;

        OS platform = getPlatform();

        switch (platform) {
            case windows32:
            case windows64:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, applicationName + '/');
                }
                break;
            case macosx:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            case linux32:
            case linux64:
            default:
                workingDirectory = new File(userHome, applicationName + '/');
        }
        if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs())) {
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        }
        return workingDirectory;
    }

    private String[] getResourceListing(String path) throws URISyntaxException, IOException {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        URL dirURL = source.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
            String me = source.getName().replace(".", "/") + ".class";
            dirURL = source.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries();
            Set<String> result = new HashSet<>();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) {
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }

    public static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch");
        if (osName.contains("linux") || osName.contains("unix")) {
            if (arch.contains("64")) {
                return OS.linux64;
            } else {
                return OS.linux32;
            }
        } else if (osName.contains("win")) {
            if (arch.contains("64")) {
                return OS.windows64;
            } else {
                return OS.windows32;
            }
        } else if (osName.contains("mac")) {
            return OS.macosx;
        } else {
            return OS.unknown;
        }
    }

    public static enum OS {

        linux32, linux64, windows32, windows64, macosx, unknown;
    }
}
