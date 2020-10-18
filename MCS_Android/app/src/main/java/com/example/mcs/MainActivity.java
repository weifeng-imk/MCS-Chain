package com.example.mcs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

import org.w3c.dom.Attr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn6_1;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn101, btn102;

    public static Node_t node;
    public static BasicCrypto basicCrypto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String ip_addr = core_network_tools.getLocalIpAddress(MainActivity.this);
        Log.println(Log.ERROR, "IP Address ", ip_addr);
        Pairing pairing;
        //PairingFactory.getInstance().setUsePBCWhenPossible(true);
        //pairing = PairingFactory.getPairing("assets/a.properties");


        try {
            this.basicCrypto = new BasicCrypto();
            this.node = new Node_t();
            this.node.ip = ip_addr;
        } catch(IOException e)
        {
            Log.println(Log.ERROR, "Error", "IOException during initialization of node");
        }catch(NoSuchAlgorithmException e){
            Log.println(Log.ERROR, "NoAlgorithmException", "IOException during initialization of node");
        }

        String string_ip1 = String.valueOf("192.168.5.150");
        String string_ip2 = String.valueOf("192.168.8.151");

        String string_ip3 = String.valueOf("192.168.8.152");

        String[] ip = {string_ip1, string_ip2, string_ip3};
        for(int loop = 0; loop < 3; loop ++){
            if(!node.ip.equals(ip[loop])){
                node.outgoing_list.ip_list.add(ip[loop]);
                node.outgoing_list.id_list.add(new ID_t());
                //node.outgoing_list.id_list.get(loop).value=new String("test");
            }
        }

        this.btn101 = (Button)findViewById(R.id.btn102_in_main);
        this.btn102 = (Button)findViewById(R.id.btn101_in_main);

        if(node.ip.equals(ip[2])){
            btn101.setEnabled(true);
            btn102.setEnabled(true);
        }else{
            btn101.setEnabled(false);
            btn102.setEnabled(false);
        }

        //初始化变量


        //NodeProfile_t node = null;

        TextView viewll = (TextView) findViewById(R.id.text0_in_main);
        this.btn1 = (Button) findViewById(R.id.btn1_in_main);
        this.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_personal_information.class);
                startActivity(intent);
            }
        });

        this.btn2 = (Button) findViewById(R.id.btn2_in_main);
        this.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_node_information.class);
                startActivity(intent);
            }
        });

        this.btn3 = (Button)findViewById(R.id.btn3_in_main);
        this.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_block_curr.class);
                startActivity(intent);
            }
        });

        this.btn4 = (Button) findViewById(R.id.btn4_in_main);
        this.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_block_prev.class);
                startActivity(intent);
            }
        });

        //生成任务
        this.btn5 = (Button) findViewById(R.id.btn5_in_main);
       this.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_task_generation.class);
                startActivityForResult(intent, 5);
            }
        });

       this.btn6=(Button) findViewById(R.id.btn6_in_main);
       this.btn6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ui_task_selection.class);
                startActivityForResult(intent, 6);
            }
       });

       this.btn6_1 = (Button) findViewById(R.id.btn6_1_in_main);
       this.btn6_1.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               Intent intent = new Intent(MainActivity.this, ui_worker_selection.class);
               startActivityForResult(intent, 61);
            }
        });

        btn7 = (Button)findViewById(R.id.btn7_in_main);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_task_finish.class);
                startActivityForResult(intent, 7);
            }
        });

        btn8 = (Button)findViewById(R.id.btn8_in_main);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_execution_preview.class);
                startActivity(intent);
            }
        });

        btn9 = (Button) findViewById(R.id.btn9_in_main);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ui_feedback_generation1.class);
                startActivityForResult(intent, 9);
            }
        });



        btn101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Req_t req = null;
                try {
                    req = new Req_t();
                } catch (java.net.UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    //e1.printStackTrace();
                }
                req.req_type = 999;
                req.trans_type = 999;
                req.task_id = null;
                req.From = node.node_info.id;
                //req.content = this.ip;
                //System.out.print("check ip\t" + this.ip);
                req.pk_sender = node.node_info.pk;
                req.ip_addr = node.ip;

                Random r = new Random();
                int temp = r.nextInt(999);
                temp = temp * 999 +  temp;
                req.content = String.valueOf(temp);
                node.just_for_test.add(temp);

                try {
                    req.Sig_from = BasicCrypto.MySignature1(req.content, node.node_info.sk, node.node_info.pk);
                } catch (NoSuchAlgorithmException e1) {
                    System.out.println("Error");
                    e1.printStackTrace();
                }


                try {
                    BasicTool.BroadcastMsg(req);
                } catch (java.net.UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


            }
        });

        btn102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Req_t req = null;
                try {
                    req = new Req_t();
                } catch (java.net.UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                req.req_type = 999;
                req.trans_type = 999;
                req.task_id = null;
                req.From = node.node_info.id;
                //req.content = this.ip;
                //System.out.print("check ip\t" + this.ip);
                req.pk_sender = node.node_info.pk;
                req.ip_addr = node.ip;

                Random r = new Random();
                int temp = r.nextInt(999);
                temp = temp * 1000 + temp;
                req.content = String.valueOf(temp);

                try {
                    req.Sig_from = BasicCrypto.MySignature1(req.content, node.node_info.sk, node.node_info.pk);
                } catch (NoSuchAlgorithmException e1) {
                    System.out.println("Error");
                    e1.printStackTrace();
                }
                try {
                    BasicTool.BroadcastMsg(req);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        /*CommProcess pro = new CommProcess();
        Thread thread_compro = new Thread(pro);
        thread_compro.start();*/

        Thread thread3 = new Thread(node);
        thread3.start();

        ListeningProcess listening_process = new ListeningProcess();
        Thread thread_listening = new Thread(listening_process);
        thread_listening.start();
        MiningProcess mining_process = new MiningProcess();
        Thread thread_mining = new Thread(mining_process);
        thread_mining.start();
    }

    public void save(String path, String data, int mode)
    {
        FileOutputStream out = null;
        BufferedWriter writer = null;

        try {
            out = openFileOutput(path, mode);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String load(String path)
    {
        FileInputStream in = null;
        BufferedReader reader = null;

        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput(path);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null)
            {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 5:{
                Task_t task = null;
                try{
                    task = new Task_t();
                }catch(java.net.UnknownHostException e){
                    Log.println(Log.ERROR, "Error", "UnknownHost Exception in onActivityResult in case 5 in MainActivity");
                }
                String string;
                string = data.getStringExtra("task return").toString();
                try{
                    task = task.deserialize(string);
                }catch(java.io.IOException e){
                    Log.println(Log.ERROR, "Error", "IOException in onActivityResult in case 5 in MainActivity");
                }catch(java.lang.ClassNotFoundException e){
                    Log.println(Log.ERROR, "Error", "java.lang.ClassNotFoundException in onActivityResult in case 5 in MainActivity");
                }
               // node.task_issued.add(task);
                node.task_list_curr.add(task);
                node.my_task.add(task);
                node.task_location.add(node.task_list_curr.size() - 1);
                node.task_record.add(0);

                ArrayList<Bid_t> bid_list = new ArrayList<Bid_t>();
                ArrayList<Data_t> data_list = new ArrayList<Data_t>();
                ArrayList<Integer> bid_record_list = new ArrayList<Integer>();
                ArrayList<ID_t> worker_selected_input = new ArrayList<ID_t>();
                ArrayList<Integer> worker_selected_record_input = new ArrayList<Integer>();
                ArrayList<FB_t> feedback_list = new ArrayList<FB_t>();
                ArrayList<Integer> feedback_list_record = new ArrayList<Integer>();
                ArrayList<Integer> worker_selected_sub_input = new ArrayList<Integer>();
                ArrayList<Integer> worker_selected_sub_record_input = new ArrayList<Integer>();
                ArrayList<Integer> data_hash_list_record_input = new ArrayList<Integer>();


                node.bid_list.add(bid_list);
                node.data_hash_list.add(data_list);
                node.bid_list_record.add(bid_record_list);
                node.worker_selected.add(worker_selected_input);
                node.worker_selected_record.add(worker_selected_record_input);
                node.feedback_list_collected.add(feedback_list) ;
                node.feedback_list_collected_record.add(feedback_list_record);
                node.worker_selected_sub.add(worker_selected_sub_input);
                node.worker_selected_sub_record.add(worker_selected_sub_record_input);
                node.worker_selected_bid.add(new ArrayList<String>());
                node.data_hash_list_record.add(data_hash_list_record_input);
                Req_t req = null;
                try{
                    req = new Req_t(task);
                }catch(java.io.IOException e){
                    Log.println(Log.ERROR, "Error 2", "IOException in onActivityResult in case 5 in MainActivity");
                }
                String msg  =BasicTool.preSign(req);
                try{
                    req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);
                }catch(java.security.NoSuchAlgorithmException e){
                    Log.println(Log.ERROR, "Error 3", "IOException in onActivityResult in case 5 in MainActivity");
                }
                try{
                    BasicTool.BroadcastMsg(req);
                }catch(java.net.UnknownHostException e){
                    Log.println(Log.ERROR, "Exception 1", "xxx in xxx function in xxx file");
                }catch(java.io.IOException e){
                    Log.println(Log.ERROR, "Exception 2", "Java.io.IOException  in case 5 function in MainActivity file");
                }
                break;
            }
            case 6:{
                String string = null;
                if(resultCode == RESULT_OK){
                    string = data.getStringExtra("task id");
                    int taskid = Integer.valueOf(string);
                    string = data.getStringExtra("subtask id");
                    int subtaskid = Integer.valueOf(string);

                    Bid_t bid = new Bid_t();
                    ArrayList<Attr_t> attr_list = new ArrayList<Attr_t>();
                    Attr_t attr = new Attr_t();
                    attr.value = 1;
                    attr_list.add(attr);
                    if(taskid > MainActivity.node.task_list_curr.size()){
                        return;
                    }
                    if(subtaskid > MainActivity.node.task_list_curr.get(taskid).content.subtask_list.size()){
                        return;
                    }
                    bid.task_id = MainActivity.node.task_list_curr.get(taskid).task_id;
                    bid.From = node.node_info.id;
                    bid.pk_sender = node.node_info.pk;
                    bid.trans_type = 99999;
                    bid.content.pay_expected.value = 10.0;
                    bid.content.sub_id.id = subtaskid;
                    bid.content.attr = new String();
                    for(int loop = 0; loop < attr_list.size(); loop ++)
                    {
                        bid.content.attr = "移动节点";
                    }
                    UIPF uipf = new UIPF();
                    try{
                        uipf.BidGeneration(MainActivity.node, bid);
                    }catch(java.net.UnknownHostException e){
                        return;
                    }catch(java.security.NoSuchAlgorithmException e) {
                        return;
                    }catch(java.io.IOException e){
                        return;
                    }

                }

                break;
            }
            case 61:{
                String string = data.getStringExtra("bid id");
                int bid_id = Integer.valueOf(string);
                int taskid = data.getIntExtra("task id", -1);
                int subtaskid = data.getIntExtra("subtask id", -1);

                MainActivity.node.worker_selected.get(taskid).add(node.bid_list.get(taskid).get(bid_id).From);
                node.worker_selected_record.get(taskid).add(0);
                node.worker_selected_sub.get(taskid).add(node.bid_list.get(taskid).get(bid_id).content.sub_id.id);
                String str_hash = null;
                try {
                    str_hash = new String(BasicCrypto.MyHash(node.bid_list.get(taskid).get(bid_id).serialize()));
                } catch (NoSuchAlgorithmException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                node.worker_selected_bid.get(taskid).add(str_hash);

                String content = null;
                WorkerSelectRes worker_list = new WorkerSelectRes();
                worker_list.worker_list_sub = node.worker_selected_sub.get(taskid);
                worker_list.worker_list = node.worker_selected.get(taskid);
                worker_list.bid_hash_list = node.worker_selected_bid.get(taskid);
                try {
                    content = worker_list.serialize();
                    System.out.println(content);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Req_t req = null;
                try {
                    req = new Req_t();
                } catch (java.net.UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                req.content = content;
                req.From = node.node_info.id;
                req.ip_addr = node.ip;
                req.req_type = 5;
                req.task_id.value = node.task_list_curr.get(taskid).task_id.value;
                req.trans_type = 999;
                req.pk_sender = node.task_list_curr.get(taskid).pk_sender;

                String msg = BasicTool.preSign(req);

                try {
                    req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    BasicTool.BroadcastMsg(req);
                    //frame2.dispose();
                    //WarningWindow ww = new WarningWindow("Info", "Worker Selection Finished");
                    //return;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
            case 7:{
                int taskid = data.getIntExtra("task id", -1);
                int subtaskid = data.getIntExtra("subtask id", -1);
                int imageid = data.getIntExtra("image id", -1);
                Data_t data_upload = new Data_t();


                data_upload.content.data.add(String.valueOf(imageid).getBytes());
                data_upload.content.data_hash.add(String.valueOf(imageid).getBytes());
                data_upload.content.len_list.add(1);

                data_upload.From = node.node_info.id;
                data_upload.req_type = 6;
                data_upload.pk_sender = node.node_info.pk;
                //data_upload.task_id.value = text_area.getText();
                data_upload.trans_type = 3;
                data_upload.task_id.value = node.task_list_curr.get(taskid).task_id.value;
                data_upload.sub_id = node.task_list_curr.get(taskid).content.subtask_list.get(subtaskid).subtask_no;

                node.data_hash_list.get(taskid).add(data_upload);

                Req_t req = null;
                try{
                    req = new Req_t(data_upload);
                }catch(java.io.IOException e){
                    return;
                }
                String msg = BasicTool.preSign(req);
                try{
                    req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);

                }catch(java.security.NoSuchAlgorithmException e){
                    return;
                }
                try{
                    msg = req.serialize();
                }catch(java.io.IOException e){
                    return;
                }




               /* Socket socket =  new Socket();
                socket.connect(new InetSocketAddress(.node.task_list_curr.get(taskid).ip_addr, SystemParam.GotGlobalPort()), 3000);

                if(socket!= null)
                {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    if(out != null)
                    {
                        out.writeObject(req);
                    }
                }

                //for test
                try {
                    Data_t Data_test = new Data_t(req);
                    File file_test = new File("files\\test"+ ".jpg");
                    file_test.createNewFile();
                    FileOutputStream fos_test = new FileOutputStream(file_test);
                    fos_test.write(Data_test.content.data_upload.get(0));
                    System.out.println("Testing: writing files\t len: \t"+ data_upload.content.len_list.get(0)+"\t"+data_upload.content.data_upload.get(0).length);


                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //for test


                read_stream.close();
                fos.flush();
                fos.close();*/

               /*data_upload .content.data = new ArrayList<byte[]>();
                data_upload.trans_type = 1;
                try{
                    req = new Req_t(data_upload);
                }catch(java.io.IOException e){
                    return;
                }*/
                msg = BasicTool.preSign(req);
                try{
                    req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);

                }catch(java.security.NoSuchAlgorithmException e){
                    return;
                }
                try{
                    BasicTool.BroadcastMsg(req);
                }catch(java.io.IOException e){
                    return;
                }
                break;
            }
            case 9:{
                int bidid = data.getIntExtra("bid id", -1);
                double  feedback = data.getDoubleExtra("feedback", -1.0);
                int taskid  = data.getIntExtra("task id", -1);

                if(bidid == 0){
                    FBD_t fbd = new FBD_t();
                    fbd.evaluatee = node.task_list_curr.get(taskid).From;
                    fbd.value = feedback;
                    fbd.bid_hash = "task publisher";

                    FB_t fb_temp = new FB_t();
                    fb_temp.From.value = node.node_info.id.value;
                    fb_temp.pk_sender.value.set(node.node_info.pk.value);
                    fb_temp.req_type = 7;
                    fb_temp.trans_type = 999;
                    fb_temp.task_id = node.task_list_curr.get(taskid).task_id;
                    fb_temp.content = fbd;
                    Req_t req = null;
                    try{
                        req = new Req_t(fb_temp);
                    }catch(java.io.IOException e){
                        Log.println(Log.ERROR, "利用fb生成req出错", "就是酱紫");
                    }
                    String msg = new String();
                    msg = BasicTool.preSign(req);
                    try{
                        req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);
                    }catch(NoSuchAlgorithmException e){
                        return;
                    }
                    while(true){
                        if(node.fb_read_allowed == 1){
                            node.fb_read_allowed = 0;
                            node.feedback_list_collected.get(taskid).add(fb_temp);
                            node.feedback_list_collected_record.get(taskid).add(0);
                            node.fb_read_allowed = 1;
                            break;
                        }
                    }
                    try{
                        BasicTool.BroadcastMsg(req);
                    }catch(java.io.IOException e){
                        return;
                    }
                }
                else{
                    bidid = bidid - 1;
                    FBD_t fbd = new FBD_t();
                    fbd.evaluatee = node.task_list_curr.get(taskid).From;
                    fbd.value = feedback;
                    try{
                        fbd.bid_hash = node.bid_list.get(taskid).get(bidid).serialize();
                    }catch(java.io.IOException e){
                        return;
                    }
                    FB_t fb_temp = new FB_t();
                    fb_temp.From.value = node.node_info.id.value;
                    fb_temp.pk_sender.value.set(node.node_info.pk.value);
                    fb_temp.req_type = 7;
                    fb_temp.trans_type = 999;
                    fb_temp.task_id = node.task_list_curr.get(taskid).task_id;
                    fb_temp.content = fbd;
                    Req_t req = null;
                    try{
                        req = new Req_t(fb_temp);
                    }catch(java.io.IOException e){
                        Log.println(Log.ERROR, "利用fb生成req出错", "就是酱紫");
                    }
                    String msg = new String();
                    msg = BasicTool.preSign(req);
                    try{
                        req.Sig_from = BasicCrypto.MySignature1(msg, node.node_info.sk, node.node_info.pk);
                    }catch(NoSuchAlgorithmException e){
                        return;
                    }
                    while(true){
                        if(node.fb_read_allowed == 1){
                            node.fb_read_allowed = 0;
                            node.feedback_list_collected.get(taskid).add(fb_temp);
                            node.feedback_list_collected_record.get(taskid).add(0);
                            node.fb_read_allowed = 1;
                            break;
                        }
                    }
                    try{
                        BasicTool.BroadcastMsg(req);
                    }catch(java.io.IOException e){
                        return;
                    }
                }
            }
        }
    }


}
