package org.itachi.cms.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.TestDTO;

import java.util.List;

/**
 * Created by DE-9887 on 2017/4/20.
 */
@Mapper
public interface TestMapper {
    List<TestDTO> getTest();
    void insertTest(TestDTO testDTO );
    int deleteTestDTO(int[] ids);
}
