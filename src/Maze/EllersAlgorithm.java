package Maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Eller's maze-generation algorithm; For more information see www.neocomputer.org/projects/eller.html
 * */

public class EllersAlgorithm {

	int w, h;
	private final boolean toConsole;
	private final Random randomizer = new Random();
	private final Cell[] cells;

	public EllersAlgorithm() {
		this(10, 10);
	}

	public EllersAlgorithm(final int w, final int h) {
		this(w, h, true);
	}

	public EllersAlgorithm(final int w, final int h, final boolean toConsole) {
		this.w = w;
		this.h = h;
		this.toConsole = toConsole;
		cells = new Cell[w];

		/* Create the first row. No cells will be members of any set */
		for(int i = 0; i < w; i++)
			cells[i] = new Cell(i, 0);

		outRoof();
	}

	private void merge(final Cell current, final Cell next) {
		final List<Cell> currentSet = current.getSet();
		final List<Cell> nextSet = next.getSet();
		for(final Cell nCell : nextSet) {
			currentSet.add(nCell);
			nCell.setSet(currentSet);
		}
	}

	private void outRoof() {
		if(!toConsole)
			return;

		final StringBuilder builder = new StringBuilder();
		final String[] roof = new String[w];
		Arrays.fill(roof, " _");
		for(final String r : roof)
			builder.append(r);
		System.out.println(builder.toString());
	}

	private Cell[] out() {

		final Cell[] copy = new Cell[w];
		for(int index = 0; index < w; index++)
			copy[index] = cells[index].copy();

		if(!toConsole)
			return copy;

		final StringBuilder builder = new StringBuilder();
		builder.append("|");
		for(final Cell c : cells)
			builder.append(c.toString());
		System.out.println(builder.toString());

		return copy;
	}

	public Cell[] step(final int position) {
		Cell[] link = null;
		/* Join any cells not members of a set to their own unique set */
		fill(cells);
		/* Create right-walls, moving from left to right */
		merge(cells);
		/* Create bottom-walls, moving from left to right */
		floor(cells);
		if(h - position != 1) {
			/* 5.A */
			cells[cells.length - 1].setRight(true);
			link = out();
			next(cells);
		}
		else {
			/* 5.B */
			end(cells);
			link = out();
		}
		return link;
	}

	private void end(final Cell[] row) {
		for(int i = 1; i < row.length; i++) {
			final Cell current = row[i - 1];
			final Cell next = row[i];
			current.setDown(true);
			if(Cell.isContainsInSet(current.getSet(), next) == -1) {
				current.setRight(false);
				merge(current, next);
			}
		}
		row[row.length - 1].setDown(true);
		row[row.length - 1].setRight(true);
	}

	private void fill(final Cell[] row) {
		for(final Cell cell : row) {
			if(cell.getSet() == null) {
				cell.setSet(new ArrayList<Cell>());
				cell.getSet().add(cell);
			}
		}
	}

	private void merge(final Cell[] row) {
		for(int i = 1; i < row.length; i++) {

			final Cell current = row[i - 1];
			final Cell next = row[i];

			if(Cell.isContainsInSet(current.getSet(), next) != -1) {
				current.setRight(true);
				continue;
			}

			if(randomizer.nextBoolean())
				current.setRight(true);
			else
				merge(current, next);
		}
	}

	private void floor(final Cell[] row) {
		Cell.queryCallback(new Callback() {

			@Override
			public void action(final List<Cell> set) {
				if(set.size() > 1) {
					int count = 0;
					while(true) {
						if(count == set.size() - 1)
							break;
						count++;
						final boolean flag = randomizer.nextBoolean();
						if(!flag)
							continue;
						final int g = randomizer.nextInt(set.size());
						set.get(g).setDown(true);
					}
				}
			}
		}, row);
	}

	private void next(final Cell[] previousCells) {
		for(final Cell cell : previousCells) {
			cell.setRight(false);
			cell.setY(cell.getY()+1);
			if(cell.isDown()) {
				cell.getSet().remove(Cell.isContainsInSet(cell.getSet(), cell));
				cell.setSet(null);
				cell.setDown(false);
			}
		}
	}

}