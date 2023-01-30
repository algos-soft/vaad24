package it.algos;

import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.*;
import com.vaadin.flow.theme.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.autoconfigure.security.servlet.*;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets and some desktop browsers.
 * Questa classe contiene il metodo 'main' che è il punto di ingresso unico di una applicazione Java <br>
 * Nel JAR finale (runtime) si può avere una sola classe col metodo 'main' <br>
 * Nel WAR finale (runtime) occorre (credo) inserire dei servlet di context diversi <br>
 * Senza @EntityScan, SpringBoot non 'vede' le classi con @SpringView che sono in una directory diversa da questo package <br>
 * <p>
 * Annotated with @SpringBootApplication (obbligatorio) <br>
 * Annotated with @EnableVaadin (obbligatorio) <br>
 * Annotated with @EntityScan (obbligatorio) <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * <p>
 * Tutte le view devono essere comprese nel path di questa classe (directory interne incluse) <br>
 * Una sola view può avere @Route("") nulla <br>
 * <p>
 * Spring Boot introduces the @SpringBootApplication annotation. <br>
 * This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan. <br>
 * Se l'applicazione NON usa la security, aggiungere exclude = {SecurityAutoConfiguration.class} a @SpringBootApplication <br>
 */
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos"})
@EntityScan({"it.algos"})
@Theme(value = "vaad24")
@PWA(name = "vaad24", shortName = "vaad24", offlineResources = {})
@NpmPackage(value = "@adobe/lit-mobx", version = "2.0.0")
@NpmPackage(value = "mobx", version = "^6.3.5")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Vaad24SimpleApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Vaad24SimpleApplication.class, args);
//        SimpleBoot.start();
    }

}
