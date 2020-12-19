package com.appgate.risk.repository;

import com.appgate.risk.repository.document.ProviderLevelServiceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderLevelServiceRepository extends MongoRepository<ProviderLevelServiceDocument, String> {


    ProviderLevelServiceDocument findByProvider(String provider);

}
