package com.example.administrator.mcs_bc;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class mainPane1
{ 
	JLabel username;
	JLabel dateandtime;
	mainPane1Information information;
	mainPane1TaskTable task_table;
	mainPane1Operation operation_list;
	mainPane1Info information2;
	
	
	
	public mainPane1() throws IOException
	{	 
		this.dateandtime = new JLabel();
		this.information = new mainPane1Information();
		this.task_table = new mainPane1TaskTable();
		this.operation_list = new mainPane1Operation();
		this.information2 = new mainPane1Info();

		File file = new File("files\\userbasic");
		InputStreamReader read1 = new InputStreamReader(new FileInputStream(file));
		BufferedReader read2 = new BufferedReader(read1);
		String user_read_str = read2.readLine();
		
		this.username = new JLabel();
		this.username.setBounds(15, 15, 80, 25);
		this.username.setText("User: " + user_read_str);
		this.dateandtime.setBounds(700, 15, 200, 25 );
		this.dateandtime.setText(DataandTime.getDataandTime());	
//		this.task_table
	}
	
	public void bind(JPanel panel)
	{
		panel.add(this.username);
		panel.add(this.dateandtime);
		
		this.information.bind(panel);
		this.task_table.bind(panel);
		this.operation_list.bind(panel);
		this.information2.bind(panel);
	}
}

class mainPane1Information
{
	public JComboBox consult_box;	
	public JTextArea information_present_area;
	public JScrollPane information_scroll;


	
	
	public mainPane1Information()
	{
		//Function 1
		String[] consult_labels = {"1 Blockchain Information", "2 Communication List", "3 Personal Profile", "4 Node Profile List"};
		this.consult_box = new JComboBox(consult_labels);
		this.consult_box.setBounds(675, 50, 200, 25);
		
		this.information_present_area = new JTextArea();
		this.information_present_area.setLineWrap(true);		
		this.information_present_area.setBorder(BorderFactory.createLineBorder(Color.black));	
		this.information_present_area.setEditable(false);
		
		this.information_scroll = new JScrollPane();
		this.information_scroll.setViewportView(this.information_present_area);
		this.information_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.information_scroll.setBounds(675, 80, 200, 625);
		
		//Action
		this.consult_box.addItemListener(new java.awt.event.ItemListener()
		{
	    	public void itemStateChanged(java.awt.event.ItemEvent e)
	    	{
	    		if(e.getStateChange() == ItemEvent.SELECTED)
	    		{
	    			int a = consult_box.getSelectedIndex();
	    			switch (a)
	    			{
	    				case 0:
	    				{
	    					
	    					information_present_area.setText(null);
	    					int number = Test3.node.current_block_list.size();
	    					if(number > 0)
	    					{
	    						for(int loop_present = 0; loop_present < number; loop_present ++) {
		    						information_present_area.append(Test3.node.current_block_list.get(loop_present).no+"\n");
	    						}
	    					}
	    					else
	    					{
	    						information_present_area.setText("No Block now\n");
	    					}
	    					break;
	    				}
	    				case 1:
	    				{
	    					information_present_area.setText(null);
	    					break;
	    				}
	    				
	    				case 2:
	    				{
	    					information_present_area.setText(null);
	    					information_present_area.setText(Test3.msg_comm);
	    					try {
								information_present_area.append("\n IP Address" 
										+ InetAddress.getLocalHost().getHostAddress()
										+ "\n:" + Test3.node.myport);
								
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	    					break;
	    				}
	    				
	    				case 3:
	    				{
	    					information_present_area.setText(null);
	    					break;
	    				}
	    			}
	    		}
	    	}
	    });		
	}
	

	
	public void bind(JPanel panel)
	{
		panel.add(this.consult_box);
		panel.add(this.information_scroll);
	}
}

class mainPane1TaskTable
{
	JTable task_list;
	JScrollPane task_list_scroll;
	
	public mainPane1TaskTable()
	{			
//		String[] headers = { "No. ", "Requirement", "Description" }; 
//		Object[][] cellData = {{2, 3, 4}, {1, 3, 4}};
		
		Vector<String> headers = new Vector<String>();
		Vector<Vector<String>> cellData = new Vector<Vector<String>>();
		
		headers.add("Task");
		headers.add("Subtask");
		headers.add("Requirements");
		headers.add("Description");
		headers.add("Worker Num");
		headers.add("Payment");
		
		for(int loop = 0; loop < Test3.node.task_list_curr.size(); loop++)
		{
			for(int loop1 = 0; loop1 < Test3.node.task_list_curr.get(loop).content.subtask_list.size(); loop1 ++)
			{
				Vector<String> vec_temp = new Vector<String>();
				
				vec_temp.add(String.valueOf(loop));
				vec_temp.add(String.valueOf(loop1));
				vec_temp.add(String.valueOf(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_no));
				vec_temp.add(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_des.value);
			}
		}
		
		DefaultTableModel model = new DefaultTableModel(cellData, headers)
		{ /**
			 * 
			 */
			private static final long serialVersionUID = -1200403005500178755L;
			public boolean isCellEditable(int row, int column) 
			{ 
				return false; 
			}
		};
		
		this.task_list = new JTable(model);		
		this.task_list_scroll = new JScrollPane();
		this.task_list_scroll.setViewportView(this.task_list);
		this.task_list_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.task_list_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		this.task_list_scroll.setBounds(15, 80, 635, 250);
		this.task_list_scroll.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void bind(JPanel panel)
	{
		panel.add(this.task_list_scroll);
	}

	public void reSetTable(JPanel panel)
	{
		Vector<String> headers = new Vector<String>();
		Vector<Vector<String>> cellData = new Vector<Vector<String>>();
		
		headers.add("Task");
		headers.add("Subtask");
		headers.add("Requirements");
		headers.add("Description");
		headers.add("Worker Num");
		headers.add("Payment");
		
		while(true)
		{				
			if(Test3.node.task_read_allowed == 1)
			{
				Test3.node.task_read_allowed = 0;
				int row_no = -1;
				for(int loop = 0; loop < Test3.node.task_list_curr.size(); loop++)
				{
						for(int loop1 = 0; loop1 < Test3.node.task_list_curr.get(loop).content.subtask_list.size(); loop1 ++)
						{
							if(loop1 == 0)
							{								
/*
								for(int loop2 = 0; loop2 < Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).attr_required_list.size(); loop2 ++)
								{*/
									Vector<String> vec_temp = new Vector<String>();
									/*if(loop2 == 0)*/
						/*			{*/
										vec_temp.add(String.valueOf(loop));
										vec_temp.add(String.valueOf(loop1));
										vec_temp.add(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).requirements)/*SystemParam.getAttrList()[Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).requirements;get(loop1).attr_required_list.get(loop2).value])*/;
										vec_temp.add(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_des.value);
										vec_temp.add(String.valueOf(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).worker_num));
										vec_temp.add(String.valueOf(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).pay_budget.value));
										cellData.add(vec_temp);
									/*}	
									else
									{
										vec_temp.add(null);
										vec_temp.add(null);
										vec_temp.add(SystemParam.getAttrList()[Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).attr_required_list.get(loop2).value]);
										vec_temp.add(null);
										vec_temp.add(null);
										vec_temp.add(null);
										cellData.add(vec_temp);
									}*/
										
/*								}*/
							}
							else
							{
								/*for(int loop2 = 0; loop2 < Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).attr_required_list.size(); loop2 ++)
								{*/
									Vector<String> vec_temp = new Vector<String>();
									/*if(loop2 == 0)
									{*/
										vec_temp.add(null);
										vec_temp.add(String.valueOf(loop1));
										vec_temp.add(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).requirements/*SystemParam.getAttrList()[Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).attr_required_list.get(loop2).value]*/);
										vec_temp.add(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_des.value);
										vec_temp.add(String.valueOf(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).worker_num));
										vec_temp.add(String.valueOf(Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).pay_budget.value));
										cellData.add(vec_temp);
									/*}
									else
									{*/
				/*						vec_temp.add(null);
										vec_temp.add(null);
										vec_temp.add(SystemParam.getAttrList()[Test3.node.task_list_curr.get(loop).content.subtask_list.get(loop1).attr_required_list.get(loop2).value]);
										vec_temp.add(null);
										vec_temp.add(null);
										vec_temp.add(null);*/
								/*		cellData.add(vec_temp);
									}
								}*/
								//vec_temp.add(str);

							}							
							
						}
				}
				Test3.node.task_read_allowed = 1;
				break;
			}
		}

		DefaultTableModel model = new DefaultTableModel(cellData, headers)
		{ /**
			 * 
			 */
			private static final long serialVersionUID = -1200403005500178755L;
			public boolean isCellEditable(int row, int column) 
			{ 
				return false; 
			}
			
		};
		
		this.task_list = new JTable(model);		
		this.task_list.setRowHeight(1, 20);
		
		
		this.task_list_scroll = new JScrollPane();
		this.task_list_scroll.setViewportView(this.task_list);
		this.task_list_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.task_list_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		this.task_list_scroll.setBounds(15, 80, 635, 250);
		this.task_list_scroll.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel.add(this.task_list_scroll);

	}
}

class mainPane1Operation
{
	public JButton feedback_gen;
	public JButton data_upload;
	public JButton task_gen;
	public JButton pay_set;
	public JButton commun_list;
	public JButton commun_list_poten;
	public JButton poten_list_add;
	public JButton worker_selection;
	public JButton data_selection;
	//public JButton block_check;
	
	public mainPane1Operation()
	{
		this.feedback_gen = new JButton("Feedback");
		this.data_upload = new JButton("Upload Data");
		this.task_gen = new JButton("New Task");
		this.pay_set = new JButton("Accept a Task");
		this.commun_list = new JButton("Communication List");
		this.commun_list_poten = new JButton ("Potential List");
		this.poten_list_add = new JButton("Add");
		this.worker_selection = new JButton("Select Worker");
		this.data_selection = new JButton("Select Data");
		
		//this.block_check = new JButton("current block");
		
		this.feedback_gen.setBounds(15, 350, 130, 25);
		this.data_upload.setBounds(15, 380, 130, 25);
		this.task_gen.setBounds(15, 410, 130, 25);
		this.pay_set.setBounds(15, 440, 130, 25);
		this.commun_list.setBounds(15,  470,  130,  25);
		this.commun_list_poten.setBounds(15, 500, 130, 25);
		this.poten_list_add.setBounds(15,530, 130, 25 );
		this.worker_selection.setBounds(15, 560, 130, 25);
		this.data_selection.setBounds(15, 590, 130, 25);
		
		this.data_selection.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						TextPresent9 testwindow = new TextPresent9();
					}
				});
		
		this.poten_list_add.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args)
					{
						TextPresent7 textwindow = new TextPresent7();
					}
				}
				);
		
		this.feedback_gen.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						TextPresent3 textwindow = new TextPresent3();

					}
				}
				);
		
		this.data_upload.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args3)
					{
						TextPresent4 textwindow = new TextPresent4();

					}
				}
				);
		
		this.pay_set.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{						
						TextPresent6 textwindow = new TextPresent6();
					}
				}
				);
		
		
		this.task_gen.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						try {
							TextPresent5 textwindow = new TextPresent5();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				);
		
		
		this.commun_list.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					TextPresent2 textwindow = new TextPresent2();

					
					if(Test3.node.incoming_list.ip_list.size() == 0)
					{
						textwindow.textfield1.append("There is no valid incoming node");
						
					}
					
					else
					{
						for(int loop = 0; loop < Test3.node.incoming_list.ip_list.size(); loop ++)
						{
							textwindow.textfield1.append("IP Address\t");
							/*textwindow.textfield1.append(BasicTool.printID(Test3.node.incoming_list.id_list.get(loop)) + '\n');*/
							textwindow.textfield1.append(Test3.node.incoming_list.ip_list.get(loop) + "\n");
						}	
					}
					
					
					if(Test3.node.outgoing_list.ip_list.size() == 0)
					{
						textwindow.textfield2.append("There is no valid outgoing node");
					}
					else
					{
						for(int loop = 0; loop < Test3.node.outgoing_list.ip_list.size(); loop ++)
						{
							textwindow.textfield2.append("IP Address\t");
							textwindow.textfield2.append(Test3.node.outgoing_list.ip_list.get(loop) + '\n');
						}
					}
					
				}
			}
				);
		
		this.commun_list_poten.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextPresent2 textwindow = new TextPresent2();
				textwindow.label1.setText("Potential Incoming List");
				textwindow.label2.setText("Potential Outgoing List");
				
				if(Test3.node.incoming_potential.size() == 0)
				{
					textwindow.textfield1.setText("There is no potential incoming nodes in your list");
				}
				else
				{
					for(int loop = 0; loop < Test3.node.incoming_potential.size(); loop ++)
					{
						textwindow.textfield1.append("IP Address\t");
						textwindow.textfield1.append(Test3.node.incoming_potential.get(loop) + '\n');
					}
				}
				
				if(Test3.node.outgoing_potential.size() == 0)
				{
					textwindow.textfield2.setText("There is no potential outgoing nodes in your list");
				}
				else
				{
					for(int loop = 0; loop < Test3.node.outgoing_potential.size(); loop ++)
					{
						textwindow.textfield2.append("IP Address\t");
						textwindow.textfield2.append(Test3.node.outgoing_potential.get(loop) + '\n');
					}
				}
				
				

//				

			
			}
		}
			);
		
		this.worker_selection.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args)
					{
						TextPresent8 textwindow = new TextPresent8();
					}
				});
		
	}
	
	
	
	public void bind(JPanel panel)
	{
		panel.add(this.feedback_gen);
		panel.add(this.data_upload);
		panel.add(this.task_gen);
		panel.add(this.pay_set);
		panel.add(this.commun_list);
		panel.add(this.commun_list_poten);
		panel.add(this.poten_list_add);
		panel.add(this.worker_selection);
		panel.add(this.data_selection);
	}
	
	
}

class mainPane1Info
{
	public JTextArea info_area;
	public JScrollPane info_area_scroll;
	
	public mainPane1Info()
	{
		this.info_area = new JTextArea();
		this.info_area.setEditable(false);
		
		this.info_area_scroll = new JScrollPane();
		this.info_area_scroll.setViewportView(this.info_area);
		this.info_area_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.info_area_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.info_area_scroll.setBounds(150, 350, 500, 350);
	}
	
	public void bind(JPanel panel)
	{
		panel.add(this.info_area_scroll);
	}
	
	public int setText(String str)
	{
		this.info_area.setText(str);
		return 1;
	}
	
	public int appendText(String str)
	{
		this.info_area.append(str);
		return 1;
	}
	
	public String getText()
	{
		return this.info_area.getText();
	}
}

