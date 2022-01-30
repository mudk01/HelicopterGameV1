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
import org.graalvm.compiler.phases.util.GraphOrder;

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

    @Override
    public void run() {
        repaint();
    }

    public Game() {
        gw = new GameWorld();
    }

     public void paint(Graphics g) {
        super.paint(g);
        gw.draw(g);
    }

}

class GameWorld {
    private River river;
    private Helipad helipad;

    public GameWorld() {
        init();
    }
    private void init() {
        river = new River();
        helipad = new Helipad();
    }
    void draw(Graphics g) {
        river.draw(g);
        helipad.draw(g);
    }
}

class River {
    private Point location;
    private int width;
    private int height;

    public River() {
        width = Display.getInstance().getDisplayWidth();
        height = Display.getInstance().getDisplayHeight()/7;
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
        circleSize = 130;
        centerLocation = new Point(Display.getInstance().getDisplayWidth()/2, (int) (Display.getInstance().getDisplayHeight() - (boxSize*1.5)));
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.GRAY);
        g.drawRect(centerLocation.getX(), centerLocation.getY(), boxSize, boxSize, 5);
        g.fillArc(centerLocation.getX() - circleSize/2, centerLocation.getY() + circleSize/2, circleSize, circleSize, 0, 360);
    }
}

//class Fire {
//
//}
//
//class Helicopter {
//
//}