package org.itachi.cms.repository;

import org.itachi.cms.mapper.AdmusergroupcheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:22
 */
@Component
@Transactional(propagation= Propagation.SUPPORTS, readOnly = true)
public class AdmUserGroupCheckRepository {
    @Autowired
    private AdmusergroupcheckMapper admusergroupcheckMapper;

    public Map<String,Boolean> getAllCheckGroup(long id){
        List<Long>  list = admusergroupcheckMapper.getAllCheckGroup(id);
        if (list.isEmpty() && list.size()<1) {
            return null;
        }

        Map<String,Boolean>roleIdMap =new HashMap<String,Boolean>();

        for (int i = 0; i < list.size(); i++ ){
           String idstr = list.get(i)+"";
            roleIdMap.put(idstr,true) ;
        }
        return roleIdMap;
    }


}
