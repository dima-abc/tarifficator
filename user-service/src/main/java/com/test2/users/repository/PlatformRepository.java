package com.test2.users.repository;

import com.test2.users.entity.Platform;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
    Optional<Platform> findPlatformByPlatformNameIgnoreCase(String platformName);
    Iterable<Platform> findPlatformByPlatformNameLikeIgnoreCase(String platformName);
}
