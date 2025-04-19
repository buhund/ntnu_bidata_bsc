package no.ntnu.idatt2106.service;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Service layer logic for pulling the latest finance news from RSS news feeds.
 */
@Service
public class NewsService {

    /**
    * Pulls the latest news from the "Børs og Finans" news feed from E24.
    *
    * @return a list of item objects representing the RSS items from the feed.
    * @throws RuntimeException when the service is unable to get content from the RSS feed.
    */
    public List<Item> getNewsFromE24() throws RuntimeException {
    RestClient restClient = RestClient.builder()
        .messageConverters(httpMessageConverters -> {
            RssChannelHttpMessageConverter rssChannelHttpMessageConverter = new RssChannelHttpMessageConverter();
            rssChannelHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_XML, MediaType.APPLICATION_RSS_XML));
            httpMessageConverters.add(rssChannelHttpMessageConverter);
        })
        .build();

    Channel e24BorsAndFinansChannel = restClient.get()
        .uri("https://e24.no/rss2/?seksjon=boers-og-finans")
        .retrieve()
        .body(Channel.class);

    if (e24BorsAndFinansChannel == null) {
        throw new RuntimeException("Could not get E24 RSS channel.");
    }

    List<Item> items = e24BorsAndFinansChannel.getItems();
    items.forEach(newsItem -> newsItem.setForeignMarkup(null));

    return items;
    }
}
