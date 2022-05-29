package visable.example.codingtask.messageque.consumer;


import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import visable.example.codingtask.dao.Message;
import visable.example.codingtask.messageque.config.MessageConfig;
import visable.example.codingtask.repository.MessageRepo;
import visable.example.codingtask.services.MessageService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class MessageConsumer {


    @Autowired
    private MessageRepo messageRepo;

    public void processMessage(Message message) {

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("logs/MessageQueLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Message: " + message.getContent() + " added.");

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues = MessageConfig.QUEUE)
    public void messageFromQue(Message message) {
        System.out.println("Message consumer initiated : " + message.getContent());
        messageRepo.save(message);
//        MessageConsumer messageConsumer = new MessageConsumer();
//        messageConsumer.processMessage(message);

    }


}
