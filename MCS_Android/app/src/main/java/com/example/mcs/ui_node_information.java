package com.example.mcs;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ui_node_information extends AppCompatActivity{
    private TextView view1, view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_information);

        this.view1 = (TextView)findViewById(R.id.textView_node_information_1);
        this.view2 = (TextView)findViewById(R.id.textView_node_information_2);

        if(MainActivity.node.outgoing_list.ip_list.size() == 0){
            this.view2.setText("暂时没有节点接入");
        }else{
            this.view2.setText("");
            for(int loop = 0; loop < MainActivity.node.outgoing_list.ip_list.size(); loop ++){
                this.view2.append(MainActivity.node.outgoing_list.ip_list.get(loop)+"\n");
            }
        }
    }

}
