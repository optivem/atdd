package com.optivem.atdd.acceptancetests.shared.channels.parametrized;

import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(ChannelParameterizedExtension.class)
public @interface ChannelParameterizedTest {
    ChannelType[] value();
}