package com.files.buyerservice.Services.Razorpay;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PaymentService {
    @Autowired
    private RazorpayClient razorpayClient;
   @Autowired
   RazorpayConfig razorpayConfig;
    public String createOrder(double amount) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency","INR" );
        orderRequest.put("payment_capture", 1);
        Order order = razorpayClient.Orders.create(orderRequest);
        return order.toString();
    }
    public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature) {
        String payload = orderId + '|' + paymentId;
        try {
            return Utils.verifySignature(payload, razorpaySignature, razorpayConfig.getSecret());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public JSONObject createTransfer(String accountId,double amount)throws RazorpayException{
        JSONObject transferREQ=new JSONObject();
        transferREQ.put("account",accountId);
        transferREQ.put("amount",amount*100);
        transferREQ.put("currency","INR");
        transferREQ.put("notes",new JSONObject().put("payment for E-chapter","payment"));
        return razorpayClient.Transfers.create(transferREQ).toJson();
    }
}
