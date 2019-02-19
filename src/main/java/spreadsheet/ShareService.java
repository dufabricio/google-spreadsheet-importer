package spreadsheet;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class ShareService {

    @Autowired
    private GoogleServicesAPI googleServices;

    private Drive driveService;

    public ShareService() {

    }

    public Drive getDriveService(){
        if(driveService == null){
            try {
                driveService = googleServices.getDriveService();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        return driveService;
    }

    public void shareSpreadSheet(String fileId) throws IOException {

        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                // Handle error
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                System.out.println("Permission ID: " + permission.getId());
            }
        };

        BatchRequest batch = getDriveService().batch();
        Permission userPermission = new Permission()
                .setType("user")
                .setRole("writer")
                .setEmailAddress("eduardo.fabricio@sbwebservices.net");
        getDriveService().permissions().create(fileId, userPermission)
                .setFields("id")
                .queue(batch, callback);

        batch.execute();
    }

}
