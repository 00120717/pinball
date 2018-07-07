/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinballprueba;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class main
{
public static void main(String[] args)
{

JFrame frame = new JFrame();


frame.setSize(800,650);


frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


PinballPrueba panel = new PinballPrueba();
   

frame.add(panel);

frame.setVisible(true);


}
}