package com.example.a1deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity {
    EditText display;
    boolean enSonEsiteBasildi=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display=findViewById(R.id.display);
        display.setShowSoftInputOnFocus(false);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getString(R.string.tap_here).equals(display.getText().toString())){
                    display.setText("");
                }
            }
        });

    }

    public void anyButton(View view) {
        if(enSonEsiteBasildi){
            display.setText("");
            enSonEsiteBasildi=false;
        }
        switch (view.getId()){
            case R.id.clear:display.setText("");break;
            case R.id.brackets:addBrackets();break;
            case R.id.square:updateDisplay("^");break;
            case R.id.slash:updateDisplay("÷");break;
            case R.id.seven:updateDisplay("7");break;
            case R.id.eight:updateDisplay("8");break;
            case R.id.nine:updateDisplay("9");break;
            case R.id.cross:updateDisplay("×");break;
            case R.id.four:updateDisplay("4");break;
            case R.id.five:updateDisplay("5");break;
            case R.id.six:updateDisplay("6");break;
            case R.id.minus:updateDisplay("-");break;
            case R.id.one:updateDisplay("1");break;
            case R.id.two:updateDisplay("2");break;
            case R.id.three:updateDisplay("3");break;
            case R.id.plus:updateDisplay("+");break;
            case R.id.three_zero:updateDisplayThreeZero();   break;
            case R.id.zero:updateDisplay("0");break;
            case R.id.dot:updateDisplay(".");break;
            case R.id.equals:calculateTheResult();break;
            case R.id.backspace:backSpace();break;




        }
    }

    private void backSpace() {
        int cursorPos=display.getSelectionStart();
        if(cursorPos>0){
            String oldDisplay=display.getText().toString();
            String liftSideOfDisplay=oldDisplay.substring(0,cursorPos-1);
            String rightSideOfDisplay=oldDisplay.substring(cursorPos);
            String newText=liftSideOfDisplay+rightSideOfDisplay;
            display.setText(newText);
            display.setSelection(cursorPos-1);
        }
    }

    private void calculateTheResult() {

        String textDisplay= display.getText().toString();
        String reTextDisplay=textDisplay.replaceAll("÷","/");
        reTextDisplay=reTextDisplay.replaceAll("×","*");
        Expression ifade=new Expression(reTextDisplay);
        String result=String.valueOf(ifade.calculate()).toString();
        if(!result.equals("NaN")){
            display.setText(result);
            display.setSelection(result.length());
        } else{
            showToast("Hatali Giris");
        }

        enSonEsiteBasildi=true;

    }

    private void updateDisplayThreeZero() {
        int cursorPos=display.getSelectionStart();
        if(getString(R.string.tap_here).equals(display.getText().toString())){
            display.setText("000");
        }
        else{
            String oldDisplay=display.getText().toString();
            String liftSideOfDisplay=oldDisplay.substring(0,cursorPos);
            String rightSideOfDisplay=oldDisplay.substring(cursorPos);
            String newText=liftSideOfDisplay+"000"+rightSideOfDisplay;
            display.setText(newText);
        }
        display.setSelection(cursorPos+3);
    }

    private void updateDisplay(String addCharToDisplay) {
        int cursorPos=display.getSelectionStart();
        if(getString(R.string.tap_here).equals(display.getText().toString())){
            display.setText(addCharToDisplay);
        }
        else{
           String oldDisplay=display.getText().toString();
           String liftSideOfDisplay=oldDisplay.substring(0,cursorPos);
           String rightSideOfDisplay=oldDisplay.substring(cursorPos);
           String newText=liftSideOfDisplay+addCharToDisplay+rightSideOfDisplay;
           display.setText(newText);
        }
        display.setSelection(cursorPos+1);
    }

    private void addBrackets() {
        String textDisplay=display.getText().toString();
        int cursorPos=display.getSelectionStart();
        int countBrackets=0;
        for(int i=0;i<textDisplay.length();i++){
            String anotherString;

            if(textDisplay.substring(i,i+1).equalsIgnoreCase("(")) countBrackets++;
            if(textDisplay.substring(i,i+1).equalsIgnoreCase(")")) countBrackets--;
        }
        String lastCharOfTextDisplay=textDisplay.substring(textDisplay.length()-1);
        if(countBrackets==0|| lastCharOfTextDisplay.equals("(")) updateDisplay("(");
        else if(countBrackets>0 && !lastCharOfTextDisplay.equals(")")) updateDisplay(")");
    }
    private void showToast(String text){
        LayoutInflater inflater=getLayoutInflater();
        View loyout=inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout));
        Toast toast=new Toast(getApplicationContext());
        TextView toastText=loyout.findViewById(R.id.toast_text);
        toastText.setText(text);

        toast.setGravity(Gravity.CENTER,0,-200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(loyout);
        toast.show();
    }
}