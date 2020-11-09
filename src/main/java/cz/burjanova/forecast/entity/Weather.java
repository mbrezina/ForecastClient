package cz.burjanova.forecast.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Slf4j
@Data
public class Weather {

    private String datetimeStr;
    private String readableDate;
    private Float temp;
    private Long datetime;
    private Float precip;
    private Float windchill;
    private String conditions;

    public Weather() {
    }

    public void applyReadableDate() {
        Timestamp ts = new Timestamp(this.datetime);
        SimpleDateFormat jdf = new SimpleDateFormat("d MMMMM yyyy");
        String date = jdf.format(ts);
        this.setReadableDate(date);
    }

}
