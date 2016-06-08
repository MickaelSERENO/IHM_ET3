package com.et3.IHM.Threes;

import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;

public class PieMenu extends JPanel implements MouseListener, MouseMotionListener
{
	private int m_radius;
	private TilePanel m_panel;
	private int m_highlightedDir;
	private boolean m_highlight;
	private boolean m_activeHighlight;

	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			Point p = e.getPoint();

			//Convert the coordinate system
			p.y *= -1;
			p.x -= m_radius;
			p.y += m_radius;

			//If the point is in the piemenu
			if(Math.sqrt(p.x * p.x + p.y * p.y) < m_radius)
			{
				//Get the angle
				int d = 0;
				float angle = (float)Math.acos((float)p.x / (float)Math.sqrt((float)(p.x * p.x + p.y * p.y)));
				if(p.y < 0)
					angle = (float)(2.0f*Math.PI) - angle;

				System.out.println("x = " + p.x);
				System.out.println("y = " + p.y);
				System.out.println("angle = " + angle);

				//Determine the direction choosen
				if(angle < Math.PI/4 || angle > Math.PI * 7 / 4.0f)
					d = Direction.RIGHT;

				else if(angle < Math.PI * 3 / 4.0f)
					d = Direction.TOP;

				else if(angle < Math.PI * 5 / 4.0f)
					d = Direction.LEFT;
				
				else
					d = Direction.BOTTOM;

				m_panel.move(d);
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if(m_activeHighlight)
			m_panel.move(m_highlightedDir);
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

	public void mouseMoved(MouseEvent e)
	{
		Point p = e.getPoint();
		mouseMoved(p.x, p.y);
	}

	public void mouseDragged(MouseEvent e)
	{
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D)g;
		gr.setColor(new Color(180, 180, 180));
		gr.fillOval(0, 0, 2*m_radius, 2*m_radius);

		if(m_highlight)
		{
			gr.setColor(new Color(220, 220, 220));
			switch(m_highlightedDir)
			{
				case Direction.TOP:
					gr.fillArc(0, 0, 2*m_radius, 2*m_radius,
							   45, 90);
					break;
				case Direction.RIGHT:
					gr.fillArc(0, 0, 2*m_radius, 2*m_radius,
							   -45, 90);
					break;
				case Direction.LEFT:
					gr.fillArc(0, 0, 2*m_radius, 2*m_radius,
							   135, 90);
					break;
				case Direction.BOTTOM:
					gr.fillArc(0, 0, 2*m_radius, 2*m_radius,
							   180+45, 90);
					break;
			}
		}

		gr.setColor(Color.black);
		gr.drawLine((int)(m_radius + m_radius*Math.cos(3/4.0f*Math.PI)), (int)(m_radius - m_radius*Math.sin(3/4.0f * Math.PI)), 
				(int)(m_radius + m_radius*Math.cos(1/4.0f * Math.PI)), (int)(m_radius - m_radius*Math.sin(-1/4.0f*Math.PI)));
		gr.drawLine((int)(m_radius + m_radius*Math.cos(3/4.0f*Math.PI)), (int)(m_radius + m_radius*Math.sin(3/4.0f * Math.PI)), 
				(int)(m_radius + m_radius*Math.cos(1/4.0f * Math.PI)), (int)(m_radius + m_radius*Math.sin(-1/4.0f*Math.PI)));

		gr.drawString("UP", m_radius - 8, (int)(m_radius * 0.4));
		gr.drawString("DOWN", m_radius-18, (int)(m_radius * 1.7));
		gr.drawString("LEFT", (int)(m_radius*0.2), m_radius + 5);
		gr.drawString("RIGHT", (int)(m_radius*1.2), m_radius + 5);
	}

	public PieMenu(TilePanel g, int radius)
	{
		m_highlight = false;
		m_activeHighlight = false;
		m_panel = g;
		m_radius = radius;
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(2*m_radius, 2*m_radius));
		setBackground(new Color(0, 0, 0, 0));
	}

	public void setActiveHighlight(boolean a)
	{
		m_activeHighlight = a;
	}

	public void moveHighligh()
	{
		m_panel.move(m_highlightedDir);
	}

	public void mouseMoved(int x, int y)
	{
		System.out.println("move !! ");
		//Convert the coordinate system
		y *= -1;
		x -= m_radius;
		y += m_radius;

		//If the point is in the piemenu
		if(m_activeHighlight || Math.sqrt(x * x + y * y) < m_radius)
		{
			//Get the angle
			int d = 0;
			float angle = (float)Math.acos((float)x / (float)Math.sqrt((float)(x * x + y * y)));
			if(y < 0)
				angle = (float)(2.0f*Math.PI) - angle;

			//Determine the direction choosen
			if(angle < Math.PI/4 || angle > Math.PI * 7 / 4.0f)
				d = Direction.RIGHT;

			else if(angle < Math.PI * 3 / 4.0f)
				d = Direction.TOP;

			else if(angle < Math.PI * 5 / 4.0f)
				d = Direction.LEFT;
			
			else
				d = Direction.BOTTOM;

			m_highlightedDir = d;
			m_highlight = true;
		}

		else
			m_highlight = false;
		repaint();
	}
}
