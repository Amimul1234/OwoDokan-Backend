package com.shopKpr.categories.medicine.repository.elasticRepo;

import com.shopKpr.categories.medicine.model.Medicine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<Medicine, String> {
    Optional<Medicine> findByBrandName( String brandName );
}
