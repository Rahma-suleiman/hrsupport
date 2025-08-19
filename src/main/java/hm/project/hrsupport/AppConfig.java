package hm.project.hrsupport;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class AppConfig {
    // @Bean
    // public ModelMapper modelMapper() {
    // return new ModelMapper();
    // }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true); // <-- This is the fix
        return mapper;
    }

    // The fix is to tell ModelMapper:
    // “If the source (DTO) field is null, skip mapping that field — keep whatever
    // value is already in the destination (entity).”
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.modules(new JavaTimeModule());
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

    // add its dependenc in pom.xml
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Force LocalTime formatting
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm")));
        mapper.registerModule(module);

        return mapper;
    }

 
}
// Why this works
// Without skipNullEnabled(true)

// DTO: { name: "Alice", email: null }

// Entity before mapping: { name: "Old Name", email: "old@example.com" }

// After mapping: { name: "Alice", email: null } ❌ (email is erased)

// With skipNullEnabled(true)

// DTO: { name: "Alice", email: null }

// Entity before mapping: { name: "Old Name", email: "old@example.com" }

// After mapping: { name: "Alice", email: "old@example.com" } ✅ (email is
// preserved)
