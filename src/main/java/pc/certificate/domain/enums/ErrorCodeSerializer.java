package pc.certificate.domain.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by liutim on 2016/10/5.
 */
public class ErrorCodeSerializer extends JsonSerializer<ErrorCode> {

        @Override
        public void serialize(ErrorCode value, JsonGenerator generator,
                              SerializerProvider provider) throws IOException,
                JsonProcessingException {
            generator.writeStartObject();
            generator.writeFieldName("errorcode");
            generator.writeNumber(value.getErrorcode());
            generator.writeFieldName("errorinfo");
            generator.writeString(value.getErrorinfo());
            generator.writeEndObject();
        }

}