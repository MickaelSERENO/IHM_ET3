package com.et3.IHM.Threes;
import com.et3.IHM.Threes.TContainer;
import com.et3.IHM.Threes.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TWindow extends JFrame implements KeyListener
{
	private TContainer m_container;

	//KeyListener Interface
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			m_container.move(Direction.TOP);

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
	//End KeyListener Interface

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
		  m_container = new TContainer(panel);
		  panel.setLayout(m_container);
		  panel.setFocusable(true);
		  panel.requestFocusInWindow();
		  panel.addKeyListener(this); //Used to get the event applied to this panel
		  setContentPane(panel);

		  //Show the window
		  setVisible(true);
	}
}
