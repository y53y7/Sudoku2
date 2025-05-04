import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class MySudokuBoard { 
   private int [][] board;
   
   public MySudokuBoard (String fileName) throws FileNotFoundException {
      board = new int [9][9];
      File f = new File(fileName);
      Scanner s = new Scanner (f);
      
      for (int r= 0; r<9; r++ ) {
         if (s.hasNextLine()) {
            String line = s.nextLine();
            for (int c=0; c< 9; c++){
               char num = line.charAt(c);
               
               if(num=='.') {
                  board[r][c]=0;
               } else {
                  board[r][c]= num-'0';
               }
            }
         }
         
      }
   }
      
   private boolean addNum() {
      Set<Integer> valSet = new TreeSet<>();
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] != 0) { valSet.add(board[r][c]); }
         }
      }
      return true;
   }
   
   
   private boolean helpRows () {
      Set<Integer> valSet = new TreeSet<>();
          
      for(int r = 0; r < board.length; r++) {
         if(!valSet.contains(board[r][0])) { valSet.add(board[r][0]); }   
      }      
      if(valSet.size() == 9) { return true; }
      return false;
   }
   
   private boolean helpCol () {
      Set<Integer> valSet = new TreeSet<>();
      
      for(int c = 0; c < board[0].length; c++) {
            if(!valSet.contains(board[0][c])) { valSet.add(board[0][c]); }
      }      
      if(valSet.size() == 9) { return true; }
      return false;
   }
   
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   private boolean helpMini(int[][] mini) {
      Set<Integer> miniSet = new TreeSet<>();
      for(int r = 0; r < mini.length; r++) {
         for(int c = 0; c < mini[0].length; c++) {
            if(mini[r][c] != 0) { miniSet.add(mini[r][c]); }
         }
      }
      
      if(miniSet.size() == 9) { return true; }
      return false;
   }
   
   public boolean isValid() {
      if(!addNum() || !helpRows() || !helpCol()) { 
         return false; 
      }
      for (int i = 1; i <= 9; i++) {
         if (!helpMini(miniSquare(i))) {
            return false;
         }
      }
      return true;
   }
   
   public boolean isSolved() {
      Map<Integer, Integer> namehi = new TreeMap<>();
      
      for(int i = 1; i <= 9; i++) {
         namehi.put(i, 0);
      }
      
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            int value = board[r][c];
            if(value > 0 && value < 10) {
               namehi.put(value, namehi.get(value) + 1);
            }  
         }
      }
      
      for (int i = 1; i <= 9; i++) {
         if (namehi.get(i) != 9) {
            return false;
         }
      }
      
      if(isValid()) { 
         return true; }
      else { 
         return false; }
   }

   
   public String toString () {
      String result = "";
      for (int r=0;r<9;r++) {
         if (r%3 ==0) {
            result += "| - - - + - - - + - - - |\n";
         }
         for (int c=0; c<9; c++) {
            if(c%3==0) {
               result+="| ";
            }
            if (board[r][c]==0) {
               result += ". ";
            } else {
               result += board[r][c] + " ";
            }
         }
         result += "| \n";
         
      }
      result += "| - - - + - - - + - - - |\n";
      return result;
   }
}

/*
# PROGRAM OUTPUT
 | - - - + - - - + - - - |
 | 2 . . | 1 . 5 | . . 3 | 
 | . 5 4 | . . . | 7 1 . | 
 | . 1 . | 2 . 3 | . 8 . | 
 | - - - + - - - + - - - |
 | 6 . 2 | 8 . 7 | 3 . 4 | 
 | . . . | . . . | . . . | 
 | 1 . 5 | 3 . 9 | 8 . 6 | 
 | - - - + - - - + - - - |
 | . 2 . | 7 . 1 | . 6 . | 
 | . 8 1 | . . . | 2 4 . | 
 | 7 . . | 4 . 2 | . . 1 | 
 | - - - + - - - + - - - |
*/