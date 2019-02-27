package br.com.reclameaqui.complaints.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
class Company implements Serializable {

    private static final long serialVersionUID = -2725338351193263653L;

    @NotBlank(message = "Company name must be informed")
    private String name;
}
