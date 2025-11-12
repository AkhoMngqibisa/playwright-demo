package org.example.toolshop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.example.toolshop.pageobjects.ProductList;
import org.example.toolshop.pageobjects.SearchComponent;
import org.junit.jupiter.api.*;
import java.util.List;
import org.assertj.core.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddingItemsToTheCartTest {

    @DisplayName("Search for pliers")
    @Test
    void searchForPliers(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();

        assertThat(page.locator(".card")).hasCount(4);

        List<String> productNames = page.getByTestId("product-name").allTextContents();
        Assertions.assertThat(productNames).allMatch(name -> name.contains("Pliers"));

        Locator outOfStockItem = page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of Stock"))
                .getByTestId("product-name");

        assertThat(outOfStockItem).hasCount(1);
        assertThat(outOfStockItem).hasText("Long Nose Pliers");
    }

    @DisplayName("When there is no matching product")
    @Test
    void whenThereIsNoMatchingProduct(Page page) {
        SearchComponent searchComponent = new SearchComponent(page);
        ProductList productList = new ProductList(page);
        searchComponent.searchBy("unknown");

        var matchingProducts = productList.getProductNames();

        Assertions.assertThat(matchingProducts).isEmpty();

    }

}
