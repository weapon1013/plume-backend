package com.plume.backend.api.sample.repository.jpa;

import com.plume.backend.api.sample.domain.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SamplgggeJpaRepository")
public interface SampleJpaRepository extends JpaRepository<SampleEntity, Long> {

    SampleEntity findBySeqAndDelYn(long seq, String delYn);
}
