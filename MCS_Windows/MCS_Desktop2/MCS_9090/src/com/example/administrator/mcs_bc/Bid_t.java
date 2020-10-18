package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



class Bid_list implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Bid_t> bid_list;
	
	public Bid_list()
	{
		this.bid_list = new ArrayList<Bid_t>();
	}
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Bid_list deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Bid_list res =(Bid_list) objIn.readObject();  
        return res;  
	}
	
}

class Bid_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*5207023324770102174L;*/1L;
	//basic Information
	public int trans_type;
	public ID_t task_id;
	public Sig_t SigFrom;
	public ID_t From;
	public PK_t pk_sender;
	public int req_type;
	
	
	//public TransType_t TransType;
	public BidContent_t content;

	

	public Bid_t(Bid_t bid)
	{
		this.trans_type = bid.trans_type;
		this.task_id = new ID_t();
		this.task_id.value = new String(bid.task_id.value);
		this.SigFrom = new Sig_t();
		this.SigFrom.value.set(bid.SigFrom.value);
		this.From = new ID_t();
		this.From.value = new String(bid.From.value);
		this.pk_sender = new PK_t();
		this.pk_sender.value.set(bid.pk_sender.value);
		this.req_type = bid.req_type;
		this.content = new BidContent_t(bid.content);
	}
	
	public Bid_t()
	{
		this.trans_type =4;
		this.req_type = 4;
		this.pk_sender = new PK_t();
		this.task_id = new ID_t();
		this.content = new BidContent_t();
		this.SigFrom = new Sig_t();
		this.From = new ID_t();
	}
	
	public Bid_t(Req_t req_input) throws ClassNotFoundException, IOException
	{
		if(req_input.req_type != 4)
			System.out.println("wrong");
		else
		{
			this.trans_type = req_input.trans_type;
			this.task_id = new ID_t();
			this.task_id.value = req_input.task_id.value;
			this.From = new ID_t();
			this.SigFrom = req_input.Sig_from;
			this.SigFrom = new Sig_t();
			this.pk_sender = new PK_t();
			this.pk_sender = req_input.pk_sender;
			this.From = req_input.From;
			this.content = new BidContent_t();
			this.content = this.content.deserialize(req_input.content);
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
	
	public Bid_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Bid_t res =(Bid_t) objIn.readObject();  
        return res;  
	}
	
	
	
}



class BidContent_t implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = /*1363541143288875793L;*/1L;
	/*ArrayList<Attr_t> attr_list;*/
	String attr;
	SubID_t sub_id;
	Pay_t pay_expected;
	
	public BidContent_t(BidContent_t input)
	{
		this.attr = new String(input.attr);
		this.sub_id = new SubID_t();
		this.sub_id.id = input.sub_id.id;
		this.pay_expected = new Pay_t();
		this.pay_expected.value = input.pay_expected.value;
	}
	
	public BidContent_t()
	{
		/*        this.attr_list = new ArrayList<Attr_t>();*/
        this.sub_id = new SubID_t();
        this.attr = new String();
        this.pay_expected = new Pay_t();
	}
	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public BidContent_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        BidContent_t res =(BidContent_t) objIn.readObject();  
        return res;  
	}
}
