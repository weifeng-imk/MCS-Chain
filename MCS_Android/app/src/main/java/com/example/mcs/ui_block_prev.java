package com.example.mcs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ui_block_prev extends AppCompatActivity {
    private TextView view1, view2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_prev);

        this.view2=(TextView)findViewById(R.id.textView2_block_prev);
        if(MainActivity.node.current_block_list.size() == 0){
            this.view2.setText("当前区块链没有区块");
        }else if(MainActivity.node.current_block_list.size() == 1){
            this.view2.setText("当前区块链只有一个区块");
        }else {
            int location = MainActivity.node.current_block_list.size() - 2;
            this.view2.setText("");
            for (int loop = 0; loop < 10; loop++) {
                this.view2.append(String.valueOf(loop) + "\t" + String.valueOf(MainActivity.node.current_block_list.get(location).just_for_test.get(loop)) + "\n");
            }
        }
    }
}
