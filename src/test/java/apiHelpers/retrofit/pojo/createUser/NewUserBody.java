package apiHelpers.retrofit.pojo.createUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewUserBody {

    @JsonProperty("name")
    private String name;
    @JsonProperty("job")
    private String job;


}
