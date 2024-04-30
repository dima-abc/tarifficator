package com.test2.product.controller;

import com.test2.product.payload.ProductDTO;
import com.test2.product.service.ProductVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/products/version/{uuid}")
@RequiredArgsConstructor
public class ProductVersionController {
    private final ProductVersionService productVersionService;

    @GetMapping("/actual")
    public ProductDTO findActualProductVersion(@PathVariable("uuid") String uuid) {
        return this.productVersionService.findCurrentVersionProductById(uuid)
                .orElseThrow(() -> new NoSuchElementException("product_service.error.product.not_found"));
    }

    @GetMapping("/previous")
    public List<ProductDTO> findPreviousProductVersion(@PathVariable("uuid") String uuid) {
        return this.productVersionService.findPreviousVersionsProductById(uuid);
    }

    @GetMapping("/period")
    public List<ProductDTO> findPeriodProductVersion(@PathVariable("uuid") String uuid,
                                                     @RequestParam("start-period") String startPeriod,
                                                     @RequestParam("end-period") String endPeriod) {
        return this.productVersionService.findBetweenDateProductById(uuid, startPeriod, endPeriod);
    }

    @PostMapping("/revert")
    public ProductDTO revertVersionProduct(@PathVariable("uuid") String uuid,
                                           @RequestParam("target-version") long targetVersion) {
        return this.productVersionService
                .revertProductBeforeVersion(uuid, targetVersion)
                .orElseThrow(() -> new NoSuchElementException("product_service.error.product.version_not_found"));
    }
}
