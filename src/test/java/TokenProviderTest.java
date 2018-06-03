import Internal.TokenProvider;
import org.junit.Before;

public class TokenProviderTest {

    TokenProvider tokenProvider;

    @Before
    public void initializeTokenProvider() {
        tokenProvider = new TokenProvider(
                "5a658f91e4b0f52492b4979c",
                "43cb8ec1a1884913a28ea5f3");
    }

}
