package com.example.seedcommando_2.payhistory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<HashMap<String, String>> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        new GetContacts().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"sgd"+i+"",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),TransactiondetailActivity.class);
                HashMap a= transactionList.get(i);
                intent.putExtra("details",a);
                startActivity(intent);
            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // Making a request to url and getting response
            Myhtttp p=new Myhtttp();
            String transdata=p.fetchData();
            Log.d(TAG, "Response from url: " + transdata);

            if (transdata != null) {
                try {
                    // Getting JSON Array node
                    JSONObject transjsonobj = new JSONObject(transdata);
                    JSONArray obb = transjsonobj.getJSONArray("paymentHistoryList");
                    Log.d(TAG, "Response from  transaction url: " + obb);
                    // looping through All Contacts
                    for (int i = 0; i < obb.length(); i++) {
                        JSONObject c = obb.getJSONObject(i);
                        //  String paymentTS = c.getString("paymentTS");

                        long paymenTS =Long.parseLong(c.getString("paymentTS"));
                        String convrt_time= new Timestamps().getdata(paymenTS);
                        String paymentMode = c.getString("paymentMode");
                        String orderId = c.getString("orderId");
                        String amount = c.getString("amount");
                        String transactionId = c.getString("transactionId").toString();
                        String paymentStatus=c.getString("paymentStatus");


                        HashMap<String, String> transaction = new HashMap<>();

                        // adding each child node to HashMap key => value


                        transaction.put("paymentTS", convrt_time);
                        transaction.put("paymentMode", paymentMode);
                        transaction.put("orderId", orderId);
                        transaction.put("amount", "Rs." + amount);
                        transaction.put("transactionId", "Transaction No:"+(transactionId));
                        transaction.put("paymentStatus",paymentStatus);
                        Log.d("traid",""+transactionId);


                        // adding transaction to transaction list
                        transactionList.add(transaction);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */


            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, transactionList,
                    R.layout.list_item, new String[]{"paymentTS","transactionId","amount"}, new int[]{R.id.datea,R.id.transa ,R.id.rsa}

            );

            lv.setAdapter(adapter);
        }


    }
}