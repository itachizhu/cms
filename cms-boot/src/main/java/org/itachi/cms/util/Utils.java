package org.itachi.cms.util;

import org.itachi.cms.bean.AdminUserParamBean;
import org.itachi.cms.dto.AdminUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by itachi on 2017/5/7.
 * User: itachi
 * Date: 2017/5/7
 * Time: 15:46
 */
public final class Utils {
    private Utils() {

    }

    public static void cleanSessions(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                if (names != null) {
                    while (names.hasMoreElements()) {
                        String attribute = names.nextElement();
                        session.removeAttribute(attribute);
                    }
                }
                session.invalidate();
            }
        } catch (Exception e) {
        }
    }
    public static AdminUserDTO adminUserForm(AdminUserParamBean dminUserParamBean) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setAccount(dminUserParamBean.getAdmaccout());
        adminUserDTO.setPhone(dminUserParamBean.getAdmuserphone());
        adminUserDTO.setId(dminUserParamBean.getAdmuserid());
        adminUserDTO.setMail(dminUserParamBean.getAdmusermail());
        adminUserDTO.setName(dminUserParamBean.getAdmusername());
       return adminUserDTO;
    }

    }

