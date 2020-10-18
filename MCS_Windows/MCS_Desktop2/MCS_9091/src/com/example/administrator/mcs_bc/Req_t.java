package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;



//import java.net.Socket;

class Req_t implements Serializable
{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	public String ip_addr;
	public int req_type;
	public ID_t task_id;  //
	public int trans_type;  // the type of the request
	public String content; // the content of the request
	public  Sig_t Sig_from; // the signature of the type 
	public ID_t From; // who send the request;
	public  PK_t pk_sender;
	
	
//	private void writeObject(ObjectOutputStream out) throws IOException 
//	{  
//        out.defaultWriteObject(); 
// 
//        if(this.pk_sender == null)
//            this.pk_sender = new PK_t();
//        if(this.Sig_from == null)
//        	this.Sig_from = new Sig_t();
//        
//        byte[] str1 = this.pk_sender.value.toBytes();
//        byte[] str2 = this.Sig_from.value.toBytes();
//        
//        out.writeObject(str1);
//        out.writeObject(str2);
//    }
//
//	
//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException 
//	{  
//        //invoke default serialization method
//        in.defaultReadObject();
//  
//        byte[] str1 = (byte[])in.readObject();
//        byte[] str2 = (byte[])in.readObject();
//        
//        this.pk_sender.value = BasicCrypto.G1.newElement();
//        this.pk_sender.value.setFromBytes(str1);
//        this.Sig_from.value.setFromBytes(str2);
//    }
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Req_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		System.out.println("Test in req deserialize str_input\t" + str_input);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Req_t res = new Req_t(); 
        res = (Req_t) objIn.readObject();  
        return res;  
	}
	
	public Req_t(Block_t block) throws IOException
	{
		this.content = block.serialize();
		this.req_type = 2;
		this.From = new ID_t();
		this.From.value = block.id.value;
		this.ip_addr = InetAddress.getLocalHost().getHostAddress();
		this.pk_sender = block.pk;
		this.trans_type = 999;

	}
	
	public Req_t() throws UnknownHostException
	{
		this.req_type = 0;
		this.task_id = new ID_t();
		this.trans_type = 0;
		this.content = new String();
		this.Sig_from = new Sig_t();
		this.From = new ID_t();
		this.pk_sender = new PK_t();
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_t(Req_t input)
	{
		
		this.content = new String(input.content);
		this.From.value = new String(input.From.value);
		this.ip_addr = new String(input.ip_addr);
		this.pk_sender = new PK_t();
		this.pk_sender.value.set(input.pk_sender.value);
		this.req_type = input.req_type;
		this.Sig_from = new Sig_t();
		this.task_id = new ID_t();
		this.task_id.value = new String(input.task_id.value);
		this.trans_type = input.trans_type;
	}
	
	public Req_t(Task_t task_input) throws IOException
	{
		this.task_id = new ID_t();
		this.task_id.value = new String(task_input.task_id.value);
		this.trans_type = task_input.trans_type;
		this.content = task_input.content.serialize();
		this.From = new ID_t();
		this.From.value = new String(task_input.From.value);
		this.Sig_from = task_input.SigFrom;
		this.pk_sender = new PK_t();
		this.pk_sender.value.set(task_input.pk_sender.value);
		this.req_type = task_input.req_type;
		this.ip_addr = new String(task_input.ip_addr);
	}
	
	public Req_t(Bid_t bid_input) throws IOException
	{
		this.task_id = bid_input.task_id;
		this.req_type = bid_input.req_type;
		this.trans_type = bid_input.trans_type;
		this.content = bid_input.content.serialize();
		this.Sig_from = bid_input.SigFrom;
		this.From = bid_input.From;
		this.pk_sender = bid_input.pk_sender;
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_t(Data_t data_input) throws IOException
	{
		this.task_id = data_input.task_id;
		this.req_type = data_input.req_type;
		this.trans_type = data_input.trans_type;
		this.content = data_input.content.serialize();
		this.Sig_from = data_input.SigFrom;
		this.pk_sender = data_input.pk_sender;
		this.From = data_input.From;
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_t(FB_t fb_input) throws IOException
	{
		this.task_id = new ID_t();
		this.content = new String();
		this.Sig_from = new Sig_t();
		this.pk_sender = new PK_t();
		this.From  = new ID_t();
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
		
		this.From.value = fb_input.From.value;
		this.task_id.value = fb_input.task_id.value;
		this.req_type = fb_input.req_type;
		this.trans_type = fb_input.trans_type;
		this.content = fb_input.content.serialize();
		this.Sig_from = fb_input.SigFrom;
		this.pk_sender = fb_input.pk_sender;
	}
}



class Req_fortest implements Serializable
{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	public String ip_addr;
	public int req_type;
	public ID_t task_id;  //
	public int trans_type;  // the type of the request
	public String content; // the content of the request
	public  Sig_t Sig_from; // the signature of the type 
	public ID_t From; // who send the request;
	public  PK_t pk_sender;
	
	
//	private void writeObject(ObjectOutputStream out) throws IOException 
//	{  
//        out.defaultWriteObject(); 
// 
//        if(this.pk_sender == null)
//            this.pk_sender = new PK_t();
//        if(this.Sig_from == null)
//        	this.Sig_from = new Sig_t();
//        
//        byte[] str1 = this.pk_sender.value.toBytes();
//        byte[] str2 = this.Sig_from.value.toBytes();
//        
//        out.writeObject(str1);
//        out.writeObject(str2);
//    }
//
//	
//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException 
//	{  
//        //invoke default serialization method
//        in.defaultReadObject();
//  
//        byte[] str1 = (byte[])in.readObject();
//        byte[] str2 = (byte[])in.readObject();
//        
//        this.pk_sender.value = BasicCrypto.G1.newElement();
//        this.pk_sender.value.setFromBytes(str1);
//        this.Sig_from.value.setFromBytes(str2);
//    }
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Req_fortest deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Req_fortest res = new Req_fortest(); 
        res = (Req_fortest) objIn.readObject();  
        return res;  
	}
	
	public Req_fortest(Block_t block) throws IOException
	{
		this.content = block.serialize();
		this.req_type = 2;
		this.From = new ID_t();
		this.From.value = block.id.value;
		this.ip_addr = InetAddress.getLocalHost().getHostAddress();
		this.pk_sender = block.pk;
		this.trans_type = 999;

	}
	
	public Req_fortest() throws UnknownHostException
	{
		this.req_type = 0;
		this.task_id = new ID_t();
		this.trans_type = 0;
		this.content = new String();
		this.Sig_from = new Sig_t();
		this.From = new ID_t();
		this.pk_sender = new PK_t();
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_fortest(Task_t task_input) throws IOException
	{
		this.task_id = task_input.task_id;
		this.trans_type = task_input.trans_type;
		this.content = task_input.content.serialize();
		this.From = task_input.From;
		this.Sig_from = task_input.SigFrom;
		this.pk_sender = task_input.pk_sender;
		this.req_type = task_input.req_type;
		this.ip_addr = task_input.ip_addr;
	}
	
	public Req_fortest(Bid_t bid_input) throws IOException
	{
		this.task_id = bid_input.task_id;
		this.req_type = bid_input.req_type;
		this.trans_type = bid_input.trans_type;
		this.content = bid_input.content.serialize();
		this.Sig_from = bid_input.SigFrom;
		this.From = bid_input.From;
		this.pk_sender = bid_input.pk_sender;
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_fortest(Data_t data_input) throws IOException
	{
		this.task_id = data_input.task_id;
		this.req_type = data_input.req_type;
		this.trans_type = data_input.trans_type;
		this.content = data_input.content.serialize();
		this.Sig_from = data_input.SigFrom;
		this.pk_sender = data_input.pk_sender;
		this.From = data_input.From;
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
	}
	
	public Req_fortest(FB_t fb_input) throws IOException
	{
		this.task_id = new ID_t();
		this.content = new String();
		this.Sig_from = new Sig_t();
		this.pk_sender = new PK_t();
		this.From  = new ID_t();
		this.ip_addr = new String(InetAddress.getLocalHost().getHostAddress());
		
		this.task_id = fb_input.task_id;
		this.req_type = fb_input.req_type;
		this.trans_type = fb_input.trans_type;
		this.content = fb_input.content.serialize();
		this.Sig_from = fb_input.SigFrom;
		this.pk_sender = fb_input.pk_sender;
	}
}
