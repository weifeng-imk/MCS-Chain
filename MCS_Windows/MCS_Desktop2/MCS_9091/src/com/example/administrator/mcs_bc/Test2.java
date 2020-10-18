package com.example.administrator.mcs_bc;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public class Test2
{
	public static void main(String[] args)
	{
		BasicCrypto bs = new BasicCrypto();
		
		Bid_t bid = new Bid_t();
		
		ArrayList<Bid_t> bid_List = new ArrayList<Bid_t>();
		
		bid.req_type = 9988989;
		bid_List.add(new Bid_t(bid));
		
		bid_List.get(0).req_type = 0;
		
		System.out.println(bid.req_type);
		
		
	}
}