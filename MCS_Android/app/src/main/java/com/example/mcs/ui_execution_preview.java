package com.example.mcs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ui_execution_preview extends AppCompatActivity {
    Button button, previous, next;
    EditText editText1, editText2;
    ImageView imageView;
    int index = 0;
    int taskid;
    int subtaskid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_preview);
        button = (Button)findViewById(R.id.button1_execution_preview);
        previous = (Button)findViewById(R.id.button_previous);
        next = (Button)findViewById(R.id.button_next);
        imageView = (ImageView)findViewById(R.id.imageView1_execution_preview);
        editText1 = (EditText) findViewById(R.id.editText1_execution_preview);
        editText2 = (EditText)findViewById(R.id.editText2_execution_preview);

        previous.setEnabled(false);
        next.setEnabled(false);

        final int[] imageItems = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7};
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = editText1.getText().toString();
                String string2 = editText2.getText().toString();
                taskid = Integer.valueOf(string1);
                subtaskid = Integer.valueOf(string2);
                if(MainActivity.node.task_list_curr.size() <= taskid){
                    previous.setEnabled(false);
                    next.setEnabled(false);
                    Toast.makeText(ui_execution_preview.this, "没有这个任务", Toast.LENGTH_SHORT).show();
                }
                else if(!MainActivity.node.task_list_curr.get(taskid).From.value.equals(MainActivity.node.node_info.id.value)){
                    Toast.makeText(ui_execution_preview.this, "您并非该任务的发布者", Toast.LENGTH_SHORT).show();
                }
                else if(MainActivity.node.data_hash_list.get(taskid).size()==0){
                    Toast.makeText(ui_execution_preview.this, "该任务暂无数据", Toast.LENGTH_SHORT).show();
                }
                else{
                    previous.setEnabled(false);
                    if(MainActivity.node.data_hash_list.get(taskid).size() > 1){
                        next.setEnabled(true);
                    }
                    String string_byte = new String(MainActivity.node.data_hash_list.get(taskid).get(index).content.data_hash.get(0));
                    imageView.setImageResource(imageItems[Integer.valueOf(string_byte)]);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index - 1;
                //String string_byte = new String(MainActivity.node.data_hash_list.get(taskid).get(index).content.data_hash);
                String string_byte = new String(MainActivity.node.data_hash_list.get(taskid).get(index).content.data_hash.get(0));
                imageView.setImageResource(imageItems[Integer.valueOf(string_byte)]);

                if(index == 0){
                    previous.setEnabled(false);
                }else{
                    previous.setEnabled(true);
                }

                if(index >= MainActivity.node.data_hash_list.size() - 1){
                   next.setEnabled(false);
                }else{
                    next.setEnabled(true);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index + 1;
                imageView.setImageResource(Integer.valueOf(String.valueOf(MainActivity.node.data_hash_list.get(taskid).get(index).content.data_hash)));

                if(index == 0){
                    previous.setEnabled(false);
                }else{
                    previous.setEnabled(true);
                }

                if(index >= MainActivity.node.data_hash_list.size() - 1){
                    next.setEnabled(false);
                }else{
                    next.setEnabled(true);
                }
            }
        });
    }
}
