package erica.be.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {
    @GetMapping("/test")
    fun test(): String {
        return "test"
    }

    @GetMapping("/test2")
    fun test2(): String {
        return "test2 ㅎㅎ"
    }
}
