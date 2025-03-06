package com.example.shortenurlservice.infrastructure;

import com.example.shortenurlservice.domain.ShortenUrl;
import com.example.shortenurlservice.domain.ShortenUrlRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository {

    private final Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

    @Override
    public void saveShortenUrl(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }

    @Override
    public ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrls.get(shortenUrlKey);

        return shortenUrl;
    }
}
