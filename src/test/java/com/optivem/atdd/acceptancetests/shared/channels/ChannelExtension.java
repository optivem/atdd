package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChannelExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod().isPresent() &&
               context.getTestMethod().get().isAnnotationPresent(Channel.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        Channel channelAnnotation = testMethod.getAnnotation(Channel.class);
        ChannelType[] channels = channelAnnotation.value();

        // Check if @MethodSource is present (parameterized)
        MethodSource methodSource = testMethod.getAnnotation(MethodSource.class);
        if (methodSource != null) {
            String[] paramNames = Stream.of(testMethod.getParameters())
                .map(p -> p.getName())
                .toArray(String[]::new);

            String source = methodSource.value().length > 0 ? methodSource.value()[0] : testMethod.getName();
            try {
                Method paramMethod = testMethod.getDeclaringClass().getDeclaredMethod(source);
                paramMethod.setAccessible(true);
                Stream<?> paramStream = (Stream<?>) paramMethod.invoke(null);

                List<Object[]> paramList = paramStream
                    .map(argsObj -> (argsObj instanceof Arguments) ? ((Arguments) argsObj).get() : (Object[]) argsObj)
                    .collect(Collectors.toList());

                return Stream.of(channels)
                    .flatMap(channel -> paramList.stream()
                        .map(argsArr -> new ChannelInvocationContext(channel, argsArr, paramNames)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Non-parameterized: no arguments, no param names
            return Stream.of(channels)
                .map(channel -> new ChannelInvocationContext(channel, new Object[0], new String[0]));
        }
    }
}