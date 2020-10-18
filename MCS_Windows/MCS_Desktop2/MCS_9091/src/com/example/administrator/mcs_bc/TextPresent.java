package com.example.administrator.mcs_bc;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

class TextPresent
{
	public JFrame frame;
	public JPanel panel;
	public JTextArea textfield;
	
	public TextPresent()
	{
		this.frame = new JFrame("Information");
		this.panel = new JPanel();
		this.textfield = new JTextArea();
		
		
		this.panel.setLayout(null);
		
		this.frame.setBounds(500, 500, 500, 500);
		this.panel.setBounds(10, 10, 480, 480);
		this.textfield.setBounds(20, 20, 460, 460);
		
		this.frame.add(this.panel);
		this.panel.add(this.textfield);
		
		this.frame.setVisible(true);		
	}	
}

class TextPresent2//communication List;
{
	public JFrame frame;
	public JPanel panel;
	public JTextArea textfield1;
	public JTextArea textfield2;
	public JScrollPane scrollfield1;
	public JScrollPane scrollfield2;
	public JLabel label1;
	public JLabel label2;
	
	public TextPresent2()
	{
		this.frame = new JFrame("Information");
		this.panel = new JPanel();
		this.scrollfield1 = new JScrollPane();
		this.scrollfield2 = new JScrollPane();
		this.textfield1 = new JTextArea();
		this.textfield2 = new JTextArea();
		this.label1 = new JLabel("Incoming Node List");
		this.label2 = new JLabel("Outgoing Node List");
		
		
		
		this.textfield1.setEditable(false);
		this.textfield2.setEditable(false);
		
		this.textfield1.setLineWrap(true);
		this.textfield2.setLineWrap(true);
		
		
		this.panel.setLayout(null);
		this.frame.setResizable(false);
		
		this.frame.setBounds(20, 20, 600, 630);
		this.panel.setBounds(10, 10, 580, 580);
		
		this.label1.setBounds(20, 20, 270, 20);
		this.label2.setBounds(320, 20, 270, 20);
		
		this.scrollfield1.setBounds(20, 50, 270, 540);		
		this.textfield1.setBounds(30, 60, 250, 520);
		
		this.scrollfield2.setBounds(310, 50, 270, 540);
		this.textfield2.setBounds(320, 60, 250, 520);
		
		this.frame.add(this.panel);
		
		this.panel.add(this.label1);
		this.panel.add(this.label2);
		
		this.scrollfield1.setViewportView(this.textfield1);
		this.scrollfield2.setViewportView(this.textfield2);
		this.panel.add(this.scrollfield1);
		this.panel.add(this.scrollfield2);
		
		this.scrollfield1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollfield1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollfield2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollfield2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.frame.setVisible(true);		
	}	
}


class TextPresent3 //Button of generation of feedback
{
	public JFrame frame;
	public JPanel panel;
	public JTextField textfield;
	public JLabel label;
	public JButton button;
	
	public TextPresent3()
	{
		this.frame = new JFrame();
		this.panel = new JPanel();
		this.textfield = new JTextField();
		this.label = new JLabel();
		this.button = new JButton("OK");
		
		this.frame.setBounds(20,  20, 400, 200);
		//this.panel.setBounds(10,  10, 380, 180);
		this.label.setBounds(100, 50, 90, 30);
		this.textfield.setBounds(200, 50, 50, 30);
		this.button.setBounds(170, 100, 60, 30);
		
		this.frame.setTitle("Select a Task");
		this.label.setText("Input the task id");
		
		this.panel.add(this.label);
		this.panel.add(this.textfield);
		this.panel.add(this.button);
		
		this.frame.add(this.panel);
		
		this.panel.setLayout(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		
		this.button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						String str_temp = textfield.getText();
						//int int_input = Integer.valueOf(str_temp);
						
						Integer no;
						try
						{
							no = Integer.parseInt(str_temp);
						} catch (NumberFormatException e)
						{
							WarningWindow ww = new WarningWindow("The task id is not valid");
							frame.dispose();
							return;
						}
						
						JFrame frame2 = new JFrame("Feedback Generation");
						JPanel panel2 = new JPanel();
						JScrollPane scroll_pane = new JScrollPane();
						JButton button2 = new JButton("OK");
						
						
						Vector<Vector<String>> data_content = new Vector<Vector<String>>();
						
						{						

							
							////////////////////////////////////////////////////////////////////////////////////
							//For testing
							for(int loop = 0; loop < Test3.node.worker_selected.get(no).size(); loop ++)
							{
								Vector<String> vec_temp = new Vector<String>();
								vec_temp.add(String.valueOf(loop));
								vec_temp.add(BasicTool.printID(Test3.node.worker_selected.get(no).get(loop)));
								vec_temp.add("Worker");
								
								data_content.add(vec_temp);
							}
							////////////////////////////////////////////////////////////////////////////////////////
							
							
							Vector<String> header = new Vector<String>();
							header.add("No.");
							header.add("ID");
							header.add("Role");
							header.add("Rate");
							
							DefaultTableModel model = new DefaultTableModel(data_content, header)
							{
								/**
								 * 
								 */
								private static final long serialVersionUID = -7640448828960692606L;

								public boolean isCellEditable(int row, int column) 
								{ 
									if((column ==  3)&&(row < getRowCount()))
									{
										return true;
									}
									else
									{
										return false;
									}
								}
							};
							JTable table = new JTable(model);
							
							scroll_pane.setViewportView(table);
							scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							panel2.setLayout(null);
							
							panel2.add(scroll_pane);
							panel2.add(button2);
							frame2.add(panel2);
							
							
							frame2.setBounds(10, 10, 720, 670);						
							scroll_pane.setBounds(20, 20, 680, 560);
							button2.setBounds(330, 605, 60, 30);
							
							frame2.setResizable(false);
							frame2.setVisible(true);						
	
							//ArrayList<Double> input = new ArrayList<Double>();
							
							ArrayList<FBD_t> fbd = new ArrayList<FBD_t>();
							
							button2.addActionListener(new ActionListener()
									{
										@Override
										public void actionPerformed(ActionEvent args2)
										{
											fbd.clear();
											
											for(int loop = 0; loop < Test3.node.worker_selected.get(no).size(); loop ++)
											{
												String str_input = new String();
												str_input = (String) table.getValueAt(loop, 3);
												
												if(str_input == null)
												{
													WarningWindow ww = new WarningWindow(String.valueOf(loop) + "You haven't finish all the feedback");
													break;
												}
												else
												{
													//input.add(Double.parseDouble(str_input));
													FBD_t fbd_temp = new FBD_t();
													fbd_temp.evaluatee = Test3.node.worker_selected.get(no).get(loop);
													fbd_temp.value = Double.parseDouble(str_input);
													fbd_temp.bid_hash = Test3.node.worker_selected_bid.get(no).get(loop);
													fbd.add(fbd_temp);
													
													if(loop == Test3.node.worker_selected.get(no).size() - 1)
													{
														Test3.node.mining_allowed = 0;
														FB_t fb_temp = new FB_t();
														Req_t req_temp = null;
														try {
															req_temp = new Req_t();
														} catch (UnknownHostException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														
														fb_temp.From.value = Test3.node.node_info.id.value;
														fb_temp.pk_sender.value.set(Test3.node.node_info.pk.value);
														fb_temp.req_type = 7;
														fb_temp.trans_type = 999;
														fb_temp.task_id = Test3.node.task_list_curr.get(no).task_id;
														
														
														for(int loop2 = 0; loop2 < Test3.node.worker_selected.get(no).size(); loop2 ++)
														{
															fb_temp.content = fbd.get(loop2);
															try {
																req_temp = new Req_t(fb_temp);
															} catch (IOException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
															
															String msg = new String();
															msg = BasicTool.preSign(req_temp);
															try {
																req_temp.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
															} catch (NoSuchAlgorithmException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
															
															try {
																fb_temp = new FB_t(req_temp);
																while(true)
																{
																	if(Test3.node.fb_read_allowed == 1)
																	{
																		Test3.node.fb_read_allowed = 0;
																		
																		Test3.node.feedback_list_collected.get(no).add(fb_temp);
																		Test3.node.feedback_list_collected_record.get(no).add(0);
																		
																		Test3.node.fb_read_allowed = 1;
																		break;
																	}
																}
																BasicTool.BroadcastMsg(req_temp);
															} catch (IOException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (ClassNotFoundException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
														}
														
														if(Test3.node.undecided_block_list.size() == 0)
														{
															Test3.node.blockgen();
														}
													
														
														WarningWindow test = new WarningWindow("Message", "You have finished the generation of the feedback");
														Test3.node.mining_allowed = 1;
														frame2.dispose();
													}
												}
											}
										}
									}
									);
							
							//you need to add further prcessing on these data
							
							frame.dispose();
						}
					}
				}
				);
		
	}
}



class TextPresent4 //upload data
{
	public JFrame frame;
	public JPanel panel;
	public JTextField text_field;
	public JTextField text_field2;
	public JTextArea text_area;
	public JButton button1;
	public JButton button2;
	public JButton button3;
	public JScrollPane scroll_pane;
	public JLabel label;
	public JLabel label2;
	
	ArrayList<String> location_list;
	
	public TextPresent4()
	{
		this.location_list = new ArrayList<String>();
		
		this.frame = new JFrame("Upload Data");
		this.panel = new JPanel();
		this.text_field = new JTextField();
		this.text_field2 = new JTextField();
		this.text_area = new JTextArea();
		
		this.button1 = new JButton("Select a file");
		this.button2 = new JButton("OK");
		this.button3 = new JButton("Preview");
		this.scroll_pane = new JScrollPane();
		this.label = new JLabel("Input the task");
		this.label2 = new JLabel("Input the subtask");
		
		this.frame.setSize(620, 520);		
		this.panel.setLayout(null);
		
		this.panel.setBounds(10, 10, 600, 500);
		this.label.setBounds(20, 20, 285, 30);
		this.text_field.setBounds(190, 20, 285, 30);
		this.label2.setBounds(20, 55, 285, 30);
		this.text_field2.setBounds(190, 55, 285, 30);
		this.scroll_pane.setBounds(20, 90, 460, 370);
		this.scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scroll_pane.setViewportView(this.text_area);
		this.text_area.setEditable(false);
		this.button1.setBounds(500, 90, 100, 30);
		this.button2.setBounds(500, 140, 100, 30);
		this.button3.setBounds(500, 190, 100, 30);
		
		this.panel.add(this.text_field);
		this.panel.add(this.text_field2);
		this.panel.add(this.button1);
		this.panel.add(this.button2);
		this.panel.add(this.button3);
		this.panel.add(this.scroll_pane);
		this.panel.add(this.label);
		this.panel.add(this.label2);
		
		this.frame.add(this.panel);
		
		
		
		
		this.button1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent action)
					{
						FileReader file_reader = new FileReader();
						file_reader.btn2.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent action2)
									{
										String str_read = new String();
										
										str_read = file_reader.textField.getText();
										if(str_read == null)
										{
											WarningWindow warning_window = new WarningWindow("No images chosen");
										}
										else
										{
											location_list.add(str_read);
											text_area.append(str_read + '\n');
											file_reader.dispose();
										}
									}
								}
								);
					}
				}
				);
		this.button2.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						//you can transfer the data directly
						//String str_input = text_field.getText();
						
				        Integer int_taskid = Integer.parseInt(text_field.getText());
				        Integer int_subid = Integer.parseInt(text_field2.getText());
						
						if(location_list.size() == 0)
						{
							WarningWindow ww = new WarningWindow("You have not selected any images");
							frame.dispose();
						}
						
						else if(int_taskid == null || int_subid == null)
						{
							WarningWindow ww = new WarningWindow("You have not selct a task/subtask");
							frame.dispose();
						}
						else if(int_taskid >= Test3.node.worker_selected.size())
						{
							WarningWindow ww = new WarningWindow("Invalid Task");
							frame.dispose();
						}
						
						else if(int_subid >= Test3.node.task_list_curr.get(int_taskid).content.subtask_list.size())
						{
							WarningWindow ww = new WarningWindow("Invalid subtask id");
							frame.dispose();
						}
						else
						{
							int int_flag = -1;
							for(int loop = 0; loop < Test3.node.worker_selected.get(int_taskid).size(); loop ++)
							{
								if(Test3.node.worker_selected.get(int_taskid).get(loop).value.equals(Test3.node.node_info.id.value))
								{
									int_flag = 1;
									break;
								}
							}
							
							if(int_flag == -1)
							{
								WarningWindow ww = new WarningWindow("Your are not the worker of this task");
							}
							
							else
							{
								Data_t data = new Data_t();		

								
								File file = null;
								
								for(int loop = 0; loop < location_list.size(); loop ++)
								{
									file = new File(location_list.get(loop));
									FileInputStream file_read;
									try {
										file_read = new FileInputStream(file);
										
										if(file_read != null)
										{
											@SuppressWarnings("resource")
											FileInputStream read_stream = new FileInputStream(file);
											FileOutputStream fos = new FileOutputStream(new File(String.valueOf(loop) + ".jpg"));
											
									        byte[] in = new byte[1024];
									        byte[] byte_arr_temp1 = new byte[0];
									        int len = 0;
									        int len_accu = 0;
									        while((len= read_stream.read(in)) != -1)
									        {
									        	if(len_accu == 0)
									        	{
									        		len_accu += len;
									        		byte_arr_temp1 = in.clone();
									        	}
									        	else
									        	{
										        	len_accu += len;
										        	byte_arr_temp1 = BasicTool.arraycat(byte_arr_temp1, in);
									        	}

									        }


									        //fos.write(byte_arr_temp1, 0, len_accu);
											byte[] test_temp = new byte[len_accu];
											
											System.arraycopy(byte_arr_temp1, 0, test_temp, 0, len_accu);
									        data.content.data.add(test_temp);
									        data.content.len_list.add(len_accu);
									        

									        
									        data.From = Test3.node.node_info.id;
									        data.req_type = 6;
									        data.pk_sender = Test3.node.node_info.pk;
									        //data.task_id.value = text_area.getText();
									        data.trans_type = 3; 
									        data.task_id.value = Test3.node.task_list_curr.get(int_taskid).task_id.value;
									        data.sub_id = Test3.node.task_list_curr.get(int_taskid).content.subtask_list.get(int_subid).subtask_no;
									        
									        
									        //data.content.data.add(e)
									        

									        Req_t req = new Req_t(data);
									        String msg = BasicTool.preSign(req);
									        req.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
									        msg = req.serialize();						        							        
					
									        
											Socket socket =  new Socket();
											socket.connect(new InetSocketAddress(Test3.node.task_list_curr.get(int_taskid).ip_addr, SystemParam.GotGlobalPort()), 3000);
									        
											if(socket!= null)
											{
												ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
												if(out != null)
												{
													out.writeObject(req);
												}
											}
											
											//for test
											try {
												Data_t data_test = new Data_t(req);
												File file_test = new File("files\\test"+ ".jpg");
												file_test.createNewFile();
												FileOutputStream fos_test = new FileOutputStream(file_test);
												fos_test.write(data_test.content.data.get(0));
												System.out.println("Testing: writing files\t len: \t"+ data.content.len_list.get(0)+"\t"+data.content.data.get(0).length);
												

											} catch (ClassNotFoundException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											//for test
					        
									        
									        read_stream.close();
									        fos.flush();
									        fos.close();
									        
									        data.content.data = new ArrayList<byte[]>();
									        data.trans_type = 1;
									        req = new Req_t(data);
									        msg = BasicTool.preSign(req);
									        req.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
									        BasicTool.BroadcastMsg(req);
								        
									    }
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
								frame.dispose();	
							}
						}
						

					}
				}
				);
		
		this.button3.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent action3)
					{
						
						if(location_list.size() != 0)
						{	
							//If time left, you can set image size according to the document;
							JFrame frame_image = new JFrame("Image Preview");
							JPanel panel_image = new JPanel();
							JButton button1_image = new JButton("Previous");
							JButton button2_image = new JButton("Next");
							
							class TEMPCLASS
							{
								public int value;
							};
							
							TEMPCLASS loop = new TEMPCLASS();
							loop.value = 0;
							
							
							panel_image.setLayout(null);
							button1_image.setBounds(20, 20, 70, 30);
							button2_image.setBounds(20, 70, 70, 30);
							
							frame_image.setSize(2000, 1100);
							panel_image.add(button1_image);
							panel_image.add(button2_image);
							
							JLabel label_image = new JLabel(new ImageIcon(location_list.get(loop.value)));
							label_image.setBounds(100, 20, 1920, 1080);
							panel_image.add(label_image, JPanel.CENTER_ALIGNMENT);
							
							frame_image.add(panel_image);
							frame_image.setVisible(true);
							
							
							
							button1_image.addActionListener(new ActionListener()
									{
										@Override
										public void actionPerformed(ActionEvent action_when)
										{
											if(loop.value == 0)
												return;
											else
											{
												loop.value --;
												
												label_image.setIcon(new ImageIcon(location_list.get(loop.value)));
												frame_image.repaint();
											}
										}
										
										
									}
									);
							
							button2_image.addActionListener(new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent action_when)
								{
									if(loop.value == location_list.size() - 1)
										return;
									else
									{
										loop.value ++;
										
										label_image.setIcon(new ImageIcon(location_list.get(loop.value)));
										frame_image.repaint();
									}
								}
								
								
							}
							);	
						}
						else
						{
							WarningWindow warning_win = new WarningWindow("You haven't selected any image");
						}
						
						
						
					}
				}
				);
		
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}
}

class TextPresent5  //task gen
{
	public JFrame frame;
	public JPanel panel;
	
	public JScrollPane scroll_pane;
	
	public JButton button1;
	public JButton button2;
	
	public JTextArea text_area;
	public static ArrayList<Attr_t> attr_list;
	
	Task_t task;
	
	public TextPresent5() throws UnknownHostException
	{
		this.task = new Task_t();
		
		this.frame = new JFrame("Task Generation");
		this.panel = new JPanel();
		this.scroll_pane = new JScrollPane();
		
		
		this.button1 = new JButton("Add SubTask");
		this.button2 = new JButton("OK");
		this.text_area = new JTextArea();
		
		this.panel.setLayout(null);
		//this.frame.setModalExclusionType(exclusionType);;
		this.frame.setSize(740,640);
		this.panel.setBounds(10, 10, 720, 620);
		this.scroll_pane.setBounds(20, 20, 700, 500);
		this.button1.setBounds(260, 550, 135, 30);
		this.button2.setBounds(420, 550, 75, 30);
		
		this.scroll_pane.setViewportView(this.text_area);
		this.text_area.setEditable(false);
		this.scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.attr_list = new ArrayList<Attr_t>();
		
		this.button2.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(task.content.subtask_list.size() == 0)
						{
							WarningWindow ww = new WarningWindow("You haven't provided any subtask");
							return;
						}
						task.From = Test3.node.node_info.id;
						try {
							task.ip_addr = InetAddress.getLocalHost().getHostAddress();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						task.pk_sender = Test3.node.node_info.pk;
						task.From = new ID_t();
						task.From.value= Test3.node.node_info.id.value;
						task.trans_type = 999;
						task.task_id = new ID_t();
						try {
							task.task_id.value = task.content.serialize()
									+task.From.value
									+task.ip_addr
									+task.pk_sender.value.toString()
									+String.valueOf(task.req_type)
									+String.valueOf(task.trans_type);
							
							task.task_id.value = new String(BasicCrypto.MyHash(task.task_id.value));
							/*
							
							task.task_id.value = BasicTool.str2byte(task.task_id.value);*/
							
							Test3.node.task_list_curr.add(task);
							Test3.node.task_record.add(0);
							Test3.node.my_task.add(task);
							Test3.node.my_task_location.add(Test3.node.task_list_curr.size() - 1);

							ArrayList<Bid_t> bid_list = new ArrayList<Bid_t>();
							ArrayList<Data_t> data_list = new ArrayList<Data_t>();
							ArrayList<Integer> bid_record_list = new ArrayList<Integer>();
							ArrayList<ID_t> worker_selected_input = new ArrayList<ID_t>();
							ArrayList<Integer> worker_selected_record_input = new ArrayList<Integer>();
							ArrayList<FB_t> feedback_list = new ArrayList<FB_t>();
							ArrayList<Integer> feedback_list_record = new ArrayList<Integer>();
							ArrayList<Integer> worker_selected_sub_input = new ArrayList<Integer>();
							ArrayList<Integer> worker_selected_sub_record_input = new ArrayList<Integer>();
							

							Test3.node.bid_list.add(bid_list);
							Test3.node.data_hash_list.add(data_list);
							Test3.node.bid_list_record.add(bid_record_list);
							Test3.node.worker_selected.add(worker_selected_input);
							Test3.node.worker_selected_record.add(worker_selected_record_input);
							Test3.node.feedback_list_collected.add(feedback_list) ;
							Test3.node.feedback_list_collected_record.add(feedback_list_record);
							Test3.node.worker_selected_sub.add(worker_selected_sub_input);
							Test3.node.worker_selected_sub_record.add(worker_selected_sub_record_input);
							Test3.node.worker_selected_bid.add(new ArrayList<String>());
							
							
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							Req_t req = new Req_t(task);
						
							String msg = BasicTool.preSign(req);
							req.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
							System.out.println("test in text present 3\n" + msg);
							

							
							BasicTool.BroadcastMsg(req); 

							System.out.println(Test3.node.waid ++ + "\tNew task generation finished");
							//further processing
							frame.dispose();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("error");
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("error");
						}
						
					}
				});
		
		this.button1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						SubTask_t subtask = new SubTask_t();
						
						JFrame frame2 = new JFrame("Add Information");
						JPanel panel2 = new JPanel();
						JButton button_add = new JButton("OK");
						
						JLabel label_sub1 = new JLabel("Input the description of the task");
						JTextArea text_input1 = new JTextArea();
						text_input1.setEditable(true);
						
						JLabel label_sub2 = new JLabel("Worker Number");
						JTextField text_input2 = new JTextField();
						
						JLabel label_sub3 = new JLabel("Attribute Required");
						String[] attribute_list = SystemParam.getAttrList();
						JComboBox attr_selec = new JComboBox(attribute_list);
						JButton button_add2 = new JButton("Add");
						
						JLabel label_sub4 = new JLabel("Payment");
						JTextField text_input3 = new JTextField();
						
						JTextArea text_output = new JTextArea();
						text_output.setEditable(false);
						
						button_add2.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent e)
									{
										int int_temp1 = -1;
										int_temp1 = attr_selec.getSelectedIndex();
										if(int_temp1 != -1)
										{
											Attr_t attr = new Attr_t();
											attr.value = int_temp1;
											
											
											
											for(int loop = 0; loop < /*subtask.attr_required_list*/attr_list.size(); loop ++)
											{
												if(attr.value ==/* subtask.attr_required_list*/attr_list.get(loop).value)
												{
													WarningWindow ww = new WarningWindow("You have selected this attribute already");
													return;
												}
											}
											attr_list.add(attr);
											subtask.requirements = subtask.requirements + String.valueOf('\t') + new String(SystemParam.getAttrList()[attr.value]);							

											text_output.append((String)attr_selec.getSelectedItem() + '\n');
										}
									}
								}
								);
						
						button_add.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent e)
									{
										try
										{
											
											subtask.subtask_des.value = text_input1.getText();
											subtask.worker_num = Integer.parseInt(text_input2.getText());
											subtask.pay_budget.value = Double.parseDouble(text_input3.getText());
											subtask.subtask_no = task.content.subtask_list.size();
											
											task.content.subtask_list.add(subtask);
											
											text_area.append("Subtask:\t" + task.content.subtask_list.size() + '\n');
											text_area.append("Attribute Requirements:\t\n");
											for(int loop = 0; loop < /*subtask.attr_required_list*/attr_list.size(); loop++)
											{
												text_area.append("\t" + SystemParam.getAttrList()[/*subtask.attr_required_list*/attr_list.get(loop).value] + '\n');
											}
											text_area.append("Worker Number Required:\t\n");
											text_area.append("\t" + String.valueOf(subtask.worker_num));
											text_area.append("\nDetailed Description\n");
											text_area.append("\t" + subtask.subtask_des.value);
											
											frame2.dispose();
										}catch (NumberFormatException e1)
										{
											WarningWindow ww = new WarningWindow("You haven't provided all the information required");
										}catch (NullPointerException e2)
										{
											WarningWindow ww = new WarningWindow("You haven't provided all the information required");
										}
									}
								}
								);
						
						frame2.setSize(740, 640);
						panel2.setLayout(null);
						
						panel2.setBounds(10, 10, 720, 620);
						label_sub1.setBounds(20, 20, 400, 30);
						text_input1.setBounds(20, 60, 400, 500 );
						
						label_sub2.setBounds(440,20, 130, 30);
						text_input2.setBounds(440, 60, 130, 30);
						label_sub4.setBounds(580, 20, 130, 30);
						text_input3.setBounds(580, 60, 130, 30);
						
						
						label_sub3.setBounds(440, 90, 280, 30);
						attr_selec.setBounds(440, 120, 150, 30);
						button_add2.setBounds(645, 120, 75, 30);
						text_output.setBounds(440, 160, 280, 400);
						
						button_add.setBounds(645, 570, 75, 30);
						
						panel2.add(label_sub1);
						panel2.add(label_sub2);
						panel2.add(label_sub3);
						panel2.add(label_sub4);
						
						panel2.add(text_output);
						panel2.add(text_input2);
						panel2.add(text_input1);
						panel2.add(text_input3);
						

						panel2.add(button_add2);
						panel2.add(button_add);
						panel2.add(attr_selec);
						
						frame2.add(panel2);
						frame2.setResizable(false);
						frame2.setVisible(true);
					}
				}
				);
		
		this.panel.add(this.scroll_pane);
		this.panel.add(this.button1);
		this.panel.add(this.button2);
		this.frame.add(this.panel);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		
		
	}
	
	
	
}



class TextPresent6
{
	JFrame frame;
	JPanel panel;
	
	//Procedure
	JLabel label1;
	JTextField text_field1;
	JButton button1;
	

	
	public TextPresent6()
	{
		this.frame = new JFrame();
		this.panel = new JPanel();
		
		
		//Procedure1
		this.label1 = new JLabel("Please input the task you selected", JLabel.CENTER);
		this.text_field1 = new JTextField();
		this.button1 = new JButton("OK");
		
		this.frame.setTitle("Task Selection");
		this.frame.setSize(400, 200);
		this.frame.setResizable(false);
		this.panel.setLayout(null);
		
		this.panel.setBounds(10, 10, 380, 180);
		this.label1.setBounds(10, 20, 380, 30);
		this.text_field1.setBounds(160, 70, 80, 30);
		this.button1.setBounds(160, 110, 70, 30);
		
		this.panel.add(this.label1);
		this.panel.add(this.text_field1);
		this.panel.add(this.button1);
		this.frame.add(this.panel);
		//this.frame.setDefaultCloseOperation(this.frame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		

		
		
		
		this.button1.addActionListener(new ActionListener()
				{
					@SuppressWarnings("deprecation")
					@Override
					public void actionPerformed(ActionEvent args)
					{
						String input = text_field1.getText();						
						int input_int = Integer.parseInt(input);
						
						if(input_int >= Test3.node.task_list_curr.size())
						{
							WarningWindow ww = new WarningWindow("Invalid task id");
							return;
						}
						
						Bid_t bid = new Bid_t();						
						ArrayList<Attr_t> attr_list = new ArrayList<Attr_t>();

						frame.dispose();
						
						

						JFrame frame2 = new JFrame("Subtask Selection");
						JPanel panel2 = new JPanel();
						JLabel label_sub1 = new JLabel("Expected Subtask");
						JLabel label_sub2 = new JLabel("Expected Payment");
						JLabel label_sub3 = new JLabel("Personal Attributes");
						
						JTextField text_field_sub1 = new JTextField();
						JTextField text_field_sub2 = new JTextField();
						JTextArea text_area_sub1 = new JTextArea();
						
						frame2.setSize(420, 720);						
						panel2.setBounds(10, 10, 400, 700);
						panel2.setLayout(null);
						JScrollPane scroll_pane_sub1 = new JScrollPane();
						JComboBox combo_sub1 = new JComboBox(SystemParam.getAttrList());
						
						JButton button_sub1 = new JButton("Add");
						JButton button_sub2 = new JButton("OK");
						
						
						label_sub1.setBounds(10, 10, 150, 30);
						label_sub2.setBounds(10, 50, 150, 30);
						label_sub3.setBounds(10, 90, 400, 30);
						
						text_field_sub1.setBounds(240, 10, 160, 30);
						text_field_sub2.setBounds(240, 50, 160, 30);
						
						combo_sub1.setBounds(10, 130, 250, 30);
						
						button_sub1.setBounds(305, 130, 75, 30);
						button_sub2.setBounds(305, 650, 75, 30);
						
						scroll_pane_sub1.setBounds(10, 170, 390, 470);
						
						scroll_pane_sub1.setViewportView(text_area_sub1);
						scroll_pane_sub1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scroll_pane_sub1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						
						button_sub1.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent args)
									{
										int index = combo_sub1.getSelectedIndex();
										Attr_t attr_temp = new Attr_t(index);
										
										for(int loop = 0; loop < attr_list.size(); loop ++) 
										{
											if(attr_list.get(loop).value == index)
											{
												WarningWindow ww = new WarningWindow("You have already selected the attribute");
												return;
											}
										}
										attr_list.add(attr_temp);
										
										text_area_sub1.append(SystemParam.getAttrList()[index] + "\n");
									}
								});
						
						button_sub2.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent args)
									{

										
										try {
											int input_int2 = Integer.parseInt(text_field_sub1.getText());
											if(input_int2 >= Test3.node.task_list_curr.get(input_int).content.subtask_list.size())
											{
												WarningWindow ww = new WarningWindow("Invalid Subtask");
												return;
											}
											bid.task_id = Test3.node.task_list_curr.get(input_int).task_id;
											bid.From = Test3.node.node_info.id;
											bid.pk_sender = Test3.node.node_info.pk;
											bid.trans_type = 99999;
											bid.content.pay_expected.value = Double.parseDouble(text_field_sub2.getText());
											bid.content.sub_id.id = input_int2;
											/*bid.content.attr_list = attr_list;*/
											bid.content.attr = new String();
											for(int loop = 0; loop < attr_list.size(); loop ++)
											{
												bid.content.attr += new String(SystemParam.getAttrList()[attr_list.get(loop).value]);
											}
											
											UIPF uipf = new UIPF();
											uipf.BidGeneration(Test3.node, bid);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (NoSuchAlgorithmException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										frame2.dispose();
									}
								});
						
						panel2.add(label_sub1);
						panel2.add(label_sub2);
						panel2.add(label_sub3);
						panel2.add(button_sub1);
						panel2.add(button_sub2);
						//panel.add(text_area_sub1);
						panel2.add(text_field_sub1);
						panel2.add(text_field_sub2);
						panel2.add(scroll_pane_sub1);
						panel2.add(combo_sub1);
						
						frame2.add(panel2);
						frame2.setResizable(false);
						frame2.setVisible(true);
						
						
						
						
						
					}
				}
				);
		
		
		
		
	}
}


class TextPresent7
{
	JFrame frame;
	JPanel panel;
	JButton button;
	JButton button2;
	JLabel label;
	JTextField text_field;
	JTextArea text_area;
	
	public TextPresent7()
	{
		this.frame = new JFrame("Add IP Address");
		this.panel = new JPanel();
		this.button = new JButton("Add");
		this.button2 = new JButton("OK");
		this.label = new JLabel("Please input the ip address");
		this.text_area = new JTextArea();
		this.text_field = new JTextField();
		
		this.frame.setSize(420, 720);
		this.frame.setResizable(false);
		this.panel.setLayout(null);
		this.text_area.setEditable(false);
		this.text_area.setLineWrap(true);
		
		this.panel.setBounds(10, 10, 400, 700);
		this.label.setBounds(10, 10, 400, 30);
		this.text_field.setBounds(10, 50, 290, 30);
		this.button.setBounds(310, 50, 90, 30);
		this.text_area.setBounds(10, 90, 400, 550);
		this.button2.setBounds(150, 650, 110, 30);
		
		this.panel.add(this.button);
		this.panel.add(this.button2);
		this.panel.add(this.label);
		this.panel.add(this.text_area);
		this.panel.add(this.text_field);
		
		this.frame.add(this.panel);
		this.frame.setVisible(true);
		
		ArrayList<String> ip_list = new ArrayList<String>();
		
		this.button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args)
					{
						ip_list.add(text_field.getText());
						text_area.append(text_field.getText() + "\n");
					}
				});
		
		this.button2.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent args2)
					{
						if(ip_list.size() <= 0)
						{
							WarningWindow ww = new WarningWindow("You haven't input any valid ip address");
						}
						else
						{
							for(int loop = 0; loop < ip_list.size(); loop ++)
							{
								Test3.node.incoming_potential.add(ip_list.get(loop));
								Test3.node.outgoing_potential.add(ip_list.get(loop));
								Test3.node.outgoing_list.ip_list.add(ip_list.get(loop));
								Test3.node.incoming_list.ip_list.add(ip_list.get(loop));
							}
							
							WarningWindow ww2 = new WarningWindow("Information", Integer.toString(ip_list.size()) + "\t addresses added successfully");
							frame.dispose();
						}
							
					}
				});
		
		
	}
}

class TextPresent8 //Button of Bid selection
{
	public JFrame frame;
	public JPanel panel;
	public JTextField textfield;
	public JLabel label;
	public JButton button;
	
	public TextPresent8()
	{
		this.frame = new JFrame();
		this.panel = new JPanel();
		this.textfield = new JTextField();
		this.label = new JLabel();
		this.button = new JButton("OK");
		
		this.frame.setBounds(20,  20, 400, 200);
		//this.panel.setBounds(10,  10, 380, 180);
		this.label.setBounds(100, 50, 90, 30);
		this.textfield.setBounds(200, 50, 50, 30);
		this.button.setBounds(170, 100, 60, 30);
		
		this.frame.setTitle("Select a Task");
		this.label.setText("Input the task id");
		
		this.panel.add(this.label);
		this.panel.add(this.textfield);
		this.panel.add(this.button);
		
		this.frame.add(this.panel);
		
		this.panel.setLayout(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		
		this.button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					String str_temp = textfield.getText();
					frame.dispose();
					Integer no;
					try
					{
						no = Integer.parseInt(str_temp);
					} catch (NumberFormatException e)
					{
						WarningWindow ww = new WarningWindow("The task id is not valid");
						frame.dispose();
						return;
					}
					
					if(!Test3.node.task_list_curr.get(no).From.value.equals(Test3.node.node_info.id.value))
					{
						WarningWindow ww = new WarningWindow("This is not your task");
						return;
					}
					
					
					JFrame frame2 = new JFrame("Worker Selection");
					JPanel panel2 = new JPanel();
					JScrollPane scroll_pane = new JScrollPane();
					JButton button2 = new JButton("OK");
					
					
					Vector<Vector<String>> data_content = new Vector<Vector<String>>();

					int row_num = 0;
					for(int loop = 0; loop < Test3.node.bid_list.get(no).size(); loop ++)
					{
						TV_t tv = new TV_t();
						tv.value = 0.0;
						for(int loop3 = 0; loop3 < Test3.node.node_list.size(); loop3 ++)
						{
							if(Test3.node.bid_list.get(no).get(loop).From.value.equals(Test3.node.node_list.get(loop3).id.value))
							{
								tv = Test3.node.node_list.get(loop3).tv;
							}
						}
						
				/*		for(int loop2 = 0; loop2 < Test3.node.bid_list.get(no).get(loop).content.attr_list.size(); loop2++)
						{*/
							Vector<String> vec_temp = new Vector<String>();
/*							if(loop2 == 0)
							{*/
								vec_temp.add(String.valueOf(loop));
								vec_temp.add(String.valueOf(Test3.node.bid_list.get(no).get(loop).content.sub_id.id));
								vec_temp.add(String.valueOf(tv));
								vec_temp.add(String.valueOf(Test3.node.bid_list.get(no).get(loop).content.pay_expected.value));
								vec_temp.add(Test3.node.bid_list.get(no).get(loop).content.attr/*SystemParam.getAttrList()[Test3.node.bid_list.get(no).get(loop).content.attr_list.get(loop2).value]*/);
								data_content.add(vec_temp);
							/*}
							else
							{
								vec_temp.add(null);
								vec_temp.add(null);
								vec_temp.add(null);
								vec_temp.add(SystemParam.getAttrList()[Test3.node.bid_list.get(no).get(loop).content.attr_list.get(loop2).value]);
								data_content.add(vec_temp);
							}*/
						/*}*/
					}
					Vector<String> header = new Vector<String>();
					header.add("No.");
					header.add("Subtask");
					header.add("Trust Value");
					header.add("Pay Required");
					header.add("Attribute");
					header.add("Y/N");
					
					DefaultTableModel model = new DefaultTableModel(data_content, header)
					{
						/**
						 * 
						 */
						private static final long serialVersionUID = -7640448828960692606L;

						public boolean isCellEditable(int row, int column) 
						{ 
							if((column ==  5)&&(row < getRowCount()))
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					};
					JTable table = new JTable(model);
					
					scroll_pane.setViewportView(table);
					scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					panel2.setLayout(null);
					
					panel2.add(scroll_pane);
					panel2.add(button2);
					frame2.add(panel2);
					
					
					frame2.setBounds(10, 10, 720, 670);						
					scroll_pane.setBounds(20, 20, 680, 560);
					button2.setBounds(330, 605, 60, 30);
					
					frame2.setResizable(false);
					frame2.setVisible(true);						

					ArrayList<Integer> input = new ArrayList<Integer>();
					
					button2.addActionListener(new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent args2)
								{
									input.clear();
									String str_y = new String("Y");
									String str_n = new String("N");
									for(int loop = 0; loop < Test3.node.bid_list.get(no).size(); loop ++)
									{
										String str_input = new String();
										str_input = (String) table.getValueAt(loop, 5);
										if(str_input == null)
										{
											WarningWindow ww = new WarningWindow("No slection, try again");
											return;
										}
										else if(str_input.equals(str_y)||str_input.equals(str_n))
										{
											continue;
										}
										else
										{
											WarningWindow ww = new WarningWindow("Invalid slection, try again");
											return;
										}
									}
									
									String str_content = new String();
									
									for(int loop = 0; loop < Test3.node.bid_list.get(no).size(); loop ++)
									{
	
										String str_input = new String();
										str_input = (String)table.getValueAt(loop, 5);
										{
											if(str_input.equals(str_y))
											{
												Test3.node.worker_selected.get(no).add(Test3.node.bid_list.get(no).get(loop).From);
												Test3.node.worker_selected_record.get(no).add(0);
												Test3.node.worker_selected_sub.get(no).add(Test3.node.bid_list.get(no).get(loop).content.sub_id.id);
												String str_hash = null;
												try {
													str_hash = new String(BasicCrypto.MyHash(Test3.node.bid_list.get(no).get(loop).serialize()));
												} catch (NoSuchAlgorithmException | IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												Test3.node.worker_selected_bid.get(no).add(str_hash);
											}
										}
									}
									String content = null;
									WorkerSelectRes worker_list = new WorkerSelectRes();
									worker_list.worker_list_sub = Test3.node.worker_selected_sub.get(no);
									worker_list.worker_list = Test3.node.worker_selected.get(no);
									worker_list.bid_hash_list = Test3.node.worker_selected_bid.get(no);
									try {
										content = worker_list.serialize();
										System.out.println(content);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Req_t req = null;
									try {
										req = new Req_t();
									} catch (UnknownHostException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									req.content = content;
									req.From = Test3.node.node_info.id;
									req.ip_addr = Test3.node.ip;
									req.req_type = 5;
									req.task_id.value = Test3.node.task_list_curr.get(no).task_id.value;
									req.trans_type = 999;
									req.pk_sender = Test3.node.task_list_curr.get(no).pk_sender;
									
									String msg = BasicTool.preSign(req);
									
									try {
										req.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									try {
										BasicTool.BroadcastMsg(req);
										frame2.dispose();
										WarningWindow ww = new WarningWindow("Info", "Worker Selection Finished");
										return;
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							);
					
					//you need to add further prcessing on these data
					
					;
			}
		}
		);
		
	}
}

class TextPresent9 //check data
{
	public JFrame frame;
	public JPanel panel;
	public JTextField text_field;
	public JTextField text_field2;
	public JTextArea text_area;
	public JButton button3;
	public JScrollPane scroll_pane;
	public JLabel label;
	public JLabel label2;
	
	ArrayList<String> location_list;
	
	public TextPresent9()
	{
		this.location_list = new ArrayList<String>();
		
		this.frame = new JFrame("Select Data");
		this.panel = new JPanel();
		this.text_field = new JTextField();
		this.text_field2 = new JTextField();
		this.button3 = new JButton("Preview");
		this.label = new JLabel("Input the task");
		this.label2 = new JLabel("Input the subtask");
		
		this.frame.setSize(620, 220);		
		this.panel.setLayout(null);
		
		this.panel.setBounds(10, 10, 600, 200);
		this.label.setBounds(20, 20, 285, 30);
		this.text_field.setBounds(190, 20, 285, 30);
		this.label2.setBounds(20, 55, 285, 30);
		this.text_field2.setBounds(190, 55, 285, 30);
		this.button3.setBounds(500, 150, 100, 30);
		
		this.panel.add(this.text_field);
		this.panel.add(this.text_field2);
		this.panel.add(this.button3);
		this.panel.add(this.label);
		this.panel.add(this.label2);
		
		this.frame.add(this.panel);
		
		this.button3.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent action3)
					{
						int input1 = Integer.valueOf(text_field.getText());
						int input2 = Integer.valueOf(text_field2.getText());
						
						String str1 = String.valueOf(input1);
						String str2 = String.valueOf(input2);
						
						
						for(int loop = 0; ;loop++)
						{
							String str = new String("files\\TaskData\\" +str1 + "\\" + str2 + "\\"+ String.valueOf(loop)+ ".jpg");
							File file = new File(str);
							if(file.exists() == true)
							{
								location_list.add(str);
								continue;
							}
							break;
						}
						

						
						if(location_list.size() != 0)
						{	
							JFrame frame_image = new JFrame("Image Preview");
							JPanel panel_image = new JPanel();
							JButton button1_image = new JButton("Previous");
							JButton button2_image = new JButton("Next");
							
							class TEMPCLASS
							{
								public int value;
							};
							
							TEMPCLASS loop = new TEMPCLASS();
							loop.value = 0;
							
							
							panel_image.setLayout(null);
							button1_image.setBounds(20, 20, 70, 30);
							button2_image.setBounds(20, 70, 70, 30);
							
							frame_image.setSize(2000, 1100);
							panel_image.add(button1_image);
							panel_image.add(button2_image);
							
							JLabel label_image = new JLabel(new ImageIcon(location_list.get(loop.value)));
							label_image.setBounds(100, 20, 1920, 1080);
							panel_image.add(label_image, JPanel.CENTER_ALIGNMENT);
							
							frame_image.add(panel_image);
							frame_image.setVisible(true);
							
							
							
							button1_image.addActionListener(new ActionListener()
									{
										@Override
										public void actionPerformed(ActionEvent action_when)
										{
											if(loop.value == 0)
												return;
											else
											{
												loop.value --;
												
												label_image.setIcon(new ImageIcon(location_list.get(loop.value)));
												frame_image.repaint();
											}
										}
										
										
									}
									);
							
							button2_image.addActionListener(new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent action_when)
								{
									if(loop.value == location_list.size() - 1)
										return;
									else
									{
										loop.value ++;
										
										label_image.setIcon(new ImageIcon(location_list.get(loop.value)));
										frame_image.repaint();
									}
								}
								
								
							}
							);	
						}
						else
						{
							WarningWindow warning_win = new WarningWindow("No data");
						}
						
						
						
					}
				}
				);
		
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}
}
















































