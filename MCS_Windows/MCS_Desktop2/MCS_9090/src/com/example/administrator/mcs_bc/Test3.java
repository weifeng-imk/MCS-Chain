//The local port is 9090
package com.example.administrator.mcs_bc;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.swing.JLabel;

import it.unisa.dia.gas.jpbc.Element;

public class Test3
{
	public static String msg_comm;
	public static Node_t node;
	

	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException
	{	
		//System.out.print("This is for test");
		BasicCrypto bs = new BasicCrypto();
		Test3.node = new Node_t();

		MyUI ui = new MyUI();
		
		System.out.println("Game Started");
		
		//Thread.sleep(3000);
		
		ListeningProcess listening_process = new ListeningProcess();
		Thread thread_listening = new Thread(listening_process);
		thread_listening.start();	
		MiningProcess mining_process = new MiningProcess();
		Thread thread_mining = new Thread(mining_process);
		thread_mining.start();

		//System.out.println(thread_listening.getState());

		Thread.sleep(10000);
		
		
		
		//Test3.node.incoming_potential.add("127.0.0.1");
		//Test3.node.outgoing_potential.add("127.0.0.1");

		
		



		int sha = 0;
		while(true)
		{
			if (MyUI.state == 1)
			{
				ui.setMainPanel();			
				break;
			}
			
		}
		
		CommProcess pro = new CommProcess();
		Thread thread_compro = new Thread(pro);
		thread_compro.start();
		
		Thread thread3 = new Thread(node);
    	thread3.start();
		
		Integer[] intn_hash = new Integer[node.node_info.id.value.length()];
		String str_temp = new String();
		for(int loop = 0; loop < intn_hash.length; loop ++)
		{
			intn_hash[loop] = (int) node.node_info.id.value.charAt(loop);
			str_temp = str_temp + String.format("%x", intn_hash[loop]);
		}
		     
		Test3.msg_comm =  "User ID:  " + "\n" + str_temp + "\n" + "Trust Value:  \n" + node.node_info.tv.value
				+ "\n" + "Public Key:  \n" + node.node_info.pk.value + "\n" + "Private Key: \n " + node.node_info.sk.value;

		Calendar calendar = Calendar.getInstance();
		long time1 = calendar.getTimeInMillis();
		long time2 = calendar.getTimeInMillis();
		while(true)
		{
			ui.function1.dateandtime.setText(DataandTime.getDataandTime());	
			Calendar calendar2 = Calendar.getInstance();
			time2 = calendar2.getTimeInMillis() - time1;
			if(time2 > 1000)
			{
				ui.main_panel.remove(ui.function1.task_table.task_list_scroll);
				ui.function1.task_table.reSetTable(ui.main_panel);
				ui.main_panel.add(ui.function1.task_table.task_list_scroll);
				ui.main_frame.repaint();
				
				Calendar calendar3 = Calendar.getInstance();
				time1 = calendar3.getTimeInMillis();
			}
				

		}
		
		
		
		

	}
}







	
	
	



