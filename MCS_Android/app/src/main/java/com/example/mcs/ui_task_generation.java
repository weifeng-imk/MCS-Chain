package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;

public class ui_task_generation extends AppCompatActivity {
    //Intent intent1, intent2;
    Task_t task = null;
    int num_sub_task = 0;
    TextView textView2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_generation);
        try{
            task = new Task_t();
        }catch(java.net.UnknownHostException e) {
            Toast.makeText(ui_task_generation.this, "Error", Toast.LENGTH_SHORT);
            finish();
        }

        //定义Task返回内容的默认信息
        task.From = MainActivity.node.node_info.id;
        task.ip_addr=MainActivity.node.ip;
        task.pk_sender = MainActivity.node.node_info.pk;
        task.From = new ID_t();
        task.From.value= MainActivity.node.node_info.id.value;
        task.trans_type = 999;
        task.task_id = new ID_t();
        try{
            task.task_id.value = task.content.serialize()
                    +task.From.value
                    +task.ip_addr
                    +task.pk_sender.value.toString()
                    +String.valueOf(task.req_type)
                    +String.valueOf(task.trans_type);

            task.task_id.value = new String(BasicCrypto.MyHash(task.task_id.value));
        }catch(java.io.IOException e){
            Toast.makeText(ui_task_generation.this, "Error 2", Toast.LENGTH_SHORT);
            finish();
        }catch(NoSuchAlgorithmException e){
            Toast.makeText(ui_task_generation.this, "Error 2", Toast.LENGTH_SHORT);
            finish();
        }
        //设置任务内容的基本信息
        TaskContent_t taskcontent = new TaskContent_t();
        taskcontent.task_des.value = "移动众包任务";
        this.textView2 = (TextView) findViewById(R.id.textView2_in_task_generation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_generation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1_task_generation_menu:
            {
                Intent intent = new Intent(ui_task_generation.this, ui_task_generation_sub.class);
                intent.putExtra("task num", String.valueOf(num_sub_task));
                startActivityForResult(intent, 1);
                break;
            }
            case R.id.item2_task_generation_menu:{
                String string = null;
                try{
                    string = task.serialize();
                }catch(java.io.IOException e){
                    Toast.makeText(ui_task_generation.this, "出错了", Toast.LENGTH_SHORT);
                    Log.println(Log.ERROR, "Error", "java.io.IOException in task generation in menu 2");
                }
                Intent intent = new Intent();
                intent.putExtra("task return", string);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode,  data);
        switch(requestcode){
            case 1:
            {
                SubTask_t subtask = new SubTask_t();
                try{
                    subtask = subtask.deserialize(data.getStringExtra("subtask return"));
                }catch(java.io.IOException e){
                    Toast.makeText(ui_task_generation.this, "出错了", Toast.LENGTH_SHORT);
                    Log.println(Log.ERROR, "Error", "读取出错");
                }catch(java.lang.ClassNotFoundException e){
                    Toast.makeText(ui_task_generation.this, "出错了", Toast.LENGTH_SHORT);
                    Log.println(Log.ERROR, "Error", "出错了");


                }
                this.task.content.subtask_list.add(subtask);
                this.textView2.setText(subtask.subtask_des.value);
                num_sub_task ++;
            }
        }
    }
}
