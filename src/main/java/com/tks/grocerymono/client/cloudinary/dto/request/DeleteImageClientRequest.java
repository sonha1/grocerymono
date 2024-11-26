package com.tks.grocerymono.client.cloudinary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteImageClientRequest {
    private List<String> cloudinaryIdList;
}
