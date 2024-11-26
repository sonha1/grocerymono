package com.tks.grocerymono.controller;

import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.category.*;
import com.tks.grocerymono.dto.response.category.*;
import com.tks.grocerymono.service.CategoryService;
import com.tks.grocerymono.utils.JsonMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseFactory responseFactory;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<CreateCategoryResponse>> createCategory(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String categoryJson
    ) {
        CreateCategoryRequest request = JsonMapperUtil.parseJsonToObject(categoryJson, CreateCategoryRequest.class);
        request.setMultipartFile(file);
        return responseFactory.success(categoryService.createCategory(request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<DeleteCategoryByIdResponse>> deleteCategoryById(@PathVariable("id") int id) {
        return responseFactory.success(categoryService.deleteCategoryById(new DeleteCategoryByIdRequest(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<GetAllCategoryResponse>> getAllCategory() {
        GetAllCategoryRequest request = new GetAllCategoryRequest();
        return responseFactory.success(categoryService.getAllCategory(request));
    }

    @GetMapping("/numberOfCategory")
    ResponseEntity<BaseResponse<GetNumberOfCategoryResponse>> getNumberOfCategory() {
        return responseFactory.success(categoryService.getNumberOfCategory(new GetNumberOfCategoryRequest()));
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<UpdateCategoryResponse>> updateCategory(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("category") String categoryJson
    ) {
        UpdateCategoryRequest request = JsonMapperUtil.parseJsonToObject(categoryJson, UpdateCategoryRequest.class);
        if (file != null) {
            request.setMultipartFile(file);
        }
        return  responseFactory.success(categoryService.updateCategory(request));
    }
}
