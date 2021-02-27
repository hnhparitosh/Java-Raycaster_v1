package mainpkg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -7189890504393401106L;

    public static final int HEIGHT = 512;
    public static final int WIDTH = 1024;
    public boolean running = false;

    private Thread rayThread;
    private Player p;
    private Map map;

    public Game() {
        canvasSetup();
        new Window("Raycaster by Paritosh Dahiya", this);
        intitialize();

        this.addKeyListener(new KeyInput(p));
        this.setFocusable(true);
    }

    private void intitialize(){
        //initialize player
        p = new Player();
        //initialize map
        map = new Map();

    }

    private void canvasSetup(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }
    String fps="FPS:";
    public void run(){
        this.requestFocus();

		// game timer
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps =("FPS:" + frames);
				frames = 0;
			}
		}

		stop();
    }

    private void draw() {
        
        //initialize drawing tools
        BufferStrategy buffer = this.getBufferStrategy();
        if(buffer == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        //Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        //draw background
        drawBackground(g);
        //draw map
        map.drawMap2D(g);
        //draw rays
        p.drawRays2D(g);
        //draw player
        p.draw(g);
        //draw fps
        drawFPS(g);
        //dispose or Actually draw
        g.dispose();
        buffer.show();
    }
    private void drawBackground(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
    private void drawFPS(Graphics g){
        BufferStrategy buffer = this.getBufferStrategy();
            if(buffer == null){
                this.createBufferStrategy(3);
                return;
            }
            Font font=new Font("Roboto",Font.PLAIN,24);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(fps);
            int h = fm.getAscent();
            g.setColor(Color.WHITE);
            g.drawString(fps, 980-(w/2),500+(h/4));
    }

    public void start() {
        rayThread = new Thread(this);
        rayThread.start();
        running = true;
    }

    public void stop(){
        try{
            rayThread.join();
            running = false;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        new Game();
    }

}
