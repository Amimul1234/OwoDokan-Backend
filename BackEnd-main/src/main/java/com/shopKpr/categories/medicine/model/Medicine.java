package com.shopKpr.categories.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "medicine")
@org.springframework.data.mongodb.core.mapping.Document(collection = "medicine")
public class Medicine {
    @Id
    @MongoId
    private String documentId;
    @Field(type = Text)
    private String brandName;
    @Field(type = Text)
    private String generics;
    @Field(type = Keyword)
    private String companyName;
    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date creationDate;
    @Field(type = Keyword)
    private String imageUrl;
    @Field(type = Nested)
    private List<DosageForm> dosageFormList;
}
