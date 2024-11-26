package com.tks.grocerymono.utils;


import com.tks.grocerymono.client.cloudinary.ICloudinaryClient;
import com.tks.grocerymono.client.cloudinary.dto.request.UploadImageClientRequest;
import com.tks.grocerymono.client.cloudinary.dto.response.UploadImageClientResponse;
import com.tks.grocerymono.dto.response.product.ProductResponse;
import com.tks.grocerymono.entity.Image;
import com.tks.grocerymono.entity.Product;
import com.tks.grocerymono.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProductUtil {

    private final ImageRepository imageRepository;
    private final ICloudinaryClient cloudinaryClient;

    @Value("${cloudinary.product-url.regex}")
    private String cloudinaryProductUrlRegex;

    @Value("${cloudinary.product-url.resized-regex}")
    private String cloudinaryResizedProductUrlRegex;

    @Value("${cloudinary.product-prefix-path}")
    private String cloudinaryProductPrefixPath;

    public String convertNameToCode(String name) {
        String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern
                .matcher(temp)
                .replaceAll("")
                .toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("đ", "d");
    }

    public ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse productResponse = Mapper.map(product, ProductResponse.class);
        List<Image> imageList = product.getImageList();
        List<String> imageUrlList = imageList
                .stream()
                .map(Image::getUrl)
                .toList();
        productResponse.setImageUrlList(imageUrlList);
        return productResponse;
    }

    @Async
    public void saveProductImages(Product product, List<MultipartFile> fileList) {
        String pathToFile = cloudinaryProductPrefixPath + "/" + product.getCategory().getCode();
        List<CompletableFuture<UploadImageClientResponse>> completableFutureList = fileList.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return cloudinaryClient.uploadImage(UploadImageClientRequest.builder()
                                .file(file.getBytes())
                                .pathToFile(pathToFile)
                                .build());
                    } catch (IOException exception) {
                        return null;
                    }
                }))
                .toList();
        List<UploadImageClientResponse> uploadImageClientResponseList = completableFutureList.stream()
                .map(CompletableFuture::join)
                .toList();
        List<Image> imageList = uploadImageClientResponseList.stream()
                .map(uploadImageClientResponse -> Image.builder()
                        .cloudinaryId(uploadImageClientResponse.getCloudinaryId())
                        .product(product)
                        .url(uploadImageClientResponse.getImageUrl())
                        .resizedUrl(getResizedImageUrl(uploadImageClientResponse.getImageUrl()))
                        .build())
                .toList();
        imageRepository.saveAll(imageList);
    }

    private String getResizedImageUrl(String imageUrl) {
        return imageUrl.replaceFirst(cloudinaryProductUrlRegex, cloudinaryResizedProductUrlRegex);
    }
}
