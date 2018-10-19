package com.dickow.chortlin.dtupay.shared;

import java.time.LocalDateTime;

public abstract class Console {
    public static void println(String str, Object... formattings){
        System.out.print(String.format("%s ---- ", LocalDateTime.now().toString()));
        System.out.println(String.format(str, formattings));
    }

    public static <T> void invocation(Class<T> clazz){
       var callingMethod = new RuntimeException().getStackTrace()[1];
       Console.println("Invocation of Class<%s>::Method<%s>", clazz.getName(), callingMethod.getMethodName());
    }
}
