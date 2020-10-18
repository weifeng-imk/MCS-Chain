package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class Pay_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID =/* -338046793443312309L;*/1L;
	public Double value;
	
	public Pay_t()
	{
		this.value = new Double(0);
	}
	
	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Pay_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Pay_t res =(Pay_t) objIn.readObject();  
        return res;  
	}
}

class Result_t
{
	public String value;
	
	public Result_t()
	{
		this.value = new String();
	}
}

class Attr_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*2888011716777309886L;*/1L;
	public Integer value;
	
	public Attr_t()
	{
		this.value = new Integer(-1);
	}
	
	public Attr_t(int input)
	{
		this.value = input;
	}
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Attr_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Attr_t res =(Attr_t) objIn.readObject();  
        return res;  
	}
}

class FB_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*1878971145175599989L;*/1L;
	public ID_t task_id;
	public ID_t From;
	public PK_t pk_sender;
	public int req_type;
	public int trans_type;
	public FBD_t content;
	public Sig_t SigFrom;
	
	public FB_t()
	{
		this.task_id = new ID_t();
		this.From  = new ID_t();
		this.pk_sender = new PK_t();
		this.content = new FBD_t();
		this.SigFrom = new Sig_t();
	}
	
	public FB_t(Req_t req_input) throws ClassNotFoundException, IOException
	{
		this.task_id = new ID_t();
		this.From  = new ID_t();
		this.pk_sender = new PK_t();
		this.content = new FBD_t();
		this.SigFrom = new Sig_t();
		
		this.task_id.value = req_input.task_id.value;
		this.From.value = req_input.From.value;
		this.pk_sender.value.set(req_input.pk_sender.value);
		this.req_type = req_input.req_type;
		this.trans_type = req_input.trans_type;
		this.content = this.content.deserialize(req_input.content);
		this.SigFrom.value.set(req_input.Sig_from.value);
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public FB_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        FB_t res =(FB_t) objIn.readObject();  
        return res;  
	}
	
}

class FBD_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*-8189107088227909491L;*/1L;
	
//	public ArrayList<ID_t> evaluatee;
//	public ArrayList<Double> value;
	
	public ID_t evaluatee;
	public double value;
	public String bid_hash;
	
	public FBD_t()
	{
//		this.evaluatee = new ArrayList<ID_t>();
//		this.value = new ArrayList<Double>();
		
		this.evaluatee = new ID_t();
		this.value = 0;
		this.bid_hash = new String();
	}
	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public FBD_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        FBD_t res =(FBD_t) objIn.readObject();  
        return res;  
	}
}

class TV_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*6175829030401170459L;*/1L;
	public double value;
	public int start;
	
	public TV_t()
	{
		this.value = 0;
		this.start = 0;
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public TV_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        TV_t res =(TV_t) objIn.readObject();  
        return res;  
	}
}

class TaskD_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*8880039376705296843L;*/1L;
	public String value;
	
	public TaskD_t()
	{
		this.value  = new String();
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public TaskD_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        TaskD_t res =(TaskD_t) objIn.readObject();  
        return res;  
	}
}
