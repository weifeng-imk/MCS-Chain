package com.example.mcs;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.security.*;

import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;



class BasicCrypto
{
    public static int HashSize = 32;

    public static Pairing pairing;
    @SuppressWarnings("rawtypes")
    public static Field G1, G2, GT, Zn;
    public static Element P;

    public void outputParam()
    {
        System.out.println(HashSize);
        System.out.println(pairing.toString());
        System.out.println(G1.toString());
        System.out.println(G2.toString());
        System.out.println(GT.toString());
        System.out.println(Zn.toString());
        System.out.println(P.toString());

    }

    public BasicCrypto()
    {

        pairing = PairingFactory.getPairing("assets/a.properties");
        PairingFactory.getInstance().setUsePBCWhenPossible(true);

        G1 = pairing.getG1();
        G2 = pairing.getG2();
        GT = pairing.getGT();
        Zn = pairing.getZr();

        /*P = G2.newRandomElement();*/

        byte[] byte_value2 = {48,2,-119,-41,-58,-95,-69,66,-114,-101,49,-119,
                -39,18,113,-40,-121,55,-53,124,35,98,94,79,49,59,-60,116,60,32,
                -118,-43,108,59,109,-62,-123,-33,29,63,83,114,95,38,122,-113,19,
                -58,98,40,-70,-97,-16,29,3,115,-33,-72,-109,-73,45,-93,-62,-32,27,
                -5,-106,23,-49,124,3,23,113,61,-25,-16,77,91,16,31,16,23,76,-122,108,
                113,-104,25,40,-116,98,-30,0,-46,55,28,-97,-47,113,123,-12,49,-83,-106,
                85,-8,32,64,52,78,-53,-65,-26,-85,11,77,-89,70,-2,-116,-62,22,98,126,8,
                -34,-64,86};

        P = G2.newElement();
        P.setFromBytes(byte_value2);


        pairing.toString();



    }

    public static byte[] MyHash(String msg) throws NoSuchAlgorithmException
    {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] msgbyte = msg.getBytes();
        sha.update(msgbyte);
        byte[] msgdigest = sha.digest();
        return msgdigest;
    }

    public static Sig_t MySignature1(String msg, SK_t sk, PK_t pk) throws NoSuchAlgorithmException
    {


        Sig_t res = new Sig_t();
        Element temp = G1.newElement();

        byte[] msghash = MyHash(msg);

        temp.setFromHash(msghash,  0,  HashSize);
        res.value.set(temp);
        res.value.powZn(sk.value);
        return res;
    }

    public static boolean SignatureVerify1(String msg, PK_t pk, Sig_t sig) throws NoSuchAlgorithmException
    {


        boolean res;

        Element temp1 = GT.newElement();
        Element temp2 = GT.newElement();

        Element temp3msg = G1.newElement();

        byte[] msghash = MyHash(msg);
        temp3msg.setFromHash(msghash, 0, HashSize);

        temp1 = pairing.pairing(temp3msg,  pk.value);
        temp2 = pairing.pairing(sig.value, P);




        res = temp1.isEqual(temp2);

/*		System.out.println("test in verification function: msg size" + msg.length());
		System.out.println("test in verification function: msg" + msg);
		System.out.println("test in verification function: P" + String.valueOf(BasicCrypto.P));
		System.out.println("test in verification function: PK" + String.valueOf(pk.value));
		System.out.println("test in verification function: Sig" + String.valueOf(sig.value));
		System.out.println("test in verification function: Hash" + String.valueOf(temp3msg));
		System.out.println("test in verification function: Temp one " + String.valueOf(temp1));
		System.out.println("test in verification function: Temp two" + String.valueOf(temp2));*/



/*		byte[] bytetemp = msg.getBytes();
		System.out.println("Byte size" + bytetemp.length);
		for(int loop = 0; loop < msg.length(); loop ++)
		{
			if(loop %100 == 0)
			{
				System.out.println();
				System.out.println(loop);
			}

			int a = (int) msg.charAt(loop);
			System.out.println( a );

		}*/

        return res;
    }
}