package dufabricio.googlespreadsheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleServicesAPI {

    @Autowired
    private GoogleAuthorizeUtil googleAuthorizeUtil;

    @Value("${google.api.application-name}")
    public String APPLICATION_NAME;

    public Sheets getSheets() throws IOException, GeneralSecurityException {
        Credential credential = googleAuthorizeUtil.authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME).build();
    }

    public Drive getDriveService() throws IOException, GeneralSecurityException {
        Credential credential = googleAuthorizeUtil.authorize();
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME).build();
    }

}