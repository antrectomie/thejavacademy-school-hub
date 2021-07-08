package com.thejavacademy.userservice.model.serde;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.thejavacademy.userservice.model.UserElastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static com.thejavacademy.userservice.model.serde.UserFields.*;

@Component
public class UserElasticSerializer extends StdSerializer<UserElastic> {

    @Autowired
    private ObjectMapper objectMapper;

    protected UserElasticSerializer() {
        this(UserElastic.class);
    }

    protected UserElasticSerializer(Class<UserElastic> claz) {
        super(claz);
    }

    @Override
    public void serialize(UserElastic userElastic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(ID, userElastic.getId());
        jsonGenerator.writeStringField(EMAIL, userElastic.getEmail());
        jsonGenerator.writeStringField(USERNAME, userElastic.getUsername());
        jsonGenerator.writeStringField(FIRST_NAME, userElastic.getFirstName());
        jsonGenerator.writeStringField(LAST_NAME, userElastic.getLastName());
        jsonGenerator.writeEndObject();
    }


    @PostConstruct
    public void registerSerializer() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(UserElastic.class, this);
        objectMapper.registerModule(simpleModule);
    }
}
