package inf112.skeleton.app.GUI;

import inf112.skeleton.app.Interfaces.IBoard;
import inf112.skeleton.app.Position;
import inf112.skeleton.app.Tile;

import java.io.IOException;
import java.util.HashMap;

public class Board implements IBoard {
	Tile[][] tileGrid;
	HashMap<Player, Position> playerPosition = new HashMap<>();
	int height;
	int width;

	public Board(String filename) throws IOException {
		BoardBuilder builder = new BoardBuilder();
		tileGrid = builder.buildBoard(filename);
		height = builder.getHeight();
		width = builder.getWidth();
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setObject(Position pos, Object element) {

	}

	@Override
	public Object getObject(Position pos) {
		return null;
	}

	@Override
	public void movePlayer(Position pos) {

	}

	@Override
	public void removeObject(Position pos, Object element) {

	}

	@Override
	public boolean isValid(Position pos) {
		return false;
	}
}
