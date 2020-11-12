package cz.burjanova.forecast.entity;

import lombok.Data;

@Data
public class ApiUrl {

    private String baseURL;
    private String callType;
    private String keyQuery;
    private String placeQuery;
    private String place;
    private String additionalCallParameters;
    private String timeQuery;

    public ApiUrl(String callType, String place, String key) {
        this.baseURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/";
        this.callType = callType;
        this.place = place;
        this.keyQuery = "key=" + key;
        this.placeQuery = "locations=" + place;
        this.additionalCallParameters = "aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&dataElements=default";
    }

    public ApiUrl(String callType, String place, String key, String timeQuery) {
        this.baseURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/";
        this.callType = callType;
        this.place = place;
        this.keyQuery = "key=" + key;
        this.placeQuery = "locations=" + place;
        this.additionalCallParameters = "aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&dataElements=default";
        this.timeQuery = timeQuery;
    }

    public String composeForecastApiUrl() {
        return this.baseURL + callType + "?" + placeQuery + "&" + placeQuery + "&" + keyQuery + "&" + additionalCallParameters;
    }

    public String composeHistoryApiUrl() {
        return this.baseURL + callType + "?" + placeQuery + "&" + placeQuery + "&" + keyQuery + "&" + additionalCallParameters + "&" + timeQuery;
    }

}
