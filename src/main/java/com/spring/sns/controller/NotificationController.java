package com.spring.sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private AmazonSNSClient snsClient;

    String TOPIC_ARN = "arn:aws:sns:us-east-1:890689297847:notification" ;

    @GetMapping("/addSubscription/{email}")
    public String addSubscription(@PathVariable String email) {
        SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
        snsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your email : " + email;
    }

    @GetMapping("/sendNotification")
    public String publishMessageToTopic(){
        PublishRequest publishRequest=new PublishRequest(TOPIC_ARN,buildEmailBody(),"Notification: Network connectivity issue");
        snsClient.publish(publishRequest);
        return "Notification send successfully !!";
    }


    private String buildEmailBody(){
        return "Dear User ,\n" +
                "\n" +
                "\n" +
                "Server down due to heavy rainfall."+"\n"+
                "! \n";
    }

}
