package Utilities.IO.XML;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;

public class XMLElement {
    String uri;
    String name;
    String text;
    AttributesImpl attributes;
    ArrayList<XMLElement> childrens;

    XMLElement parent;
    public  XMLElement(String uri, String name, AttributesImpl attributes, XMLElement parent)
    {
        this.uri = uri;
        this.name = name;
        this.attributes = attributes;
        this.text = "";
        this.childrens = new ArrayList<>();
        this.parent = parent;
    }

    public void addChildren(XMLElement child)
    {
        this.childrens.add(child);
    }

    public String getText() {
        return text;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public AttributesImpl getAttributes() {
        return attributes;
    }

    public ArrayList<XMLElement> getChildrens() {
        return childrens;
    }

    public XMLElement getParent() {
        return parent;
    }

    public void addChar(char c)
    {
        this.text += c;
    }

    public void setText(String text) {
        this.text = text;
    }



}
