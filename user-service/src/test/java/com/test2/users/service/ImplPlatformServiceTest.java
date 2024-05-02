package com.test2.users.service;

import com.test2.users.controller.payload.NewPlatform;
import com.test2.users.controller.payload.UpdatePlatform;
import com.test2.users.entity.Platform;
import com.test2.users.repository.PlatformRepository;
import com.test2.users.service.ImplPlatformService;
import com.test2.users.service.mapper.PlatformMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplPlatformServiceTest {
    @Mock
    PlatformRepository repository;
    @InjectMocks
    ImplPlatformService service;

    @Test
    public void createPlatformThenReturnPlatform() {
        NewPlatform newPlatform = NewPlatform.of()
                .platformName("email")
                .bankId(true)
                .lastName(true)
                .firstName(true)
                .middleName(true)
                .birthDate(true)
                .passport(true)
                .placeBirth(true)
                .phone(true)
                .email(true)
                .addressRegistered(true)
                .addressLife(true)
                .build();
        Platform expected = PlatformMapper.mapToPlatform(newPlatform);
        expected.setId(1L);
        Platform platform = PlatformMapper.mapToPlatform(newPlatform);
        doReturn(expected).when(this.repository)
                .save(platform);
        Platform result = this.service.createPlatform(newPlatform);
        assertEquals(expected, result);
        verify(this.repository).save(PlatformMapper.mapToPlatform(newPlatform));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void updatePlatformThenThrowNoSuchElementException() {
        Long platformId = 1L;
        UpdatePlatform updatePlatform = new UpdatePlatform();
        assertThrows(NoSuchElementException.class,
                () -> this.service.updatePlatform(platformId, updatePlatform));
        verify(this.repository).findById(platformId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void updatePlatformThenUpdatePlatform() {
        Long platformId = 1L;
        Platform platform = Platform.of()
                .id(platformId)
                .platformName("email")
                .bankId(true)
                .lastName(true)
                .firstName(true)
                .middleName(true)
                .birthDate(true)
                .passport(true)
                .placeBirth(true)
                .phone(true)
                .email(true)
                .addressRegistered(true)
                .addressLife(true)
                .build();
        UpdatePlatform updatePlatform = UpdatePlatform.of()
                .platformName("newEmail")
                .build();
        doReturn(Optional.of(platform))
                .when(this.repository).findById(platformId);
        this.service.updatePlatform(platformId, updatePlatform);
        verify(this.repository).findById(platformId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findByIdPlatformThenReturnOptionalEmpty() {
        Platform platform = Platform.of()
                .id(1L)
                .platformName("mobile")
                .build();
        Optional<Platform> result = this.service.findPlatformById(platform.getId());
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(this.repository).findById(platform.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findByIdPlatformThenOptionalNotEmpty() {
        Platform platform = Platform.of()
                .id(1L)
                .platformName("mobile")
                .build();
        doReturn(Optional.of(platform)).when(this.repository)
                .findById(platform.getId());
        Optional<Platform> result = this.service.findPlatformById(platform.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(platform, result.orElseThrow());
        verify(this.repository).findById(platform.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findByNameThenOptionalEmpty() {
        Platform platform = Platform.of()
                .id(1L)
                .platformName("mobile")
                .build();
        Iterator<Platform> result = this.service.findAllPlatform(platform.getPlatformName()).iterator();
        assertNotNull(result);
        assertFalse(result.hasNext());
        verify(this.repository).findPlatformByPlatformNameLikeIgnoreCase(platform.getPlatformName());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findByNameThenOptionalNotEmpty() {
        Platform platform = Platform.of()
                .id(1L)
                .platformName("mobile")
                .build();
        doReturn(List.of(platform)).when(this.repository)
                .findPlatformByPlatformNameLikeIgnoreCase(platform.getPlatformName());
        Iterator<Platform> result = this.service.findAllPlatform(platform.getPlatformName()).iterator();
        assertNotNull(result);
        assertTrue(result.hasNext());
        assertEquals(platform, result.next());
        verify(this.repository).findPlatformByPlatformNameLikeIgnoreCase(platform.getPlatformName());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void deleteByIdThenDeletePlatform() {
        Long platformId = 1L;
        this.service.deletePlatformById(platformId);
        verify(this.repository).deleteById(platformId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findAllPlatformThenReturnEmptyList() {
        doReturn(List.of()).when(this.repository)
                .findAll();
        Iterable<Platform> result = this.service.findAllPlatform(null);
        assertEquals(List.of(), result);
        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    void findAllPlatformThenReturnListPlatform() {
        Iterable<Platform> platforms = LongStream.rangeClosed(1L, 4L)
                .mapToObj(i -> Platform.of()
                        .id(i)
                        .platformName("name" + i)
                        .email(true)
                        .build()).toList();
        doReturn(platforms).when(this.repository).findAll();
        Iterable<Platform> result = this.service.findAllPlatform("");
        assertEquals(platforms, result);
        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }
}