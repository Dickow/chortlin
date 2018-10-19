package com.dickow.chortlin.dtupay.dtu;

import com.dickow.chortlin.dtupay.shared.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dtu/pay")
public class DTUPayController {
    private final DTUBankIntegration bankIntegration;

    @Autowired
    public DTUPayController(DTUBankIntegration bankIntegration) {
        this.bankIntegration = bankIntegration;
    }

    @PostMapping("{merchant}/{token}")
    public ResponseEntity<Boolean> pay(
            @PathVariable String merchant, @RequestBody int amount, @PathVariable String token) {
        Console.invocation(this.getClass());
        var result = bankIntegration.transferMoney(merchant, token, amount);
        return ResponseEntity.ok(result);
    }
}
