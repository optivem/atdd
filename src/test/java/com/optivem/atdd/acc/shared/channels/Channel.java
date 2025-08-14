package com.optivem.atdd.acc.shared.channels;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(ChannelExtension.class)
public @interface Channel {
    ChannelType[] value();
}