package mazeMaker;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		Cell cell = maze.getCell(randGen.nextInt(maze.getWidth()), randGen.nextInt(maze.getHeight()));
		selectNextPath(cell);
		maze.getCell(randGen.nextInt(maze.getWidth()), 0).setNorthWall(false);
		maze.getCell(randGen.nextInt(maze.getWidth()), maze.getHeight() - 1).setSouthWall(false);
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		currentCell.setBeenVisited(true);
		if (getUnvisitedNeighbors(currentCell).size() > 0) {
			Cell randomUnvisitedNeighbor = getUnvisitedNeighbors(currentCell)
					.get(randGen.nextInt(getUnvisitedNeighbors(currentCell).size()));
			uncheckedCells.add(randomUnvisitedNeighbor);
			removeWalls(currentCell, randomUnvisitedNeighbor);
			selectNextPath(randomUnvisitedNeighbor);
		} else {
			if (uncheckedCells.size() > 0)
				selectNextPath(uncheckedCells.pop());
		}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getY() == c2.getY()) {
			if (c1.getX() < c2.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
		} else {
			if (c1.getY() < c2.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> ret = new ArrayList<>();
		if(maze.getHeight() > c.getY() + 1)
			if(!maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited())
				ret.add(maze.getCell(c.getX(), c.getY() + 1));
		if(c.getY() > 0)
			if(!maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited())
				ret.add(maze.getCell(c.getX(), c.getY() - 1));
		if(maze.getWidth() > c.getX() + 1)
			if(!maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited())
				ret.add(maze.getCell(c.getX() + 1, c.getY()));
		if(c.getX() > 0)
			if(!maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited())
				ret.add(maze.getCell(c.getX() - 1, c.getY()));
		return ret;
	}
}