package apiHelpers.retrofit.pojo.getUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("support")
    private Support support;

}
