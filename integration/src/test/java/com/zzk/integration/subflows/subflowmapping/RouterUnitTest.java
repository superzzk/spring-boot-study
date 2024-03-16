package com.zzk.integration.subflows.subflowmapping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = { RouterExample.class })
class RouterUnitTest {
    @Autowired
    private QueueChannel multipleofThreeChannel;

    @Autowired
    private QueueChannel remainderIsOneChannel;

    @Autowired
    private QueueChannel remainderIsTwoChannel;

    @Autowired
    private RouterExample.NumbersClassifier numbersClassifier;

    @Test
    void whenSendMessagesToFlow_thenNumbersAreClassified() {

        numbersClassifier.classify(Arrays.asList(1, 2, 3, 4, 5, 6));

        Message<?> outMessage = multipleofThreeChannel.receive(0);

        assertEquals(outMessage.getPayload(), 3);

        outMessage = multipleofThreeChannel.receive(0);

        assertEquals(outMessage.getPayload(), 6);

        outMessage = remainderIsOneChannel.receive(0);

        assertEquals(outMessage.getPayload(), 1);
        outMessage = remainderIsOneChannel.receive(0);

        assertEquals(outMessage.getPayload(), 4);

        outMessage = remainderIsTwoChannel.receive(0);

        assertEquals(outMessage.getPayload(), 2);

        outMessage = remainderIsTwoChannel.receive(0);

        assertEquals(outMessage.getPayload(), 5);

    }

}
