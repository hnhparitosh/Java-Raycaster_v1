package mainpkg;

import java.awt.Color;
import java.awt.Graphics;

public class Map {
    public Map(){
    }

    int mapX=8, mapY=8, mapS=64;
    int arr[] = 
    {
        1,1,1,1,1,1,1,1,
        1,0,1,0,0,0,0,1,
        1,0,1,0,0,0,0,1,
        1,0,1,0,0,0,0,1,
        1,0,0,0,0,0,0,1,
        1,0,0,0,0,1,0,1,
        1,0,0,0,0,0,0,1,
        1,1,1,1,1,1,1,1
    };

    public void drawMap2D(Graphics g){
        int x,y,xo,yo;
        for(y=0;y<mapY;y++){
            for(x=0;x<mapX;x++){
                if(arr[y*mapX+x]==1)
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.BLACK);
                xo=x*mapS; yo=y*mapS;
                g.fillRect(xo+1, yo+1, mapS-1, mapS-1);
            }
        }
    }   
}
