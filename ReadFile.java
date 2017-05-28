import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Read Files and return all the necessary elements for further coding
 */

public class ReadFile {
    private int _row;
    private int _col;

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

    public Grid[] processLineToGetRow(String sentence,int startIndex){
        Grid[] rowGrid = new Grid[_col];
        int length = sentence.length();
        for(int i=0;i<length;i++){
            Grid newGrid = new Grid(startIndex+i,String.valueOf(sentence.charAt(i)));
            rowGrid[i] = newGrid;
        }
        return rowGrid;
    }

    public int get_row(){return _row;}

    public int get_col(){return _col;}
}
