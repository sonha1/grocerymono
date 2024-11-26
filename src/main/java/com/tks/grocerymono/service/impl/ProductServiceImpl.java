package com.tks.grocerymono.service.impl;

import com.tks.grocerymono.client.cloudinary.CloudinaryClient;
import com.tks.grocerymono.client.cloudinary.dto.request.DeleteImageClientRequest;
import com.tks.grocerymono.dto.request.bill.GetBestSellingProductIdListRequest;
import com.tks.grocerymono.dto.request.bill.GetBestSellingProductQuantityListRequest;
import com.tks.grocerymono.dto.request.bill.GetRecommendedProductIdListRequest;
import com.tks.grocerymono.dto.request.product.*;
import com.tks.grocerymono.dto.response.bill.BestSellingProductQuantityResponse;
import com.tks.grocerymono.dto.response.bill.GetRecommendedProductIdListResponse;
import com.tks.grocerymono.dto.response.product.*;
import com.tks.grocerymono.entity.Category;
import com.tks.grocerymono.entity.Image;
import com.tks.grocerymono.entity.Product;
import com.tks.grocerymono.repository.*;
import com.tks.grocerymono.service.BillService;
import com.tks.grocerymono.service.ProductService;
import com.tks.grocerymono.utils.Mapper;
import com.tks.grocerymono.utils.PageSupport;
import com.tks.grocerymono.utils.ProductUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private static final int RECENT_DAYS = 30;
    private static final int SIZE = 10;
    private static final int PRODUCT_LIST_SIZE = 5;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductUtil productUtil;
    private final CloudinaryClient cloudinaryClient;
    private final ImageRepository imageRepository;
    private final BillRepository billRepository;
    private final StatisticRepository statisticRepository;
    private final BillService billService;

    @Override
    public CreateProductResponse createProduct(CreateProductRequest requestData) {
        Product product = Mapper.map(requestData, Product.class);

        Category category = categoryRepository.findById(requestData.getCategoryId()).get();
        String productCode = productUtil.convertNameToCode(requestData.getName());
        product.setCategory(category);
        product.setCode(productCode);

        product = productRepository.save(product);

        productUtil.saveProductImages(product, requestData.getFileList());

        ProductResponse productResponse = Mapper.map(product, ProductResponse.class);
        return new CreateProductResponse(productResponse);
    }

    @Transactional
    @Override
    public DeductQuantityListProductResponse deductQuantityListProduct(DeductQuantityListProductRequest requestData) {
        List<DeductingProduct> deductingProductList = requestData.getDeductingProductList();
        deductingProductList.forEach(deductingProduct -> {
            Product product = productRepository.findById(deductingProduct.getProductId()).get();
            int newQuantity = product.getQuantity() - deductingProduct.getDeductingQuantity();
            product.setQuantity(newQuantity);
            productRepository.save(product);
        });
        return new DeductQuantityListProductResponse(deductingProductList);
    }

    @Transactional
    @Override
    public DeleteProductByIdResponse deleteProductById(DeleteProductByIdRequest requestData) {
        Optional<Product> optionalProduct = productRepository.findById(requestData.getId());
        if (optionalProduct.isEmpty()) {
            throw new NoSuchElementException();
        }
        Product product = optionalProduct.get();
        List<Image> imageList = product.getImageList();
        if (imageList != null && !imageList.isEmpty()) {
            List<String> cloudinaryIdList = product.getImageList()
                    .stream()
                    .map(Image::getCloudinaryId)
                    .toList();
            cloudinaryClient.deleteImage(new DeleteImageClientRequest(cloudinaryIdList));
            imageRepository.deleteAll(product.getImageList());
        }
        productRepository.delete(product);
        return new DeleteProductByIdResponse(requestData.getId());
    }

    @Override
    public GetAvailableProductByIdResponse getAvailableProductById(GetAvailableProductByIdRequest requestData) {
        Product product = productRepository
                .findById(requestData.getProductId())
                .get();
        GetAvailableProductByIdResponse response = new GetAvailableProductByIdResponse();
        if (product.getQuantity() >= requestData.getQuantity()) {
            ProductResponse productResponse = Mapper.map(product, ProductResponse.class);
            response.setAvailable(true);
            response.setProductResponse(productResponse);
        } else {
            response.setAvailable(false);
        }
        return response;
    }

    @Override
    public GetAvailableProductListResponse getAvailableProductList(GetAvailableProductListRequest requestData) {
        GetAvailableProductListResponse response = GetAvailableProductListResponse
                .builder()
                .isAvailable(true)
                .build();
        List<ProductResponse> unavailableProductResponseList = new ArrayList<>();
        requestData.getAvailableProductRequestList().forEach(availableProductRequest -> {
            Product product = productRepository.findById(availableProductRequest.getProductId()).get();
            if (product.getQuantity() < availableProductRequest.getQuantity()) {
                response.setAvailable(false);
                ProductResponse productResponse = productUtil.mapProductToProductResponse(product);
                unavailableProductResponseList.add(productResponse);
            }
        });
        if (!response.isAvailable()) {
            response.setUnavailableProductResponseList(unavailableProductResponseList);
        }
        return response;
    }


    @Override
    public GetBestSellingProductResponse getBestSellingProduct(GetBestSellingProductRequest requestData) {
        List<Integer> productIdList = billRepository.getBestSellingProductIdList(
                RECENT_DAYS,
                SIZE
        );

        List<Product> productList = productRepository.findAllById(productIdList);
        List<ProductResponse> productResponseList = productList.stream()
                .map(productUtil::mapProductToProductResponse)
                .toList();
        return new GetBestSellingProductResponse(productResponseList);
    }

    @Override
    public GetBestSellingProductQuantityResponse getBestSellingProductQuantity(GetBestSellingProductQuantityRequest requestData) {

        List<BestSellingProductQuantityResponse> productQuantityResponseList = billService.
                getBestSellingProductQuantityList(new GetBestSellingProductQuantityListRequest(RECENT_DAYS, SIZE)).getProductQuantityResponseList();

        List<Product> productList = productRepository.findAllById(productQuantityResponseList
                .stream()
                .map(BestSellingProductQuantityResponse::getId)
                .toList());

        List<BestSellingProductResponse> bestSellingProductResponseList = productQuantityResponseList
                .stream()
                .map(productQuantityClientResponse -> {
                    BestSellingProductResponse bestSellingProductResponse =
                            Mapper.map(productQuantityClientResponse, BestSellingProductResponse.class);
                    for (Product product : productList) {
                        if (product.getId() == bestSellingProductResponse.getId()) {
                            bestSellingProductResponse.setName(product.getName());
                            break;
                        }
                    }
                    return bestSellingProductResponse;
                })
                .toList();
        return new GetBestSellingProductQuantityResponse(bestSellingProductResponseList);
    }

    @Override
    public GetNumberOfProductResponse getNumberOfProduct(GetNumberOfProductRequest requestData) {
        return new GetNumberOfProductResponse(productRepository.count());
    }

    @Override
    public GetProductByCategoryIdResponse getProductByCategoryId(GetProductByCategoryIdRequest requestData) {
        List<Product> productList = productRepository.findByCategoryId(requestData.getCategoryId(), null);
        List<ProductResponse> productResponseList = productList
                .stream()
                .map(product -> {
                    ProductResponse productResponse = productUtil.mapProductToProductResponse(product);
                    productResponse.setCategoryId(requestData.getCategoryId());
                    return productResponse;
                })
                .toList();
        return new GetProductByCategoryIdResponse(productResponseList);
    }

    @Override
    public PageSupport<ProductResponse> getProductByCategoryIdPaging(GetProductByCategoryIdPagingRequest requestData) {
        Pageable pageable = requestData.getPageable();
        List<Product> productList = productRepository.findByCategoryId(requestData.getCategoryId(), pageable);
        List<ProductResponse> productResponseList = productList
                .stream()
                .map(productUtil::mapProductToProductResponse)
                .toList();
        return new PageSupport<>(
                productResponseList,
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                productResponseList.size()
        );
    }


    @Override
    public ProductResponse getProductById(GetProductByIdRequest requestData) {
        Product product = productRepository.findById(requestData.getId()).get();
        return productUtil.mapProductToProductResponse(product);
    }

    @Override
    public GetProductByIdListResponse getProductByIdList(GetProductByIdListRequest requestData) {
        List<Product> productList = productRepository.findAllById(requestData.getProductIdList());
        List<ProductResponse> productResponseList = productList
                .stream()
                .map(productUtil::mapProductToProductResponse)
                .toList();
        return new GetProductByIdListResponse(productResponseList);
    }

    @Override
    public GetRecommendedProductResponse getRecommendedProduct(GetRecommendedProductRequest requestData) {
        GetRecommendedProductIdListResponse recommendedProductIdList = billService.getRecommendedProductIdList(
                GetRecommendedProductIdListRequest.builder().recentDays(RECENT_DAYS).size(SIZE).accessToken(requestData.getAccessToken()).build()
        );
        List<Integer> productIdList = recommendedProductIdList.getProductIdList();
        List<Product> productList = productIdList.isEmpty()
                ?
                productRepository.findTopDistinctByCategory(SIZE)
                :
                productRepository.findAllById(productIdList);
        List<ProductResponse> productResponseList = productList.stream()
                .map(productUtil::mapProductToProductResponse)
                .toList();
        return new GetRecommendedProductResponse(productResponseList);
    }

    @Override
    public GetSimilarProductListByIdResponse getSimilarProductListById(GetSimilarProductListByIdRequest requestData) {
        List<Integer> bestSellingProductIdList = billService
                .getBestSellingProductIdList(new GetBestSellingProductIdListRequest(RECENT_DAYS, SIZE))
                .getProductIdList();


        List<Product> bestSellingProductList = productRepository.findAllById(bestSellingProductIdList);
        Product requestProduct = productRepository.findById(requestData.getId()).orElseThrow(NoSuchElementException::new);
        int requestProductCategoryId = requestProduct.getCategory().getId();
        bestSellingProductList.removeIf(product -> product.getCategory().getId() != requestProductCategoryId
                || product.getId() == requestData.getId());
        if (bestSellingProductList.size() < PRODUCT_LIST_SIZE) {
            int querySize = PRODUCT_LIST_SIZE - bestSellingProductList.size();
            List<Product> addingProductList = productRepository.findTopRandomByCategoryIdAndIdNot(
                    requestProductCategoryId,
                    requestData.getId(),
                    querySize);
            addingProductList.forEach(product -> {
                if (!bestSellingProductList.contains(product)) {
                    bestSellingProductList.add(product);
                }
            });
        }
        List<ProductResponse> productResponseList = bestSellingProductList
                .stream()
                .map(productUtil::mapProductToProductResponse)
                .toList();
        return new GetSimilarProductListByIdResponse(productResponseList);
    }

    @Override
    public SearchProductResponse searchProduct(SearchProductRequest requestData) {
        if (requestData.getQuery().isBlank()) {
            return new SearchProductResponse(new ArrayList<>());
        } else {
            Pageable pageable = PageRequest.of(0, 8);
            List<Product> productList = productRepository.findByNameContainingIgnoreCase(requestData.getQuery(), pageable);
            List<ProductResponse> productResponseList = productList
                    .stream()
                    .map(productUtil::mapProductToProductResponse)
                    .toList();
            return new SearchProductResponse(productResponseList);
        }
    }

    @Override
    public UpdateProductResponse updateProduct(UpdateProductRequest requestData) {
        Product updatingProduct = productRepository.findById(requestData.getId()).orElseThrow(NoSuchElementException::new);
        List<Image> imageList = updatingProduct.getImageList();

        Product updatedProduct = Mapper.map(requestData, updatingProduct);
        Category category = categoryRepository.findById(requestData.getCategoryId()).get();
        updatedProduct.setCategory(category);
        updatedProduct = productRepository.save(updatedProduct);

        List<Image> deletedImageList = imageList.stream()
                .filter(image -> !requestData.getImageUrlList().contains(image.getUrl()))
                .toList();
        // DELETE Images if the ImageURLList size in Request is less than old ImageURLList
        if (!deletedImageList.isEmpty()) {
            cloudinaryClient.deleteImage(new DeleteImageClientRequest(deletedImageList.stream()
                    .map(Image::getCloudinaryId)
                    .toList()));
            imageRepository.deleteAll(deletedImageList);
        }

        // SAVE Images of Product if Request contains new FileList of Images
        if (requestData.getFileList() != null) {
            productUtil.saveProductImages(updatedProduct, requestData.getFileList());
        }

        ProductResponse productResponse = productUtil.mapProductToProductResponse(updatedProduct);
        return new UpdateProductResponse(productResponse);
    }
}
