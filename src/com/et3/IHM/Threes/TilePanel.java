package com.et3.IHM.Threes;

import com.et3.IHM.Threes.Model;
import com.et3.IHM.Threes.Tile;
import com.et3.IHM.Threes.InGame2;
import com.et3.IHM.Threes.PieMenu;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.Timer;
import javax.swing.JPopupMenu;

public class TilePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
	private static final int TIMER_DELAY = 50;
	private static final int SIZE_PER_TILE = 100;
	private static final float MAX_TIMER   = 5.0f;

	private Point m_mousePos;
	private Timer m_timer=null;
	private Timer m_clickTimer = null;
	private int m_clickPie[];
	private int m_currentMousePos[];
	private boolean m_pieTimerTriggered = false;

	private int m_numberTimer;
	private Model m_model;
	private Tile m_tiles[][];
	private InGame2 m_inGame;

	private PieMenu m_pieMenu;
	private JPopupMenu m_popup;
	private TilePanel m_this;

	private ActionListener m_pieMenuHandler = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			m_pieTimerTriggered = true;
			m_pieMenu.setActiveHighlight(true);
			m_popup.setVisible(true);
			m_popup.show(m_this, m_clickPie[0], m_clickPie[1]);
			m_clickTimer.stop();
		}
	};

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
			move(Direction.TOP);

		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			move(Direction.BOTTOM);
		
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			move(Direction.LEFT);
		
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			move(Direction.RIGHT);

	}

	public void move(int d)
	{
		if(m_model.testEnd())
			return;

		m_model.move(d);

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
	
	//Start MouseListener Interface
	public void mousePressed(MouseEvent e)
   	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			m_mousePos = e.getPoint();
			m_popup.setVisible(false);
			if(m_clickTimer != null)
				m_clickTimer.stop();
			m_clickPie[0] = e.getPoint().x;
			m_clickPie[1] = e.getPoint().y;

			m_clickTimer= new Timer(1000, m_pieMenuHandler);
			m_clickTimer.start();
		}

		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			m_pieMenu.setActiveHighlight(false);
			m_popup.setVisible(true);
			m_popup.show(this, e.getPoint().x, e.getPoint().y);
		}
    }

    public void mouseReleased(MouseEvent e)
   	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			if(m_clickTimer != null)
			{
				m_clickTimer.stop();
				m_clickTimer = null;
			}
			if(m_pieTimerTriggered)
			{
				m_popup.setVisible(false);
				m_pieMenu.moveHighligh();
				m_pieTimerTriggered = false;
			}

			else
			{
				Point p = e.getPoint();

				//If the motion is enough to be taken
				if((-m_mousePos.x + p.x) * (-m_mousePos.x + p.x) + 
				   (-m_mousePos.y + p.y) * (-m_mousePos.y + p.y) > 400)
				{
					if((p.x - m_mousePos.x) * (p.x - m_mousePos.x) < (p.y - m_mousePos.y) * (p.y - m_mousePos.y))
					{
						if(p.y - m_mousePos.y > 0)
							move(Direction.BOTTOM);
						else
							move(Direction.TOP);
					}
					else
					{
						if(p.x - m_mousePos.x > 0)
							move(Direction.RIGHT);
						else
							move(Direction.LEFT);
					}
				}
			}
		}
    }

    public void mouseEntered(MouseEvent e)
   	{
    }

    public void mouseExited(MouseEvent e)
   	{
    }

    public void mouseClicked(MouseEvent e) 
	{
    }
	//End MouseListener
	
	//Start MouseMotionListener
	
	public void mouseMoved(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
		if(m_pieTimerTriggered)
		{
			System.out.println("TilePanel");
			Point p = e.getPoint();
			p.x -= m_clickPie[0];
			p.y -= m_clickPie[1];
			m_pieMenu.mouseMoved(p.x, p.y);
		}
	}

	//End MouseMotionListener
	

	public TilePanel(InGame2 window)
	{
		m_inGame = window;
		m_this = this;
		m_model        = new Model();
		m_tiles        = new Tile[4][4];

		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				m_tiles[i][j] = new Tile();

		m_popup = new JPopupMenu();
		m_pieMenu = new PieMenu(this, 50);
		m_popup.setBorderPainted(false);
		m_popup.setBackground(new Color(0, 0, 0, 0));
		m_popup.add(m_pieMenu);

		updateGraphics(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setFocusable(true);
		requestFocusInWindow();
		m_numberTimer = 0;
		m_clickPie = new int[2];
		m_currentMousePos = new int[2];
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
