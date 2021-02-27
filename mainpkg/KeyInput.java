package mainpkg;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyInput extends KeyAdapter{
    
    private Player p;
    public KeyInput(Player pl){
        p = pl;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP){
            p.moveFront();
        }
        if(key == KeyEvent.VK_S || key==KeyEvent.VK_DOWN){
            p.moveBack();
        }
        if(key == KeyEvent.VK_A || key==KeyEvent.VK_LEFT){
            p.moveLeft();
        }
        if(key == KeyEvent.VK_D || key==KeyEvent.VK_RIGHT){
            p.moveRight();
        }
    }
}
