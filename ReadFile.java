package HelpingClass;

import Account.Account;
import Map.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// ReadFile class: a helping class to read information from files

public class ReadFile {
    // Private variables for readCharacterMapFile method
    private int _row;
    private int _col;

    /**
     * to read all the account information from file
     * @param fileName the account file name
     * @return an arrayList of account storing in the file
     */
    public ArrayList<Account> readAccountFile(String fileName){
        ArrayList<Account> accountList = new ArrayList<>();

        try{
            File inputFile = new File(fileName);
            Scanner in = new Scanner(inputFile);
            String sentence = "";

            while(in.hasNextLine()){
                //get next line
                sentence = in.nextLine();
                if(Constants.DEBUG) System.out.println(sentence);
                Account account = processLineToGetAccount(sentence);
                accountList.add(account);
            }
            if(Constants.DEBUG) System.out.println("Done read next line");
            in.close();
        }
        catch(FileNotFoundException exception){
            System.out.println("FILE NOT FOUND.");
        }
        return accountList;
    }

    /**
     * to process the reading sentence and break down into account username and password
     * @param sentence the reading sentence
     * @return the created account
     */
    public Account processLineToGetAccount(String sentence){
        Account account = new Account();
        String[] arrayString = sentence.split(" ");

        String username = arrayString[0];
        String password = arrayString[1];
        if(!account.createAccount(username,password)){
            System.out.println("The Account is invalid!");
            System.out.println("username: " + username + ", password: " + password);
        }
        return account;
    }

    /**
     * to read the full map information from the file
     * @param fileName the reading filename
     * @return the two dimension Grid array (the full map)
     */
    public Grid[][] readCharacterMapFile(String fileName){
        Grid[][] twoDGridList = null;

        try{
            File inputFile = new File(fileName);
            Scanner in = new Scanner(inputFile);
            String sentence = in.nextLine();
            String[] rowcol = sentence.split(" ");
            _row = Integer.parseInt(rowcol[0]);
            _col = Integer.parseInt(rowcol[1]);
            twoDGridList = new Grid[_row][];

            int rowNum = 0;
            while(in.hasNextLine()){
                //get next line
                sentence = in.nextLine();
                if(Constants.DEBUG) System.out.println(sentence);
                Grid[] oneRow = processLineToGetRow(sentence,_col * rowNum);
                twoDGridList[rowNum] = oneRow;
                rowNum++;
            }
            in.close();
        }
        catch(FileNotFoundException exception){
            System.out.println("FILE NOT FOUND.");
        }
        return twoDGridList;
    }

    /**
     * to process the reading line to get a row of the map
     * @param sentence the reading line
     * @param startIndex the start index of the current row
     * @return a Grid array with the current row
     */
    public Grid[] processLineToGetRow(String sentence,int startIndex){
        Grid[] rowGrid = new Grid[_col];
        int length = sentence.length();
        for(int i=0;i<length;i++){
            Grid newGrid = new Grid(startIndex+i,String.valueOf(sentence.charAt(i)));
            rowGrid[i] = newGrid;
        }
        return rowGrid;
    }

    /**
     * get the total row number of the reading full map
     * @return the total row number
     */
    public int get_row(){return _row;}

    /**
     * get the total col number of the reading full map
     * @return the total col number
     */
    public int get_col(){return _col;}
}
