package com.example.administrator.mcs_bc;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class CommProcess implements Runnable
{

	@Override
	public  synchronized void run() 
	{

			//case 10: requesting another miner as a incoming node;	
			//case 11: requesting another miner as a outgoing node;
		while(true)
		{
			if((Test3.node.state_broadcast_ic == 0 	&& Test3.node.incoming_list.id_list.size() < SystemParam.getICThres())
					||(Test3.node.state_broadcast_og == 0 && Test3.node.outgoing_list.id_list.size() < SystemParam.getOGThres()))
			{
				
				System.out.println(Test3.node.waid ++ + "\tTrying to recruting communication nodes");		
				Test3.node.process_case10_gen();
				Test3.node.process_case11_gen();

				try {
					Thread.sleep(300000);;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		
	}
	
}

class ListeningProcess implements Runnable
{

	@Override
	public void run() 
	{
		if(Test3.node.state_listening == 0)
		{
			System.out.println(Test3.node.waid ++ + "\tTrying for listening");
			Test3.node.state_listening = 1;	
			
				while(true)
				{
		        	if(Test3.node.my_socket == null)
		        	{
		        		while(Test3.node.my_socket == null)
		        		{
		        			try {
								Test3.node.my_socket = new ServerSocket(9091);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("Exception");
								Test3.node.state_listening = 0;
							}
		        		}
		        	}
		        	
		        	Test3.node.state_listening = 2;
		        	
		        	Socket socket = null;
		        	try {
						 socket = Test3.node.my_socket.accept();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Exception");
						e.printStackTrace();
						
						Test3.node.state_listening = 0;
					}
		        	
		        	if(socket != null)
		        	{
						ObjectInputStream in = null;
						try {
							in = new ObjectInputStream(socket.getInputStream());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String str = null;
						
						
						if(in != null)
						{
							
							Req_t req = null;
							
							try {
								req = new Req_t();
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								req = (Req_t) in.readObject();
							} catch (ClassNotFoundException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								if(!BasicTool.isPrevProcessed(req))
								{
									if(!req.From.value.equals(Test3.node.node_info.id.value))
									{
										while(true)
										{
											if(Test3.node.read_allowed == 1)
											{
												Test3.node.read_allowed = 0;
									            Test3.node.request_list.add(req);
									            Test3.node.index_list.add(0);
									            
									            System.out.println("Received a request, request case is " + req.req_type + ", Total Request number is " + Test3.node.request_list.size());
									            Test3.node.read_allowed = 1;
									            break;
											}
											
											if(req.req_type == 6 && req.trans_type == 3)
											{
												socket.close();
												continue;
											}
											BasicTool.TransferMsg(req, req.ip_addr);
										}
									}
									else
									{
										System.out.println("Repeated Message");
									}
									
									
								}
							} catch (NoSuchAlgorithmException | IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Test3.node.state_listening = 0;
						}
		        	}
			      }
		}
	}
	
}

class MiningProcess implements Runnable
{

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		System.out.println("Mining Started");
		while(true)
		{
			try {
				Test3.node.block_select();
			} catch (ClassNotFoundException | NoSuchAlgorithmException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}