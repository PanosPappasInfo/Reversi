import java.util.*;


public class Main {
	
	
	public static void main(String[] args) 
	{
		
		int depth;
		int turn;
		String replay;
		Scanner sc = new Scanner(System.in);
	
		//difficulty
		System.out.println("Select the depth you want : ");
		depth = sc.nextInt(); 
		//turn
		System.out.println("X's play first");
		System.out.println("What kind of disks do you want ? (1 for X's or -1 for O's)");
		turn = sc.nextInt();
		//game setup
		StartGame(depth, turn);
		//end game
		System.out.println("Thanks for playing!");
		
	}
	
	public static void StartGame(int depth, int turn)
	{
		int computerTurn;
		if(turn == -1)
		{
			computerTurn = 1;
		}
		else
		{
			computerTurn = -1;
		}
		GamePlayer gp = new GamePlayer(depth, computerTurn);
		Board board = new Board();
		Board b;
		Scanner sc = new Scanner(System.in);
		//r = row
		int r;
		//c = col
		int c;
		//prints the starting board
		board.printBoard();
		do
		{
			System.out.println();
			
			switch(board.getLastLetterPlayed())
			{
				case Board.X:
					System.out.println("O's turn");
					if(turn == 1)
					{
						//if there are possible moves for O
						if(!board.createChildren(-1).isEmpty())
						{
							board = gp.MiniMax(board, board, 0, computerTurn);
						}
						else
						{
							System.out.println("O has no valid moves and loses turn");
						}
					}
					else
					{
						//if there are possible moves for O
						if(!board.createChildren(-1).isEmpty())
						{
							System.out.println("Give me your move: (row, col)");
							r = sc.nextInt();
							c = sc.nextInt();
							while(!board.isValidMove(r, c, -1))
							{
								System.out.println("Invalid move!\nGive me your move: (row, col)");
								r = sc.nextInt();
								c = sc.nextInt();
							}
							//make the valid move
							b = new Board(board);
							b.makeMove(r, c, -1);
							board = b;
						}
					}
					board.setLastLetterPlayed(-1);
					break;   
				case Board.O:
					System.out.println("X's turn");
					if(turn == -1)
					{
						//if there are possible moves for X
						if(!board.createChildren(1).isEmpty())
						{
							board = gp.MiniMax(board, board, 0, computerTurn);
						}
						else
						{
							System.out.println("X has no valid moves and loses turn");
						}
					}
					else
					{
						//if there are possible moves for X
						if(!board.createChildren(1).isEmpty())
						{
							System.out.println("Give me your move: (row, col)");
							r = sc.nextInt();
							c = sc.nextInt();
							while(!board.isValidMove(r, c, 1))
							{
								System.out.println("Invalid move!\nGive me your move: (row, col)");
								r = sc.nextInt();
								c = sc.nextInt();
							}
							//make the valid move
							b = new Board(board);
							b.makeMove(r, c, 1);
							board = b;
						}	
					}
					board.setLastLetterPlayed(1);
					break;
				default:
					break;
			}	
			//prints the current board
			board.printBoard();
		}while(!board.isTerminal());
		//prints the final score
		board.actualScore();
	}

}