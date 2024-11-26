package com.tks.grocerymono.dto.request.category;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest extends CommandRequestData {
    private int id;
    private String name;
    private String code;
    private String imageUrl;
    private MultipartFile multipartFile;
}
