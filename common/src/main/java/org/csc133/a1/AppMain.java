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
import com.sun.management.internal.GarbageCollectorExtImpl;
import org.graalvm.compiler.phases.util.GraphOrder;

import java.util.ArrayList;
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

    final static int DISP_W = Display.getInstance().getDisplayWidth();
    final static int DISP_H = Display.getInstance().getDisplayHeight();
//
//    public static int getSmallDim() { return Math.min(DISP_W,DISP_H); }
//    public static int getLargeDim() { return Math.max(DISP_W,DISP_H); }

    public Game() {
        gw = new GameWorld();

        addKeyListener('Q', (evt) -> gw.quit());
        addKeyListener(-92, (evt) -> gw.input(-92));
        addKeyListener(-91, (evt) -> gw.input(-91));
        addKeyListener(-94, (evt) -> gw.input(-94));
        addKeyListener(-93, (evt) -> gw.input(-93));


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
        gw.tick();
        repaint();
    }

}

class GameWorld {
    private River river;
    private Helipad helipad;
    private Fire fire1, fire2, fire3;
    private ArrayList<Fire> fires;
    private Helicopter helicopter;
    private int fireSize1, fireSize2, fireSize3;
    private Point fireLocation1, fireLocation2, fireLocaton3;


    public GameWorld() {
        init();
    }

    private void init() {
        river = new River();
        helipad = new Helipad();
        fireSize1 = new Random().nextInt(100) +
                Game.DISP_H/10;
        fireSize2 = new Random().nextInt(100) +
                Game.DISP_H/8;
        fireSize3 = new Random().nextInt(100) +
                Game.DISP_H/5;
        fireLocation1 = new Point(new Random().nextInt(80) +
                (int)(Game.DISP_W/4.5),
                new Random().nextInt(50) +
                Game.DISP_H/3 -
                (int)(Game.DISP_H/3.5));
        fireLocation2 = new Point(new Random().nextInt(80) +
                Game.DISP_W/2,
                new Random().nextInt(80) +
                Game.DISP_H/2);
        fireLocaton3 = new Point(new Random().nextInt(50) +
                Game.DISP_W -
                (int)(fireSize3*1.5), new Random().nextInt(80) +
                Game.DISP_H/3 -
                (int)(Game.DISP_H/3.5));
        fire1 = new Fire(fireSize1, fireLocation1);
        fire2 = new Fire(fireSize2, fireLocation2);
        fire3 = new Fire(fireSize3, fireLocaton3);
        fires = new ArrayList<>();
        fires.add(fire1);
        fires.add(fire2);
        fires.add(fire3);
        helicopter = new Helicopter(helipad.getHelipadCenter());
    }

    void draw(Graphics g) {
        river.draw(g);
        helipad.draw(g);
        for(Fire fire : fires) {
            fire.draw(g);
        }
        helicopter.draw(g);
    }

    public void tick() {
        for(Fire fire : fires) {
            if((new Random().nextInt(100)) % 3 == 0) {
                fire.growFire();
            }
        }
        helicopter.move();

    }

    public void input(int input) {
        switch (input) {
            case -92:
                helicopter.moveBackwards();
                break;
            case -91:
                helicopter.moveForwards();
                break;
            case -93:
                helicopter.moveLeft();
                break;
            case -94:
                helicopter.moveRight();
                break;
        }
    }

//    public void move(int input) {
//
//    }

    public void quit() {
        Display.getInstance().exitApplication();
    }
}

class River {
    private Point location;
    private int width;
    private int height;

    public River() {
        width = Game.DISP_W;
        height = Game.DISP_H/8;
        location = new Point(0, Game.DISP_H/3 - height);

    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.BLUE);
        g.drawRect(location.getX(),location.getY(), width, height);
    }
}

class Helipad {
    private Point rectangleLocation, centerLocation;
    private int boxSize;
    private int circleSize, radius;

    public Helipad() {
        boxSize = 150;
        circleSize = 100;
        radius = circleSize/2;
        rectangleLocation = new Point(Game.DISP_W/2 - boxSize/2,
                (int) (Game.DISP_H - (boxSize*1.5)));
        centerLocation =
                new Point(rectangleLocation.getX() + (boxSize/2),
                        rectangleLocation.getY() + (boxSize/2));
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.GRAY);
        g.drawRect(rectangleLocation.getX(), rectangleLocation.getY(), boxSize,
                boxSize, 5);
        g.drawArc(centerLocation.getX() - radius,
                centerLocation.getY() - radius, circleSize,
                circleSize, 0, 360);
        g.setColor(ColorUtil.BLUE);
    }

    public Point getHelipadCenter() {
        return centerLocation;
    }


}

class Fire {
    private Point centerLocation;
    private int size, radius;
    private Font fireSizeFont;

    public Fire(int fireSize, Point fireLocation) {
        size = fireSize;
        radius = fireSize/2;
        centerLocation = new Point(fireLocation.getX() + radius,
                fireLocation.getY() + radius);
        fireSizeFont = Font.createSystemFont(Font.FACE_SYSTEM,
                Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

    }

    //

    void growFire() {
        int move = new Random().nextInt(5);
        size += move;
        radius = size/2;
        centerLocation.setX(centerLocation.getX() - (int)(move/2));
        centerLocation.setY(centerLocation.getY() - (int)(move/2));
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.MAGENTA);
        g.setFont(fireSizeFont);

        g.fillArc(centerLocation.getX() - radius,
                centerLocation.getY() - radius, size, size,0,
                360);
        g.drawString("" + size, centerLocation.getX() + radius,
                centerLocation.getY() + radius);
        g.setColor(ColorUtil.BLUE);
    }

}

class Helicopter {
    private int size, hRadius, centerX, centerY, currSpeed, fuel, water;
    private Point helipadCenterLocation, heliLocation;
    private int startHeadX, startHeadY, endHeadX, endHeadY;
    private double angle;
    private final int MAX_SPEED = 10;

    public Helicopter(Point heliCenter) {
        size = 30;
        currSpeed = 0;
        fuel = 30000;
        helipadCenterLocation = heliCenter;
        hRadius = size/2;

        centerX = helipadCenterLocation.getX();
        centerY = helipadCenterLocation.getY();
        heliLocation = new Point(centerX - hRadius,
                centerY -hRadius);

        angle = Math.toRadians(90);
//        startHeadX = centerX;
//        startHeadY = centerY;
        endHeadX = centerX;
        endHeadY = centerY - size;

    }

    public void move(){
        while(currSpeed > 0 && currSpeed <= 10) {
            centerX = centerX + currSpeed;
            centerY = centerY + currSpeed;
        }
    }

    void moveForwards() {
        currSpeed += 1;
    }

    void moveBackwards() {
        currSpeed -= 1;
    }

    void moveLeft() {
//        helipadCenterLocation.setX(helipadCenterLocation.getX() + 10);
        angle += Math.toRadians(15);
        endHeadX = (int) (centerX + Math.cos(angle) * size);
        endHeadY = (int) (centerY - Math.sin(angle) * size);
    }

    void moveRight() {
        angle -= Math.toRadians(15);
        endHeadX = (int) (centerX + Math.cos(angle) * size);
        endHeadY = (int) (centerY - Math.sin(angle) * size);
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.YELLOW);
        g.fillArc(heliLocation.getX(),
                heliLocation.getY(), size,
                size, 0, 360);
        g.drawLine(centerX, centerY , endHeadX,
                endHeadY);
        g.drawString("" + currSpeed, heliLocation.getX() + size,
                heliLocation.getY() +size);

    }


}