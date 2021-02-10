package com.example.capstonewallet.Models.Clients;

import android.util.Log;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

public class BraintreeServer {
    private BraintreeGateway gateway;
    private String clientToken;

    public String getClientToken() throws InterruptedException {
        if(this.clientToken == null) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        gateway = new BraintreeGateway(
                                String.valueOf(Environment.SANDBOX),
                                "x456g3sz4n8wfbjf",
                                "rmsmpv5fk4v6y6gb",
                                "d366d726a3469196bb09a74be9a2cfb0"
                        );
                        String clientToken = gateway.clientToken().generate();
                        setClientToken(clientToken);
                        Log.d("yo123", "clientTooken" + clientToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();
        }
        return this.clientToken;
    }

    public BraintreeGateway getGateway() {
        return this.gateway;
    }

    private void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}
