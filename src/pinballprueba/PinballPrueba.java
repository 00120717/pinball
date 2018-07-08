/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinballprueba;

/**
 *
 * @author Marvin Ramirez
 */
import Imagenes.Imagen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PinballPrueba extends JPanel {
	Timer t = new Timer(1, new Listener());
	int ctr = 0;
	double G = 0.1; //Gravedad

	final int xpos = 570; // posicion en x de 
	
	double[] p2d = {570, 200};
	double[] v2d = {0, 0};
	
	int points = 0;
	int lives = 0;
	int sides = 13;
	int stars=0;
	double snorm = 400;
	double sd = 450;
	double sv = 0;
	boolean setlock = false;
	boolean rdown, ldown;
	double paddle = 130;
	double rtheta = 0;
	double ltheta = 0;
	
	int preset[][] = {
            
                        //x,y
			{100, 500, 135, 450,1}, //paleta derecha
                                    //x,y
			{100, 450, 440, 500,1}, //paleta izquierda
                        
                        
                        
			{570, 0, 600, 20, 1}, //esquina derecha
			{600, 0, 600, 650, 1}, //pared derecha
			{-1, 0, 600, 0, 1}, //pared de arriba
			{0, -1, 0, 650, 1}, //pared izquierda
                        {645, 0, 600, 0, 1}
	};
        // dibujar circulos
	int[][] balls = {
			{80, 80, 40, 50},
			{230, 280, 30, 50},//
			{400, 200, 25, 100},//
			{250, 100, 30, 50},//
                        {50, 400, 35, 50},
                        {500, 400, 35, 50}
	};
	int lines[][] = new int[100][5];
	
	public PinballPrueba(){
		super();
		t.start();
                
		addKeyListener(new Key());
		setFocusable(true);
		
		for(int i = 0; i < preset.length; i++){
			lines[i] = preset[i];
		}
		
		int plen = preset.length;
		
		int ct = 0;
		for(int k = 0; k < balls.length; k++){
			int px = balls[k][0], py = balls[k][1], radius = balls[k][2];
			for(double i = 0; i < 2 * Math.PI; i+= 2 * Math.PI/ sides){
				ct++;
				lines[plen + ct][0] = px + (int) (radius * Math.cos(i));
				lines[plen + ct][1] = py + (int) (radius * Math.sin(i));
				lines[plen + ct][2] = px + (int) (radius * Math.cos(i - 2 *Math.PI / sides));
				lines[plen + ct][3] = py + (int) (radius * Math.sin(i - 2 * Math.PI / sides));
			}
		}
		
	}
	
	private class Listener implements ActionListener {
		
            public void actionPerformed(ActionEvent e){
			repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		v2d[1] += G;
		p2d[1] += v2d[1];
		p2d[0] += v2d[0];
                
                try{
                //Dibujar imagenes
		Imagen img = new Imagen();
                img.paint(g);
                }catch(Exception e) {
                    e.printStackTrace();
                }  
		
		if(p2d[1] > 1000){
			p2d[0] = 570;
			p2d[1] = 200;
			v2d[0] = 0;
			v2d[1] = 0;
			lives++;
		}
		if(p2d[0] == 570 && p2d[1] > sd){
			p2d[1] = sd;
			v2d[1] = Math.min(v2d[1], sv);
		}
		
		if(setlock == false){
			sv *= 0.95; //impulso
			sv -= (sd - snorm)/50;
			sd += sv;
		}
		double rc = 0.1;
		if(rdown){
			rtheta = Math.max(-0.5, rtheta - rc);
		}else{
			rtheta = Math.min(0.5, rtheta + rc);
		}
		if(ldown){
			ltheta = Math.max(-0.5, ltheta - rc);
		}else{
			ltheta = Math.min(0.5, ltheta + rc);
		}
		
		lines[0][2] = lines[0][0] + (int) (Math.cos(ltheta) * paddle);
		lines[0][3] = lines[0][1] + (int) (Math.sin(ltheta) * paddle);
		lines[1][0] = lines[1][2] + (int) (-Math.cos(rtheta) * paddle);
		lines[1][1] = lines[1][3] + (int) (Math.sin(rtheta) * paddle);
		int rX = (int) p2d[0];
		int rY = (int) p2d[1];
		int r = 10;
		g.setColor(Color.blue);//color bolita
		g.drawArc(rX - r, rY - r, 2 * r, 2 * r, 0, 360);
		g.setColor(Color.MAGENTA);//color lineas del marco
		for(int i = 0; i < lines.length; i++){
			int x1 = lines[i][0],
				y1 = lines[i][1],
				x2 = lines[i][2];
			double y2 = lines[i][3] + 0.0001;
			if(i > preset.length){
                                //Color circulos
				g.setColor(Color.black);
			}
			g.drawLine(x1, y1, x2, (int) Math.round(y2));

			double bmag = Math.sqrt(v2d[0] * v2d[0] + v2d[1] * v2d[1]);
			double lineslope = ((double)(x2 - x1))/((double)(y2 - y1));
			double ballslope = v2d[0] / v2d[1];
			double binter = p2d[0] - ballslope * p2d[1];
			double linter = x1 - lineslope * y1;
			
			double y = (binter - linter)/(lineslope - ballslope);
			double sx = y * ballslope + binter;
			double la = Math.atan2(y2 - y1, x2 - x1);
			double ba = Math.atan2(v2d[1], v2d[0]);
			
			double da = 2 * la -  ba;
					
			
			if(sx >= Math.min(x2, x1) && sx <= Math.max(x1, x2) && 
			   Math.min(y1, y2) <= y && Math.max(y1, y2) >= y){
				double interdist = Math.sqrt(Math.pow(sx - p2d[0],2) + Math.pow(y - p2d[1],2));
				double tiny = 0.0001;
				double futuredist = Math.sqrt(Math.pow(sx - (p2d[0] + Math.cos(ba) * tiny),2) + Math.pow(y - (p2d[1] + Math.sin(ba) * tiny),2));
				
				if(interdist <=  bmag + r && futuredist < interdist){ 
					
					if(i > preset.length){
						int ball = (int) Math.floor((i - preset.length)/sides);
					
						points += balls[ball][3] * bmag;
					}
					v2d[0] = Math.cos(da) * bmag;
					v2d[1] = Math.sin(da) * bmag;
				}
			}
		}
		g.setColor(Color.black);
		
		g.fillRect(xpos - 5, (int)sd + 10, 10, 20);
		
                stars=points/500;
		g.drawString("PUNTAJE: " + points , 610, 30);
		g.drawString("VIDAS: " +  lives, 610, 60);
                g.drawString("ESTRELLAS " + stars, 610, 90);
                
	}
	
        // controles 
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				setlock = true;
				sd += 2;
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				ldown = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_L){
				rdown = true;
			}
		}
		public void keyReleased(KeyEvent e){
			setlock = false;
			if(e.getKeyCode() == KeyEvent.VK_A){
				ldown = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_L){
				rdown = false;
			}
		}
	}

    public int getLives() {
        return lives;
    }

    public int getStars() {
        return stars;
    }
    
    
}
