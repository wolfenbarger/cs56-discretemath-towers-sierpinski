package edu.ucsb.cs56.projects.discretemath.towers_sierpinski.SierpinskiTriangle;

import edu.ucsb.cs56.projects.discretemath.towers_sierpinski.SVGraphics.*;
import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * SierpinskiTriangleBuilder class used with SierpinskiTriangle class to build 
 * Towers of Hanoi representation
 *
 * @author Martin Wolfenbarger
 * @version 2013/05/16 
 */
public class SierpinskiTriangleBuilder {
    private int disks;
    private int width = 960;
    private int height = 832;
    private SVDefinitions definitions = new SVDefinitions();
    
    /** one arg constructor defines the # of disks
     */
    public SierpinskiTriangleBuilder(int disks) {
        this.disks = disks;
        loadColors();
    }
    
    /** two arg constructor defines the # of disks
     */
    public SierpinskiTriangleBuilder(int disks, ArrayList<Color> colors) {
        this.disks = disks;
        loadColors();
        setColors(colors);
    }
    
    /** sets width and height of triangle, called before build
     *  @param width int for width
     *  @param height int for height
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /** loads colors from colors.properties file
     */
    public void loadColors() {
        Properties prop = new Properties();
        ArrayList<Color> colors = new ArrayList<Color>();
        int i = 0;
    	try {
    		prop.load(new FileInputStream("colors.properties"));
            while(prop.getProperty(""+i) != null) {
                try {
                    Color c = Color.decode("0x" + prop.getProperty(""+i));
                    colors.add(c);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } finally {
                    i++;
                }
            }
            
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
        setColors(colors);
    }
    
    /** sets color array list for SierpinskiTriangle class
     *  @param colors 
     */
    public void setColors(ArrayList<Color> colors) {
        SierpinskiTriangle.colors = colors;
        for(int i = SierpinskiTriangle.colors.size(); this.disks > i; i++) SierpinskiTriangle.colors.add(Color.BLACK);
    }
    
    /** Returns the actual SVGraphics item that is the Sierpinski triangle
     */
    public void buildDefinition(){
        this.createDiskDefinitions();
        this.createSideDefinitions();
        this.createTowerDefinition();
        
        if(this.disks < 1) return;
        SVCustom s = new SVCustom(new Point(40, (int)Math.pow(2, this.disks)*104 - 60));
        int left[] = new int[]{0,1};
        int right[] = new int[]{1,2};
        int bottom[] = new int[]{0,2};
        SierpinskiTriangle t = new SierpinskiTriangle(0,this.disks,this.disks-1, left,  right, bottom, buildInitial(this.disks));
        s.setAttribute("font-family", "Arial");
        s.addContent(t, "Triangle");
        SVSymbol symbol = new SVSymbol("SierpinskiTriangle");
        symbol.setAttribute("viewBox", "0 0 " + ((int)Math.pow(2, this.disks)*120 - 40) +
                            " " + ((int)Math.pow(2, this.disks)*104 - 20));
        symbol.addContent(s,"content");
        
        this.definitions.addContent(symbol, "SierpinskiTriangle");
    }
    
    /** Builds the arraylist of arraylist integers that represent the current state of the game.
     */
    public static ArrayList<ArrayList<Integer>> buildInitial(int disks) {
        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        a.add(new ArrayList<Integer>());
        a.add(new ArrayList<Integer>());
        a.add(new ArrayList<Integer>());
        for(int i = 0; disks > i; i++) a.get(0).add(0,new Integer(i));
        return a;
    }
    
    /** Creates definition elements for triangle sides once here
     */
    private void createSideDefinitions() {
        for(int i = 0; this.disks > i; i++) {
            SVRectangle s = new SVRectangle(new Point(-60,-5),120,10);
            s.setBorderRadius(3);
            s.setColor(SierpinskiTriangle.colors.get(i));
            s.setId("side" + i);
            this.definitions.addContent(s, "side"+i);
        }
    }
    
    /** Creates definition elements for disks once here
     */
    private void createDiskDefinitions() {
        int sizeFactor;
        for(int i = 0; this.disks > i; i++) {
            SVCustom d = new SVCustom();
            d.setId("disk" + i);
            sizeFactor = 10*(i+1)/this.disks;
            SVEllipse e = new SVEllipse(new Point(0,0),20 + sizeFactor,14);
            SVEllipse esmall = new SVEllipse(new Point(0,0),11,13);
            SVText t = new SVText(new Point(1,4),""+i);
            esmall.setColor(Color.white);
            e.setColor(SierpinskiTriangle.colors.get(i));
            esmall.setBorderWidth(1);
            esmall.setBorderColor(Color.lightGray);
            
            t.setAttribute("text-anchor", "middle");
            t.setAttribute("font-size", "13");
            t.setAttribute("font-weight", "bold");
            
            d.addContent(e, "e"+i);
            d.addContent(esmall, "esmall"+i);
            d.addContent(t,"t"+i);
            this.definitions.addContent(d, "disk"+i);
        }
    }
    
    /** Creates tower element once here
     */
    private void createTowerDefinition() {
        SVCustom t = new SVCustom();
        t.setId("tower");
        SVRectangle background = new SVRectangle(new Point(-51,-67),102,79);
        background.setBorderRadius(4);
        background.setColor(Color.lightGray);
        background.setOpacity(0.15);
        SVRectangle background2 = new SVRectangle(new Point(-48,-65),96,75);
        background2.setBorderRadius(4);
        background2.setColor(Color.white);
        background2.setOpacity(0.5);
        SVRectangle base = new SVRectangle(new Point(-45,-5),90,10);
        base.setBorderRadius(3);
        base.setColor(Color.black);
        SVRectangle p1 = new SVRectangle(new Point(-33,-60),6,60);
        p1.setBorderRadius(3);
        p1.setColor(Color.black);
        SVRectangle p2 = new SVRectangle(new Point(-3,-60),6,60);
        p2.setBorderRadius(3);
        p2.setColor(Color.black);
        SVRectangle p3 = new SVRectangle(new Point(27,-60),6,60);
        p3.setBorderRadius(3);
        p3.setColor(Color.black);
        t.addContent(background, "background");
        t.addContent(background2, "background2");
        t.addContent(p1,"p1");
        t.addContent(p2,"p2");
        t.addContent(p3,"p3");
        t.addContent(base,"base");
        this.definitions.addContent(t, "tower");
    }
    
    /* Returns definitions
        @return definitons variable
     */
    public SVDefinitions getDefinitions() {
        return this.definitions;
    }
    
    /* Returns use graphics element
     @return use graphics element
     */
    public SVUse getUse() {
        SVUse u = new SVUse("SierpinskiTriangle");
        u.setWidth(this.width);
        u.setHeight(this.height);
        return u;
    }
    
    public void save(String file) {
        buildDefinition();
        SVCanvas c = new SVCanvas(this.width, this.height);
        
        c.addXMLContent(getDefinitions());
        c.addXMLContent(getUse());
        
        
        c.saveTo(file);
    }
}
