package lk.ijse.dep11.tasktide.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;
@Service
public class FirebaseService {
    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = new FileInputStream("serviceAccount.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

//    public void saveTodo(TodoTO todo) throws Exception {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").orderBy("id", Query.Direction.DESCENDING).limit(1).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        int newId = 0;
//        if (!documents.isEmpty()) {
//
//            String lastId = documents.get(0).getId();
//            newId = Integer.parseInt(lastId)+ 1;
//        } else {
//            newId = 1;
//        }
//        todo.setId(newId);
//        dbFirestore.collection("todos").document(String.valueOf(newId)).set(todo);
//    }
//
//    public List<TodoTO> getAllTodos(String email) throws Exception {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("email", email).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        List<TodoTO> todos = new LinkedList<>();
//        for (QueryDocumentSnapshot document : documents) {
//            todos.add(document.toObject(TodoTO.class));
//        }
//        return todos;
//    }
//
//    public void updateTodo(TodoTO todo) throws Exception {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("id", todo.getId()).whereEqualTo("email", todo.getEmail()).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//
//        if (!documents.isEmpty()) {
//            dbFirestore.collection("todos").document(String.valueOf(todo.getId())).set(todo);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
//        }
//    }
//
//    public void deleteTodo(int id, String email) throws Exception {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("id", id).whereEqualTo("email", email).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        System.out.println(id + email);
//        System.out.println(documents.size());
//        if (!documents.isEmpty()) {
//            dbFirestore.collection("todos").document(String.valueOf(id)).delete();
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
//        }
//
//    }
}
