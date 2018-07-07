/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imagenes;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Marvin Ramirez
 */
public class Imagen extends javax.swing.JPanel{
    public Imagen() {
    this.setSize(300, 400); //se selecciona el tamaño del panel
    }

    //Se crea un método cuyo parámetro debe ser un objeto Graphics

    public void paint(Graphics grafico) {

    //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa

    ImageIcon Img = new ImageIcon(getClass().getResource("sol.png")); 
    ImageIcon Img2 = new ImageIcon(getClass().getResource("planet2.png")); 
    ImageIcon Img3 = new ImageIcon(getClass().getResource("planet3.png"));
    
    
    //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel

    grafico.drawImage(Img.getImage(), 40, 40, 80, 80, null);
    grafico.drawImage(Img2.getImage(), 25, 175, 50, 50, null);
    grafico.drawImage(Img3.getImage(), 250, 250, 50, 50, null);
    
    
    setOpaque(false);
    super.paintComponent(grafico);
    }
}
