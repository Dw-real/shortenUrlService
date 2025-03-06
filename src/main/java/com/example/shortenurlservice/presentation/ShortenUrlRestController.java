package com.example.shortenurlservice.presentation;

import com.example.shortenurlservice.application.SimpleShortenUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ShortenUrlRestController {

    private final SimpleShortenUrlService simpleShortenUrlService;

    @Autowired
    public ShortenUrlRestController(SimpleShortenUrlService simpleShortenUrlService) {
        this.simpleShortenUrlService = simpleShortenUrlService;
    }

    // 단축 URL 생성 API
    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
                simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        return ResponseEntity.ok().body(shortenUrlCreateResponseDto);
    }

    // 단축 URL 리다이렉트 API
    @RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey
    ) throws URISyntaxException {
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    // 단축 URL 정보 조회 API
    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(@PathVariable String shortenUrlKey) {
        ShortenUrlInformationDto shortenUrlInformationDto
                = simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);
        return ResponseEntity.ok().body(shortenUrlInformationDto);
    }
}
