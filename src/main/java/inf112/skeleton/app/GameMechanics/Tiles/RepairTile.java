package inf112.skeleton.app.GameMechanics.Tiles;

import inf112.skeleton.app.GameMechanics.Direction;
import inf112.skeleton.app.GUI.SpriteType;
import inf112.skeleton.app.GameMechanics.GameObjects.GameObject;

public class RepairTile extends Tile {

	public RepairTile(GameObject[] gameObjects, Direction direction) {
		this.gameObjects = gameObjects;
		this.direction = direction;
		super.spriteType = SpriteType.REPAIR_TILE;
	}

}