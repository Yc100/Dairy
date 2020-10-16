package com.yc.controller.others;

import com.yc.auth.Authorization;
import com.yc.common.entity.others.OptionClass;
import com.yc.common.utils.Result;
import com.yc.controller.BasicController;
import com.yc.service.others.OptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务参数管理
 * @author YC
 * @date 2019-3-13 15:13
 */
@RestController
@Authorization
@RequestMapping("/others/option")
@Api(tags="业务参数查询")
public class OptionController extends BasicController {

    @Autowired
    private OptionService optionService;

    @ApiOperation("根据参数大类查询参数列表")
    @PostMapping("/queryOptionList")
    public Result<List<OptionClass>> queryOptionList(@RequestParam("typeId") String typeId) {
        List<OptionClass> optionClasses = optionService.queryOptionList(typeId);
        return new Result<List<OptionClass>>().ok(optionClasses);
    }
}

