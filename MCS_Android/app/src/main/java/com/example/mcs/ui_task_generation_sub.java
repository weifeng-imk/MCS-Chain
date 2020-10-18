package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ui_task_generation_sub extends AppCompatActivity {
    SubTask_t subtask;
    EditText editText1, editText2, editText3;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_generation_sub);
        this.editText1 = (EditText) findViewById(R.id.editText1_task_generation_sub);
        this.editText2 = (EditText) findViewById(R.id.editText2_task_generation_sub);
        this.editText3 = (EditText) findViewById(R.id.editText3_task_generation_sub);
        this.button = (Button) findViewById(R.id.button1_task_generation_sub);
        this.subtask = new SubTask_t();

        //读取传输过来的数据
        Intent intent = getIntent();
        String data = intent.getStringExtra("task num");
        int num = Integer.valueOf(data);
        this.subtask.subtask_no = num;


        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = editText1.getText().toString();
                String string2 = editText2.getText().toString();
                String string3 = editText3.getText().toString();

                if(string1.length() == 0||string2.length() == 0||string3.length() == 0){
                    Toast.makeText(ui_task_generation_sub.this, "请输入全部信息", Toast.LENGTH_SHORT);
                    return;
                }
                else{
                    subtask.pay_budget.value = Double.valueOf(string2);
                    subtask.worker_num = Integer.valueOf(string1);
                    subtask.subtask_des.value = string3;
                    subtask.requirements = "移动节点";

                    String res  = null;
                    try{
                        res = subtask.serialize();
                    }catch(java.io.IOException e){
                        Toast.makeText(ui_task_generation_sub.this, "返回时出错", Toast.LENGTH_SHORT);
                        Log.println(Log.ERROR, "Error", "java io exception");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("subtask return", res);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }


}
