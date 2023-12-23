package lk.ijse.dep11.tasktide.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseService {
    @PostConstruct
    public void initialize() {
        try {
//            InputStream serviceAccount = new FileInputStream("service-account.json");
//            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(credentials)
//                    .build();
//            FirebaseApp.initializeApp(options);

            GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("service-account.json"));;
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
