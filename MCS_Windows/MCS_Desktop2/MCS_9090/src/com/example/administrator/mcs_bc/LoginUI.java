package com.example.administrator.mcs_bc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginUI
{
	final JFrame frame;
	JLabel username;
	JLabel password;
	
	JTextField username_input;
	JPasswordField password_input;
	
	JPanel panel;
	
	JButton login_button;
	JButton set_button;
	
	
	
	public LoginUI()
	{
		this.frame = new JFrame("Login");
		
		this.username = new JLabel("Your Username");		
		this.password = new JLabel("Your Password");
		
		this.username_input = new JTextField();
		this.password_input = new JPasswordField();
		
		this.login_button = new JButton("Login");
		this.set_button = new JButton("Reset");
		
		int change = 10;
		this.username.setBounds(200,230 - change,100,25);
		this.password.setBounds(200,260- change,100,25);
		this.username_input.setBounds(320,230- change,170,25);
		this.password_input.setBounds(320,260- change,170,25);
		this.login_button.setBounds(320, 300- change, 80, 25);
		this.set_button.setBounds(405, 300- change, 80, 25);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		this.panel.add(this.username);
		this.panel.add(this.password);
		
		this.panel.add(this.username_input);
		this.panel.add(this.password_input);
		
		this.panel.add(this.login_button);
		this.panel.add(this.set_button);
		
		this.frame.setSize(700,600);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.add(panel);
		this.frame.setVisible(true);
		
		this.set_button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						RegisterUI rui = new RegisterUI();
						
					}
				}
				);
		
		this.login_button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						String password_read = new String(password_input.getPassword());
						String username_read = username_input.getText();
						
						File userprofile = new File("files\\userbasic");
						if(userprofile.exists() == false)
						{
							JOptionPane.showMessageDialog(null, "You haven't got a username");
						}
						
						else
						{
							 try 
							 {
								InputStreamReader read = new InputStreamReader(new FileInputStream(userprofile));
								@SuppressWarnings("resource")
								BufferedReader read2 = new BufferedReader(read);
								String username_read2 = read2.readLine();
								String password_read2 = read2.readLine();
								
								if((username_read.equals(username_read2) != true) || (password_read.equals(password_read2)) != true)
								{
									JOptionPane.showMessageDialog(null, "Wrong, please check your username and password");
								}
								
								else
								{
									MyUI.state = 1;
									JOptionPane.showMessageDialog(null, "You have login successfully");
									frame.dispose();
									
								}
							 } 
							 catch (FileNotFoundException e) 
							 {
								System.out.println("Error");
								e.printStackTrace();
							 } catch (IOException e) {
								System.out.println("Error");
								e.printStackTrace();
							}
						}
					}
				}
				);	
	}
	public String getPassword()
	{
		char[] input_char = new char[100];
		input_char = this.password_input.getPassword();
		String output = new String(input_char);
		return output;
		
	}
	
	public String  getUsername()
	{
		String output = this.username_input.getText();
		return output;
	}
	
	public void set_buttonAction(JFrame frame)
	{
		RegisterUI register_ui = new RegisterUI();
		frame.add(register_ui.panel);
	}
	
}