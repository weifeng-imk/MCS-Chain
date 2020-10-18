package com.example.administrator.mcs_bc;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

class MyUI
{
	//main frame
	JFrame main_frame;
	JPanel main_panel;
	
	//Button
	public JButton login_button;
	
	//state
	public static Integer state;
	mainPane1 function1;
	
	
	//procedure 1
	
	
	public MyUI() throws IOException
	{
		
		state = new Integer(0);
		  
		this.main_frame = new JFrame("MCS-BC");
		this.main_frame.setSize(200,  500);
		this.main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.main_frame.setResizable(false);
		
		this.main_panel = new JPanel();
		this.main_panel.setLayout(null);
		
		this.login_button = new JButton("Login");
		//this.login_button.setBackground(bg);
		this.login_button.setBounds(60, 200, 80, 25);
		
		
		this.main_panel.add(this.login_button);
		this.main_frame.add(this.main_panel);
		this.main_frame.setVisible(true);
		
		//PROCEDURE 1
		this.function1 = new mainPane1();
		

		
		//=================================================================
		this.login_button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				LoginUI lui = new LoginUI();
			}
		}
		);	
		
		
		
	}
	
	//procedure 1
	
	
	public void setMainPanel() throws IOException
	{
		switch (MyUI.state)
		{
			case 1: 
			{
				this.main_panel.remove(this.login_button);
				this.main_frame.setSize(900, 750);			
				File file = new File("files\\userbasic");
				if(file.exists() == true)
				{
					function1.bind(this.main_panel);
					this.main_frame.repaint();
					this.main_frame.setVisible(true);
				}
				
				
			}
			case 2:
		}
	}
	
}