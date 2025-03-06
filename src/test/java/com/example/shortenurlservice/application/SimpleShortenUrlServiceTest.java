package com.example.shortenurlservice.application;

import com.example.shortenurlservice.domain.NotFoundShortenUrlException;
import com.example.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import com.example.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleShortenUrlServiceTest {

    @Autowired
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("URL을 단축한 후 단축된 URL 키로 조회하면 원래 URL이 조회되어야 한다.")
    void shortenUrlAddTest() {
        String expectedOriginalUrl = "https://www.github.com/Dw-real";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto =
                new ShortenUrlCreateRequestDto(expectedOriginalUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
                simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);

        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        assertTrue(originalUrl.equals(expectedOriginalUrl));
    }

    @Test
    @DisplayName("존재하지 않는 단축 URL을 조회하면 NotFoundShortenUrlException이 발생해야 한다.")
    void findUrlNotExistShortenUrlKeyTest() {
        String notExistShortenUrlKey = "notExist";

        assertThrows(NotFoundShortenUrlException.class, () -> {
            simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(notExistShortenUrlKey);
        });
    }
}