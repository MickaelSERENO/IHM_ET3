package com.et3.IHM.Threes;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import com.et3.IHM.Threes.Direction;

public class TContainer extends GridLayout
{
	//Store tiles;
	private JLabel[][] m_tiles;

	//Tell the tiles id available (empty)
	private int[] m_tilesAvailable;
	
	public TContainer(JPanel panel)
	{
		//4*4 grid container
		super(4, 4, 6, 6);
		m_tiles = new JLabel[4][4];
		m_tilesAvailable = new int[16];
		
		//Init the grid
		for(int j=0; j < 4; j++)
			for(int i=0; i < 4; i++)
			{
				m_tiles[i][j] = new JLabel(""); //Empty Button
				m_tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.black));

				panel.add(m_tiles[i][j]);
				m_tiles[i][j].setHorizontalAlignment(JLabel.CENTER);
				m_tiles[i][j].setVerticalAlignment(JLabel.CENTER);
				m_tiles[i][j].setOpaque(true);

				m_tilesAvailable[4*i+j] = 4*i+j; //Tile 4*i + j available (empty)
			}
		
		for(int i=0; i < 2; i++)
		{
			int nbTiles = getTileAvailableSize();

			int x = (int)(Math.random()*1000) % nbTiles; //Get a random number from 0 to nbTiles-1

			//Remember, its m_tilesAvailable which contains the id of the tile (very importante !)
			addTile(m_tilesAvailable[x]);
		}
		updateTilesAvailable();
		updateColor();
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
		//Look per column
		if(dir == Direction.TOP || dir == Direction.BOTTOM)
		{
			for(int i=0; i < 4; i++)
			{
				boolean hasMove = false;
				for(int j=0; j < 3; j++)
				{
					//Move to the top
					if(dir == Direction.TOP)
					{
						if(m_tiles[i][j].getText().equals(""))
						{
							hasMove = true;
							m_tiles[i][j].setText(m_tiles[i][j+1].getText());
							m_tiles[i][j+1].setText("");
						}
					}

					//Move to the bottom
					else
					{
						if(m_tiles[i][3-j].getText().equals(""))
						{
							hasMove = true;
							m_tiles[i][3-j].setText(m_tiles[i][2-j].getText());
							m_tiles[i][2-j].setText("");
						}
					}

					//if the tile is blocked and is equals to the next one
					if(hasMove == false)
					{
						if(dir == Direction.TOP)
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

						else
						{
							if(m_tiles[i][3-j].getText().equals("1"))
							{
								if(m_tiles[i][2-j].getText().equals("2"))
								{
									m_tiles[i][3-j].setText("3");
									m_tiles[i][2-j].setText("");
								}
							}
								
							else if(m_tiles[i][3-j].getText().equals("2"))
							{
								if(m_tiles[i][2-j].getText().equals("1"))
								{
									m_tiles[i][3-j].setText("3");
									m_tiles[i][2-j].setText("");
								}
							}

							else if(m_tiles[i][3-j].getText().equals(m_tiles[i][2-j].getText()))
							{
								m_tiles[i][3-j].setText(Integer.toString(Integer.parseInt(m_tiles[i][3-j].getText())*2)); //Multiply by two its value
								m_tiles[i][2-j].setText("");
							}
						}
					}
				}
			}

			if(dir == Direction.TOP)
			{
				for(int i=0; i < 4; i++)
				{
					if(m_tiles[i][3].getText().equals(""))
					{
						int append = 0;
						while(append == 0)
						{
							int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
							if(m_tiles[x][3].getText().equals(""))
							{
								append = 1;
								addTile(4*3+x);
							}
						}
						break;
					}
				}
			}

			else
			{
				for(int i=0; i < 4; i++)
				{
					if(m_tiles[i][0].getText().equals(""))
					{
						int append = 0;
						while(append == 0)
						{
							int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
							if(m_tiles[x][0].getText().equals("") && dir==Direction.BOTTOM)
							{
								append = 1;
								addTile(x);
							}
						}
						break;
					}
				}
			}
		}

		else if(dir == Direction.LEFT || dir == Direction.RIGHT)
		{
			for(int j=0; j < 4; j++)
			{
				boolean hasMove = false;
				for(int i=0; i < 3; i++)
				{
					//Move to the LEFT
					if(dir == Direction.LEFT)
					{
						if(m_tiles[i][j].getText().equals(""))
						{
							hasMove = true;
							m_tiles[i][j].setText(m_tiles[i+1][j].getText());
							m_tiles[i+1][j].setText("");
						}
					}

					//Move to the RIGHT
					else
					{
						if(m_tiles[3-i][j].getText().equals(""))
						{
							hasMove = true;
							m_tiles[3-i][j].setText(m_tiles[2-i][j].getText());
							m_tiles[2-i][j].setText("");
						}
					}

					//if the tile is blocked and is equals to the next one
					if(hasMove == false)
					{
						if(dir == Direction.LEFT)
						{
							if(m_tiles[i][j].getText().equals("1"))
							{
								if(m_tiles[i+1][j].getText().equals("2"))
								{
									m_tiles[i][j].setText("3");
									m_tiles[i+1][j].setText("");
								}
							}
								
							else if(m_tiles[i][j].getText().equals("2"))
							{
								if(m_tiles[i+1][j].getText().equals("1"))
								{
									m_tiles[i][j].setText("3");
									m_tiles[i+1][j].setText("");
								}
							}

							else if(m_tiles[i][j].getText().equals(m_tiles[i+1][j].getText()))
							{
								m_tiles[i][j].setText(Integer.toString(Integer.parseInt(m_tiles[i][j].getText())*2)); //Multiply by two its value
								m_tiles[i+1][j].setText("");
							}
						}

						else
						{
							if(m_tiles[3-i][j].getText().equals("1"))
							{
								if(m_tiles[2-i][j].getText().equals("2"))
								{
									m_tiles[3-i][j].setText("3");
									m_tiles[2-i][j].setText("");
								}
							}
								
							else if(m_tiles[3-i][j].getText().equals("2"))
							{
								if(m_tiles[2-i][j].getText().equals("1"))
								{
									m_tiles[3-i][j].setText("3");
									m_tiles[2-i][j].setText("");
								}
							}

							else if(m_tiles[3-i][j].getText().equals(m_tiles[2-i][j].getText()))
							{
								m_tiles[3-i][j].setText(Integer.toString(Integer.parseInt(m_tiles[3-i][j].getText())*2)); //Multiply by two its value
								m_tiles[2-i][j].setText("");
							}
						}
					}
				}
			}

			if(dir == Direction.LEFT)
			{
				for(int i=0; i < 4; i++)
				{
					if(m_tiles[3][i].getText().equals(""))
					{
						int append = 0;
						while(append == 0)
						{
							int x = (int)(Math.random()*1000) % (4-i)+i; //Get a random number from i to 3
							if(m_tiles[3][x].getText().equals(""))
							{
								append = 1;
								addTile(4*x+3);
							}
						}
						break;
					}
				}
			}
			else
			{
				for(int i=0; i < 4; i++)
				{
					if(m_tiles[0][i].getText().equals(""))
					{
						int append = 0;
						while(append == 0)
						{
							int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
							if(m_tiles[0][x].getText().equals(""))
							{
								append = 1;
								addTile(4*x);
							}
						}
						break;
					}
				}
			}
		}
		updateColor();
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

	public boolean testEnd()
	{
		for(int i=0; i < 4; i++)
		{
			for(int j=0; j < 4; j++)
			{
				if(m_tiles[i][j].getText().equals(""))
					return false;

				else if(m_tiles[i][j].getText().equals("2"))
				{
				    if(j < 3 && m_tiles[i][j+1].getText().equals("1"))
						return false;
					else if(i < 3 && m_tiles[i+1][j].getText().equals("1"))
						return false;
				}

				else if(m_tiles[i][j].getText().equals("1"))
				{
				    if(j < 3 && m_tiles[i][j+1].getText().equals("2"))
						return false;
					else if(i < 3 && m_tiles[i+1][j].getText().equals("2"))
						return false;
				}

				else if(j < 3 && m_tiles[i][j].getText().equals(m_tiles[i][j+1].getText()))
						return false;

				else if(i < 3 && m_tiles[i][j].getText().equals(m_tiles[i+1][j].getText()))
						return false;
			}
		}
		return true;
	}

	public int getScore()
	{
		int score = 0;
		for(int i=0; i < 3; i++)
			for(int j=0; j < 3; j++)
				score = score + Integer.parseInt(m_tiles[i][j].getText());
		return score;
	}

	private void updateColor()
	{
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
			{
				if(m_tiles[i][j].getText().equals("2"))
					m_tiles[i][j].setBackground(Color.red);
				else if(m_tiles[i][j].getText().equals("1"))
					m_tiles[i][j].setBackground(Color.blue);
				else if(m_tiles[i][j].getText().equals(""))
					m_tiles[i][j].setBackground(Color.gray);
				else
					m_tiles[i][j].setBackground(Color.white);
			}
	}
}
