package com.example.administrator.mcs_bc;
class Trans_t 
{
	public ID_t task_id;
	public int trans_type;
	
	public ID_t From;
	public PK_t pk_sender;
	public Sig_t sig;
	
	
	public ID_t To;
	public PK_t pk_receiver;
	public Sig_t sig_receiver;
	
	public String content;
	
	
}

