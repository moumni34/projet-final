package tn.esprit.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class TwilioExample {
    public static final String ACCOUNT_SID = "AC229b65434150962128250afae0b73e71";
    public static final String AUTH_TOKEN = "118b1bca05d8f5b43b03f597a1951306";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber("+21695018151"), // Replace with recipient's phone number
                        new PhoneNumber("+15673716374"), // Replace with your Twilio phone number
                        "Hello, World!")
                .create();

        System.out.println(message.getSid());
    }
}

