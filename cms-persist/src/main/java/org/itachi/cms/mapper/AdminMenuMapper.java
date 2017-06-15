package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.AdminMenuDTO;

import java.util.List;

/**
 * Created by itachi on 2017/6/15.
 * User: itachi
 * Date: 2017/6/15
 * Time: 15:15
 */
@Mapper
public interface AdminMenuMapper {
    List<AdminMenuDTO> select();
}
