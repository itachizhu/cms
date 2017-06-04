package org.itachi.cms.bean;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * Created by itachi on 2017/5/17.
 * User: itachi
 * Date: 2017/5/17
 * Time: 18:50
 */
public class AdminUserParamBean {

    @QueryParam("admuserphone")
    private String admuserphone;

    @QueryParam("admusername")
    private String admusername;

    @QueryParam("admuserid")
    private Long admuserid;

    @QueryParam("admaccout")
    private String admaccout;

    @QueryParam("admusermail")
    private String admusermail;

    public Long getAdmuserid() {
        return admuserid;
    }

    public void setAdmuserid(Long admuserid) {
        this.admuserid = admuserid;
    }

    public String getAdmaccout() {
        return admaccout;
    }

    public void setAdmaccout(String admaccout) {
        this.admaccout = admaccout;
    }

    public String getAdmuserphone() {
        return admuserphone;
    }

    public void setAdmuserphone(String admuserphone) {
        this.admuserphone = admuserphone;
    }

    public String getAdmusername() {
        return admusername;
    }

    public void setAdmusername(String admusername) {
        this.admusername = admusername;
    }

    public String getAdmusermail() {
        return admusermail;
    }

    public void setAdmusermail(String admusermail) {
        this.admusermail = admusermail;
    }

}
