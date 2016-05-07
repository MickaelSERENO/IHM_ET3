package com.et3.IHM.Threes;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.et3.IHM.Threes.Direction;

public class TContainer extends GridLayout
{
	//Store tiles;
	private JButton[][] m_tiles;

	//Tell the tiles id available (empty)
	private int[] m_tilesAvailable;
	
	public TContainer(JPanel panel)
	{
		//4*4 grid container
		super(4, 4);
		m_tiles = new JButton[4][4];
		m_tilesAvailable = new int[16];
		
		//Init the grid
		for(int j=0; j < 4; j++)
			for(int i=0; i < 4; i++)
			{
				m_tiles[i][j] = new JButton(""); //Empty Button
				panel.add(m_tiles[i][j]);
				m_tilesAvailable[4*i+j] = 4*i+j; //Tile 4*i + j available (empty)
			}
		
		for(int i=0; i < 2; i++)
		{
			int nbTiles = getTileAvailableSize();

			int x = (int)(Math.random()*1000) % nbTiles; //Get a random number from 0 to nbTiles-1

			//Remember, its m_tilesAvailable which contains the id of the tile (very importante !)
			addTile(m_tilesAvailable[x]);
		}
	}

	public void addTile(int pos)
	{
		int nbTiles = getTileAvailableSize();
		int value = (int)(Math.random()*1000) % 2 + 1;

		System.out.println("pos + " + pos + " " + pos % 4 + " " + pos/4);

		m_tiles[pos%4][pos/4].setText(Integer.toString(value));

		//Update m_tilesAvailable (need to remove the value)
		if(nbTiles > 0)
			for(int i=0; i < nbTiles; i++)
				if(m_tilesAvailable[i] == pos)
				{
					m_tilesAvailable[i] = m_tilesAvailable[nbTiles-1];
					m_tilesAvailable[nbTiles-1] = -1;
				}
	}

	public int getTileAvailableSize()
	{
		int i;
		for(i=0; i < 16 && m_tilesAvailable[i] != -1; i++);
		return i;
	}

	public void move(int dir)
	{
		//Determine how many tiles if available per column
		int[] nbTilesColumn = new int[4];

		for(int i=0; i < 4; i++)
		{
			nbTilesColumn[i] = 0;

			for(int j=0; j < 4; j++)
				if(!m_tiles[i][j].getText().equals("")) //Valid tile
					nbTilesColumn[i]++;
		}

		for(int i=0; i < 4; i++)
		{
			boolean hasMove = false;
			for(int j=0; j < 3; j++)
			{
				if(m_tiles[i][j].getText().equals(""))
				{
					hasMove = true;
					m_tiles[i][j].setText(m_tiles[i][j+1].getText());
					m_tiles[i][j+1].setText("");
				}

				//Else if the tile is blocked and is equals to the next one
				else if(hasMove == false)
				{
					if(m_tiles[i][j].getText().equals("1"))
					{
						if(m_tiles[i][j+1].getText().equals("2"))
						{
							m_tiles[i][j].setText("3");
							m_tiles[i][j+1].setText("");
						}
					}
						
					else if(m_tiles[i][j].getText().equals("2"))
					{
						if(m_tiles[i][j+1].getText().equals("1"))
						{
							m_tiles[i][j].setText("3");
							m_tiles[i][j+1].setText("");
						}
					}

					else if(m_tiles[i][j].getText().equals(m_tiles[i][j+1].getText()))
					{
						m_tiles[i][j].setText(Integer.toString(Integer.parseInt(m_tiles[i][j].getText())*2)); //Multiply by two its value
						m_tiles[i][j+1].setText("");
					}
				}
			}
			if(m_tiles[i][3].getText().equals(""))
				addTile(4*3+i);
		}

		updateTilesAvailable();
	}

	private void updateTilesAvailable()
	{
		int nbTilesAvailable=0;
		for(int i=0; i < 12; i++)
			if(m_tiles[i%4][i/4].getText().equals(""))
			{
				m_tilesAvailable[nbTilesAvailable] = i;
				nbTilesAvailable++;
			}

		if(nbTilesAvailable < 12)
			m_tilesAvailable[nbTilesAvailable] = -1;
	}
}
