package glank.app.examples.google.calendar;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
 
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
 
public class SimpleCalendarTest {
 
    public static void main(String[] args) throws IOException{
        //Two globals that will be used in each step.
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        
        //Create the authorization code flow manager
        Set<String> scope = Collections.singleton(CalendarScopes.CALENDAR);
        String clientId = GCGlobals.clientId;
        String clientSecret = GCGlobals.clientSecret;
        //Use a factory pattern to create the code flow
        AuthorizationCodeFlow.Builder codeFlowBuilder = 
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, 
                        jsonFactory, 
                        clientId, 
                        clientSecret, 
                        scope
                );
        AuthorizationCodeFlow codeFlow = codeFlowBuilder.build();
        
        //set the code flow to use a dummy user
        //in a servlet, this could be the session id
        String userId = "slsienijosieur";
        
        //"redirect" to the authentication url
        String redirectUri = GCGlobals.redirectUri;
        AuthorizationCodeRequestUrl authorizationUrl = codeFlow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(redirectUri);
        System.out.println("Go to the following address:");
        System.out.println(authorizationUrl);
        
        //use the code that is returned as a url parameter
        //to request an authorization token
        System.out.println("What is the 'code' url parameter?");
        String code = new Scanner(System.in).nextLine();
        AuthorizationCodeTokenRequest tokenRequest = codeFlow.newTokenRequest(code);
        tokenRequest.setRedirectUri(redirectUri);
        TokenResponse tokenResponse = tokenRequest.execute();
        
        //Now, with the token and user id, we have credentials
        Credential credential = codeFlow.createAndStoreCredential(tokenResponse, userId);
        
        //Credentials may be used to initialize http requests
        HttpRequestInitializer initializer = credential;
        //and thus are used to initialize the calendar service
        Calendar.Builder serviceBuilder = new Calendar.Builder(
                httpTransport, jsonFactory, initializer);
        serviceBuilder.setApplicationName(GCGlobals.appName);
        Calendar calendar = serviceBuilder.build();
        
        //get some data
        Calendar.CalendarList.List listRequest = calendar.calendarList().list();
        CalendarList feed = listRequest.execute();
        for(CalendarListEntry entry:feed.getItems()){
            System.out.println("ID: " + entry.getId());
            System.out.println("Summary: " + entry.getSummary());
        }
    }
}
