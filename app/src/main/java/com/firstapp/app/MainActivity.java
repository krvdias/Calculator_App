package com.firstapp.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv,solutionTv;
    Button buttonC,openBracket,closeBracket;
    Button devide,multiply,add,substract,equal;
    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    Button dot,buttonAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC,R.id.button_c);
        assignId(openBracket,R.id.open_bracket);
        assignId(closeBracket,R.id.close_bracket);
        assignId(devide,R.id.devide);
        assignId(multiply,R.id.multiply);
        assignId(add,R.id.add);
        assignId(substract,R.id.substract);
        assignId(equal,R.id.equal);
        assignId(dot,R.id.dot);
        assignId(buttonAC,R.id.button_clear);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId (Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 1 && !dataToCalculate.equals("")) { // Check if dataToCalculate is empty
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            } else {
                solutionTv.setText("");
                resultTv.setText("0");
                return;
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult (String data) {
        try {
            Context cx = Context.enter();
            cx.setOptimizationLevel(-1);
            Scriptable scriptable = cx.initStandardObjects();
            String finalResult = cx.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }
        catch (Exception e) {
            return "Err";
        }
    }
}