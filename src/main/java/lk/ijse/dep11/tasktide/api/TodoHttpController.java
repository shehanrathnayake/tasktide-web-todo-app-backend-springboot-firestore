package lk.ijse.dep11.tasktide.api;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lk.ijse.dep11.tasktide.service.FirebaseService;
import lk.ijse.dep11.tasktide.to.TodoTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@CrossOrigin
public class TodoHttpController {

    private Firestore dbFirestore;

    @PostConstruct
    public void connectDb() {
        dbFirestore = FirestoreClient.getFirestore();
    }

    @Autowired
    FirebaseService fbService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TodoTO createTodo(@RequestBody @Validated(TodoTO.Create.class) TodoTO todo) {
        try {
            todo.setStatus(false);
            ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").orderBy("id", Query.Direction.DESCENDING).limit(1).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            int newId = 0;
            if (!documents.isEmpty()) {
                String lastId = documents.get(0).getId();
                newId = Integer.parseInt(lastId)+ 1;
            } else {
                newId = 1;
            }
            todo.setId(newId);
            dbFirestore.collection("todos").document(String.valueOf(newId)).set(todo);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return todo;
    }
    @GetMapping(produces = "application/json")
    public List<TodoTO> getAllTodos(@RequestParam("email") String userEmail) {
        List<TodoTO> todos = new LinkedList<>();;
        try {
            ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("email", userEmail).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                todos.add(document.toObject(TodoTO.class));
            }
            return todos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public void updateTodo(@PathVariable("id") int todoId,
                           @RequestBody @Validated(TodoTO.Update.class) TodoTO todo) {
        try {
            ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("id", todoId).whereEqualTo("email", todo.getEmail()).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//
            if (!documents.isEmpty()) {
//                dbFirestore.collection("todos").document(todo.getId()+"").set(todo);
                DocumentReference updatedTodo = dbFirestore.collection("todos").document(todoId + "");
                updatedTodo.update("description", todo.getDescription());
                updatedTodo.update("status", todo.getStatus());
                updatedTodo.update("color", todo.getColor());

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable int id,
                           @RequestParam("email") String userEmail) {
        try {
            ApiFuture<QuerySnapshot> future = dbFirestore.collection("todos").whereEqualTo("id", id).whereEqualTo("email", userEmail).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                dbFirestore.collection("todos").document(id+"").delete();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
