package com.example.mcs;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ui_personal_information extends AppCompatActivity {


    private TextView view1, view2, view3, view4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);

        this.view1 = (TextView)findViewById(R.id.textView);
        this.view2 = (TextView)findViewById(R.id.textView2);
        this.view3 = (TextView)findViewById(R.id.textView3);
        this.view4 = (TextView)findViewById(R.id.textView4);

        if(MainActivity.node == null){
            view1.setText("用户公钥");
            view2.setText("未设置");
            view3.setText("用户IP");
            view4.setText("未设置");
        }else{
            String tmp = MainActivity.node.node_info.id.value;
            String tmp2 = MainActivity.node.ip;

            view1.setText("用户公钥");
            view2.setText(tmp);
            view3.setText("用户IP");
            view4.setText(tmp2);
        }
    }


}
