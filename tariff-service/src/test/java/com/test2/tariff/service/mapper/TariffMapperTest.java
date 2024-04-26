package com.test2.tariff.service.mapper;

import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.UpdateTariff;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TariffMapperTest {

    @Test
    void mapToTariff_By_NewTariff_Returns_Tariff() {
        NewTariff newTariff = new NewTariff("name", "2024-04-24",
                "2025-04-24", "description", 20D);
        Tariff expected = Tariff.of()
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 24))
                .description("description")
                .rate(20D)
                .build();
        Tariff actual = TariffMapper.mapToTariff(newTariff);
        assertEquals(expected, actual);
    }

    @Test
    void mapToTariff_By_NewTariff_Returns_Tariff_LocalDate_null() {
        NewTariff newTariff = new NewTariff("name", "20240424",
                "20250424", "description", 20D);
        Tariff expected = Tariff.of()
                .name("name")
                .description("description")
                .rate(20D)
                .build();
        Tariff actual = TariffMapper.mapToTariff(newTariff);
        assertEquals(expected, actual);
    }

    @Test
    void mapToTariff_UpdateTariff_Returns_Tariff() {
        Tariff tariff = Tariff.of()
                .name("oldName")
                .build();
        UpdateTariff updateTariff = UpdateTariff.of()
                .name("new Name")
                .startDate("2024-04-24")
                .endDate("2025-04-24")
                .build();
        TariffMapper.mapToTariff(tariff, updateTariff);
        assertEquals("new Name", tariff.getName());
        assertEquals("2024-04-24", tariff.getStartDate().toString());
        assertEquals("2025-04-24", tariff.getEndDate().toString());
    }

    @Test
    public void test_handleNullName() {
        Tariff tariff = Tariff.of()
                .name("old name")
                .build();
        UpdateTariff updateTariff = new UpdateTariff();
        TariffMapper.mapToTariff(tariff, updateTariff);
        assertEquals("old name", tariff.getName());
    }

    @Test
    public void test_handle_StartDate_EndDate_Null() {
        Tariff tariff = Tariff.of()
                .name("oldName")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 24))
                .build();
        UpdateTariff updateTariff = UpdateTariff.of()
                .startDate("2023-04-24")
                .build();
        TariffMapper.mapToTariff(tariff, updateTariff);
        assertEquals(updateTariff.getStartDate(), tariff.getStartDate().toString());
        assertEquals(tariff.getEndDate(), LocalDate.of(2025, 4, 24));
    }

    @Test
    public void test_handleDescription_Return_newDescription() {
        Tariff tariff = Tariff.of()
                .name("oldName")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 24))
                .description("old description")
                .build();
        UpdateTariff updateTariff = UpdateTariff.of()
                .description("new description")
                .build();
        TariffMapper.mapToTariff(tariff, updateTariff);
        assertEquals("new description", tariff.getDescription());
    }

    @Test
    public void test_handleRate_Return_newRate() {
        Tariff tariff = Tariff.of()
                .rate(10D)
                .build();
        UpdateTariff updateTariff = UpdateTariff.of()
                .rate(20D)
                .build();
        TariffMapper.mapToTariff(tariff, updateTariff);
        assertEquals(20D, tariff.getRate());
    }

    @Test
    void mapToDate_Return_LocalDate() {
        LocalDate expectedDate = LocalDate.of(2024, 4, 24);
        String dateIn = "2024-04-24";
        LocalDate actualDate = TariffMapper.mapToDate(dateIn);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void mapToLocalDate_Return_Null() {
        String dateIn = "abc";
        LocalDate localDate = TariffMapper.mapToDate(dateIn);
        assertNull(localDate);
    }

    @Test
    void mapToUUID_Return_UUID() {
        UUID uuidExpect = UUID.randomUUID();
        String uuidIn = uuidExpect.toString();
        UUID actual = TariffMapper.mapToUUID(uuidIn);
        assertEquals(uuidExpect, actual);
    }

    @Test
    void mapToUUID_Return_Null() {
        String uuidIn = "abcd";
        UUID actual = TariffMapper.mapToUUID(uuidIn);
        assertNull(actual);
    }
}