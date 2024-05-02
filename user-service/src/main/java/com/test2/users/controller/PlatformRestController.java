package com.test2.users.controller;

import com.test2.users.controller.payload.UpdatePlatform;
import com.test2.users.entity.Platform;
import com.test2.users.service.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/platforms/{platformId:\\d+}")
@RequiredArgsConstructor
public class PlatformRestController {
    private final PlatformService platformService;

    @ModelAttribute("platform")
    public Platform getPlatform(@PathVariable("platformId") long platformId) {
        return this.platformService.findPlatformById(platformId)
                .orElseThrow(() -> new NoSuchElementException("customer.errors.platform.not_found"));
    }

    @GetMapping
    public Platform findPlatform(@ModelAttribute("platform") Platform platform) {
        return platform;
    }

    @PutMapping
    public ResponseEntity<?> updatePlatform(@PathVariable("platformId") long platformId,
                                            @Valid @RequestBody UpdatePlatform updatePlatform,
                                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.platformService.updatePlatform(platformId, updatePlatform);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlatform(@PathVariable("platformId") long platformId) {
        this.platformService.deletePlatformById(platformId);
        return ResponseEntity.noContent()
                .build();
    }
}