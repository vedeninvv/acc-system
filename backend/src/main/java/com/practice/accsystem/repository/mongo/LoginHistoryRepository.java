package com.practice.accsystem.repository.mongo;

import com.practice.accsystem.entity.LoginHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginHistoryRepository extends MongoRepository<LoginHistory, String> {
}
