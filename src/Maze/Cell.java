package Maze;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	private int x = 0, y = 0;
	private boolean right;
	private boolean down;
	private List<Cell> set;

	public Cell(final int x, final int y) {
		right = false;
		down = false;
		this.x = x;
		this.y = y;
	}

	public Cell(final Cell other) {
		set(other);
		set = new ArrayList<Cell>(other.set);
	}

	public Cell copy() {
		return new Cell(this);
	}

	public int getX() {
		return x;
	}

	public int getY() {  
		return y;
	}
	
	public boolean isRight() {
		return right;
	}

	public void setRight(final boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(final boolean down) {
		this.down = down;
	}

	public List<Cell> getSet() {
		return set;
	}

	public void setSet(final List<Cell> set) {
		this.set = set;
	}

	public static int isSetContainsDowns(final List<Cell> set) {
		int count = 0;
		for(final Cell cell : set)
			if(cell.isDown())
				count++;
		return count;
	}

	public static int isContainsInSet(final List<Cell> set, final Cell ccell) {
		int count = 0;
		for(final Cell cell : set) {
			if(cell.getX() == ccell.getX())
				return count;
			count++;
		}
		return -1;
	}

	public static void queryCallback(final Callback cb, final Cell[] row) {
		final List<List<Cell>> sets = new ArrayList<List<Cell>>();
		for(final Cell cell : row) {
			boolean add = true;
			for(final List<Cell> set : sets)
				SetsWhile: {
					for(final Cell inSetCell : set) {
						if(inSetCell.getX() == cell.getX()) {
							add = false;
							break SetsWhile;
						}
					}
				}
			if(add)
				sets.add(cell.getSet());
		}
		for(final List<Cell> set : sets)
			cb.action(set);
	}
	
	public void setX(int x) { 
		this.x = x;
	}

	public void setY(int y) { 
		this.y = y;
	}
	
	public void set(final Cell other) {
		right = other.right;
		down = other.down;
		setX(other.x);
		setY(other.y);
	}

	@Override
	public String toString() {
		final String down = isDown() ? "_" : " ";
		final String right = isRight() ? "|" : " ";
		return new StringBuilder().append(down).append(right).toString();
	}

}