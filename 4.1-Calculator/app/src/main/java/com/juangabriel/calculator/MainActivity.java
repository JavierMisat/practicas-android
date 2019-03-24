package com.juangabriel.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText resultText;
    private EditText newNumberText;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "=";


    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (EditText) findViewById(R.id.editText);
        newNumberText = (EditText) findViewById(R.id.editText2);
        displayOperation = (TextView) findViewById(R.id.textView);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDecimal);

        Button buttonPlus = (Button) findViewById(R.id.buttonAdd);
        Button buttonMinus = (Button) findViewById(R.id.buttonSubstract);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonEquals = (Button) findViewById(R.id.buttonEqual);


        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String s = b.getText().toString();
                newNumberText.append(s);
            }
        };

        button0.setOnClickListener(listener1);
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener1);
        button3.setOnClickListener(listener1);
        button4.setOnClickListener(listener1);
        button5.setOnClickListener(listener1);
        button6.setOnClickListener(listener1);
        button7.setOnClickListener(listener1);
        button8.setOnClickListener(listener1);
        button9.setOnClickListener(listener1);
        buttonDot.setOnClickListener(listener1);


        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String operation = b.getText().toString();
                String value = newNumberText.getText().toString();// ""
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, operation);
                } catch(NumberFormatException e){
                    //e.printStackTrace();
                    newNumberText.setText("");
                }
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);

            }
        };

        buttonPlus.setOnClickListener(listener2);
        buttonMinus.setOnClickListener(listener2);
        buttonMultiply.setOnClickListener(listener2);
        buttonDivide.setOnClickListener(listener2);
        buttonEquals.setOnClickListener(listener2);



        Button buttonNeg = (Button) findViewById(R.id.button_neg);


        if(buttonNeg!=null) {
            buttonNeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentValue = newNumberText.getText().toString();

                    if (currentValue.length() == 0) {
                        newNumberText.setText("-");
                    } else {
                        try {
                            Double doubleValue = Double.valueOf(currentValue);
                            doubleValue *= -1;
                            newNumberText.setText(doubleValue.toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            //Solo se lanzará si currentValue es '-' o un '.'
                            newNumberText.setText("");
                        }

                    }

                }
            });
        }



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(STATE_PENDING_OPERATION, pendingOperation);

        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1);
        }


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);

        displayOperation.setText(pendingOperation);
    }


    private  void performOperation(Double value, String operation){

        if (null == operand1){
            operand1 = value;
        } else {

            if (pendingOperation.equals("=")){
                pendingOperation = operation;
            }


            switch (pendingOperation){
                case  "+":
                    operand1 += value;
                    break;
                case  "-":
                    operand1 -= value;
                    break;
                case  "x":
                    operand1 *= value;
                    break;
                case  "/":
                    if (value == 0){
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case  "=":
                    operand1 = value;
                    break;
            }
        }



        resultText.setText(operand1.toString());
        newNumberText.setText("");

    }
}
