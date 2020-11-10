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

    public ApiUrl(String callType, String place, String key) {
        this.baseURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/";
        this.callType = callType;
        this.place = place;
        this.keyQuery = "key=" + key;
        this.placeQuery = "locations=" + place;
        this.additionalCallParameters = "aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&dataElements=default";
    }

    public String composeApiUrl() {
        String composedUrl = this.baseURL + callType + "?" + placeQuery + "&" + placeQuery + "&" + keyQuery + "&" + additionalCallParameters;
        return composedUrl;
    }
}
