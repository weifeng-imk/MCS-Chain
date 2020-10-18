package com.example.mcs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

//Data Type 1
class ID_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*-2701132667208160108L*/1L;
    public String value;

    public ID_t()
    {
        this.value = new String();
    }

    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public ID_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        ID_t res =(ID_t) objIn.readObject();
        return res;
    }
}

class WorkerSelectRes implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L/*7628309539148852331L*/;
    public ArrayList<ID_t> worker_list;
    public ArrayList<Integer> worker_list_sub;
    public ArrayList<Double> payment_list;
    public ArrayList<String> bid_hash_list;

    public WorkerSelectRes()
    {
        this.worker_list = new ArrayList<ID_t>();
        this.worker_list_sub = new ArrayList<Integer>();
        this.payment_list = new ArrayList<Double>();
        this.bid_hash_list = new ArrayList<String>();
    }
    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public WorkerSelectRes deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        WorkerSelectRes res =(WorkerSelectRes) objIn.readObject();
        return res;
    }
}

class PK_t  implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*1528979184671053497L*/1L;
    public transient Element value;

    public PK_t()
    {
        this.value = BasicCrypto.G2.newElement();
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();

        if(this.value == null)
            this.value = BasicCrypto.G2.newElement();

        byte[] str2 = this.value.toBytes();

        out.writeObject(str2);
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        //invoke default serialization method
        in.defaultReadObject();


        byte[] str2 = (byte[])in.readObject();
        this.value = BasicCrypto.G1.newElement();

        this.value.setFromBytes(str2);
    }



    public PK_t(Element P, SK_t sk_input)
    {
        this.value.set(P);
        this.value.powZn(sk_input.value);
    }

    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public PK_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        PK_t res =(PK_t) objIn.readObject();
        return res;
    }
}

class SK_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*4878039769420589901L;*/1L;
    public Element value;

    public SK_t()
    {
        this.value = BasicCrypto.Zn.newRandomElement();
    }

    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public SK_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        SK_t res =(SK_t) objIn.readObject();
        return res;
    }

}

class Sig_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*6296489857804803990L;*/1L;
    public transient Element value;

    public Sig_t()
    {
        this.value = BasicCrypto.G1.newElement();
    }

    public void setValue(PK_t pk, SK_t sk, String msg) throws NoSuchAlgorithmException
    {
        //byte[] byte_tem1_hash = BasicCrypto.MyHash(msg);
        this.value.set(BasicCrypto.MySignature1(msg, sk,  pk).value);
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();

        if(this.value == null)
            this.value = BasicCrypto.G1.newElement();

        byte[] str2 = this.value.toBytes();

        out.writeObject(str2);
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        //invoke default serialization method
        in.defaultReadObject();

        byte[] str2 = (byte[])in.readObject();
        this.value = BasicCrypto.G1.newElement();

        this.value.setFromBytes(str2);
    }

    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public Sig_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);

        Sig_t res =(Sig_t) objIn.readObject();
        return res;
    }
}

class Time_t implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = /*7066380237263508024L;*/1L;
    double value;


    public String serialize() throws IOException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        String res = byteOut.toString("ISO-8859-1");
        return res;
    }

    public Time_t deserialize(String str_input) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str_input.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        Time_t res =(Time_t) objIn.readObject();
        return res;
    }
}