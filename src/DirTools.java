import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirTools 
{
    private File directory;

    //filecounter
    private long filecounter;

    //filetypes
    private List<String> filetypes;
    private Map<String, Integer> filetypescount;

    //Constructor
    public DirTools(File ADir)
    {
        directory = ADir;
        filetypescount = new HashMap<String,Integer>();
        filetypes = new ArrayList<>();
        filecounter = 0L;
    }

    //getDirLen - getDirectoryLength
    public long getDirLen()
    {
        return getDirLen(directory);
    }

    private long getDirLen(File ADir)
    {
        File[] files = ADir.listFiles();

        long result = 0L;

        if (files != null)
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isDirectory())
                    result += getDirLen(files[i]);
                else
                {
                    result++;
                    System.out.println(files[i].getAbsolutePath() + "\n");
                }
            }
        return result;
    }

    //scanDir - scanDirectory
    public void scanDir()
    {   
        filetypescount.clear();  
        filetypes.clear();
        filecounter = 0L;

        scanDir(directory);

        int count = 0;

        System.out.println("Anzahl an Datei-Typen mit (> 10 Dateien)");
        for (int i = 0; i < filetypes.size(); i++)
        {
            if (filetypescount.get(filetypes.get(i)) > 10)
              System.out.println(filetypes.get(i) + ": " + filetypescount.get(filetypes.get(i)) + " Dateien");
            count ++;
        }

        System.out.println("Anzahl an Datei-Typen insgesant: " + count);

        System.out.println("Anzahl an Dateien im Verzeichnis: " + filecounter);
    }

    private void scanDir(File ADir)
    {
        File[] files = ADir.listFiles();

        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isDirectory())
                    scanDir(files[i]);
                else
                {    
                    //Output Console                 
                    String path = files[i].getPath();
                    System.out.print(files[i].getAbsolutePath());
                    if (path.split("\\.").length > 0)
                        System.out.print(" (" + path.split("\\.")[path.split("\\.").length - 1] + ")");  
                    System.out.print(" [" + files[i].length() + " Byte" + "]\n");    

                    //Filecounter
                    filecounter++;

                    //Filetypes
                    if (files[i].getAbsolutePath().split("\\.").length > 1)
                    {
                        if (!filetypescount.containsKey(files[i].getAbsolutePath().split("\\.")[files[i].getAbsolutePath().split("\\.").length - 1]))
                        {
                            filetypescount.put(files[i].getAbsolutePath().split("\\.")[files[i].getAbsolutePath().split("\\.").length - 1], 1);
                            filetypes.add(files[i].getAbsolutePath().split("\\.")[files[i].getAbsolutePath().split("\\.").length - 1]);
                        }
                        else
                        {
                            int cache = filetypescount.get(files[i].getAbsolutePath().split("\\.")[files[i].getAbsolutePath().split("\\.").length - 1]);
                            filetypescount.replace(files[i].getAbsolutePath().split("\\.")[files[i].getAbsolutePath().split("\\.").length - 1], cache, cache + 1);
                        }  
                    }
                    else
                    {
                        if (!filetypescount.containsKey("_typeless_"))
                        {
                            filetypescount.put("_typeless_", 1);
                            filetypes.add("_typeless_");
                        }
                        else
                        {
                            int cache = filetypescount.get("_typeless_");
                            filetypescount.replace("_typeless_", cache, cache + 1);
                        }
                    }    
                }
            }
        }
    }
}
