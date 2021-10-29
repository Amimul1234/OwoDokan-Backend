package com.shopKpr.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @NotNull
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(
                "mongodb+srv://shopKpr:ShopKpr-2021@cluster0.amkd0.mongodb.net/shopKpr?retryWrites=true&w=majority");
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return mongoClient().getDatabase("shopkpr").getName();
    }
}
