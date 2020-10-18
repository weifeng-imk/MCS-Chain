package com.example.mcs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ui_block_curr extends AppCompatActivity {
    private TextView view1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_curr);
        this.view1 = (TextView) findViewById(R.id.textView2_block_curr);
        if(MainActivity.node.current_block_list.size() == 0){
            this.view1.setText("当前区块链没有区块");
        }else{
            int location = MainActivity.node.current_block_list.size() - 1;
            for(int loop = 0; loop < 10; loop ++){
                this.view1.append(String.valueOf(loop)+"\t"+String.valueOf(MainActivity.node.current_block_list.get(location).just_for_test.get(loop))+"\n");
            }
        }
    }
}
