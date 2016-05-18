package com.et3.IHM.Threes;

import com.et3.IHM.Threes.TWindow;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Start extends JPanel
{
	private JButton m_startButton;
	private TWindow m_currentFrame;
	private ActionListener m_buttonListener;

	public Start(TWindow frame)
	{
		m_currentFrame = frame;
		m_buttonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				m_currentFrame.setCurrentPanel("InGame");
			}
		};

		m_startButton = new JButton("Start");
		m_startButton.addActionListener(m_buttonListener);
		add(m_startButton);
	}

	public void resume()
	{
	}
}
