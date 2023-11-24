package simpleTests;

import org.junit.jupiter.api.Test;
import testBase.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class TitleTest extends TestBase {

    @Test
    public void shouldGetCorrectTitleTest() {
        String title = driver.getTitle();
        assertThat(title).isEqualTo(getFromEnv("expectedTitle"));
    }
}