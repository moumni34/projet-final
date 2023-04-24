package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.esprit.entities.Events;
import tn.esprit.entities.User;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.*;


public class EventReminderScheduler {

    private static final String ACCOUNT_SID = "AC229b65434150962128250afae0b73e71";
    private static final String AUTH_TOKEN = "118b1bca05d8f5b43b03f597a1951306";
    private static final String FROM_NUMBER = "+15673716374";
    private static final long REMINDER_INTERVAL = 1 * 60 * 1000; // 5 minutes

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    private final Timer timer;
    private final Events event;

    public EventReminderScheduler(Events event) {
        this.event = event;
        this.timer = new Timer();
    }

    public void start() {
        Date date = event.getStartDate();
        LocalDateTime localDateTime = new Timestamp(date.getTime()).toLocalDateTime();
        long initialDelay = Duration.between(LocalDateTime.now(), localDateTime).toMillis();
        timer.schedule(new CountdownTask(event.getName(), initialDelay), initialDelay, REMINDER_INTERVAL);
    }


    public void stop() {
        timer.cancel();
    }
//fasekhhha bbad
    public CountdownTask getCountdownTask() {
        return new CountdownTask(event.getName(), 0L);
    }
//rodha private baad
    public class CountdownTask extends TimerTask {
        private final String eventName;
        private long countdownTimeMillis;
        private boolean isSmsSent = false;

        public CountdownTask(String eventName, long countdownTimeMillis) {
            this.eventName = eventName;
            this.countdownTimeMillis = countdownTimeMillis;
        }

        @Override
        public void run() {
            if (countdownTimeMillis >= 0) {
                List<String> phoneNumbers = getRegisteredUsersPhoneNumbersForEvent(event);
                String reminderMessage = String.format("Reminder: Your event '%s' starts in %d minutes!",
                        eventName, countdownTimeMillis / (60 * 1000));

                for (String phoneNumber : phoneNumbers) {
                    Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(FROM_NUMBER), reminderMessage)
                            .create();
                }
                isSmsSent = true;
                countdownTimeMillis -= REMINDER_INTERVAL;
            } else {
                stop();
            }
        }
        public boolean isSmsSent() {
            return isSmsSent;
        }
    }   private List<String> getRegisteredUsersPhoneNumbersForEvent(Events event) {
        List<User> users = event.getUsers();
        List<String> phoneNumbers = new ArrayList<>();
        for (User user : users) {
            phoneNumbers.add(user.getPhone());
        }
        return phoneNumbers;
    }

}



