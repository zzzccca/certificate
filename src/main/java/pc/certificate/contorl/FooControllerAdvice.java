package pc.certificate.contorl;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pc.certificate.domain.enums.ErrorCode;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wu on 17-8-30.
 */
@ControllerAdvice
public class FooControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
//        if(ex.getCause()!=null && ex.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException)//精确查询cause里那个异常问题
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(ErrorCode.ERRORFILESIZE, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
