package oceansim;
import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class OceanSim 
{
    public static int row = 0;
    public static int col = 0;
    public static int spots = 0;
    public static boolean [][] flags; 
    public static Random r1;
    
    public static int printDirection(int load)
    {
        
        System.out.println("                   Welcome to the Ocean Simulator.\n");
        System.out.println("Would you like to continue from a previous saved game?\n[ 1 ] Yes\n[ 2 ] No");
        load = SavitchIn.readLineInt();
        return load;
    }
    public static void switchBoard(char[][] ocean, char uObject)
    {
        int choice = 0;
        
        
        
        while (choice!= 6)
        {
            System.out.println("What would you like to do?");
            System.out.println("[ 1 ] Increase Time.\n[ 2 ] Add an object.\n[ 3 ] Remove an object.");
            System.out.println("[ 4 ] Save the game.\n[ 5 ] Load a previous save.\n[ 6 ] Quit the game.");
            choice = SavitchIn.readLineInt();
            if (choice == 1)
            {
                increaseTime(ocean);
            }
            else if (choice == 2)
            {
                addObject(ocean, uObject);
                printOcean(ocean);
            }
            else if (choice == 3)
            {
                removeObject(ocean); 
                printOcean(ocean);
            }
            else if (choice == 4)
            {
                saveGame(ocean);
            }
            else if (choice == 5)
            {
                ocean = loadGame(ocean);
            }
            else if (choice == 6)
            {
                break;
            }
            else
            {
                System.out.println("Please enter one of the options above.");
            }             
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// Time Advancement ////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void moveDirection(char[][] ocean, int r, int c)
    {
        char temp = ' ';
        int move = 0;
        int currentRow = r;
        int currentCol = c;
        temp = ocean[r][c];
        move = r1.nextInt(4);
        move = 0;////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(move == 0)//move down
        {
            r+=1;
            if (r >= row)
            {
                r-=1;
            }
        }
        else if (move == 1)//move right
        {
            c+=1;
            if(c >= col)
            {
                c-=1;
            }
        }
        else if (move == 2)//move up
        {
            r-=1;
            if(r < 0)
            {
                r+=1;
            }
        }
        else if (move == 3)//move left
        {
            c-=1;
            if(c < 0)
            {
                c+=1;
            }
        }
        
        flags[r][c]=true;
        if(ocean[r][c] == '^')//object moves to an empty spot
        {
            ocean[currentRow][currentCol] = '^';
            ocean[r][c] = temp; 
        }
        else if((ocean[r][c] == ocean[currentRow][currentCol]) && (ocean[currentRow][currentCol] != 'B') && (ocean[currentRow][currentCol] != 'J'))
        {//*******************************object that can move and breed*******************************
            if((r != currentRow) && (c != currentCol))
            {
                
            }
            else
            {
                objectHitsSameObject(ocean ,ocean[r][c] ,r ,c,currentRow,currentCol);
            }
        }
        else if((ocean[currentRow][currentCol]=='F') && (ocean[r][c] !='^'))
        {
            fishInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='S') && (ocean[r][c] !='^'))
        {
            sharkInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='B') && (ocean[r][c] !='^'))
        {
            boatInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='J') && (ocean[r][c] !='^'))
        {
            jellyInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='K') && (ocean[r][c] !='^'))
        {
            krakenInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='L') && (ocean[r][c] !='^'))
        {
            lobsterInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='A') && (ocean[r][c] !='^'))
        {
            birdInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else if((ocean[currentRow][currentCol]=='I') && (ocean[r][c] != '^'))
        {
            icebergInteractions(ocean, ocean[r][c],r ,c,currentRow,currentCol);
        }
        else
        {
            System.out.println("Error in the object interaction");
        }
    }
    public static void icebergInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        int tempRow = r;
        int tempCol = c;
        chance = r1.nextInt(100);
        if (otherObject =='F')
        {
            ocean[currentRow][currentCol] = '^';//add in if the fish cannot move, it is crushed
            
            moveDirection(ocean,r,c);
            ocean[tempRow][tempCol] = 'I';
            System.out.println("iceberg moved fish");
        }
    }
    public static void birdInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if(chance< 50)
            {
                ocean[currentCol][currentRow] = '^';//bird ate fish
                ocean[r][c] = 'A';
            }
        }
        else if(otherObject == 'K')
        {
            if(chance< 80)
            {
                ocean[currentCol][currentRow] = '^';//kraken ate bird
                ocean[r][c] = 'K';
            }
        }
        else
        {
            System.out.println("Problem in the Bird interaction");
        }
    }
    public static void lobsterInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if(chance< 25)
            {
                ocean[currentRow][currentCol] = '^';//lobster ate fish
                ocean[r][c] = 'L';
            }
            else if (chance < 75)
            {
                ocean[currentRow][currentCol] = '^'; //fish ate lobster
                ocean[r][c] = 'F';
            }
            else
            {
                ocean[currentRow][currentCol] = 'L'; //nothing happened
                ocean[r][c] = 'F';
            }
        }
        else if(otherObject == 'S')
        {
            if(chance< 25)
            {
                ocean[currentCol][currentRow] = '^';//shark eat lobster
                ocean[r][c] = 'S';
            }
        }
        else if(otherObject == 'B')
        {
            if(chance< 25)
            {
                ocean[currentCol][currentRow] = '^';//boat caught lobster
                ocean[r][c] = 'B';
            }
        }
        else if(otherObject == 'J')
        {
            if(chance< 25)
            {
                ocean[currentCol][currentRow] = '^';//lobster eats jelly
                ocean[r][c] = 'L';
            }
        }
        else if(otherObject == 'K')
        {
            if(chance< 90)
            {
                ocean[currentCol][currentRow] = '^';//kraken eat lobster
                ocean[r][c] = 'K';
            }
        }
        else
        {
            System.out.println("Problem in the Lobster interaction");
        }
    }
    public static void krakenInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats fish
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'S')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats shark
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'B')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats boat
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'J')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats jellyfish
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'L')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats lobster
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'A')
        {
            if(chance< 80)
            {
                ocean[currentRow][currentCol] = '^'; //kraken eats bird
                ocean[r][c] = 'K';
            }
        }
        else if(otherObject == 'O')
        {
            if(chance< 98)
            {
                ocean[currentRow][currentCol] = '^'; //kraken destroys oil rig
                ocean[r][c] = 'K';
            }
        }
        else
        {
            System.out.println("Problem in the KRAKEN interaction");
        }
    }
    public static void jellyInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if (chance < 30)//jelly eats fish
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'J';
            }
            else if(chance < 55)//fish eats jelly
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] ='F';
            }
            else
            {
                ocean[currentRow][currentCol] = 'J';
                ocean[r][c] ='F';
            }
        }
        else if( otherObject == 'S')
        {
            if(chance<10)
            {
                ocean[currentRow][currentCol]='^';//jelly got eaten by shark
                ocean[r][c]='S';
            }
        }
        else if( otherObject == 'K')
        {
            if(chance < 90)
            {
                ocean[currentRow][currentCol] = '^';//kraken ate jelly
                ocean[r][c] = 'K';
            }
        }
        else if ( otherObject == 'L')
        {
            if (chance < 30)
            {
                ocean[currentRow][currentCol] = '^';//jelly killed lobster
                ocean[r][c] = 'J';
            }
            else if (chance < 40)
            {
                ocean[currentRow][currentCol] = '^';//lobster killed jelly
                ocean[r][c] = 'L';
            }
            else
            {
                ocean[currentRow][currentCol] = 'J';
                ocean[r][c] = 'L';
            }
        }
        else
        {
            System.out.println("Problem in the jellyfish interaction");
        }
    }
    public static void boatInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if (chance < 75)
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'B';
                spots++;
            }
        }
        else if (otherObject =='S')//boat catches shark
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'B';
                spots++;
            }
            else if( chance < 5)//boat gets sunk by shark
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'S';
                spots++;
            }
            else
            {
                ocean[currentRow][currentCol] = 'B'; 
                ocean[r][c] = 'S';
            }
        }
        else if ( otherObject == 'I')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat wrecks into iceberg
                ocean[r][c] = 'I';
                spots++;
            }
        }
        else if ( otherObject == 'R')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat wrecks into rock
                ocean[r][c] = 'R';
                spots++;
            }
        }
        else if ( otherObject == 'J')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat catches jelly
                ocean[r][c] = 'B';
                spots++;
            }
        }
        else if ( otherObject == 'K')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat wrecks into kraken
                ocean[r][c] = 'K';
                spots++;
            }
        }
        else if ( otherObject == 'L')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat catches lobster
                ocean[r][c] = 'B';
                spots++;
            }
        }
        else if ( otherObject == 'A')
        {
            if (chance < 5)
            {
                ocean[currentRow][currentCol] = '^'; //boat crewman kill bird
                ocean[r][c] = 'B';
                spots++;
            }
        }
        else if ( otherObject == 'O')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat wrecks into oilrig
                ocean[r][c] = 'O';
                spots++;
            }
            else if (chance<50)
            {
                ocean[currentRow][currentCol] = '^';// oil rig blows up 
                ocean[r][c] = 'B';
            }
            else
            {
                ocean[currentRow][currentCol] = 'B'; //boat evades oil rig
                ocean[r][c] = 'O';
            }
        }
        else if ( otherObject == 'B')
        {
            if (chance < 25)
            {
                ocean[currentRow][currentCol] = '^'; //boat crashes into boat, one sinks
                ocean[r][c] = 'B';
                spots++;
            }
            else if (chance <50)
            {
                ocean[currentRow][currentCol] = '^'; // both boats sink
                ocean[r][c] = '^';
            }
            else
            {
                ocean[currentRow][currentCol] = 'B'; // both boats miss each other
                ocean[r][c] = 'B';
            }
        }
        else
        {
            System.out.println("Problem in the boat interaction");
        }
    }
    public static void sharkInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'F')
        {
            if (chance < 75)//shark moves to fish,shark eats fish
            {
                ocean[currentRow][currentCol] = '^'; //TEST THIS
                ocean[r][c] = 'S';
                spots++;
            }
        }
        else if (otherObject == 'B')
        {
            if (chance < 5)//shark moves to boat, boat dissappears
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'S';
                spots++;
            }
            else if (chance < 15) //boat stays in same spot and shark dissappers, boat caught shark
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'B';
                spots++;
            }
            else//nothing interesting happened
            {
                ocean[currentRow][currentCol] = 'S';
                ocean[r][c] = 'B';
            }
        }
        else if (otherObject == 'J')
        {
            if(chance<75)// shark eats jellyfish
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'S';
                spots++;
            }
        }
        else if(otherObject == 'K')
        {
            if (chance < 90) //kraken ate shark
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'K';
                spots++;
            }
        }
        else if(otherObject == 'L')
        {
            if (chance < 75)//shark ate lobster
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'S';
                spots++;
            }
        }
        else if (otherObject == 'A')
        {
            if (chance < 75)//shark ate bird 
            {
                ocean[currentRow][currentCol] = '^';
                ocean[r][c] = 'S';
                spots++;
            }
        }
        else
        {
            System.out.println("Problem in the Shark interaction");
        }
    }
    public static void fishInteractions(char[][]ocean, char otherObject, int r, int c, int currentRow,int currentCol)
    {
        int chance = 0;
        chance = r1.nextInt(100);
        if(otherObject == 'S')
        {
            if (chance < 25)//shark eat fish
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'S';
                spots++;
            }
        }
        else if (otherObject == 'B')
        {
            if(chance < 25)//boat catches fish
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'B';
                spots++;
            }
        }
        else if (otherObject == 'J')
        {
            if (chance < 10)//jellyfish ate shark
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'J';
                spots++;
            }
            else if(chance < 25)//fish ate jellyfish
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'F';
                spots++;
            }
        }
        else if (otherObject == 'K')
        {
            if (chance < 20)//kraken ate jellyfish
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'K';
                spots++;
            }
        }
        else if (otherObject == 'A')
        {
            if (chance < 75)//bird ate fish
            {
                ocean[currentRow][currentCol] = '^'; 
                ocean[r][c] = 'A';
                spots++;
            }
        }
        else
        {
            System.out.println("Problem in the Fish interaction");
        }
    }
    public static void objectHitsSameObject(char[][] ocean, char currentObject, int r, int c, int currentRow,int currentCol)
    {
        int sameObjectHit = 0;
        sameObjectHit = r1.nextInt(100);
        if(spots > 0)
        {
            if (currentObject != 'B')
            {
                if (sameObjectHit < 50)
                {
                    if (((currentObject == 'I') && (sameObjectHit < 45)) || (currentObject == 'F') || (currentObject == 'S') ||
                         (currentObject == 'J') || (currentObject == 'K') || (currentObject == 'L') || (currentObject == 'A'))
                    {
                        if((currentObject == 'I') && (sameObjectHit < 25))
                        {
                            ocean[r][c] = '^';
                            ocean[currentRow][currentCol] = '^';
                            spots+=2;       
                        }
                        else
                        {
                            while(ocean[r][c] != '^')
                            {
                                if((r+1<row) && (ocean[r+1][c] =='^'))//move down
                                {
                                    r+=1;
                                    if (r >= row)
                                    {
                                        r-=1;
                                    }
                                    spots--;
                                }
                                else if ((c+1<col) && (ocean[r][c+1] =='^'))//move right
                                {
                                    c+=1;
                                    if(c >= col)
                                    {
                                        c-=1;
                                    }
                                    spots--;
                                }
                                else if ((r-1>0) && (ocean[r-1][c] =='^'))//move up
                                {
                                    r-=1;
                                    if(r < 0)
                                    {
                                        r+=1;
                                    }
                                    spots--;
                                }
                                else if ((c-1>0) && (ocean[r][c-1] =='^'))//move left
                                {
                                    c-=1;
                                    if(c < 0)
                                    {
                                        c+=1;
                                    }
                                    spots--;
                                }
                                else
                                {
                                    //System.out.println("Error in the baby maker.");
                                }
                                ocean[r][c] = currentObject;
                                flags[r][c] = true;
                                break;
                            }
                        }
                    }
                }
            }
            else if (currentObject == 'B')
            {
                if(sameObjectHit < 25)
                {
                    ocean[r][c] = '^';
                    ocean[currentRow][currentCol] = currentObject;
                    System.out.println("The Boats crashed but one survived!");
                    spots+=1;
                }
                else if (sameObjectHit > 100)
                {
                    ocean[r][c] = '^';
                    ocean[currentRow][currentCol] = '^';
                    System.out.println("The Boats crashed and were destroyed!");
                    spots+=2;
                }
            }
        }
    }
    public static char deathChance(char object)
    {
        int death = 0;
        death = r1.nextInt(1000);
        death = 1000;////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if((object == 'F') || (object == 'S') || (object == 'J') ||(object == 'A'))
        {
            if (death < 50)
            {
                object = '^';
                spots ++;
            }
        }
        else if ((object == 'I') || (object == 'B'))
        {
            if (death < 5)
            {
                System.out.println("the death chance is ---------------------------- "+death);
                object = '^';
                spots ++;
            }
        }
        else if ((object == 'K') || (object == 'O'))
        {
            if (death < 10)
            {
                object = '^';
                spots ++;
            }
        }
        else if (object == 'L')
        {
            if (death < 100)
            {
                object = '^';
                spots ++;
            }
        }
        return object;
    }
    public static char[][] moveObject(char[][] ocean)
    {
        int moveChance = 0;
        char object =' ';
        moveChance = r1.nextInt(100);
        flags = new boolean [row][col];

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if(flags[i][j] != true)
                {
                    if((ocean[i][j] != '^') && (ocean[i][j] != 'R') && (ocean[i][j] != 'O'))
                    {
                        object = ocean[i][j];
                        ocean[i][j] = deathChance(object);
                    }       
                    if((ocean[i][j] != '^') && (ocean[i][j] != 'R') && (ocean[i][j] != 'O'))
                    {
                        if((ocean[i][j] == 'F') || (ocean[i][j] == 'S'))
                        {
                            if(moveChance < 50)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }  
                        else if (ocean[i][j] == 'I')
                        {
                            if(moveChance < 100)//////////////////////////////////////////////////////////////
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else if (ocean[i][j] == 'B')
                        {
                            if(moveChance < 80)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else if (ocean[i][j] == 'J')
                        {
                            if(moveChance < 15)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else if (ocean[i][j] == 'K')
                        {
                            if(moveChance < 85)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else if (ocean[i][j] == 'L')
                        {
                            if(moveChance < 10)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else if (ocean[i][j] == 'A')
                        {
                            if(moveChance < 90)
                            {
                                moveDirection(ocean,i,j);
                            }
                        }
                        else
                        {
                            System.out.println("error in the object move");
                        }
                    }
                    flags[i][j] = true;
                }    
            }
        }
        return ocean;
    }
    public static void increaseTime(char[][] ocean)
    {
        int timeAdvancement = 1;
        
        System.out.print("How many days would you like to increment by: ");
        timeAdvancement = SavitchIn.readLineInt();
        for(int i =0; i < timeAdvancement;i++)
        {
            ocean = moveObject(ocean);
        }
        printOcean(ocean);
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// LOAD GAME ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static char[][] loadGame(char[][] ocean)
    {
        int choice = 0;
        String loadGame = "";
        System.out.println("Which save file would you like to load from?");
        System.out.println("[ 1 ] File 1\n[ 2 ] File 2 \n[ 3 ] File 3");
        while ((choice <=0) || (choice >=4))
        {
            choice = SavitchIn.readLineInt();
            if(choice == 1)
            {
                loadGame = "./saveGame1.csv";
            }
            else if (choice == 2)
            {
                loadGame = "./saveGame2.csv";
            }
            else if (choice == 3)
            {
                loadGame = "./saveGame3.csv";
            }
        }
        char[][] test2 = new char[1][1];
        BufferedReader bufferedReader = null;
        try
        {
            String sCurrentLine;
            bufferedReader = new BufferedReader(new FileReader(loadGame));
            sCurrentLine = bufferedReader.readLine();
            String[] arrOfStr = sCurrentLine.split(",");
            row = Integer.parseInt(arrOfStr[0]);
            col = Integer.parseInt(arrOfStr[1]);
            spots = Integer.parseInt(arrOfStr[2]);
            ocean = new char[row][col];
            int j =0;
            String[] arrOfStr2;
            while ((sCurrentLine = bufferedReader.readLine())!= null)
            {
                arrOfStr2 = sCurrentLine.split(",");
                for (int i = 0; i < arrOfStr2.length; i++)
                {
                    ocean[j][i]=arrOfStr2[i].charAt(0);
                }
                j++;
            }
        }
        catch (Exception ex) 
        {
            System.out.println("Error! -loadGame- "+ex.toString());
        }
        finally
        {
            printOcean(ocean);
        }
        return ocean;
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// SAVE GAME ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void saveGame(char[][] ocean)
    {
        int choice = 0;
        String saveGame = "";
        System.out.println("Which save file would you like to save to?");
        System.out.println("[ 1 ] File 1\n[ 2 ] File 2 \n[ 3 ] File 3");
        while ((choice <=0) || (choice >=4))
        {
            choice = SavitchIn.readLineInt();
            if(choice == 1)
            {
                saveGame = "./saveGame1.csv";
            }
            else if (choice == 2)
            {
                saveGame = "./saveGame2.csv";
            }
            else if (choice == 3)
            {
                saveGame = "./saveGame3.csv";
            }
        }
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append("" +row);
            sb.append(",");
            sb.append("" +col);
            sb.append(",");
            sb.append("" +spots);
            sb.append("\n");
            for(int i = 0; i < row; i++)
            { 
               for(int j = 0; j < col; j++)
               {
                   
                  sb.append(ocean[i][j]);
                  if(j < col - 1)
                     sb.append(",");
               }
               sb.append("\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveGame));
            writer.write(sb.toString());
            writer.close();
        }
        catch (IOException e) 
        {

        }
        finally
        {
            System.out.println("\nGame successfully saved!\n");
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// REMOVE OBJECT ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void removeObject(char[][] ocean)
    {
        int choice = 0;
        System.out.println("How would you want to remove an object?");
        System.out.println("[ 1 ] Clear the board\n[ 2 ] Remove a type from the board\n[ 3 ] Remove a single object\n[ 4 ] Cancel");
        while((choice < 1) || (choice > 4))
        {
            choice = SavitchIn.readLineInt();
            if(choice == 1)
            {
                initOcean(ocean);
            }
            else if (choice == 2)
            {
                System.out.println("Which object would you like to clear?");
                clearObjectByType(ocean);

            }
            else if (choice == 3)
            {
                clearSingleObject(ocean);
            }
            else if (choice == 4)
            {
                break;
            }
            else
            {
                System.out.println("Please enter one of the options from above.");
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// CLEAR SINGLE ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void clearSingleObject(char[][] ocean)
    {
        String object = "";
        int r = 0;
        int c = 0;
        System.out.println("What are the coordinates of the object you wish to delete?\n");
        System.out.println("What row is this object on?   (number on the left side)");
        r = SavitchIn.readLineInt();
        while ((r > row) || (r <=0))
             {
                 if ((r) > row)
                 {
                     System.out.println("This number can't be higher then the number of rows: " + row);
                 }
                 else if ((r) <= 0)
                 {
                     System.out.println("This number can't be lower than or equal to 0.");
                 }
                 r = SavitchIn.readLineInt();
             } 
        System.out.println("What column is this object on?   (number on the top side)");
        c = SavitchIn.readLineInt();
        while ((c > col) || (c <=0))
        {
            if ((c) > col)
            {
                System.out.println("This number can't be higher then the number of columns: " + col);
            }
            else if ((c) <= 0)
            {
                System.out.println("This number can't be lower than or equal to 0.");
            }
            c = SavitchIn.readLineInt();
        }
        spots++;
        ocean[r-1][c-1]='^';
        
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// CLEAR BY TYPE ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void clearObjectByType(char[][] ocean)
    {
        char object = ' ';
        System.out.println("What object would you like to eradicate from the world?");
        System.out.println("[ F ]-Fish        [ S ]-Shark       [ I ]-Iceberg ");
        System.out.println("[ B ]-Boat        [ R ]-Rock        [ J ]-Jellyfish ");
        System.out.println("[ K ]-Kraken      [ L ]-Lobster     [ A ]-Bird ");
        System.out.println("                  [ O ]-Oil Rig ");
        while (!((object == 'F') || (object == 'S') || (object == 'I') || (object == 'B') || (object == 'R') ||
                 (object == 'J') || (object == 'K') || (object == 'L') || (object == 'A') || (object == 'O')))
        {
            object = SavitchIn.readLineNonwhiteChar();
            object = Character.toUpperCase(object);
            if (!((object == 'F') || (object == 'S') || (object == 'I') || (object == 'B') || (object == 'R') ||
                 (object == 'J') || (object == 'K') || (object == 'L') || (object == 'A') || (object == 'O')))
            {
                System.out.println("Please enter one of the letters above.");
            }
        }
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if (ocean[i][j]==object)
                {
                    spots++;
                    ocean[i][j] = '^';
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// ADD RANDOM ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void addRandom(char[][] ocean, char uObject)
    {
        int x = 0;
        int y = 0;
        double amount = 0;
        String object = "";
        
        System.out.println("How many " + object + " would you like to add?\n");
        amount = SavitchIn.readLineInt();
        while(amount > spots)
        {
            System.out.println("The number can not be higher than the empty spaces in the world\n The number must be lower than or equal to: " + spots);
            amount = SavitchIn.readLineInt();
        }
        for(int i=0;i<amount;i++)
        {
            spots--;
            x = r1.nextInt(row);
            y = r1.nextInt(col);
            while(ocean[x][y] != '^')
            {

                if (ocean[x][y] != '^')
                {

                    x = r1.nextInt(row);
                    y = r1.nextInt(col);

                }

            }
            ocean[x][y] = uObject;
            // when adding random, do not overwrite, fill in the empty spaces
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// ADD SPECIFIC ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void addSpecific(char[][] ocean, char uObject)
    {
        int loop = 0;
        int r = 1;
        int c = 1;
        int choice = 0;
        String object = "";
        System.out.println("How many would you like to add?");
         loop = SavitchIn.readLineInt();
         for(int i = 0; i < loop; i++)
         {
             spots--;
             System.out.print("Please enter the column you would like to insert your object into: ");
             c = SavitchIn.readLineInt();
             while (((c) > col) || ((c) <=0))
             {
                 if ((c) > col)
                 {
                     System.out.println("This number can't be higher then the number of columns: " + col);
                 }
                 else if ((c) <= 0)
                 {
                     System.out.println("This number can't be lower than or equal to 0.");
                 }
                 c = SavitchIn.readLineInt();
             }
             System.out.print("Please enter the row you would like to insert your object into: ");
             r = SavitchIn.readLineInt();
             while (((r) > row) || ((r) <=0))
             {
                 if ((r) > row)
                 {
                     System.out.println("This number can't be higher then the number of rows: " + row);
                 }
                 else if ((r) <= 0)
                 {
                     System.out.println("This number can't be lower than or equal to 0.");
                 }
                 r = SavitchIn.readLineInt();
             }    
             System.out.println("");
             if (ocean[r-1][c-1] != '^')
             {
                 spots++;
                 System.out.println("It seems there is an object already here,"
                         + "\n would you like to overwrite it?\n"
                         + "[ 1 ] Yes \n"
                         + "[ 2 ] No");
                 choice = 0;
                 while ((choice != 1) && (choice != 2))
                 {
                     choice = SavitchIn.readLineInt();
                     if(choice == 1)
                     {
                         ocean[r-1][c-1] = uObject;
                     }
                     else if (choice == 2)
                     {
                         break;
                     }
                     else
                     {
                         System.out.println("Please enter an option from above.");
                     }
                 }
             }
             else
             {
                 ocean[r-1][c-1] = uObject;
             }
         }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// NAMER ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static char namer()
    {
        String fullName = "";
        char oName = ' ';
        System.out.println("What object would you like to place in the world?");
        System.out.println("[ F ]-Fish        [ S ]-Shark       [ I ]-Iceberg ");
        System.out.println("[ B ]-Boat        [ R ]-Rock        [ J ]-Jellyfish ");
        System.out.println("[ K ]-Kraken      [ L ]-Lobster     [ A ]-Bird ");
        System.out.println("                  [ O ]-Oil Rig ");
        
        while (!((oName == 'F') || (oName == 'S') || (oName == 'I') || (oName == 'B') || (oName == 'R') ||
                 (oName == 'J') || (oName == 'K') || (oName == 'L') || (oName == 'A') || (oName == 'O')))
        {
            if (!((oName == 'F') || (oName == 'S') || (oName == 'I') || (oName == 'B') || (oName == 'R') ||
                 (oName == 'J') || (oName == 'K') || (oName == 'L') || (oName == 'A') || (oName == 'O')))
            {
                System.out.println("Please enter one of the letters above.");
            }
            oName = SavitchIn.readLineNonwhiteChar();
            oName = Character.toUpperCase(oName);
        }
        if (oName == 'F')
        {
            fullName = "Fish";
        }
        else if (oName == 'S')
        {
            fullName = "Shark";
        }
        else if (oName == 'I')
        {
            fullName = "Iceberg";
        }
        else if (oName == 'B')
        {
            fullName = "Boat";
        }
        else if (oName == 'R')
        {
            fullName = "Rock";
        }
        else if (oName == 'J')
        {
            fullName = "Jellyfish";
        }
        else if (oName == 'K')
        {
            fullName = "Kraken";
        }
        else if (oName == 'L')
        {
            fullName = "Lobster";
        }
        else if (oName == 'A')
        {
            fullName = "Bird";
        }
        else if (oName == 'O')
        {
            fullName = "Oil Rig";
        }
        else
        {
            System.out.print("------------------------------The name catcher has failed--------------------------");
        }
        System.out.println("Would you like to add the "+ fullName +"'s randomly,");
        System.out.println("or would you like to place them in certain spots?");
        return oName;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// INITIALIZER ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initOcean(char[][] ocean)
    {
        spots = 0;
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                ocean[i][j] = '^';
                if(ocean[i][j] == '^')
                {
                    spots++;
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// PRINT OCEAN ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void printOcean(char[][] ocean)
    {
        System.out.print("    ");
        for(int i = 1; i <= col; i++)
        {
            if(i < 10)
            {
                System.out.print("  " + i + " ");
            }
            else if ( i < 100)
            {
                System.out.print(" " + i + " ");
            }
            else 
            {
                System.out.print(i + " ");
            }
        }
        System.out.println("");
        for(int i = 0; i < row; i++)
        {
            if(i < 9)
            {
                System.out.print("  " + (i+1));
            }
            else if ( i < 99)
            {
                System.out.print(" " + (i+1));
            }
            else 
            {
                System.out.print( (i+1));
            }
            
            for(int j = 0; j < col; j++)
            {
                System.out.print("   " + ocean[i][j]);
            }
            System.out.println(" ");
            
        }
        System.out.println("\n***   There are " + spots + " spaces left that are free.   ***\n");
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////// ADD ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void addObject(char[][] ocean, char uObject)
    {
        int choice = 0;
        String object = "";
        uObject = namer();
        
        System.out.println("[ 1 ] Random");
        System.out.println("[ 2 ] Specific Spots");
        while ((choice != 1) && (choice != 2))
        choice = SavitchIn.readLineInt();
        if (choice == 1)
        {
            addRandom(ocean, uObject);// need to pass the char random and in specific 
        }
        else if (choice == 2)
        {
            addSpecific(ocean, uObject);
        }
        else 
        {
            System.out.println("Please enter a 1 or a 2.");
        }
    }
    public static void runSim()
    {
        int choice = 0;
        char uObject = ' ';
        int r = 0;
        int c = 0;
        char[][] ocean;
        int load =0;
        
        ocean = new char[row][col];
        load = printDirection(load);
        if (load != 1)
        {
            System.out.println("Please enter the number of rows, ");
            System.out.println("the number must be greater than 0, less than 999.");
            while ((r >= 1000) || (r <= 0))
            {
                r = SavitchIn.readLineInt();
            }
            row = r;
            System.out.println("Please enter the number of columns, ");
            System.out.println("the number must be greater than 0, less than 999.");
            while ((c >= 1000) || (c <= 0))
            {
                c = SavitchIn.readLineInt();
            }
            col = c;
            System.out.println("");
            ocean = new char[row][col];// fix this
            initOcean(ocean);
            printOcean(ocean);
        }
        else
        {
            ocean = loadGame(ocean);
        }
        
        
        switchBoard(ocean, uObject);
    }
    
    public static void main(String[] args) 
    {
        r1 = new Random();
        runSim();
    }
    
}
