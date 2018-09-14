package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.api.TypeAPI;
import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.test.interaction.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.Participant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeApiTest {

    private final TypeAPI typeApi = new TypeAPI();

    @Test
    void createParticipantUsingMethodName() {
        var participant = typeApi.participant(MethodReferenceClass.class, "stringReturnMethod");

        Assertions.assertEquals(MethodReferenceClass.class, participant.getClazz());
        Assertions.assertEquals("stringReturnMethod", participant.getMethod().getName());
    }

    @Test
    void createParticipantWithDuplicateMethodName() {
        Assertions.assertThrows(
                TypeAPIException.class,
                () -> typeApi.participant(MethodReferenceClass.class, "duplicateMethod"));
    }

    @Test
    void createParticipantUsingTypeArguments() {
        Participant<MethodReferenceClass> participant =
                typeApi.participant(MethodReferenceClass.class, Void.TYPE, String.class, Integer.class);

        Assertions.assertEquals(Void.TYPE, participant.getMethod().getReturnType());
        Assertions.assertEquals(String.class, participant.getMethod().getParameterTypes()[0]);
        Assertions.assertEquals(Integer.class, participant.getMethod().getParameterTypes()[1]);
        Assertions.assertEquals("voidReturn2InputMethod", participant.getMethod().getName());
    }

    @Test
    void createParticipantUsingTypeArgumentsAndName() {
        var participant = typeApi.participant(
                MethodReferenceClass.class,
                "voidReturn2InputMethod",
                String.class, Integer.class);

        Assertions.assertEquals(Void.TYPE, participant.getMethod().getReturnType());
        Assertions.assertEquals(String.class, participant.getMethod().getParameterTypes()[0]);
        Assertions.assertEquals(Integer.class, participant.getMethod().getParameterTypes()[1]);
        Assertions.assertEquals("voidReturn2InputMethod", participant.getMethod().getName());
    }

    @Test
    void createParticipantUsingTypeArgumentsAndNameNotExisting() {
        Assertions.assertThrows(
                TypeAPIException.class,
                () -> typeApi.participant(
                        MethodReferenceClass.class,
                        "thisMethodDoesNotExist",
                        Integer.TYPE, String.class
                ));
    }
}
