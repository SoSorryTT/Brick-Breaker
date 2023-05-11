public enum ItemType {
    Bullet_SPLIT("bullet.png"),
    EXTRA_LIFE("heart.png");

    private final String imageName;

    ItemType(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
