package com.example.administrator.mcs_bc;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class WarningWindow
{
	JFrame warning_frame;
	JPanel warning_panel;
	JLabel warning_label;
	
	public WarningWindow()
	{
		this.warning_frame = new JFrame("Warning");
		this.warning_panel = new JPanel();
		this.warning_label = new JLabel("null", JLabel.CENTER);
		warning_frame.setSize(400, 200);
		warning_panel.setBounds(20, 20, 360, 160);
		warning_label.setBounds(50, 50, 300, 50);
		
		warning_panel.setLayout(null);
		
		warning_panel.add(warning_label);
		warning_frame.add(warning_panel);
		
		warning_frame.setResizable(false);
		warning_frame.setVisible(true);
	}
	
	
	public WarningWindow(String str_input)
	{
		this.warning_frame = new JFrame("Warning");
		this.warning_panel = new JPanel();
		this.warning_label = new JLabel(str_input, JLabel.CENTER);
		warning_frame.setSize(400, 200);
		warning_panel.setBounds(20, 20, 360, 160);
		warning_label.setBounds(50, 50, 300, 50);
		
		warning_panel.setLayout(null);
		
		warning_panel.add(warning_label);
		warning_frame.add(warning_panel);
		
		warning_frame.setResizable(false);
		warning_frame.setVisible(true);
	}
	
	public WarningWindow(String str_input1, String str_input2)
	{
		this.warning_frame = new JFrame(str_input1);
		this.warning_panel = new JPanel();
		this.warning_label = new JLabel(str_input2, JLabel.CENTER);
		warning_frame.setSize(400, 200);
		warning_panel.setBounds(20, 20, 360, 160);
		warning_label.setBounds(50, 50, 300, 50);
		
		warning_panel.setLayout(null);
		
		warning_panel.add(warning_label);
		warning_frame.add(warning_panel);
		
		warning_frame.setResizable(false);
		warning_frame.setVisible(true);
	}
}

//class InputWindow
//{
//	public JFrame frame;
//	public JPanel panel;
//	public JButton button;
//	public JTextField textfield;
//	public JLabel label;
//	
//	public InputWindow()
//	{
//		
//	}
//	
//	public InputWindow(String title, String msg)
//	{
//		this.frame = new JFrame(title);
//		this.panel = new JPanel();
//		this.label = new JLabel(msg);
//		this.button = new JButton("OK");
//		this.textfield = new JTextField();
//		
//		
//		
//	}
//}














