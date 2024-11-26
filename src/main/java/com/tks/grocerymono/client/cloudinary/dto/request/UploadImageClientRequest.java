package com.tks.grocerymono.client.cloudinary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadImageClientRequest {
    private String pathToFile;
    private byte[] file;
}
