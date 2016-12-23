package com.example.seedcommando_2.payhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class TransactiondetailActivity extends AppCompatActivity {
ListView listview;
TextView tv;
    View a;
    TextView activationStatus,tansDate,payModelbl,payMode,transamt,transamtlbl,transNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.transactiondetail);
       a =(View) findViewById(R.id.line10);
        tv=(TextView) findViewById(R.id.textView8);
        activationStatus=(TextView)findViewById(R.id.textView9);

        tansDate =(TextView)findViewById(R.id.textView10);
        payModelbl=(TextView) findViewById(R.id.textView11);
        payMode=(TextView) findViewById(R.id.textView12);
        transamtlbl=(TextView) findViewById(R.id.textView13);
        transamt=(TextView) findViewById(R.id.textView14);
        transNumber=(TextView) findViewById(R.id.textView15);

        Intent intent1 = getIntent();
        Bundle b = intent1.getExtras();
        Object o = b.get("details");
        HashMap<String, String> a = (HashMap<String, String>) o;
        String paymentMethod = a.get("paymentMode");
        String amount = a.get("amount");
        String orderId = a.get("orderId");
        String transactionId = a.get("transactionId");
        String paymentStatus = a.get("paymentStatus");
        String paymentTS=a.get("paymentTS");
        tv.setText(paymentTS);
      //  System.out.println(paymentMethod + " " + amount + "  " + orderId + "  " + transactionId+" "+paymentStatus);
      if (paymentStatus.equals("SUCCESS")) {
           System.out.println("successggggggghjhjhjhjhjhjhjjjjjjjjjjjjjjjjjjjjjjjjj");
          tv.setText("PAYMENT SUCCESSFULL");
          activationStatus.setText("Your sling cource has been activated");
          tansDate.setText(paymentTS);
          payModelbl.setText("Payment Method");
          payMode.setText(paymentMethod);
          if(payMode.getText().toString().equals("Paytm"))
          {
              payMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pytm,0,0,0);
          }
         else
          {
              payMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.viks,0,0,0);

          }
          transamtlbl.setText("Amount"+"          "+"OrderNo");
          transamt.setText(amount+"          "+orderId);
          transNumber.setText(transactionId);
        }
        else if(paymentStatus.equals("PENDING"))
        {
            tv.setText("PAYMENT PENDING"+"\n");
            activationStatus.setText("Your sling cource has been  not activated");
            tansDate.setText(paymentTS);
            payModelbl.setText("Payment Method");
            payMode.setText(paymentMethod);
            transamtlbl.setText("Amount"+"          "+"OrderNo");
            transamt.setText(amount+"          "+orderId);
            transNumber.setText(transactionId);

        }
        else
      {
            
          tansDate.setVisibility(View.INVISIBLE);
          payModelbl.setVisibility(View.INVISIBLE);
          payMode.setVisibility(View.INVISIBLE);
          transamtlbl.setVisibility(View.INVISIBLE);
          transamt.setVisibility(View.INVISIBLE);
          transNumber.setVisibility(View.INVISIBLE);
          tv.setText("PAYMENT FAILED"+"\n"+"Your sling cource has been not activated");
      }


    }
}
