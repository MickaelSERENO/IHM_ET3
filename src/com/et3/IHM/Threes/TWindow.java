package com.et3.IHM.Threes;

import com.et3.IHM.Threes.Start;
import com.et3.IHM.Threes.End;
import com.et3.IHM.Threes.InGame;

import javax.swing.JFrame;

public class TWindow extends JFrame
{
	private InGame m_inGame;
	private Start  m_start;
	private End    m_end;

	public TWindow()
	{
		  //Init the window
		  super();
		  setTitle("Threes");
		  setSize(800, 600);
		  setResizable(false);
		  setLocationRelativeTo(null);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  //Init the panel
		  m_inGame = new InGame(this);
		  m_start  = new Start(this);
		  m_end    = new End(this);
		  setContentPane(m_start);

		  //Show the window
		  setVisible(true);
	}

	public void setCurrentPanel(String nextPanel)
	{
		if(nextPanel.equals("InGame"))
		{
			setContentPane(m_inGame);
			m_inGame.resume();
		}
		else if(nextPanel.equals("Start"))
		{
			setContentPane(m_start);
			m_start.resume();
		}
		else if(nextPanel.equals("End"))
		{
			setContentPane(m_end);
			m_end.resume(m_inGame.getScore());
		}
		setVisible(true);
	}
}
