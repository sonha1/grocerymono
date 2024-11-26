package com.tks.grocerymono.dto.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest extends CommandRequestData {
    private int id;
    private int categoryId;
    private String name;
    private String code;
    private Integer quantity;
    private Integer unitPrice;
    private String description;
    @JsonProperty("images")
    private List<String> imageUrlList;
    private List<MultipartFile> fileList;
}
