package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.junit.Test;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    private int category_id;
    private String email;
    private String category_title;
    private int seller_id;
    List<CustomResponse> responses;
    private String seller_name;




}
