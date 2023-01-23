//===================================================================
//SaveDesmond13
//Jacob Brian Felushko.
//January 20th, 2023.
//Java, version 1.8.
//===================================================================
//Problem Definition - Design a program that will permit the player to interact with a 2Dimensional Array, gaining points and interacting with objects.
//I - Select program function, input integer or string type data to interact with UFP's displayed.
//O - UFP's. gameBoard type int[][] values display. String based graphics.
//P - Determine changes in player position based on string type input. Generate controled values of MultiDimension array type Integer. Store all previous values of player position in a secondary MultiDimensional ArrayList. Use boolean type varriables to check when certain events have occured. Execute looping, selection and method calls. Utilise Try-Catch statements to limit user inputs.
//===================================================================
import java.io.*;   //Allow access to objects within the IO library
import java.util.Random;    //Allow access to object random
import java.util.ArrayList;     //Allow usage of data type ArrayList 
import java.util.Arrays;    //Allow usage of commands within the Arrays library
public class SaveDesmond {    //Start of class SaveDesmond13
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    //static Declaration and instantiation of a BufferedReader object.
	static Random rng = new Random();   //static Declaration and instatiation of a Random object
    static SaveDesmond sD = new SaveDesmond();  //static Declaration and instatiation of a class object
/*Main method:
 * This procedural method is called automatically and is used to organize the calling of other meedthods defined in the class
 * 
 * List of local Variables  - Let GRID_SIZE (data type int) represent the length of gameBoard (data type int[][]).
//                          - Let gameBoard (data type int[][]) represent the playable area.
//                          - Let ZOMBIE, DESMOND, PLAYER, SANDPIT, WALL, POWERUP, and EMPTY (data type int constant) represent each objects numerical value on gameBoard (data type int[][]).
//                          - Let turnCount (data type int) represent the number of turns the player has completed.
//                          - Let playerAction (data type int) represent the numerical signifigants of the players input within the UFP's.
//                          - Let totalZombies, zombiesLeft, totalFalls and totalWalls (data type int) represent the game changes made at result of the player.
//                          - Let score (data type int) represent a value used to show progress.
//                          - Let difficulty (data type int) represent a value used in selection to determine other int type values.
//                          - Let startAction (data type int) represent a value used to collect the first user input.
//                          - Let startGame (data type int) represent a value used within a while loop to allow for multiple executions of the program.
//                          - Let playerName (data type String) represent a prefered form of identification throughout the program.
//                          - Let radarResponse (data type String) represent the returned value of method desondRadar; used in a UFP.
//                          - Let zomRadar (data type String) represent the returned value of method zombieRadar; used in a UFP.
//                          - Let playerChar (data type char) represent the first character in inputted value playerName(data type String).
//                          - Let alpha (data type String[]) represent the first 15 letters of the english alphabet.
//                          - Let win, alive, hasDesmond, atSpawn, showDesmond, showZombies, moved, moveDes, moveZom, giveUp, cheatWin, hasPowerUp and usedCheats (data type boolean) represent true or false checks used in method calls. 
//                          - Let playerMoves (data type ArrayList<ArrayList<Integer>>) represent a track record of previous locations of the PLAYER (data type int constant) has been on gameBoard (data type int[][]).
@param args <type String>
@throws IOException, InterruptedException
@return void
*/
    public static void main(String[] args)throws IOException, InterruptedException{     //Main method header
        //declaration of variables
        final int GRID_SIZE = 15;
        int gameBoard[][]=new int[GRID_SIZE][GRID_SIZE];
		final int ZOMBIE = 2, DESMOND =3, PLAYER=1, SANDPIT=8, WALL=9, POWERUP=10, EMPTY=0;
        int turnCount=0, playerAction=10, playerHealth=3, totalZombies=0, zombiesLeft=0, score=0, difficulty=0, startAction, startGame=0, totalFalls=0, totalWalls=0;
        String playerName=" ", radarResponse, zomRadar;
        char playerChar;
        String[] alpha={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
        boolean win=false, alive=true, hasDesmond=false, atSpawn, showDesmond=false, showZombies=false, moved=false, moveDes=false, moveZom=false, giveUp=false, cheatWin=false, hasPowerUp=false, usedCheats=false;
        ArrayList<ArrayList<Integer>> playerMoves = new ArrayList<ArrayList<Integer>>();

        sD.menuArt();
        while(startGame!=2){ //while loop used to permit players to replay or view multiple options before playing
        	//resetting variables to ensure replay is fresh
            score=0;
            difficulty=0;
        	turnCount=0;
        	playerHealth=3;
        	win=false;
        	alive=true;
        	hasDesmond=false;
        	giveUp=false;
            hasPowerUp=false;
        	playerMoves.clear();
            while(startGame==0){
                startAction=sD.startMenu();
                switch (startAction) {
                    case 1:
                        startGame=1;
                        break;
                    case 2:
                        difficulty=sD.setDifficulty();
                        break;
                    case 3:
                        sD.howToPlay();
                        break;
                    case 4:
                        startGame=2;
                        break;
                    default:
                        break;
                }
            }
            if(startGame==1){
                sD.gameExplain();
                sD.howToPlay();
                Thread.sleep(1000);
                if(difficulty==0){
                    difficulty=sD.setDifficulty();
                }
                if(difficulty==1){      //changes varriables based on value of difficulty <type int>
                    totalZombies=7;
                    totalFalls=3;
                    totalWalls=3;
                    showDesmond=true;
                    showZombies=true;
                    moveDes=false;
                    moveZom=false;
                }
                else if(difficulty==2){     //changes varriables based on value of difficulty <type int>
                    totalZombies=14;
                    totalFalls=5;
                    totalWalls=4;
                    showDesmond=false;
                    showZombies=false;
                    moveDes=true;
                    moveZom=false;
                }
                else{       //changes varriables based on value of difficulty <type int>
                    totalZombies=28;
                    totalFalls=7;
                    totalWalls=5;
                    showZombies=false;
                    showDesmond=false;
                    moveDes=true;
                    moveZom=true;
                }
                playerName=sD.getName();
                playerChar=playerName.charAt(0); //find the first character of String playerName and uses it to represent player location
                System.out.println("\nThis is the game board:");
                gameBoard=sD.fillBoard(gameBoard);
                gameBoard=sD.addObjects(gameBoard, ZOMBIE, GRID_SIZE, DESMOND, PLAYER, totalZombies, totalFalls, SANDPIT, totalWalls, WALL, POWERUP);
                while(alive!=win && giveUp==false){
                    if(hasDesmond==false){
                        if(moveDes==true){ //calls method based on boolean value
                            gameBoard=sD.desmondMovement(gameBoard, DESMOND, EMPTY);
                        }
                    }
                    if(moveZom==true){ //calls method based on boolean value
                        gameBoard=sD.zombieMovement(gameBoard, ZOMBIE, DESMOND, PLAYER);
                    }
                    if(turnCount==0){ //starts the trackMoves <type ArrayList<ArrayList<Integer>>> arraylist and sets position 0 to the starting point
                        playerMoves=sD.trackMoves(playerMoves, gameBoard, turnCount, alpha, PLAYER);
                        turnCount++;
                    }
                    sD.displayBoard(gameBoard, alpha,PLAYER, playerChar, ZOMBIE, DESMOND, playerMoves, showDesmond, showZombies);
                    radarResponse=sD.desmondRadar(gameBoard, DESMOND, PLAYER, hasDesmond);
                    zomRadar=sD.zombieRadar(gameBoard, ZOMBIE, PLAYER);
                    gameBoard=SaveDesmond.playerMovement(gameBoard, PLAYER, playerName, alpha, turnCount, playerMoves, playerAction, radarResponse, zomRadar, ZOMBIE, playerHealth, DESMOND, score, hasDesmond, SANDPIT, WALL, hasPowerUp);
                    moved=true;
                    hasDesmond=sD.checkDes(gameBoard, DESMOND, hasDesmond);
                    for(int x=0;x<gameBoard.length;x++){        //searches the code for any new elements, if found, change boolean varriables and set gameBoard[x][y] to 0 ensuring data isn't displayed
                        for(int y=0; y<gameBoard[x].length; y++){       //this search permits much more information to be returned from playerMovement methods than it seems 
                            if(gameBoard[x][y]==20){
                                moved=false;
                                gameBoard[x][y]=0;
                            }
                            if(gameBoard[x][y]==5){
                                showDesmond=true;
                                moved=false;
                                gameBoard[x][y]=0;
                            }
                            if(gameBoard[x][y]==7){
                                giveUp=true;
                            }
                            if(gameBoard[x][y]==6){
                                usedCheats=true;
                                win=true;
                            }
                        }
                    }
                    if(giveUp==false && usedCheats==false){     //checks to ensure that exit conditions haven't been met
                        Thread.sleep(500);
                        if(hasPowerUp==false){      //runs block based on boolean value
                            hasPowerUp=sD.checkPU(gameBoard, hasPowerUp, POWERUP);
                            if(hasPowerUp==true){
                                sD.showPU();
                            }
                        }
                        zombiesLeft=sD.countZom(gameBoard, ZOMBIE, totalZombies, zombiesLeft);
                        if(zombiesLeft<totalZombies){
                            score+=100;
                            totalZombies=zombiesLeft;
                        }
                        if(moved==true){ //runs block based on boolean value
                            playerHealth=sD.healthCheck(playerHealth, playerMoves, turnCount);
                        }
                        alive=sD.checkDead(playerHealth, alive);
                        atSpawn=sD.checkSpawn(hasDesmond, gameBoard, playerMoves, PLAYER);
                        win=sD.checkWin(gameBoard, win, alive, hasDesmond, atSpawn);
                        if(win!=true){ //runs block based on boolean value
                            turnCount++;
                        }
                    }
                }
                score=sD.endings(giveUp, cheatWin, alive, score, gameBoard, playerName, difficulty, usedCheats, turnCount);
                sD.scoreDisplay(score, playerName);
                startGame=0;
            }
            else{
                System.out.println("You have chosen to quit the program.");
            }
        }
        
        //endings
        System.out.println("\n\n\n");
        System.out.println("\n\nThank you for playing!");
        sD.credits();
	}
	/*fillBoard functional method:
     * This functional method takes varriable gameBoard and fills it completely with 0 values
     * @param gameBoard -- for data storage <type int[][]>
     * @return gameBoard -- now filled with values;
     */
    private int[][] fillBoard(int[][] gameBoard){
		for(int x=0;x<gameBoard.length;x++){
			for(int y=0;y<gameBoard[x].length;y++){
				gameBoard[x][y]=0;
			}
		}
		return gameBoard;
	}
    /*displayBoard void method:
     * This procedural method reads param to determind the main display
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        alpha --  stores first 15 letters in the english alphabet <type String[]>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        playerChar -- value used to represent the first letter of playerName <type char>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        showDesmond -- value used to determine output <type boolean>
     *        showZombies -- value used to determine output <type boolean>
     * 
     *@return void
     */
    private void displayBoard(int[][] gameBoard, String[] alpha, final int PLAYER, char playerChar, final int ZOMBIE, final int DESMOND, ArrayList<ArrayList<Integer>> playerMoves, boolean showDesmond, boolean showZombies){
    	for(int x=0;x<gameBoard.length;x++){
            System.out.println("\n----|-----------------------------------------------------------------------------------------|");
            System.out.printf("%-2s",alpha[x]);
            System.out.printf("%3s","|");
            for(int y=0;y<gameBoard[x].length;y++){
            	if(x==playerMoves.get(0).get(0) && y==playerMoves.get(0).get(1) && gameBoard[x][y]!=PLAYER){
                    System.out.printf("%3s", "E");
                }
                else if(gameBoard[x][y]==0){
            		System.out.printf("%3s", " ");
            	}
                else if(gameBoard[x][y]==5 || gameBoard[x][y]==6 ||gameBoard[x][y]==7||gameBoard[x][y]==8||gameBoard[x][y]==9||gameBoard[x][y]==10){
                    System.out.printf("%3s", " ");
                }
                else if(gameBoard[x][y]==ZOMBIE){
                    if(showZombies==true){
                        System.out.printf("%3s", "Z");
                    }
                    else{
                        System.out.printf("%3s", " ");
                    }
                }
                else if(gameBoard[x][y]==DESMOND){
                    if(showDesmond==true){
                        System.out.printf("%3s", "D");
                    }
                    else{
                        System.out.printf("%3s", " ");
                    }
                }
            	else if(gameBoard[x][y]==PLAYER){
            		System.out.printf("%3s",playerChar);
            	}
            	else{
            		System.out.printf("%3s",gameBoard[x][y]);
            	}
                System.out.printf("%3s","|");
            }
        }
        System.out.println("\n----|-----------------------------------------------------------------------------------------|");
        System.out.println("____|__________________________________________________________________________________________");
        System.out.print("    ");
        for(int x=0; x<gameBoard.length;x++){
            System.out.printf("%3s",x+1);
            System.out.printf("%3s","|");

        }
    }
	/*addObjects functional method:
     * This functional method is used to fill the board with randomized but logical values representing interactions
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        GRID_SIZE -- value used to work in for loops <type int constant>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        totalZombies -- value used as a limiting factor in for loops <type int>
     *        totalFalls -- value used as a limiting factor in for loops <type int>
     *        SANDPIT -- value used to represent a different interaction <type int constant>
     *        totalWalls -- value used as a limiting factor in for loops <type int>
     *        WALL -- value used to represent a different interaction <type int constant>
     *        POWERUP -- value used to represent a different interaction <type int constant>
     * 
     * @return the new value of gameBoard <type int[][]>
     */
    private int[][] addObjects(int[][] gameBoard, final int ZOMBIE, final int GRID_SIZE, final int DESMOND, final int PLAYER, int totalZombies, int totalFalls, final int SANDPIT, int totalWalls, final int WALL, final int POWERUP){
		int x=0, y=0;
		for(int i=0;i<totalZombies; i++){
			x=rng.nextInt(gameBoard.length);
            y=rng.nextInt(gameBoard.length);
            gameBoard[x][y]=ZOMBIE;
		}
        for(int i=0;i<totalFalls; i++){
            while(true){
                x=rng.nextInt(gameBoard.length);
                y=rng.nextInt(gameBoard.length);
                if(gameBoard[x][y]==0){
                    gameBoard[x][y]=SANDPIT;
                    break;
                }
            }
		}
        for(int i=0;i<totalWalls; i++){
            while(true){
                x=rng.nextInt(gameBoard.length);
                y=rng.nextInt(gameBoard.length);
                if(gameBoard[x][y]==0){
                    gameBoard[x][y]=WALL;
                    break;
                }
            }
		}
        while(true){
            x=rng.nextInt(gameBoard.length);
            y=rng.nextInt(gameBoard.length);
            if(gameBoard[x][y]==0){
                gameBoard[x][y]=POWERUP;
                break;
            }
        }
        while(gameBoard[x][y]!=PLAYER){
            for(int i=0;i<2;i++){
                x=rng.nextInt(gameBoard.length);
                y=rng.nextInt(gameBoard.length);
                if(i==0){
                    gameBoard[x][y]=DESMOND;
                }
                else{
                    if(gameBoard[x][y]==0)
                    gameBoard[x][y]=PLAYER;
                }
            }
        }
        return gameBoard;
	}
    /*desmondMovement functional method:
     * This functional method is used to calculate a dynamically changing location of the objective
     * 
     *@param gameBoard --  stores all game data <type int[][]>
     *       DESMOND -- value used to represent the objective on the board <type int constant>
     *       EMPTY -- value used to represent a void or 0 <type int constant>
     * 
     *@return the new value of gameBoard <type int[][]>
     */
    private int[][] desmondMovement(int[][]gameBoard,final int DESMOND, final int EMPTY){
        int desmondX=0, desmondY=0, desmondAction=0;
        for(int x=0;x<gameBoard.length;x++){
            for(int y=0;y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==DESMOND){
                    desmondX=x;
                    desmondY=y;
                }
            }
        }
        while(true){
            try{
                desmondAction=rng.nextInt(8);//high param to ensure changes in gameBoard only occur 50% of the time
                switch(desmondAction){
                    case 1:
                        if(gameBoard[desmondX][desmondY+1]+gameBoard[desmondX][desmondY] ==3){
                            gameBoard[desmondX][desmondY+1]=DESMOND;
                            gameBoard[desmondX][desmondY]=EMPTY;
                        }
                        break;
                    case 2:
                    	if(gameBoard[desmondX][desmondY-1]+gameBoard[desmondX][desmondY] ==3){
                            gameBoard[desmondX][desmondY-1]=DESMOND;
                            gameBoard[desmondX][desmondY]=EMPTY;
                        }
                        break;
                    case 3:
                    	if(gameBoard[desmondX-1][desmondY]+gameBoard[desmondX][desmondY] ==3){
                            gameBoard[desmondX-1][desmondY]=DESMOND;
                            gameBoard[desmondX][desmondY]=EMPTY;
                        }
                        break;
                    case 4:
                    	if(gameBoard[desmondX+1][desmondY]+gameBoard[desmondX][desmondY] ==3){
                            gameBoard[desmondX+1][desmondY]=DESMOND;
                            gameBoard[desmondX][desmondY]=EMPTY;
                        }
                        break;
                    default:
                        break;
                }
                break;
            }
            catch(IndexOutOfBoundsException e){
                gameBoard[desmondX][desmondY]=DESMOND;
            }
        }
        return gameBoard;
    }
    /*playerMovement functional method:
     * This functional method is used to organize the calls to other methods, allowing for player interactions and movement within the board
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        playerName -- value recieved from the player to represent themselves <type String>
     *        alpha --  stores first 15 letters in the english alphabet <type String[]>
     *        turnCount -- value used to track how many turns the player has taken <type int>
     *        playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        playerAction -- value used in selection to determine processing reaction to player input <type int>
     *        radarResponse -- stores the message recieved from calculations for distance between player and objective <type String>
     *        zomDar -- stores the message recieved from calculations for distance between player and enemy <type String>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        playerHealth -- value used to determine if the player has had too many bad encounters <type int>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        score -- value used to show progress <type int>
     *        hasDesmond -- value determines if the player has collected the main objective <type boolean>
     *        SANDPIT -- value used to represent a different interaction <type int constant>
     *        WALL -- value used to represent a different interaction <type int constant>
     *        hasPowerUp -- value used to determine selection within later UFP <type boolean>
     *        
     *@throws IOException, InterruptedException
     *        
     *@return new value of gameBoard <type int[][]>      
     */
    static private int[][] playerMovement(int[][] gameBoard, final int PLAYER, String playerName, String[] alpha, int turnCount, ArrayList<ArrayList<Integer>> playerMoves, int playerAction, String radarResponse, String zomRadar, final int ZOMBIE, int playerHealth, final int DESMOND, int score, boolean hasDesmond, final int SANDPIT, final int WALL, boolean hasPowerUp)throws IOException, InterruptedException{
        int playerX=0, playerY=0;
        String directionMoved= " ";
        int battleOutcome=0, i, k;
        double distance=0;
        for(int x=0;x<gameBoard.length;x++){
            for(int y=0;y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==PLAYER){
                    playerX=x;
                    playerY=y;
                }
            }
        }
        while(true){
            try{
                playerAction=sD.getAction(turnCount, playerAction, playerX, playerY, alpha, radarResponse, zomRadar, playerHealth, score);
                if(playerAction==5){
                    gameBoard=sD.devMenu(gameBoard, playerName, playerX, playerY);
                }
                else if(playerAction==6){
                    while(true){
                        i=rng.nextInt(gameBoard.length);
                        k=rng.nextInt(gameBoard.length);
                        distance=Math.sqrt(Math.pow((k-playerY),2)+Math.pow((i-playerX),2));
                        if(gameBoard[i][k]==0){
                            if(distance>3){
                                gameBoard[i][k]=7;
                                break;
                            }
                        }
                    }
                }
                else{
                    gameBoard[playerX][playerY]=4;
                }
                switch(playerAction){
                    case 1:
                        directionMoved="East";
                        if(gameBoard[playerX][playerY+1]==DESMOND || gameBoard[playerX][playerY+1]==ZOMBIE){
                            if(gameBoard[playerX][playerY+1]==DESMOND){
                                battleOutcome=sD.catchDesmond(playerName, battleOutcome);
                            }
                            else{
                                battleOutcome=sD.fightingMiniGame(playerName, battleOutcome, hasPowerUp);
                            }
                            if(battleOutcome==0){
                                gameBoard[playerX][playerY]=PLAYER;
                                break;
                            }
                            else if(battleOutcome==1){
                                gameBoard[playerX][playerY+1]=PLAYER;
                                playerY=playerY+1;
                                break;
                            }
                            else{
                                gameBoard[playerX][playerY+1]=PLAYER;
                                playerY=playerY+1;
                                gameBoard=sD.cowardResponse(gameBoard, ZOMBIE, PLAYER);
                                break;
                            }
                        }
                        else if(gameBoard[playerX][playerY+1]==SANDPIT){
                            gameBoard=sD.pitFall(playerName, gameBoard, playerMoves, PLAYER);
                            gameBoard[playerX][playerY]=DESMOND;
                        }
                        else if(gameBoard[playerX][playerY+1]==WALL){
                            gameBoard=sD.wall(gameBoard);
                            gameBoard[playerX][playerY]=PLAYER;
                        }
                        else{
                            gameBoard[playerX][playerY+1]=PLAYER;
                            playerY=playerY+1;
                        }
                        break;
                    case 2:
                        directionMoved="West";
                        if(gameBoard[playerX][playerY-1]==DESMOND || gameBoard[playerX][playerY-1]==ZOMBIE){
                            if(gameBoard[playerX][playerY-1]==DESMOND){
                                battleOutcome=sD.catchDesmond(playerName, battleOutcome);
                            }
                            else{
                                battleOutcome=sD.fightingMiniGame(playerName, battleOutcome, hasPowerUp);
                            }
                            if(battleOutcome==0){
                                gameBoard[playerX][playerY]=PLAYER;
                                break;
                            }
                            else if(battleOutcome==1){
                                gameBoard[playerX][playerY-1]=PLAYER;
                                playerY=playerY-1;
                                break;
                            }
                            else{
                                gameBoard[playerX][playerY-1]=PLAYER;
                                playerY=playerY-1;
                                gameBoard=sD.cowardResponse(gameBoard, ZOMBIE, PLAYER);
                                break;
                            }
                        }
                        else if(gameBoard[playerX][playerY-1]==SANDPIT){
                            gameBoard=sD.pitFall(playerName, gameBoard, playerMoves, PLAYER);
                            gameBoard[playerX][playerY]=DESMOND;
                        }
                        else if(gameBoard[playerX][playerY-1]==WALL){
                            gameBoard=sD.wall(gameBoard);
                            gameBoard[playerX][playerY]=PLAYER;
                        }
                        else{
                            gameBoard[playerX][playerY-1]=PLAYER;
                            playerY=playerY-1;
                        }
                        break;
                    case 3:
                        directionMoved="North";
                        if(gameBoard[playerX-1][playerY]==DESMOND || gameBoard[playerX-1][playerY]==ZOMBIE){
                            if(gameBoard[playerX-1][playerY]==DESMOND){
                                battleOutcome=sD.catchDesmond(playerName, battleOutcome);
                            }
                            else{
                                battleOutcome=sD.fightingMiniGame(playerName, battleOutcome, hasPowerUp);
                            }
                            if(battleOutcome==0){
                                gameBoard[playerX][playerY]=PLAYER;
                                break;
                            }
                            else if(battleOutcome==1){
                                gameBoard[playerX-1][playerY]=PLAYER;
                                playerX=playerX-1;
                                break;
                            }
                            else{
                                gameBoard[playerX-1][playerY]=PLAYER;
                                playerX=playerX-1;
                                gameBoard=sD.cowardResponse(gameBoard, ZOMBIE, PLAYER);
                                break;
                            }
                        }
                        else if(gameBoard[playerX-1][playerY]==SANDPIT){
                            gameBoard=sD.pitFall(playerName, gameBoard, playerMoves, PLAYER);
                            gameBoard[playerX][playerY]=DESMOND;
                        }
                        else if(gameBoard[playerX-1][playerY]==WALL){
                            gameBoard=sD.wall(gameBoard);
                            gameBoard[playerX][playerY]=PLAYER;
                        }
                        else{
                            gameBoard[playerX-1][playerY]=PLAYER;
                            playerX=playerX-1;
                        }
                        break;
                    case 4:
                        directionMoved="South";
                        if(gameBoard[playerX+1][playerY]==DESMOND || gameBoard[playerX+1][playerY]==ZOMBIE){
                            if(gameBoard[playerX+1][playerY]==DESMOND){
                                battleOutcome=sD.catchDesmond(playerName, battleOutcome);
                            }
                            else{
                                battleOutcome=sD.fightingMiniGame(playerName, battleOutcome, hasPowerUp);
                            }
                            if(battleOutcome==0){
                                gameBoard[playerX][playerY]=PLAYER;
                                break;
                            }
                            else if(battleOutcome==1){
                                gameBoard[playerX+1][playerY]=PLAYER;
                                playerX=playerX+1;
                                break;
                            }
                            else{
                                gameBoard[playerX+1][playerY]=PLAYER;
                                playerX=playerX+1;
                                gameBoard=sD.cowardResponse(gameBoard, ZOMBIE, PLAYER);
                                break;
                            }
                        }
                        else if(gameBoard[playerX+1][playerY]==SANDPIT){
                            gameBoard[playerX][playerY]=DESMOND;
                            gameBoard=sD.pitFall(playerName, gameBoard, playerMoves, PLAYER);
                        }
                        else if(gameBoard[playerX+1][playerY]==WALL){
                            gameBoard=sD.wall(gameBoard);
                            gameBoard[playerX][playerY]=PLAYER;
                        }
                        else{
                            gameBoard[playerX+1][playerY]=PLAYER;
                            playerX=playerX+1;
                        }
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("\nPlease enter a valid numerical input from the choices above.");
                        break;
                }
                break;
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("You cannot move any farther "+directionMoved);
                gameBoard[playerX][playerY]=PLAYER;
            }
        }
        //loseCondition

        playerMoves=sD.trackMoves(playerMoves, gameBoard, turnCount, alpha, PLAYER);

        return gameBoard;
    }
    /*getAction functional method:
     * This functional method is used to read user input and return a corresponding value. Displays UFP.
     * 
     * @param turnCount -- value used to track how many turns the player has taken <type int>
     *        playerAction -- value used in selection to determine processing reaction to player input <type int>
     *        playerX -- value corresponds to half of the players location at the start of that turn <type int>
     *        playerY -- value corresponds to half of the players location at the start of that turn <type int>
     *        alpha --  stores first 15 letters in the english alphabet <type String[]>
     *        radarResponse -- stores the message recieved from calculations for distance between player and objective <type String>
     *        zomDar -- stores the message recieved from calculations for distance between player and enemy <type String>
     *        playerHealth -- value used to determine if the player has had too many bad encounters <type int>
     *        score -- value used to show progress <type int>
     *
     * @throws IOException
     * 
     * @returns value of playerAction <type int>
     */
    private int getAction(int turnCount, int playerAction, int playerX, int playerY, String[] alpha, String radarResponse, String zomRadar, int playerHealth, int score)throws IOException{//bring in playerMoves Arraylist --- bring in direction moved(display last)
    	String readInput=" ";
        final String NORTH="W", SOUTH="S", WEST="A", EAST="D"; 
        System.out.println("\n\n+---------------------------------------+\t+---------------------------------------+");                                        
		System.out.println("|Turn #"+turnCount+", Current Position: ("+(alpha[playerX])+", "+(playerY+1)+")\t|\t|Current Score: "+score+"\t\t\t|");       
    	System.out.println("|---------------------------------------|\t|---------------------------------------|");
    	System.out.printf("|%-15s %20s","\tWhat Action Will You Take?\t|","\t|Desmond Radar: ["+radarResponse+"]\t\t\t|");
    	System.out.println("\n|---------------------------------------|\t|---------------------------------------|");
    	System.out.printf("|%10s %10s %s", "[D] Move East\t\t", "[A] Move West\t|","\t|Zombie Radar: ["+zomRadar+"]\t\t\t|");
    	System.out.println("\n|\t\t\t\t\t|\t|---------------------------------------|");
    	System.out.printf("|%10s %10s %s", "[W] Move North\t\t", "[S] Move South |","\t|Health Indicator:\t/\\_/\\\t\t|");
    	System.out.println("\n|\t\t\t\t\t|\t|\t\t\t\\ "+playerHealth+" /\t\t|");
        System.out.printf("|%10s %10s %5s %s", "5. Dev Mode\t\t", "6. Give Up","|","\t|\t\t\t \\_/\t\t|");//dev mode options will happen next turn, please enter a movement 
    	System.out.println("\n+---------------------------------------+\t+---------------------------------------+");
        System.out.println("Please enter an input from the listed selection.");
    	while(true){
            try{
                readInput=br.readLine();
                readInput=readInput.toUpperCase();
                if(readInput.equals(EAST)){
                    playerAction=1;
                    break;
                }
                else if(readInput.equals(WEST)){
                    playerAction=2;
                    break;
                }
                else if(readInput.equals(SOUTH)){
                    playerAction=4;
                    break;
                }
                else if(readInput.equals(NORTH)){
                	playerAction=3;
                    break;
                }
                else if(readInput.equals("5")){
                    playerAction=5;
                    break;
                }
                else if(readInput.equals("6")){
                    playerAction=6;
                    break;
                }
                else{
                    System.out.println("\nPlease enter an input from the listed selection.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input detected, please try again.");
            }
        }
    	return playerAction;
    }
    /*trackMoves functional method: 
     * This functional method is used to keep an infinite potential quantity of data and displaying all previous locations the player has been.
     * 
     * @param playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        gameBoard --  stores all game data <type int[][]>
     *        turnCount -- value used to track how many turns the player has taken <type int>
     *        alpha --  stores first 15 letters in the english alphabet <type String[]>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        
     * @return value of trackMoves <type ArrayList<ArrayList<Integer>>>
     */
    private ArrayList<ArrayList<Integer>> trackMoves(ArrayList<ArrayList<Integer>> playerMoves, int[][]gameBoard, int turnCount, String[] alpha, final int PLAYER){//use arraylists to track movement
        int pX=0, pY=0, displayCount=-1;
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0; y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==PLAYER){
                    pX=x;
                    pY=y;
                }
            }
        }
        playerMoves.add(new ArrayList<Integer>(Arrays.asList(pX, pY)));
        System.out.println("\n+---------------------------------------------------------------------------------------------+");
        System.out.println("Previous Locations:");
        for(int x=0; x<playerMoves.size();x++){//row
        	displayCount++;
        	if(displayCount==0){
        		System.out.print("Spawn Point: ");
        	}
        	else{
                System.out.print("Turn #"+displayCount+":");
        	}
        	for(int y=0; y<(playerMoves.get(x).size());y++){//col
                if(y==0){
                    System.out.print("(");
                    System.out.print(alpha[((playerMoves.get(x)).get(y))]);
                }
                else{
                    System.out.print(", ");
                    System.out.print(((playerMoves.get(x)).get(y))+1);
                    System.out.print(")   ");
                    if(displayCount%5==0||displayCount==0){
                    	System.out.println("");
                    }
                }
                
            }
        }
        System.out.println("\n+---------------------------------------------------------------------------------------------+");
        return playerMoves;
    }
    /*desmondRadar functional method: 
     * This method is used to calculate the distance between the player and the main objective
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        hasDesmond -- value determines if the player has collected the main objective <type boolean>
     * 
     * @return message corresponding to the distance within a range <type String>
     */
    private String desmondRadar (int[][]gameBoard, final int DESMOND, final int PLAYER, boolean hasDesmond){
        int desX=0, desY=0, pX=0, pY=0;
        double distanceDP=0;
        String radarResponse="";
        if(hasDesmond==false){
            for(int x=0; x<gameBoard.length;x++){
                for(int y=0; y<gameBoard[x].length;y++){
                    if(gameBoard[x][y]==DESMOND){
                        desX=x;
                        desY=y;
                    }
                    if(gameBoard[x][y]==PLAYER){
                        pX=x;
                        pY=y;
                    }
                }
            }
            distanceDP=Math.sqrt(Math.pow((desY-pY),2)+Math.pow((desX-pX),2));
            if(distanceDP<=1 && distanceDP>0){
                radarResponse="lava";
            }
            else if(distanceDP<=2 && distanceDP>1){
                radarResponse="hot";
            }
            else if(distanceDP>2 && distanceDP<=5){
                radarResponse="warm";
            }
            else if(distanceDP>5 && distanceDP<=7){
                radarResponse="cold";
            }
            else{
                radarResponse="icy";
            }
        }
        else{
            radarResponse="found";
        }
        
        return radarResponse;
    }
    /*zombieRadar functional method: 
     * This method is used to calculate the distance between the player and the main enemy
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        PLAYER -- value used to represent the player character <type int constant>
     *        
     * @return message corresponding to the distance within a range <type String>
     */
    private String zombieRadar (int[][]gameBoard, final int ZOMBIE, final int PLAYER){
        int zomX=0, zomY=0, pX=0, pY=0;
        double zomDistance, zomClosest=20;
        String zomRadar="";
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0;y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==PLAYER){
                    pX=x;
                    pY=y;
                }
            }
        }
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0; y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==ZOMBIE){
                    zomX=x;
                    zomY=y;
                    zomDistance=Math.sqrt(Math.pow((zomY-pY),2)+Math.pow((zomX-pX),2));
                    if(zomDistance<zomClosest){
                        zomClosest=zomDistance;
                    }
                }
            }
        }
        if(zomClosest==1){
            zomRadar="lava";
        }
        else if(zomClosest>1 && zomClosest<=2){
            zomRadar="hot";
        }
        else if(zomClosest>2 && zomClosest<=5){
            zomRadar="warm";
        }
        else if(zomClosest>5 && zomClosest<=7){
            zomRadar="cold";
        }
        else{
            zomRadar="icy";
        }
        return zomRadar;
    }
    /*fightingMiniGame functional method: 
     * This method is used to display UFP alongside collection of user input to determine value of a result
     * 
     * @param playerName -- value recieved from the player to represent themselves <type String>
     *        battleOutcome -- value used to determine the outcome from the players input
     *        hasPowerUp -- hasPowerUp -- value used to determine selection within UFP <type boolean>
     *        
     * @throws InterruptedException, IOException
     * 
     * @return battleOutcome value <type int>
     */
    private int fightingMiniGame(String playerName, int battleOutcome, boolean hasPowerUp)throws InterruptedException, IOException{
        final double HIDECHANCE=0.3;
        int fightAction=0, fightRespond=0;
        double fightchance=0.15;
        if(hasPowerUp==true){
            fightchance=0.5;
        }
        System.out.println("\n\n\n _______________________________________");
        System.out.println("|BRAINS... WANT. EAT. BRAINS            |");
        System.out.println("|                           \\           |");
        System.out.println("|                               .....   |");
        System.out.println("|                              C C  /   |");
        System.out.println("|                             /<   /    |");
        System.out.println("|              ___ __________/_#__=o    |");
        System.out.println("|             /(- /(\\_\\________   \\     |");
        System.out.println("|             \\ ) \\ )_      \\o     \\    |");
        System.out.println("|             /|\\ /|\\       |'     |    |");
        System.out.println("|                           |     _|    |");
        System.out.println("|                           /o   __\\    |");
        System.out.println("|                          / '     |    |");
        System.out.println("|                         / /      |    |");
        System.out.println("|                        /_/\\______|    |");
        System.out.println("|                       (   _(    <     |");
        System.out.println("|                        \\    \\    \\    |");
        System.out.println("|                         \\    \\    |   |");
        System.out.println("|                          \\____\\____\\  |");
        System.out.println("|                          ____\\_\\__\\_\\ |");
        System.out.println("|                        /`   /`     o\\ |");
        System.out.println("|                        |___ |_______| |");
        System.out.println("|_______________________________________|");
        System.out.println("|\tWILD ZOMBIE\t\t\t|");
        System.out.println("|\tAPPEARED!!!\t\t\t|");
        System.out.println("|_______________________________________|");
        Thread.sleep(750);
        System.out.println("+---------------------------------------+");                                        
    	System.out.printf("|%-15s","\tWhat Action Will You Take?\t|");
    	System.out.println("\n|---------------------------------------|");
    	System.out.printf("|%10s", "1. Hide ("+(HIDECHANCE*100)+"% Chance)\t\t\t|");
        System.out.printf("\n|%10s", "2. Throw Hands ("+(fightchance*100)+"% Chance)\t\t|");
    	System.out.println("\n|\t\t\t\t\t|");
    	System.out.println("+---------------------------------------+");
        while(true){
            System.out.println("Please enter a numerical input from the listed selection.");
            try{
                fightAction=Integer.parseInt(br.readLine());
                if(fightAction >0 && fightAction <3){
                    break;
                }
            }
            catch(NumberFormatException e){
                System.out.println("\nPlease enter a valid numerical input from the listed selection.");
            }
        }
        Thread.sleep(750);
        if(fightAction==1){
            fightRespond=rng.nextInt(9);
            if(fightRespond <3){//testing at 10, normal is 3
                battleOutcome=2;
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tHIDES IN A NEARBY BUSH!!!\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tGETS CONFUSED!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tLURKS NEARBY!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tTHREAT\t\t\t\t|");
                System.out.println("|\tAVOIDED!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
            }
            else{
                battleOutcome=0;
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tHIDES IN A NEARBY BUSH!!!\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tSEES YOU AND ATTACKS!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tTAKES 1 DAMAGE!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
            }
        }
        if(fightAction==2){
            if(hasPowerUp==true){
                fightRespond=rng.nextInt(6);
            }
            else{
                fightRespond=rng.nextInt(19);
            }
            if(fightRespond <3){ //testing at 20, normal is 3
                battleOutcome=1;
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tTHROWS HANDS!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tTAKES 700 DAMAGE!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tFOE\t\t\t\t|");
                System.out.println("|\tVANQUISHED!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
            }
            else{
                battleOutcome=0;
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tTHROWS HANDS!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tTAKES 0 DAMAGE!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\tZOMBIE\t\t\t\t|");
                System.out.println("|\tUSED BITE!!!\t\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
                System.out.println(" _______________________________________");
                System.out.println("|\t"+playerName+"\t\t\t\t|");
                System.out.println("|\tTAKES 1 DAMAGE!!!\t\t|");
                System.out.println("|_______________________________________|");
                System.out.println("\n\n");
                Thread.sleep(750);
            }
        }
        return battleOutcome;
    }
    /*menuArt procedural method:
     * This procedural method is used to display a nicely formatted title screen
     * 
     * @return void
     */
    private void menuArt(){
            System.out.println("     _______.     ___   ____    ____  _______     _______   _______     _______..___  ___.   ______   .__   __.  _______  ");
            System.out.println("    /       |    /   \\  \\   \\  /   / |   ____|   |       \\ |   ____|   /       ||   \\/   |  /  __  \\  |  \\ |  | |       \\ ");
            System.out.println("   |   (----`   /  ^  \\  \\   \\/   /  |  |__      |  .--.  ||  |__     |   (----`|  \\  /  | |  |  |  | |   \\|  | |  .--.  |");
            System.out.println("    \\   \\      /  /_\\  \\  \\      /   |   __|     |  |  |  ||   __|     \\   \\    |  |\\/|  | |  |  |  | |  . `  | |  |  |  |");
            System.out.println(".----)   |    /  _____  \\  \\    /    |  |____    |  '--'  ||  |____.----)   |   |  |  |  | |  `--'  | |  |\\   | |  '--'  |");
            System.out.println("|_______/    /__/     \\__\\  \\__/     |_______|   |_______/ |_______|_______/    |__|  |__|  \\______/  |__| \\__| |_______/ ");
            System.out.println("\n+-----------------------------------------------------------------------------------------------------------------------------------+");
        }
    /*credits procedural method
     * This procedural method is used to display the credits to websites borrowed and coding completed
     * 
     * @return void
     */
    private void credits(){
        System.out.println("\nThanks to https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Save%20Desmond for the title font designs.");
        System.out.println("Thanks to http://www.asciiartfarts.com/zombie.html for the zombie design.");
        System.out.println("Thanks to https://www.asciiart.eu/computers/computers for computer design.");
        System.out.println("Thanks to Jacob Felushko for all the programming.");
    }
    /*healthCheck functional method: 
     * This functional method checks if the player has moved from the location they were in last turn to determine if the player recieves damage
     * 
     * @param playerHealth -- value used to determine if the player has had too many bad encounters <type int>
     *        playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        turnCount -- value used to track how many turns the player has taken <type int>
     * 
     * @return playerHealth value <type int>
     */    
    private int healthCheck(int playerHealth, ArrayList<ArrayList<Integer>> playerMoves, int turnCount){
        if(playerMoves.get(turnCount).get(0)==playerMoves.get(turnCount-1).get(0)){
            if(playerMoves.get(turnCount).get(1)==playerMoves.get(turnCount-1).get(1)){
                    playerHealth-=1;
            }
        }
        return playerHealth;        
    }
    /*checkDead functional method:
     * This functional method does a check to see if the value of playerHealth has reached 0
     * 
     * @param playerHealth -- value used to determine if the player has had too many bad encounters <type int>
     *        alive -- true if the playerHealth value is above zero <type boolean>
     * 
     * @return alive -- true or false <type boolean>
     */
    private boolean checkDead(int playerHealth, boolean alive){
        if(playerHealth==0){
            alive=false;
        }
        return alive;
    }
    /*countZom functional method:
     * This functional method utilises a for loop and counter to determine if any zombies objects have been removed from the board
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        totalZombies -- value used as a limiting factor in for loops <type int>
     *        zombiesLeft -- value used as a counter to check how many zombie objects are left <type int>
     * 
     * @return zombiesLeft -- counter <type int>
     */
    private int countZom(int[][] gameBoard, final int ZOMBIE, final int totalZombies, int zombiesLeft){
        zombiesLeft=0;
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0; y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==ZOMBIE){
                    zombiesLeft++;
                }
            }
        }
        return zombiesLeft;
    }
    /*catchDesmond functional method: 
     * This method is used to display UFP alongside collection of user input to determine value of a result
     * 
     * @param playerName -- value recieved from the player to represent themselves <type String>
     *        battleOutcome -- value used to determine the outcome from the players input
     * 
     * @throws InterruptedException, IOException
     * 
     * @return battleOutcome value <type int>
     */
    private int catchDesmond(String playerName, int battleOutcome)throws InterruptedException, IOException{
        final double CAPTURECHANCE=0.75;
        int captureDesmond, captureRespond;
        System.out.println("\n\n\n _______________________________________");
        System.out.println("|GOO GOO GAA GAA                        |");
        System.out.println("|                   \\                   |");
        System.out.println("|\t\t            _)_         |");
        System.out.println("|\t\t         .-'(/ '-.      |");
        System.out.println("|\t\t        /    `    \\     |");
        System.out.println("|\t\t       /  -     -  \\    |");
        System.out.println("|\t\t      (`  a     a  `)   |");
        System.out.println("|\t\t       \\     ^     /    |");
        System.out.println("|\t\t        '. '---' .'     |");
        System.out.println("|\t\t        .-`'---'`-.     |");
        System.out.println("|\t\t       /           \\    |");
        System.out.println("|\t\t      /  / '   ' \\  \\   |");
        System.out.println("|\t\t    _/  /|       |\\  \\_ |");
        System.out.println("|\t\t   `/|\\` |+++++++|`/|\\` |");
        System.out.println("|\t\t        /\\       /\\     |");
        System.out.println("|\t\t        | `-._.-` |     |");
        System.out.println("|\t\t        \\   / \\   /     |");
        System.out.println("|\t\t        |_ |   | _|     |");
        System.out.println("|\t\t        | _|   |_ |     |");
        System.out.println("|\t\t        (ooO   Ooo)     |");
        System.out.println("|_______________________________________|");
        System.out.println("|\tDESMOND\t\t\t\t|");
        System.out.println("|\tAPPEARED!!!\t\t\t|");
        System.out.println("|_______________________________________|");
        Thread.sleep(750);
        System.out.println("+---------------------------------------+");                                        
    	System.out.printf("|%-15s","\tWhat Action Will You Take?\t|");
    	System.out.println("\n|---------------------------------------|");
    	System.out.printf("|%10s", "1. Capture ("+(CAPTURECHANCE*100)+"% Chance)\t\t|");
    	System.out.println("\n|\t\t\t\t\t|");
    	System.out.println("+---------------------------------------+");
        System.out.println("Please enter a numerical input from the listed selection.");
        while(true){
            try{
                captureDesmond=Integer.parseInt(br.readLine());
                if(captureDesmond==1){
                    break;
                }
                else{
                    System.out.println("\nPlease enter a numerical input from the listed selection.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("\nPlease enter a valid numerical input from the listed selection.");
            }
        }
        Thread.sleep(750);
        captureRespond=rng.nextInt(4);
        if(captureRespond<3){
            battleOutcome=1;
            System.out.println(" _______________________________________");
            System.out.println("|\t"+playerName+"\t\t\t\t|");
            System.out.println("|\tPICKS UP DESMOND!!!\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
            System.out.println(" _______________________________________");
            System.out.println("|\tDESMOND\t\t\t\t|");
            System.out.println("|\tDOESN'T MIND!!!\t\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
            System.out.println(" _______________________________________");
            System.out.println("|\tDESMOND\t\t\t\t|");
            System.out.println("|\tACQUIRED!!!\t\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
        }
        else{
            battleOutcome=0;
            System.out.println(" _______________________________________");
            System.out.println("|\t"+playerName+"\t\t\t\t|");
            System.out.println("|\tPICKS UP DESMOND!!!\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
            System.out.println(" _______________________________________");
            System.out.println("|\tDESMOND\t\t\t\t|");
            System.out.println("|\tUSES BITE\t\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
            System.out.println(" _______________________________________");
            System.out.println("|\t"+playerName+"\t\t\t\t|");
            System.out.println("|\tTAKES 1 DAMAGE!!!\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("\n\n");
            Thread.sleep(750);
        }
        return battleOutcome;
    }
    /*checkDes functional method: 
     * This method utilises for loops to determine if the player has collected the main objective
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        hasDesmond -- value determines if the player has collected the main objective <type boolean>
     * 
     * @return hasDesmond <type boolean>
     */
    private boolean checkDes(int [][] gameBoard, final int DESMOND, boolean hasDesmond){//method checks to see if desmond value is located on the board, if not set hasDesmond to true
        int foundDes=0;
        for(int x=0; x<gameBoard.length; x++){
            for(int y=0; y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==DESMOND){
                    foundDes+=1;
                }
            }
        }
        if(foundDes==0){
            hasDesmond=true;
        }
        else{
            hasDesmond=false;
        }
        return hasDesmond;
    }
    /*checkWin functional method:
     * This functional method uses selection statements to determine if the win conditions have been met.
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        win -- value reveals if win conditions have been met
     *        alive -- true if the playerHealth value is above zero <type boolean>
     *        hasDesmond -- value determines if the player has collected the main objective <type boolean>
     *        atSpawn -- value reveals if the player is at their origionally generated location <type boolean>
     * 
     * @return win <type boolean>
     */
    private boolean checkWin(int[][] gameBoard, boolean win, boolean alive, boolean hasDesmond, boolean atSpawn){
        if(alive==true && hasDesmond==true && atSpawn==true){
            win=true;
        }
        else{
            win=false;
        }
        return win;
    }
    /*checkSpawn functional method:
     * This functional method uses looping and selection to determine if the player is at their origionally generated location
     * 
     * @param atSpawn -- value reveals if the player is at their origionally generated location <type boolean>
     *        gameBoard --  stores all game data <type int[][]>
     *        playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        PLAYER -- value used to represent the player character <type int constant>
     * 
     * @return atSpawn <type boolean>
     */
    private boolean checkSpawn(boolean atSpawn, int[][]gameBoard, ArrayList<ArrayList<Integer>> playerMoves, final int PLAYER){
        int pX=0, pY=0;
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0; y<gameBoard[x].length; y++){
                if(gameBoard[x][y]==PLAYER){
                    pX=x;
                    pY=y;
                }
            }
        }
        if(pX==playerMoves.get(0).get(0) && pY==playerMoves.get(0).get(1)){
            atSpawn=true;
        }
        else{
            atSpawn=false;
        }
        return atSpawn;
    }
    /*cowardResponse functional method:
     * This functional method changes the gameBoard <type int[][]> if the player previously selected certain options
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        PLAYER -- value used to represent the player character <type int constant>
     * 
     * @return gameBoard <type int[][]>
     */    
    private int[][] cowardResponse(int [][]gameBoard, final int ZOMBIE, final int PLAYER){
        int zX=0, zY=0, pX=0, pY=0;
        double zomDistance=0;
        boolean zombieAdded=false;
        for(int x=0; x<gameBoard.length;x++){
            for(int y=0;y<gameBoard[x].length;y++){
                if(gameBoard[x][y]==PLAYER){
                    pX=x;
                    pY=y;
                }
            }
        }
        while(zombieAdded==false){
            zX=rng.nextInt(gameBoard.length);
            zY=rng.nextInt(gameBoard.length);
            zomDistance=Math.sqrt(Math.pow((zY-pY),2)+Math.pow((zX-pX),2));
            if(gameBoard[zX][zY]==0){
                if(zomDistance<=2){
                    gameBoard[zX][zY]=ZOMBIE;
                    zombieAdded=true;
                }
            }
        }
        return gameBoard;
    }
    /*getName functional method
     * This functional method collects user input and ensures that playerName doesn't intephere with formatting
     * 
     * @throws IOException
     * 
     * @return playerName <type String>
     */
    private String getName()throws IOException{
        String playerName;
        while(true){
            System.out.println("Please enter a name.(3-9 characters)");
            playerName=br.readLine();
            if(playerName.length()<=9 && playerName.length()>=3){
                break;
            }
        }
        return playerName;
    }
    /*setDifficulty functional method: 
     * This functional method collects user input and displays formatted UFP's. Returned value is used for selection.
     * 
     * @throws IOException, InterruptedException
     * 
     * @return difficulty <type int>
     */
    private int setDifficulty()throws IOException, InterruptedException{
        int difficulty;
        System.out.println(" _______   __   _______  _______  __    ______  __    __   __      .___________.____    ____         _______. _______ .___________.___________. __  .__   __.   _______ ");
        System.out.println("|       \\ |  | |   ____||   ____||  |  /      ||  |  |  | |  |     |           |\\   \\  /   /        /       ||   ____||           |           ||  | |  \\ |  |  /  _____|");
        System.out.println("|  .--.  ||  | |  |__   |  |__   |  | |  ,----'|  |  |  | |  |     `---|  |----` \\   \\/   /        |   (----`|  |__   `---|  |----`---|  |----`|  | |   \\|  | |  |  __  ");
        System.out.println("|  |  |  ||  | |   __|  |   __|  |  | |  |     |  |  |  | |  |         |  |       \\_    _/          \\   \\    |   __|      |  |        |  |     |  | |  . `  | |  | |_ | ");
        System.out.println("|  '--'  ||  | |  |     |  |     |  | |  `----.|  `--'  | |  `----.    |  |         |  |        .----)   |   |  |____     |  |        |  |     |  | |  |\\   | |  |__| | ");
        System.out.println("|_______/ |__| |__|     |__|     |__|  \\______| \\______/  |_______|    |__|         |__|        |_______/    |_______|    |__|        |__|     |__| |__| \\__|  \\______| ");
        System.out.println("\n+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("\n\nEasy Mode:(For New Players)");
        System.out.println("\t- Number Zombies: BARELY ANY");
        System.out.println("\t- Desmond Movement: OFF");
        System.out.println("\t- Zombie Movement: OFF");
        System.out.println("\t- Desmond: VISABLE");
        System.out.println("\t- Zombies: VISABLE");
        Thread.sleep(400);
        System.out.println("\nNormal Mode:(For Adept Players)");
        System.out.println("\t- Number Zombies: MANAGEABLE");
        System.out.println("\t- Desmond Movement: ON");
        System.out.println("\t- Zombie Movement: OFF");
        System.out.println("\t- Desmond: NOT VISABLE");
        System.out.println("\t- Zombies: NOT VISABLE");
        Thread.sleep(400);
        System.out.println("\nHard Mode:(For Advanced Players)");
        System.out.println("\t- Number Zombies: COPIOUS");
        System.out.println("\t- Desmond Movement: ON");
        System.out.println("\t- Zombie Movement: ON");
        System.out.println("\t- Desmond: NOT VISABLE");
        System.out.println("\t- Zombies: NOT VISABLE");
        System.out.println("\npress <enter> to continue");
        br.readLine();
        System.out.println("\n\n+---------------------------------------+");                                        
    	System.out.printf("|%-15s","\tSet Your Difficulty!\t\t|");
    	System.out.println("\n|---------------------------------------|");
    	System.out.printf("|%10s %10s", "1. Easy\t\t", "2. Normal\t|");
    	System.out.println("\n|\t\t\t\t\t|");
    	System.out.printf("|%10s", " 3. Hard\t\t\t\t|"); 
    	System.out.println("\n+---------------------------------------+");
        System.out.println("Please enter a numerical input from the listed selection.");
        while(true){
            try{
                difficulty=Integer.parseInt(br.readLine());
                if(difficulty<4 && difficulty>0){
                    break;
                }
                else{
                    System.out.println("\nPlease enter a numerical input from the listed selection.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("\nPlease enter a valid numerical input from the listed selection.");
            }
        }
        return difficulty;
    }
    /*zombieMovement functional method:
     * This functional method is used to calculate a dynamically changing location of the objective
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        ZOMBIE -- value used to represent the enemy on the board <type int constant>
     *        DESMOND -- value used to represent the objective on the board <type int constant>
     *        PLAYER -- value used to represent the player character <type int constant>
     * 
     * @return gameBoard <type int[][]> 
     */
    private int[][] zombieMovement(int[][] gameBoard, final int ZOMBIE, final int DESMOND, final int PLAYER){
    	int zombieX=0, zombieY=0, zombieAction=0;
    	for(int x=0; x<gameBoard.length;x++){
    		for(int y=0; y<gameBoard[x].length;y++){
    			if(gameBoard[x][y]==ZOMBIE){
    				zombieX=x;
    				zombieY=y;
    				while(true){
    		            try{
    		                zombieAction=rng.nextInt(12);//1 in 3 zombies will actually move
    		                switch(zombieAction){
    		                    case 1:
    		                        if(gameBoard[zombieX][zombieY+1]+gameBoard[zombieX][zombieY] ==ZOMBIE){
    		                            gameBoard[zombieX][zombieY+1]=ZOMBIE;
    		                            gameBoard[zombieX][zombieY]=0;
    		                        }
    		                        break;
    		                    case 2:
    		                    	if(gameBoard[zombieX][zombieY-1]+gameBoard[zombieX][zombieY] ==ZOMBIE){
    		                            gameBoard[zombieX][zombieY-1]=ZOMBIE;
    		                            gameBoard[zombieX][zombieY]=0;
    		                        }
    		                        break;
    		                    case 3:
    		                    	if(gameBoard[zombieX-1][zombieY]+gameBoard[zombieX][zombieY] ==ZOMBIE){
    		                            gameBoard[zombieX-1][zombieY]=ZOMBIE;
    		                            gameBoard[zombieX][zombieY]=0;
    		                        }
    		                        break;
    		                    case 4:
    		                    	if(gameBoard[zombieX+1][zombieY]+gameBoard[zombieX][zombieY] ==ZOMBIE){
    		                            gameBoard[zombieX+1][zombieY]=ZOMBIE;
    		                            gameBoard[zombieX][zombieY]=0;
    		                        }
    		                        break;
                                default:
                                    break;
    		                }
    		                break;
    		            }
    		            catch(IndexOutOfBoundsException e){
    		                gameBoard[zombieX][zombieY]=ZOMBIE;
    		            }
    				}
    			}
    		}
    	}
    	return gameBoard;
    }
    /*gameExplain procedural method:
     * This procedural method is used to display game story and read player involvment
     * 
     * @throws InterruptedException, IOException
     * 
     * @return void
     */
    private void gameExplain()throws InterruptedException, IOException{
        System.out.println("\nYour baby brother, Desmond, has accidentally wandered into the wasteland.");
        Thread.sleep(1000);
        System.out.println("A place that was once a busy city park since abandonned and consumed by a thick fog.");
        Thread.sleep(1000);
        System.out.println("It isn't the fog that worries you though, you know better. Lurking in the park are creatures gone mad, searching for their favourite delicacy,");
        Thread.sleep(1000);
        System.out.println("Human Brains.");
        Thread.sleep(1000);
        System.out.println("You set out with only one goal in mind. Find Desmond at all costs, and bring escape before it's too late.");
        Thread.sleep(1000);
        System.out.println("\nPress <enter> to continue");
        br.readLine();
    }
    /*startMenu functinal method:
     * This functional method is used to display a UFP and read a user input
     * 
     * @throws IOException
     * 
     * @return number entered <type int> 
     */
    private int startMenu()throws IOException{
        int action=0;
        System.out.println("\n\n+---------------------------------------+");                                        
		System.out.println("|\t\tSave Desmond\t\t|");       
    	System.out.println("|---------------------------------------|");
    	System.out.printf("|%-20s","\t     Select a Function\t\t|");
    	System.out.println("\n|---------------------------------------|");
    	System.out.printf("|%10s %10s", "1. Launch Game\t", "2. Alter Difficulty\t|");
    	System.out.println("\n|\t\t\t\t\t|");
    	System.out.printf("|%10s %5s %s", "3. View Rules\t", "4. Quit\t       ","|");
        System.out.println("\n+---------------------------------------+");                                        
        System.out.println("Please enter a numerical input from the listed selection.");
        while(true){
            try{
                action=Integer.parseInt(br.readLine());
                if(action<=4){
                    break;
                }
                else{
                    System.out.println("\nPlease enter a numerical input from the listed selection.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("\nPlease enter a valid numerical input from the listed selection.");
            }
        }
        return action;
    }
    /*devMenu functional method:
     * This functional method is used to collect user inputs and display UFP's
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        playerName -- value recieved from the player to represent themselves <type String>
     *        playerX -- value corresponds to half of the players location at the start of that turn <type int>
     *        playerY -- value corresponds to half of the players location at the start of that turn <type int>
     *
     * @throws IOException, InterruptedException
     * 
     * @return gameBoard <type int[][]>
     */
    private int[][] devMenu(int[][] gameBoard, String playerName, int playerX, int playerY)throws IOException, InterruptedException{
        int devOption=0, buttonPress=0, x=0, y=0;
        double distance;
        Thread.sleep(500);
        System.out.println(".........");
        Thread.sleep(500);
        System.out.println("Somebody there?");
        Thread.sleep(500);
        System.out.println("v----ZX---__-------------)S-----------+");
    	System.out.printf("l%-15s","\tAre you there\t");
        System.out.print(playerName+"?\t|");
    	System.out.println("\n|---------------------------------------i");
    	System.out.printf("|%10s %10s", "1{} y35\t\t", "{2]. nO\t|");
    	System.out.println("\n|\t\t\\|");
        System.out.println("--------------< -------   -----------ds");
        while(true){
            try{
                devOption=Integer.parseInt(br.readLine());
                if(devOption==2){
                    break;
                }
                else{
                    Thread.sleep(500);
                    System.out.println("Come here... Got smThn to show ya");
                    Thread.sleep(500);
                    System.out.println("Look at this. Some old computer...");
                    Thread.sleep(500);
                    System.out.println("\n             ,----------------,              ,---------,");
                    System.out.println("        ,-----------------------,          ,\"        ,\"|");
                    System.out.println("      ,\"                      ,\"|        ,\"        ,\"  |");
                    System.out.println("     +-----------------------+  |      ,\"        ,\"    |");
                    System.out.println("     |  .-----------------.  |  |     +---------+      |");
                    System.out.println("     |  |                 |  |  |     | -==----'|      |");
                    System.out.println("     |  |  [1]Show DES    |  |  |     |         |      |");
                    System.out.println("     |  |  [2]WinScreen   |  |  |/----|`---=    |      |");
                    System.out.println("     |  |  [3]GoBack      |  |  |   ,/|==== ooo |      ;");
                    System.out.println("     |  |                 |  |  |  // |(((( [33]|    ,\"");
                    System.out.println("     |  `-----------------'  |,\" .;'| |((((     |  ,\"");
                    System.out.println("     +-----------------------+  ;;  | |         |,\"  ");
                    System.out.println("        /_)______________(_/  //'   | +---------+");
                    System.out.println("   ___________________________/___  `,");
                    System.out.println("  /  oooooooooooooooo  .o.  oooo /,   \\,\"-----------");
                    System.out.println(" / ==ooooooooooooooo==.o.  ooo= //   ,`\\--{)B     ,\"");
                    System.out.println("/_==__==========__==_ooo__ooo=_/'   /___________,\"");
                    System.out.println("`-----------------------------'");
                    System.out.println("\nPress Button >?@#1!");
                    while(true){
                        try{
                            buttonPress=Integer.parseInt(br.readLine());
                            if(buttonPress >0 && buttonPress <4){
                                break;
                            }
                            else{
                                System.out.println("\nPress Button /#^$!");
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("\nPress Button %&*$!");
                        }
                    }
                    switch(buttonPress){
                        case 1:
                            System.out.println("[Desmond is now visable]");
                            Thread.sleep(750);
                            while(true){
                                x=rng.nextInt(gameBoard.length);
                                y=rng.nextInt(gameBoard.length);
                                distance=Math.sqrt(Math.pow((x-playerY),2)+Math.pow((x-playerX),2));
                                if(gameBoard[x][y]==0){
                                    if(distance>3){
                                        gameBoard[x][y]=5;
                                        break;
                                    }
                                }
                            }
                            break;
                        case 2:
                            while(true){
                                x=rng.nextInt(gameBoard.length);
                                y=rng.nextInt(gameBoard.length);
                                distance=Math.sqrt(Math.pow((x-playerY),2)+Math.pow((x-playerX),2));
                                if(gameBoard[x][y]==0){
                                    if(distance>3){
                                        gameBoard[x][y]=6;
                                        break;
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
            catch(NumberFormatException e){
            }
        }
        return gameBoard;
    }
    /*howToPlay procedural method:
     * This method displays gamerules and reads player involvement
     * 
     * @throws IOException, InterruptedException
     * 
     * @return void
     */
    private void howToPlay()throws IOException, InterruptedException{
    	int viewGameRules;
    	System.out.println("\n+----------------------------------------------+");
    	System.out.printf("|%15s %4s","Would you like to view the game rules?\t   ","|");
    	System.out.println("\n|----------------------------------------------|");
    	System.out.printf("|%-10s %2s", "1. yes(noob) \t\t2. nah(pro)\t","      |");
    	System.out.println("\n+----------------------------------------------+");
    	while(true){
    		try{
    			viewGameRules=Integer.parseInt(br.readLine());
    			if(viewGameRules>0 && viewGameRules <3){
    				break;
    			}
    			else{
    	            System.out.println("Please enter a numerical input from the listed selection.");
    			}
    		}
    		catch(NumberFormatException e){
	            System.out.println("\nPlease enter a valid numerical input from the listed selection.");
    		}
    	}
    	if(viewGameRules==1){
    		System.out.println(" __    __    ______   ____    __    ____    .___________.  ______      .______    __          ___   ____    ____ ");
    		System.out.println("|  |  |  |  /  __  \\  \\   \\  /  \\  /   /    |           | /  __  \\     |   _  \\  |  |        /   \\  \\   \\  /   / ");
    		System.out.println("|  |__|  | |  |  |  |  \\   \\/    \\/   /     `---|  |----`|  |  |  |    |  |_)  | |  |       /  ^  \\  \\   \\/   /  ");
    		System.out.println("|   __   | |  |  |  |   \\            /          |  |     |  |  |  |    |   ___/  |  |      /  /_\\  \\  \\_    _/   ");
    		System.out.println("|  |  |  | |  `--'  |    \\    /\\    /           |  |     |  `--'  |    |  |      |  `----./  _____  \\   |  |     ");
    		System.out.println("|__|  |__|  \\______/      \\__/  \\__/            |__|      \\______/     | _|      |_______/__/     \\__\\  |__|     ");
    		System.out.println("");
        	System.out.println("+------------------------------------------------------------------------------------------------------------+");
        	System.out.println("\tDesmond is lost somewhere in the grid. Your goal is to locate him and return him to your spawn point.");
            System.out.println("\nIf you encounter a zombie, you'll enter the battle stage. ");
            System.out.println("You're faced with 2 options. Hide or Fight.");
            System.out.println("\tIf you hide, you may avoid the threat temporarally, yet lurking nearby the zombies will be waiting for you.");
            System.out.println("\tHowever, if you choose to stand your ground and fight the zombie, you have the chance to remove them from the board entirely, earning extra points in the process.");
        	System.out.println("Press <enter> to continue");
        	br.readLine();
            Thread.sleep(1000);
        	System.out.println("\nEvery turn you will be presented with a menu that looks like this.");
        	System.out.println("Any other menu you may encounter follows the same format and laws as this one.");
        	System.out.println("\n\n+---------------------------------------+\t+---------------------------------------+");                                        
    		System.out.println("|Turn #4, Current Position: (A, 2)\t|\t|Current Score: 100\t\t\t|");       
        	System.out.println("|---------------------------------------|\t|---------------------------------------|");
        	System.out.printf("|%-15s %20s","\tWhat Action Will You Take?\t|","\t|Desmond Radar: [icy]\t\t\t|");
        	System.out.println("\n|---------------------------------------|\t|---------------------------------------|");
        	System.out.printf("|%10s %10s %s", "[D] Move East\t\t", "[A] Move West\t|","\t|Zombie Radar: [hot]\t\t\t|");
        	System.out.println("\n|\t\t\t\t\t|\t|---------------------------------------|");
        	System.out.printf("|%10s %10s %s", "[W] Move North\t\t", "[S] Move South |","\t|Health Indicator:\t/\\_/\\\t\t|");
        	System.out.println("\n|\t\t\t\t\t|\t|\t\t\t\\ 3 /\t\t|");
            System.out.printf("|%10s %10s %5s %s", "5. Dev Mode\t\t", "6. Give Up","|","\t|\t\t\t \\_/\t\t|");
        	System.out.println("\n+---------------------------------------+\t+---------------------------------------+");
        	Thread.sleep(1000);
        	System.out.println("\nLets take a closer look at the left side.");
        	System.out.println("Press <enter> to continue");
        	br.readLine();
        	System.out.println("\n+---------------------------------------+");                                       
    		System.out.println("|Turn #4, Current Position: (A, 2)\t| <--- This box shows the turn you're on and your current location on the board.");
    		System.out.println("|---------------------------------------|");
        	System.out.printf("|%-15s","\tWhat Action Will You Take?\t|");
        	System.out.println("\n|\t\t\t\t\t|");
        	System.out.printf("|%10s %10s %s", "[D] Move East\t\t", "[A] Move West\t|","<--- These options show the characters you can input to perform certain actions");
        	System.out.println("\n|\t\t\t\t\t|");
        	System.out.printf("|%10s %10s", "[W] Move North\t\t", "[S] Move South |");
        	System.out.println("\n|\t\t\t\t\t|");
            System.out.printf("|%10s %10s %5s", "5. Dev Mode\t\t", "6. Give Up","|");
            System.out.println("\n+---------------------------------------+");
            Thread.sleep(1000);
        	System.out.println("\nNow lets take a look at the right side.");
        	System.out.println("Press <enter> to continue");
        	br.readLine();
        	System.out.println("\n+---------------------------------------+");                                       
    		System.out.println("|Current Score: 100\t\t\t| <--- Certain actions will grant you points. The higher the better. Your final score will be displayed at the end.");
        	System.out.println("|---------------------------------------|");
    		System.out.printf("%-15s","|Desmond Radar: [icy]\t\t\t| <--- Every turn, this radar will be updated. Use the message displayed to help locate your brother.");
        	System.out.println("\n|---------------------------------------|");
    		System.out.printf("%10s","|Zombie Radar: [hot]\t\t\t| <--- Every turn, this radar will be updated. Use the message displayed to help avoid zombies.");
        	System.out.println("\n|---------------------------------------|");
        	System.out.printf("%10s","|Health Indicator:\t/\\_/\\\t\t|");
        	System.out.println("\n|\t\t\t\\ 3 /\t\t| <--- As a player, you have 3 lives. if you take damage 3 times, you will die and lose the game.");
            System.out.printf("%s","|\t\t\t \\_/\t\t|");
            System.out.println("\n+---------------------------------------+");
            System.out.println("Press <enter> to continue");
        	br.readLine();
            
    	}
    }
    /*ending functional method:
     * This functional method takes uses selection to determine a final output and processing to the score varriable <type int>
     * 
     * @param giveUp -- value used to check if player gave up their search
     *        win -- value reveals if win conditions have been met
     *        alive -- true if the playerHealth value is above zero <type boolean>
     *        score -- value used to show progress <type int>
     *        gameBoard --  stores all game data <type int[][]>
     *        playerName -- value recieved from the player to represent themselves <type String>
     *        difficulty -- value determined by player <type int>
     *        usedCheats -- value reveals if player accessed the cheat menu 
     *        turnCount -- value used to track how many turns the player has taken <type int>
     *        
     * @throws InterruptedException
     * 
     * @return score <type int>      
     */
    private int endings(boolean giveUp, boolean win, boolean alive, int score, int[][] gameBoard, String playerName, int difficulty, boolean usedCheats, int turnCount)throws InterruptedException{
        //losing by give up
        if(giveUp==true){
            System.out.println("____    ____  ______    __    __      _______   ______   .______       _______  _______  __  .___________.");
            System.out.println("\\   \\  /   / /  __  \\  |  |  |  |    |   ____| /  __  \\  |   _  \\     |   ____||   ____||  | |           |");
            System.out.println(" \\   \\/   / |  |  |  | |  |  |  |    |  |__   |  |  |  | |  |_)  |    |  |__   |  |__   |  | `---|  |----`");
            System.out.println("  \\_    _/  |  |  |  | |  |  |  |    |   __|  |  |  |  | |      /     |   __|  |   __|  |  |     |  |     ");
            System.out.println("    |  |    |  `--'  | |  `--'  |    |  |     |  `--'  | |  |\\  \\----.|  |     |  |____ |  |     |  |     ");
            System.out.println("    |__|     \\______/   \\______/     |__|      \\______/  | _| `._____||__|     |_______||__|     |__|     \t"+playerName);
            System.out.println("\n");
            score=0;
        }
    //losing by death
        else if(alive==false && giveUp==false){
            System.out.println("+---------------------------------------+");
            System.out.println("|                      ,____\t\t|");
            System.out.println("|                      |---.\\\t\t|");
            System.out.println("|              ___     |    `\t\t|");
            System.out.println("|             / .-\\  ./=)\t\t|");
            System.out.println("|            |  |\"|_/\\/|\t\t|");
            System.out.println("|            ;  |-;| /_|\t\t|");
            System.out.println("|           / \\_| |/ \\ |\t\t|");
            System.out.println("|          /      \\/\\( |\t\t|");
            System.out.println("|          |   /  |` ) |\t\t|");
            System.out.println("|          /   \\ _/    |\t\t|");
            System.out.println("|         /--._/  \\    |\t\t|");
            System.out.println("|         `/|)    |    /\t\t|");
            System.out.println("|           /     |   |\t\t\t|");
            System.out.println("|         .'      |   |\t\t\t|");
            System.out.println("|        /         \\  |\t\t\t|");
            System.out.println("|       (_.-.__.__./  /\t\t\t|");
            System.out.println("|_______________________________________|");
            System.out.println("|\tdeath\t\t\t\t|");
            System.out.println("|\ttakes you away...\t\t|");
            System.out.println("|_______________________________________|");
            Thread.sleep(1000);
            System.out.println("____    ____  ______    __    __      _______   __   _______  _______  ");
            System.out.println("\\   \\  /   / /  __  \\  |  |  |  |    |       \\ |  | |   ____||       \\ ");
            System.out.println(" \\   \\/   / |  |  |  | |  |  |  |    |  .--.  ||  | |  |__   |  .--.  |");
            System.out.println("  \\_    _/  |  |  |  | |  |  |  |    |  |  |  ||  | |   __|  |  |  |  |");
            System.out.println("    |  |    |  `--'  | |  `--'  |    |  '--'  ||  | |  |____ |  '--'  |");
            System.out.println("    |__|     \\______/   \\______/     |_______/ |__| |_______||_______/  "+playerName);
            System.out.println("");
        }
    //winning by cheats
        else if(usedCheats==true){
            System.out.println("__   _______ _   _   _____  _   _  _____  ___ _____ ___________ ");
            System.out.println("\\ \\ / /  _  | | | | /  __ \\| | | ||  ___|/ _ \\_   _|  ___|  _  \\");
            System.out.println(" \\ V /| | | | | | | | /  \\/| |_| || |__ / /_\\ \\| | | |__ | | | |");
            System.out.println("  \\ / | | | | | | | | |    |  _  ||  __||  _  || | |  __|| | | |");
            System.out.println("  | | \\ \\_/ / |_| | | \\__/\\| | | || |___| | | || | | |___| |/ / ");
            System.out.println("  \\_/  \\___/ \\___/   \\____/\\_| |_/\\____/\\_| |_/\\_/ \\____/|___/  "+playerName);
            Thread.sleep(1000);
            System.out.println("\nyour score has been invalidated.");
            score=0;
        }
        
        //proper and fair win
        else{ //if(win==true && alive==true){
            System.out.println("____    ____  ______    __    __     ____    __    ____  __  .__   __. ");
            System.out.println("\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  | ");
            System.out.println(" \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  | ");
            System.out.println("  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  | ");
            System.out.println("    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   | ");
            System.out.println("    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__| "+playerName);
            System.out.println("");
            System.out.println("");
            //easy
            if(difficulty==1){
                if(turnCount<10){
                    System.out.println("You have been granted 300pts for winning in less than 10 turns on easy.");
                    score+=300;
                }
                else if(turnCount>=10 && turnCount <20){
                    System.out.println("You have been granted 200pts for winning in less than 20 turns on easy.");
                    score+=200;
                }
                else{
                    System.out.println("You have been granted 100pts for winning on easy.");
                    score+=100;
                }
            }
            //medium
            else if(difficulty==2){
                if(turnCount<10){
                    System.out.println("You have been granted 500pts for winning in less than 10 turns on normal.");
                    score+=500;
                }
                else if(turnCount>=10 && turnCount <20){
                    System.out.println("You have been granted 400pts for winning in less than 20 turns on normal.");
                    score+=400;
                }
                else{
                    System.out.println("You have been granted 300pts for winning on normal.");
                    score+=300;
                }
            }
            //hard
            else{
                if(turnCount<10){
                    System.out.println("You have been granted 1000pts for winning in less than 10 turns on hard.");
                    score+=1000;
                }
                else if(turnCount>=10 && turnCount <20){
                    System.out.println("You have been granted 900pts for winning in less than 20 turns on hard.");
                    score+=900;
                }
                else{
                    System.out.println("You have been granted 800pts for winning on hard.");
                    score+=800;
                }
            }
        }
        return score;
    }
    /*checkPU functional method: 
     * This functional method uses for loops to check the grid searching for the value of POWERUP, if it can't find it, the player has found it
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     *        hasPowerUp -- hasPowerUp -- value used to determine selection within UFP <type boolean>
     *        POWERUP -- value used to represent the powerUp object
     * 
     * @throws IOException
     * 
     * @return hasPowerUp <type boolean>
     */ 
    private boolean checkPU (int[][] gameBoard, boolean hasPowerUp, final int POWERUP)throws IOException{
        for(int x=0; x<gameBoard.length; x++){
            for(int y=0; y<gameBoard[x].length; y++){
                if(gameBoard[x][y]==POWERUP){
                    return false;
                }
            }
        }
        return true;
    }
    /*showPU procedural method:
     * This procedural method is used to display a UFP for when the variable hasPowerUp <type boolean> becomes true
     * 
     * @throws IOException
     * 
     * @return void
     */    
    private void showPU()throws IOException{
        System.out.println(" _______________________________________");
        System.out.println("|SMASH. HIT. SUPER. FIST.               |");
        System.out.println("|          \\                            |");
        System.out.println("|\t       ,--.--._\t\t\t|");
        System.out.println("|\t------\" _, \\___)\t\t|");
        System.out.println("|\t        / _/____)\t\t|");
        System.out.println("|\t        \\//(____)\t\t|");
        System.out.println("|\t------\\     (__)\t\t|");
        System.out.println("|\t       `-----\"\t\t\t|");
        System.out.println("|_______________________________________|");
        System.out.println("|\tFOUND\t\t\t\t|");
        System.out.println("|\tSUPER STRENGH!!!\t\t|");
        System.out.println("|_______________________________________|");
        System.out.println("\npress <enter> to continue");
        br.readLine();
    }
    /*pitFall functional method:
     * This functional method is used to communicate with the player when they have interacted with object value SANDPIT and change gameBoard <type int[][]> accordingly
     * 
     * @param playerName -- value recieved from the player to represent themselves <type String>
     *        gameBoard --  stores all game data <type int[][]>
     *        playerMoves -- value used to represent all of the players previous moves on the board <type ArrayList<ArrayList<Integer>>>
     *        PLAYER -- value used to represent the player character <type int constant>
     * 
     * @throws InterruptedException, IOException
     * 
     * @return gameBoard <type int[][]>
     */
    private int[][] pitFall(String playerName, int[][] gameBoard, ArrayList<ArrayList<Integer>> playerMoves, final int PLAYER)throws InterruptedException, IOException{
        if(rng.nextInt(2)==1){
            System.out.println(" \n _______________________________________");
            System.out.println("|\t"+playerName+"\t\t\t\t|");
            System.out.println("|\tDROPS DESMOND!!!\t\t|");
            System.out.println("|_______________________________________|");
            Thread.sleep(750);
        }
        System.out.println(" \n _______________________________________");
        System.out.println("|\t"+playerName+"\t\t\t\t|");
        System.out.println("|\tFALLS IN A BIG HOLE!!!\t\t|");
        System.out.println("|_______________________________________|");
        gameBoard[playerMoves.get(0).get(0)][playerMoves.get(0).get(1)]=PLAYER;
        Thread.sleep(500);
        System.out.println("You have been returned to the spawn point.");
        System.out.println("\npress <enter> to continue");
        br.readLine();
        return gameBoard;
    }
    /*wall functional method:
     * This functional method is used to display a UFP and complete processing when the player interacts with object value WALL
     * 
     * @param gameBoard --  stores all game data <type int[][]>
     * 
     * @throws IOException
     * 
     * @return gameBoard <type int[][]>
     */
    private int[][] wall(int[][] gameBoard)throws IOException{
        System.out.println(" _______________________________________");
        System.out.println("|how did this get here?                 |");
        System.out.println("|          \\                            |");
        System.out.println("|\t               _ _ \t\t|");
        System.out.println("|\t              | | |\t\t|");
        System.out.println("|\t__      ____ _| | |\t\t|");
        System.out.println("|\t\\ \\ /\\ / / _` | | |\t\t|");
        System.out.println("|\t \\ V  V / (_| | | |\t\t|");
        System.out.println("|\t  \\_/\\_/ \\__,_|_|_|\t\t|");
        System.out.println("|_______________________________________|");
        System.out.println("|\tWALL\t\t\t\t|");
        System.out.println("|\tBLOCKS YOUR PATH!!!\t\t|");
        System.out.println("|_______________________________________|");
        System.out.println("\npress <enter> to continue");
        br.readLine();
        while(true){
            int i=rng.nextInt(gameBoard.length);
            int k=rng.nextInt(gameBoard.length);
            if(gameBoard[i][k]==0){
                gameBoard[i][k]=20;
                break;
            }
        }

        return gameBoard;
    }
    /*scoreDisplay procedural method:
     * This procedural method is used to display the final score <type int> achieved by player
     * 
     * @param score -- value used to show progress <type int>
     *        playerName -- value recieved from the player to represent themselves <type String>
     * 
     * @throws IOException
     * 
     * @return void
     */
    private void scoreDisplay(int score, String playerName)throws IOException{
        for(int x=0; x<65; x++){
            System.out.println("");
        }
        System.out.println("\n _______  __  .__   __.      ___       __              _______.  ______   ______   .______       _______ ");
        System.out.println("|   ____||  | |  \\ |  |     /   \\     |  |            /       | /      | /  __  \\  |   _  \\     |   ____|");
        System.out.println("|  |__   |  | |   \\|  |    /  ^  \\    |  |           |   (----`|  ,----'|  |  |  | |  |_)  |    |  |__   ");
        System.out.println("|   __|  |  | |  . `  |   /  /_\\  \\   |  |            \\   \\    |  |     |  |  |  | |      /     |   __|  ");
        System.out.println("|  |     |  | |  |\\   |  /  _____  \\  |  `----.   .----)   |   |  `----.|  `--'  | |  |\\  \\----.|  |____ ");
        System.out.println("|__|     |__| |__| \\__| /__/     \\__\\ |_______|   |_______/     \\______| \\______/  | _| `._____||_______|");
        System.out.println("\n+-------------------------------------------------------------------------------------------------------------+");
        System.out.println("\n ______________________________________________");
        System.out.println("|\t"+playerName+"\t\t\t\t        |");

        System.out.println("|\tENDED WITH A SCORE OF: "+score+"!!!\t\t|");
        System.out.println("|______________________________________________|");
        System.out.println("\n\n");
        System.out.println("press <enter> to continue");
        br.readLine();
    }
}