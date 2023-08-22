package com.btk.service;

import com.btk.dto.request.ProductSaveRequestDto;
import com.btk.dto.request.ProductUpdateRequestDto;
import com.btk.dto.response.ProductSaveResponseDto;
import com.btk.dto.response.ProductUpdateResponseDto;
import com.btk.entity.Brand;
import com.btk.entity.Category;
import com.btk.entity.Product;
import com.btk.exception.ErrorType;
import com.btk.exception.ProductManagerException;
import com.btk.mapper.IProductMapper;
import com.btk.repository.IProductRepository;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends ServiceManager<Product, String> {
    private final IProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductService(IProductRepository productRepository, BrandService brandService, CategoryService categoryService) {
        super(productRepository);
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    public ProductSaveResponseDto save(ProductSaveRequestDto dto){
        Product product = IProductMapper.INSTANCE.toProductFromSaveDto(dto);

        /*
        allMatch() --> product "categoryIds" içerisinde "dto' dan" gönderilen "id' ler" eşleşiyorsa(yani tüm durumlar true ise) true döndür,
                       eşleşmeyen herhangi bir durumda false döndür.
         */
        boolean status = product.getCategoryIds().stream().allMatch(categoryId -> categoryService.existByCategoryId(categoryId));
        if (brandService.existByBrandId(product.getBrandId()) && status) {
            productRepository.save(product);
            return IProductMapper.INSTANCE.toSaveDtoFromProduct(product);
        }
        throw new ProductManagerException(ErrorType.BRAND_AND_CATEGORY_NOT_FOUND);
    }

    public ProductUpdateResponseDto update(ProductUpdateRequestDto dto){
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new ProductManagerException(ErrorType.PRODUCT_NOT_FOUND));
        boolean status = !product.getCategoryIds().stream().map(categoryId -> categoryService.existByCategoryId(categoryId))
                .anyMatch(n -> n == false);

        if (brandService.existByBrandId(product.getBrandId()) && status) {
            update(IProductMapper.INSTANCE.updateFromDtoToProduct(dto, product));
            return IProductMapper.INSTANCE.toUpdateDtoFromProduct(product);
        }
        throw new ProductManagerException(ErrorType.BRAND_AND_CATEGORY_NOT_FOUND);
    }

    public Boolean delete(String productId){
        productRepository.deleteById(productId);
        return true;
    }

}
