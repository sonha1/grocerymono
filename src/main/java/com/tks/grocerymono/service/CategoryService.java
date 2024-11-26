package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.category.*;
import com.tks.grocerymono.dto.response.category.*;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest requestData);
    DeleteCategoryByIdResponse deleteCategoryById(DeleteCategoryByIdRequest requestData);
    GetAllCategoryResponse getAllCategory(GetAllCategoryRequest requestData);
    GetNumberOfCategoryResponse getNumberOfCategory(GetNumberOfCategoryRequest requestData);
    UpdateCategoryResponse updateCategory(UpdateCategoryRequest requestData);
}
