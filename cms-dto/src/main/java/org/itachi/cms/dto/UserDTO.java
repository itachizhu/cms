package org.itachi.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:17
 */
public class UserDTO implements Serializable {
    /*用户id*/
    private Long id;
    /*用户w3帐号*/
    @JsonProperty("w3_id")
    private String w3Id;
    /*用户工号*/
    private String employeeNO;
    /*用户英文名*/
    @JsonProperty("ne")
    private String englishName;
    /*用户中文名*/
    @JsonProperty("nc")
    private String chineseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getW3Id() {
        return w3Id;
    }

    public void setW3Id(String w3Id) {
        this.w3Id = w3Id;
    }

    public String getEmployeeNO() {
        return employeeNO;
    }

    public void setEmployeeNO(String employeeNO) {
        this.employeeNO = employeeNO;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
