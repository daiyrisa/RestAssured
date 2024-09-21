package entities;

import lombok.Data;

@Data
public class RequestBody {
    private String category_title;
    private String category_description;
    private boolean flag;
    private String email;
    private String company_name;
    private String seller_name;
    private String phone_number;
    private String address;
    private String password;

}
