package cz.burjanova.forecast.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ApiCall {

    private final OkHttpClient client = new OkHttpClient();

    public Location doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();

        log.info(stringResponse);

        if (stringResponse.contains("This is a preview of the first 2 rows.You have exceeded the maximum number of daily result records for your account.")) {
            log.info("**********************************");
            log.info("API CREDIT RAN OUT");
            log.info("**********************************");
            return new Location("The credit for API is not sufficient");
        } else if (stringResponse.contains("Bad api key")) {
            log.info("**********************************");
            log.info("NO VALID API KEY");
            log.info("**********************************");
            return new Location("The key for the API is not valid");

        } else if (stringResponse.contains("this is a partial address, please consider adding addition detail")) {
            return new Location("The place you inserted was not found, try another place or check typos");

        } else if (stringResponse.contains("Please verify the location and dates requested")) {
            return new Location("The place you inserted was not found, try another place or check typos");

        } else if (stringResponse.contains("Must specify either location list or input datasource")) {
            return new Location("You have not inserted any character, fill the search field to see the forecast");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        cz.burjanova.forecast.entity.Response rawResponse = objectMapper.readValue(stringResponse, cz.burjanova.forecast.entity.Response.class);
        Location location = rawResponse.getLocation();

        List<Weather> rawWeather = location.getValues();
        for (Weather day : rawWeather) {
            day.applyReadableDate();
        }
        return location;
    }

}


