package com.example.paymentGateway;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.stripe.Stripe;


// @Component annotation is used to create a bean of the class so that we can autowire it in other classes

@Component("stripePaymentGateway")
public class StripePaymentGateway implements PaymentGateway {

    // @Value annotation is used to inject values from application.properties file
    @Value("${stripe.key.secret}")
    private String stripeKeySecret;


    // Use Stripe API to create payment link using documentation: https://docs.stripe.com/api/payment_links/payment_links/create
    @Override
    public String generatePaymentLink(Long orderId, Long amount) throws StripeException {
        // Set the secret key to authenticate the request
        Stripe.apiKey = stripeKeySecret;

        // Create a price object to set the currency, amount and product data using documentation: https://docs.stripe.com/api/prices/create
        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(amount)  // amount is in long format so 1000 is 10.00 INR
                        .setProductData(  // Required field, to set the product name to which price will belong to
                                PriceCreateParams.ProductData.builder().setName("ProductName").build()
                        )
                        .build();
        Price price = Price.create(priceCreateParams);

        // Create a payment link using the price object created above and set the redirect URL after payment completion
        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        // The line items represents what is being sold. Each line item represents an item being sold. Up to 20 line items are supported.
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())  // Required field, to set the price id
                                        .setQuantity(2L)  // Required field, to set the quantity of the product
                                        .build()
                        )
                        // Behavior after the purchase is completed
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setRedirect(
                                                // Redirect to this URL once the payment is completed
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://github.com/SweliaMonteiro")
                                                        .build()
                                        )
                                        // Redirects the customer to the specified URL
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .build()
                        )
                        .build();

        // Create a payment link using all the parameters passed in the paymentLinkCreateParams object
        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);

        // Return the generated payment link i.e. url from the response
        return paymentLink.getUrl();
    }
}
