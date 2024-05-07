package com.test2.client.controller;

import com.test2.client.payload.product.ProductDTO;
import com.test2.client.service.ProductVersionClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/client/products/version/{uuid}")
@RequiredArgsConstructor
public class ProductVersionController {
    private final ProductVersionClientService productVersionService;

    @GetMapping("/actual")
    public Mono<ProductDTO> findActualProductVersion(@PathVariable("uuid") String uuid) {
        return this.productVersionService
                .findActualProductVersion(uuid);
    }

    @GetMapping("/previous")
    public Flux<ProductDTO> findPreviousProductVersion(@PathVariable("uuid") String uuid) {
        return this.productVersionService
                .findPreviousProductVersion(uuid);
    }

    @GetMapping("/period")
    public Flux<ProductDTO> findPeriodProductVersion(@PathVariable("uuid") String uuid,
                                                     @RequestParam("start-period") String startPeriod,
                                                     @RequestParam("end-period") String endPeriod) {
        return this.productVersionService
                .findPeriodProductVersion(uuid, startPeriod, endPeriod);
    }

    @PostMapping("/revert")
    public Mono<ProductDTO> revertVersionProduct(@PathVariable("uuid") String uuid) {
        return this.productVersionService
                .revertVersionProduct(uuid);
    }

}

