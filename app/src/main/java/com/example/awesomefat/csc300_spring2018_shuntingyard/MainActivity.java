package com.example.awesomefat.csc300_spring2018_shuntingyard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private Queue q;
    private Queue outputQ;
    private OpStack opStack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //"10+3-2" -> turn into a queue of NumNodes and OpNodes
        this.q = new Queue();
    }

    private String removeSpaces(String s) {
        String answer = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                answer = answer + s.charAt(i);
            }
        }
        return answer;
    }

    private void testQ() {
        while (!this.q.isEmpty()) {
            Node n = this.q.dequeue();
            if (n instanceof NumNode) {
                NumNode temp = (NumNode) n;
                System.out.println(temp.getPayload());
            } else {
                OpNode temp = (OpNode) n;
                System.out.println(temp.getPayload());
            }
        }
    }

    private void testOutputQ() {
        String answer = "";
        while (!this.outputQ.isEmpty()) {
            Node n = this.outputQ.dequeue();
            if (n instanceof NumNode) {
                NumNode temp = (NumNode) n;
                System.out.println(temp.getPayload());
                answer = answer + temp.getPayload() + ' ';
            } else {
                OpNode temp = (OpNode) n;
                System.out.println(temp.getPayload());
                answer = answer + temp.getPayload() + ' ';
            }
        }
        EditText editText = findViewById(R.id.rpnET);
        editText.setText(answer);
    }

    private void parseString(String s) {
        String currNumber = "";
        String digits = "0123456789";
        for (int i = 0; i < s.length(); i++) {
            if (digits.indexOf(s.charAt(i)) != -1) {
                currNumber = currNumber + s.charAt(i);
            } else {
                this.q.enqueue(Integer.parseInt(currNumber));
                currNumber = "";
                this.q.enqueue(s.charAt(i));
            }
        }
        this.q.enqueue(Integer.parseInt(currNumber));
//        this.testQ();
    }

    private void parseStringTok(String s) {
        StringTokenizer st = new StringTokenizer(s, "+-*/", true);
        String temp;
        String ops = "+-*/";
        while (st.hasMoreTokens()) {
            temp = st.nextToken().trim();
            if (ops.indexOf(temp.charAt(0)) == -1) {
                this.q.enqueue(Integer.parseInt(temp));
            } else {
                //"+" -> '+'
                this.q.enqueue(temp.charAt(0));
            }
        }
//        this.testQ();
    }

    public void onClickMeButtonPressed(View v) {
        EditText inputET = (EditText) this.findViewById(R.id.inputET);
        String valueWithoutSpaces = this.removeSpaces(inputET.getText().toString());
        this.parseStringTok(inputET.getText().toString());
    }

    public void deQandStackOPs(View view) {
        opStack = new OpStack();
        this.outputQ = new Queue();
        Node temp;
        while (!this.q.isEmpty()) {
            temp = q.dequeue();
            if (temp instanceof NumNode) {
                this.outputQ.enqueue(((NumNode) temp).getPayload());
            }
            if (temp instanceof OpNode) {
                if (this.opStack.isEmpty()) {
                    this.opStack.push((OpNode) temp);
                } else {
                    this.tryToPushOp((OpNode) temp);
                }
            }
        }
        while (!this.opStack.isEmpty()) {
            OpNode tempOp = this.opStack.pop();
            this.outputQ.enqueue(tempOp.getPayload());
        }
        this.testOutputQ();
    }

    private void tryToPushOp(OpNode node) {
        char op = this.opStack.peek().getPayload();
        if (op == '+' || op == '-') {
            this.opStack.push(node);
        } else {
            OpNode temp = this.opStack.pop();
            this.outputQ.enqueue(temp.getPayload());
            this.opStack.push(node);
        }
    }
}
