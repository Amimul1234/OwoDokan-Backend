package com.shopKpr.shopKprRegister.repository.elasticSearchRepo;

import com.shopKpr.shopKprRegister.model.ShopKprUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopKprRegElasticRepo extends ElasticsearchRepository<ShopKprUser, String> {

}
