package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ui_task_selection extends AppCompatActivity {
    Button button;
    TextView textView3;
    EditText editText1, editText2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_selection);
        button = (Button)findViewById(R.id.btn1_task_selection);
        textView3 = (TextView) findViewById(R.id.textView3_task_selection);
        editText1 = (EditText) findViewById(R.id.editText1_task_selection);
        editText2 = (EditText) findViewById(R.id.editText2_task_selection);

        if(MainActivity.node.task_list_curr.size() == 0) {
            textView3.setText("暂时没有任务发布");
        }else{
            textView3.setText("");
            for(int loop = 0; loop < MainActivity.node.task_list_curr.size(); loop ++){
                textView3.append("任务编号:\t"+ String.valueOf(loop)+"\n");
                textView3.append(MainActivity.node.task_list_curr.get(loop).content.task_des.value + "\n");
                for(int loop1 = 0; loop1 < MainActivity.node.task_list_curr.get(loop).content.subtask_list.size(); loop1 ++){
                    textView3.append("子任务编号\t"+String.valueOf(loop) + "."+String.valueOf(loop1) + "\n");
                    textView3.append(MainActivity.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_des.value + "\n");
                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = editText1.getText().toString();
                String string2 = editText2.getText().toString();
                if (string1.length() == 0 || string2.length() == 0) {
                    Log.println(Log.ERROR, "Info", "No input in this function in ui_task_selection.java");
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("task id", string1);
                    intent.putExtra("subtask id", string2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


}
