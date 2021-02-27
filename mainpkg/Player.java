package mainpkg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.Graphics;

public class Player{
    public static final int SIZE=8;
    //private static int mapX=8, mapY=8, mapS=64;
    private double px,py,pdx,pdy,pa=0;
    private double DR = 0.0174533; //one degree in radians
    Map map=new Map();
    public Player(){
        // x=Game.WIDTH/2 - SIZE/2;
        // y=Game.HEIGHT/2 - SIZE/2;
        px=300; py=300;
        pdx=Math.cos(pa)*5;
        pdy=Math.sin(pa)*5;
        
        
    }

	public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        //g.fillOval(x, y, SIZE, SIZE);
        g2d.fill(new Rectangle2D.Double(px-SIZE/2, py-SIZE/2, SIZE, SIZE));
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new Line2D.Double(px, py, px+pdx*5, py+pdy*5));
	}

    public void drawRays2D(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(528, 0, 480, 160);
        g.setColor(Color.BLUE);
        g.fillRect(528, 160, 480, 160);
        int r,mx,my,mp,dof;
        double rx=px,ry=py,ra,xo=0,yo=0,disT=0;
        ra=pa-DR*30;
        if(ra<0){ra+=2*Math.PI;}
        if(ra>2*Math.PI){ra-=2*Math.PI;}
        for(r=0;r<120;r++){
            // check horizontal lines
            dof=0;
            double disH=1000000,hx=px,hy=py;
            double aTan = -1/Math.tan(ra);
            if(ra>Math.PI){
                ry=(((int)py>>6)<<6)-0.0001;
                rx=(py-ry)*aTan+px;
                yo=-64; xo=-yo*aTan;
            } //looking up

            if(ra<Math.PI){
                ry=(((int)py>>6)<<6)+64;
                rx=(py-ry)*aTan+px;
                yo=64; xo=-yo*aTan;
            } //looking down

            if(ra==0 || ra==Math.PI){
                rx=px; ry=py; dof=8;
            } //looking straight left or right
            

            while(dof<8){
                mx=(int)(rx)>>6;
                my=(int)(ry)>>6;
                mp=my*map.mapX+mx;
                if(mp>0 && mp<map.mapX*map.mapY && map.arr[mp]==1){
                    hx=rx; hy=ry;
                    disH=dist(px,py,hx,hy,ra);
                    dof=8;
                } //hit wall
                else{ rx+=xo; ry+=yo; dof+=1; } //next line
            }

            // check vertical lines
            dof=0;
            double disV=1000000,vx=px,vy=py;
            double nTan = -Math.tan(ra);
            if(ra>(Math.PI)/2 && ra<3*(Math.PI)/2){
                rx=(((int)px>>6)<<6)-0.0001;
                ry=(px-rx)*nTan+py;
                xo=-64; yo=-xo*nTan;
            } //looking left

            if(ra<(Math.PI)/2 || ra>3*(Math.PI)/2){
                rx=(((int)px>>6)<<6)+64;
                ry=(px-rx)*nTan+py;
                xo=64; yo=-xo*nTan;
            } //looking right

            if(ra==0 || ra==Math.PI){
                rx=px; ry=py; dof=8;
            } //looking straight up or down
            
            while(dof<8){
                mx=(int)(rx)>>6;
                my=(int)(ry)>>6;
                mp=my*map.mapX+mx;
                if(mp>0 && mp<map.mapX*map.mapY && map.arr[mp]==1){
                    vx=rx; vy=ry;
                    disV=dist(px,py,vx,vy,ra);
                    dof=8;
                } //hit wall
                else{ rx+=xo; ry+=yo; dof+=1; }         //next line
            }
            Graphics2D g2d = (Graphics2D) g;
            if(disV<disH){rx=vx; ry=vy; disT=disV;
                g2d.setColor(new Color(0, 230, 0));}     //vertical wall hit
            if(disH<disV){rx=hx; ry=hy; disT=disH;
                g2d.setColor(new Color(0, 180, 0));}     //horizontal wall hit
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(new Line2D.Double(px, py, rx,ry));

            // draw 3D walls
            double ca=pa-ra;
            if(ca<0){ca+=2*Math.PI;}
            if(ca>2*Math.PI){ca-=2*Math.PI;}
            disT = disT*Math.cos(ca);       //fix fisheye 
            double lineH=(map.mapS*320)/disT;
            if(lineH>320){lineH=320;}       // line height
            double lineO=160-lineH/2;       // line offset
            g2d.setStroke(new BasicStroke(4));
            g2d.draw(new Line2D.Double(r*4+530, lineO, r*4+530,lineH+lineO));

            ra+=DR/2;
            if(ra<0){ra+=2*Math.PI;}
            if(ra>2*Math.PI){ra-=2*Math.PI;}
        }
	}


	public void moveFront() {
        px+=pdx; py+=pdy;
	}

	public void moveBack() {
        px-=pdx; py-=pdy;
	}

	public void moveLeft() {
        pa-=0.1;
        if(pa<0){
            pa+=2*Math.PI;
        }
        pdx=Math.cos(pa)*5;
        pdy=Math.sin(pa)*5;
        //System.out.println("angle:"+ pa*57.2958);
	}

    public void moveRight() {
        pa+=0.1;
        if(pa>2*Math.PI){
            pa-=2*Math.PI;
        }
        pdx=Math.cos(pa)*5;
        pdy=Math.sin(pa)*5;
        //System.out.println("angle:"+ pa*57.2958);
	}
    public double dist(double ax,double ay,double bx, double by,double angle){
        return (Math.sqrt((bx-ax)*(bx-ax)+(by-ay)*(by-ay)));
    }
}
