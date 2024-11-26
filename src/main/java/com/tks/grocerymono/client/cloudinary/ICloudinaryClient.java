package com.tks.grocerymono.client.cloudinary;


import com.tks.grocerymono.client.cloudinary.dto.request.DeleteImageClientRequest;
import com.tks.grocerymono.client.cloudinary.dto.request.UploadImageClientRequest;
import com.tks.grocerymono.client.cloudinary.dto.response.UploadImageClientResponse;

public interface ICloudinaryClient {
    UploadImageClientResponse uploadImage(UploadImageClientRequest request);

    void deleteImage(DeleteImageClientRequest request);
}
