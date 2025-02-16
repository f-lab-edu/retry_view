package com.pjw.retry_view.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FCMConfig {

    @PostConstruct
    public void init() throws IOException{
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("retry-view-firebase-adminsdk-fd6yj-e0d4bede58.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount).createScoped("https://www.googleapis.com/auth/firebase.messaging");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }
}
