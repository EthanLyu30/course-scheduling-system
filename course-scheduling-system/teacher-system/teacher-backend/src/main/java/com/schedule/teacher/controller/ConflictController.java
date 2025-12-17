package com.schedule.teacher.controller;

import com.schedule.teacher.dto.ConflictResolveDTO;
import com.schedule.teacher.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/conflicts")
@Tag(name = "Conflicts")
public class ConflictController {

    @PostMapping("/resolve")
    @Operation(summary = "提交冲突解决意见（占位，后续与教务协同对接）")
    public Result<Void> resolve(@RequestBody ConflictResolveDTO dto) {
        // TODO: 调用教务协调子系统或写入冲突记录
        return Result.ok();
    }
}
