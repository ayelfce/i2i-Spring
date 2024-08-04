package com.example.serving_web_content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.DriverManager;

@Controller
public class SubscriberController {

    @Autowired
    private JdbcTemplate jdbcTemplate; // JdbcTemplate enjekte edilir

    @GetMapping("/subscriber")
    public String subscriberForm(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "subscriber"; // Thymeleaf şablonu adı
    }

    @PostMapping("/subscriber")
    public String subscriberSubmit(@ModelAttribute Subscriber subscriber, Model model) {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","***","***");
            String sql = "INSERT INTO subscriber (subsc_id, subsc_name, subsc_surname, msisdn, tariff_id) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, subscriber.getSubsc_id(), subscriber.getSubsc_name(), subscriber.getSubsc_surname(), subscriber.getMsisdn(), subscriber.getTariff_id());
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

        return "result";
    }
}

