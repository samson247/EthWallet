package com.example.capstonewallet.Models.Servers;

import android.util.Log;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

/**
 * Server class for Braintree, retrieves token to complete transaction
 */
public class BraintreeServer {
    private BraintreeGateway gateway;
    private String clientToken;

    /**
     * Gets new client token
     * @return the string representation of the token
     * @throws InterruptedException if thread becomes interrupted
     */
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
                        Log.d("yo123", "clientTooken " + clientToken);
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

    /**
     * Getter for Braintree gateway
     * @return the Braintree gateway
     */
    public BraintreeGateway getGateway() {
        return this.gateway;
    }

    /**
     * Setter for client token
     * @param clientToken the token used to complete transaction
     */
    private void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}
