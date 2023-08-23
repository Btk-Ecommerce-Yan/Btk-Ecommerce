package com.btk.service;

import com.btk.dto.request.ProductSaveRequestDto;
import com.btk.dto.request.ProductUpdateRequestDto;
import com.btk.dto.response.*;
import com.btk.entity.Brand;
import com.btk.entity.Category;
import com.btk.entity.Product;
import com.btk.entity.enums.ERole;
import com.btk.exception.ErrorType;
import com.btk.exception.ProductManagerException;
import com.btk.mapper.IProductMapper;
import com.btk.repository.IProductRepository;
import com.btk.utility.JwtTokenProvider;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends ServiceManager<Product, String> {
    private final IProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductService(IProductRepository productRepository, BrandService brandService, CategoryService categoryService, JwtTokenProvider jwtTokenProvider) {
        super(productRepository);
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ProductSaveResponseDto save(ProductSaveRequestDto dto, String token) {
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (roles.contains(ERole.SITE_MANAGER.toString())) {
            Product product = IProductMapper.INSTANCE.toProductFromSaveDto(dto);
        /*
        allMatch() --> product "categoryIds" içerisinde "dto' dan" gönderilen "id' ler" eşleşiyorsa(yani tüm durumlar true ise) true döndür,
                       eşleşmeyen herhangi bir durumda false döndür.
         */
            boolean status = product.getCategoryIds().stream().allMatch(categoryId -> categoryService.existByCategoryId(categoryId));
            if (brandService.existByBrandId(product.getBrandId()) && status) {
                save(product);
                return IProductMapper.INSTANCE.toSaveDtoFromProduct(product);
            }
            throw new ProductManagerException(ErrorType.BRAND_AND_CATEGORY_NOT_FOUND);
        } else {
            throw new ProductManagerException(ErrorType.NOT_AUTHORIZED);
        }
    }

    public ProductUpdateResponseDto update(ProductUpdateRequestDto dto, String token) {
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (roles.contains(ERole.SITE_MANAGER.toString())) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new ProductManagerException(ErrorType.PRODUCT_NOT_FOUND));
            boolean status = product.getCategoryIds().stream().allMatch(categoryId -> categoryService.existByCategoryId(categoryId));

            if (brandService.existByBrandId(product.getBrandId()) && status) {
                update(IProductMapper.INSTANCE.updateFromDtoToProduct(dto, product));
                return IProductMapper.INSTANCE.toUpdateDtoFromProduct(product);
            }
            throw new ProductManagerException(ErrorType.BRAND_AND_CATEGORY_NOT_FOUND);
        } else {
            throw new ProductManagerException(ErrorType.NOT_AUTHORIZED);
        }
    }

    public Boolean delete(String productId, String token) {
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (roles.contains(ERole.SITE_MANAGER.toString())) {
            productRepository.deleteById(productId);
            return true;
        } else {
            throw new ProductManagerException(ErrorType.NOT_AUTHORIZED);
        }
    }

    public ProductDetailsResponseDto productDetails(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductManagerException(ErrorType.PRODUCT_NOT_FOUND));

        List<String> categoryNames = product.getCategoryIds().stream()
                .map(categoryId -> categoryService.findById(categoryId))
                .map(category -> category.get().getCategoryName())
                .collect(Collectors.toList());

        Brand brand = brandService.findById(product.getBrandId()).orElseThrow(() -> new ProductManagerException(ErrorType.BRAND_NOT_FOUND));
        /*
        Brand ve Category servislerinden data geldiği için mapper yapsakta build işlemine veya set işlemine ihtiyaç duyacaktık. Bu yüzden hepsini builder ile yönettik.
         */
        ProductDetailsResponseDto productDetailsResponseDto = ProductDetailsResponseDto.builder()
                .productName(product.getProductName())
                .brandName(brand.getBrandName())
                .categoryName(categoryNames)
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .photoImages(product.getPhotoImages())
                .build();
        return productDetailsResponseDto;
    }

    public List<SearchProductResponseDto> searchProductWithCategoryName(String categoryName) {
        Category category = categoryService.getCategoryWithCategoryName(categoryName);
        List<Product> products = productRepository.findProductByCategoryIdsContains(category.getCategoryId());
        List<SearchProductResponseDto> searchProductResponseDto = products.stream().map(product -> {
            SearchProductResponseDto dto = SearchProductResponseDto.builder()
                    .productName(product.getProductName())
                    .photoImages(product.getPhotoImages())
                    .price(product.getPrice())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        return searchProductResponseDto;
    }
    public Double getPriceByProductId(String productId){
        return productRepository.findById(productId).get().getPrice();
    }

    public List<SearchProductResponseDto> searchProductWithProductName(String productName) {
        List<Product> products = productRepository.findProductByProductNameContainsIgnoreCase(productName);
        List<SearchProductResponseDto> searchProductResponseDto = products.stream().map(product -> {
            SearchProductResponseDto dto = SearchProductResponseDto.builder()
                    .productName(product.getProductName())
                    .photoImages(product.getPhotoImages())
                    .price(product.getPrice())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        return searchProductResponseDto;
    }

    public List<SearchProductResponseDto> searchProductWithProductPrice(Double minPrice, Double maxPrice) {
        List<Product> products;
        if (minPrice == null) products = productRepository.findByPriceLessThanEqual(maxPrice);
        else if (maxPrice == null) products = productRepository.findByPriceGreaterThanEqual(minPrice);
        else products = productRepository.findProductByPriceBetween(minPrice, maxPrice);

        List<SearchProductResponseDto> searchProductResponseDto = products.stream().map(product -> {
            SearchProductResponseDto dto = SearchProductResponseDto.builder()
                    .productName(product.getProductName())
                    .photoImages(product.getPhotoImages())
                    .price(product.getPrice())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        return searchProductResponseDto;
    }
       public GetProductDescriptionsFromProductServiceResponseDto findDescriptionsByProductId(String productId){
        Product product=findById(productId).get();
        GetProductDescriptionsFromProductServiceResponseDto dto=IProductMapper.INSTANCE.toGetProductDescriptionsFromProductServiceResponseDtoFromProduct(product);
        return dto;
    }
}
