package io.github.yasenia.core.rest.exception;

import io.github.yasenia.core.exception.BusinessException;
import io.github.yasenia.core.exception.SystemException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/stub")
public class StubController {

    @PostMapping("/throw-biz-error/{errorCode}")
    public void throwBusinessException(@PathVariable String errorCode, @RequestParam(required = false) List<Object> args) throws BusinessException {
        throw new BusinessException(errorCode, isNull(args) ? emptyList() : args) {
        };
    }

    @PostMapping("/throw-sys-error/{errorCode}")
    public void throwSystemException(@PathVariable String errorCode, @RequestParam String description) {
        throw new SystemException(null, errorCode, description) {
        };
    }
}
