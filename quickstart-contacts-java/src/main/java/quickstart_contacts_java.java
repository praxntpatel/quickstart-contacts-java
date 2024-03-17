// Import Java Utilities
import java.util.*;
import static spark.Spark.*;
import com.nylas.NylasClient;
import com.nylas.models.*;

//Import DotEnv to handle .env files
import io.github.cdimascio.dotenv.Dotenv;

public class quickstart_contacts_java {

    public static void main(String[] args) {
        // Load the .env file
        Dotenv dotenv = Dotenv.load();
        // Connect it to Nylas using the Access Token from the .env file
        NylasClient nylas = new NylasClient.Builder(dotenv.get("NYLAS_API_KEY")).apiUri(dotenv.get("NYLAS_API_URI")).build();

        get("/nylas/auth", (request, response) -> {

            List<String> scope = new ArrayList<>();
            scope.add("https://www.googleapis.com/auth/calendar");

            UrlForAuthenticationConfig config = new UrlForAuthenticationConfig(dotenv.get("NYLAS_CLIENT_ID"),
                    "http://localhost:4567/oauth/exchange",
                    AccessType.ONLINE,
                    AuthProvider.GOOGLE,
                    Prompt.DETECT,
                    scope,
                    true,
                    "sQ6vFQN",
                    "swag@nylas.com");

            String url = nylas.auth().urlForOAuth2(config);
            response.redirect(url);
            return null;
        });

        get("/oauth/exchange", (request, response) -> {
            String code = request.queryParams("code");
            if(code == null) { response.status(401);}
            assert code != null;
            CodeExchangeRequest codeRequest = new CodeExchangeRequest(
                    "http://localhost:4567/oauth/exchange",
                    code,
                    dotenv.get("NYLAS_CLIENT_ID"),
                    null,
                    null);
            try{
                CodeExchangeResponse codeResponse = nylas.auth().exchangeCodeForToken(codeRequest);
                request.session().attribute("grant_id", codeResponse.getGrantId());
                return "%s".formatted(codeResponse.getGrantId());
            }catch(Exception e){
                return  "%s".formatted(e);
            }
        });

        get("/nylas/list-contacts", (request, response) -> {
            try {
                ListContactsQueryParams listContactsQueryParams =
                        new ListContactsQueryParams.Builder().
                                limit(5).build();
                List<Contact> contacts = nylas.contacts().list(request.session().attribute("grant_id"),
                        listContactsQueryParams).getData();
                return "%s".formatted(contacts);
            }catch (Exception e) {
                return "%s".formatted(e);
            }
        });

        get("/nylas/create-contact", (request, response) -> {
            try {
                List<ContactEmail> contactEmails = new ArrayList<>();
                contactEmails.add(new ContactEmail("swag@nylas.com","work"));

                List<WebPage> contactWebpages = new ArrayList<>();
                contactWebpages.add(new WebPage("https://www.nylas.com", "work"));

                CreateContactRequest requestBody = new CreateContactRequest.Builder().
                        emails(contactEmails).
                        companyName("Nylas").
                        givenName("Nylas' Swag").
                        notes("This is good swag").
                        webPages(contactWebpages).
                        build();

                Response<Contact> contact = nylas.contacts().create(dotenv.get("GRANT_ID"), requestBody);
                return "%s".formatted(contact);
            }catch (Exception e) {
                return "%s".formatted(e);
            }
        });
    }
}
