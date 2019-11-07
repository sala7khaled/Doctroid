package network.model;

public class Category {

    private String name;
    private int icon;

    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setLogo(int icon) {
        this.icon = icon;
    }
}
