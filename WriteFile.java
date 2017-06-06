package HelpingClass;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

import Account.AccountList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// WriteFile class: to write the information into the file

public class WriteFile {

    /**
     * to write the new registered account into the file
     * @param fileName the writing file name
     * @param accountList the current account list
     * @return write successfully or not
     */
    public boolean writeFile(String fileName, AccountList accountList){
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
