package apiHelpers.retrofit.pojo.getUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("last_name")
    private String lastName;


}
