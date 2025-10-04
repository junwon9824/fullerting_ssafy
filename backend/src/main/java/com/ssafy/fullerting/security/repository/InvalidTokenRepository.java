package com.ssafy.fullerting.security.repository;

import com.ssafy.fullerting.security.model.entity.InvalidToken;
import org.springframework.data.repository.CrudRepository;

public interface InvalidTokenRepository extends CrudRepository<InvalidToken, String> {
}
