import java.util.ArrayList;

public class Board{

	//Variables for the Boards' values
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
	
	//Main variables for every Board
	private int [][] gameBoard;
	Board father;
	private int score;
	
	/* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
	private static int lastLetterPlayed = O;
	
	//Constructors{
	public Board()
	{
		gameBoard = new int[8][8];
		father = null;
		for(int i=0; i<=7; i++)
		{
			for(int j=0; j<=7; j++)
			{
				if((i==3 && j==3) || (i==4 && j==4)){
					gameBoard[i][j]= X;
				}
				else if((i==3 && j==4) || (i==4 && j==3)){
					gameBoard[i][j] = O;
				}
				else{
					gameBoard[i][j] = EMPTY;
				}
			}
		}
		this.score = this.evaluate();
	}
	
	public Board(Board board)
	{
		gameBoard = new int[8][8];
		this.father = board;
		for(int i=0; i<=7; i++)
		{
			for(int j=0; j<=7; j++)
			{
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
		this.score = board.getScore();
	}
	//Constructors}
	
	/*Prints the actual score that is comprised by the total number of 
	* black disks in the board and the respective of the white ones
	*/
	public void actualScore()
	{
		int totalX = 0;
		int totalO = 0;
		
		for(int i=0; i<=7; i++)
		{
			for(int j=0; j<=7; j++)
			{
				if (gameBoard[i][j] == X)
				{
					totalX++;
				}
				else if(gameBoard[i][j] == O)
				{
					totalO++;
				}	
			}
		}
		System.out.println("O player: " + totalO + "\nX player: " + totalX);
		if(totalX>totalO)
		{
			System.out.println("X win!");
		}
		else
		{
			System.out.println("O win!");
		}
	}
	
	//Getter for last Letter Played
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	//Setter for last Letter Played
	public void setLastLetterPlayed(int llp)
	{
		lastLetterPlayed = llp;
	}
	
	//Getter for the father of a board
	public Board getFather()
	{
		return this.father;
	}
	
	//Getter for the heuristic score of the current board
	public int getScore()
	{
		return score;
	}
	
	//Make a move; it creates a new board by placing the letter "letter" in the position [row][col] on the "father"-board
	public void makeMove(int row, int col, int letter)
	{
		gameBoard[row][col] = letter;
		flip(row, col, letter);
		this.score = this.evaluate();
	}
	
	//Flips the appropriate disks after a move is made
	public void flip(int row, int col, int letter)
	{
		//create a variable for !letter (X -> O || O -> X)
		int oppositeLetter;
		if(letter == X)
		{
			oppositeLetter = O;
		}
		else
		{
			oppositeLetter = X;
		}
		
		//flips opposite disks{
		//horizontal{
		//left{
		if(col>1)
		{
			if(gameBoard[row][col-1] == oppositeLetter)
			{
				for(int i = col-2 ; i>=0 ; i--)
				{
					//if there's an empty disk 
					if(gameBoard[row][i] == EMPTY) break;
					if(gameBoard[row][i] == letter)
					{
						//make the left flips
						for(++i ; i<col ; i++)
						{
							gameBoard[row][i] = letter;
						}
						break;
					}
				}
			}
		} 
		//left}
		//right{
		if(col<6)
		{
			if(gameBoard[row][col+1] == oppositeLetter)
			{
				for(int i = col+2 ; i<=7 ; i++)
				{
					//if there's an empty disk
					if(gameBoard[row][i] == EMPTY) break;
					if(gameBoard[row][i] == letter)
					{
						//make the right flips
						for(--i ; i>col ; i--)
						{
							gameBoard[row][i] = letter;
						}
						break;
					}
					
				}
			}
		}	
		//right}
		//horizontal}
		//vertical{
		//up{
		if(row>1)
		{
			if(gameBoard[row-1][col] == oppositeLetter)
			{
				for(int i = row-2 ; i>=0 ; i--)
				{
					//if there's an empty disk 
					if(gameBoard[i][col] == EMPTY) break;
					if(gameBoard[i][col] == letter)
					{
						//make the up flips
						for(++i ; i<row ; i++)
						{
							gameBoard[i][col] = letter;
						}
						break;
					}
				}
			}
		}
		//up}
		//down{
		if(row<6)
		{
			if(gameBoard[row+1][col] == oppositeLetter)
			{
				for(int i = row+2 ; i<=7 ; i++)
				{
					//if there's an empty disk
					if(gameBoard[i][col] == EMPTY) break;
					if(gameBoard[i][col] == letter)
					{
						//make the down flips
						for(--i ; i>row ; i--)
						{
							gameBoard[i][col] = letter;
						}
						break;
					}
				}
			}
		}
		//down}
		//vertical}
		//diagonal{
		//NorthWest{
		if(row>1 && col>1)
		{
			if(gameBoard[row-1][col-1] == oppositeLetter)
			{
				for(int i = row-2, j = col-2 ; i>=0 && j>=0 ; i--, j--)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//make the NorthWest flips
						for(++i , ++j ; i<row && j<col ; i++ , j++)
						{
							gameBoard[i][j] = letter;
						}
						break;
					}
				}	
			}
		}
		//NorthWest}
		//SouthWest{
		if(row<6 && col>1)
		{
			if(gameBoard[row+1][col-1] == oppositeLetter)
			{
				for(int i = row+2, j = col-2 ; i<=7 && j>=0 ; i++, j--)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//make the SouthWest flips
						for(--i , ++j ; i>row && j<col ; i-- , j++)
						{
							gameBoard[i][j] = letter;
						}
						break;
					}
				}	
			}
		}
		//SouthWest}
		//SouthEast{
		if(row<6 && col<6)
		{
			if(gameBoard[row+1][col+1] == oppositeLetter)
			{
				for(int i = row+2, j = col+2 ; i<=7 && j<=7 ; i++, j++)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//make the SouthEast flips
						for(--i , --j ; i>row && j>col ; i-- , j--)
						{
							gameBoard[i][j] = letter;
						}
						break;
					}
				}	
			}
		}
		//SouthEast}
		//NorthEast{
		if(row>1 && col<6)
		{
			if(gameBoard[row-1][col+1] == oppositeLetter)
			{
				for(int i = row-2, j = col+2 ; i>=0 && j<=7 ; i--, j++)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//make the NorthEast flips
						for(++i , --j ; i<row && j>col ; i++ , j--)
						{
							gameBoard[i][j] = letter;
						}
						break;
					}
				}	
			}
		}
		//NorthEast}
		//diagonal}
		//turns opposite disks}
		
	}
	
	//Creates an arrayList of boards that contains every valid child of the current board
	public ArrayList<Board> createChildren(int letter)
	{
		ArrayList<Board> children = new ArrayList<Board>();
		for(int i=0 ; i<=7 ; i++)
		{
			for(int j=0 ; j<=7 ; j++)
			{
				if(this.isValidMove(i, j, letter))
				{
					Board child = new Board(this);
					child.makeMove(i, j, letter);
					children.add(child);
				}
			}
		}
		return children;
	}
	
	//Checks whether the current board is terminal
	public boolean isTerminal() 
	{	
		if(!this.createChildren(X).isEmpty()) return false;
		if(!this.createChildren(O).isEmpty()) return false;	
		return true;
	}
	
	//Checks whether a move is valid; whether a square is empty or inside boarders or can flip opposite disks
	public boolean isValidMove(int row, int col, int letter)
	{
		//create a variable for !letter (X -> O || O -> X)
		int oppositeLetter;
		if(letter == X)
		{
			oppositeLetter = O;
		}
		else
		{
			oppositeLetter = X;
		}
		
		//doesn't overlap other disks{
		if(gameBoard[row][col] != EMPTY)
		{
			return false;
		}
		//doesn't overlap other disks}

		//inside boarders{
		if((row < 0) || (col < 0) || (row > 7) || (col > 7))
		{
			return false;
		}
		//inside boarders}
		
		//can flip opposite disks{
		//horizontal{
		//left{
		if(col>1)
		{
			if(gameBoard[row][col-1] == oppositeLetter)
			{
				for(int i = col-2 ; i>=0 ; i--)
				{
					//if there's an empty disk 
					if(gameBoard[row][i] == EMPTY) break;
					if(gameBoard[row][i] == letter)
					{
						//there are possible left flips
						return true;
					}
				}
			}
		}
		//left}
		//right{
		if(col<6)
		{
			if(gameBoard[row][col+1] == oppositeLetter)
			{
				for(int i = col+2 ; i<=7 ; i++)
				{
					//if there's an empty disk
					if(gameBoard[row][i] == EMPTY) break;
					if(gameBoard[row][i] == letter)
					{
						//there are possible right flips
						return true;
					}
					
				}
			}
		}	
		//right}
		//horizontal}
		//vertical{
		//up{
		if(row>1)
		{
			if(gameBoard[row-1][col] == oppositeLetter)
			{
				for(int i = row-2 ; i>=0 ; i--)
				{
					//if there's an empty disk 
					if(gameBoard[i][col] == EMPTY) break;
					if(gameBoard[i][col] == letter)
					{
						//there are possible up flips
						return true;
					}
				}
			}
		}
		//up}
		//down{
		if(row<6)
		{
			if(gameBoard[row+1][col] == oppositeLetter)
			{
				for(int i = row+2 ; i<=7 ; i++)
				{
					//if there's an empty disk
					if(gameBoard[i][col] == EMPTY) break;
					if(gameBoard[i][col] == letter)
					{
						//there are possible down flips
						return true;
					}
				}
			}
		}
		//down}
		//vertical}
		//diagonal{
		//NorthWest{
		if(row>1 && col>1)
		{
			if(gameBoard[row-1][col-1] == oppositeLetter)
			{
				for(int i = row-2, j = col-2 ; i>=0 && j>=0 ; i--, j--)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//there are possible NorthWest flips
						return true;
					}
				}	
			}
		}
		//NorthWest}
		//SouthWest{
		if(row<6 && col>1)
		{
			if(gameBoard[row+1][col-1] == oppositeLetter)
			{
				for(int i = row+2, j = col-2 ; i<=7 && j>=0 ; i++, j--)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//there are possible SouthWest flips
						return true;
					}
				}	
			}
		}
		//SouthWest}
		//SouthEast{
		if(row<6 && col<6)
		{
			if(gameBoard[row+1][col+1] == oppositeLetter)
			{
				for(int i = row+2, j = col+2 ; i<=7 && j<=7 ; i++, j++)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//there are possible SouthEast flips
						return true;
					}
				}	
			}
		}
		//SouthEast}
		//NorthEast{
		if(row>1 && col<6)
		{
			if(gameBoard[row-1][col+1] == oppositeLetter)
			{
				for(int i = row-2, j = col+2 ; i>=0 && j<=7 ; i--, j++)
				{
					//if there's an empty disk
					if(gameBoard[i][j] == EMPTY) break;
					if(gameBoard[i][j] == letter)
					{
						//there are possible NorthEast flips
						return true;
					}
				}	
			}
		}
		//NorthEast}
		//diagonal}
		//can flip opposite disks}
		
		return false;
	}
	
	/*Using the heuristic: h(n) = f1(n) + 3 * f2(n) + 2 * f3(n) 
	Where f1(n): sum of black disks minus the sum of white disks
		  f2(n): sum of black disks on the corners of the board minus the sum of white disks on the corners of the board
		  f3(n): sum of black disks on the sides of the board minus the sum of white disks on the sides of the board
	*/
	public int evaluate() 
	{	
		//f1
		int totalX = 0;
		int totalO = 0;
		//f2
		int XCorner = 0;
		int OCorner = 0;
		//f3
		int XSide = 0;
		int OSide = 0;
		
		
		for(int i=0; i<=7; i++)
		{
			for(int j=0; j<=7; j++)
			{
				if (gameBoard[i][j] == X)
				{
					totalX++;
					if ( (i == 0 && j == 0) || (i == 7 && j == 7) || (i == 0 && j == 7) || (i == 7 && j == 0) )	
					{
						XCorner++;	
					}
					else if ( ( i > 0 && i < 7 && j == 0) || ( i > 0 && i < 7 && j == 7) || ( j > 0 && j < 7 && i == 0) || ( j > 0 && j < 7 && i == 7) )
					{
						XSide++;
					}
				}
				else if(gameBoard[i][j] == O)
				{
					totalO++;
					if ( (i == 0 && j == 0) || (i == 7 && j == 7) || (i == 0 && j == 7) || (i == 7 && j == 0) )	
					{
						OCorner++;
					}
					else if ( ( i > 0 && i < 7 && j == 0) || ( i > 0 && i < 7 && j == 7) || ( j > 0 && j < 7 && i == 0) || ( j > 0 && j < 7 && i == 7) )
					{
						OSide++;
					}
				}	
			}
		}
		
		int f1 = totalX - totalO;
		int f2 = XCorner - OCorner;
		int f3 = XSide - OSide;
		
		//h(n)
		return f1 + (3 * f2) + (2 * f3);
	}
	
	/*Prints the board with the format below
	
		(blank) 0 1 2 3 4 5 6 7
			0
			1
			2		
			3
			4	(gameBoard)
			5
			6
			7
	
	*/
	public void printBoard()
	{
		System.out.print("  ");
		for(int i=0 ; i<=7 ; i++)
		{
			System.out.print(i + " ");
		}
		System.out.print("\n");
		for(int i=0 ; i<=7 ; i++)
		{
			System.out.print(i + " ");
			for(int j=0 ; j<=7 ; j++)
			{
				if(gameBoard[i][j] == 1)
				{
					System.out.print("X ");
				}
				else if(gameBoard[i][j] == -1)
				{
					System.out.print("O ");
				}
				else
				{
					System.out.print("  ");
				}
			}
			System.out.print("\n");
		}
	}

}