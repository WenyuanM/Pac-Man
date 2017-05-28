import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Nancy on 2017/5/24.
 */

public class WriteFile {

    public boolean writeFile(String fileName,AccountList accountList){
        try{
            File outputFile = new File(fileName);
            PrintWriter out = new PrintWriter(outputFile);
            int size = accountList.getNumAccount();
            for(int i=0;i<size;i++){
                out.println(accountList.getAccount(i).toString());
            }
            if(Constants.DEBUG) System.out.println("After writing the file");
            out.close();
        }
        catch(FileNotFoundException exception){
            return false;
        }
        return true;
    }
}
