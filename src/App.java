import java.io.File;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        File f = new File("/home/sergej/");

        DirTools dt = new DirTools(f);
        //System.out.println(dt.getDirLen(f));
        dt.scanDir();
    }
}
