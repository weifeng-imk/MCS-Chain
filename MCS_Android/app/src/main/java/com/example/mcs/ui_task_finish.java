package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ui_task_finish extends AppCompatActivity {

    private Button btn2;
    private EditText editText1, editText2;
    private TextView textView3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_selection);
        btn2 = (Button) findViewById(R.id.btn1_task_selection);
        editText1 = (EditText)findViewById(R.id.editText1_task_selection);
        editText2 = (EditText)findViewById(R.id.editText2_task_selection);
        textView3 = (TextView)findViewById(R.id.textView3_task_selection);

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

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ui_task_finish.this, ui_task_finish_sub.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String string1 = editText1.getText().toString();
        String string2 = editText2.getText().toString();

        int taskid = Integer.valueOf(string1);
        int subtaskid =  Integer.valueOf(string2);

        Intent intent = new Intent();
        intent.putExtra("task id", taskid);
        intent.putExtra("subtask id", subtaskid);
        intent.putExtra("image id", data.getIntExtra("image id", -1));
        setResult(RESULT_OK, intent);
        finish();
    }
}
