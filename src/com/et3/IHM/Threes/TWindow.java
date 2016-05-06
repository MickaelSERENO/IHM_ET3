package com.et3.IHM.Threes;
import com.et3.IHM.Threes.TContainer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TWindow extends JFrame implements KeyListener
{
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			System.out.println("Key Up");
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{}
		
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{}
		
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{}
	}
 	
	public void keyReleased(KeyEvent e) 
	{
 	
	}
 	
	public void keyTyped(KeyEvent e)
	{
 		
	}

	public TWindow()
	{
		  //Init the window
		  super();
		  setTitle("Threes");
		  setSize(800, 600);
		  setResizable(false);
		  setLocationRelativeTo(null);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  //Init the panel
		  JPanel panel = new JPanel();
		  panel.setLayout(new TContainer(panel));
		  panel.setFocusable(true);
		  panel.requestFocusInWindow();
		  panel.addKeyListener(this);
		  setContentPane(panel);
		  //Show the window
		  setVisible(true);
	}
}
