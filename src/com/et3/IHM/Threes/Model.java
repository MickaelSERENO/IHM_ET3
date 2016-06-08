package com.et3.IHM.Threes;

public class Model
{
	private int       m_lastTiles[][];
	private int       m_tiles[][];
	private boolean   m_tileMoved[][];
	private int       m_lastDir;

	public Model()
	{
		m_tiles     = new int[4][4];
		m_tileMoved = new boolean[4][4];
		m_lastTiles = new int[4][4];
		m_lastDir   = Direction.NO_DIR;

		initGame();
	}

	public void initGame()
	{
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				m_tiles[i][j] = 0;

		for(int i=0; i < 2; i++)
		{
			int x;
			do
			{
				x = (int)(Math.random()*1000) % 16; //Get a random number from 0 to 15
			}while(m_tiles[x%4][x/4] != 0);
			addTile(x);
		}
	}

	public void addTile(int pos)
	{
		int value = (int)(Math.random()*1000)%2 + 1;
		m_tiles[pos%4][pos/4] = value;
	}

	public void move(int dir)
	{
		/*Re init tiles for animations*/
		m_lastDir = dir;
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
			{
				m_lastTiles[i][j] = m_tiles[i][j];
				m_tileMoved[i][j] = false;
			}

		boolean hasMove=false;
		boolean canAddTile=false;

		if(dir == Direction.TOP)
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 3; j++)
				{
					hasMove = false;
					if(m_tiles[i][j] == 0)
					{
						m_tiles[i][j]   = m_tiles[i][j+1];
						if(m_tiles[i][j+1] != 0)
						{
							m_tileMoved[i][j+1] = true;
							canAddTile = true;
						}
						m_tiles[i][j+1] = 0;
						hasMove = true;
					}

					if(hasMove == false)
					{
						if(m_tiles[i][j] == 1 && m_tiles[i][j+1] == 2 ||
						   m_tiles[i][j] == 2 && m_tiles[i][j+1] == 1)
						{
							canAddTile = true;
							m_tiles[i][j]   = 3;
							m_tiles[i][j+1] = 0;
							m_tileMoved[i][j+1] = true;
						}

						else if(m_tiles[i][j] == m_tiles[i][j+1] &&
								m_tiles[i][j] != 1 && m_tiles[i][j] != 2)
						{
							canAddTile = true;
							m_tiles[i][j]   = 2*m_tiles[i][j];
							m_tiles[i][j+1] = 0;
							m_tileMoved[i][j+1] = true;
						}
					}
				}
			}

			for(int i=0; canAddTile && i < 4; i++)
			{
				if(m_tiles[i][3] == 0)
				{
					int append = 0;
					while(append == 0)
					{
						int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
						if(m_tiles[x][3] == 0)
						{
							append = 1;
							addTile(4*3+x);
						}
					}
					return;
				}
			}
		}

		else if(dir == Direction.BOTTOM)
		{
			for(int i=0; i < 4; i++)
			{
				for(int j=0; j < 3; j++)
				{
					hasMove = false;
					if(m_tiles[i][3-j] == 0)
					{
						m_tiles[i][3-j]   = m_tiles[i][2-j];
						hasMove = true;
						if(m_tiles[i][2-j] != 0)
						{
							canAddTile = true;
							m_tileMoved[i][2-j] = true;
						}
						m_tiles[i][2-j] = 0;
					}

					if(hasMove == false)
					{
						if(m_tiles[i][3-j] == 1 && m_tiles[i][2-j] == 2 ||
						   m_tiles[i][3-j] == 2 && m_tiles[i][2-j] == 1)
						{
							canAddTile = true;
							m_tiles[i][3-j] = 3;
							m_tiles[i][2-j] = 0;
							m_tileMoved[i][2-j] = true;
						}

						else if(m_tiles[i][3-j] == m_tiles[i][2-j] &&
								m_tiles[i][3-j] != 1 && m_tiles[i][3-j] != 2)
						{
							canAddTile = true;
							m_tiles[i][3-j]   = 2*m_tiles[i][3-j];
							m_tiles[i][2-j] = 0;
							m_tileMoved[i][2-j] = true;
						}
					}
				}
			}
			
			for(int i=0; canAddTile && i < 4; i++)
			{
				if(m_tiles[i][0] == 0)
				{
					int append = 0;
					while(append == 0)
					{
						int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
						if(m_tiles[x][0] == 0)
						{
							append = 1;
							addTile(x);
						}
					}
					break;
				}
			}
		}

		else if(dir == Direction.LEFT)
		{
			for(int i=0; i < 3; i++)
			{
				for(int j=0; j < 4; j++)
				{
					hasMove = false;
					if(m_tiles[i][j] == 0)
					{
						hasMove = true;
						m_tiles[i][j] = m_tiles[i+1][j];
						if(m_tiles[i+1][j] != 0)
						{
							canAddTile = true;
							m_tileMoved[i+1][j] = true;
						}
						m_tiles[i+1][j] = 0;
					}

					if(hasMove == false)
					{
						if(m_tiles[i][j] == 1 && m_tiles[i+1][j] == 2 ||
						   m_tiles[i][j] == 2 && m_tiles[i+1][j] == 1)
						{
							canAddTile = true;
							m_tiles[i][j]   = 3;
							m_tiles[i+1][j] = 0;
							m_tileMoved[i+1][j] = true;
						}

						else if(m_tiles[i][j] == m_tiles[i+1][j] &&
								m_tiles[i][j] != 1 && m_tiles[i][j] != 2)
						{
							canAddTile = true;
							m_tiles[i][j] = m_tiles[i][j]*2;
							m_tiles[i+1][j] = 0;
							m_tileMoved[i+1][j] = true;
						}
					}
				}
			}

			for(int i=0; canAddTile && i < 4; i++)
			{
				if(m_tiles[3][i] == 0)
				{
					int append = 0;
					while(append == 0)
					{
						int x = (int)(Math.random()*1000) % (4-i)+i; //Get a random number from i to 3
						if(m_tiles[3][x] == 0)
						{
							append = 1;
							addTile(4*x+3);
						}
					}
					break;
				}
			}
		}
		
		else if(dir == Direction.RIGHT)
		{
			for(int i=0; i < 3; i++)
			{
				for(int j=0; j < 4; j++)
				{
					hasMove = false;
					if(m_tiles[3-i][j] == 0)
					{
						hasMove = true;
						m_tiles[3-i][j] = m_tiles[2-i][j];
						if(m_tiles[2-i][j] != 0)
						{
							canAddTile = true;
							m_tileMoved[2-i][j] = true;
						}
						m_tiles[2-i][j] = 0;
					}

					if(hasMove == false)
					{
						if(m_tiles[3-i][j] == 1 && m_tiles[2-i][j] == 2 ||
						   m_tiles[3-i][j] == 2 && m_tiles[2-i][j] == 1)
						{
							canAddTile = true;
							m_tiles[3-i][j]   = 3;
							m_tiles[2-i][j]   = 0;
							m_tileMoved[2-i][j] = true;
						}

						else if(m_tiles[3-i][j] == m_tiles[2-i][j] && 
								m_tiles[3-i][j] != 1 && m_tiles[3-i][j] != 2)
						{
							canAddTile = true;
							m_tiles[3-i][j] = m_tiles[3-i][j]*2;
							m_tiles[2-i][j] = 0;
							m_tileMoved[2-i][j] = true;
						}
					}
				}
			}

			for(int i=0; canAddTile && i < 4; i++)
			{
				if(m_tiles[0][i] == 0)
				{
					int append = 0;
					while(append == 0)
					{
						int x = (int)(Math.random()*1000) % (4-i) + i; //Get a random number from i to 3
						if(m_tiles[0][x] == 0)
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

	public boolean testEnd()
	{
		for(int i=0; i < 4; i++)
		{
			for(int j=0; j < 4; j++)
			{
				if(m_tiles[i][j] == 0)
					return false;

				else if(m_tiles[i][j] == 2)
				{
				    if(j < 3 && m_tiles[i][j+1] == 1)
						return false;
					else if(i < 3 && m_tiles[i+1][j] == 1)
						return false;
				}

				else if(m_tiles[i][j] == 1)
				{
				    if(j < 3 && m_tiles[i][j+1] == 2)
						return false;
					else if(i < 3 && m_tiles[i+1][j] == 2)
						return false;
				}

				else if(j < 3 && m_tiles[i][j] == m_tiles[i][j+1])
						return false;

				else if(i < 3 && m_tiles[i][j] == m_tiles[i+1][j])
						return false;
			}
		}
		return true;
	}

	public int getScore()
	{
		int score = 0;
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				score = score + m_tiles[i][j];
		return score;
	}

	public int getValue(int pos)
	{
		return m_tiles[pos%4][pos/4];
	}

	public boolean hasTileMoved(int pos)
	{
		return m_tileMoved[pos%4][pos/4];
	}

	public int getLastValue(int pos)
	{
		return m_lastTiles[pos%4][pos/4];
	}

	public int getLastDir()
	{
		return m_lastDir;
	}
}
