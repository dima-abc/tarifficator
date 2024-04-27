package com.test2.product.controller;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.service.ProductService;
import com.test2.product.service.ProductVersionService;
import com.test2.product.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;
    private final TariffService tariffService;
    private final ProductVersionService versionService;

    @GetMapping({"", "/"})
    public Product createProduct() {
        Tariff tariff = this.tariffService.createTariff(new Tariff(UUID.randomUUID(), 0));
        UUID authorId = UUID.randomUUID();
        Product product = Product.of()
                .name("product")
                .typeProduct(TypeProduct.CARD)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("описание продукта")
                .tariff(tariff)
                .authorId(authorId)
                .build();
        return null;
    }

    @GetMapping("/u")
    public Iterable<ProductDTO> updateProduct() {
        String uuidProd = "9d6e8587-e14c-4d7c-972f-e8d87971cf96";
        String tariffId = "f4b820f3-359f-4c56-a0c9-faec6980e51d";
        String authorId = UUID.randomUUID().toString();
        ProductDTO productDTO = productService.findProductById(uuidProd).get();
        LocalDate startDate = LocalDate.parse(productDTO.getStartDate()).plusYears(1);
        LocalDate endDate = LocalDate.parse(productDTO.getEndDate()).plusYears(2);
        UpdateProduct updateProduct = UpdateProduct.of()
                .name("product")
                .typeProductId(TypeProduct.LOAN.getId())
                .startDate(startDate.toString())
                .endDate(endDate.toString())
                .description("описание продукта")
                .tariffId(tariffId)
                .authorId(authorId)
                .build();
        productService.updateProduct(uuidProd, updateProduct);
        return productService.findAllProducts();
    }

    @GetMapping("/p")
    public List<ProductDTO> findPrevuis() {
        String uuidProd = "9d6e8587-e14c-4d7c-972f-e8d87971cf96";
        return versionService.findPreviousVersionsProductById(uuidProd);
    }
}
