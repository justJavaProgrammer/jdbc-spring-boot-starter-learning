package com.odeyalo.jdbc.odeyalojdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class OdeyaloJdbcApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext run = SpringApplication.run(OdeyaloJdbcApplication.class, args);
        Connection bean = run.getBean(Connection.class);
        int res = bean.createStatement().executeUpdate("INSERT INTO image(id, user_id, path) VALUES(23, 42, 'C:\\Users\\thepr_2iz2cnv\\IdeaProjects\\FILE-STORAGE-MICROSERVICE\\file.jpeg')");
        System.out.println(res);


    }

}
