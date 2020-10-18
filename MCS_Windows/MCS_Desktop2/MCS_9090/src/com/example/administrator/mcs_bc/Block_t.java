package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class Block_t implements Serializable
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//basic Information
	public int no; //block no. 
	public ID_t id;//block id
	public TV_t tv;//trust value of the generator of the block
	public Time_t time;//the time when the block is found;
	public String hash_prev;
	public int num_block_obtain;
	
	
	public Sig_t sig;// the signature of the block generator
	public PK_t pk; // the public key of the block generator
	
	//Key Information
	//MerkleTree merkle_tree; // transaction information is included;
	MerkleTree merkle_task;
	MerkleTree merkle_bid;
	
	MerkleTree merkle_worker;
	MerkleTree merkle_data;
	MerkleTree merkle_feedback;
	MerkleTree merkle_state;
	
	//Block Initialization
	//initialize an empty block
	public Block_t()
	{
		this.id = new ID_t();
		this.tv = new TV_t();
		this.time = new Time_t();
		this.hash_prev = new String();
		this.sig = new Sig_t();
		this.pk = new PK_t();
		
		this.merkle_task = new MerkleTree();
		this.merkle_bid = new MerkleTree();
		this.merkle_worker = new MerkleTree();
		this.merkle_data =new MerkleTree();
		this.merkle_feedback = new MerkleTree();
		this.merkle_state = new MerkleTree();
	}	
	
	public String serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Block_t deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Block_t res = new Block_t(); 
        res = (Block_t) objIn.readObject();  
        return res;  
	}
}

//Return a task by its id

/*public Task_t  GetTask(ID_t id_request, ArrayList<Task_t> task_list_input)
{
	//Task_t res = new Task_t();
	
	for(int loop = 0; loop < task_list_input.size(); loop ++)
	{
		if(id_request == task_list_input.get(loop).task_id)
		{
			return task_list_input.get(loop);
		}
	}
	return null;
}*/

