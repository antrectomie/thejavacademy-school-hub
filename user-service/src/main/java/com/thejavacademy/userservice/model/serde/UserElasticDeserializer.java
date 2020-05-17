package com.thejavacademy.userservice.model.serde;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thejavacademy.userservice.model.UserElastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class UserElasticDeserializer extends StdDeserializer<UserElastic> {


    @Autowired
    public ObjectMapper objectMapper;

    public UserElasticDeserializer() {
        this(UserElastic.class);
    }

    protected UserElasticDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserElastic deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String id = node.get(UserFields.ID).asText();
        String email = node.get(UserFields.EMAIL).asText();
        String username = node.get(UserFields.USERNAME).asText();
        String firstName = node.get(UserFields.FIRST_NAME).asText();
        String lastName = node.get(UserFields.LAST_NAME).asText();

        return UserElastic.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }


    @PostConstruct
    public void registerDeserializer() {
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(UserElastic.class, this);
        objectMapper.registerModule(module);
    }
}
