package com.dickow.chortlin.dtupay.bank;

import com.dickow.chortlin.dtupay.shared.Console;
import com.dickow.chortlin.dtupay.shared.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank")
public class BankController {

    @PostMapping("transactions")
    public ResponseEntity<Boolean> transfer(@RequestBody TransactionDTO transaction){
        Console.invocation(this.getClass());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
