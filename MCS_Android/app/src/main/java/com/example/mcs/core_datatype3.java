package com.example.mcs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class Data_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*3794403742515000260L;*/1L;
    public int trans_type = 3;
    public ID_t task_id;
    public int sub_id;
    public Sig_t SigFrom;
    public ID_t From;
    public PK_t pk_sender;
    public int req_type = 6;

    public DataContent_t content;


    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public Data_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        Data_t res =(Data_t) objIn.readObject();
        return res;
    }


    public Data_t()
    {
        this. trans_type = 3;
        this.task_id = new ID_t();
        this.content = new DataContent_t();
        this.SigFrom = new Sig_t();
        this.From = new ID_t();
        this.pk_sender = new PK_t();
        this.sub_id = 0;
    }

    public void set (Data_t data_input)
    {
        this.trans_type = data_input.trans_type;
        this.task_id = data_input.task_id;
        this.SigFrom = data_input.SigFrom;
        this.From = data_input.From;
        this.pk_sender = data_input.pk_sender;
        this.content = data_input.content;
    }

    public Data_t (Req_t req_input) throws ClassNotFoundException, IOException
    {
        if(req_input.trans_type != TransType.data_type)
            System.out.println("Wrong");
        else
        {
            this.trans_type = req_input.trans_type;
            this.task_id = req_input.task_id;
            this.SigFrom = req_input.Sig_from;
            this.From = req_input.From;
            this.pk_sender = req_input.pk_sender;
            this.req_type = req_input.req_type;

            this.content = new DataContent_t();
            this.content = this.content.deserialize(req_input.content);
        }
    }
}

class DataContent_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*2988524427848254461L;*/1L;
    ArrayList<byte[]> data;
    ArrayList<byte[]> data_hash;

    ArrayList<Integer> len_list;
    String bid_hash;

    public DataContent_t()
    {
        this.len_list = new ArrayList<Integer>();
        this.data_hash = new ArrayList<byte[]>();
        this.data = new ArrayList<byte[]>();
        this.bid_hash = new String();

        //this.list_subtask = new ArrayList<ID_t>();
    }

    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public DataContent_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        DataContent_t res =(DataContent_t) objIn.readObject();
        return res;
    }

}