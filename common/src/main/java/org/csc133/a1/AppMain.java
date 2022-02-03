package org.csc133.a1;

import static com.codename1.ui.CN.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.system.Lifecycle;
import com.codename1.ui.*;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.*;
import com.codename1.io.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import org.graalvm.compiler.phases.util.GraphOrder;

import java.util.Random;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class AppMain extends Lifecycle {
    @Override
    public void runApp() {
        new Game().show();
    }


}

class Game extends Form implements Runnable {
    private GameWorld gw;

    public Game() {
        gw = new GameWorld();

        UITimer timer = new UITimer(this);
        timer.schedule(100, true, this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();
    }

     public void paint(Graphics g) {
        super.paint(g);
        gw.draw(g);
    }

    @Override
    public void run() {
        repaint();
    }

}

class GameWorld {
    private River river;
    private Helipad helipad;
    private Fire fire;
    private Helicopter helicopter;


    public GameWorld() {
        init();
    }

    private void init() {
        river = new River();
        helipad = new Helipad();
        fire = new Fire();
        helicopter = new Helicopter(helipad.getHelipadLocation(), helipad.getBoxSize(), helipad.getCircleSize());
    }

    void draw(Graphics g) {
        river.draw(g);
        helipad.draw(g);
        fire.draw(g);
        helicopter.draw(g);
    }
}

class River {
    private Point location;
    private int width;
    private int height;

    public River() {
        width = Display.getInstance().getDisplayWidth();
        height = Display.getInstance().getDisplayHeight()/8;
        location = new Point(0, Display.getInstance().getDisplayHeight()/3 - height);

    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.BLUE);
        g.drawRect(location.getX(),location.getY(), width, height);
    }
}

class Helipad {
    private Point centerLocation;
    private int boxSize;
    private int circleSize;

    public Helipad() {
        boxSize = 150;
        circleSize = 120;
        centerLocation = new Point(Display.getInstance().getDisplayWidth()/2 - boxSize/2, (int) (Display.getInstance().getDisplayHeight() - (boxSize*1.5)));
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.GRAY);
        g.drawRect(centerLocation.getX(), centerLocation.getY(), boxSize, boxSize, 5);
        g.drawArc(centerLocation.getX() + (boxSize-circleSize)/2, centerLocation.getY() + (boxSize-circleSize)/2, circleSize, circleSize, 0, 360);
    }

    public Point getHelipadLocation() {
        return centerLocation;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public int getCircleSize() {
        return circleSize;
    }
}

class Fire {
    private Point leftRiver, belowRiver, rightRiver;
    private int size;

    //TODO : Research how to use Fire in a Java Collection.
    //

    public Fire() {
        size = new Random().nextInt(100) + Display.getInstance().getDisplayHeight()/10;
        leftRiver = new Point(new Random().nextInt(80) + (int)(Display.getInstance().getDisplayWidth()/4.5), new Random().nextInt(50) + Display.getInstance().getDisplayHeight()/3 - (int)( Display.getInstance().getDisplayHeight()/3.5));
        belowRiver = new Point(new Random().nextInt(80) + Display.getInstance().getDisplayWidth()/2,new Random().nextInt(80) + Display.getInstance().getDisplayHeight()/2);
        rightRiver = new Point(new Random().nextInt(80) + Display.getInstance().getDisplayWidth() - (int)(size*2.25), new Random().nextInt(80) + Display.getInstance().getDisplayHeight()/3 - (int)(Display.getInstance().getDisplayHeight()/3.5));
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.MAGENTA);
        //Fire 1
        //

        g.fillArc(leftRiver.getX(),leftRiver.getY(), size,size,0,360);
        g.drawString("" + size, leftRiver.getX() + size , leftRiver.getY() + size);

        //Fire 2
        //

        g.fillArc(belowRiver.getX(), belowRiver.getY(), size+50, size+50, 0, 360);
        g.drawString(""+ (size+50), belowRiver.getX() + (size+50), belowRiver.getY() + (size+50));

        //Fire3
        //

        g.fillArc(rightRiver.getX(), rightRiver.getY(), size+150, size+150, 0, 360);
        g.drawString(""+ (size+150), rightRiver.getX() + (size+150), rightRiver.getY() + (size+150));
    }

}

class Helicopter {
    private int size;
    private Point location;
    private Point helipadCenterLocation;
    private int centerStart;
    private int helipadCircleSize;

    public Helicopter(Point heliLocation, int helipadBoxSize, int heliCircleSize) {
        size = 35;
        helipadCenterLocation = heliLocation;
        centerStart = (helipadBoxSize-heliCircleSize);
        location = new Point(helipadCenterLocation.getX() + centerStart, helipadCenterLocation.getY() + centerStart);
        helipadCircleSize = heliCircleSize;
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.YELLOW);
        g.fillArc(location.getX() + size, location.getY() + size, size, size, 0, 360);
        g.drawLine(location.getX()+size, location.getY() + size, location.getX(), location.getY());
    }


}