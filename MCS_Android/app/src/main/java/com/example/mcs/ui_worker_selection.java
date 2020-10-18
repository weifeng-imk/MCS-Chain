package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ui_worker_selection extends AppCompatActivity {
    Button button;
    EditText editText1, editText2;
    TextView textView;
    int a, b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_selection);

        button  = (Button) findViewById(R.id.button_worker_selection);
        editText1 = (EditText) findViewById(R.id.editText1_work_selection);
        editText2 = (EditText) findViewById(R.id.editText2_worker_selection);
        textView = (TextView) findViewById(R.id.textView_worker_selection) ;

        if(MainActivity.node.task_list_curr.size() == 0){
            textView.setText("没有任何任务");
        }else{
            for(int loop = 0; loop < MainActivity.node.task_list_curr.size(); loop ++){
                textView.append("任务编号:\t"+ String.valueOf(loop)+"\n");
                textView.append(MainActivity.node.task_list_curr.get(loop).content.task_des.value + "\n");
                for(int loop1 = 0; loop1 < MainActivity.node.task_list_curr.get(loop).content.subtask_list.size(); loop1 ++){
                    textView.append("子任务编号\t"+String.valueOf(loop) + "."+String.valueOf(loop1) + "\n");
                    textView.append(MainActivity.node.task_list_curr.get(loop).content.subtask_list.get(loop1).subtask_des.value + "\n");
                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = null;
                String string2 = null;
                string1 = editText1.getText().toString();
                string2 = editText2.getText().toString();

                a = Integer.valueOf(string1);
                b = Integer.valueOf(string2);

                Intent intent = new Intent(ui_worker_selection.this, ui_worker_selection_sub.class);
                intent.putExtra("task id", string1);
                intent.putExtra("subtask id", string2);

                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1: {
                if (resultCode == RESULT_OK) {
                    String string = data.getStringExtra("bid id");
                    Intent intent3 = new Intent();
                    intent3.putExtra("bid id", string);
                    intent3.putExtra("task id", a);
                    intent3.putExtra("subtask id", b);
                    setResult(RESULT_OK, intent3);
                    finish();
                }
            }
        }
    }
}
