package br.com.reclameaqui.complaints.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Locale implements Serializable {

    private static final long serialVersionUID = 5989051926319604291L;

    @NotBlank(message = "Address must be informed")
    private String address;

    @NotBlank(message = "City must be informed")
    private String city;

    @NotBlank(message = "Zip Code must be informed")
    private String zipCode;

    @NotBlank(message = "State must be informed")
    private String state;

    @NotBlank(message = "Country must be informed")
    private String country;
}
