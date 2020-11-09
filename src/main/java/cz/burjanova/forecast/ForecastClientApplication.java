package cz.burjanova.forecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;

@SpringBootApplication
public class ForecastClientApplication {


	public static void main(String[] args) {
		SpringApplication.run(ForecastClientApplication.class, args);
	}

    static final Logger logger = LoggerFactory.getLogger(ForecastClientApplication.class);

    /**
     * Oprava chovani beanu, ktery je zodpovedny za tuto funkcionalitu:
     * Pokud v ModelAndView neni rucne nastavene viewName (= cesta k souboru sablony),
     * tento bean vygeneruje viewName automaticky na zaklade URL pozadavku.
     *
     * Problem je ve chvili, kdy si vypneme automaticke suffixovani viewNamu ve viewResolveru,
     * napriklad pomoci spring.thymeleaf.suffix="",
     * protoze misto "soubor-sablony" chceme psat "soubor-sablony.html".
     * Ve vychozim nastaveni by tento bean totiz zahazoval
     * priponu souboru z URL.
     *
     * Aby to nedelal, predefinovavame ho zde.
     */
    @Bean
    public RequestToViewNameTranslator viewNameTranslator(Environment environment) {
        DefaultRequestToViewNameTranslator viewNameTranslator = new DefaultRequestToViewNameTranslator();
        String thymeleafSuffix = environment.getProperty("spring.thymeleaf.suffix");
        if (thymeleafSuffix != null && thymeleafSuffix.isEmpty()) {
            viewNameTranslator.setStripExtension(false);
        }
        return viewNameTranslator;
    }

    /**
     * Posluchac udalosti nasazeni webove aplikace na Tomcat, ktery zaloguje adresu,
     * na ktere je aplikace nasazena
     * @param evt The event object
     */
    @EventListener
    public void onAppEvent(ServletWebServerInitializedEvent evt) {
        int port = evt.getApplicationContext().getWebServer().getPort();
        logger.info("Your web app address: http://localhost:" + port +
            evt.getApplicationContext().getServletContext().getContextPath());
    }


}
