import java.util.*;


public class GamePlayer {
	
	int maxDepth;
	int computerLetter;
	
	public GamePlayer()
	{
		maxDepth = 2;
		computerLetter = Board.X;
	}
	
	public GamePlayer(int maxDepth, int computerLetter)
	{
		this.maxDepth = maxDepth;
		this.computerLetter = computerLetter;
	}
	
	public Board MiniMax(Board startingBoard, Board board, int depth , int computerLetter) 
	{
		if (board.isTerminal() || (maxDepth == depth)) 
		{	
			return board;
		}
		//max
		if (computerLetter == board.X)
		{	
			int max = Integer.MIN_VALUE;
			ArrayList<Board> children = board.createChildren(computerLetter);
			
			for (Board child : children) 
			{	
				Board best = MiniMax(child, child, depth + 1, board.O);
				if (best.getScore() > max)
				{
					board = best;
					max = best.getScore();	
				}
			}
			
		}
		//min
		else 
		{	
			int min = Integer.MAX_VALUE;
			ArrayList<Board> children = board.createChildren(computerLetter);
			
			for (Board child : children) 
			{	
				Board worst = MiniMax(child, child, depth + 1, board.X);
				if (worst.getScore() < min)
				{
					board = worst;
					min = worst.getScore();
				}
			}
		}
		//return the child of the starting board with the best heuristic value (not the next descendants like grandchildren etc.)
		while(board.getFather()!=startingBoard)
		{
			board = board.getFather();
		}
		return board;
	}
}
	
	
	
	