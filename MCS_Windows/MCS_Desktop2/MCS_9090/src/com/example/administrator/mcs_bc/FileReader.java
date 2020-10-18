package com.example.administrator.mcs_bc;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
 
class FileReader extends JFrame implements ActionListener
{
 
 


	/**
	 * 
	 */
	private static final long serialVersionUID = -8445798253638202261L;
	JButton btn = null;
	public JButton btn2 = null;
 
    JTextField textField = null;
 
    public FileReader()
    {
        this.setTitle("choose");
        FlowLayout layout = new FlowLayout();
        JLabel label = new JLabel("choose file:");
        textField = new JTextField(30);
        btn = new JButton("Go");
        btn2 = new JButton("OK");
 
        layout.setAlignment(FlowLayout.LEFT);
        this.setLayout(layout);
        this.setBounds(400, 200, 600, 70);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btn.addActionListener(this);
        this.add(label);
        this.add(textField);
        this.add(btn);
        this.add(btn2);
 
    }
 

 
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showDialog(new JLabel(), "OK");
        File file = chooser.getSelectedFile();
        if(file!= null)
        {
        	textField.setText(file.getAbsoluteFile().toString());
        }
    }
}





