package com.example.administrator.mcs_bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class MerkleTree implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int merkle_type;
	Merkle root;
	ArrayList<String> trans_list;
	ArrayList<Merkle> merkle_list;
	int height;
	Merkle last;
	int last_index;
	
	
	public String Serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public MerkleTree Deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        MerkleTree res =(MerkleTree) objIn.readObject();  
        return res;  
	}
	
	
	public MerkleTree()
	{
		merkle_type = -1;
		this.root = new Merkle(2);
		this.height = 2;
		merkle_list = this.TraverseTree(this.root, false);
		this.trans_list = new ArrayList<String>();
	}
	
	public MerkleTree(MerkleTree tree)//only a citation
	{
		this.root = new Merkle(tree.root, 1);
		this.height = tree.height;
		this.trans_list = tree.trans_list;
		this.last = tree.last;
		this.merkle_list = tree.merkle_list;
	}
	
	public String getString()
	{
		String res = new String();
		for(int loop = 0; loop < this.trans_list.size(); loop ++)
		{
			res += this.trans_list.get(loop);
		}
		
		return res;
	}
	
	public MerkleTree(ArrayList<String> trans_input) throws NoSuchAlgorithmException
	{
		
		this.trans_list = new ArrayList<String>();
		this.trans_list.addAll(trans_input);
		
		
		double double_temp1_height =  Math.log((double) this.trans_list.size())/Math.log(2);
		int int_temp1_height = (int) double_temp1_height;
		
		
		if(double_temp1_height - (double) int_temp1_height > 0)
			this.height = int_temp1_height + 2;
		else
			this.height = int_temp1_height + 1;
		
		
		this.root = new Merkle(this.height);
		
		this.merkle_list = this.TraverseTree(this.root);
		
		this.last_index = 0;
		this.last = this.merkle_list.get(this.last_index);
		
	}
	
	public ArrayList<Merkle> TraverseTree(Merkle merkle)
	{
		if(merkle == null)
		{
			return null;
		}
		if(merkle.isleaf == 0)
		{
			ArrayList<Merkle> merkle_list1 = TraverseTree(merkle.left);
			ArrayList<Merkle> merkle_list2 = TraverseTree(merkle.right);
			
			merkle_list1.addAll(merkle_list2);
			return merkle_list1;
		}
		
		else
		{
			ArrayList<Merkle> merkle_list1 = new ArrayList<Merkle>();
			merkle_list1.add(merkle);
			return merkle_list1;
		}
	}
	
	public ArrayList<Merkle> TraverseTree(Merkle merkle, boolean choice)
	{
		//ArrayList<Merkle> merkle_list_res = new ArrayList<Merkle>();
		if(!choice)
		{
			if(merkle.isleaf == 0)
			{
				ArrayList<Merkle> merkle_list1 = TraverseTree(merkle.left, false);
				ArrayList<Merkle> merkle_list2 = TraverseTree(merkle.right, false);
				if(merkle_list1.addAll(merkle_list2) == true)
					return merkle_list1;
				else
					return null;
			}
			
			else
			{
				ArrayList<Merkle> merkle_list1 = new ArrayList<Merkle>();
				merkle_list1.add(merkle);
				return merkle_list1;
			}
		}
			
		else if(this.checkHeight())
		{
			if(merkle.isleaf == 0)
			{
				ArrayList<Merkle> merkle_list1 = TraverseTree(merkle.left);
				ArrayList<Merkle> merkle_list2 = TraverseTree(merkle.right);
				if(merkle_list1.addAll(merkle_list2) == true)
					return merkle_list1;
				else
					return null;
			}
			
			else
			{
				ArrayList<Merkle> merkle_list1 = new ArrayList<Merkle>();
				merkle_list1.add(merkle);
				return merkle_list1;
			}
		}
		else
		{
			return null;
		}
		
//		return merkle_list_res;
	}
	
	public boolean addElement(String str_input) throws NoSuchAlgorithmException
	{
		boolean res = false;
		
		this.trans_list.add(str_input);
		this.last_index ++;
		this.last.hash_value = new String(BasicCrypto.MyHash(str_input));
		this.hashUpdate(this.last);
		this.last = this.merkle_list.get(last_index);
		
		
		
		return res;
	}
	
	public void hashUpdate(Merkle merkle) throws NoSuchAlgorithmException
	{
		if(merkle.father != null)
		{
			if(merkle.father.left == merkle)
			{
				if(merkle.father.right.hash_value == null)
				{
					merkle.father.hash_value = new String(merkle.hash_value);
					hashUpdate(merkle.father);
				}
				else
				{
					String str_temp1_hash = merkle.hash_value + merkle.father.right.hash_value;
					str_temp1_hash = BasicCrypto.MyHash(str_temp1_hash).toString();
					merkle.father.hash_value = str_temp1_hash;
					hashUpdate(merkle.father);
				}
			}
			else
			{
				String str_temp1_hash = merkle.father.left.hash_value + merkle.hash_value;
				str_temp1_hash = BasicCrypto.MyHash(str_temp1_hash).toString();
				merkle.father.hash_value = str_temp1_hash;
				hashUpdate(merkle.father);
			}
		}
			
	}
	
	public Merkle findElement(int index)
	{
		if(index >= (int) Math.pow(2.0,  this.height))
			return null;
		if(this.height == 1)
			return null;
		
		Merkle merkle_temp = this.root;
		for(int loop = 0; loop < this.height -1; loop ++)
		{
			int int_temp1 = (int) Math.pow(2.0,  this.height - 2 - loop);
			if(index >= int_temp1)
				merkle_temp = merkle_temp.right;
			else
				merkle_temp = merkle_temp.right;
		}
		
		return merkle_temp;
	}
	
	public Merkle FindElementbyIndex(int index)
	{
		if (this.checkHeight() == true)
		{
			Merkle merkle = this.root;
			int value = (int)Math.pow(2.0,  (double) this.height - 2);
			for(int loop = 0; loop < this.height - 2; loop ++)
			{
				if(index >= value)
				{
					merkle = merkle.right;
					value = value + (int) Math.pow(2.0,  this.height - 3 -loop);
				}
				else
				{
					merkle = merkle.left;
					value = value - (int)Math.pow(2.0,  this.height -3 - loop);
				}	
					
			}
			
			return merkle;
		}
		else
		{
			return null;
		}
	}
	
	public boolean checkHeight()
	{
		double double_temp1_height = Math.log(this.trans_list.size())/Math.log(2);
		int int_temp1_height = (int) double_temp1_height;
		
		if((double_temp1_height - (double) int_temp1_height) > 0)
		{
			return (this.height == int_temp1_height + 2);
		}
		
		else if(double_temp1_height == (double) int_temp1_height)
		{
			return (this.height == int_temp1_height + 1);
		}
		else
			return false;
	}
}



class Merkle implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String hash_value;
	String info_extra;
	Merkle father;
	Merkle left;
	Merkle right;	
	
	int isleaf;
	
	
	public String Serialize() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);  
        objOut.writeObject(this);  
        String res = byteOut.toString("ISO-8859-1");
        return res;
	}
	
	public Merkle Deserialize(String str_input) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));  
        ObjectInputStream objIn = new ObjectInputStream(byteIn);  
        Merkle res =(Merkle) objIn.readObject();  
        return res;  
	}
	
	public Merkle()
	{
		this.hash_value = null;
		this.father = null;
		this.left = null;
		this.right = null;
		this.isleaf = 0;
		
		this.info_extra = null;
	}
	
	public int FillMerkle(int height, ArrayList<Merkle> merkle_list, int label)
	{
		int res = 0;
		
		return res;
	}
	public Merkle(int height)
	{
		if(height == 1)
		{
			this.father = null;
			this.left = null;
			this.right = null;
			this.isleaf = 1;
			this.hash_value = null;
		}
		
		else
		{
			for(int loop = 0; loop < height -1; loop ++)
			{
				this.hash_value = null;
				this.father = null;
				this.isleaf = 0;
				
				this.left = new Merkle(height -1);
				this.right = new Merkle(height -1);
				
				this.left.father = this;
				this.right.father = this;
			}
		}

	}
	
	
	public Merkle(Merkle merkle, int choice)
	{
		if (choice < 0)
		{
			this.father = merkle.father;
			this.left = merkle.left;
			this.right = merkle.right;
			this.hash_value = merkle.hash_value;
			this.isleaf = merkle.isleaf;
		}
		
		else if(choice > 0)
		{
			this.father = merkle.father;
			this.hash_value = merkle.hash_value;
			this.isleaf = merkle.isleaf;
			
			if(merkle.left != null)
				this.left = new Merkle(merkle.left, 1);
			if(merkle.right != null)
				this.right = new Merkle(merkle.right, 1);
		}
	}


	public void printMerkle()
	{
		System.out.println(this.isleaf);
		if(this.left != null)
			this.left.printMerkle();
		if(this.right != null)
			this.right.printMerkle();
	}
}

