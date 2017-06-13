package org.itachi.cms.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 16:51
 */
@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminUserApiController extends BaseController {
}
