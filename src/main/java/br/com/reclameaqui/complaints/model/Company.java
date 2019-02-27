package br.com.reclameaqui.complaints.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Company implements Serializable {

    private static final long serialVersionUID = -2725338351193263653L;

    @NotBlank(message = "Company name must be informed")
    private String name;
}
