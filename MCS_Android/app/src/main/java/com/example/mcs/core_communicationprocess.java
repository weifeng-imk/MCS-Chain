package com.example.mcs;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class CommProcess implements Runnable
{

    @Override
    public  synchronized void run()
    {

        //case 10: requesting another miner as a incoming node;
        //case 11: requesting another miner as a outgoing node;
        while(true)
        {

            if((MainActivity.node.state_broadcast_ic == 0 	&& MainActivity.node.incoming_list.id_list.size() < SystemParam.getICThres())
                    ||(MainActivity.node.state_broadcast_og == 0 && MainActivity.node.outgoing_list.id_list.size() < SystemParam.getOGThres()))
            {

                System.out.println(MainActivity.node.waid ++ + "\tTrying to recruting communication nodes");
                MainActivity.node.process_case10_gen();
                MainActivity.node.process_case11_gen();

                /*try {
                    Thread.sleep(300000);;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }
        }



    }

}

class ListeningProcess implements Runnable
{

    @Override
    public void run()
    {
        if(MainActivity.node.state_listening == 0)
        {
            System.out.println(MainActivity.node.waid ++ + "\tTrying for listening");
            MainActivity.node.state_listening = 1;

            while(true)
            {

                if(MainActivity.node.my_socket == null)
                {
                    while(MainActivity.node.my_socket == null)
                    {
                        try {
                            MainActivity.node.my_socket = new ServerSocket(9090);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception");
                            MainActivity.node.state_listening = 0;
                        }
                    }
                }

                MainActivity.node.state_listening = 2;

                Socket socket = null;
                try {
                    socket = MainActivity.node.my_socket.accept();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Exception");
                    e.printStackTrace();

                    MainActivity.node.state_listening = 0;
                }

                if(socket != null)
                {
                    ObjectInputStream in = null;
                    try {
                        in = new ObjectInputStream(socket.getInputStream());
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    String str = null;


                    if(in != null)
                    {

                        Req_t req = null;

                        try {
                            req = new Req_t();
                        } catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            req = (Req_t) in.readObject();
                        } catch (ClassNotFoundException | IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            if(!BasicTool.isPrevProcessed(req))
                            {
                                if(!req.From.value.equals(MainActivity.node.node_info.id.value))
                                {
                                    while(true)
                                    {
                                        if(MainActivity.node.read_allowed == 1)
                                        {
                                            MainActivity.node.read_allowed = 0;
                                            MainActivity.node.request_list.add(req);
                                            MainActivity.node.index_list.add(0);

                                            System.out.println("Received a request, request case is " + req.req_type + ", Total Request number is " + MainActivity.node.request_list.size());
                                            MainActivity.node.read_allowed = 1;
                                            break;
                                        }

                                        if(req.req_type == 6 && req.trans_type == 3)
                                        {
                                            socket.close();
                                            continue;
                                        }
                                        BasicTool.TransferMsg(req, req.ip_addr);
                                    }
                                }
                                else
                                {
                                    System.out.println("Repeated Message");
                                }


                            }
                        } catch (NoSuchAlgorithmException | IOException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                    }

                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        MainActivity.node.state_listening = 0;
                    }
                }
            }
        }
    }

}

class MiningProcess implements Runnable
{

    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        System.out.println("Mining Started");
        while(true)
        {

            try {
                MainActivity.node.block_select();
            } catch (ClassNotFoundException | NoSuchAlgorithmException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}