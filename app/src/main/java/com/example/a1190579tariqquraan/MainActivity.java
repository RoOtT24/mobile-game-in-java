package com.example.a1190579tariqquraan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //////////
        EditText password = (EditText) findViewById(R.id.input_password);
        EditText input = (EditText) findViewById(R.id.input);
        /////////
        Button q = (Button) findViewById(R.id.q);
        Button w = (Button) findViewById(R.id.w);
        Button e = (Button) findViewById(R.id.e);
        Button r = (Button) findViewById(R.id.r);
        Button t = (Button) findViewById(R.id.t);
        Button y = (Button) findViewById(R.id.y);
        Button u = (Button) findViewById(R.id.u);
        Button l = (Button) findViewById(R.id.l);
        Button a = (Button) findViewById(R.id.a);
        Button s = (Button) findViewById(R.id.s);
        Button b = (Button) findViewById(R.id.b);
        Button j = (Button) findViewById(R.id.j);
        Button space = (Button) findViewById(R.id.space);
        Button [] buttons = {q,w,e,r,t,y,u,l,a,s,b,j};

        Button delete = (Button) findViewById(R.id.delete);
        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        //////////
        TextView score = (TextView) findViewById(R.id.score);
        TextView attempt = (TextView) findViewById(R.id.attempt);
        TextView outputText = (TextView) findViewById(R.id.outputText);
        //////////
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        //////////
        final boolean [] checked = {true,true,false,false};
        input.setEnabled(false);
        password.setEnabled(false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switch1.isChecked()){
                    if(!checked[2]) {
                        password.setEnabled(true);
                        checked[1] = false;
                        checked[3] = true;
                    }
                    input.setEnabled(false);
                }
                else {

                    if(checked[2]) {
                        input.setEnabled(true);
                        one.setEnabled(false);
                        checked[3] = true;
                    }
                    password.setEnabled(false);
                    checked[1] = false;
                }
            }
        });


        ///////////////////////////////////////////////////

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().length()>0) {
                    checked[0] = false;
                    checked[1] = true;
                    checked[2] = true;
                    checked[3] = !switch1.isChecked();
                    password.setEnabled(false);
                    one.setEnabled(false);
                    if(!switch1.isChecked()) {
                        input.setEnabled(true);
                        checked[3] = true;
                        checked[1] = false;
                    }
                }
            }
        });

        ////////////////////////////////////////////////////

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()) {
                    password.setEnabled(true);
                    checked[1] = false;
                    checked[3] = true;
                }
                reset(input, password, one, two, score, attempt, outputText, checked, switch1); // restart the game

            }
        });

        ////////////////////////////////////////////////////

        // Disable keyboard typing on EditTexts
        // This link lead me to this java method :D
        // https://stackoverflow.com/questions/10636635/disable-keyboard-on-edittext#:~:text=To%20stop%20the%20keyboard%20from,)%20%7B%20return%20true%3B%20%7D%20%7D)%3B
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        ///////////////////////////////////////////////////////////////////

        // Letters Events

        for(int i = 0 ; i < buttons.length; ++i){
            final Button button = buttons[i];
                    button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked[1] && checked[3])
                        if(checked[0] && !checked[2] && password.isEnabled()){
                            addPassword(password, button.getText().toString());
                        }
                        else if(input.isEnabled()){
                            addInput(input, password, one, two, score, attempt,
                                    outputText, button.getText().toString(), checked, switch1);
                        }
                }
            });
        }

        /////

        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checked[1])
                    if(checked[0] && password.isEnabled()){
                        addPassword(password, " ");
                    }
                    else if(input.isEnabled()){
                        addInput(input, password, one, two, score, attempt, outputText, " ", checked, switch1);
                    }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checked[1])
                    if(checked[0] && password.isEnabled()){
                        if(password.getText().toString().length()>0)
                            password.setText(password.getText().toString().substring(0,password.getText().toString().length()-1));
                    }
                    else if(input.isEnabled()){
                        int num = Integer.parseInt(attempt.getText().toString().split(":")[1]);
                        if(num > 0){
                            String text2 = password.getText().toString();
                            num = Integer.parseInt(score.getText().toString().split(":")[1]);
                            String text1 = input.getText().toString();
                            num = Integer.parseInt(score.getText().toString().split(":")[1]);

                            // In case a button was pressed after the game is over
                            if(num == text2.length() && text2.length()>0){
                                outputText.setText("Success");
                            }
                            else {
                                // if a correct letter was deleted
                                if(text1.charAt(text1.length()-1) == text2.charAt(text1.length()-1))
                                    score.setText("Score:"+Integer.toString(num-1));

                                if(input.getText().toString().length()>0)
                                input.setText(input.getText().toString().substring(0,input.getText().toString().length()-1));

                            }
                        }   // end if num > 0
                        else {
                            switch1.setChecked(true); // restart the game
                        }
                    }
            }
        });

    }
    private void addInput(EditText input, EditText password, Button one, Button two, TextView score,
                          TextView attempt, TextView output, String letter, boolean [] checked, Switch switch1){
        int num = Integer.parseInt(attempt.getText().toString().split(":")[1]);
        if(num > 0 && password.getText().toString().length()>0){
            String text2 = password.getText().toString();
            num = Integer.parseInt(score.getText().toString().split(":")[1]);

            // In case a button was pressed after the game is over
            if(num == text2.length() && text2.length()>0){
                output.setText("Success");
                two.setEnabled(true);
                return;
            }

            input.setText(input.getText().toString()+letter);
            String text1 = input.getText().toString();
            if(text1.length()<=text2.length() && text1.charAt(text1.length()-1) == text2.charAt(text1.length()-1)){
                score.setText("Score:"+Integer.toString(num = num+1));
                if(num == text2.length() && text2.length()>0) {
                    output.setText("Success");
                    two.setEnabled(true);
                }

            }
            else{ // false letter
                num = Integer.parseInt(attempt.getText().toString().split(":")[1]);
                attempt.setText("#Attempt:"+Integer.toString(num-1));
                --num;
                if(num == 0) {
                    output.setText("Game Over!");
                    two.setEnabled(true);
                }
            }
        } // end if num > 0
        else {
            reset(input, password, one, two, score, attempt, output, checked, switch1); // restart the game
            checked[0] = true;
            checked[1] = true;
            checked[2] = false;
            checked[3] = false;
        }
    }


    private void addPassword (EditText password, String letter){
        if(password.length()<8)
            password.setText(password.getText().toString()+letter);
    }

    private void reset (EditText input, EditText password, Button one,
                        Button two, TextView score, TextView attempt,
                        TextView output, boolean [] checked, Switch switch1){
        input.setEnabled(false);
        input.setText("");
        password.setText("");
        one.setEnabled(true);
        two.setEnabled(false);
        score.setText("Score:0");
        attempt.setText("#Attempts:3");
        output.setText("Output Text");
        checked[0] = true;
        checked[1] = true;
        checked[2] = false;
        checked[3] = switch1.isChecked();
    }
}