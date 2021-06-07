package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.AutomaticdataApi;
import com.blockchain.server.cct.entity.Automaticdata;
import com.blockchain.server.cct.service.AutomaticdataService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api()
@RestController
@RequestMapping("/automaticdata")
public class AutomaticdataController extends BaseController {

    @Autowired
    private AutomaticdataService automaticdataService;

    @ApiOperation(value = AutomaticdataApi.list.METHOD_TITLE_NAME,
            notes = AutomaticdataApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(AutomaticdataApi.list.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @ApiParam(AutomaticdataApi.list.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Automaticdata> list = automaticdataService.listAll();
        return generatePage(list);
    }

    @ApiOperation(value = AutomaticdataApi.insert.METHOD_TITLE_NAME,
            notes = AutomaticdataApi.insert.METHOD_TITLE_NOTE)
    @PostMapping("/insert")
    public ResultDTO insert(@ApiParam(AutomaticdataApi.insert.METHOD_API_PARAM) Automaticdata automaticdata) {
        automaticdataService.insertAutomaticdata(automaticdata);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AutomaticdataApi.delete.METHOD_TITLE_NAME,
            notes = AutomaticdataApi.delete.METHOD_TITLE_NOTE)
    @PostMapping("/delete")
    public ResultDTO delete(@ApiParam(AutomaticdataApi.delete.METHOD_API_ID) @RequestParam("id") String id) {
        automaticdataService.deleteAutomaticdata(id);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AutomaticdataApi.update.METHOD_TITLE_NAME,
            notes = AutomaticdataApi.update.METHOD_TITLE_NOTE)
    @PostMapping("/update")
    public ResultDTO update(@ApiParam(AutomaticdataApi.update.METHOD_API_PARAM) Automaticdata automaticdata) {
        automaticdataService.updataAutomaticdata(automaticdata);
        return ResultDTO.requstSuccess();
    }
}
