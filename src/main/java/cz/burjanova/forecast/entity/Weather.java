package cz.burjanova.forecast.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Slf4j
@Data
public class Weather {

    private Float temp;
    private Long datetime;
    private Float precip;
    private Float windchill;
    private String date;


    public Weather(Float temp, Long datetime, Float precip, Float windchill) {
        this.temp = temp;
        this.datetime = datetime;
        this.precip = precip;
        this.windchill = windchill;
        this.date = countDate(datetime);
    }

    public String countDate(Long datetime) {
        Timestamp ts = new Timestamp(datetime);
        SimpleDateFormat jdf = new SimpleDateFormat("d MMMMM yyyy");
        log.info(jdf.format(ts));
        return jdf.format(ts);
    }

//    public Weather(Float temp, Long datetime, Float precip, Float windchill, String date) {
//        this.temp = temp;
//        this.datetime = datetime;
//        this.precip = precip;
//        this.windchill = windchill;
//        this.date = date;
//    }

    public Weather() {
    }


}
