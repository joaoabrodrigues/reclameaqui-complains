package br.com.reclameaqui.complaints.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorDTO {

    private Long timestamp;

    private Integer status;

    private List<String> errors;

    private String path;
}
