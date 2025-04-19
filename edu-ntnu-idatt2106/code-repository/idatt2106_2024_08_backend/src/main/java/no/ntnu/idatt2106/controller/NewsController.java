package no.ntnu.idatt2106.controller;

import com.rometools.rome.feed.rss.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.ntnu.idatt2106.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-endpoints for getting news about finance.
 */
@ApiResponse(responseCode = "401", description = "A valid JWT token was not supplied.",
    content = @Content(schema = @Schema()))
@SecurityRequirement(name = "jwt_auth")
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * Endpoint for getting news about finance from E24.
     *
     * @return a list of items from the E24 Børs og Finans news feed.
     */
    @Operation(summary = "Get news", description = "Get a list of news items.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "News were successfully retrieved.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
    })
    @GetMapping("/news")
    List<Item> getNews() {
        return newsService.getNewsFromE24();
    }
}
