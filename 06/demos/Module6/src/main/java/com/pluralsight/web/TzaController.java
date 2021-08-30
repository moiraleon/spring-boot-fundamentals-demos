package com.pluralsight.web;

import com.pluralsight.entity.Application;
import com.pluralsight.entity.Ticket;
import com.pluralsight.exception.ApplicationNotFoundException;
import com.pluralsight.service.ApplicationService;
import com.pluralsight.service.ReleaseService;
import com.pluralsight.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController //adds @Controller and @ResponseBody
@RequestMapping("/tza")
public class TzaController {
    private ApplicationService applicationService;
    private TicketService ticketService;

    @Autowired
    public void setApplicationService(ApplicationService applicationService) { this.applicationService = applicationService; }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> list = ticketService.listTickets();
        return new ResponseEntity<List<Ticket>>(list, HttpStatus.OK);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> list = applicationService.listApplications();
        return new ResponseEntity<List<Application>>(list, HttpStatus.OK);
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Application>(applicationService.findApplication(id),
                    HttpStatus.OK);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application Not Found");
        }
        //adding axception handling to API's
        //moleon@MacBook-Pro-2 fundamentals2 % curl http://localhost:8080/tza/application/76
        //{"timestamp":"2021-08-30T21:04:11.625+00:00","status":404,"error":"Not Found","path":"/tza/application/76"}%
        // moleon@MacBook-Pro-2 fundamentals2 %
    }
}

//moleon@MacBook-Pro-2 ~ % cd Desktop/DevMountain/Java\ Specialization/
//moleon@MacBook-Pro-2 Java Specialization % ls
//08_16_2021Lab				java-collections-fundamentals
//08_19_2021				java-fundamentals-demo
//App.java				mars-java
//Getting_Started_With_Java_Part_2	object-oriented-java-code
//Program Files				object-oriented-programming-java
//fundamentals				oo-practice-melons-java
//fundamentals2				spring-big-picture
//guessing-game-java			spring-boot-fundamentals
//hello					spring-fundamentals
//moleon@MacBook-Pro-2 Java Specialization % cd fundamentals2
//moleon@MacBook-Pro-2 fundamentals2 % curl http://localhost:8080/tza/applications
//{"timestamp":"2021-08-30T20:55:46.467+00:00","status":404,"error":"Not Found","path":"/tza/applications"}%                                                      moleon@MacBook-Pro-2 fundamentals2 % curl http:://localhost:8080/tza/ickets
//curl: (3) URL using bad/illegal format or missing URL
//moleon@MacBook-Pro-2 fundamentals2 % curl http:://localhost:8080/tza/application/1
//curl: (3) URL using bad/illegal format or missing URL
//moleon@MacBook-Pro-2 fundamentals2 %