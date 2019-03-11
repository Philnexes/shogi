package Graphics.GUI;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GUIManager {
    /* Nahrání, správa a shovavání jednotivých menu
     * Components - části menu: Button,Text,List,...
     * Prefabs - hotová menu: Main, Multiplayer, Settings, ... */
    protected ArrayList<GUIManagerMenu> menus;
    protected Pane pane;

    public GUIManager(Pane pane)
    {
        this.pane = pane;
        this.menus = new ArrayList<>();
    }
    public GUIManager(Pane pane,ArrayList<IGUIMenu> menus)
    {
        this(pane);
        for(IGUIMenu menu: menus)
        {
            addMenu(menu.getClass().getSimpleName(),menu,false);
        }
    }

    public void addMenu(String key, IGUIMenu menu, boolean visible)
    {
        this.menus.add(new GUIManagerMenu(key,menu,visible));
    }
    public void removeMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
            {
                this.menus.remove(menu);
                return;
            }
        }
    }
    public void purgeMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
            {
                menu.getMenu().erase();
                this.menus.remove(menu);
                return;
            }
        }
    }
    public boolean HasMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
                return true;
        }
        return false;
    }

    public void hideAll()
    {
        for (GUIManagerMenu menu: this.menus) {
            menu.hide();
        }
    }
    public void hide(String key)
    {
        for (GUIManagerMenu menu: this.menus) {
            if(menu.getKey() == key)
                menu.hide();
        }
    }
    public void showAll()
    {
        for (GUIManagerMenu menu: this.menus) {
            menu.show();
        }
    }
    public void show(String key)
    {
        for (GUIManagerMenu menu: this.menus) {
            if(menu.getKey() == key)
                menu.show();
        }
    }
    public void reDraw(Point2D position, Point2D size)
    {
        for (GUIManagerMenu menu: this.menus) {
                menu.getMenu().move(position,size);
        }
    }
    public ArrayList<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<>();
        for(GUIManagerMenu menu: this.menus)
        {
            keys.add(menu.getKey());
        }
        return keys;
    }
    public GUIManagerMenu getMenuHolder(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
                return menu;
        }
        return null;
    }
    public IGUIMenu getMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
                return menu.getMenu();
        }
        return null;
    }
    public void drawMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
                menu.getMenu().draw();
        }
    }
    public void draw()
    {
        for(GUIManagerMenu menu: this.menus)
        {
            menu.getMenu().draw();
        }
    }
    public void eraseMenu(String key)
    {
        for(GUIManagerMenu menu: this.menus)
        {
            if(menu.getKey() == key)
                menu.getMenu().erase();
        }
    }
    public void erase()
    {
        for(GUIManagerMenu menu: this.menus)
        {
            menu.getMenu().erase();
        }
    }

}
