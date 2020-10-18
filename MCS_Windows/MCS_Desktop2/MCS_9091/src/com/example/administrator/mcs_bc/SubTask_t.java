package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class SubTask_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*-7716035727164916655L;*/1L;

	//to be finished;
	public int subtask_no;
	
	public TaskD_t subtask_des;
	/*public ArrayList<Attr_t> attr_required_list;*/
	public String requirements;
	public int worker_num;
	public Pay_t pay_budget;

	
	public SubTask_t()
	{
		this.subtask_des = new TaskD_t();
/*		this.attr_required_list = new ArrayList<Attr_t>();*/
		this.requirements = new String();
		this.pay_budget = new Pay_t();

	}
	
	public SubTask_t(SubTask_t subtask_input)
	{
		this.subtask_des.value = new String(subtask_input.subtask_des.value.toCharArray());
/*		for(int loop = 0; loop < subtask_input.attr_required_list.size(); loop ++)
		{
			this.attr_required_list.add(subtask_input.attr_required_list.get(loop));
		}*/
		this.requirements = subtask_input.requirements;
		this.pay_budget.value = subtask_input.pay_budget.value;
		

	}
	
	
	public void set_no(int num)
	{
		this.subtask_no = num;
	}
	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public SubTask_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        SubTask_t res =(SubTask_t) objIn.readObject();  
        return res;  
	}
}

class SubID_t implements Serializable
{
	Integer id;
	
	public SubID_t()
	{
		this.id = new Integer(0);
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public SubID_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        SubID_t res =(SubID_t) objIn.readObject();  
        return res;  
	}
}



