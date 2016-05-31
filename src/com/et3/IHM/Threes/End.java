package com.et3.IHM.Threes;

import com.et3.IHM.Threes.TWindow;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class End extends JPanel
{
	private JButton m_endButton;
	private JLabel  m_score;
	private TWindow m_currentFrame;

	public End(TWindow frame)
	{
		m_currentFrame = frame;
		m_score = new JLabel("");
		m_endButton = new JButton("Restart");
		m_endButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m_currentFrame.setCurrentPanel("Start");
			}
		});

		add(m_score);
		add(m_endButton);
	}

	public void resume(int score)
	{
		m_score.setText("Perdu ! \t Score : " + score);
	}
}
