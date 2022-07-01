//package io.github.yasenia.pricing.domain.repository.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.yasenia.pricing.domain.model.constraint.PromotionConstraint;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//@Converter
//public class PromotionConstraintConverter implements AttributeConverter<PromotionConstraint, String> {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(PromotionConstraint constraint) {
//        try {
//            return objectMapper.writeValueAsString(constraint);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public PromotionConstraint convertToEntityAttribute(String columnData) {
//        try {
//            return objectMapper.readValue(columnData, PromotionConstraint.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
