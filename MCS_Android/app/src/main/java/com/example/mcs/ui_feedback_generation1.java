package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ui_feedback_generation1 extends AppCompatActivity {
    Button button;
    TextView textView3;
    EditText editText1, editText2;
    int taskid = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_selection);
        button = (Button)findViewById(R.id.btn1_task_selection);
        textView3 = (TextView)findViewById(R.id.textView3_task_selection);
        editText1 = (EditText)findViewById(R.id.editText1_task_selection);
        editText2 = (EditText)findViewById(R.id.editText2_task_selection);

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
                String string1 = null, string2 = null;
                string1 = editText1.getText().toString();
                string2 = editText2.getText().toString();

                if(string1.length() == 0||string2.length() == 0){
                    Toast.makeText(ui_feedback_generation1.this, "请输入全部信息", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    taskid = Integer.valueOf(string1);
                    int subtaskid = Integer.valueOf(string2);
                    Intent intent = new Intent(ui_feedback_generation1.this, ui_feedback_generation.class);
                    intent.putExtra("task id", taskid);
                    intent.putExtra("subtask id", subtaskid);
                    intent.putExtra("task id", taskid);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int bidid = data.getIntExtra("bid id", -1);
        double  feedback = data.getDoubleExtra("feedback", -1.0);
        Intent intent2 = new Intent();
        intent2.putExtra("bid id", bidid);
        intent2.putExtra("feedback", feedback);
        intent2.putExtra("task id", taskid);
        setResult(RESULT_OK, intent2);
        this.finish();
    }

}
