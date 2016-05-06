package com.et3.IHM.Threes;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TContainer extends GridLayout
{
	private JLabel[][] m_tiles;
	
	public TContainer(JPanel panel)
	{
		super(4, 4);
		m_tiles = new JLabel[4][4];
		
		//Init the grid
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
			{
				m_tiles[i][j] = new JLabel("");
				panel.add(m_tiles[i][j]);
			}
		
		for(int i=0; i < 2; i++)
		{
			int x = (int)(Math.random()*1000) % 4;
			int y = (int)(Math.random()*1000) % 4;
			
			if(m_tiles[x][y].getText().equals("") == false)
			{
				i = i-1;
				continue;
			}
			
			int value = (int)(Math.random()*1000) % 2 + 1;
			m_tiles[x][y].setText(Integer.toString(value));
		}
	}
}