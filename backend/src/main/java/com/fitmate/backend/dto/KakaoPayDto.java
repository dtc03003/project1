package com.fitmate.backend.dto;

import lombok.*;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KakaoPayDto {
    private String cid;
//    private String cid_secret;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
//    private String item_code;
    private Integer quantity;
    private Integer total_amount;
    private Integer tax_free_amount;
//    private Integer vat_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;
//    private JsonAr available_cards;
//    private final String payment_method_type = "MONEY";
//    private String install_month = "0";
//    private JSONMap custom_json;
}
