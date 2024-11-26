package com.tks.grocerymono.client.cloudinary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadImageClientResponse {
    private String cloudinaryId;
    private String imageUrl;
}
