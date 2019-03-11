package Graphics.GUI;

public class GUIManagerMenu {
    boolean visible;
    String key;
    IGUIMenu menu;

    public GUIManagerMenu(String key,IGUIMenu menu)
    {
        this.key = key;
        this.visible = false;
        this.menu = menu;
    }
    public GUIManagerMenu(String key,IGUIMenu menu,boolean visible)
    {
        this.key = key;
        this.visible = visible;
        this.menu = menu;
        this.menu.setVisible(visible);
    }

    public void show()
    {
        this.visible = true;
        this.menu.show();
    }

    public void hide()
    {
        this.visible = false;
        this.menu.hide();
    }

    public boolean IsVisible()
    {
        return this.visible;
    }

    public String getKey()
    {
        return this.key;
    }
    public IGUIMenu getMenu()
    {
        return this.menu;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
    public void setMenu(IGUIMenu menu)
    {
        this.menu = menu;
    }
}
