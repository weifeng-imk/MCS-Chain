package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.text.html.parser.Element;

class Task_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*8895323869179354222L;*/1L;
	//basic information, 5 totally
	public int trans_type;	
	public ID_t task_id;
	public PK_t pk_sender;
	public Sig_t SigFrom;
	public ID_t From;
	public String ip_addr;
	public Integer req_type;
	
	//content related	
	public TaskContent_t content;
	
	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString(/*"ISO-8859-1"*/"ISO-8859-1");
        return res;
	}
	
	public Task_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Task_t res =(Task_t) objIn.readObject();  
        return res;  
	}
	
	public Task_t() throws UnknownHostException
	{
		this.trans_type = 99999;
		this.task_id = new ID_t();
		this.pk_sender = new PK_t();
		this.SigFrom = new Sig_t();
		this.ip_addr = new String(InetAddress.getLocalHost().getHostName());
		this.content = new TaskContent_t();
		this.req_type = 3;
	}
	

	public Task_t(Req_t req_input) throws ClassNotFoundException, IOException
	{

			this.trans_type = req_input.trans_type;
			this.task_id = req_input.task_id;
			this.pk_sender = req_input.pk_sender;
			this.SigFrom = req_input.Sig_from;
			this.From = req_input.From;
			this.ip_addr = req_input.ip_addr;
			this.req_type = req_input.req_type;
			System.out.println("Testtestestestestestest" + req_input.content == null);
			this.content = new TaskContent_t();
			this.content = this.content.deserialize(req_input.content);

	}
	
}

class TaskContent_t implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = /*7918441085286152986L;*/1L;
//	public ID_t task_id;
	
	public TaskD_t task_des;
	public ArrayList<SubTask_t> subtask_list;
	
	public TaskContent_t()
	{
//		this.task_id = null;
		this.task_des = new TaskD_t();
		this.subtask_list = new ArrayList<SubTask_t>();
	}
	
	public TaskContent_t(TaskContent_t content_input)
	{
//		this.task_id = content_input.task_id;
		this.task_des = content_input.task_des;
		for(int loop = 0; loop < content_input.subtask_list.size();loop ++)
		{
			this.subtask_list.add(content_input.subtask_list.get(loop));
		}
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public TaskContent_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        TaskContent_t res =(TaskContent_t) objIn.readObject();  
        return res;  
	}
	
}