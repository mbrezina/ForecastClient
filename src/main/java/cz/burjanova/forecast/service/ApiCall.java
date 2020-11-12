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

    final String FAKE_RESPONSE_NOT_VALID_ADDRESS = "{\n    \"columns\": {\n        \"temp\": {\n            \"id\": \"temp\",\n            \"name\": \"Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"maxt\": {\n            \"id\": \"maxt\",\n            \"name\": \"Maximum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"visibility\": {\n            \"id\": \"visibility\",\n            \"name\": \"Visibility\",\n            \"type\": 2,\n            \"unit\": \"km\"\n        },\n        \"wspd\": {\n            \"id\": \"wspd\",\n            \"name\": \"Wind Speed\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"heatindex\": {\n            \"id\": \"heatindex\",\n            \"name\": \"Heat Index\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"cloudcover\": {\n            \"id\": \"cloudcover\",\n            \"name\": \"Cloud Cover\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"pop\": {\n            \"id\": \"pop\",\n            \"name\": \"Chance Precipitation (%)\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"mint\": {\n            \"id\": \"mint\",\n            \"name\": \"Minimum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"datetime\": {\n            \"id\": \"datetime\",\n            \"name\": \"Date time\",\n            \"type\": 3,\n            \"unit\": null\n        },\n        \"precip\": {\n            \"id\": \"precip\",\n            \"name\": \"Precipitation\",\n            \"type\": 2,\n            \"unit\": \"mm\"\n        },\n        \"snowdepth\": {\n            \"id\": \"snowdepth\",\n            \"name\": \"Snow Depth\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"snow\": {\n            \"id\": \"snow\",\n            \"name\": \"Snow\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"name\": {\n            \"id\": \"name\",\n            \"name\": \"Name\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"humidity\": {\n            \"id\": \"humidity\",\n            \"name\": \"Relative Humidity\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"wgust\": {\n            \"id\": \"wgust\",\n            \"name\": \"Wind Gust\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"conditions\": {\n            \"id\": \"conditions\",\n            \"name\": \"Conditions\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"windchill\": {\n            \"id\": \"windchill\",\n            \"name\": \"Wind Chill\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        }\n    },\n    \"remainingCost\": 99,\n    \"queryCost\": 1,\n    \"messages\": null,\n    \"location\": {\n        \"stationContributions\": null,\n        \"values\": [\n            {\n                \"temp\": 11.9,\n                \"maxt\": 14.3,\n                \"visibility\": 24.1,\n                \"wspd\": 7.2,\n                \"datetimeStr\": \"2020-11-11T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.3,\n                \"pop\": 0.0,\n                \"mint\": 9.4,\n                \"datetime\": 1605052800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 76.8,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.4\n            },\n            {\n                \"temp\": 12.5,\n                \"maxt\": 15.1,\n                \"visibility\": 24.1,\n                \"wspd\": 7.5,\n                \"datetimeStr\": \"2020-11-12T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 93.7,\n                \"pop\": 14.3,\n                \"mint\": 10.6,\n                \"datetime\": 1605139200000,\n                \"precip\": 1.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 66.7,\n                \"wgust\": 34.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.6\n            },\n            {\n                \"temp\": 13.6,\n                \"maxt\": 16.2,\n                \"visibility\": 24.1,\n                \"wspd\": 7.1,\n                \"datetimeStr\": \"2020-11-13T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 85.0,\n                \"pop\": 0.0,\n                \"mint\": 11.8,\n                \"datetime\": 1605225600000,\n                \"precip\": 0.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 78.6,\n                \"wgust\": 27.7,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.1\n            },\n            {\n                \"temp\": 12.6,\n                \"maxt\": 14.1,\n                \"visibility\": 17.3,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-14T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.6,\n                \"pop\": 14.3,\n                \"mint\": 11.5,\n                \"datetime\": 1605312000000,\n                \"precip\": 1.9,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 88.5,\n                \"wgust\": 22.3,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 11.3,\n                \"maxt\": 11.4,\n                \"visibility\": 10.3,\n                \"wspd\": 5.4,\n                \"datetimeStr\": \"2020-11-15T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 97.5,\n                \"pop\": 85.6,\n                \"mint\": 11.3,\n                \"datetime\": 1605398400000,\n                \"precip\": 1.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 13.7,\n                \"conditions\": \"Rain, Overcast\",\n                \"windchill\": 7.7\n            },\n            {\n                \"temp\": 10.0,\n                \"maxt\": 12.3,\n                \"visibility\": 24.1,\n                \"wspd\": 11.8,\n                \"datetimeStr\": \"2020-11-16T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 65.4,\n                \"pop\": 9.5,\n                \"mint\": 8.0,\n                \"datetime\": 1605484800000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.4,\n                \"wgust\": 40.3,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 5.8\n            },\n            {\n                \"temp\": 11.7,\n                \"maxt\": 14.1,\n                \"visibility\": 24.1,\n                \"wspd\": 14.8,\n                \"datetimeStr\": \"2020-11-17T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.8,\n                \"datetime\": 1605571200000,\n                \"precip\": 0.2,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 87.7,\n                \"wgust\": 42.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.5\n            },\n            {\n                \"temp\": 13.3,\n                \"maxt\": 15.7,\n                \"visibility\": 24.1,\n                \"wspd\": 12.5,\n                \"datetimeStr\": \"2020-11-18T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 76.4,\n                \"pop\": 23.7,\n                \"mint\": 11.4,\n                \"datetime\": 1605657600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 10.4,\n                \"maxt\": 13.3,\n                \"visibility\": 8.8,\n                \"wspd\": 13.7,\n                \"datetimeStr\": \"2020-11-19T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 53.3,\n                \"pop\": 19.0,\n                \"mint\": 7.9,\n                \"datetime\": 1605744000000,\n                \"precip\": 5.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 64.7,\n                \"wgust\": 36.0,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 6.6\n            },\n            {\n                \"temp\": 7.6,\n                \"maxt\": 9.7,\n                \"visibility\": 24.1,\n                \"wspd\": 6.6,\n                \"datetimeStr\": \"2020-11-20T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 77.8,\n                \"pop\": 23.7,\n                \"mint\": 6.1,\n                \"datetime\": 1605830400000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 60.9,\n                \"wgust\": 18.4,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.1\n            },\n            {\n                \"temp\": 6.9,\n                \"maxt\": 9.2,\n                \"visibility\": 24.1,\n                \"wspd\": 8.3,\n                \"datetimeStr\": \"2020-11-21T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 33.1,\n                \"pop\": 23.7,\n                \"mint\": 4.7,\n                \"datetime\": 1605916800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.5,\n                \"wgust\": 19.4,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 2.6\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 12.2,\n                \"visibility\": 24.1,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-22T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.0,\n                \"datetime\": 1606003200000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 12.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.2\n            },\n            {\n                \"temp\": 8.5,\n                \"maxt\": 10.5,\n                \"visibility\": 24.1,\n                \"wspd\": 7.4,\n                \"datetimeStr\": \"2020-11-23T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.0,\n                \"pop\": 19.0,\n                \"mint\": 7.3,\n                \"datetime\": 1606089600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 81.0,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.0\n            },\n            {\n                \"temp\": 8.2,\n                \"maxt\": 10.3,\n                \"visibility\": 24.1,\n                \"wspd\": 10.8,\n                \"datetimeStr\": \"2020-11-24T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 84.9,\n                \"pop\": 33.2,\n                \"mint\": 5.6,\n                \"datetime\": 1606176000000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 82.0,\n                \"wgust\": 34.9,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 3.0\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 11.6,\n                \"visibility\": 11.3,\n                \"wspd\": 13.1,\n                \"datetimeStr\": \"2020-11-25T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 94.1,\n                \"pop\": 23.7,\n                \"mint\": 8.3,\n                \"datetime\": 1606262400000,\n                \"precip\": 1.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 75.9,\n                \"wgust\": 42.5,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.4\n            },\n            {\n                \"temp\": 9.4,\n                \"maxt\": 10.7,\n                \"visibility\": 24.1,\n                \"wspd\": 17.4,\n                \"datetimeStr\": \"2020-11-26T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.9,\n                \"pop\": 19.0,\n                \"mint\": 8.4,\n                \"datetime\": 1606348800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 69.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.7\n            }\n        ],\n        \"id\": \"paris\",\n        \"address\": \"Paris, Île-de-France, France\",\n        \"name\": \"not a valid place\",\n        \"index\": 0,\n        \"latitude\": 48.8572,\n        \"longitude\": 2.34141,\n        \"distance\": 0.0,\n        \"time\": 0.0,\n        \"tz\": \"Europe/Paris\",\n        \"currentConditions\": {\n            \"wdir\": null,\n            \"temp\": 9.0,\n            \"sunrise\": \"2020-11-11T07:53:58+01:00\",\n            \"visibility\": 0.0,\n            \"wspd\": 5.4,\n            \"icon\": \"cloudy\",\n            \"stations\": \"\",\n            \"heatindex\": null,\n            \"cloudcover\": 100.0,\n            \"datetime\": \"2020-11-11T07:00:00+01:00\",\n            \"precip\": null,\n            \"moonphase\": 0.9,\n            \"snowdepth\": null,\n            \"sealevelpressure\": null,\n            \"dew\": 9.0,\n            \"sunset\": \"2020-11-11T17:14:50+01:00\",\n            \"humidity\": 100.0,\n            \"wgust\": null,\n            \"windchill\": 8.5\n        },\n        \"alerts\": null\n    }\n}";
    final String FAKE_RESPONSE_NO_PLACE_FILLED = "{\n    \"columns\": {\n        \"temp\": {\n            \"id\": \"temp\",\n            \"name\": \"Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"maxt\": {\n            \"id\": \"maxt\",\n            \"name\": \"Maximum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"visibility\": {\n            \"id\": \"visibility\",\n            \"name\": \"Visibility\",\n            \"type\": 2,\n            \"unit\": \"km\"\n        },\n        \"wspd\": {\n            \"id\": \"wspd\",\n            \"name\": \"Wind Speed\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"heatindex\": {\n            \"id\": \"heatindex\",\n            \"name\": \"Heat Index\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"cloudcover\": {\n            \"id\": \"cloudcover\",\n            \"name\": \"Cloud Cover\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"pop\": {\n            \"id\": \"pop\",\n            \"name\": \"Chance Precipitation (%)\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"mint\": {\n            \"id\": \"mint\",\n            \"name\": \"Minimum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"datetime\": {\n            \"id\": \"datetime\",\n            \"name\": \"Date time\",\n            \"type\": 3,\n            \"unit\": null\n        },\n        \"precip\": {\n            \"id\": \"precip\",\n            \"name\": \"Precipitation\",\n            \"type\": 2,\n            \"unit\": \"mm\"\n        },\n        \"snowdepth\": {\n            \"id\": \"snowdepth\",\n            \"name\": \"Snow Depth\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"snow\": {\n            \"id\": \"snow\",\n            \"name\": \"Snow\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"name\": {\n            \"id\": \"name\",\n            \"name\": \"Name\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"humidity\": {\n            \"id\": \"humidity\",\n            \"name\": \"Relative Humidity\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"wgust\": {\n            \"id\": \"wgust\",\n            \"name\": \"Wind Gust\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"conditions\": {\n            \"id\": \"conditions\",\n            \"name\": \"Conditions\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"windchill\": {\n            \"id\": \"windchill\",\n            \"name\": \"Wind Chill\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        }\n    },\n    \"remainingCost\": 99,\n    \"queryCost\": 1,\n    \"messages\": null,\n    \"location\": {\n        \"stationContributions\": null,\n        \"values\": [\n            {\n                \"temp\": 11.9,\n                \"maxt\": 14.3,\n                \"visibility\": 24.1,\n                \"wspd\": 7.2,\n                \"datetimeStr\": \"2020-11-11T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.3,\n                \"pop\": 0.0,\n                \"mint\": 9.4,\n                \"datetime\": 1605052800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 76.8,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.4\n            },\n            {\n                \"temp\": 12.5,\n                \"maxt\": 15.1,\n                \"visibility\": 24.1,\n                \"wspd\": 7.5,\n                \"datetimeStr\": \"2020-11-12T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 93.7,\n                \"pop\": 14.3,\n                \"mint\": 10.6,\n                \"datetime\": 1605139200000,\n                \"precip\": 1.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 66.7,\n                \"wgust\": 34.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.6\n            },\n            {\n                \"temp\": 13.6,\n                \"maxt\": 16.2,\n                \"visibility\": 24.1,\n                \"wspd\": 7.1,\n                \"datetimeStr\": \"2020-11-13T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 85.0,\n                \"pop\": 0.0,\n                \"mint\": 11.8,\n                \"datetime\": 1605225600000,\n                \"precip\": 0.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 78.6,\n                \"wgust\": 27.7,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.1\n            },\n            {\n                \"temp\": 12.6,\n                \"maxt\": 14.1,\n                \"visibility\": 17.3,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-14T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.6,\n                \"pop\": 14.3,\n                \"mint\": 11.5,\n                \"datetime\": 1605312000000,\n                \"precip\": 1.9,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 88.5,\n                \"wgust\": 22.3,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 11.3,\n                \"maxt\": 11.4,\n                \"visibility\": 10.3,\n                \"wspd\": 5.4,\n                \"datetimeStr\": \"2020-11-15T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 97.5,\n                \"pop\": 85.6,\n                \"mint\": 11.3,\n                \"datetime\": 1605398400000,\n                \"precip\": 1.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 13.7,\n                \"conditions\": \"Rain, Overcast\",\n                \"windchill\": 7.7\n            },\n            {\n                \"temp\": 10.0,\n                \"maxt\": 12.3,\n                \"visibility\": 24.1,\n                \"wspd\": 11.8,\n                \"datetimeStr\": \"2020-11-16T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 65.4,\n                \"pop\": 9.5,\n                \"mint\": 8.0,\n                \"datetime\": 1605484800000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.4,\n                \"wgust\": 40.3,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 5.8\n            },\n            {\n                \"temp\": 11.7,\n                \"maxt\": 14.1,\n                \"visibility\": 24.1,\n                \"wspd\": 14.8,\n                \"datetimeStr\": \"2020-11-17T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.8,\n                \"datetime\": 1605571200000,\n                \"precip\": 0.2,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 87.7,\n                \"wgust\": 42.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.5\n            },\n            {\n                \"temp\": 13.3,\n                \"maxt\": 15.7,\n                \"visibility\": 24.1,\n                \"wspd\": 12.5,\n                \"datetimeStr\": \"2020-11-18T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 76.4,\n                \"pop\": 23.7,\n                \"mint\": 11.4,\n                \"datetime\": 1605657600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 10.4,\n                \"maxt\": 13.3,\n                \"visibility\": 8.8,\n                \"wspd\": 13.7,\n                \"datetimeStr\": \"2020-11-19T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 53.3,\n                \"pop\": 19.0,\n                \"mint\": 7.9,\n                \"datetime\": 1605744000000,\n                \"precip\": 5.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 64.7,\n                \"wgust\": 36.0,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 6.6\n            },\n            {\n                \"temp\": 7.6,\n                \"maxt\": 9.7,\n                \"visibility\": 24.1,\n                \"wspd\": 6.6,\n                \"datetimeStr\": \"2020-11-20T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 77.8,\n                \"pop\": 23.7,\n                \"mint\": 6.1,\n                \"datetime\": 1605830400000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 60.9,\n                \"wgust\": 18.4,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.1\n            },\n            {\n                \"temp\": 6.9,\n                \"maxt\": 9.2,\n                \"visibility\": 24.1,\n                \"wspd\": 8.3,\n                \"datetimeStr\": \"2020-11-21T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 33.1,\n                \"pop\": 23.7,\n                \"mint\": 4.7,\n                \"datetime\": 1605916800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.5,\n                \"wgust\": 19.4,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 2.6\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 12.2,\n                \"visibility\": 24.1,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-22T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.0,\n                \"datetime\": 1606003200000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 12.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.2\n            },\n            {\n                \"temp\": 8.5,\n                \"maxt\": 10.5,\n                \"visibility\": 24.1,\n                \"wspd\": 7.4,\n                \"datetimeStr\": \"2020-11-23T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.0,\n                \"pop\": 19.0,\n                \"mint\": 7.3,\n                \"datetime\": 1606089600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 81.0,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.0\n            },\n            {\n                \"temp\": 8.2,\n                \"maxt\": 10.3,\n                \"visibility\": 24.1,\n                \"wspd\": 10.8,\n                \"datetimeStr\": \"2020-11-24T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 84.9,\n                \"pop\": 33.2,\n                \"mint\": 5.6,\n                \"datetime\": 1606176000000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 82.0,\n                \"wgust\": 34.9,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 3.0\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 11.6,\n                \"visibility\": 11.3,\n                \"wspd\": 13.1,\n                \"datetimeStr\": \"2020-11-25T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 94.1,\n                \"pop\": 23.7,\n                \"mint\": 8.3,\n                \"datetime\": 1606262400000,\n                \"precip\": 1.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 75.9,\n                \"wgust\": 42.5,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.4\n            },\n            {\n                \"temp\": 9.4,\n                \"maxt\": 10.7,\n                \"visibility\": 24.1,\n                \"wspd\": 17.4,\n                \"datetimeStr\": \"2020-11-26T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.9,\n                \"pop\": 19.0,\n                \"mint\": 8.4,\n                \"datetime\": 1606348800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 69.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.7\n            }\n        ],\n        \"id\": \"paris\",\n        \"address\": \"Paris, Île-de-France, France\",\n        \"name\": \"no location inserted\",\n        \"index\": 0,\n        \"latitude\": 48.8572,\n        \"longitude\": 2.34141,\n        \"distance\": 0.0,\n        \"time\": 0.0,\n        \"tz\": \"Europe/Paris\",\n        \"currentConditions\": {\n            \"wdir\": null,\n            \"temp\": 9.0,\n            \"sunrise\": \"2020-11-11T07:53:58+01:00\",\n            \"visibility\": 0.0,\n            \"wspd\": 5.4,\n            \"icon\": \"cloudy\",\n            \"stations\": \"\",\n            \"heatindex\": null,\n            \"cloudcover\": 100.0,\n            \"datetime\": \"2020-11-11T07:00:00+01:00\",\n            \"precip\": null,\n            \"moonphase\": 0.9,\n            \"snowdepth\": null,\n            \"sealevelpressure\": null,\n            \"dew\": 9.0,\n            \"sunset\": \"2020-11-11T17:14:50+01:00\",\n            \"humidity\": 100.0,\n            \"wgust\": null,\n            \"windchill\": 8.5\n        },\n        \"alerts\": null\n    }\n}";
    final String FAKE_RESPONSE_NO_API_KEY = "{\n    \"columns\": {\n        \"temp\": {\n            \"id\": \"temp\",\n            \"name\": \"Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"maxt\": {\n            \"id\": \"maxt\",\n            \"name\": \"Maximum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"visibility\": {\n            \"id\": \"visibility\",\n            \"name\": \"Visibility\",\n            \"type\": 2,\n            \"unit\": \"km\"\n        },\n        \"wspd\": {\n            \"id\": \"wspd\",\n            \"name\": \"Wind Speed\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"heatindex\": {\n            \"id\": \"heatindex\",\n            \"name\": \"Heat Index\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"cloudcover\": {\n            \"id\": \"cloudcover\",\n            \"name\": \"Cloud Cover\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"pop\": {\n            \"id\": \"pop\",\n            \"name\": \"Chance Precipitation (%)\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"mint\": {\n            \"id\": \"mint\",\n            \"name\": \"Minimum Temperature\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        },\n        \"datetime\": {\n            \"id\": \"datetime\",\n            \"name\": \"Date time\",\n            \"type\": 3,\n            \"unit\": null\n        },\n        \"precip\": {\n            \"id\": \"precip\",\n            \"name\": \"Precipitation\",\n            \"type\": 2,\n            \"unit\": \"mm\"\n        },\n        \"snowdepth\": {\n            \"id\": \"snowdepth\",\n            \"name\": \"Snow Depth\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"snow\": {\n            \"id\": \"snow\",\n            \"name\": \"Snow\",\n            \"type\": 2,\n            \"unit\": \"cm\"\n        },\n        \"name\": {\n            \"id\": \"name\",\n            \"name\": \"Name\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"humidity\": {\n            \"id\": \"humidity\",\n            \"name\": \"Relative Humidity\",\n            \"type\": 2,\n            \"unit\": null\n        },\n        \"wgust\": {\n            \"id\": \"wgust\",\n            \"name\": \"Wind Gust\",\n            \"type\": 2,\n            \"unit\": \"kph\"\n        },\n        \"conditions\": {\n            \"id\": \"conditions\",\n            \"name\": \"Conditions\",\n            \"type\": 1,\n            \"unit\": null\n        },\n        \"windchill\": {\n            \"id\": \"windchill\",\n            \"name\": \"Wind Chill\",\n            \"type\": 2,\n            \"unit\": \"degC\"\n        }\n    },\n    \"remainingCost\": 99,\n    \"queryCost\": 1,\n    \"messages\": null,\n    \"location\": {\n        \"stationContributions\": null,\n        \"values\": [\n            {\n                \"temp\": 11.9,\n                \"maxt\": 14.3,\n                \"visibility\": 24.1,\n                \"wspd\": 7.2,\n                \"datetimeStr\": \"2020-11-11T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.3,\n                \"pop\": 0.0,\n                \"mint\": 9.4,\n                \"datetime\": 1605052800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 76.8,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.4\n            },\n            {\n                \"temp\": 12.5,\n                \"maxt\": 15.1,\n                \"visibility\": 24.1,\n                \"wspd\": 7.5,\n                \"datetimeStr\": \"2020-11-12T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 93.7,\n                \"pop\": 14.3,\n                \"mint\": 10.6,\n                \"datetime\": 1605139200000,\n                \"precip\": 1.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 66.7,\n                \"wgust\": 34.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.6\n            },\n            {\n                \"temp\": 13.6,\n                \"maxt\": 16.2,\n                \"visibility\": 24.1,\n                \"wspd\": 7.1,\n                \"datetimeStr\": \"2020-11-13T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 85.0,\n                \"pop\": 0.0,\n                \"mint\": 11.8,\n                \"datetime\": 1605225600000,\n                \"precip\": 0.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 78.6,\n                \"wgust\": 27.7,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.1\n            },\n            {\n                \"temp\": 12.6,\n                \"maxt\": 14.1,\n                \"visibility\": 17.3,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-14T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.6,\n                \"pop\": 14.3,\n                \"mint\": 11.5,\n                \"datetime\": 1605312000000,\n                \"precip\": 1.9,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 88.5,\n                \"wgust\": 22.3,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 11.3,\n                \"maxt\": 11.4,\n                \"visibility\": 10.3,\n                \"wspd\": 5.4,\n                \"datetimeStr\": \"2020-11-15T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 97.5,\n                \"pop\": 85.6,\n                \"mint\": 11.3,\n                \"datetime\": 1605398400000,\n                \"precip\": 1.6,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 13.7,\n                \"conditions\": \"Rain, Overcast\",\n                \"windchill\": 7.7\n            },\n            {\n                \"temp\": 10.0,\n                \"maxt\": 12.3,\n                \"visibility\": 24.1,\n                \"wspd\": 11.8,\n                \"datetimeStr\": \"2020-11-16T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 65.4,\n                \"pop\": 9.5,\n                \"mint\": 8.0,\n                \"datetime\": 1605484800000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.4,\n                \"wgust\": 40.3,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 5.8\n            },\n            {\n                \"temp\": 11.7,\n                \"maxt\": 14.1,\n                \"visibility\": 24.1,\n                \"wspd\": 14.8,\n                \"datetimeStr\": \"2020-11-17T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.8,\n                \"datetime\": 1605571200000,\n                \"precip\": 0.2,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 87.7,\n                \"wgust\": 42.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 7.5\n            },\n            {\n                \"temp\": 13.3,\n                \"maxt\": 15.7,\n                \"visibility\": 24.1,\n                \"wspd\": 12.5,\n                \"datetimeStr\": \"2020-11-18T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 76.4,\n                \"pop\": 23.7,\n                \"mint\": 11.4,\n                \"datetime\": 1605657600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": null\n            },\n            {\n                \"temp\": 10.4,\n                \"maxt\": 13.3,\n                \"visibility\": 8.8,\n                \"wspd\": 13.7,\n                \"datetimeStr\": \"2020-11-19T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 53.3,\n                \"pop\": 19.0,\n                \"mint\": 7.9,\n                \"datetime\": 1605744000000,\n                \"precip\": 5.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 64.7,\n                \"wgust\": 36.0,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 6.6\n            },\n            {\n                \"temp\": 7.6,\n                \"maxt\": 9.7,\n                \"visibility\": 24.1,\n                \"wspd\": 6.6,\n                \"datetimeStr\": \"2020-11-20T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 77.8,\n                \"pop\": 23.7,\n                \"mint\": 6.1,\n                \"datetime\": 1605830400000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 60.9,\n                \"wgust\": 18.4,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.1\n            },\n            {\n                \"temp\": 6.9,\n                \"maxt\": 9.2,\n                \"visibility\": 24.1,\n                \"wspd\": 8.3,\n                \"datetimeStr\": \"2020-11-21T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 33.1,\n                \"pop\": 23.7,\n                \"mint\": 4.7,\n                \"datetime\": 1605916800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 73.5,\n                \"wgust\": 19.4,\n                \"conditions\": \"Partially cloudy\",\n                \"windchill\": 2.6\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 12.2,\n                \"visibility\": 24.1,\n                \"wspd\": 4.4,\n                \"datetimeStr\": \"2020-11-22T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 98.9,\n                \"pop\": 23.7,\n                \"mint\": 9.0,\n                \"datetime\": 1606003200000,\n                \"precip\": 0.1,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 77.0,\n                \"wgust\": 12.6,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 8.2\n            },\n            {\n                \"temp\": 8.5,\n                \"maxt\": 10.5,\n                \"visibility\": 24.1,\n                \"wspd\": 7.4,\n                \"datetimeStr\": \"2020-11-23T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.0,\n                \"pop\": 19.0,\n                \"mint\": 7.3,\n                \"datetime\": 1606089600000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 81.0,\n                \"wgust\": 24.1,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.0\n            },\n            {\n                \"temp\": 8.2,\n                \"maxt\": 10.3,\n                \"visibility\": 24.1,\n                \"wspd\": 10.8,\n                \"datetimeStr\": \"2020-11-24T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 84.9,\n                \"pop\": 33.2,\n                \"mint\": 5.6,\n                \"datetime\": 1606176000000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 82.0,\n                \"wgust\": 34.9,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 3.0\n            },\n            {\n                \"temp\": 10.3,\n                \"maxt\": 11.6,\n                \"visibility\": 11.3,\n                \"wspd\": 13.1,\n                \"datetimeStr\": \"2020-11-25T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 94.1,\n                \"pop\": 23.7,\n                \"mint\": 8.3,\n                \"datetime\": 1606262400000,\n                \"precip\": 1.5,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 75.9,\n                \"wgust\": 42.5,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 6.4\n            },\n            {\n                \"temp\": 9.4,\n                \"maxt\": 10.7,\n                \"visibility\": 24.1,\n                \"wspd\": 17.4,\n                \"datetimeStr\": \"2020-11-26T00:00:00+01:00\",\n                \"heatindex\": null,\n                \"cloudcover\": 99.9,\n                \"pop\": 19.0,\n                \"mint\": 8.4,\n                \"datetime\": 1606348800000,\n                \"precip\": 0.0,\n                \"snowdepth\": 0.0,\n                \"snow\": 0.0,\n                \"humidity\": 69.4,\n                \"wgust\": 38.2,\n                \"conditions\": \"Overcast\",\n                \"windchill\": 5.7\n            }\n        ],\n        \"id\": \"paris\",\n        \"address\": \"Paris, Île-de-France, France\",\n        \"name\": \"the API key is not valid\",\n        \"index\": 0,\n        \"latitude\": 48.8572,\n        \"longitude\": 2.34141,\n        \"distance\": 0.0,\n        \"time\": 0.0,\n        \"tz\": \"Europe/Paris\",\n        \"currentConditions\": {\n            \"wdir\": null,\n            \"temp\": 9.0,\n            \"sunrise\": \"2020-11-11T07:53:58+01:00\",\n            \"visibility\": 0.0,\n            \"wspd\": 5.4,\n            \"icon\": \"cloudy\",\n            \"stations\": \"\",\n            \"heatindex\": null,\n            \"cloudcover\": 100.0,\n            \"datetime\": \"2020-11-11T07:00:00+01:00\",\n            \"precip\": null,\n            \"moonphase\": 0.9,\n            \"snowdepth\": null,\n            \"sealevelpressure\": null,\n            \"dew\": 9.0,\n            \"sunset\": \"2020-11-11T17:14:50+01:00\",\n            \"humidity\": 100.0,\n            \"wgust\": null,\n            \"windchill\": 8.5\n        },\n        \"alerts\": null\n    }\n}";


    private final OkHttpClient client = new OkHttpClient();

    public Location doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();


//        if (stringResponse.equals("{\"errorCode\":999,\"sessionId\":\"\",\"executionTime\":-1,\"message\":\"No rows were returned. Please verify the location and dates requested\"}")) {
//            stringResponse = FAKE_RESPONSE_NOT_VALID_ADDRESS;
//        } else if (stringResponse.equals("{\"errorCode\": 999,\"sessionId\": \"\",\"executionTime\": -1,\"message\": \"Must specify either location list or input datasource\"}")) {
//            stringResponse = FAKE_RESPONSE_NO_PLACE_FILLED;
//        } else if (stringResponse.equals("{\"errorCode\": 999,\"sessionId\": \"\",\"executionTime\": -1,\"message\": \"Must specify either location list or input datasource\"}")) {
//            stringResponse = FAKE_RESPONSE_NO_API_KEY;
//        })

//        String stringResponse = "{\n" +
//            "    \"columns\": {\n" +
//            "        \"temp\": {\n" +
//            "            \"id\": \"temp\",\n" +
//            "            \"name\": \"Temperature\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"degC\"\n" +
//            "        },\n" +
//            "        \"maxt\": {\n" +
//            "            \"id\": \"maxt\",\n" +
//            "            \"name\": \"Maximum Temperature\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"degC\"\n" +
//            "        },\n" +
//            "        \"visibility\": {\n" +
//            "            \"id\": \"visibility\",\n" +
//            "            \"name\": \"Visibility\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"km\"\n" +
//            "        },\n" +
//            "        \"wspd\": {\n" +
//            "            \"id\": \"wspd\",\n" +
//            "            \"name\": \"Wind Speed\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"kph\"\n" +
//            "        },\n" +
//            "        \"heatindex\": {\n" +
//            "            \"id\": \"heatindex\",\n" +
//            "            \"name\": \"Heat Index\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"degC\"\n" +
//            "        },\n" +
//            "        \"cloudcover\": {\n" +
//            "            \"id\": \"cloudcover\",\n" +
//            "            \"name\": \"Cloud Cover\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"pop\": {\n" +
//            "            \"id\": \"pop\",\n" +
//            "            \"name\": \"Chance Precipitation (%)\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"mint\": {\n" +
//            "            \"id\": \"mint\",\n" +
//            "            \"name\": \"Minimum Temperature\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"degC\"\n" +
//            "        },\n" +
//            "        \"datetime\": {\n" +
//            "            \"id\": \"datetime\",\n" +
//            "            \"name\": \"Date time\",\n" +
//            "            \"type\": 3,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"precip\": {\n" +
//            "            \"id\": \"precip\",\n" +
//            "            \"name\": \"Precipitation\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"mm\"\n" +
//            "        },\n" +
//            "        \"snowdepth\": {\n" +
//            "            \"id\": \"snowdepth\",\n" +
//            "            \"name\": \"Snow Depth\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"cm\"\n" +
//            "        },\n" +
//            "        \"snow\": {\n" +
//            "            \"id\": \"snow\",\n" +
//            "            \"name\": \"Snow\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"cm\"\n" +
//            "        },\n" +
//            "        \"name\": {\n" +
//            "            \"id\": \"name\",\n" +
//            "            \"name\": \"Name\",\n" +
//            "            \"type\": 1,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"humidity\": {\n" +
//            "            \"id\": \"humidity\",\n" +
//            "            \"name\": \"Relative Humidity\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"wgust\": {\n" +
//            "            \"id\": \"wgust\",\n" +
//            "            \"name\": \"Wind Gust\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"kph\"\n" +
//            "        },\n" +
//            "        \"conditions\": {\n" +
//            "            \"id\": \"conditions\",\n" +
//            "            \"name\": \"Conditions\",\n" +
//            "            \"type\": 1,\n" +
//            "            \"unit\": null\n" +
//            "        },\n" +
//            "        \"windchill\": {\n" +
//            "            \"id\": \"windchill\",\n" +
//            "            \"name\": \"Wind Chill\",\n" +
//            "            \"type\": 2,\n" +
//            "            \"unit\": \"degC\"\n" +
//            "        }\n" +
//            "    },\n" +
//            "    \"remainingCost\": 84,\n" +
//            "    \"queryCost\": 1,\n" +
//            "    \"messages\": null,\n" +
//            "    \"location\": {\n" +
//            "        \"stationContributions\": null,\n" +
//            "        \"values\": [\n" +
//            "            {\n" +
//            "                \"temp\": -22.2,\n" +
//            "                \"maxt\": -20.5,\n" +
//            "                \"visibility\": 1.4,\n" +
//            "                \"wspd\": 12.4,\n" +
//            "                \"datetimeStr\": \"2020-11-10T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 100,\n" +
//            "                \"pop\": 0,\n" +
//            "                \"mint\": -23.7,\n" +
//            "                \"datetime\": 1604966400000,\n" +
//            "                \"precip\": 0.2,\n" +
//            "                \"snowdepth\": 116.5,\n" +
//            "                \"snow\": 232.8,\n" +
//            "                \"humidity\": 93.7,\n" +
//            "                \"wgust\": 18.7,\n" +
//            "                \"conditions\": \"Snow, Overcast\",\n" +
//            "                \"windchill\": -33.7\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -20.8,\n" +
//            "                \"maxt\": -18.6,\n" +
//            "                \"visibility\": 0.4,\n" +
//            "                \"wspd\": 10.8,\n" +
//            "                \"datetimeStr\": \"2020-11-11T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 99.9,\n" +
//            "                \"pop\": 42.9,\n" +
//            "                \"mint\": -23.1,\n" +
//            "                \"datetime\": 1605052800000,\n" +
//            "                \"precip\": 0.7,\n" +
//            "                \"snowdepth\": 114.2,\n" +
//            "                \"snow\": 455.6,\n" +
//            "                \"humidity\": 94.4,\n" +
//            "                \"wgust\": 23.8,\n" +
//            "                \"conditions\": \"Snow, Overcast\",\n" +
//            "                \"windchill\": -32.8\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -17.8,\n" +
//            "                \"maxt\": -14.6,\n" +
//            "                \"visibility\": 0.2,\n" +
//            "                \"wspd\": 20.4,\n" +
//            "                \"datetimeStr\": \"2020-11-12T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 99.9,\n" +
//            "                \"pop\": 52.4,\n" +
//            "                \"mint\": -21,\n" +
//            "                \"datetime\": 1605139200000,\n" +
//            "                \"precip\": 0.8,\n" +
//            "                \"snowdepth\": 111.4,\n" +
//            "                \"snow\": 333,\n" +
//            "                \"humidity\": 91.4,\n" +
//            "                \"wgust\": 41.8,\n" +
//            "                \"conditions\": \"Snow, Overcast\",\n" +
//            "                \"windchill\": -29.1\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -12.1,\n" +
//            "                \"maxt\": -10.8,\n" +
//            "                \"visibility\": 0.1,\n" +
//            "                \"wspd\": 16.7,\n" +
//            "                \"datetimeStr\": \"2020-11-13T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 100,\n" +
//            "                \"pop\": 71.4,\n" +
//            "                \"mint\": -14.4,\n" +
//            "                \"datetime\": 1605225600000,\n" +
//            "                \"precip\": 3,\n" +
//            "                \"snowdepth\": 109,\n" +
//            "                \"snow\": 329,\n" +
//            "                \"humidity\": 94.3,\n" +
//            "                \"wgust\": 27,\n" +
//            "                \"conditions\": \"Snow, Overcast\",\n" +
//            "                \"windchill\": -22.3\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -14.3,\n" +
//            "                \"maxt\": -12.2,\n" +
//            "                \"visibility\": 0.1,\n" +
//            "                \"wspd\": 16.7,\n" +
//            "                \"datetimeStr\": \"2020-11-14T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 99.8,\n" +
//            "                \"pop\": 71.4,\n" +
//            "                \"mint\": -20.7,\n" +
//            "                \"datetime\": 1605312000000,\n" +
//            "                \"precip\": 1,\n" +
//            "                \"snowdepth\": 107.5,\n" +
//            "                \"snow\": 320.9,\n" +
//            "                \"humidity\": 89.7,\n" +
//            "                \"wgust\": 24.5,\n" +
//            "                \"conditions\": \"Snow, Overcast\",\n" +
//            "                \"windchill\": -31.9\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -18.1,\n" +
//            "                \"maxt\": -15.1,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 25.6,\n" +
//            "                \"datetimeStr\": \"2020-11-15T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 65.4,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -20.9,\n" +
//            "                \"datetime\": 1605398400000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 104.2,\n" +
//            "                \"snow\": 311.7,\n" +
//            "                \"humidity\": 76.2,\n" +
//            "                \"wgust\": 43.6,\n" +
//            "                \"conditions\": \"Snow, Partially cloudy\",\n" +
//            "                \"windchill\": -33.6\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -17.9,\n" +
//            "                \"maxt\": -14.8,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 27.4,\n" +
//            "                \"datetimeStr\": \"2020-11-16T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 37.9,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -21.4,\n" +
//            "                \"datetime\": 1605484800000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 101.7,\n" +
//            "                \"snow\": 202.8,\n" +
//            "                \"humidity\": 72.2,\n" +
//            "                \"wgust\": 48.2,\n" +
//            "                \"conditions\": \"Snow, Partially cloudy\",\n" +
//            "                \"windchill\": -33.8\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -19,\n" +
//            "                \"maxt\": -17.1,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 46.1,\n" +
//            "                \"datetimeStr\": \"2020-11-17T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 26.4,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -22.3,\n" +
//            "                \"datetime\": 1605571200000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.4,\n" +
//            "                \"snow\": 299,\n" +
//            "                \"humidity\": 61.2,\n" +
//            "                \"wgust\": 88.6,\n" +
//            "                \"conditions\": \"Snow, Partially cloudy\",\n" +
//            "                \"windchill\": -39.1\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -22.7,\n" +
//            "                \"maxt\": -19.8,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 43.7,\n" +
//            "                \"datetimeStr\": \"2020-11-18T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 0,\n" +
//            "                \"pop\": 9.5,\n" +
//            "                \"mint\": -26.6,\n" +
//            "                \"datetime\": 1605657600000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99,\n" +
//            "                \"snow\": 199,\n" +
//            "                \"humidity\": 66.7,\n" +
//            "                \"wgust\": 85.7,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -42.6\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -24.3,\n" +
//            "                \"maxt\": -21.1,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 28.7,\n" +
//            "                \"datetimeStr\": \"2020-11-19T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 0,\n" +
//            "                \"pop\": 9.5,\n" +
//            "                \"mint\": -28.2,\n" +
//            "                \"datetime\": 1605744000000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.3,\n" +
//            "                \"snow\": 199,\n" +
//            "                \"humidity\": 71.5,\n" +
//            "                \"wgust\": 58,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -42.7\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -27.2,\n" +
//            "                \"maxt\": -23.8,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 25.6,\n" +
//            "                \"datetimeStr\": \"2020-11-20T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 1.1,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -32.5,\n" +
//            "                \"datetime\": 1605830400000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.4,\n" +
//            "                \"snow\": 100,\n" +
//            "                \"humidity\": 75.2,\n" +
//            "                \"wgust\": 46.1,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -47.8\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -30.4,\n" +
//            "                \"maxt\": -27.4,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 24.1,\n" +
//            "                \"datetimeStr\": \"2020-11-21T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 57.5,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -33.9,\n" +
//            "                \"datetime\": 1605916800000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.5,\n" +
//            "                \"snow\": 100,\n" +
//            "                \"humidity\": 77,\n" +
//            "                \"wgust\": 43.9,\n" +
//            "                \"conditions\": \"Snow, Partially cloudy\",\n" +
//            "                \"windchill\": -49\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -29.3,\n" +
//            "                \"maxt\": -26.1,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 25.2,\n" +
//            "                \"datetimeStr\": \"2020-11-22T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 5.1,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -32.7,\n" +
//            "                \"datetime\": 1606003200000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.5,\n" +
//            "                \"snow\": 100,\n" +
//            "                \"humidity\": 78.1,\n" +
//            "                \"wgust\": 48.2,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -47.9\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -27.6,\n" +
//            "                \"maxt\": -24.5,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 33.6,\n" +
//            "                \"datetimeStr\": \"2020-11-23T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 0,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -30.6,\n" +
//            "                \"datetime\": 1606089600000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.5,\n" +
//            "                \"snow\": 100,\n" +
//            "                \"humidity\": 71,\n" +
//            "                \"wgust\": 56.5,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -47.4\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -28.4,\n" +
//            "                \"maxt\": -25.7,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 35.7,\n" +
//            "                \"datetimeStr\": \"2020-11-24T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 0,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -31.6,\n" +
//            "                \"datetime\": 1606176000000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.5,\n" +
//            "                \"snow\": 100,\n" +
//            "                \"humidity\": 70.5,\n" +
//            "                \"wgust\": 60.1,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -48.5\n" +
//            "            },\n" +
//            "            {\n" +
//            "                \"temp\": -28.5,\n" +
//            "                \"maxt\": -26.3,\n" +
//            "                \"visibility\": 24.1,\n" +
//            "                \"wspd\": 31.2,\n" +
//            "                \"datetimeStr\": \"2020-11-25T00:00:00Z\",\n" +
//            "                \"heatindex\": null,\n" +
//            "                \"cloudcover\": 0,\n" +
//            "                \"pop\": 4.8,\n" +
//            "                \"mint\": -31.1,\n" +
//            "                \"datetime\": 1606262400000,\n" +
//            "                \"precip\": 0,\n" +
//            "                \"snowdepth\": 99.4,\n" +
//            "                \"snow\": 99,\n" +
//            "                \"humidity\": 73.6,\n" +
//            "                \"wgust\": 53.3,\n" +
//            "                \"conditions\": \"Snow\",\n" +
//            "                \"windchill\": -48.1\n" +
//            "            }\n" +
//            "        ],\n" +
//            "        \"id\": \"antarctica\",\n" +
//            "        \"address\": \"Antarctica\",\n" +
//            "        \"name\": \"antarctica\",\n" +
//            "        \"index\": 0,\n" +
//            "        \"latitude\": -76.6106,\n" +
//            "        \"longitude\": 66.7926,\n" +
//            "        \"distance\": 0,\n" +
//            "        \"time\": 0,\n" +
//            "        \"tz\": \"UTC\",\n" +
//            "        \"currentConditions\": {},\n" +
//            "        \"alerts\": null\n" +
//            "    }\n" +
//            "}";


        if (stringResponse.contains("No rows were returned")) {
            stringResponse = FAKE_RESPONSE_NOT_VALID_ADDRESS;
        } else if (stringResponse.contains("Must specify either location list or input datasource")) {
            stringResponse = FAKE_RESPONSE_NO_PLACE_FILLED;
        } else if (stringResponse.contains("Bad api key")) {
            stringResponse = FAKE_RESPONSE_NO_API_KEY;
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

    public Location parseResponse(String resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        cz.burjanova.forecast.entity.Response rawResponse = objectMapper.readValue(resp, cz.burjanova.forecast.entity.Response.class);
        Location location = rawResponse.getLocation();
        List<Weather> rawWeather = location.getValues();
        for (Weather day : rawWeather) {
            day.applyReadableDate();
        }
        Location locationDetails = rawResponse.getLocation();
        return locationDetails;
    }


}


