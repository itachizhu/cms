package org.itachi.cms.repository;

import org.itachi.cms.dto.TestDTO;
import org.itachi.cms.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:22
 */
@Component
@Transactional(propagation= Propagation.SUPPORTS, readOnly = true)
public class TestRepository {
    @Autowired
    private TestMapper testMapper;

    public List<TestDTO> getTest() throws Exception {
        return testMapper.getTest();
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public void insertTest(TestDTO testDTO) throws Exception {
        testMapper.insertTest(testDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int deleteRoleDTO(int[] ids){
        return testMapper.deleteTestDTO(ids);
    }
}
