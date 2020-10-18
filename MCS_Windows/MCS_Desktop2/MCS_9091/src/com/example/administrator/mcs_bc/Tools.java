package com.example.administrator.mcs_bc;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

class DataandTime
{
	public static String getDataandTime()
	{
		Integer y,m,d,h,mi,s;   
		Calendar cal=Calendar.getInstance();   
		y=cal.get(Calendar.YEAR);   
		m=cal.get(Calendar.MONTH);   
		d=cal.get(Calendar.DATE);   
		h=cal.get(Calendar.HOUR_OF_DAY);   
		mi=cal.get(Calendar.MINUTE);   
		s=cal.get(Calendar.SECOND);  
		
		String res = new String();
		res = y.toString() + "-" + Integer.valueOf(m + 1).toString() + "-" + d.toString() + ", " +h.toString() + ":" + mi.toString() + ":" + s.toString() + "\n";
		return res;
	}
	

}

class BasicTool
{

	public static String preSignBlock(Req_t req)
	{
		String msg = req.content
				+req.From.value
				+req.ip_addr
				+req.pk_sender.value.toString()
				+String.valueOf(req.req_type)
//				+req.task_id.value
				+String.valueOf(req.trans_type);
		
		return msg;
	}
	
	public static String preSign(Req_t req)
	{
		String msg = req.content
				+req.From.value
				+req.ip_addr
				+req.pk_sender.value.toString()
				+String.valueOf(req.req_type)
				+req.task_id.value
				+String.valueOf(req.trans_type);
		
		return msg;
	}
	
	public static byte[] arraycat(byte[] buf1,byte[] buf2)
	{
		byte[] bufret=null;
		int len1=0;
		int len2=0;
		
		if(buf1!=null)
			len1=buf1.length;
		if(buf2!=null)
			len2=buf2.length;
		if(len1+len2>0)
			bufret=new byte[len1+len2];
		
		if(len1>0)
			System.arraycopy(buf1,0,bufret,0,len1);
		if(len2>0)
			System.arraycopy(buf2,0,bufret,len1,len2);
		return bufret;
	}
	

	
	
    public static String  printByte(byte[] byte_value)
    {
        int len = byte_value.length;
        String str = new String();
        for(int i = 0; i < len; i ++)
        {
            int int_temp =  (int) byte_value[i];
            str += String.valueOf(int_temp);
        }
        System.out.println(str);
        return str;
    }
	
	
	public static int TransferMsg(Req_t req, String ip_input) throws UnknownHostException, IOException
	{
		int res = 0;
		String msg = req.serialize();
		
		
		
		while(true)
		{
			if(Test3.node.broadcast_allowed == 1)
			{
				Test3.node.broadcast_allowed = 0;
				for(int loop = 0; loop < Test3.node.outgoing_list.ip_list.size(); loop ++ )
				{
					if(ip_input == Test3.node.outgoing_list.ip_list.get(loop))
					{
						continue;
					}
					
					else
					{
						Socket socket = new Socket(Test3.node.outgoing_list.ip_list.get(loop), SystemParam.GotGlobalPort());
						if(socket != null)
						{
							ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
							if(out != null)
							{
								out.writeObject(req);
							}
							socket.close();
						}
					}
				}
				Test3.node.broadcast_allowed = 1;
				break;
			}
		}
		return res;
	}
	
	public static int BroadcastMsg(Req_t req) throws UnknownHostException, IOException
	{
		int res = 0;
		String msg = req.serialize();

		System.out.println("Runtime Info: the information in BroadcastMsg\t"+ String.valueOf(req.req_type));
		
/*		try {
			System.out.println("Test in broadcast function\t hash value" );
			BasicTool.printByte(BasicCrypto.MyHash(msg));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		while(true)
		{
			if(Test3.node.broadcast_allowed == 1)
			{
				Test3.node.broadcast_allowed = 0;
				for(int loop = 0; loop < Test3.node.outgoing_list.ip_list.size(); loop ++ )
				{
					/*Socket socket = new Socket(Test3.node.outgoing_list.ip_list.get(loop), SystemParam.GotGlobalPort());*/
					Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(Test3.node.outgoing_list.ip_list.get(loop), SystemParam.GotGlobalPort()), 3000);

					if(socket != null)
					{
/*						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						if(out != null)
						{
							out.writeUTF(msg);
						}
						socket.close();*/
						
						ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						if(out != null)
						{
							out.writeObject(req);
						}
						socket.close();
					}
				}
				Test3.node.broadcast_allowed = 1;
				break;
			}
		}
		
		return res;
	}
	
	public static String str2byte(String str_input)
	{
		String res = new String();
		int len = str_input.length();
		byte[]  byte_res = new byte[len]; 
		
		for(int loop = 0; loop < len; loop ++)
		{
			byte_res[loop] = (byte) str_input.charAt(loop);
			res += String.valueOf(byte_res[loop]);
		}
		
		
		System.out.println("Test in str2bytes\t" + str_input);
		/*System.out.ptrintln("Test in str2bytes\t" + );*/
		System.out.println("Test in str2bytes\t Length 1\t" + str_input.length());
		System.out.println("Test in str2bytes\tLength 2\t" + res.length());
		
		
		return res;
	}
	
	public static String byte2str(String str_input)
	{
		String res = new String();
		int len = str_input.length();
		
		
		for(int loop = 0; loop < len; loop ++)
		{
			byte int_temp = (byte) str_input.charAt(loop);
			
		}
		
		return res;
	}
	
	public static boolean isPrevProcessed(Req_t req) throws IOException, NoSuchAlgorithmException
	{
		boolean res = false;
		byte[] hash_value = BasicCrypto.MyHash(req.serialize());
		
		for(int loop = 0; loop < Test3.node.request_list.size(); loop ++)
		{
			byte[] hash_value2 = BasicCrypto.MyHash(Test3.node.request_list.get(loop).serialize());
			if(BasicTool.compareHash(hash_value, hash_value2))
			{
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	public static boolean isInICList(String ip_addr)
	{
		boolean res = false;
		{
			for(int loop = 0; loop < Test3.node.incoming_list.ip_list.size(); loop ++)
			{
				if(ip_addr == Test3.node.incoming_list.ip_list.get(loop))
				{
					res = true;
					break;
				}
			}
			
			
		}
		return res;
	}
	
	public static boolean isInOGList(String ip_addr)
	{
		boolean res = false;
		{
			for(int loop = 0; loop < Test3.node.outgoing_list.ip_list.size(); loop ++)
			{
				if(ip_addr == Test3.node.outgoing_list.ip_list.get(loop))
				{
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	public static boolean compareHash(byte[] hash1, byte[] hash2)
	{
		boolean res = false;
		if(hash1.length != hash2.length)
		{
			return res;
		}
		
		for(int loop = 0; loop < hash1.length; loop ++)
		{
			if(hash1[loop] != hash2[loop])
			{
				return res;
			}
		}
		
		res = true;
		return res;
	}
	
	
	public static String printID(ID_t id_input)
	{
		String res = new String();
		byte[] byte_res = id_input.value.getBytes();
		int temp = 0;
		for(int loop = 0; loop < id_input.value.length(); loop ++)
		{
			temp = byte_res[loop];
			String str_temp =  String.valueOf(temp);
			res = res + str_temp;		
		}
		
		return res;
	}

}

















