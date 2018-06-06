package com.azhen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/table")
public class TableController {
    @Resource
    TableService tableService;
    @RequestMapping("test")
    public String test() {
        tableService.updateState();
        return "ok";
    }
}
