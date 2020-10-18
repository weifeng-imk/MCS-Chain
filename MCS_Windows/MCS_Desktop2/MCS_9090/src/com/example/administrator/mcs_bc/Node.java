package com.example.administrator.mcs_bc;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
//import weka.associations.*;
//import weka.classifiers.*;

class NodeProfile_t
{
	public ID_t id;
	public PK_t pk;
	public SK_t sk;
	public TV_t tv;
	

	
	//public int block_no_start;
	//public int block_no_end;
	
	public NodeProfile_t() throws NoSuchAlgorithmException
	{
		this.id = new ID_t();
		this.pk = new PK_t();
		this.sk = new SK_t();
		this.tv = new TV_t();
		
		this.sk.value = BasicCrypto.Zn.newRandomElement();
		this.pk.value.set(BasicCrypto.P);
		this.pk.value.powZn(this.sk.value);
		
		this.id.value = new String(BasicCrypto.MyHash(this.pk.value.toString()));
		this.id.value = BasicTool.str2byte(this.id.value);
		
		this.tv.value = 0;
		
	}
}

class SimplifiedNodeProfile_t
{
	public ID_t id;
	public TV_t tv;
	public int block_no_start;
	public int block_no_end;
	
}

class Node_t implements Runnable
{	
	public long time0 = 0;
	public int waid = 0;
	//1 Personal Information ================================================================
	public NodeProfile_t node_info;
	public Integer num_block_obtain = 0;
	
	//Profile List of all current users;
	public ArrayList<SimplifiedNodeProfile_t> node_list;
	public ArrayList<Integer> node_blocknum_list;
	
	//2================================================================
	//Communication List 
	public CommunicationList_t incoming_list;
	public CommunicationList_t outgoing_list;
	
	//3====================================================================
	//potential communication list
	public ArrayList<String> incoming_potential;
	public ArrayList<String> outgoing_potential;
	
	//4==========================================================================
	//Personal network information, including ip address, socket, and port no. The default port is 9090;
	String ip;
	ServerSocket my_socket;
	int myport;
	
	//5==================================================================
	//basic and procedure1
	public ArrayList<Task_t> task_list_curr;// all the unfinished tasks will be stored in the list
	public ArrayList<Integer> task_record;
	public ArrayList<Integer> state_task_list;// all the states of tasks is stored in the list, the list is corresponding to the former list
	
	//procedure 2
	public ArrayList<ArrayList<Bid_t>> bid_list; // collected bid list for each tasks
	public ArrayList<ArrayList<Integer>> bid_list_record;
	
	public ArrayList<ArrayList<ID_t>> worker_selected;// selected workers 
	public ArrayList<ArrayList<Integer>> worker_selected_record;
	public ArrayList<ArrayList<Integer>> worker_selected_sub;
	public ArrayList<ArrayList<String>> worker_selected_bid;
	public ArrayList<ArrayList<Integer>>worker_selected_sub_record;
	
	public ArrayList<ArrayList<Pay_t>> pay_list_decided;//pay for each selected tasks
	public ArrayList<ArrayList<Integer>> pay_list_decided_record;
	
	//procedure 3
	public ArrayList<ArrayList<Data_t>> data_hash_list; //Data Collected for each task
	public ArrayList<ArrayList<Integer>> data_hash_list_record;
	
	//procedure 4
	public ArrayList<ArrayList<Pay_t>> pay_list_finalized; // Final Pay for tasks
	public ArrayList<ArrayList<Integer>> pay_list_finalized_record;
	
	
	public ArrayList<ArrayList<FB_t>> feedback_list_collected;//Feedback received
	public ArrayList<ArrayList<Integer>> feedback_list_collected_record;
	public ArrayList<ArrayList<TV_t>> trust_evaluated;//trust evaluated in the procedure
	public ArrayList<ArrayList<Integer>> trust_evaluated_record;
	
	public ArrayList<ArrayList<ID_t>> evaluator_list;
	public ArrayList<ArrayList<ID_t>> evaluatee_list;
	public ArrayList<ArrayList<Integer>> evaluator_list_record;
	public ArrayList<ArrayList<Integer>> evaluatee_list_record;
	//extra
	Pay_t pay_decided_total = new Pay_t();
	
	public ArrayList<Block_t> current_block_list;
	public ArrayList<Block_t> undecided_block_list;
	
	//5============================================================================
	
	//public ArrayList<Boolean> state_task_record;
	//public ArrayList<ArrayList<Integer>> bid_record;
	
	//ArrayList<ArrayList<ID_t>> 
	
	//6======================================================================
	public ArrayList<Req_t> request_list; //request here
	public ArrayList<Socket> socket_list;
	public ArrayList<Integer> index_list; //
	
	public int pointer;
	
	public ArrayList<ArrayList<Req_t>> req_block_list;
	public ArrayList<ArrayList<Block_t>> block_list;
	public ArrayList<Integer> req_block_no;
	
	public int read_allowed; 	// this value is set:
						// = 0; not allowed
						// = 1: allowed
						// = -1: no request now
	
	public int read_allowed_block;
	
	public int state_mining;  	//0 mining not started
						//1 currently mining;
						//2 block processing, mining paused
						//
	public int state_broadcast_ic = 0;
	public int state_broadcast_og= 0;
	public int state_process = 0;
	public int state_listening = 0;//0 not listening 2 listening
	
	public int state_ic_finding = 0;
	public int state_og_finding = 0;//0 not finding, 1 = finding
	//7=======================================================================
	
	public int ic_write_allowed = 1;
	public int og_write_allowed = 1;
	public int req_write_allowed = 1;
	public int task_read_allowed = 1;
	public int broadcast_allowed = 1;
	public int data_read_allowed = 1;
	public int data_write_allowed = 1;
	public int fb_read_allowed = 1;
	public int profile_read_allowed = 1;
	public int isAll = 0;
	public int mining_allowed = 1;
	
	
	
	 //7. As a User
    ArrayList<Task_t> task_issued;
    ArrayList<Integer> block_status;
    public ArrayList<Integer> task_accepted;
    
    //8. As a Worker
    ArrayList<SubID_t> subtask_accepted;
    public ArrayList<Integer> task_location;
    ArrayList<Data_t> data_generated;
    ArrayList<ID_t> miner_for_worker;
    //9. Role Record
    boolean worker_role = false;
    boolean user_role = false;
    boolean miner_role = false;

    //10. Extra
    public ArrayList<Task_t> my_task;
    /*public ArrayList<SubTask_t> accpted_task;*/
    /*public ArrayList<ID_t> accepted_task_id;*/
    public ArrayList<Integer> my_task_location;
    
    public ArrayList<ArrayList<Boolean>> worker_payoff;
    public ArrayList<Integer> task_state;
	
	public boolean setAll(int in)
	{
		

		this.isAll = in;
		while(true)
		{

			
			if(this.task_read_allowed == in 
					&& this.data_read_allowed == in 
					&& this.fb_read_allowed == in
					&& this.profile_read_allowed == in 
					&& this.data_write_allowed == in)
			{
				break;
			}
			else
			{
				if(this.task_read_allowed != 0)
					this.task_read_allowed = in;
				if(this.data_read_allowed != 0)
					this.data_read_allowed = in;
				if(this.fb_read_allowed != 0)
					this.fb_read_allowed = in;
				if(this.profile_read_allowed != 0)
					this.profile_read_allowed = in;
				if(this.data_write_allowed != 0)
					this.data_write_allowed = in;
			}
		}
		
		return true;
	}
	
	
	
	public Node_t() throws  IOException, NoSuchAlgorithmException 
	{
		//1  initialize all the profile information of users
		//=====================================================================
		
		this.node_info = new NodeProfile_t();
		this.num_block_obtain = 0;

		
		this.node_list = new ArrayList<SimplifiedNodeProfile_t>();
		this.node_blocknum_list = new ArrayList<Integer> ();
		
		//2======================================================================
		this.incoming_list = new CommunicationList_t();
		this.outgoing_list = new CommunicationList_t();
		
		//3======================================================================
		this.incoming_potential = new ArrayList<String>();
		this.outgoing_potential = new ArrayList<String>();
		
		
		//4=============================================================================
		this.ip = InetAddress.getLocalHost().getHostAddress();
		/*this.ip = new String("127.0.0.1");*/
		//System.out.println(ip);
		this.my_socket = new ServerSocket(9090/*SystemParam.GotGlobalPort2()*/);//SystemParam.GotGlobalPort());
		this.myport = SystemParam.GotGlobalPort2();
		
		//5=========================================================================
		
		//basic and procedure1
		this.task_list_curr = new ArrayList<Task_t>();// all the unfinished tasks will be stored in the list
		this.task_record = new ArrayList<Integer>();
		this.state_task_list = new ArrayList<Integer>();// all the states of tasks is stored in the list, the list is corresponding to the former list
		
		//procedure 2
		bid_list = new ArrayList<ArrayList<Bid_t>>(); // collected bid list for each tasks
		this.bid_list_record = new ArrayList<ArrayList<Integer>>();
		

		this.worker_selected = new ArrayList<ArrayList<ID_t>>();
		this.worker_selected_record = new ArrayList<ArrayList<Integer>>();
		this.worker_selected_sub = new ArrayList<ArrayList<Integer>>();
		this.worker_selected_bid = new ArrayList<ArrayList<String>>();
		this.worker_selected_sub_record = new ArrayList<ArrayList<Integer>>();

		this.pay_list_decided = new ArrayList<ArrayList<Pay_t>>();
		this.pay_list_decided_record = new ArrayList<ArrayList<Integer>>();
		
		//procedure 3 
		this.data_hash_list = new ArrayList<ArrayList<Data_t>>();//Data Collected for each task
		this.data_hash_list_record = new ArrayList<ArrayList<Integer>>();
		
		//procedure 4
		this.pay_list_finalized = new ArrayList<ArrayList<Pay_t>>();
		this.pay_list_finalized_record = new ArrayList<ArrayList<Integer>>();
		

		this.feedback_list_collected = new ArrayList<ArrayList<FB_t>>();
		this.feedback_list_collected_record = new ArrayList<ArrayList<Integer>>();
		this.trust_evaluated = new ArrayList<ArrayList<TV_t>>();
		this.trust_evaluated_record = new ArrayList<ArrayList<Integer>>();

		this.evaluator_list = new ArrayList<ArrayList<ID_t>>();
		this.evaluatee_list = new ArrayList<ArrayList<ID_t>>();
		this.evaluator_list_record = new ArrayList<ArrayList<Integer>>();
		this.evaluatee_list_record = new ArrayList<ArrayList<Integer>>();
		//extra
		this.pay_decided_total = new Pay_t();
		
		//6=========================================================================

		this.request_list = new ArrayList<Req_t>();
		this.socket_list = new ArrayList<Socket>();
		this.index_list = new ArrayList<Integer>();
		

		this.pointer = 0;
	
		this.req_block_list = new ArrayList<ArrayList<Req_t>>();
		this.block_list = new ArrayList<ArrayList<Block_t>>();
		this.req_block_no = new ArrayList<Integer>();
		
		this.read_allowed = 1; 	// this value is set:
							// = 0; not allowed
							// = 1: allowed
							// = -1: no request now
		
		this.read_allowed_block = 1;
							//this value is set
							// = 0; not allowed
							// = 1: allowed
							// = -1: no request now
		this.state_mining = 0;  	//0 mining not started
							//1 currently mining;
							//2 block processing, mining paused
							//
		
		this.state_broadcast_ic = 0;
		this.state_broadcast_og= 0;
		this.state_process = 0;
		this.state_listening = 0;//port is not listening 1 = port is setting 2 = port is listening
		this.ic_write_allowed  = 1;
		this.og_write_allowed = 1;
		
		this.undecided_block_list = new ArrayList<Block_t>();
		this.current_block_list = new ArrayList<Block_t>();
		

        //7. As a User
        this.task_issued = new  ArrayList<Task_t> ();
        this.block_status = new ArrayList<Integer> ();
        //8. As a Worker
        this.subtask_accepted = new ArrayList<SubID_t>();
        this.data_generated = new ArrayList<Data_t> ();
        this.miner_for_worker = new ArrayList<ID_t>();
        //9. Role Record
        this.worker_role = false;
        this.user_role = false;
        this.miner_role = false;

        //10. Extra
        this.my_task = new ArrayList<Task_t>();
        /*this.accpted_task = new ArrayList<SubTask_t>()*/;
        this.task_location = new ArrayList<Integer>();
        this.my_task_location = new ArrayList<Integer>();
        this.task_accepted = new  ArrayList<Integer> ();
		
        this.worker_payoff = new ArrayList<ArrayList<Boolean>>();
        this.task_state = new ArrayList<Integer>();
	}


	@Override
	public void run() 
	{
		while(true)
		{
			System.out.print("");
			if((this.request_list.size() != 0)&&(this.pointer < this.request_list.size()))
			{
				System.out.println(this.waid ++ 
									+ "\tTrying to deal with request\t" 
									+ this.pointer + "\t in case\t" 
									+ this.request_list.get(pointer).req_type);
				Req_t req = this.request_list.get(this.pointer);			
				this.pointer++;		
				this.read_allowed = 1;
				
				switch (req.req_type)
				{
				case 1:
					//case 1: block mining 
					//done before
					break;
				case 2:
					//case 2: block check or comparison
					try {
						this.process_case2(req);
						break;
					} catch (NoSuchAlgorithmException | ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				case 3:
					//case 3: receiving a task request
					this.process_case3(req);
					break;
				case 4:
					//case 4: receiving a bid
					try {
						this.process_case4(req);
					} catch (NoSuchAlgorithmException | ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					//case 5: processing bid and find the worker (maybe not necessary for this task)
					try {
						this.process_case5(req);
					} catch (ClassNotFoundException | NoSuchAlgorithmException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 6:
					//case 6: receiving a data declaration and related processing
					try {
						this.process_case6(req);
					} catch (NoSuchAlgorithmException | ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 7:
					try {
						this.process_case7(req);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 8:
					//embed it in the case 7
					break;
				case 9:
					break;
				case 10:
					//finished previously
					//case 12: receiving other node's request to be an incoming node;			
					if(this.outgoing_list.id_list.size() < SystemParam.OGThres)
					{				
						this.process_case12(req);
					}
					break;
				case 11:
	//==========================================================================================================
					//finished previously
					//case 13: receiving other nodes' request to be an outgoing node;
	//==========================================================================================================
					if(this.incoming_list.id_list.size() < SystemParam.ICThres)
					{
						this.process_case13(req);
					}
					break;
	//==========================================================================================================
				case 12:
	//==========================================================================================================
					//case 12: receiving other node's request to be an incoming node;
	//==========================================================================================================
				case 13:
	//==========================================================================================================
					//case 13: receiving other nodes' request to be an outgoing node;
	//==========================================================================================================
	//==========================================================================================================
				case 14:
	//==========================================================================================================
					//case 14: receiving requested node's reply (IC)
	//==========================================================================================================
					//if(this.state_broadcast_ic != 1)
					//{
						this.process_case14(req);
					//}
					break;
	//==========================================================================================================
				case 15:
					//case 15: receiving requested node's reply;(OG)
					//if(this.state_broadcast_og != 1)
					//{
						this.process_case15(req);
					//}
					break;
				case 16:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 17:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 18:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 19:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 20:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 21:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 22:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 23:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 24:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				case 25:
					//********************************************
					//*  you need an extra check processing      *
					//********************************************
				}
					
			}
		}
		
	}
	
	public void process_case2(Req_t req) throws NoSuchAlgorithmException,  IOException, ClassNotFoundException
	{
		System.out.println(this.waid + "\tTrying to deal with\t" + this.pointer + "\trequest in case \t" + req.req_type);
		if(BasicCrypto.SignatureVerify1(BasicTool.preSignBlock(req), req.pk_sender, req.Sig_from))
		{
			Block_t block = null;
			
			block = new Block_t();
			block = block.deserialize(req.content);
			
			if(block.no != Test3.node.block_list.size())
			{
				return;
			}
			
			String msg_block = String.valueOf(block.no) 
								+ block.id.value
								+ String.valueOf(block.tv.value)
								+ String.valueOf(block.time.value)
								+ block.hash_prev
								+ String.valueOf(block.num_block_obtain)
								
								+ new String(block.pk.value.toBytes())
								
								+ block.merkle_bid.getString()
								+ block.merkle_data.getString()
								+ block.merkle_feedback.getString()
								+ block.merkle_state.getString()
								+ block.merkle_task.getString()
								+ block.merkle_worker.getString();
			
			if(true/*BasicCrypto.SignatureVerify1(msg_block, block.pk, block.sig)*/)
			{
				this.undecided_block_list.add(block);//request in case 2 processing
				System.out.println("Finished\t" + req.req_type);
			}
		}
	}
	
	public void process_case3(Req_t req)
	{
		try {
			String msg = BasicTool.preSign(req);
			if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from))
			{
				while(true)
				{
					if(this.task_read_allowed == 1)
					{
						this.task_read_allowed = 0;
						
						Task_t task_3 = new Task_t(req);
						
						this.task_list_curr.add(task_3);
						this.task_record.add(0);
						
						
						ArrayList<Bid_t> bidlist_temp = new ArrayList<Bid_t>();
						this.bid_list.add(bidlist_temp);
						this.bid_list_record.add(new ArrayList<Integer>());
						
						ArrayList<Pay_t> paylist_temp = new ArrayList<Pay_t>();
						this.pay_list_decided.add(paylist_temp);
						this.pay_list_decided_record.add(new ArrayList<Integer>());
						
						
						ArrayList<Pay_t> paylist_temp2 = new ArrayList<Pay_t>();
						this.pay_list_finalized.add(paylist_temp2);
						this.pay_list_finalized_record.add(new ArrayList<Integer>());
						
						ArrayList<ID_t> idlist_temp = new ArrayList<ID_t>();
						this.worker_selected.add(idlist_temp);
						this.worker_selected_record.add(new ArrayList<Integer>());
						this.worker_selected_bid.add(new ArrayList<String>());
						this.worker_selected_sub.add(new ArrayList<Integer>());
						this.worker_selected_sub_record.add(new ArrayList<Integer>());
						
						
						ArrayList<Data_t> datalist_temp = new ArrayList<Data_t>();
						this.data_hash_list.add(datalist_temp);
						this.data_hash_list_record.add(new ArrayList<Integer>());
						
						ArrayList<FB_t> fb_temp = new ArrayList<FB_t>();
						this.feedback_list_collected.add(fb_temp);
						this.feedback_list_collected_record.add(new ArrayList<Integer>());
						
						this.trust_evaluated.add(new ArrayList<TV_t>());
						this.trust_evaluated_record.add(new ArrayList<Integer>());
						this.evaluator_list.add(new ArrayList<ID_t>());
						this.evaluatee_list.add(new ArrayList<ID_t>());
						this.evaluator_list_record.add(new ArrayList<Integer>());
						this.evaluatee_list_record.add(new ArrayList<Integer>());
						
						this.task_state.add(0);
						
						this.task_read_allowed = 1;
						break;
						
					}
				}
				
				
				System.out.println("Finished\t" + req.req_type);
				
			}
		} catch (NoSuchAlgorithmException e1) {
			System.out.println("Error");
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	
	}
	
	public void process_case4(Req_t req) throws NoSuchAlgorithmException, ClassNotFoundException, IOException
	{
			String msg = BasicTool.preSign(req);
			if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from))
			{
				//record to location
				int index = -1;
				for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
				{
					if(this.task_list_curr.get(loop).task_id.value.equals(req.task_id.value))
					{
						index = loop;
						break;
					}
				}
				
				if(index == -1)
				{
					System.out.println(BasicTool.printID(this.task_list_curr.get(0).task_id));
					System.out.println(BasicTool.printID(req.task_id));
					System.out.println("Cannot find the task");
					return;
				}

				Bid_t bid_input = new Bid_t(req);
				
				this.bid_list.get(index).add(bid_input);
				this.bid_list_record.get(index).add(0);
				
				msg = req.serialize();
			}
			System.out.println("Finished\t" + req.req_type);
	}
	
	public void process_case5(Req_t req) throws ClassNotFoundException, IOException, NoSuchAlgorithmException
	{
		String msg = BasicTool.preSign(req);
		if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from) == false)
		{
			return;
		}
		WorkerSelectRes result = new WorkerSelectRes();
		result = result.deserialize(req.content);
		
		for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
		{
			if(req.task_id.value.equals(this.task_list_curr.get(loop).task_id.value))
			{
				for(int loop2 = 0; loop2 < result.worker_list.size(); loop2 ++)
				{
					this.worker_selected.get(loop).add(result.worker_list.get(loop2));
					this.worker_selected_record.get(loop).add(0);
					this.worker_selected_sub.get(loop).add(result.worker_list_sub.get(loop2));
					this.worker_selected_bid.get(loop).add(result.bid_hash_list.get(loop2));
					this.worker_selected_sub_record.get(loop).add(0);
					
					if(result.worker_list.get(loop2).value.equals(this.node_info.id.value))
					{
                        SubID_t subtemp = new SubID_t();
                        subtemp.id = result.worker_list_sub.get(loop2);
                        this.subtask_accepted.add(subtemp);
                        this.task_accepted.add(loop);
					}
					
				}
				break;
			}
		}
	}
	
	public void process_case6(Req_t req) throws NoSuchAlgorithmException, ClassNotFoundException, IOException
	{
		String msg = BasicTool.preSign(req);
		
		System.out.println("Just for Testing");

		if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from))
		{
			System.out.println("Just for Testing 222");
			if(req.trans_type == 1)
			{
				System.out.println("Processing for type 6 for " +  req.trans_type);
				try {
					Data_t data_6 = new Data_t(req);
					for(int loop = 0; loop < this.task_list_curr.size();loop ++)
					{
						if(this.task_list_curr.get(loop).equals(data_6.task_id))
						{
							while(true)
							{
								if(Test3.node.data_read_allowed == 1)
								{
									Test3.node.data_read_allowed = 0;
									
									//boolean flag = false;
									for(int loop1 = 0; loop1 < this.worker_selected.get(loop).size(); loop1 ++)
									{
										/*if(data_6.From.equals(this.worker_selected.get(loop).get(loop1)))*/
										if(data_6.content.bid_hash.equals(this.worker_selected_bid.get(loop).get(loop1)))
										{
											this.data_hash_list.get(loop).add(data_6);
										}
									}

//									data_6.content.data = new ArrayList<byte[]>();
//									data_6.trans_type = 1;
//									
//									Req_t req_cast = new Req_t(data_6);
//									String msg_cast = BasicTool.preSign(req_cast);
//									
//									req_cast.Sig_from = BasicCrypto.MySignature1(msg_cast, Test3.node.node_info.sk, Test3.node.node_info.pk);
//									BasicTool.BroadcastMsg(req_cast);
									
									
									if(checkDataState())
									{
										this.state_task_list.set(loop, TaskState.task_data_processing);
										if(checkProcessState())
										{
											this.state_task_list.set(loop, TaskState.task_feedback_collecting);
										}
									}
									Test3.node.data_read_allowed = 1;
									break;
								}
							}
							break;
						}
					}
					
					

				} catch (ClassNotFoundException | IOException e1) {
					System.out.println("Error");
					e1.printStackTrace();
				}
			}
			
			if(req.trans_type == 3)
			{
				System.out.println("Processing for type 6 for " + req.trans_type);
				for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
				{
					if(req.task_id.value.equals(this.task_list_curr.get(loop).task_id.value))
					{
						Data_t data = new Data_t(req);
						for(int loop2 = 0; loop2 < this.worker_selected.get(loop).size(); loop2 ++)
						{
							if(req.From.value.equals(this.worker_selected.get(loop).get(loop2).value))
							{
								while(true)
								{
									System.out.println("Testing");
									if(this.data_write_allowed == 1)
									{
										this.data_write_allowed = 0;
										

										Data_t data1 = new Data_t(req);
										for(int loop3 = 0; loop3 < data1.content.data.size(); loop3 ++)
										{
											File file = new File("files\\TaskData\\"+String.valueOf(loop)+"\\" + String.valueOf(loop2)+ "\\"+String.valueOf(loop3) + ".jpg");
											if(!file.exists())
											{
												if(file.getParentFile().exists() == false)
												{
													if(!file.getParentFile().getParentFile().exists())
													{
														file.getParentFile().getParentFile().mkdirs();
													}
													file.getParentFile().mkdirs();
												}
												file.createNewFile();
												
												
												FileOutputStream fos = new FileOutputStream(file);
												fos.write(data1.content.data.get(loop3));
											}
										}

										
										this.data_write_allowed = 1;
										
/*										data1.content.data = new ArrayList<byte[]>();
										data1.trans_type = 1;
										req = new Req_t(data1);
										this.process_case6(req);*/
										System.out.println("Data Created");
										break;
									}
								}
							}
							break;
						}
						break;
					}
				}
			}
			
		}
		
	}
	public void process_case7(Req_t req) throws ClassNotFoundException, IOException, NoSuchAlgorithmException
	{
		FB_t fb_temp = new FB_t(req);
		while(true)
		{
			System.out.println("");
			if(this.fb_read_allowed == 1 && this.mining_allowed == 1)
			{
				this.fb_read_allowed = 0;
				
				for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
				{
					if(this.task_list_curr.get(loop).task_id.value.equals(fb_temp.task_id.value))
					{
						this.feedback_list_collected.get(loop).add(fb_temp);
						this.feedback_list_collected_record.get(loop).add(0);
						
						this.pay_decided_total.value += updatePayAll(loop);
						
						
						 if(this.feedback_list_collected.get(loop).size() ==
			                        this.worker_selected.get(loop).size() * this.worker_selected.get(loop).size())
			                {
			                    //Trust Evaluation;
			                    for(int k = 0; k < this.feedback_list_collected.get(loop).size(); k ++)
			                    {
			                        int location = -1;
			                        ArrayList<FB_t> Feedback = this.feedback_list_collected.get(loop);
			                        ArrayList<Pay_t> Pay = new ArrayList<Pay_t>();
			                        for(int j = 0; j < this.feedback_list_collected.get(loop).size(); j ++)
			                        {
			                        	Pay_t pay = new Pay_t();
			                        	pay.value = 0.0;
			                        	for(int j1 = 0; j1 < this.bid_list.get(loop).size(); j1 ++)
			                        	{
			                        		if(Feedback.get(j).From.value.equals(this.bid_list.get(loop).get(j1).From.value))
			                        		{
			                        			pay.value += this.bid_list.get(loop).get(j1).content.pay_expected.value;
			                        		}
			                        	}
			                        	Pay.add(pay);
			                        }
			                        TV_t TrustPas = new TV_t();
			                        TrustPas.value = 0;
			                        TrustPas.start = 0;
			                        for(int i = 0; i < this.node_list.size(); i ++)
			                        {
			                            if(this.feedback_list_collected.get(loop).get(k).From.value.equals(this.node_list.get(i).id.value))
			                            {
			                                TrustPas.value = this.node_list.get(i).tv.value;
			                                TrustPas.start = this.node_list.get(i).tv.start;
			                                location = i;
			                            }
			                        }
			                        Integer NumofFB = this.feedback_list_collected.get(loop).size();
			                        Integer Kcurr = this.current_block_list.size();
			                        Integer Kpast = TrustPas.start;
			                        Integer KofFB = TrustPas.start;
			                        Double Tau = SystemParam.getTau();
			                        


			                        Double trust_res = TrustEva.TrustEvaluation(Feedback,Pay,TrustPas,NumofFB, Kcurr, Kpast, KofFB, Tau);
			                        if(location != -1)
			                        {
			                            this.node_list.get(location).tv.value = trust_res;
			                            this.node_list.get(location).tv.start = this.block_list.size();
			                        }
			                        else
			                        {
			                            TV_t tv_temp = new TV_t();
			                            tv_temp.value = trust_res;
			                            tv_temp.start = this.current_block_list.size();
			                            SimplifiedNodeProfile_t snt = new SimplifiedNodeProfile_t();
			                            snt.tv = new TV_t();
			                            snt.tv = tv_temp;
			                            snt.id = new ID_t();
			                            snt.id = this.feedback_list_collected.get(loop).get(k).From;
			                           /* snt.pk = this.feedback_list_collected.get(loop).get(k).pk_sender;*/
			                            this.node_list.add(snt);
			                        }
			                    }

			                }
						
						
						
						break;
					}
				}
				
               
				
				this.fb_read_allowed = 1;
				break;
			}
		}
		
		System.out.println("Finished\t7");
		/*blockgen();*/
		if(this.undecided_block_list.size() == 0)
		{
			this.blockgen();
		}
		
		
	}
	public void process_listening() throws IOException, ClassNotFoundException
	{
		
	}

	public void process_case10_gen()
	{
		
		
		Req_t req = null;
		try {
			req = new Req_t();
		} catch (UnknownHostException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		req.req_type = 10;
		req.trans_type = 999;
		req.task_id = null;
		req.From = this.node_info.id;
		req.content = this.ip;
		//System.out.print("check ip\t" + this.ip);
		req.pk_sender = this.node_info.pk;
		req.ip_addr = this.ip;

	
		try {
			req.Sig_from = BasicCrypto.MySignature1(req.content, this.node_info.sk, this.node_info.pk);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		String msg = null;
		try {
			msg = req.serialize();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}		
		
		for(int loop = 0; loop < this.incoming_potential.size(); loop ++) 
		{
			Socket socket = null;
			if(!BasicTool.isInICList(this.incoming_potential.get(loop)))
			{
				try {
					socket = new Socket(this.incoming_potential.get(loop), SystemParam.GotGlobalPort());
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
					continue;
				}			
				DataOutputStream out = null;
				try {
					if(socket != null)
					{
						out = new DataOutputStream(socket.getOutputStream());
						if(out != null)
							out.writeUTF(msg);
						socket.close();
					}
						
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}

			}
		}

	}
	public void process_case11_gen()
	{
		Req_t req = null;
		try {
			req = new Req_t();
		} catch (UnknownHostException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		req.req_type = 11;
		req.trans_type = -11;
		req.task_id = null;
		req.From = this.node_info.id;
		req.content = this.ip;
		req.pk_sender = this.node_info.pk;
		req.ip_addr = this.ip;
	
		try {
			req.Sig_from = BasicCrypto.MySignature1(req.content, this.node_info.sk, this.node_info.pk);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		String msg = null;
		try {
			msg = req.serialize();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}		
		
		for(int loop = 0; loop < this.outgoing_potential.size(); loop ++)
		{
			Socket socket = null;
			
			if(BasicTool.isInOGList(this.outgoing_potential.get(loop)))
			{
				try {
					socket = new Socket(this.outgoing_potential.get(loop), SystemParam.GotGlobalPort());
				} catch (IOException e) {
					System.out.println("Error" + this.outgoing_potential.get(loop));
					e.printStackTrace();
				}			
				DataOutputStream out = null;
				try {
					if(socket != null)
						out = new DataOutputStream(socket.getOutputStream());
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}

				try {
					if(out != null)
						out.writeUTF(msg);
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}	
			}
		}
	}
	public void process_case12(Req_t req)
	{
		try {
			if(BasicCrypto.SignatureVerify1(req.content, req.pk_sender, req.Sig_from))
			{
				Socket socket = new Socket(req.content, SystemParam.GotGlobalPort());
				
				if(BasicTool.isInOGList(req.ip_addr))
				{
					return;
				}
				else
				{
					while(true)
					{
						if(this.og_write_allowed == 1)
						{
							
							this.og_write_allowed = 0;
							this.outgoing_list.id_list.add(req.From);
							this.outgoing_list.ip_list.add(req.ip_addr);
							this.outgoing_list.socket_list.add(socket);
							this.outgoing_list.times.add(0);
							this.og_write_allowed = 1;
							
							break;
						}
					}
					
					Req_t req_reply = new Req_t();
					
					req_reply.content = new String(this.ip);
					req_reply.From.value = this.node_info.id.value;
					req_reply.ip_addr = this.ip;
					req_reply.pk_sender.value.set(this.node_info.pk.value);
					req_reply.req_type = 14;
					req_reply.task_id.value = null;
					req_reply.trans_type = 999;
					String msg = req_reply.content
								+req_reply.From.value
								+req_reply.ip_addr
								+req_reply.pk_sender.value.toString()
								+String.valueOf(req_reply.req_type)
								+req_reply.task_id.value
								+String.valueOf(req_reply.trans_type);
					
					Sig_t sig = new Sig_t();
					sig = BasicCrypto.MySignature1(msg, this.node_info.sk, this.node_info.pk);
					
					req_reply.Sig_from = sig;
					
					String str_reply = req_reply.serialize();
					//Socket socket_send = new Socket(req.ip_addr, SystemParam.GotGlobalPort());
					if(socket != null)
					{
						DataOutputStream out = null;
						out = new DataOutputStream(socket.getOutputStream());
						if(out != null)
						{
							out.writeUTF(str_reply);
						}
						socket.close();
					}
				}
				return;
				
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	public void process_case13(Req_t req)
	{
		if(BasicTool.isInICList(req.ip_addr))
		{
			return;
		}
		
		else
		{
			try {
				if(BasicCrypto.SignatureVerify1(req.content, req.pk_sender, req.Sig_from))
				{
					Socket socket = new Socket(req.content, SystemParam.GotGlobalPort());
					this.incoming_list.socket_list.add(socket);
					this.incoming_list.id_list.add(req.From);
					this.incoming_list.ip_list.add(req.ip_addr);
					this.incoming_list.times.add(0);
					
					Req_t req_reply = new Req_t();
					
					req_reply.content = new String(this.ip);
					req_reply.From.value = this.node_info.id.value;
					req_reply.ip_addr = this.ip;
					req_reply.pk_sender.value.set(this.node_info.pk.value);
					req_reply.req_type = 15;
					req_reply.task_id.value = null;
					req_reply.trans_type = 999;
					String msg = req_reply.content
								+req_reply.From.value
								+req_reply.ip_addr
								+req_reply.pk_sender.value.toString()
								+String.valueOf(req_reply.req_type)
								+req_reply.task_id.value
								+String.valueOf(req_reply.trans_type);
					
					Sig_t sig = new Sig_t();
					sig = BasicCrypto.MySignature1(msg, this.node_info.sk, this.node_info.pk);
					
					req_reply.Sig_from = sig;
					
					String str_reply = req_reply.serialize();
					//Socket socket_send = new Socket(req.ip_addr, SystemParam.GotGlobalPort());
					if(socket != null)
					{
						DataOutputStream out = null;
						out = new DataOutputStream(socket.getOutputStream());
						if(out != null)
						{
							out.writeUTF(str_reply);
						}
						socket.close();
					}
					
					
				}
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Error");
				e.printStackTrace();
			} catch (UnknownHostException e) {
				System.out.println("Error");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
			System.out.println("Finished:\t" + req.req_type);
		}
	}
	public void process_case14(Req_t req)
	{	
		String msg = req.content
				+req.From.value
				+req.ip_addr
				+req.pk_sender.value.toString()
				+String.valueOf(req.req_type)
				+req.task_id.value
				+String.valueOf(req.trans_type);

		try {
			if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from))
			{
				while(true)
				{
					if(this.ic_write_allowed == 1)
					{
						this.ic_write_allowed = 0;
						//System.out.println("Started");
						//Socket socket = new Socket(req.content, SystemParam.GotGlobalPort());
						//this.incoming_list.socket_list.add(socket);
						this.incoming_list.id_list.add(req.From);
						this.incoming_list.ip_list.add(req.content);
						this.incoming_list.times.add(0);
						//System.out.println("Finished:\t" + req.req_type);
						this.ic_write_allowed = 1;
						break;
					}
				}

			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error");
			e.printStackTrace();
		} 
		System.out.println("Finished:\t" + req.req_type);
	}
	public void process_case15(Req_t req)
	{
		String msg = req.content
				+req.From.value
				+req.ip_addr
				+req.pk_sender.value.toString()
				+String.valueOf(req.req_type)
				+req.task_id.value
				+String.valueOf(req.trans_type);
//		try {
//			System.out.println(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from));
//		} catch (NoSuchAlgorithmException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			if(BasicCrypto.SignatureVerify1(msg, req.pk_sender, req.Sig_from))
			{
				//System.out.println(this.og_write_allowed);
				while(true)
				{
					if(this.og_write_allowed == 1)
					{
						//System.out.println(this.og_write_allowed);
						this.og_write_allowed = 0;
						//Socket socket = new Socket(req.content, SystemParam.GotGlobalPort());
						this.outgoing_list.id_list.add(req.From);
						this.outgoing_list.ip_list.add(req.ip_addr);
						//his.outgoing_list.socket_list.add(socket);
						this.outgoing_list.times.add(0);
						this.og_write_allowed = 1;
						//socket.close();
						break;
					}

				}
				System.out.println("Finished:\t" + req.req_type);
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error in Node.java case 13 processing function");
			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			System.out.println("Error");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("Error");
//			e.printStackTrace();
		}
	}
	
	public boolean checkBlockValue()
	{
		boolean res = false;
		

		
		if(this.pay_decided_total.value.doubleValue() >= SystemParam.getBlockThres())
			res = true;
		return res;
	}
	public int process_required_check = 0;
	public int mining(Block_t block)
	{
		return 1;
	}
	public boolean checkBidState()
	{
		boolean res = false;
		return res;
	}
	public boolean checkDataState()
	{
		boolean res = false;
		return res;
	}
	public boolean checkProcessState()
	{
		boolean res = false;
		return res;
	}
	public boolean checkFeedback()
	{
		return true;
	}
	
	public double updatePayAll(int index)
	{
		double res = 0;
		
		for(int loop = 0; loop < this.worker_selected.get(index).size(); loop ++)
		{
			for(int loop2 = 0; loop2 < this.bid_list.get(index).size(); loop2 ++)
			{
				if(this.worker_selected.get(index).get(loop).value.equals(this.bid_list.get(index).get(loop2).From.value))
				{
					res += this.bid_list.get(index).get(loop2).content.pay_expected.value;
				}
			}
		}
		
		return res;
	}
	
	public void blockgen()
	{
		if(this.checkBlockValue() == true)
		{
			System.out.println("find a block");
			if(Test3.node.isAll != 3)
			{
				Test3.node.setAll(2);
				
				System.out.println(Test3.node.waid 
									+ "\tTrying to deal with the new found block");
				Test3.node.waid ++;
				
				
				
				Block_t block = new Block_t();

				byte[] hash_temp = null;

				try {
					if(Test3.node.block_list.size() > 0)
					{
						hash_temp = BasicCrypto.MyHash(Test3.node.current_block_list.get(Test3.node.current_block_list.size() - 1).serialize());
					}
					else
					{
						hash_temp = BasicCrypto.MyHash("Start");
					}
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Some error happened when starting mining");
					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Some error happened when starting mining");
					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
					e.printStackTrace();
				}
				
				

				
				
				ArrayList<String> str_list_temp = new ArrayList<String>();
	//////////////bid				
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				for(int loop = 0; loop < this.bid_list.size(); loop ++) 
				{
					for(int loop2 = 0; loop2 < this.bid_list.get(loop).size(); loop2 ++)
					{
						if(this.bid_list_record.get(loop).get(loop2) == 0)
						{
							try {
								str_list_temp.add(this.bid_list.get(loop).get(loop2).serialize());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				
				}
				
				try {
					block.merkle_bid = new MerkleTree(str_list_temp);
				} catch (NoSuchAlgorithmException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				//				for(int loop = 0; loop < Test3.node.bid_list.size(); loop ++)
//				{
//					for(int loop2 = 0; loop2 < Test3.node.bid_list.get(loop).size(); loop2 ++)
//					{
//						if(Test3.node.bid_list_record.get(loop).get(loop2) == 0)
//						{
//							byte[] byte_temp = null;
//							try {
//								byte_temp = BasicCrypto.MyHash(Test3.node.bid_list.get(loop).get(loop2).serialize());
//							} catch (NoSuchAlgorithmException e) {
//								System.out.println("Some error happened when starting mining");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							} catch (IOException e) {
//								System.out.println("Some error happened when starting mining");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							}
//							String str_temp = new String(byte_temp);
//							str_list_temp.add(str_temp);
//							
//							System.out.println(str_temp);
//						}
//						
//					}			
//				}
//				try 
//				{
//					block.merkle_bid = new MerkleTree(str_list_temp);
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("Error");
//					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//					e.printStackTrace();
//				}		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//Data
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				str_list_temp = new ArrayList<String>();
				for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
				{
					for(int loop2 = 0; loop2 < this.data_hash_list.get(loop).size(); loop2 ++)
					{
						if(this.data_hash_list_record.get(loop).get(loop2) == 0)
						{
							try {
								str_list_temp.add(this.data_hash_list.get(loop).get(loop2).serialize());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
				if(str_list_temp.size() != 0)
				{
					try {
						block.merkle_data = new MerkleTree(str_list_temp);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				//				str_list_temp = new ArrayList<String>();
//				for(int loop = 0; loop < Test3.node.data_hash_list.size(); loop ++)
//				{
//					for(int loop2 = 0; loop2 < Test3.node.data_hash_list.get(loop).size(); loop2 ++)
//					{
//						if(Test3.node.data_hash_list_record.get(loop).get(loop2)== 0)
//						{
//							try 
//							{
//								str_list_temp.add(new String(BasicCrypto.MyHash(Test3.node.data_hash_list.get(loop).get(loop2).serialize())));
//							} catch (NoSuchAlgorithmException e) {
//								System.out.println("Error");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							} catch (IOException e) {
//								System.out.println("Error");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//				try 
//				{
//					if(str_list_temp.size() != 0)
//					{
//						block.merkle_data = new MerkleTree(str_list_temp);
//					}
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("Error");
//					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//					e.printStackTrace();
//				}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				
				str_list_temp = new ArrayList<String>();
				//feedback
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
				for(int loop = 0; loop < this.feedback_list_collected.size(); loop ++)
				{
					for(int loop2 = 0; loop2 < this.feedback_list_collected.get(loop).size(); loop2 ++)
					{
						if(this.feedback_list_collected_record.get(loop).get(loop2) == 0)
						{
							try {
								str_list_temp.add(this.feedback_list_collected.get(loop).get(loop2).serialize());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
				if(str_list_temp.size() != 0)
				{
					try {
						block.merkle_feedback = new MerkleTree(str_list_temp);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
				
//				for(int loop = 0; loop < Test3.node.feedback_list_collected.size(); loop ++)
//				{
//					for(int loop2 = 0; loop2 < Test3.node.feedback_list_collected.get(loop).size(); loop2 ++)
//					{
//						if(Test3.node.feedback_list_collected_record.get(loop).get(loop2)== 0)
//						{
//							try {
//								str_list_temp.add(new String(BasicCrypto.MyHash(Test3.node.feedback_list_collected.get(loop).get(loop2).serialize())));
//							} catch (NoSuchAlgorithmException e) {
//								System.out.println("Error");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							} catch (IOException e) {
//								System.out.println("Error");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//				
//				try {
//					block.merkle_feedback = new MerkleTree(str_list_temp);
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("Error");
//					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//					e.printStackTrace();
//				}
//				
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				str_list_temp = new ArrayList<String>();
				
//				Maybe good not for present
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//				for(int loop = 0; loop < Test3.node.state_task_list.size(); loop ++)
//				{
//					try {
//						str_list_temp.add(new String (BasicCrypto.MyHash(String.valueOf(Test3.node.state_task_list.get(loop)))));
//					} catch (NoSuchAlgorithmException e) {
//						System.out.println("Error");
//						WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//						e.printStackTrace();
//					}
//				}
//				
//				try {
//					if(str_list_temp.size() != 0)
//					{
//						block.merkle_state = new MerkleTree(str_list_temp);
//					}
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("Error");
//					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//					e.printStackTrace();
//				}
//				
//				
		
//task
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				str_list_temp = new ArrayList<String>();
				for(int loop = 0; loop < this.task_list_curr.size(); loop ++)
				{
					if(this.task_record.get(loop) == 0)
					{
						try {
							str_list_temp.add(this.task_list_curr.get(loop).serialize());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(str_list_temp.size() != 0)
				{
					try {
						block.merkle_task = new MerkleTree(str_list_temp);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				

				str_list_temp = new ArrayList<String>();
				for(int loop = 0; loop < this.worker_selected.size(); loop ++)
				{
					for(int loop2 = 0; loop2 < this.worker_selected.get(loop).size(); loop2 ++)
					{
						if(this.worker_selected_record.get(loop).get(loop2) == 0)
						{
							str_list_temp.add(this.worker_selected.get(loop).get(loop2).value);
//							str_list_temp.add(this.tak
						}
					}
				}
				
				if(str_list_temp.size() != 0)
				{
					try {
						block.merkle_worker = new MerkleTree(str_list_temp);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
				
				
//				for(int loop = 0; loop < Test3.node.worker_selected.size(); loop ++)
//				{
//					for (int loop2 = 0; loop2 < Test3.node.worker_selected.get(loop).size(); loop2 ++)
//					{
//						if(Test3.node.worker_selected_record.get(loop).get(loop2) == 0)
//						{
//							try {
//								str_list_temp.add(new String(BasicCrypto.MyHash(Test3.node.worker_selected.get(loop).get(loop2).value)));
//							} catch (NoSuchAlgorithmException e) {
//								System.out.println("Error");
//								WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//				try {
//					if(str_list_temp.size() != 0)
//					{
//						block.merkle_state = new MerkleTree(str_list_temp);
//					}
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("Error");
//					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
//					e.printStackTrace();
//				}
				
				block.time.value = System.currentTimeMillis();
				block.num_block_obtain = Test3.node.num_block_obtain;
				Test3.node.num_block_obtain ++;
				block.pk = Test3.node.node_info.pk;
				block.tv = Test3.node.node_info.tv;
				block.hash_prev = new String(hash_temp);
				block.no = Test3.node.block_list.size();
				block.id.value = new String(Test3.node.node_info.id.value);
				
				String str_temp_all = new String();
				str_temp_all = String.valueOf(block.no) 
						+ block.id.value
						+ String.valueOf(block.tv.value)
						+ String.valueOf(block.time.value)
						+ block.hash_prev
						+ String.valueOf(block.num_block_obtain)
						
						+ new String(block.pk.value.toBytes())
						
						+ block.merkle_bid.getString()
						+ block.merkle_data.getString()
						+ block.merkle_feedback.getString()
						+ block.merkle_state.getString()
						+ block.merkle_task.getString()
						+ block.merkle_worker.getString();
				
				
				System.out.println("Runtime Info of MSG block");
				System.out.println(str_temp_all);
									
				
				try {
					block.sig = BasicCrypto.MySignature1(str_temp_all, Test3.node.node_info.sk, Test3.node.node_info.pk);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Error");
					WarningWindow ww = new WarningWindow("Some error happened when starting mining");
					e.printStackTrace();
				}
				
				
				Test3.node.setAll(1);
				this.pay_decided_total.value = 0.0;
				
				

				this.undecided_block_list.add(block);//in blockgen
				
				Req_t req_block = null;
				try {
					System.out.println(block.serialize());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try {
					req_block = new Req_t(block);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Block_t block2;
				block2 = new Block_t();
				try {
					block2 = block.deserialize(req_block.content);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					System.out.println("Testing in case2 processing\t" + BasicCrypto.SignatureVerify1(str_temp_all, block.pk, block.sig));
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String msg = BasicTool.preSignBlock(req_block);
				try {
					req_block.Sig_from = BasicCrypto.MySignature1(msg, Test3.node.node_info.sk, Test3.node.node_info.pk);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					BasicTool.BroadcastMsg(req_block);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("New Block found, block id is " + block.no);
				return;
			}
				
			
		}
	}
	
	public void block_select() throws ClassNotFoundException, IOException, NoSuchAlgorithmException
	{
		if(this.undecided_block_list.size() == 0)
		{
			this.time0 = System.currentTimeMillis();
		}
		else
		{
			long temp = System.currentTimeMillis();
			temp = temp - time0;
			if((temp > 6000)&&(Test3.node.undecided_block_list.size() != 0))
			{
				this.time0 = 0;
				Block_t block = new Block_t();
				if(Test3.node.undecided_block_list.size() == 1)
				{
					block = Test3.node.undecided_block_list.get(0);
				}
				else if(Test3.node.undecided_block_list.size() == 2)
				{
					block = BlockFunc.Blockdecider(this.undecided_block_list.get(0), this.undecided_block_list.get(1));
				}
				else
				{
					block = BlockFunc.Blockdecider(this.undecided_block_list.get(0), this.undecided_block_list.get(1));
					for(int loop = 2; loop < this.undecided_block_list.size(); loop ++)
					{
						block = BlockFunc.Blockdecider(block, this.undecided_block_list.get(loop));
					}
				}
			
				//delete un realted items
				

				for(int loop = 0; loop < block.merkle_task.trans_list.size(); loop ++)
				{
					String str_temp = block.merkle_task.trans_list.get(loop);
					Task_t task = new Task_t();
					task  = task.deserialize(str_temp);
					
					for(int loop2 = 0; loop2 < this.task_list_curr.size(); loop2 ++)
					{
						if(task.task_id.value.equals(this.task_list_curr.get(loop2).task_id.value))
						{
							this.task_record.set(loop2, 1);
							break;
						}
					}
				}
				
//				block.merkle_bid

				for(int loop = 0; loop < block.merkle_bid.trans_list.size(); loop ++)
				{
					String str_temp = block.merkle_bid.trans_list.get(loop);
					Bid_t bid_temp = new Bid_t();
					bid_temp = bid_temp.deserialize(str_temp);
					
					int task_no = -1;
					
					for(int loop2 = 0;loop2 < this.task_list_curr.size(); loop2 ++)
					{
						if(bid_temp.task_id.value.equals(this.task_list_curr.get(loop2).task_id.value))
						{
							String str_temp1_hash = new String(BasicCrypto.MyHash(bid_temp.serialize()));
							for(int loop3 = 0; loop3 < this.bid_list.get(loop2).size(); loop3 ++)
							{
								String str_temp2_hash = new String(BasicCrypto.MyHash(this.bid_list.get(loop2).get(loop3).serialize()));
								if(str_temp1_hash.equals(str_temp2_hash));
								{
									this.bid_list_record.get(loop2).set(loop3,  1);
									break;
								}
							}
							break;
						}
					}
				}
				
//				block.merkle_data
				
				for(int loop = 0; loop < block.merkle_data.trans_list.size(); loop ++)
				{
					Data_t data_temp = new Data_t();
					data_temp = data_temp.deserialize(block.merkle_data.trans_list.get(loop));
					
					for(int loop2 = 0; loop2 < this.task_list_curr.size(); loop2 ++)
					{
						if(data_temp.task_id.value.equals(this.task_list_curr.get(loop2).task_id.value))
						{
							String str_temp1_hash = new String(BasicCrypto.MyHash(data_temp.serialize()));
							for(int loop3 = 0; loop3 < this.data_hash_list.get(loop2).size(); loop3 ++)
							{
								if(this.data_hash_list.get(loop2).get(loop3).equals(str_temp1_hash))
								{
									this.data_hash_list_record.get(loop2).set(loop3, 1);
									
									break;
								}
							}
							break;
						}
					}
				}
				
//				block.merkle_feedback
				ArrayList<ArrayList<FB_t>> fb_list = new ArrayList<ArrayList<FB_t>>();
				for(int loop = 0; loop < block.merkle_feedback.trans_list.size(); loop ++)
				{
					FB_t fb_temp = new FB_t();
					fb_temp = fb_temp.deserialize(block.merkle_feedback.trans_list.get(loop));
					
					for(int loop2 = 0; loop2 < this.task_list_curr.size(); loop2++)
					{
						if(fb_temp.task_id.value.equals(this.task_list_curr.get(loop2)))
						{
							String str_temp1_hash = new String(BasicCrypto.MyHash(fb_temp.serialize()));
							
							for(int loop3 = 0; loop3 < this.feedback_list_collected.get(loop2).size(); loop3 ++)
							{
								if(str_temp1_hash.equals(new String(BasicCrypto.MyHash(this.feedback_list_collected.get(loop2).get(loop3).serialize()))))
								{
									this.feedback_list_collected_record.get(loop2).set(loop3, 1);
									
									for(int loop4 = 0; loop4 < block.merkle_bid.trans_list.size(); loop4 ++)
									{
										String str_temp2_hash = new String(BasicCrypto.MyHash(block.merkle_bid.trans_list.get(loop4)));
										if(fb_temp.content.bid_hash.equals(str_temp2_hash))
										{
											Bid_t bid = new Bid_t();
											this.pay_decided_total.value -= bid.deserialize(block.merkle_bid.trans_list.get(loop4)).content.pay_expected.value;
											break;
										}
									}
									
									break;
								}
							}
							
							break;
						}
					}
					
					if(fb_list.size() == 0)
                    {
                        fb_list.add(new ArrayList<FB_t>());
                        fb_list.get(0).add(fb_temp);
                    }
                    for(int loop5 = 0; loop5 < fb_list.size(); loop5 ++)
                    {
                        if(fb_temp.From.value.equals(fb_list.get(loop5).get(0).content.evaluatee.value))
                        {
                            fb_list.get(loop5).add(fb_temp);
                            break;
                        }
                    }
					
				}
				
				
//				block.merkle_state

//				block.merkle_worker
				for(int loop0 = 0; loop0 < block.merkle_worker.trans_list.size(); loop0 ++)
				{
					for(int loop = 0; loop < this.worker_selected.size(); loop ++)
					{
						int flag_break = 0;
						for(int loop2 = 0; loop2 < this.worker_selected.get(loop).size(); loop2 ++)
						{
							if(block.merkle_worker.trans_list.get(loop0).equals(this.worker_selected.get(loop).get(loop2).value))
							{
								this.worker_selected_record.get(loop).set(loop2, 0);
								flag_break = 1;
								break;
							}
						}
						if(flag_break == 1)
							break;
					}
				}
				
				
				this.current_block_list.add(block);//block request processing
				this.pay_decided_total.value = 0.0;
				this.undecided_block_list.clear();// This is not fair
				System.out.println("The\t" + block.no +"\tblock is decided");
			}
			
		}
	}
}