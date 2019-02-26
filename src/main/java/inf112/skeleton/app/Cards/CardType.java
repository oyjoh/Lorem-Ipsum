package inf112.skeleton.app.Cards;

public enum CardType {
    BACKWARD_1(0, -1),
    FORWARD_1(0, 1),
    FORWARD_2(0, 2),
    FORWARD_3(0, 3),
    ROTATE_180(2, 0),
    ROTATE_90_L(-1, 0),
    ROTATE_90_R(1, 0);

    private final int rotation;
    private final int movement;

    CardType(int rotation, int movement) {
        this.rotation = rotation;
        this.movement = movement;
    }

    public int getRotation() {
        return rotation;
    }

    public int getMovement() {
        return movement;
    }

}

//TODO: add sprite for each type