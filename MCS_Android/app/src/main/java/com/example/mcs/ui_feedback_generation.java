package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ui_feedback_generation extends AppCompatActivity {
    TextView textView1, textView2, textView3;
    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feedback_generation);
        button = (Button)findViewById(R.id.button_feedback_generation);
        editText1 = (EditText)findViewById(R.id.editText1_feedback_generation);
        editText2 = (EditText)findViewById(R.id.editText2_feedback_generation);
        textView3 = (TextView)findViewById(R.id.textView3_feedback_generation);

        Intent intent = getIntent();
        int taskid = intent.getIntExtra("task id", -1);

        textView3.setText("");
        textView3.append("编号：\t" + String.valueOf(0) + "\n" + MainActivity.node.task_list_curr.get(taskid).From.value + "\n（任务发布者）\n");

        if(MainActivity.node.bid_list.get(taskid).size() != 0){
            for(int loop = 0; loop < MainActivity.node.bid_list.get(taskid).size(); loop ++){
                textView3.append("编号：\t" + String.valueOf(loop + 1) + "\n" + MainActivity.node.bid_list.get(taskid).get(loop).From.value + "\n");
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = editText1.getText().toString();
                String string2 = editText2.getText().toString();
                if(string1.length() == 0||string2.length() == 0){
                    Toast.makeText(ui_feedback_generation.this, "请输入全部信息", Toast.LENGTH_SHORT);
                    return;
                }
                else{
                    int bidid = Integer.valueOf(string1);
                    double feedback = Double.parseDouble(string2);
                    Intent intent2 = new Intent();
                    intent2.putExtra("bid id", bidid);
                    intent2.putExtra("feedback", feedback);
                    setResult(RESULT_OK, intent2);
                    finish();
                }
            }
        });
    }
}
