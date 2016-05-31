package com.et3.IHM.Threes;

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;

public class Tile
{
	public static final int SIZE = 90;
	private String m_label;
	private Color m_color;
	private float m_pos[];

	public Tile()
	{
		super();
		m_label = "";
		m_color = Color.red;
		m_pos = new float[2];
		m_pos[0] = m_pos[1] = 0;
	}

	public void paintForeground(Graphics g, int posX, int posY)
	{
		if(m_label.compareTo("") == 0)
			return;
		g.setColor(m_color);
		g.fillRoundRect(posX + (int)m_pos[0], posY + (int)m_pos[1], SIZE, SIZE, 15, 15);
		g.setColor(Color.black);
		g.drawString(m_label, posX + (int)m_pos[0]+40, posY + (int)m_pos[1]+45);
	}

	public void paintBackground(Graphics g, int posX, int posY)
	{
		g.setColor(Color.gray);
		g.fillRoundRect(posX, posY, SIZE, SIZE, 15, 15);
	}

	public void setText(String text)
	{
		m_label = text;
	}

	public void setColor(Color color)
	{
		m_color = color;
	}

	public void setPosition(float x, float y)
	{
		m_pos[0] = x;
		m_pos[1] = y;
	}

	public void move(float x, float y)
	{
		m_pos[0] = m_pos[0] + x;
		m_pos[1] = m_pos[1] + y;
	}
}
