package Utilities.IO.XML;

import Utilities.Callback.ICallback;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class XMLFileReader extends DefaultHandler {
    String fileName;
    ArrayList<XMLElement> elements;
    boolean readed;
    ICallback<ArrayList<XMLElement>> callback;
    XMLElement parent;

    public  XMLFileReader(String fileName,ICallback<ArrayList<XMLElement>> callback) throws IOException, SAXException {
        this.fileName = fileName;
        this.readed = false;
        this.callback = callback;
        this.elements = new ArrayList<>();
    }

    public void readFile() throws SAXException, IOException {
        this.parent = null;
        XMLReader xr = XMLReaderFactory.createXMLReader();
        XMLFileReader handler = new XMLFileReader(this.fileName,this.callback);
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        FileReader r = new FileReader(this.fileName);
        xr.parse(new InputSource(r));
    }
    
    public void readFileJar() throws SAXException, IOException {
        this.parent = null;
        XMLReader xr = XMLReaderFactory.createXMLReader();
        XMLFileReader handler = new XMLFileReader(this.fileName,this.callback);
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        xr.parse(new InputSource(XMLFileReader.class.getResourceAsStream(this.fileName)));
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<XMLElement> getElements() {
        return elements;
    }

    public boolean isReaded() {
        return readed;
    }

    ////////////////////////////////////////////////////////////////////
    // Event handlers.
    ////////////////////////////////////////////////////////////////////


    public void startDocument ()
    {
        System.out.println("Start document");
    }


    public void endDocument ()
    {
        System.out.println("End document");
        this.readed = true;
        this.callback.call(this.elements);
    }


    public void startElement (String uri, String name, String qName, Attributes attributes)
    {
        XMLElement element;
        if ("".equals (uri))
            element = new XMLElement(uri,qName, new AttributesImpl(attributes),this.parent);
        else
            element = new XMLElement(uri,name, new AttributesImpl(attributes),this.parent);
        if(parent == null)
        {
            this.parent = element;
            this.elements.add(element);
        }
        else
        {
            this.parent.addChildren(element);
            this.parent = element;
        }
    }


    public void endElement (String uri, String name, String qName)
    {
        this.parent = this.parent.getParent();
    }


    public void characters (char ch[], int start, int length)
    {
        for (int i = start;i < start + length;i++) {
            this.parent.addChar(ch[i]);
        }
    }

}
