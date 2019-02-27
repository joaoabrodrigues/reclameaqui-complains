package br.com.reclameaqui.complaints.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Document(collection = "complaints")
public class Complaint implements Serializable {

    private static final long serialVersionUID = -7586239564231976676L;

    @Id
    private String id;

    @NotBlank(message = "Title must be informed")
    private String title;

    @NotBlank(message = "Description must be informed")
    private String description;

    @Valid
    @NotNull(message = "Locale must be informed")
    private Locale locale;

    @Valid
    @NotNull(message = "Company must be informed")
    private Company company;

    private String lat;

    private String lng;
}
