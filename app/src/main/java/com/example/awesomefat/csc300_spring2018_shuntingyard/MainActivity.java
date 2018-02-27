package com.example.awesomefat.csc300_spring2018_shuntingyard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Queue q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editText = findViewById(R.id.editText);

        //"10+3-2" -> turn into a queue of NumNodes and OpNodes
        this.q = new Queue();
//        q.enqueue(10);
//        q.enqueue('+');
//        q.enqueue(3);
//        System.out.println(q.dequeue() instanceof NumNode);
//        System.out.println(q.dequeue() instanceof OpNode);
//        System.out.println(q.dequeue() instanceof OpNode);
    }

    public void onClickButton(View view) {
        String string = this.editText.getText().toString();
        String regex = "(\\d+\\.\\d+)|(\\d+)|([+-/*///^])|([/(/)])";

        Matcher m = Pattern.compile(regex).matcher(string);

        while (m.find()) {
            if (TextUtils.isDigitsOnly(m.group())) {
                int i = Integer.parseInt(m.group());
                q.enqueue(i);
            } else {
                char c = m.group().charAt(0);
                q.enqueue(c);
            }

        }

    }
}
