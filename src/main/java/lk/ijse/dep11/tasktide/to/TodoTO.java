package lk.ijse.dep11.tasktide.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;
import java.io.Serializable;
@Data @AllArgsConstructor @NoArgsConstructor
public class TodoTO implements Serializable {

    @Null(message = "ID should be empty")
    private Integer id;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Null(message = "Status should be empty", groups = Create.class)
    @NotNull(message = "Status should not be empty", groups = Update.class)
    private Boolean status;

    @NotNull(message = "Color should not be empty", groups = Update.class)
    private String color;

    @NotNull(message = "Email should not be empty")
    @Email(message = "Invalid email format")
    private String email;

    public interface Update extends Default {}

    public interface Create extends Default {}
}
