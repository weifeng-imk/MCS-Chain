package com.example.administrator.mcs_bc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class RegisterUI
{
	final JFrame frame;
	
	JLabel username;
	JLabel password;
	JLabel password2;
	
	JTextField username_input;
	JPasswordField password_input;
	JPasswordField password_input2;
	
	JPanel panel;
	
	JButton ok_button;
	JButton cancel_button;
	
	
	
	
	public RegisterUI()
	{
		this.frame = new JFrame("Reset your username and password");
		
		
		
		this.username = new JLabel("New Username");		
		this.password = new JLabel("New Password");
		this.password2 = new JLabel("Confrim");
		
		this.username_input = new JTextField();
		this.password_input = new JPasswordField();
		this.password_input2 = new JPasswordField();
		
		this.ok_button = new JButton("OK");
		this.cancel_button = new JButton("Cancel");
		
		int change = 10;
		this.username.setBounds(200,230 - change,100,25);
		this.password.setBounds(200,260- change,100,25);
		this.password2.setBounds(200,290- change,100,25);
		this.username_input.setBounds(320,230- change,170,25);
		this.password_input.setBounds(320,260- change,170,25);
		this.password_input2.setBounds(320,290- change,170,25);		
		
		this.ok_button.setBounds(320, 330- change, 80, 25);
		this.cancel_button.setBounds(405, 330- change, 80, 25);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		this.panel.add(this.username);
		this.panel.add(this.password);
		this.panel.add(this.password2);
		
		this.panel.add(this.username_input);
		this.panel.add(this.password_input);
		this.panel.add(this.password_input2);
		
		this.panel.add(this.ok_button);
		this.panel.add(this.cancel_button);
		
		
		this.frame.setSize(700,600);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.add(panel);
		this.frame.setVisible(true);
		
		
		this.ok_button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args)
					{
						String str1 = username_input.getText();
						String str2 = new String(password_input.getPassword());
						String str3 = new String(password_input2.getPassword());
						
						if(str1.length() == 0)
						{
							JOptionPane.showMessageDialog(null, "Please input your username");
						}
						
						else if(str2.length() == 0 || str3.length() == 0)
						{
							JOptionPane.showMessageDialog(null, "Please input your password");
						}
						else if(str2.equals(str3) == false)
						{
							JOptionPane.showMessageDialog(null, "The passwords are not the same");
						}
						else
						{
							File file = new File("files\\userbasic");
						
							if(file.exists() == false)
							{
								try 
								{
									file.getParentFile().mkdirs();
									file.createNewFile();
									

									if(file.exists())
									{
										BufferedWriter write = new BufferedWriter(new FileWriter(file));
										write.write(str1);
										write.write('\n');
										write.write(str2);
										//write.write(str3);
										write.flush(); 
										write.close(); 
										//System.out.println("hello");
										JOptionPane.showMessageDialog(null, "Profile Reset Successfully");
										
										frame.dispose();
									}
								} catch (IOException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							else
							{
								
								try 
								{
									BufferedWriter write = new BufferedWriter(new FileWriter(file));
									write.write(str1);
									write.write('\n');
									write.write(str2);
									//write.write(str3);
									write.flush(); 
									write.close();
									JOptionPane.showMessageDialog(null, "Profile Reset Successfully");
									frame.dispose();
									
									
								} catch (IOException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 
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
	
	public String getPassword2()
	{
		char[] input_char = new char[100];
		input_char = this.password_input2.getPassword();
		String output = new String(input_char);
		return output;
		
	}
	
	public String  getUsername()
	{
		String output = this.username_input.getText();
		//System.out.println(output);
		return output;
	}
}
