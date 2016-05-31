package com.et3.IHM.Threes;

import com.et3.IHM.Threes.Model;
import com.et3.IHM.Threes.Tile;
import com.et3.IHM.Threes.InGame2;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Timer;

public class TilePanel extends JPanel implements KeyListener
{
	private static final int TIMER_DELAY = 50;
	private static final int SIZE_PER_TILE = 100;
	private static final float MAX_TIMER   = 5.0f;
	private Timer m_timer=null;
	private int m_numberTimer;
	private Model m_model;
	private Tile m_tiles[][];
	private InGame2 m_inGame;

	private ActionListener m_timerHandler = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 4; j++)
				{
					if(m_model.hasTileMoved(4*i+j))
					{
						if(m_model.getLastDir() == Direction.TOP)
							m_tiles[i][j].move(0, -SIZE_PER_TILE / MAX_TIMER);

						else if(m_model.getLastDir() == Direction.BOTTOM)
							m_tiles[i][j].move(0, SIZE_PER_TILE / MAX_TIMER);

						else if(m_model.getLastDir() == Direction.LEFT)
							m_tiles[i][j].move(-SIZE_PER_TILE / MAX_TIMER, 0);

						else if(m_model.getLastDir() == Direction.RIGHT)
							m_tiles[i][j].move(SIZE_PER_TILE / MAX_TIMER, 0);
					}
				}
			}

			m_numberTimer = m_numberTimer+1;
			if(m_numberTimer >= MAX_TIMER)
			{
				m_timer.stop();
				updateGraphics(true);
				m_numberTimer = 0;
			}
			repaint();
		}
	};

	public void paintComponent(Graphics g)
   	{
		super.paintComponent(g);
		for(int i=0; i < 4; i++)
		{
			for(int j=0; j < 4; j++)
			{
				m_tiles[i][j].paintBackground(g, SIZE_PER_TILE*j, SIZE_PER_TILE*i);
			}
		}

		if(m_timer != null && m_timer.isRunning())
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 4; j++)
				{
					if(!m_model.hasTileMoved(4*i+j))
						m_tiles[i][j].paintForeground(g, SIZE_PER_TILE*j, SIZE_PER_TILE*i);
				}
			}

			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 4; j++)
				{
					if(m_model.hasTileMoved(4*i+j))
						m_tiles[i][j].paintForeground(g, SIZE_PER_TILE*j, SIZE_PER_TILE*i);
				}
			}
		}

		else
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 4; j++)
				{
					m_tiles[i][j].paintForeground(g, SIZE_PER_TILE*j, SIZE_PER_TILE*i);
				}
			}
		}
	}

	//Start KeyListener Interface
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
			m_model.move(Direction.TOP);

		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			m_model.move(Direction.BOTTOM);
		
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			m_model.move(Direction.LEFT);
		
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			m_model.move(Direction.RIGHT);

		if(m_model.testEnd())
			m_inGame.addEnd();
		else
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 4; j++)
				{
					if(m_model.hasTileMoved(4*i+j))
					{
						if(m_timer != null)
						{
							m_timer.stop();
							m_numberTimer = 0;
						}

						m_timer = new Timer(TIMER_DELAY, m_timerHandler);
						m_timer.start();
						updateGraphics(false);
						return;
					}
				}
			}
		}
		updateGraphics(true);
	}

	public void keyReleased(KeyEvent e) 
	{
 	
	}
 	
	public void keyTyped(KeyEvent e)
	{
	}
	//End KeyListener Interface
	

	public TilePanel(InGame2 window)
	{
		m_inGame = window;
		m_model        = new Model();
		m_tiles        = new Tile[4][4];

		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				m_tiles[i][j] = new Tile();


		updateGraphics(true);
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		m_numberTimer = 0;
		setPreferredSize(new Dimension(SIZE_PER_TILE*4, SIZE_PER_TILE*4));
	}

	public int getScore()
	{
		return m_model.getScore();
	}

	public void resume()
	{
		m_model.initGame();
		updateGraphics(true);
		setFocusable(true);
		requestFocusInWindow();
	}


	public void updateGraphics(boolean newValue)
	{
		for(int i=0; i < 4; i++)
		{
			for(int j=0; j < 4; j++)
			{
				int value;
			   	if(newValue)
					value = m_model.getValue(4*i+j);
				else
					value = m_model.getLastValue(4*i+j);

				if(value == 0)
					m_tiles[i][j].setText("");
				else
					m_tiles[i][j].setText(""+value);

				m_tiles[i][j].setPosition(0, 0);

				if(value == 1)
					m_tiles[i][j].setColor(new Color(0.0f, (float)102/255.0f, 1.0f));
				else if(value == 2)
					m_tiles[i][j].setColor(new Color((float)237/255.0f, (float)25.0/255.0f, (float) 64.0/255.0f));
				else
					m_tiles[i][j].setColor(new Color(0.85f, 0.85f, 0.85f));
				repaint();
			}
		}
	}
}
