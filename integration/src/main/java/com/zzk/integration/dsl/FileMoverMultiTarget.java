package com.zzk.integration.dsl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class FileMoverMultiTarget {

    public static final String INPUT_DIR = "source";
    public static final String OUTPUT_DIR = "target";
    public static final String OUTPUT_DIR2 = "target2";

    @Bean
    public IntegrationFlow fileReader() {
        return IntegrationFlows.from(sourceDirectory(), configurer -> configurer.poller(Pollers.fixedDelay(10)))
                .filter(onlyJpgs())
                .channel("holdingTank")
                .get();
    }

    @Bean
    public IntegrationFlow fileWriter() {
        return IntegrationFlows.from("holdingTank")
                .bridge(e -> e.poller(Pollers.fixedRate(1, TimeUnit.SECONDS, 20)))
                .handle(targetDirectory())
                .get();
    }

    @Bean
    public IntegrationFlow anotherFileWriter() {
        return IntegrationFlows.from("holdingTank")
                .bridge(e -> e.poller(Pollers.fixedRate(2, TimeUnit.SECONDS, 10)))
                .handle(anotherTargetDirectory())
                .get();
    }

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(INPUT_DIR));
        return messageSource;
    }

    @Bean
    public GenericSelector<File> onlyJpgs() {
        return new GenericSelector<File>() {

            @Override
            public boolean accept(File source) {
                return source.getName()
                        .endsWith(".jpg");
            }
        };
    }

    @Bean
    public MessageHandler targetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setExpectReply(false); // end of pipeline, reply not needed
        return handler;
    }

    @Bean
    public MessageHandler anotherTargetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR2));
        handler.setExpectReply(false); // end of pipeline, reply not needed
        return handler;
    }

    @Bean
    public MessageChannel holdingTank() {
        return MessageChannels.queue().get();
    }


    public static void main(final String... args) {
        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(FileMoverMultiTarget.class);
        context.registerShutdownHook();
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a string and press <enter>: ");
        while (true) {
            final String input = scanner.nextLine();
            if ("q".equals(input.trim())) {
                context.close();
                scanner.close();
                break;
            }
        }
        System.exit(0);
    }
}
