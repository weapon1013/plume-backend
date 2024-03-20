package com.plume.backend.api.sample.repository.mybatis;

import com.plume.backend.api.sample.domain.vo.SampleVO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SampleRggepository")
public interface SampleRepository {

    List<SampleVO> selectSampleList() throws DataAccessException;

}
