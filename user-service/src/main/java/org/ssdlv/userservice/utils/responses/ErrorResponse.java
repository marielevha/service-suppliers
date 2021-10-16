package org.ssdlv.userservice.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private Date timestamp;

    /**
     * Méthode permettant de formater les messsage de type <<not found>>
     * @param message
     * @param path
     * @return ErrorResponse
     */
    public static ErrorResponse not_found_error(String message, String path) {
        return new ErrorResponse(
            404,
            "Not Found",
            message,
            path,
            new Date()
        );
    }
}
