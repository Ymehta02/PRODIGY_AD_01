package com.example.calc_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTv,solutionTv;
    MaterialButton btndiv,btnmul,btnsub,btnadd;
    MaterialButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    MaterialButton btnac,btnc,btndot,btnequal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultTv=findViewById(R.id.res_tv);
        solutionTv=findViewById(R.id.sol_tv);

        assignId(btnc,R.id.btn_c);
        assignId(btndiv,R.id.btn_div);
        assignId(btnmul,R.id.btn_mul);
        assignId(btnsub,R.id.btn_sub);
        assignId(btnadd,R.id.btn_add);
        assignId(btn1,R.id.btn_1);
        assignId(btn2,R.id.btn_2);
        assignId(btn3,R.id.btn_3);
        assignId(btn4,R.id.btn_4);
        assignId(btn5,R.id.btn_5);
        assignId(btn6,R.id.btn_6);
        assignId(btn7,R.id.btn_7);
        assignId(btn8,R.id.btn_8);
        assignId(btn9,R.id.btn_9);
        assignId(btn0,R.id.btn_0);
        assignId(btndot,R.id.btn_dot);
        assignId(btnequal,R.id.btn_equal);
        assignId(btnac,R.id.btn_ac);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId(MaterialButton btn,int id ){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btnText = btn.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (btnText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("");
            return;
        }
        if (btnText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (btnText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        }else {
            dataToCalculate = dataToCalculate.concat(btnText);
        }
        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error"))
        {
            resultTv.setText(finalResult);
        }

    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scope = context.initStandardObjects();
            String finalResult = context.evaluateString(scope,data,"javascript",1,null).toString();
            if(finalResult.endsWith(".0"))
            {
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }
        catch (Exception e){
            return "Error";
        }
    }
}