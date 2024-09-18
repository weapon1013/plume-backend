package com.plume.backend.api.auth.repository.jpa;

import com.plume.backend.api.auth.domain.entity.MailEntity;
import org.springframework.data.repository.CrudRepository;

public interface MailRepository extends CrudRepository<MailEntity, String> {
}
