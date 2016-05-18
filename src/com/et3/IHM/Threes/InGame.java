package com.et3.IHM.Threes;

import com.et3.IHM.Threes.TWindow;
import com.et3.IHM.Threes.TContainer;
import com.et3.IHM.Threes.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InGame extends JPanel implements KeyListener
{
	//Start KeyListener Interface
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			m_container.move(Direction.TOP);

		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			m_container.move(Direction.BOTTOM);
		
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			m_container.move(Direction.LEFT);
		
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			m_container.move(Direction.RIGHT);

		if(m_container.testEnd())
			m_currentFrame.setCurrentPanel("End");
	}

	public void keyReleased(KeyEvent e) 
	{
 	
	}
 	
	public void keyTyped(KeyEvent e)
	{
	}
	//End KeyListener Interface
	
	private TContainer m_container;
	private TWindow m_currentFrame;
	
	public InGame(TWindow frame)
	{
		m_currentFrame = frame;
	    m_container    = new TContainer(this);
		setLayout(m_container);
		addKeyListener(this);
		resume();
	}

	public void resume()
	{
		removeAll();
		m_container = new TContainer(this);
		setLayout(m_container);
		setFocusable(true);
		requestFocusInWindow();
	}

	public int getScore()
	{
		return m_container.getScore();
	}
}
