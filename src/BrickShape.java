public enum BrickShape {
    SHAPE_1(new String[][]{{"%", "%", "%", "%", "%"},{"*", "*", "*", "*", "*"},{"*", "*", "*", "*", "*"}}),
    SHAPE_2(new String[][]{{"*", "-", "-", "*", "*"},{"*", "*", "-", "*", "*"},{"*", "-", "*", "-", "*"}}),
    SHAPE_3(new String[][]{{"-", "-", "*", "-", "-"},{"-", "*", "*", "*", "-"},{"-", "-", "-", "-", "-"}}),
    SHAPE_4(new String[][]{{"_", "*", "*", "*", "_"},{"*", "*", "_", "*", "*"},{"_", "*", "*", "*", "_"}});

    private String[][] brickShapes;

    BrickShape(String[][] brickShapes) {
        this.brickShapes = brickShapes;
    }

    public String[][] getBrickShapes() {
        return brickShapes;
    }
}
