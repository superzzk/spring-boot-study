package org.example;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class SpringAdminServer
{
    public static void main( String[] args ) {
        SpringApplication.run(SpringAdminServer.class, args);
    }
}
