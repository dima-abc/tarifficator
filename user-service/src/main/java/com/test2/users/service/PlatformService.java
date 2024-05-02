package com.test2.users.service;

import com.test2.users.controller.payload.NewPlatform;
import com.test2.users.controller.payload.UpdatePlatform;
import com.test2.users.entity.Platform;

import java.util.Optional;

public interface PlatformService {
    Platform createPlatform(NewPlatform newPlatform);

    void updatePlatform(Long id, UpdatePlatform updatePlatform);

    Optional<Platform> findPlatformById(Long id);

    void deletePlatformById(Long id);

    Optional<Platform> findPlatformByName(String platformName);

    Iterable<Platform> findAllPlatform(String platformName);
}
