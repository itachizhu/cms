package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by DE-9887 on 2017/4/20.
 */
@Mapper
public interface AdmusergroupcheckMapper {

    List<Long> getAllCheckGroup(long id);
}
