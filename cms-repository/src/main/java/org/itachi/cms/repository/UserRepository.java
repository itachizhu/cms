package org.itachi.cms.repository;

import org.itachi.cms.dto.UserDTO;
import org.itachi.cms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:22
 */
@Component
@Transactional(propagation= Propagation.SUPPORTS, readOnly = true)
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public UserDTO getUserByW3Id(String w3Id) throws Exception {
        return userMapper.getUserByW3Id(w3Id);
    }
}
