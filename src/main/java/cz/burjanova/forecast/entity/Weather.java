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
    private String conditionsIcon;

    public Weather() {
    }

    public void applyReadableDate() {
        Timestamp ts = new Timestamp(this.datetime);
        SimpleDateFormat jdf = new SimpleDateFormat("d MMMMM yyyy");
        String date = jdf.format(ts);
        this.setReadableDate(date);
    }

    public void attachConditionsIcon() {
        String conditions = this.getConditions();
        switch (conditions) {
            case "Clear":
                this.setConditionsIcon("Clear.png");
                break;
            case "Overcast":
                this.setConditionsIcon("Overcast.png");
                break;
            case "Partially cloudy":
                this.setConditionsIcon("Overcast.png");
                break;
            case "Rain, Overcast":
                this.setConditionsIcon("Rain.png");
                break;
            case "Rain, Partially cloudy":
                this.setConditionsIcon("Clear.png");
                break;
            case "Snow, Partially cloudy":
                this.setConditionsIcon("Clear.png");
                break;
            case "Snow, Overcast":
                this.setConditionsIcon("Clear.png");
                break;
            case "Snow":
                this.setConditionsIcon("Clear.png");
                break;
            case "Rain":
                this.setConditionsIcon("Rain.png");
                break;
        }
    }


}
