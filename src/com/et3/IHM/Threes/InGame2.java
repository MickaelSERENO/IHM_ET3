package com.et3.IHM.Threes;

import com.et3.IHM.Threes.Model;
import com.et3.IHM.Threes.Tile;
import com.et3.IHM.Threes.TilePanel;
import com.et3.IHM.Threes.End;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.Timer;

public class InGame2 extends JPanel
{
	private TWindow m_currentFrame;
	private TilePanel m_tilePanel;
	private End m_endPanel;

	public InGame2(TWindow frame)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		m_currentFrame = frame;

		JPanel horizontal = new JPanel();
		horizontal.setLayout(new BoxLayout(horizontal, BoxLayout.X_AXIS));
		horizontal.add(Box.createHorizontalGlue());
		m_tilePanel = new TilePanel(this);
		horizontal.add(m_tilePanel);
		horizontal.add(Box.createHorizontalGlue());

		m_endPanel = new End(frame);

		add(horizontal);
		setVisible(true);
		add(m_endPanel);
		m_endPanel.setVisible(false);
	}

	public void resume()
	{
		m_endPanel.setVisible(false);
		m_tilePanel.resume();
	}

	public int getScore()
	{
		return m_tilePanel.getScore();
	}

	public void addEnd()
	{
		m_endPanel.resume(m_tilePanel.getScore());
		m_endPanel.setVisible(true);
		setVisible(true);
	}
}
