package com.example.capstonewallet.Models.Clients;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.capstonewallet.Models.Servers.BraintreeServer;

import java.math.BigDecimal;

public class BraintreeClient {
    final private BraintreeServer server;
    private String clientToken;
    private BraintreeGateway gateway;
    private String amount;
    private int transactionResultCode = 0;

    public BraintreeClient() throws InterruptedException {
        server = new BraintreeServer();
        clientToken = server.getClientToken();
        gateway = server.getGateway();
    }

    public String getClientToken() {
        return this.clientToken;
    }

    public int onActivityResult(int requestCode, int resultCode, Intent data, String amount) {
        Log.d("onactres", "here client");
        Log.d("onactres", "request " + requestCode);
        Log.d("onactres", "result " + resultCode);
        Log.d("onactres", "amount " + amount);
        this.amount = amount;
        if(requestCode == 400) {
            if(resultCode == Activity.RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                if(!amount.isEmpty())
                {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                TransactionRequest request = new TransactionRequest()
                                        .amount(new BigDecimal(amount))
                                        .paymentMethodNonce(strNonce)
                                        .options()
                                        .submitForSettlement(false)
                                        .done();

                                Log.d("yo123", "result" + request.toQueryString());
                                Result<Transaction> result2 = gateway.transaction().sale(request);
                                if(result2.isSuccess()) {
                                    Log.d("yo123", "success");
                                    Transaction transaction = result2.getTarget();
                                    Log.d("yo123", transaction.getStatus().toString());
                                    transactionResultCode = 1;
                                }
                                else {
                                    Log.d("yo123", "fail");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("yo123", "invalid amount");
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
            {
                Log.d("yo123", "user cancelled");
            }
            else
            {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("Err",error.toString());
            }
        }
        return transactionResultCode;
    }
}
