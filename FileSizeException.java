package postsea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import postsea.response.PsMessage;


@ControllerAdvice
public class FileSizeException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<PsMessage> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new PsMessage("File size exceeds the maximum allowed 16MB limit!"));
    }
}
