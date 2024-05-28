package com.example.paymentGateway;

import com.razorpay.PaymentLink;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;


// @Component annotation is used to create a bean of the class so that we can autowire it in other classes

@Component("razorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentGateway {

    // Bean is created to restrict creating multiple instances of the RazorpayClient class
    @Autowired
    private RazorpayClient razorpayClient;


    // Use Razorpay API to create payment link using documentation: https://razorpay.com/docs/api/payments/payment-links/create-standard/
    @Override
    public String generatePaymentLink(Long orderId, Long amount) throws RazorpayException {
        // Create json request object to create payment link
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);  // amount is in long format so 1000 is 10.00 INR
        paymentLinkRequest.put("currency", "INR");
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(60);  // Set payment link expiration time to 60 minutes from now
        paymentLinkRequest.put("expire_by", expirationTime.atZone(ZoneId.systemDefault()).toEpochSecond());  // Convert the expiration time to epoch seconds
        paymentLinkRequest.put("reference_id", orderId.toString());  // Reference id or receipt number of the payment
        paymentLinkRequest.put("description", "Payment for order no " + orderId.toString());  // Description of the payment

        // Creating a customer json object with name, contact, and email. Set the details in json request object.
        JSONObject customer = new JSONObject();
        customer.put("name", "Swelia Monteiro");
        customer.put("contact", "+919000090000");
        customer.put("email", "sweliamonteiro05@gmail.com");  // Set the email key to the email where you want to receive payment confirmation
        paymentLinkRequest.put("customer", customer);

        // Notify the customer about the payment link using SMS and email. Set the details in json request object.
        JSONObject notify = new JSONObject();
        notify.put("sms", true);  // True means Razorpay will handle sending SMS
        notify.put("email", true);  // True means Razorpay will handle sending email
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);  // Used to send reminders for the payment link

        // Redirect to this URL once customers completes the payment
        paymentLinkRequest.put("callback_url", "https://github.com/SweliaMonteiro");
        paymentLinkRequest.put("callback_method", "get");  // Make a GET request to the callback url

        // Create a payment link using all the parameters passed in the json request object
        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        // Return the generated payment link i.e. short_url from the response
        return payment.get("short_url").toString();
    }
}
