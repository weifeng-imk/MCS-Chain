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

public class ui_worker_selection_sub extends AppCompatActivity {
    Button button;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_selection_sub);

        button = (Button)findViewById(R.id.button_worker_selection_sub);
        textView = (TextView)findViewById(R.id.textView_worker_selection_sub);
        editText = (EditText)findViewById(R.id.editText_worker_selection_sub);

        Intent intent = getIntent();
        String task_id = intent.getStringExtra("task id");
        String subtask_id = intent.getStringExtra("subtask id");

        int taskid = Integer.valueOf(task_id);
        int subtaskid = Integer.valueOf(subtask_id);

        textView.setText("");
        for(int loop = 0; loop < MainActivity.node.bid_list.get(taskid).size(); loop ++){
            if(MainActivity.node.bid_list.get(taskid).get(loop).content.sub_id.id.equals(subtaskid)){
                textView.append("编号\t" + String.valueOf(loop) + "\n");
                textView.append("工作节点编号\t" + String.valueOf(MainActivity.node.bid_list.get(taskid).get(loop).From.value) + "\n");
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                if(string.length() == 0){
                    Log.println(Log.ERROR, "Warning", "No selection made in worker selection sub");
                    return;
                }
                Intent intent2 = new Intent();
                intent2.putExtra("bid id", String.valueOf(string));
                setResult(RESULT_OK, intent2);
                finish();
            }
        });
    }
}
