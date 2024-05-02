package com.test2.users.repository;

import com.test2.users.entity.Platform;
import com.test2.users.repository.PlatformRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/platform_insert.sql")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlatformRepositoryTest {
    @Autowired
    PlatformRepository platformRepository;

    @Test
    void platformRepositoryIsNotNull() {
        assertNotNull(platformRepository);
    }

    @Test
    void findPlatformByPlatformNameThenReturnEmpty() {
        Iterator<Platform> result = platformRepository.findPlatformByPlatformNameLikeIgnoreCase("empty")
                .iterator();
        assertFalse(result.hasNext());
    }

    @Test
    void findPlatformByPlatformNameThenReturnPlatformEmail() {
        Iterator<Platform> result = platformRepository.findPlatformByPlatformNameLikeIgnoreCase("email1")
                .iterator();
        Platform platform = Platform.of()
                .platformName("email1")
                .bankId(true)
                .lastName(true)
                .build();
        assertTrue(result.hasNext());
        Platform actual = result.next();
        assertThat(platform.getPlatformName())
                .isEqualTo(actual.getPlatformName());
        assertThat(platform.isBankId())
                .isEqualTo(actual.isBankId());
        assertThat(platform.isLastName())
                .isEqualTo(actual.isLastName());
        assertThat(actual.isFirstName()).isFalse();
    }

    @Test
    void findPlatformByPlatformNameThenReturnPlatformBank() {
        Iterator<Platform> result = platformRepository.findPlatformByPlatformNameLikeIgnoreCase("bank1")
                .iterator();
        Platform platform = Platform.of()
                .platformName("bank1")
                .bankId(true)
                .lastName(false)
                .build();
        assertTrue(result.hasNext());
        Platform actual = result.next();
        assertThat(platform.getPlatformName())
                .isEqualTo(actual.getPlatformName());
        assertThat(platform.isBankId())
                .isEqualTo(actual.isBankId());
        assertThat(platform.isLastName())
                .isEqualTo(actual.isLastName());
        assertThat(actual.isFirstName()).isFalse();
    }

    @Test
    void findPlatformByPlatformNameThenReturnPlatformMobile() {
        Iterator<Platform> result = platformRepository.findPlatformByPlatformNameLikeIgnoreCase("mobile1")
                .iterator();
        Platform platform = Platform.of()
                .platformName("mobile1")
                .bankId(false)
                .lastName(true)
                .build();
        assertTrue(result.hasNext());
        Platform actual = result.next();
        assertThat(platform.getPlatformName())
                .isEqualTo(actual.getPlatformName());
        assertThat(platform.isBankId())
                .isEqualTo(actual.isBankId());
        assertThat(platform.isLastName())
                .isEqualTo(actual.isLastName());
        assertThat(actual.isFirstName()).isFalse();
    }
}