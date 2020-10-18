package com.example.administrator.mcs_bc;
import java.net.Socket;
import java.util.ArrayList;

class CommunicationList_t
{
	public ArrayList<Socket> socket_list;
	public ArrayList<String> ip_list;
	public ArrayList<ID_t> id_list;
	public ArrayList<Integer> times;
	
	public CommunicationList_t()
	{
		this.socket_list = new ArrayList<Socket>();
		this.ip_list = new ArrayList<String>();
		this.id_list = new ArrayList<ID_t>();
		this.times = new ArrayList<Integer>();
	}
}
