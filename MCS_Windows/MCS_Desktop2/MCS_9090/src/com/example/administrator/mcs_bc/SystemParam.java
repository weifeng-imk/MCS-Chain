package com.example.administrator.mcs_bc;


class SystemParam
{
	public static int getBlockNumThres()
	{
		return 2;
	}
	
	public static Double getTau()
	{
		return 0.5;
	}
	
	public static double RowSize()
	{
		return 1.0;
	}
	
	public static double ColSize()
	{
		return 1.0;
	}
	
	public static double InitialTV()
	{
		double tv = 3.0;
		return tv;
	}
	
	public static int GotGlobalPort()
	{
		int  port = 9091;
		return port;
	}
	
	public static int GotGlobalPort2()
	{
		return 9090;
	}
	
	public static int ICThres = 100;
	public static int getICThres()
	{
		return ICThres;
	}

	
	public static int OGThres = 100;
	public static int getOGThres()
	{
		return OGThres;
	}
	
	private static double block_threshold = 100;
	public static double getBlockThres()
	{
		return block_threshold;
	}
	
	private static Integer width = 1024;
	private static Integer height = 768;
	
	public static String[] getAttrList()
	{
		String[] res = {"High Image Accuracy", "High Mobility", "Long Period"};
		return res;
	}
}

class ForNode
{
	public static int state_unrecord = 0;
	public static int state_semi = 1;
	public static int state_write = 2;
}

class TaskState
{
	public static int task_initial = 0;
	public static int task_worker_selecting = 1;
	public static int task_data_collecting = 2;
	public static int task_data_processing = 3;
	public static int task_feedback_collecting = 4;
	public static int task_final_processing = 5;
	public static int task_finished = 10;
}

class TransType
{
	public static int basic_type = 0;
	public static int task_type = 1;
	public static int bid_type = 2;
	public static int data_type = 3;
	public static int feedback_type = 4;
	public static int state_type = 5;
	public static int block_type = 6;
}

class ProcessOption
{
	//For Miner
	//case 1: block mining
	public static int case_mining = 1;
	//case 2: block check or comparison
	public static int case_block_checking = 2;
	
	//case 3: receiving a task request
	public static int case_task = 3;
	//case 4: receiving a bid
	public static int case_bid = 4;
	//case 5: processing bid and find the worker (maybe not necessary for this task
	public static int case_worker = 5;
	//case 6: receiving a data declaration and related processing
	public static int case_data = 6;
	//use trans type to adjust
	//case 6.1 only a declaration received;
	//case 6.2 all the data are received, and waiting to be processed;
	//case 6.3 received a data processing result
	//case 6.4 all the data have been processed
	//case 7: receiving a feedback
	public static int case_fb = 7;
	//case 8: receiving all the feedback and perform trust evaluation (should be combined with the previous one
	public static int case_tv = 8;
	//case 9: task finished
	public static int case_task_finished = 9;
	
	
	//case 10: requesting another miner as a incoming node;
	public static int case_ic_req = 10;
	//case 11: requesting another miner as a outgoing node;
	public static int case_og_req = 11;
	//case 12: receiving other node's request to be an incoming node;
	public static int case_ic_req_o = 12;
	//case 13: receiving other nodes' request to be an outgoing node;
	public static int case_og_req_o = 13;
	//case 14: receiving requested node's reply (for;
	public static int case_ic_rp = 14;
	//case 15: receiving requested node's reply;
	public static int case_og_rp = 15;
	
	//case 16: generating feedback as a miner;
	public static int case_fb_gen = 16;
	
	
	//For Worker
	//case 17: generating a bid;
	public static int case_bid_gen = 17;
	//case 18: sending a bid;
	public static int case_bid_send = 18;
	//case 19: generating data;
	public static int case_data_gen = 19;
	//case 20: generating processing result;
	public static int case_proc = 20;
	//case 21: generating feedback;
	public static int case_fb_worker = 21;
	
	
	
	//For End User
	//case 22: generating and sending a task
	public static int case_task_gen = 22;
	//case 23: selecting a node;
	public static int case_node_selection = 23;
	//case 24: generating feedback
	public static int case_fb_eu = 24;
	//case 25: final?
	public static int case_final = 25;
}

class CryptoParam
{
	
}

class ProcessBranch
{
	
}