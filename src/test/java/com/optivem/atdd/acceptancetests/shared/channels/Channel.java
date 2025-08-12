package com.optivem.atdd.acceptancetests.shared.channels;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Channel {
    ChannelType[] value();
}

