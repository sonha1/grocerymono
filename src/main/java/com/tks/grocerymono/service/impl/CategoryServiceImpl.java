package com.tks.grocerymono.service.impl;

import com.tks.grocerymono.client.cloudinary.CloudinaryClient;
import com.tks.grocerymono.client.cloudinary.dto.request.DeleteImageClientRequest;
import com.tks.grocerymono.client.cloudinary.dto.request.UploadImageClientRequest;
import com.tks.grocerymono.client.cloudinary.dto.response.UploadImageClientResponse;
import com.tks.grocerymono.dto.request.category.*;
import com.tks.grocerymono.dto.response.category.*;
import com.tks.grocerymono.entity.Category;
import com.tks.grocerymono.entity.Product;
import com.tks.grocerymono.repository.CategoryRepository;
import com.tks.grocerymono.repository.ImageRepository;
import com.tks.grocerymono.repository.ProductRepository;
import com.tks.grocerymono.service.CategoryService;
import com.tks.grocerymono.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CloudinaryClient cloudinaryClient;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    @Value("${cloudinary.category-prefix-path}")
    private String cloudinaryCategoryPrefixPath;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest requestData) {
        try {
            UploadImageClientResponse clientResponse = cloudinaryClient.uploadImage(UploadImageClientRequest.builder()
                    .file(requestData.getMultipartFile().getBytes())
                    .pathToFile(cloudinaryCategoryPrefixPath)
                    .build());
            Category category = Category.builder()
                    .name(requestData.getName())
                    .code(requestData.getCode())
                    .cloudinaryId(clientResponse.getCloudinaryId())
                    .imageUrl(clientResponse.getImageUrl())
                    .build();
            category = categoryRepository.save(category);
            return new CreateCategoryResponse(Mapper.map(category, CategoryResponse.class));
        } catch (IOException e) {
            return null;
        }
    }
    @Transactional
    @Override
    public DeleteCategoryByIdResponse deleteCategoryById(DeleteCategoryByIdRequest requestData) {
        Category category = categoryRepository.findById(requestData.getId()).get();
        List<Product> productList = category.getProductList();
        for (Product product : productList) {
            imageRepository.deleteAll(product.getImageList());
        }
        productRepository.deleteAll(productList);
        if (category.getCloudinaryId() != null) {
            cloudinaryClient.deleteImage(new DeleteImageClientRequest(List.of(category.getCloudinaryId())));
        }
        categoryRepository.deleteById(requestData.getId());
        return new DeleteCategoryByIdResponse(requestData.getId());
    }

    @Override
    public GetAllCategoryResponse getAllCategory(GetAllCategoryRequest requestData) {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = Mapper.mapList(categoryList, CategoryResponse.class);
        return GetAllCategoryResponse
                .builder()
                .categoryResponseList(categoryResponseList)
                .build();
    }

    @Override
    public GetNumberOfCategoryResponse getNumberOfCategory(GetNumberOfCategoryRequest requestData) {
        return new GetNumberOfCategoryResponse(categoryRepository.count());
    }

    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest requestData) {
        try {
            Category category = categoryRepository.findById(requestData.getId()).orElseThrow(NoSuchElementException::new);
            Category updatedCategory = Mapper.map(requestData, category);
            if (requestData.getImageUrl() == null) {
                cloudinaryClient.deleteImage(new DeleteImageClientRequest(List.of(updatedCategory.getCloudinaryId())));
                updatedCategory.setImageUrl(null);
                updatedCategory.setCloudinaryId(null);
            }
            if (requestData.getMultipartFile() != null) {
                UploadImageClientResponse clientResponse = cloudinaryClient.uploadImage(UploadImageClientRequest.builder()
                        .file(requestData.getMultipartFile().getBytes())
                        .pathToFile(cloudinaryCategoryPrefixPath)
                        .build());
                updatedCategory.setCloudinaryId(clientResponse.getCloudinaryId());
                updatedCategory.setImageUrl(clientResponse.getImageUrl());
            }
            updatedCategory = categoryRepository.save(updatedCategory);
            return new UpdateCategoryResponse(Mapper.map(updatedCategory, CategoryResponse.class));
        } catch (IOException e) {
            return null;
        }
    }
}

